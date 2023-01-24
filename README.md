# A survey on learning from imbalanced data streams: taxonomy, challenges, empirical study, and reproducible experimental framework

[![Build Status](https://app.travis-ci.com/canoalberto/imbalanced-streams.svg?token=SjdT1p8rShU4CRowEYiB&branch=master)](https://travis-ci.com/github/canoalberto/imbalanced-streams)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

This repository provides the source code, algorithms, experimental setup, and results for the experimental review on imbalanced data streams submitted for publication to the journal Machine Learning. The manuscript preprint is available at [arXiv](https://arxiv.org/abs/2204.03719).

This [website](https://people.vcu.edu/~acano/imbalanced-streams) provides interactive plots to display the metrics over time and result tables for each experiment, algorithm, and benchmark.

## Experiments

The package `src/main/java/experiments` provides the scripts for binary and multi-class experiments. It comprises the following experiments:

|Binary class experiment|Script|
|--|--|
| Static imbalance ratio | `binary/Static_Imbalance_Ratio` |
| Increasing imbalance ratio | `binary/Dynamic_Imbalance_Ratio_Increasing` |
| Increasing then decreasing imbalance ratio | `binary/Dynamic_Imbalance_Ratio_Increasing_Decreasing` |
| Flipping imbalance ratio | `binary/Dynamic_Imbalance_Ratio_Flipping` |
| Flipping then reflipping imbalance ratio | `binary/Dynamic_Imbalance_Ratio_Flipping_Reflipping` |
| Instance-level difficulties | `binary/Instance_Level_Difficulties` |
| Concept drift and static imbalance ratio | `binary/Concept_Drift_Static_Imbalance_Ratio` |
| Concept drift and dynamic imbalance ratio | `binary/Concept_Drift_Dynamic_Imbalance_Ratio_Increasing` |
| Real-world imbalanced datasets | `binary/Datasets` |

|Multi-class experiment|Script|
|--|--|
| Static imbalance ratio | `multiclass/Static_Imbalance_Ratio` |
| Dynamic imbalance ratio | `multiclass/Dynamic_Imbalance_Ratio` |
| Concept drift and static imbalance ratio | `multiclass/Concept_Drift_Static_Imbalance_Ratio` |
| Concept drift and dynamic imbalance ratio | `multiclass/Concept_Drift_Dynamic_Imbalance_Ratio` |
| Real-world imbalanced datasets | `multiclass/Datasets` |
| Semi-synthetic imbalanced datasets | `multiclass/Semisynthetic` |

## Algorithms
The package  `src/main/java/moa/classifiers` contains 24 state-of-the-art algorithms for data streams, including those inherited from the MOA 2021.07 dependency in the pom.xml file.
|Algorithm|Script|
|--|--|
|IRL| `meta.imbalanced.RebalanceStream`|
|C-SMOTE| `meta.imbalanced.CSMOTE`|
|VFC-SMOTE| `meta.imbalanced.VFCSMOTE`|
|CSARF| `meta.CSARF`|
|GHVFDT| `trees.GHVFDT`|
|HDVFDT| `trees.HDVFDT`|
|ARF| `meta.AdaptiveRandomForest`|
|KUE| `meta.KUE`|
|LB| `meta.LeveragingBag`|
|OBA| `meta.OzaBagAdwin`|
|SRP| `meta.StreamingRandomPatches`|
|ESOS-ELM| `ann.meta.ESOS_ELM`|
|CALMID| `active.CALMID`|
|MICFOAL| `active.MicFoal`|
|ROSE| `meta.imbalanced.ROSE`|
|OADA| `meta.imbalanced.OnlineAdaBoost`|
|OADAC2| `meta.imbalanced.OnlineAdaC2`|
|ARFR| `meta.imbalanced.AdaptiveRandomForestResampling`|
|SMOTE-OB| `meta.imbalanced.SMOTEOB`|
|OSMOTE| `meta.imbalanced.OnlineSMOTEBagging`|
|OOB| `meta.OOB`|
|UOB| `meta.UOB`|
|ORUB| `meta.imbalanced.OnlineRUSBoost`|
|OUOB| `meta.imbalanced.OnlineUnderOverBagging`|


## Evaluators

The package `src/main/java/moa/evaluation` contains the performance evaluators.

`ImbalancedPerformanceEvaluator` is used for binary class experiments reporting G-Mean, AUC, and Kappa metrics.

`MultiClassImbalancedPerformanceEvaluator` is used for multi-class experiments reporting G-Mean, PMAUC, and Kappa metrics. The evaluators also report the runtime (seconds), memory consumption (RAM-Hours), and the complete confusion matrix for posterior analysis.

## Results

This [website](https://people.vcu.edu/~acano/imbalanced-streams) provides interactive plots to display the metrics over time and result tables for each experiment, algorithm, and benchmark.

Complete csv results for all experiments, algorithms, and benchmarks are available to [download](https://people.vcu.edu/~acano/imbalanced-streams/results-csv.zip) to facilitate the transparency, reproducibility, and extendability of the experimental study.

ARFF files are available to download for [binary class datasets](https://drive.google.com/file/d/1N90LJdFVK_Fy-Z3a45nY26PAiOZTQBsQ/view?usp=sharing), [multi-class datasets](https://drive.google.com/file/d/19F7krux2PnhFJzM7lOwNOLT26NHnZXbo/view?usp=sharing), and [semi-synthetic datasets](https://drive.google.com/file/d/1WogmLnHrBL3zdExegZJAl-nxuO6NCVxZ/view?usp=sharing).

<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td width=50%>
<img src="https://people.vcu.edu/~acano/imbalanced-streams/images/BC_overall_scatter.png" alt="Binary class experiments: G-Mean vs Kappa" width="100%"/>
</td>
<td width=50%>
<img src="https://people.vcu.edu/~acano/imbalanced-streams/images/MC_overall_scatter.png" alt="Multi-class experiments: PMAUC vs Kappa" width="100%"/>
</td>
</tr>
<td width=50%>
<img src="https://people.vcu.edu/~acano/imbalanced-streams/images/BC_overall_barplot.png" alt="Binary class experiments: spiral barplot" width="100%"/>
</td>
<td width=50%>
<img src="https://people.vcu.edu/~acano/imbalanced-streams/images/MC_overall_barplot.png" alt="Multi-class experiments: spiral barplot" width="100%"/>
</td>
</tr>
</table>

## How to add a new algorithm, generator, or evaluator in the framework

We use the [MOA framework](https://moa.cms.waikato.ac.nz/) and its class hierarchy. Adding a new algorithm, generator, or evaluator is the same as adding it in MOA (see [MOA documentation](https://moa.cms.waikato.ac.nz/documentation/)).

First, import the source code to your favorite IDE (Eclipse, VS code, IntelliJ, etc) using Git.

To add a new algorithm, e.g. MyAlgorithmName, create a new Java file at `src/main/java/moa/classifiers/MyAlgorithmName.java`. The class must extend the `AbstractClassifier` class and implement the `public void trainOnInstanceImpl(Instance instance)` and `public double[] getVotesForInstance(Instance instance)` methods.

To add a new generator, e.g. MyGeneratorName, create a new Java file at `src/main/java/moa/streams/generators/MyGeneratorName.java`. The class must implement the `InstanceStream` interface and the `public InstanceExample nextInstance()` method.

To add a new performance metric you can edit an existing evaluator (e.g. `src/main/java/moa/evaluation/ImbalancedPerformanceEvaluator.java`) to add the metric calculation. Alternatively, you can add a new evaluator, e.g. MyEvaluatorName. To do so, create a new Java file at `src/main/java/moa/evaluation/MyEvaluatorName.java`. The class must implement the `ClassificationPerformanceEvaluator` interface, and the `public void addResult(Example<Instance> exampleInstance, double[] classVotes)` and `public Measurement[] getPerformanceMeasurements()` methods.

The next step is to compile the source code using Maven (pom.xml file). Use the command `mvn package` or your IDE options to build the jar file `target/imbalanced-streams-1.0-jar-with-dependencies.jar`

Finally, use any of the scripts provided at `src/main/java/experiments` for the different groups of experiments and add your algorithm, generator, or evaluator. These scripts will generate the command lines used to run the experiments.

## Citation
```
@misc{aguiar2022survey,
  author={Aguiar, Gabriel and Krawczyk, Bartosz and Cano, Alberto},
  title={A survey on learning from imbalanced data streams: taxonomy, challenges, empirical study, and reproducible experimental framework},
  year={2022},
  eprint={2204.03719},
  archivePrefix={arXiv},
  primaryClass={cs.LG}
}
```
