package moa.classifiers.meta.imbalanced;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.github.javacliparser.StringOption;
import com.yahoo.labs.samoa.instances.Instance;

import com.yahoo.labs.samoa.instances.Instances;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.Classifier;
import moa.classifiers.MultiClassClassifier;
import moa.classifiers.lazy.neighboursearch.LinearNNSearch;
import moa.classifiers.lazy.neighboursearch.NearestNeighbourSearch;
import moa.core.Measurement;
import moa.core.ObjectRepository;
import moa.core.Utils;
import moa.options.ClassOption;
import moa.tasks.TaskMonitor;

public class OSAMP extends AbstractClassifier implements MultiClassClassifier {

    private static final long serialVersionUID = 1L;

    public ClassOption learnerOption = new ClassOption("learner", 'l', "Classifier to train.", Classifier.class, "trees.HoeffdingAdaptiveTree");
    public StringOption oversamplingStrategyOption = new StringOption("oversamplingStrategy", 'o', "Oversampling strategy: SE or SMOTE", "SMOTE");
    public StringOption generationsNumStrategyOption = new StringOption("generationsNumStrategy", 'g', "Strategy adjusting the number of instances generated per class: RATIO or HYBRID", "HYBRID");
    public IntOption intensityOption = new IntOption("intensity", 'i', "How many times a newly generated (or selected) instance will be shown to the classifier", 100, 1, Integer.MAX_VALUE);
    public IntOption numNeighborsOption = new IntOption("numNeighbors", 'n', "How many neighbors are considered for the SMOTE strategy", 10, 1, Integer.MAX_VALUE);
    public IntOption windowSizeOption = new IntOption("windowSize", 'w', "Window size for all sliding windows used", 1000, 1, Integer.MAX_VALUE);
    public FloatOption ratioCoefOption = new FloatOption("ratioCoef", 'r', "Class ratio weight for the hybrid strategy", 0.5, 0.0, 1.0);
    public FloatOption errorCoefOption = new FloatOption("errorCoef", 'e', "Error weight for the hybrid strategy", 0.5, 0.0, 1.0);
    public FloatOption labelingBudgetOption = new FloatOption("labelingBudget", 'b', "Available labeling budget", 1.0, 0.0, 1.0);

    protected OversamplingFramework oversamplingFramework;

    @Override
    public void prepareForUseImpl(TaskMonitor monitor, ObjectRepository repository) {
        this.oversamplingFramework = this.prepareOversamplingFramework();
        super.prepareForUseImpl(monitor, repository);
    }

    private OversamplingFramework prepareOversamplingFramework() {
        Classifier classifier = (Classifier) getPreparedClassOption(this.learnerOption);

        int intensity = this.intensityOption.getValue();
        int numNeighbors = this.numNeighborsOption.getValue();
        int windowSize = this.windowSizeOption.getValue();
        double ratioCoef = this.ratioCoefOption.getValue();
        double errorCoef = this.errorCoefOption.getValue();
        double labelingBudget = this.labelingBudgetOption.getValue();

        GenerationsNumStrategy generationsNumStrategy = null;
        String gs = this.generationsNumStrategyOption.getValue();
        if (gs.equals("RATIO")) generationsNumStrategy = GenerationsNumStrategy.RATIO_DRIVEN;
        else if ((gs.equals("HYBRID"))) generationsNumStrategy = GenerationsNumStrategy.HYBRID_RATIO_ERROR_DRIVEN;

        OversamplingStrategy oversamplingStrategy = null;
        String os = this.oversamplingStrategyOption.getValue();
        if (os.equals("SE")) {
            oversamplingStrategy = new SingleExpositionStrategy(intensity, generationsNumStrategy, windowSize, ratioCoef, errorCoef);
        }
        else if ((os.equals("SMOTE"))) {
            oversamplingStrategy = new SMOTEStrategy(numNeighbors, intensity, generationsNumStrategy, windowSize, ratioCoef, errorCoef);
        }

        DriftDetectionMethod driftDetector = new MulticlassGMeanPerformanceIndicator(windowSize);

//        System.out.println(String.format("Running with: l=%s o=%s g=%s i=%d n=%d w=%d r=%f e=%f b=%f",
//                classifier.toString(),
//                oversamplingStrategy.toString(),
//                generationsNumStrategy.toString(),
//                intensity, numNeighbors, windowSize, ratioCoef, errorCoef, labelingBudget));

        return new OversamplingFramework(
                classifier,
                oversamplingStrategy,
                driftDetector,
                labelingBudget
        );
    }

    @Override
    public void resetLearningImpl() {
        this.oversamplingFramework = this.prepareOversamplingFramework();
    }

    @Override
    public void trainOnInstanceImpl(Instance inst) {
        this.oversamplingFramework.update(inst);
    }

    public boolean isRandomizable() {
        return true;
    }

    public double[] getVotesForInstance(Instance inst) {
        return this.oversamplingFramework.getClassifier().getVotesForInstance(inst);
    }

    @Override
    public void getModelDescription(StringBuilder out, int indent) {
    }

    @Override
    protected Measurement[] getModelMeasurementsImpl() {
        return null;
    }

}

class OversamplingFramework {

    private Classifier classifier;
    private OversamplingStrategy oversamplingStrategy;
    private DriftDetectionMethod driftDetector;
    private double labelingBudget;
    private Random random = new Random();

    public OversamplingFramework(Classifier classifier, OversamplingStrategy oversamplingStrategy, DriftDetectionMethod driftDetector,
                                 double labelingBudget) {
        this.classifier = classifier;
        this.classifier.prepareForUse();
        this.oversamplingStrategy = oversamplingStrategy;
        this.driftDetector = driftDetector;
        this.labelingBudget = labelingBudget;
    }

    public void update(Instance instance) {
        if (this.random.nextDouble() > this.labelingBudget) return;

        double[] votes = this.classifier.getVotesForInstance(instance);
        this.driftDetector.update(Utils.maxIndex(votes), (int)instance.classValue(), instance.numClasses());
        this.classifier.trainOnInstance(instance);

        HashMap<String, Double> driftIndicators = this.driftDetector.getDetectorIndicators();
        Instances generatedInstances = this.oversamplingStrategy.generateInstances(instance, driftIndicators);

        if (generatedInstances != null) {
            for (int i = 0; i < generatedInstances.numInstances(); i++) {
                this.classifier.trainOnInstance(generatedInstances.get(i));
            }
        }

        this.oversamplingStrategy.updateLabeled(instance);
    }

    public Classifier getClassifier() {
        return this.classifier;
    }

}

abstract class OversamplingStrategy {

    abstract public void updateLabeled(Instance instance);
    abstract public Instances generateInstances(Instance instance, HashMap<String, Double> driftIndicators);

    protected int windowSize;
    protected double maxClassProportion = 0.0;
    protected HashMap<Integer, WindowedValue> labeledClassProportions = new HashMap<>();

    void updateLabeledProportions(Instance instance, int classLabel) {
        double max = 0.0;

        for (int i = 0; i < instance.numClasses(); i++) {
            if (!labeledClassProportions.containsKey(i)) {
                labeledClassProportions.put(i, new WindowedValue(this.windowSize));
            }

            labeledClassProportions.get(i).add((i == classLabel) ? 1.0 : 0.0);

            double classProportion = this.labeledClassProportions.get(i).getAverage();
            if (classProportion > max) {
                max = classProportion;
            }
        }

        this.maxClassProportion = max;
    }
}

interface DriftDetectionMethod {

    void update(int predictedClass, int trueClass, int numClasses);
    HashMap<String, Double> getDetectorIndicators();
}


class SingleExpositionStrategy extends OversamplingStrategy {

    protected int intensity;
    protected GenerationsNumStrategy intensityStrategy;
    protected double ratioCoef;
    protected double errorCoef;
    protected Random random = new Random();

    public SingleExpositionStrategy(int intensity, GenerationsNumStrategy intensityStrategy, int windowSize,
                                    double ratioCoef, double errorCoef) {
        this.intensity = intensity;
        this.intensityStrategy = intensityStrategy;
        this.windowSize = windowSize;
        this.ratioCoef = ratioCoef;
        this.errorCoef = errorCoef;
    }

    @Override
    public void updateLabeled(Instance instance) {
        this.updateLabeledProportions(instance, (int)instance.classValue());
    }

    @Override
    public Instances generateInstances(Instance instance, HashMap<String, Double> driftIndicators) {
        Instances generatedInstances = new Instances(instance.dataset());
        int intensity = this.intensityStrategy((int)instance.classValue(), driftIndicators);

        for (int j = 0; j < intensity; j++) generatedInstances.add(instance.copy());

        return generatedInstances;
    }

    int intensityStrategy(int classValue, HashMap<String, Double> driftIndicators) {
        switch (this.intensityStrategy) {
            case RATIO_DRIVEN: return this.ratioDriven(classValue);
            case HYBRID_RATIO_ERROR_DRIVEN: return this.hybridClassErrorDriven(classValue, driftIndicators);
            default: return -1;
        }
    }

    private int ratioDriven(int classValue) {
        double maxRatio = this.labeledClassProportions.containsKey(classValue) ? this.labeledClassProportions.get(classValue).getAverage() / this.maxClassProportion : 1.0;
        return (int)Math.ceil((1 - maxRatio) * this.intensity);
    }

    private int classErrorDriven(int classValue, HashMap<String, Double> driftIndicators) {
        double classPerformance = driftIndicators.get(Integer.toString(classValue));
        if (Double.isNaN(classPerformance)) return this.ratioDriven(classValue);
        return (int)Math.ceil((1 - classPerformance) * this.intensity);
    }

    private int hybridClassErrorDriven(int classValue, HashMap<String, Double> driftIndicators) {
        return (int)Math.ceil((this.ratioCoef * this.ratioDriven(classValue) + this.errorCoef * this.classErrorDriven(classValue, driftIndicators)));
    }
}


class SMOTEStrategy extends SingleExpositionStrategy {

    Map<Integer, NearestNeighborWindowed> nnWindowed = new HashMap<>();
    private int fixedNumNeighbors;

    public SMOTEStrategy(int numNeighbors, int intensity, GenerationsNumStrategy intensityStrategy, int windowSize,
                         double ratioCoef, double errorCoef) {
        super(intensity, intensityStrategy, windowSize, ratioCoef, errorCoef);
        this.fixedNumNeighbors = numNeighbors;
    }

    @Override
    public void updateLabeled(Instance instance) {
        int classValue = (int)instance.classValue();
        if (!this.nnWindowed.containsKey(classValue)) this.init(classValue, instance);
        this.nnWindowed.get(classValue).insert(instance);
        this.updateLabeledProportions(instance, classValue);
    }

    private void init(int classValue, Instance instanceTemplate) {
        this.nnWindowed.put(classValue, new NearestNeighborWindowed(new LinearNNSearch(), this.windowSize, instanceTemplate));
    }

    @Override
    public Instances generateInstances(Instance instance, HashMap<String, Double> driftIndicators) {
        int classValue = (int)instance.classValue();
        int numNeighborsToFind = this.fixedNumNeighbors;
        if (!this.nnWindowed.containsKey(classValue) || numNeighborsToFind == 0) return null;

        Instances nearestNeighbors = this.nnWindowed.get(classValue).getNearestNeighbors(instance, numNeighborsToFind);

        if (nearestNeighbors != null && nearestNeighbors.numInstances() > 0) {
            Instances generatedInstances = InstanceUtils.createInstances(instance);
            List<Integer> neighborIndices = this.generateNeighborIndices(nearestNeighbors);

            for (int i : neighborIndices) {
                Instance randNeighbor = nearestNeighbors.get(i);
                this.generateLineInstances(instance, randNeighbor, super.intensityStrategy(classValue, driftIndicators), generatedInstances);
            }

            return generatedInstances;
        }
        return null;
    }

    private List<Integer> generateNeighborIndices(Instances nearestNeighbors) {
        return InstanceUtils.instanceIndices(nearestNeighbors);
    }

    private void generateLineInstances(Instance instance, Instance neighbor, int numGenerations, Instances generatedInstances) {
        for (int i = 0; i < numGenerations; i++) {
            Instance newInstance = instance.copy();
            newInstance.setClassValue(instance.classValue());
            double gap = this.random.nextDouble();

            for (int j = 0; j < instance.numAttributes() - 1; j++) {
                if (instance.attribute(j).isNumeric()) {
                    newInstance.setValue(j, instance.value(j) + gap * (neighbor.value(j) - instance.value(j)));
                } else {
                    newInstance.setValue(j, this.random.nextBoolean() ? instance.value(j) : neighbor.value(j));
                }
            }
            generatedInstances.add(newInstance);
        }
    }
}


class MulticlassGMeanPerformanceIndicator implements DriftDetectionMethod {

    private int numClasses;
    private int windowSize;
    private MulticlassGMean performanceIndicators;
    private boolean initialized;

    public MulticlassGMeanPerformanceIndicator(int windowSize) {
        this.windowSize = windowSize;
        this.performanceIndicators = new MulticlassGMean(windowSize, this.numClasses);
        this.initialized = false;
    }

    @Override
    public void update(int predictedClass, int trueClass, int numClasses) {
        if (!this.initialized) this.init(numClasses);
        this.performanceIndicators.add(predictedClass, trueClass);
    }

    private void init(int numClasses) {
        this.numClasses = numClasses;
        this.performanceIndicators = new MulticlassGMean(windowSize, this.numClasses);
        this.initialized = true;
    }

    @Override
    public HashMap<String, Double> getDetectorIndicators() {
        HashMap<String, Double> classPerformanceIndicators = new HashMap<>();
        for (int i = 0; i < this.numClasses; i++) {
            double performanceIndicator = this.performanceIndicators.getWindowGMeanForClass(i);
            classPerformanceIndicators.put(Integer.toString(i), performanceIndicator);
        }
        return classPerformanceIndicators;
    }

}

class MulticlassGMean {

    private int numClasses;
    private ArrayList<WindowedCounterWithNaN> windowTPValues = new ArrayList<>();
    private ArrayList<WindowedCounterWithNaN> windowTNValues = new ArrayList<>();
    private ArrayList<WindowedCounterWithNaN> globalTPValues = new ArrayList<>();
    private ArrayList<WindowedCounterWithNaN> globalTNValues = new ArrayList<>();

    public MulticlassGMean(int windowSize, int numClasses) {
        this.numClasses = numClasses;

        for (int i = 0; i < this.numClasses; i++) {
            this.windowTPValues.add(new WindowedCounterWithNaN(windowSize));
            this.windowTNValues.add(new WindowedCounterWithNaN(windowSize));
            this.globalTPValues.add(new WindowedCounterWithNaN(Integer.MAX_VALUE));
            this.globalTNValues.add(new WindowedCounterWithNaN(Integer.MAX_VALUE));
        }
    }

    public void add(int predictedClass, int trueClass) {
        int correct = (predictedClass == trueClass ? 1 : 0);

        for (int i = 0; i < this.numClasses; i++) {
            if (i == trueClass) {
                this.windowTPValues.get(i).add(correct);
                this.windowTNValues.get(i).add(Double.NaN);
                this.globalTPValues.get(i).add(correct);
            } else {
                int negCorrect = (predictedClass != i ? 1 : 0);
                this.windowTPValues.get(i).add(Double.NaN);
                this.windowTNValues.get(i).add(negCorrect);
                this.globalTNValues.get(i).add(negCorrect);
            }
        }
    }

    public double getWindowGMeanForClass(int classLabel) {
        double sensitivity = this.windowTPValues.get(classLabel).getAverage();
        double specificity = this.windowTNValues.get(classLabel).getAverage();
        return MathUtils.gmean(sensitivity, specificity);
    }
}


class WindowedValue {

    private int windowSize;
    private ArrayList<Double> window;
    private double average = 0.0;
    private double varSum = 0.0;
    private double sum = 0.0;
    private boolean negFix = true;

    public WindowedValue(int windowSize) {
        this.windowSize = windowSize;
        this.window = new ArrayList<>();
    }

    public void add(double value) {
        this.update(value);
        if (value < 0.0) this.negFix = false;
    }

    private void update(double value) {
        double oldAverage = this.average;

        if (this.window.size() == windowSize) {
            this.sum += (value - this.window.get(0));
            this.average += ((value / this.windowSize) - (this.window.get(0) / this.windowSize));
            this.varSum += (value - oldAverage) * (value - this.average) - (this.window.get(0) - oldAverage) * (this.window.get(0) - this.average);
            this.window.remove(0);
        }
        else {
            this.sum += value;
            this.average += ((value - this.average) / (this.window.size() + 1));
            this.varSum = this.varSum + (value - this.average) * (value - oldAverage);
        }

        this.window.add(value);
        if (negFix && this.average < 0.0) this.average = 0.0; // approximation error
    }

    public double getAverage() {
        return this.average;
    }
}


class WindowedCounterWithNaN {

    private int windowSize;
    private ArrayList<Double> window;
    private double sum = 0;
    private int trueSize = 0;

    WindowedCounterWithNaN(int windowSize) {
        this.windowSize = windowSize;
        this.window = new ArrayList<>();
    }

    public void add(double value) {
        if (Double.isNaN(value)) addNaN();
        else addNumber(value);
    }

    private void addNaN() {
        if (this.window.size() == this.windowSize) {
            if (!Double.isNaN(this.window.get(0))) {
                this.sum -= this.window.get(0);
                this.trueSize--;
            }
            this.window.remove(0);
        }

        this.window.add(Double.NaN);
    }

    private void addNumber(double value) {
        if (this.window.size() < this.windowSize) {
            this.sum += value;
            this.trueSize++;
        } else {
            double first = this.window.get(0);
            boolean isNaN = Double.isNaN(first);

            this.sum += (value - (!isNaN ? first : 0.0));
            this.window.remove(0);

            if (isNaN) this.trueSize++;
        }

        this.window.add(value);
    }

    public double getAverage() {
        if (this.trueSize == 0) return Double.NaN;
        return this.sum / this.trueSize;
    }
}


class WindowedInstances {

    private int windowSize;
    private Instances window;
    private Instance centroid;

    public WindowedInstances(int windowSize, Instance instanceTemplate, boolean insert) {
        this.windowSize = windowSize;
        this.window = InstanceUtils.createInstances(instanceTemplate);
        this.centroid = instanceTemplate.copy();

        if (insert) {
            this.window.add(instanceTemplate.copy());
        }
        else {
            for (int i = 0; i < this.centroid.numAttributes() - 1; i++) {
                this.centroid.setValue(i, 0);
            }
        }
    }

    public void add(Instance instance) {
        this.update(instance);

        if (this.window.size() == windowSize) {
            this.window.delete(0);
        }
        this.window.add(instance);
    }

    private void update(Instance instance) {
        for (int i = 0; i < instance.numAttributes() - 1; i++) {
            double newAttributeAverage = this.centroid.value(i);

            if (this.window.size() == windowSize) {
                newAttributeAverage += ((instance.value(i) / this.windowSize) - (this.window.get(0).value(i) / this.windowSize));
            }
            else newAttributeAverage += ((instance.value(i) - newAttributeAverage) / (this.window.size() + 1));

            this.centroid.setValue(i, newAttributeAverage);
        }
    }

    public Instances getInstances() {
        return this.window;
    }

    public Instance getCentroid() {
        return this.centroid;
    }

}


class NearestNeighborWindowed {

    private NearestNeighbourSearch nnSearch;
    private WindowedInstances windowInstances;

    public NearestNeighborWindowed(NearestNeighbourSearch nnSearch, int windowSize, Instance instanceTemplate) {
        this.nnSearch = nnSearch;
        this.windowInstances = new WindowedInstances(windowSize, instanceTemplate, false);
    }

    public void insert(Instance instance) {
        if (instance == null) return;
        this.windowInstances.add(instance);
    }

    public Instances getNearestNeighbors(Instance instance, int k) {
        try {
            this.nnSearch.setInstances(this.windowInstances.getInstances());
            return clean(this.nnSearch.kNearestNeighbours(instance, k), k);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Instances clean(Instances nearestNeighbors, double k) {
        if (nearestNeighbors == null) return null;

        int nnSize = nearestNeighbors.size();
        if (nnSize > k) {
            for (int i = 0; i < nnSize - k; i++) nearestNeighbors.delete(nnSize-i-1);
        }

        return nearestNeighbors;
    }

    public Instance getCentroid() { return this.windowInstances.getCentroid(); }
}


class MathUtils {

    static double gmean(double sensitivity, double specificity) {
        return Math.sqrt(sensitivity * specificity);
    }
}


class InstanceUtils {

    public static Instances createInstances(Instance templateInstance) {
        Instances newInstances = new Instances(templateInstance.copy().dataset());
        newInstances.delete();
        return newInstances;
    }

    public static List<Integer> instanceIndices(Instances nearestNeighbors) {
        return Arrays.stream(IntStream.range(0, nearestNeighbors.numInstances()).toArray())
                .boxed()
                .collect(Collectors.toList());
    }
}


enum GenerationsNumStrategy {
    RATIO_DRIVEN, HYBRID_RATIO_ERROR_DRIVEN
}