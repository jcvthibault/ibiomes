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

package edu.utah.bmi.ibiomes.parse.chem.amber;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.conf.IBIOMESExecutionTimeSummary;
import edu.utah.bmi.ibiomes.experiment.ComputingEnvironment;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.TaskExecution;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractParameterFile;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * AMBER MD output file
 * @author Julien Thibault
 *
 */
public class AmberMdOutputFile extends AbstractParameterFile {

	public static final String AMBER_SANDER 	= "SANDER";
	public static final String AMBER_PMEMD 		= "PMEMD";
	public static final String FLAG_MPI 		= "(MPI)";
	public static final String FLAG_GPU 		= "(CUDA)";
	public static final String FLAG_MPI_GPU 	= "(MPI/CUDA)";
	
	private static final long serialVersionUID = -5577978145409702898L;
	
	private static final String keywordStartAmberVersion = "amber ";
	private static final String keywordStartAmberPMEMD = "| PMEMD implementation of SANDER";
	private static final String regexAmberMPI = "\\|(\\s+)Running AMBER/MPI version on(\\s+)\\d+ nodes";
	
	private static final String keywordQmAtoms = "LOADING THE QUANTUM ATOMS AS GROUPS";
    
	private static final String keywordStartLangevinRandomSeed = "Note: ig = -1. Setting random seed to";
	
	private static final String regexGPUInfoStart = "\\|(\\-)+ GPU DEVICE INFO (\\-)+";
	private static final String regexGPUInfoEnd = "\\|(\\-)+";
	private static final String regexGPUDeviceName =  "\\|(\\s+)CUDA Device Name:.*";
	
	private static final String keywordInputDumpStart = "&cntrl";
	private static final String keywordParameterSectionStart = "2.  CONTROL  DATA  FOR  THE  RUN";
	private static final String keywordSectionEnd = "----------------------------------------------";
	
	private static final String keywordStartTime  = "\\|(\\s)+Run on(\\s).*(\\s)at(\\s).*";
	private static final String keywordTiming     = "\\|(\\s)+job began  at.*";
	private static final String keywordTiming2    = "\\|(\\s)+(master )?setup wall time:.*";

	private HashMap<String, String> parameters;
	private List<String> parameterSectionLabels;
	private int nGpus = 0;
	private int nCPUs = 1;
	private long startTimestamp;
	private long endTimestamp;
	private TimeLength executionTime = null;
	private String executableFlags = null;
	private Software sw = new Software(Software.AMBER);
	private ComputingEnvironment env = new ComputingEnvironment();
	private String qmAtomMask  =null;
	
	/**
	 * New AMBER MD output file
	 * @param localPath
	 * @throws Exception
	 */
	public AmberMdOutputFile(String localPath) throws Exception {
		super(localPath, FORMAT_AMBER_MDOUT);
		parameters = new HashMap<String, String>();
		parameterSectionLabels = new ArrayList<String>();
		
		boolean timingsOn =  (IBIOMESConfiguration.getInstance().hasCollectTimingsOn());
		long startTime = 0;
		try{
			if (timingsOn){
				startTime = System.currentTimeMillis();
			}
			boolean success = parseFile();
			
			if (timingsOn){
				long endTime = System.currentTimeMillis();
				IBIOMESExecutionTimeSummary.getInstance().addExecutionTimingRecord(
						"AMBER MD output", localPath, success, endTime - startTime);
			}
			
			
		} catch (Exception e){
			if (timingsOn){
				long endTime = System.currentTimeMillis();
				IBIOMESExecutionTimeSummary.getInstance().addExecutionTimingRecord(
						"AMBER MD output", localPath, false, endTime - startTime);
			}
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as an AMBER MD output file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
		}
	}

	/**
	 * Parse AMBER input file
	 * @param filePath
	 * @throws Exception
	 */
	private boolean parseFile() throws Exception 
	{
		boolean success = true;
		
		IBIOMESFileReader br = null;
		try{
			br = new IBIOMESFileReader(this);
		    String line = null;
		    String previousLine = null;
		    boolean headerFound = false;

		    boolean parsedPMEMD = false, parsedInputCtrl = false, parsedGPU = false, parsedMPI = false,
		    		parsedSeed = false, parsedTiming1 = false, parsedTiming2 = false, parsedInput = false,
		    		parsedQmAtoms = false, parsedStarTime = false;
		    
		    // Look for AMBER version in the first 5 lines
		    int l=0;
		    while (!headerFound && (line = br.readLine()) != null && l<5)
		    {
	        	line = line.trim();
	        	if (line.toLowerCase().startsWith(keywordStartAmberVersion)){
	        		headerFound = true;
	        		parseAmberVersion(line);
	        	}
	        	l++;
	        }
		    //continue only if AMBER header was found
		    if (!headerFound){
		    	br.close();
		    	logger.warn("AMBER MD output file header not found in " + this.getAbsolutePath());
		    	success = false;
		    }
		    else{
		    	//parse the whole file or until timing info is retrieved
		    	while (!parsedTiming1 && !parsedTiming2 
		    			&& (line = br.readLine()) != null)
		    	{
		    		line = line.trim();
		    		
		    		//PMEMD program info
		    		if (!parsedPMEMD && line.startsWith(keywordStartAmberPMEMD)){
		    			parsePmemdInfo(line);
		    			parsedPMEMD = true;
		    		}
		    		//task description
		    		else if (!parsedInputCtrl && line.equals(keywordInputDumpStart)){
		        		parseInputCtrl(previousLine, br);
		        		parsedInputCtrl = true;
		        	}
		    		//GPU info
		    		else if (!parsedGPU && line.matches(regexGPUInfoStart)){
		        		parseGPUInfo(line,br);
		        		parsedGPU = true;
		        	}
		    		//input parameters
		    		else if (!parsedInput && line.equals(keywordParameterSectionStart)){
		        		br.readLine();
					    parseInputParameters(br);
					    parsedInput = true;
		        	}
		    		//langevin random seed
		    		else if (!parsedSeed && line.startsWith(keywordStartLangevinRandomSeed)){
		        		parseLangevinSeed(line);
		        		parsedSeed = true;
		        	}
		    		//parse MPI info
		    		else if (!parsedMPI && line.matches(regexAmberMPI)){
		        		parseMpiInfo(line);
		        		parsedMPI = true;
		        	}
		    		//QM atoms for QM/MM
		    		else if (!parsedQmAtoms && line.equals(keywordQmAtoms)){
		    			line = br.readLine();
		    			parseQmAtoms(line);
		    			parsedQmAtoms = true;
		    		}
		    		//timings
		    		else if (!parsedTiming1 && line.matches(keywordStartTime)){
		        		parseStartTime(line);
		        		parsedStarTime = true;
		        	}
		    		else if (!parsedTiming1 && line.toLowerCase().matches(keywordTiming)){
		        		parseTimingInFormat1(line, br);
		        		parsedTiming1 = true;
		        	}
		        	else if (!parsedTiming2 && line.toLowerCase().matches(keywordTiming2)){
		        		parseTimingInFormat2(line, br);
		        		parsedTiming2 = true;
		        	}
		        	previousLine = line;
		    	}

		        br.close();
				   
			    //create tasks based on parsed info
		        this.tasks = AmberKeywordMapper.mapToTasks(parameters, parameterSectionLabels);
		        if (executableFlags!=null)
		        	sw.setExecutableName(sw.getExecutableName() + " " + executableFlags);
		        
		        boolean firstTask = true;
				for (ExperimentTask task : tasks){
					if (description!=null && description.length()>0)
						task.setDescription(description);

					TaskExecution execInfo = task.getTaskExecution();
					if (execInfo==null)
						execInfo = new TaskExecution();
					execInfo.setNumberOfGPUs(nGpus);
					execInfo.setNumberOfCPUs(nCPUs);
					//termination status
					if (!parsedTiming1 && !parsedTiming2)
						execInfo.setTerminationStatus(TaskExecution.TERMINATION_STATUS_ERROR);
					else 
						execInfo.setTerminationStatus(TaskExecution.TERMINATION_STATUS_NORMAL);
					//assign execution time to first task only... not ideal
					if (executionTime!=null && firstTask){
						execInfo.setExecutionTime(executionTime);
						execInfo.setStartTimestamp(startTimestamp);
						execInfo.setEndTimestamp(endTimestamp);
					}
					
					//build
					task.setTaskExecution(execInfo);
					task.setSoftware(sw);
					task.setPlatform(env);
					ArrayList<String> relatedFiles = new ArrayList<String>();
					relatedFiles.add(this.getCanonicalPath());
					task.setOutputFiles(relatedFiles);
					firstTask = false;
				}
		    }
		    return success;
		}
		catch (Exception e){
			try {
				if (br!=null)
					br.close();
			} catch (Exception e1) {
			}
			throw e;
		}
	}
	
	/**
	 * Parse atom mask for QM region definition
	 * @param line Current line
	 */
	private void parseQmAtoms(String line) {
		//Mask :1-2; matches    12 atoms
		line = line.trim();
		qmAtomMask = (line.substring(4, line.indexOf("matches")-1)).trim();
	}

	/**
	 * Parse AMBER program version
	 * @param line Current line
	 */
	private void parseAmberVersion(String line){
		String[] splitLine = line.split("\\s+");
		if (splitLine.length>1){
			sw.setVersion(splitLine[1]);
    		sw.setExecutableName(AMBER_SANDER + " " + splitLine[1]);
		}
	}
	
	/**
	 * Parse PMEMD info
	 * @param line Current line
	 */
	private void parsePmemdInfo(String line)
	{
		String[] splitLine = line.split("\\s+");
		String version = splitLine[splitLine.length-1];
		sw.setExecutableName(AMBER_PMEMD + " " + version);
	}
	
	/**
	 * Parse task description 
	 * @param previousLine Previous line
	 * @throws IOException
	 */
	private void parseInputCtrl(String previousLine, IBIOMESFileReader br) throws IOException
	{	
		this.description = previousLine;
		String line = null;
		while (( line = br.readLine()) != null)
        {
        	line = line.trim();
        	if (line.length()==0){
        		break;
        	}
        	else if (line.toUpperCase().equals("END")){
        		break;
        	}
        	else if (line.equals("/")){
        		line = br.readLine().trim();
        		if (!line.startsWith("&")){
        			break;
        		}
        	}
        	else if (!line.startsWith("!") && line.indexOf('=')>0)
        	{
        		//remove comment at end of line
        		int commentIdx = line.indexOf("!");
        		if (commentIdx>0)
        			line = line.substring(0, commentIdx).trim();
        			
        		//parse values
    			String[] pairs = line.split("\\,");
    			for (int p=0;p<pairs.length;p++)
    			{
    				String pair = pairs[p];
    				String[] splitPair = pair.split("\\=");
    				if (splitPair.length==2)
    				{
			    		String attribute = splitPair[0].trim().toLowerCase();
			    		String value = splitPair[1].trim();
		        		//remove quotes for string if any
		        		if (value.startsWith("'") && value.endsWith("'"))
		        			value = value.substring(1, value.length()-1);  
			    		parameters.put(attribute, value);
    				}
    			}
    		}
        	previousLine = line;
        }
	}
	
	/**
	 * Parse GPU info
	 * @param line Current line
	 * @param br Reader
	 * @throws IOException
	 */
	private void parseGPUInfo(String line, IBIOMESFileReader br) throws IOException
	{	
		sw.setExecutableName(AMBER_PMEMD + " " + sw.getVersion());
    	executableFlags = FLAG_GPU;
		boolean endFound = false;
		while (!endFound && (line = br.readLine()) != null)
	    {
			line = line.trim();
        	if (line.matches(regexGPUInfoEnd)){
        		endFound = true;
        	}
        	else if (line.matches(regexGPUDeviceName)){
        		nGpus++;
        		String deviceName = line.split(":")[1].trim();
        		env.setGpuArchitecture(deviceName);
        	}
	    }
	}
	
	/**
	 * Parse MPI info
	 * @param line Current line
	 */
	private void parseMpiInfo(String line){
		String[] values = line.split("\\s+");
		nCPUs = Integer.parseInt(values[5]);
		if (executableFlags == null)
			executableFlags = FLAG_MPI;
		else if (executableFlags.equals(FLAG_GPU))
			executableFlags = FLAG_MPI_GPU;
	}
	
	/**
	 * Parse input parameters
	 * @param br Reader
	 * @throws IOException
	 */
	private void parseInputParameters(IBIOMESFileReader br) throws IOException
	{
		boolean endFound = false;
		String line = null;
        while (!endFound && ( line = br.readLine()) != null)
        {
        	line = line.trim();
        	if (line.startsWith(keywordSectionEnd)){
        		endFound = true;
        	}
        	else{
        		if ( line.endsWith(":")){
        			parameterSectionLabels.add(line.substring(0, line.length()-1));
        		}
	        	//if the line is not commented and has at least one '='
        		else if ( !line.startsWith("|") && line.indexOf('=')>0)
	        	{
	    			String[] pairs = line.split("\\,");
	    			for (int p=0;p<pairs.length;p++)
	    			{
	    				String pair = pairs[p];
	    				String[] splitPair = pair.split("\\=");
	    				if (splitPair.length==2)
	    				{
				    		String attribute = splitPair[0].trim().toLowerCase();
				    		String value = splitPair[1].trim();
				    		parameters.put(attribute, value);
				    		//System.out.println(attribute + " = " + value);
	    				}
	    				else if (splitPair.length>2){
	    					//split based on spaces then '='
	    					String[] splitWithSpace = pair.replaceAll("\\=", "").split("\\s+");
	    					for (int s=0; s<splitWithSpace.length;s=s+2){
	    						if (s+1<splitWithSpace.length){
	    							parameters.put(splitWithSpace[s], splitWithSpace[s+1]);
	    						}
	    					}
	    				}
	    			}
	    		}
        	}
        }
	}
	
	/**
	 * Parse Langevin random seed
	 * @param line Current Line
	 */
	private void parseLangevinSeed(String line){
		String value = line.substring(keywordStartLangevinRandomSeed.length()).trim();
		value = value.substring(0,value.indexOf(' '));
        parameters.put("ig", value);
	}
	
	/**
	 * Parse timings info
	 * @param line Current line
	 * @param br Reader
	 * @throws IOException
	 * @throws ParseException
	 */
	private void parseStartTime(String line) throws IOException, ParseException
	{
		// | Run on 12/13/2011 at 21:42:45
		String[] jobStartTimeLine = line.split("\\s+");
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date jobStartTime = formatter.parse(jobStartTimeLine[3] + " " + jobStartTimeLine[5]);
		startTimestamp = (long)((double)jobStartTime.getTime()/1000.0);
	}
	
	/**
	 * Parse timings info
	 * @param line Current line
	 * @param br Reader
	 * @throws IOException
	 * @throws ParseException
	 */
	private void parseTimingInFormat1(String line, IBIOMESFileReader br) throws IOException, ParseException
	{
		// | Job began  at 13:46:05.921  on 04/19/2005
		// | Setup done at 13:46:06.175  on 04/19/2005
		// | Run   done at 15:01:18.150  on 04/19/2005
		
		String[] jobStartTimeLine = line.split("\\s+");
		String[] setupEndTimeLine = br.readLine().split("\\s+");
		String[] runEndTimeLine = br.readLine().split("\\s+");
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date jobStartTime = formatter.parse(jobStartTimeLine[6].substring(0,10) + " " + jobStartTimeLine[4].substring(0,8));
		Date setupEndTime = formatter.parse(setupEndTimeLine[6].substring(0,10) + " " + setupEndTimeLine[4].substring(0,8));
		Date runEndTime = formatter.parse(runEndTimeLine[6].substring(0,10) + " " + runEndTimeLine[4].substring(0,8));
		
		double timeDifference = ((double)(runEndTime.getTime()-jobStartTime.getTime()))/(1000.0*60.0);
		timeDifference = Math.round(timeDifference);
		executionTime = new TimeLength(timeDifference, TimeLength.Minute);
		startTimestamp = (long)((double)jobStartTime.getTime()/1000.0);
		endTimestamp = (long)((double)runEndTime.getTime()/1000.0);
	}
	
	/**
	 * Parse timings info
	 * @param line Current line
	 * @param br Reader
	 * @throws IOException
	 */
	private void parseTimingInFormat2(String line, IBIOMESFileReader br) throws IOException
	{
		//|  Setup wall time:           0    seconds
		//|  NonSetup wall time:       92    seconds
		//|  Total wall time:          92    seconds     0.03 hours
		
		//|  Master Setup wall time:           1    seconds
		//|  Master NonSetup wall time:       32    seconds
		//|  Master Total wall time:          33    seconds     0.01 hours
		
		String[] setupWallTimeLine 		= line.split(":")[1].trim().split("\\s+");
		String[] nonSetupWallTimeLine 	= br.readLine().split(":")[1].trim().split("\\s+");
		String[] totalWallTimeLine 		= br.readLine().split(":")[1].trim().split("\\s+");
		long totalWallTimeInSeconds = Long.parseLong(totalWallTimeLine[0]);
		double totalWallTimeInMins = Math.round(((double)totalWallTimeInSeconds) / 60.0);
		
		if (startTimestamp>0)
			endTimestamp = startTimestamp + totalWallTimeInSeconds;
		executionTime = new TimeLength(totalWallTimeInMins, TimeLength.Minute);
	}
}
