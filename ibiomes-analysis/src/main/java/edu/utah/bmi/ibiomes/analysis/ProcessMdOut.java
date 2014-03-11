/*
 * iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2014  Julien Thibault, University of Utah
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.utah.bmi.ibiomes.analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;

/**
 * Utility class to parse AMBER MD output files to extract quantities 
 * of interest (Energy, Temperature, Pressure, etc).
 * @author Julien Thibault
 *
 */
public class ProcessMdOut {

	private final boolean DEBUG = false;
	
	private final String filename = "summary";
	private final String filenameAvg = "summary_avg";
	private final String filenameRms = "summary_rms";
	
	private HashMap<String, String> AVG_TIME, AVG_TEMP, AVG_PRES,AVG_ETOT,AVG_EKTOT,
	AVG_EPTOT,AVG_BOND,AVG_ANGLE,AVG_DIHEDRAL,AVG_NB14,AVG_EEL14,AVG_NB,AVG_EEL,
	AVG_EHBOND,AVG_CONSTRAINT,AVG_EKCMT,AVG_VIRIAL,AVG_VOLUME,AVG_TSOLUTE,AVG_TSOLVENT,
	AVG_DENSITY,AVG_ESCF;
	
	private HashMap<String, String> RMS_TIME, RMS_TEMP, RMS_PRES,RMS_ETOT,RMS_EKTOT,
	RMS_EPTOT,RMS_BOND,RMS_ANGLE,RMS_DIHEDRAL,RMS_NB14,RMS_EEL14,RMS_NB,RMS_EEL,
	RMS_EHBOND,RMS_CONSTRAINT,RMS_EKCMT,RMS_VIRIAL,RMS_VOLUME,RMS_TSOLUTE,RMS_TSOLVENT,
	RMS_DENSITY,RMS_ESCF;
	
	private HashMap<String, String> TIME, TEMP, PRES,ETOT,EKTOT,
	EPTOT,BOND,ANGLE,DIHEDRAL,NB14,EEL14,NB,EEL,
	EHBOND,CONSTRAINT,EKCMT,VIRIAL,VOLUME,TSOLUTE,TSOLVENT,
	DENSITY,ESCF;
	
	/**
	 * Process AMBER output files to extract quantities of interest (time series)
	 */
	public ProcessMdOut()
	{
		AVG_TIME = new HashMap<String, String>();
		AVG_TEMP = new HashMap<String, String>();
		AVG_PRES = new HashMap<String, String>();
		AVG_ETOT = new HashMap<String, String>();
		AVG_EKTOT = new HashMap<String, String>();
		AVG_EPTOT = new HashMap<String, String>();
		AVG_BOND = new HashMap<String, String>();
		AVG_ANGLE = new HashMap<String, String>();
		AVG_DIHEDRAL = new HashMap<String, String>();
		AVG_NB14 = new HashMap<String, String>();
		AVG_EEL14 = new HashMap<String, String>();
		AVG_NB = new HashMap<String, String>();
		AVG_EEL = new HashMap<String, String>();
		AVG_EHBOND = new HashMap<String, String>();
		AVG_CONSTRAINT = new HashMap<String, String>();
		AVG_EKCMT = new HashMap<String, String>();
		AVG_VIRIAL = new HashMap<String, String>();
		AVG_VOLUME = new HashMap<String, String>();
		AVG_TSOLUTE = new HashMap<String, String>();
		AVG_TSOLVENT = new HashMap<String, String>();
		AVG_DENSITY = new HashMap<String, String>();
		AVG_ESCF = new HashMap<String, String>();
	
		RMS_TIME = new HashMap<String, String>();
		RMS_TEMP = new HashMap<String, String>();
		RMS_PRES = new HashMap<String, String>();
		RMS_ETOT = new HashMap<String, String>();
		RMS_EKTOT = new HashMap<String, String>();
		RMS_EPTOT = new HashMap<String, String>();
		RMS_BOND = new HashMap<String, String>();
		RMS_ANGLE = new HashMap<String, String>();
		RMS_DIHEDRAL = new HashMap<String, String>();
		RMS_NB14 = new HashMap<String, String>();
		RMS_EEL14 = new HashMap<String, String>();
		RMS_NB = new HashMap<String, String>();
		RMS_EEL = new HashMap<String, String>();
		RMS_EHBOND = new HashMap<String, String>();
		RMS_CONSTRAINT = new HashMap<String, String>();
		RMS_EKCMT = new HashMap<String, String>();
		RMS_VIRIAL = new HashMap<String, String>();
		RMS_VOLUME = new HashMap<String, String>();
		RMS_TSOLUTE = new HashMap<String, String>();
		RMS_TSOLVENT = new HashMap<String, String>();
		RMS_DENSITY = new HashMap<String, String>();
		RMS_ESCF = new HashMap<String, String>();
		
		TIME = new HashMap<String, String>();
		TEMP = new HashMap<String, String>();
		PRES = new HashMap<String, String>();
		ETOT = new HashMap<String, String>();
		EKTOT = new HashMap<String, String>();
		EPTOT = new HashMap<String, String>();
		BOND = new HashMap<String, String>();
		ANGLE = new HashMap<String, String>();
		DIHEDRAL = new HashMap<String, String>();
		NB14 = new HashMap<String, String>();
		EEL14 = new HashMap<String, String>();
		NB = new HashMap<String, String>();
		EEL = new HashMap<String, String>();
		EHBOND = new HashMap<String, String>();
		CONSTRAINT = new HashMap<String, String>();
		EKCMT = new HashMap<String, String>();
		VIRIAL = new HashMap<String, String>();
		VOLUME = new HashMap<String, String>();
		TSOLUTE = new HashMap<String, String>();
		TSOLVENT = new HashMap<String, String>();
		DENSITY = new HashMap<String, String>();
		ESCF = new HashMap<String, String>();
	}
	
	/**
	 * Parse Amber MD output files and save quantities (Energy, Temperature, Pressure, etc) to separate CSV files.
	 * @param files AMBER MD output files
	 * @param outputDir Path to the output directory
	 * @param sortFiles Sort the files or keep list in the given order.
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void process(String[] files, String outputDir, boolean sortFiles) throws NumberFormatException, Exception{
	
		File outDir = null;
		
		//if there is at least one input file
		if (files != null && files.length>0)
		{
			if (sortFiles){
				Utils.sortFileNames(files);
			}
			//check that output directory exists
			outDir = new File(outputDir);
			if (!outDir.exists()){
				System.out.println("Error: output directory '"+ outputDir +"' does not exist!");
				System.exit(-1);
			}
			
			//parse each file
			for (int f=0; f<files.length; f++)
			{
				File file = new File(files[f]);
				System.out.println("Parsing AMBER MD output file '"+ file.getAbsolutePath() +"'");
				processFile(file);
			}
		}
		else {
			System.out.println("Error: need at least one input file!");
			System.exit(-1);
		}
		
		System.out.println("Starting output...");
		
		saveDataToCSV(AVG_TEMP, "AVG_TEMP", outDir.getAbsolutePath() + "/" + filenameAvg + ".TEMP.csv");
		saveDataToCSV(AVG_TSOLUTE, "AVG_TSOLUTE", outDir.getAbsolutePath() + "/" + filenameAvg + ".TSOLUTE.csv");
		saveDataToCSV(AVG_TSOLVENT, "AVG_TSOLVENT", outDir.getAbsolutePath() + "/" + filenameAvg + ".TSOLVENT.csv");
		saveDataToCSV(AVG_PRES, "AVG_PRES", outDir.getAbsolutePath() + "/" + filenameAvg + ".PRES.csv");
		saveDataToCSV(AVG_EKCMT, "AVG_EKCMT", outDir.getAbsolutePath() + "/" + filenameAvg + ".EKCMT.csv");
		saveDataToCSV(AVG_ETOT, "AVG_ETOT", outDir.getAbsolutePath() + "/" + filenameAvg + ".ETOT.csv");
		saveDataToCSV(AVG_EKTOT, "AVG_EKTOT", outDir.getAbsolutePath() + "/" + filenameAvg + ".EKTOT.csv");
		saveDataToCSV(AVG_EPTOT, "AVG_EPTOT", outDir.getAbsolutePath() + "/" + filenameAvg + ".EPTOT.csv");
		saveDataToCSV(AVG_DENSITY, "AVG_DENSITY", outDir.getAbsolutePath() + "/" + filenameAvg + ".DENSITY.csv");
		saveDataToCSV(AVG_VOLUME, "AVG_VOLUME", outDir.getAbsolutePath() + "/" + filenameAvg + ".VOLUME.csv");
		saveDataToCSV(AVG_ESCF, "AVG_ESCF", outDir.getAbsolutePath() + "/" + filenameAvg + ".ESCF.csv");
		
		saveDataToCSV(RMS_TEMP, "RMS_TEMP", outDir.getAbsolutePath() + "/" + filenameRms + ".TEMP.csv");
		saveDataToCSV(RMS_TSOLUTE, "RMS_TSOLUTE", outDir.getAbsolutePath() + "/" + filenameRms + ".TSOLUTE.csv");
		saveDataToCSV(RMS_TSOLVENT, "RMS_TSOLVENT", outDir.getAbsolutePath() + "/" + filenameRms + ".TSOLVENT.csv");
		saveDataToCSV(RMS_PRES, "RMS_PRES", outDir.getAbsolutePath() + "/" + filenameRms + ".PRES.csv");
		saveDataToCSV(RMS_EKCMT, "RMS_EKCMT", outDir.getAbsolutePath() + "/" + filenameRms + ".EKCMT.csv");
		saveDataToCSV(RMS_ETOT, "RMS_ETOT", outDir.getAbsolutePath() + "/" + filenameRms + ".ETOT.csv");
		saveDataToCSV(RMS_EKTOT, "RMS_EKTOT", outDir.getAbsolutePath() + "/" + filenameRms + ".EKTOT.csv");
		saveDataToCSV(RMS_EPTOT, "RMS_EPTOT", outDir.getAbsolutePath() + "/" + filenameRms + ".EPTOT.csv");
		saveDataToCSV(RMS_DENSITY, "RMS_DENSITY", outDir.getAbsolutePath() + "/" + filenameRms + ".DENSITY.csv");
		saveDataToCSV(RMS_VOLUME, "RMS_VOLUME", outDir.getAbsolutePath() + "/" + filenameRms + ".VOLUME.csv");
		saveDataToCSV(RMS_ESCF, "RMS_ESCF", outDir.getAbsolutePath() + "/" + filenameRms + ".ESCF.csv");
		
		saveDataToCSV(TEMP, "TEMP", outDir.getAbsolutePath() + "/" + filename + ".TEMP.csv");
		saveDataToCSV(TSOLUTE, "TSOLUTE", outDir.getAbsolutePath() + "/" + filename + ".TSOLUTE.csv");
		saveDataToCSV(TSOLVENT, "TSOLVENT", outDir.getAbsolutePath() + "/" + filename + ".TSOLVENT.csv");
		saveDataToCSV(PRES, "PRES", outDir.getAbsolutePath() + "/" + filename + ".PRES.csv");
		saveDataToCSV(EKCMT, "EKCMT", outDir.getAbsolutePath() + "/" + filename + ".EKCMT.csv");
		saveDataToCSV(ETOT, "ETOT", outDir.getAbsolutePath() + "/" + filename + ".ETOT.csv");
		saveDataToCSV(EKTOT, "EKTOT", outDir.getAbsolutePath() + "/" + filename + ".EKTOT.csv");
		saveDataToCSV(EPTOT, "EPTOT", outDir.getAbsolutePath() + "/" + filename + ".EPTOT.csv");
		saveDataToCSV(DENSITY, "DENSITY", outDir.getAbsolutePath() + "/" + filename + ".DENSITY.csv");
		saveDataToCSV(VOLUME, "VOLUME", outDir.getAbsolutePath() + "/" + filename + ".VOLUME.csv");
		saveDataToCSV(ESCF, "ESCF", outDir.getAbsolutePath() + "/" + filename + ".ESCF.csv");
		
		System.out.println("Done!");
	}
	
	/**
	 * Parse Amber MD output files and save quantities (Energy, Temperature, Pressure, etc) to separate CSV files.
	 * Files are sorted by name before parsing.
	 * @param files AMBER MD output files
	 * @param outputDir Path to the output directory
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void process(String[] files, String outputDir) throws NumberFormatException, Exception{
		process(files, outputDir, true);
	}
	
	/**
	 * Parse Amber MD output files and save quantities (Energy, Temperature, Pressure, etc) to separate CSV files.
	 * Files are sorted by name before parsing, and CSV files are saved in the current directory.
	 * @param files AMBER MD output files
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void process(String[] files) throws NumberFormatException, Exception{
		process(files, ".", true);
	}
	
	/**
	 * Parse individual MD output file
	 * @param file
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private void processFile(File file) throws NumberFormatException, Exception
	{
		boolean isAverage = false;
		boolean isRMS = false;
		long averageOver = 0;
		
		String time=null, temp=null,pres=null,etot=null,ektot=null,eptot=null,bond=null,angle=null,dihedral=null,
		nb14=null,eel14=null,nb=null,eel=null,ehbond=null,constraint=null,ekcmt=null,virial=null,volume=null,
		tsolute=null,tsolvent=null,density=null,escf=null;
		
		IBIOMESFileReader br = new IBIOMESFileReader(file);
		String line = null;
		//for each line in the file
		while (( line = br.readLine()) != null)
        {
			//if (DEBUG)
				//System.out.println(line);
			
			if (line.indexOf("A V E R A G E S")>-1)
			{
				isAverage = true;
				averageOver = Long.parseLong(line.replace("A V E R A G E S   O V E R","").replace("S T E P S", "").trim());
				if (DEBUG) System.out.println("Average over: " + averageOver + " steps");
			}
			
			if (line.indexOf("R M S")>-1)
				isRMS = true;
			
			//NSTEP, TIME, TEMP, and PRESSURE
			if (line.matches("(\\s)*NSTEP(\\s)*\\=.*"))
			{
				String[] vals = findNumericValues(line);
				String step = vals[0];
				time = vals[1];
				temp = vals[2];
				pres = vals[3];
				if (DEBUG) System.out.println("Step: " + step + ", Time: " + time + ", Temperature: " + temp + ", Pressure: " + pres);
				
				line = br.readLine();
				
				// ETOT, EKTOT, and EPTOT
				if (line.matches("(\\s)*Etot(\\s)*\\=.*"))
				{
					vals = findNumericValues(line);
					etot = vals[0];
					ektot = vals[1];
					eptot = vals[2];
					if (DEBUG) System.out.println("Etot: " + etot + ", EKtot: " + ektot + ", EPtot: " + eptot);
					line = br.readLine();
				}
				
				// BOND, ANGLE, DIHED
				if (line.matches(".*BOND.*ANGLE.*DIHED.*"))
				{
					vals = findNumericValues(line);
					bond = vals[0];
					angle = vals[1];
					dihedral = vals[2];
					if (DEBUG) System.out.println("Bond: " + bond + ", Angle: " + angle + ", Dihedral: " + dihedral);
					line = br.readLine();
				}
				
				// 1-4 NB
				if (line.matches(".*1\\-4 NB.*"))
				{
					vals = findNumericValues(line);
					nb14 = vals[0];
					eel14 = vals[1];
					nb = vals[2];
					if (DEBUG) System.out.println("NB14: " + nb14 + ", EEL14: " + eel14 + ", NB: " + nb);
					line = br.readLine();
				}
				
				// EELEC
				if (line.matches(".*EELEC.*"))
				{
					vals = findNumericValues(line);
					eel = vals[0];
					ehbond = vals[1];
					constraint = vals[2];
					if (DEBUG) System.out.println("Eel: " + eel + ", EHbond: " + ehbond + ", Constraint: " + constraint);
					line = br.readLine();
					
					//check to see if EAMBER is in the mdout file (present when NTR=1)
					if (line.matches("EAMBER"))
						line = br.readLine();
				}
				
				// EKCMT, VIRIAL, VOLUME
				if (line.matches(".*EKCMT.*"))
				{
					vals = findNumericValues(line);
					ekcmt = vals[0];
					virial = vals[1];
					volume = vals[2];
					if (DEBUG) System.out.println("Ekcmt: " + ekcmt + ", Virial: " + virial + ", Volume: " + volume);
					line = br.readLine();
				}

				// T_SOLUTE, T_SOLVENT
				if (line.matches(".*T_SOLUTE.*"))
				{
					vals = findNumericValues(line);
					tsolute = vals[0];
					tsolvent = vals[1];
					if (DEBUG) System.out.println("Tsolute: " + tsolute + ", Tsolvent: " + tsolvent);
					line = br.readLine();
				}
				
				// Density
				if (line.matches(".*Density.*"))
				{
					vals = findNumericValues(line);
					density = vals[0];
					if (DEBUG) System.out.println("Density: " + density);
					line = br.readLine();
				}
				
				// ETOT, EKTOT, and EPTOT
				if (line.matches("(\\s)*Etot(\\s)*\\=.*"))
				{
					vals = findNumericValues(line);
					etot = vals[0];
					ektot = vals[1];
					eptot = vals[2];
					if (DEBUG) System.out.println("Etot: " + etot + ", EKtot: " + ektot + ", EPtot: " + eptot);
					line = br.readLine();
				}
				
				// ESCF
				if (line.matches("(\\s)*ESCF(\\s)*\\=.*"))
				{
					vals = findNumericValues(line);
					escf = vals[0];
					if (DEBUG) System.out.println("Escf: " + escf);
					line = br.readLine();
				}
				
				if (isAverage){
					if (time != null) AVG_TIME.put(time, time);
					if (temp != null) AVG_TEMP.put(time, temp);
					if (pres != null) AVG_PRES.put(time, pres);
					if (etot != null) AVG_ETOT.put(time, etot);
					if (ektot != null) AVG_EKTOT.put(time, ektot);
					if (eptot != null) AVG_EPTOT.put(time, eptot);
					if (bond != null) AVG_BOND.put(time, bond);
					if (angle != null) AVG_ANGLE.put(time, angle);
					if (dihedral != null) AVG_DIHEDRAL.put(time, dihedral);
					if (nb14 != null) AVG_NB14.put(time, nb14);
					if (eel14 != null) AVG_EEL14.put(time, eel14);
					if (nb != null) AVG_NB.put(time, nb);
					if (eel != null) AVG_EEL.put(time, eel);
					if (ehbond != null) AVG_EHBOND.put(time, ehbond);
					if (constraint != null) AVG_CONSTRAINT.put(time, constraint);
					if (ekcmt != null) AVG_EKCMT.put(time, ekcmt);
					if (virial != null) AVG_VIRIAL.put(time, virial);
					if (volume != null) AVG_VOLUME.put(time, volume);
					if (tsolute != null) AVG_TSOLUTE.put(time, tsolute);
					if (tsolvent != null) AVG_TSOLVENT.put(time, tsolvent);
					if (density != null) AVG_DENSITY.put(time, density);
					if (escf != null) AVG_ESCF.put(time, escf);
					
					isAverage = false;
				}
				else if (isRMS){
					if (time != null) RMS_TIME.put(time, time);
					if (temp != null) RMS_TEMP.put(time, temp);
					if (pres != null) RMS_PRES.put(time, pres);
					if (etot != null) RMS_ETOT.put(time, etot);
					if (ektot != null) RMS_EKTOT.put(time, ektot);
					if (eptot != null) RMS_EPTOT.put(time, eptot);
					if (bond != null) RMS_BOND.put(time, bond);
					if (angle != null) RMS_ANGLE.put(time, angle);
					if (dihedral != null) RMS_DIHEDRAL.put(time, dihedral);
					if (nb14 != null) RMS_NB14.put(time, nb14);
					if (eel14 != null) RMS_EEL14.put(time, eel14);
					if (nb != null) RMS_NB.put(time, nb);
					if (eel != null) RMS_EEL.put(time, eel);
					if (ehbond != null) RMS_EHBOND.put(time, ehbond);
					if (constraint != null) RMS_CONSTRAINT.put(time, constraint);
					if (ekcmt != null) RMS_EKCMT.put(time, ekcmt);
					if (virial != null) RMS_VIRIAL.put(time, virial);
					if (volume != null) RMS_VOLUME.put(time, volume);
					if (tsolute != null) RMS_TSOLUTE.put(time, tsolute);
					if (tsolvent != null) RMS_TSOLVENT.put(time, tsolvent);
					if (density != null) RMS_DENSITY.put(time, density);
					if (escf != null) RMS_ESCF.put(time, escf);
					
					isRMS = false;
				}
				else {
					if (time != null) TIME.put(time, time);
					if (temp != null) TEMP.put(time, temp);
					if (pres != null) PRES.put(time, pres);
					if (etot != null) ETOT.put(time, etot);
					if (ektot != null) EKTOT.put(time, ektot);
					if (eptot != null) EPTOT.put(time, eptot);
					if (bond != null) BOND.put(time, bond);
					if (angle != null) ANGLE.put(time, angle);
					if (dihedral != null) DIHEDRAL.put(time, dihedral);
					if (nb14 != null) NB14.put(time, nb14);
					if (eel14 != null) EEL14.put(time, eel14);
					if (nb != null) NB.put(time, nb);
					if (eel != null) EEL.put(time, eel);
					if (ehbond != null) EHBOND.put(time, ehbond);
					if (constraint != null) CONSTRAINT.put(time, constraint);
					if (ekcmt != null) EKCMT.put(time, ekcmt);
					if (virial != null) VIRIAL.put(time, virial);
					if (volume != null) VOLUME.put(time, volume);
					if (tsolute != null) TSOLUTE.put(time, tsolute);
					if (tsolvent != null) TSOLVENT.put(time, tsolvent);
					if (density != null) DENSITY.put(time, density);
					if (escf != null) ESCF.put(time, escf);
				}
			}
		
        }
		//close file
		br.close();
		
	}
	
	/**
	 * Save given data set to CSV file.
	 * @param series
	 * @param quantity
	 * @param fileName
	 * @throws IOException 
	 */
	private void saveDataToCSV(HashMap<String, String> series, String quantity, String fileName) throws IOException
	{
		if (!series.isEmpty())
		{
			System.out.println("Outputing " + fileName + " ...");
			
			File outFile = new File(fileName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			
			//write CSV header
			bw.append("TIME," + quantity + "\n");
			
			Set<String> keys = series.keySet();
			//order keys (by time)
			ArrayList<String> keysOrdered = new ArrayList<String>();
			for (String key : keys){
				keysOrdered.add(key);
			}
			Collections.sort(keysOrdered);
			
			//write time series
			for (String key : keysOrdered){
				bw.append(key + "," + series.get(key) + "\n");
			}
			bw.close();
		}
	}
	
	/**
	 * 
	 * @param line
	 * @return
	 */
	private String[] findNumericValues(String line)
	{
		ArrayList<String> nums = new ArrayList<String>();
		Pattern p = Pattern.compile("\\=(\\s)*(\\-)?\\d+(\\.\\d+)?");
		
		Matcher m = p.matcher(line);
		while (m.find()){
			nums.add(line.substring(m.start()+1,m.end()).trim());
		}
		
		String[] values = new String[nums.size()];
		for (int v=0;v<nums.size();v++){
			values[v] = nums.get(v);
		}
		return values;
	}

	/**
	 * @param args
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, Exception {
		
		String usage = "Usage: ProcessMdOut -i [md-out-file] -o [output-dir] [-s sort]";
		
		if (args.length < 2){
			System.out.println(usage);
			System.exit(1);
		}
		
		int inputStart = -1;
		int inputEnd = -1;
		int outputStart = -1;
		String input[];
		String output = ".";
		boolean sort = false;
		
		//parse arguments
		for (int i = 0; i < args.length; i++)
		{
			if ("-i".equals(args[i])) {
				inputStart = i+1;
		        i++;
			}
		    else if ("-o".equals(args[i])) {
		    	output = args[i+1];
		    	outputStart = i;
		    	i++;
			}
		    else if ("-s".equals(args[i])) {
		    	sort = true;
		    	i++;
		    }
		}
		
		if (outputStart > -1)
			inputEnd = outputStart;
		else inputEnd = args.length;
		
		input = new String[inputEnd-inputStart];
		for (int i=0; i< input.length; i++){
			input[i] = args[i+inputStart];
		}
		
		//generate CSV files from AMBER MD output files
		ProcessMdOut pr = new ProcessMdOut();
		pr.process(input, output, sort);
	}
	
}
