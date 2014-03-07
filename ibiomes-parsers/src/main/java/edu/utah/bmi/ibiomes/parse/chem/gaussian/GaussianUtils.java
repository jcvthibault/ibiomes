package edu.utah.bmi.ibiomes.parse.chem.gaussian;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GaussianUtils {

	/**
	 * Store evolution of SCF energy to CSV file 
	 * @param logFile Gaussian output file
	 * @param outputPath Path to output CSV file
	 * @return Refernce to CSV file
	 * @throws IOException
	 */
	public static File extractEnergyFromLogFile(GaussianOutputFile logFile, String outputPath) throws IOException
	{
		BufferedWriter bw = null;
		File outFile = new File(outputPath);
		try{
			bw = new BufferedWriter(new FileWriter(outFile));
			double[] energy = logFile.getEnergy();
			for (int e=0; e<energy.length; e++){
				bw.append((e+1) + "," + energy[e] + "\n");
			}
			bw.close();
			return outFile;
		}
		catch (Exception e){
			e.printStackTrace();
			if (bw!=null)
				try {
					bw.close();
				} catch (IOException e1) {
				}
			return outFile;
		}
		
	}
}
