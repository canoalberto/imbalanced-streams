package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Utils {
	
	public static void metric(String metricName, String outcome, String path, String[] algorithmFileNames, String[] generatorFileNames) throws Exception {
		ArrayList<String> errorFiles = new ArrayList<String>();
		ArrayList<String> missingFiles = new ArrayList<String>();
		
		System.out.println("");

		System.out.print(metricName.trim() + "\t");
		for(String algorithm : algorithmFileNames)
			System.out.print(algorithm + "\t");
		System.out.println("");
		
		for(String generatorFileName : generatorFileNames)
		{
			System.out.print(generatorFileName + "\t");

			for(int alg = 0; alg < algorithmFileNames.length; alg++)
			{
				int count = 0;
				double sum = 0;
				double lastValue = 0;

				String filename = algorithmFileNames[alg] + "-" + generatorFileName + ".csv";
				
				if(new File(path + filename).exists())
				{
					BufferedReader br = new BufferedReader(new FileReader(new File(path + filename)));
					
					String line;
					line = br.readLine(); // header
					if(line == null) {errorFiles.add(filename); br.close(); continue;}
					String[] columns = line.split(",");

					int classesColumnIndex = -1;
					int numClasses = -1;
					for(int i = 0; i < columns.length; i++)
						if(columns[i].equals("Classes"))
							classesColumnIndex = i;
					
					int aucColumnIndex = -1;
					for(int i = 0; i < columns.length; i++)
					if(columns[i].equals("AUC"))
						aucColumnIndex = i;

					int pmaucColumnIndex = -1;
					for(int i = 0; i < columns.length; i++)
					if(columns[i].equals("PMAUC"))
						pmaucColumnIndex = i;
					
					int kappaColumnIndex = -1;
					for(int i = 0; i < columns.length; i++)
					if(columns[i].equals("Kappa"))
						kappaColumnIndex = i;
					
					int gmeanColumnIndex = -1;
					for(int i = 0; i < columns.length; i++)
					if(columns[i].equals("G-Mean"))
						gmeanColumnIndex = i;
					
					int timeColumnIndex = -1;
					for(int i = 0; i < columns.length; i++)
					if(columns[i].equals("evaluation time (cpu seconds)"))
						timeColumnIndex = i;
					
					int memoryColumnIndex = -1;
					for(int i = 0; i < columns.length; i++)
					if(columns[i].equals("model cost (RAM-Hours)"))
						memoryColumnIndex = i;

					while((line = br.readLine()) != null)
					{
						columns = line.split(",");
						
						// First data line: determine number of classes
						if(numClasses == -1) {
							numClasses = (int) Double.parseDouble(columns[classesColumnIndex]);
						}
						
						int[][] confusionMatrix = new int[numClasses][numClasses];
						
						for(int i = 0; i < numClasses; i++) {
							for(int j = 0; j < numClasses; j++) {
								try {
									confusionMatrix[i][j] = (int) Double.parseDouble(columns[classesColumnIndex+1 + i*numClasses + j]);
								} catch (NumberFormatException e) {
									System.out.println("ERROR: " + filename);
									System.exit(0);
								}
							}
						}
						
						if(metricName.equals("evaluation time (cpu seconds)")) {
							try {
								lastValue = Double.parseDouble(columns[timeColumnIndex]);
							} catch (NumberFormatException e) {
								lastValue = 0;
							}
						}
						else if(metricName.equals("model cost (RAM-Hours)")) {
							try {
								lastValue = Double.parseDouble(columns[memoryColumnIndex]);
							} catch (NumberFormatException e) {
								lastValue = 0;
							}
						}
						else if(metricName.equals("AUC")) {
							try {
								lastValue = Double.parseDouble(columns[aucColumnIndex]);
							} catch (NumberFormatException e) {
								lastValue = 0;
							}
						}
						else if(metricName.equals("PMAUC")) {
							try {
								lastValue = Double.parseDouble(columns[pmaucColumnIndex]);
							} catch (NumberFormatException e) {
								lastValue = 0;
							}
						}
						else if(metricName.equals("Kappa")) {
							try {
								lastValue = Double.parseDouble(columns[kappaColumnIndex]);
							} catch (NumberFormatException e) {
								lastValue = 0;
							}
						}
						else if(metricName.equals("G-Mean")) {
							try {
								lastValue = Double.parseDouble(columns[gmeanColumnIndex]);
							} catch (NumberFormatException e) {
								lastValue = 0;
							}
						}
						
						sum += lastValue;
						count++;
						
					}

					br.close();
				}
				else
				{
					missingFiles.add(filename);
				}

				if(outcome.equalsIgnoreCase("averaged"))
					System.out.print((sum/count) + "\t");
				else
					System.out.print(lastValue + "\t");
			}

			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("");
		
		if(missingFiles.size() != 0) {
			System.out.println("Missing files");
			for(String str : missingFiles) {
				System.out.println(str);
			}
			System.out.println("");
		}
		
		if(errorFiles.size() != 0) {
			System.out.println("Error files");
			for(String str : errorFiles) {
				System.out.println(str);
			}
		}
	}
}