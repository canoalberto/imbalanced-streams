package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Utils {
	
	public static void metric(String metricName, String outcome, String path, String[] algorithmFileNames, String[] generatorFileNames) throws Exception {
		ArrayList<String> errorFiles = new ArrayList<String>();
		ArrayList<String> missingFiles = new ArrayList<String>();

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
				
				if(new File(path + "/" + filename).exists())
				{
					BufferedReader br = new BufferedReader(new FileReader(new File(path + "/" + filename)));
					
					String line;
					line = br.readLine(); // header
					if(line == null) {errorFiles.add(filename); br.close(); continue;}
					String[] columns = line.split(",");

					int targetColumn = -1;
					for(int i = 0; i < columns.length; i++)
						if(columns[i].equals(metricName))
							targetColumn = i;
					
					if(targetColumn == -1) {
						System.out.println("Column \"" + metricName + "\" not found on file " + (path + "/" + filename));
					}
					
					while((line = br.readLine()) != null)
					{
						columns = line.split(",");
						
						try {
							lastValue = Double.parseDouble(columns[targetColumn]);
						} catch (NumberFormatException e) {
							lastValue = 0;
						}
						
						sum += lastValue;
						count++;
					}

					br.close();
				}
				else
				{
					missingFiles.add(path + "/" + filename);
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