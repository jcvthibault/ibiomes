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

package edu.utah.bmi.ibiomes.parse.chem.namd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.conf.IBIOMESExecutionTimeSummary;
import edu.utah.bmi.ibiomes.experiment.ComputingEnvironment;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.TaskExecution;
import edu.utah.bmi.ibiomes.experiment.comp.Constraint;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.SimulatedConditionSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Barostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.LangevinThermostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.PMEModel;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractParameterFile;
import edu.utah.bmi.ibiomes.quantity.Frequency;
import edu.utah.bmi.ibiomes.quantity.Pressure;
import edu.utah.bmi.ibiomes.quantity.Temperature;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * NAMD output/log file
 * 
 * @author Julien Thibault, University of Utah
 *
 */
public class NAMDStandardOutputFile extends AbstractParameterFile
{
	private static final long serialVersionUID = -5186336458434930279L;
	private List<String> parameters;
	private String logInfoPrefix = "Info: ";
	private String logExecTimePrefix = "WallClock: ";
	private String executionInfoLine = null;

	private MDParameterSet paramSet = null;
	private SimulatedConditionSet simulatedConditionSet = null;
	private Software sw = null;
	private ComputingEnvironment env = null;
	private TaskExecution execInfo = null;
	private String boundaryConditions = ParameterSet.BC_NON_PERIODIC;
    int numberOfSteps = 0;
    double dt = 0.0;

	private String prefixSoftwareInfo = "NAMD";
	private String prefixNCPUs = "RUNNING ON ";
	private String prefixTimeStepLength = "TIMESTEP ";
	private String prefixTimeStepCount = "NUMBER OF STEPS ";
	private String prefixPeriodic = "PERIODIC CELL ";

	private String keyphraseLangevin = "LANGEVIN DYNAMICS ACTIVE";
	private String prefixLangevinTemperature = "LANGEVIN TEMPERATURE ";
	private String prefixLangevinDamping = "LANGEVIN DAMPING COEFFICIENT IS ";

	private String keyphraseBerendsenBarostat = "BERENDSEN PRESSURE COUPLING ACTIVE";
	private String prefixRefPressure = "TARGET PRESSURE IS ";

	private String keyphrasePME = "PARTICLE MESH EWALD (PME) ACTIVE";
	private String prefixPmeInterpolation = "PME INTERPOLATION ORDER ";
	private String prefixPmeEwaldCoeff = "PME EWALD COEFFICIENT ";

	private String prefixConstraintHydrogen = "RIGID BONDS TO HYDROGEN : ";
	private String prefixConstraintWater = "RIGID WATER USING SETTLE ALGORITHM";
	
	
	/**
	 * Default constructor.
	 * @param pathname Log file path.
	 * @throws Exception
	 */
	public NAMDStandardOutputFile(String pathname) throws Exception
	{
		super(pathname, LocalFile.FORMAT_NAMD_LOG);
		parameters = new ArrayList<String>();

		boolean timingsOn =  (IBIOMESConfiguration.getInstance().hasCollectTimingsOn());
		long startTime = 0;
		try{
			if (timingsOn){
				startTime = System.currentTimeMillis();
			}
			
			parseFile();
			
			if (timingsOn){
				long endTime = System.currentTimeMillis();
				IBIOMESExecutionTimeSummary.getInstance().addExecutionTimingRecord(
						"NAMD std output", pathname, true, endTime - startTime);
			}
			
		} catch (Exception e){
			if (timingsOn){
				long endTime = System.currentTimeMillis();
				IBIOMESExecutionTimeSummary.getInstance().addExecutionTimingRecord(
						"NAMD std output", pathname, false, endTime - startTime);
			}
			throw e;
		}
	}
	
	/**
	 * Parse NAMD log file
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		IBIOMESFileReader br = null;
		try{
			br =  new IBIOMESFileReader(this);
		    String line = null;
		    
	        while (( line = br.readLine()) != null)
	        {
	        	line = line.trim();
	        	if (line.length()>0 && !line.startsWith("#"))
	        	{
	        		if (line.startsWith(logInfoPrefix))
	        		{
	        			line = line.substring(logInfoPrefix.length());
		    			parameters.add(line.trim());
	        		}
	        		else if (line.startsWith(logExecTimePrefix))
	        			executionInfoLine = line;
	        	}
	        }
	        br.close();
	        
	        paramSet = new MDParameterSet();
	        simulatedConditionSet = null;
	        sw = new Software(Software.NAMD);
			env = new ComputingEnvironment();
	        execInfo = new TaskExecution();
	        boundaryConditions = ParameterSet.BC_NON_PERIODIC;

	        List<Constraint> constraints = new ArrayList<Constraint>();
			
	        for (int l=0;l<parameters.size();l++){
	        	String paramLine = parameters.get(l);
	        	
	        	//NAMD version
	        	if (paramLine.startsWith(prefixSoftwareInfo)){
	        		parseNAMDVersion(paramLine);
	        	}
	        	//CPU info
	        	else if (paramLine.toUpperCase().startsWith(prefixNCPUs)){
	        		execInfo.setNumberOfCPUs(Integer.parseInt(paramLine.split("\\s+")[2]));
	        	}
	        	//number of time steps
	        	else if (paramLine.toUpperCase().startsWith(prefixTimeStepCount)){
	        		parseTimeSteps(paramLine);
	        	}
	        	//time step length
	        	else if (paramLine.toUpperCase().startsWith(prefixTimeStepLength)){
	        		dt = Double.parseDouble(paramLine.substring(prefixTimeStepLength.length()).trim());
	        		TimeLength timeStepLength = new TimeLength(dt, TimeLength.Femtosecond);
	        		paramSet.setTimeStepLength(timeStepLength);
	        	}
	        	//boundary conditions
	        	else if (paramLine.toUpperCase().startsWith(prefixPeriodic)){
	        		boundaryConditions = ParameterSet.BC_PERIODIC;
	        	}
	        	//langevin dynamics
	        	else if (paramLine.toUpperCase().equals(keyphraseLangevin)){
	        		paramSet.setName(ParameterSet.METHOD_LANGEVIN_DYNAMICS);
	        		paramSet.setThermostat(new LangevinThermostat());
	        	}
	        	//langevin damping coeff
	        	else if (paramLine.toUpperCase().startsWith(prefixLangevinDamping)){
	        		String collFreq = paramLine.substring(
	        				prefixLangevinDamping.length(),
	        				paramLine.indexOf(" ", prefixLangevinDamping.length()+1)
	        			);
	        		LangevinThermostat thermostat = new LangevinThermostat();
	        		thermostat.setCollisionFrequency(
	        				new Frequency(Double.parseDouble(collFreq),"ps^-1"));
	        		paramSet.setThermostat(thermostat);
	        	}
	        	//langevin temperature
	        	else if (paramLine.toUpperCase().startsWith(prefixLangevinTemperature)){
	        		String temp = paramLine.substring(prefixLangevinTemperature.length()).trim();
	        		simulatedConditionSet = new SimulatedConditionSet();
	        		simulatedConditionSet.setReferenceTemperature(
	        				new Temperature(Double.parseDouble(temp), Temperature.Kelvin));
	        	}
	        	//Berendsen
	        	else if (paramLine.toUpperCase().equals(keyphraseBerendsenBarostat)){
	        		Barostat barostat = new Barostat(Barostat.BAROSTAT_BERENDSEN);
	        		for (int i=l+1;i<l+3;i++){
			        	paramLine = parameters.get(i);
			        	if (paramLine.toUpperCase().startsWith(prefixRefPressure)){
			        		String pressure = paramLine.substring(prefixRefPressure.length(),
			        				paramLine.indexOf(" ", prefixRefPressure.length()+1)).trim();
			        		simulatedConditionSet = new SimulatedConditionSet();
			        		simulatedConditionSet.setReferencePressure(
			        				new Pressure(Double.parseDouble(pressure), Pressure.Bar));
			        	}
			        }
	        		paramSet.setBarostat(barostat);
	        	}
	        	//PME
	        	else if (paramLine.toUpperCase().equals(keyphrasePME)){
	        		PMEModel pmeModel = new PMEModel();
	        		for (int i=l+1;i<l+5;i++){
			        	paramLine = parameters.get(i);
			        	if (paramLine.toUpperCase().startsWith(prefixPmeInterpolation)){
			        		pmeModel.setInterpolationOrder(
			        				Integer.parseInt(paramLine.substring(prefixPmeInterpolation.length()).trim()
			        			));
			        	}
			        	else if (paramLine.toUpperCase().startsWith(prefixPmeEwaldCoeff)){
			        		pmeModel.setEwaldCoefficient(
			        				Double.parseDouble(paramLine.substring(prefixPmeEwaldCoeff.length()).trim()
			        			));
			        	}
			        }
	        		paramSet.setElectrostatics(pmeModel);
	        	}
	        	//constraints on bonds to hydrogen
	        	else if (paramLine.toUpperCase().equals(prefixConstraintHydrogen)){
	        		Constraint constraintOnH = new Constraint(Constraint.SHAKE);
	        		constraintOnH.setTarget(Constraint.TARGET_BONDS_TO_HYDROGEN + ": " 
	        				+ paramLine.substring(prefixConstraintHydrogen.length()).toLowerCase());
	        		constraints.add(constraintOnH);
	        	}
	        	//constraints on water
	        	else if (paramLine.toUpperCase().equals(prefixConstraintWater)){
	        		Constraint constraintOnWater = new Constraint(Constraint.SETTLE);
	        		constraintOnWater.setTarget("Water");
	        		constraints.add(constraintOnWater);
	        	}
	        }
	        
        	parseExecutionInfo();
	        
	        paramSet.setSimulatedTime(new TimeLength(dt * ((double)numberOfSteps) * 0.001, TimeLength.Picosecond));
    		paramSet.setConstraints(constraints);
	        
	        //create task and associate parameters and execution info
	        ExperimentTask task = new MDTask(paramSet);
	        task.setPlatform(env);
	        task.setTaskExecution(execInfo);
	        task.setSoftware(sw);
	        task.setBoundaryConditions(boundaryConditions);
	        task.setSimulatedConditionSet(simulatedConditionSet);
	        task.setOutputFiles(this.getAbsolutePath());
	        this.tasks = new ArrayList<ExperimentTask>();
	        this.tasks.add(task);
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a NAMD log file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			if (br!=null)
				try {
					br.close();
				} catch (IOException e1) {
				}
			throw e;
		}
	}
	
	
	/**
	 * Parse number of steps
	 * @param line Current line
	 */
	private void parseTimeSteps(String line) {
		numberOfSteps = Integer.parseInt(line.substring(prefixTimeStepCount.length()).trim());
		paramSet.setNumberOfSteps(numberOfSteps);
	}

	/**
	 * Parse execution information
	 */
	private void parseExecutionInfo(){
		if (executionInfoLine!=null)
		{
			execInfo.setTerminationStatus(TaskExecution.TERMINATION_STATUS_NORMAL);
			String wallClock = executionInfoLine.substring(
					logExecTimePrefix.length(), executionInfoLine.indexOf("  "));
	    	execInfo.setExecutionTime(new TimeLength(
	    			Math.round(Double.parseDouble(wallClock)/60.0),
	    			TimeLength.Minute
	    		));
		}
		else execInfo.setTerminationStatus(TaskExecution.TERMINATION_STATUS_ERROR);
	}
	
	/**
	 * Parse NAMD program version
	 * @param line Current line
	 */
	private void parseNAMDVersion(String line){
		//e.g. "NAMD 2.6 for Linux-amd64-MPI"
		String[] values = line.split("\\s+");
		sw.setVersion(values[1]);
		sw.setExecutableName(values[0]+"-"+values[1]+"-"+values[3]);
		String[] envInfo = values[3].split("\\-");
		env.setOperatingSystem(envInfo[0]);
		env.setCpuArchitecture(envInfo[1]);
	}
		
}
