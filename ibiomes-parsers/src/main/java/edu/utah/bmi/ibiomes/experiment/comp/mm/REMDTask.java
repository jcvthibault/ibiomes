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

package edu.utah.bmi.ibiomes.experiment.comp.mm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.TaskExecution;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;

/**
 * Replica-exchange REMD task.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="task")
public class REMDTask extends MDTask {

	private REMDParameterSet remdParameterSet;
	
	@SuppressWarnings(value = { "unused" })
	private REMDTask(){
		super();
		this.methodType = ParameterSet.METHOD_REMD;
	}	
	
	/**
	 * Create new REMD task
	 * @param name Task name
	 * @param description Task description
	 * @param mdParameterSet MD parameter set
	 * @param remdParameterSet REMD parameter set
	 */
	public REMDTask(String name, String description, 
			MDParameterSet mdParameterSet, REMDParameterSet remdParameterSet){
		super(name,description,mdParameterSet);
		this.methodType = ParameterSet.METHOD_REMD;
		this.remdParameterSet = remdParameterSet;
	}
	
	/**
	 * Create new REMD task
	 * @param mdParameterSet MD parameter set
	 * @param remdParameterSet REMD parameter set
	 */
	public REMDTask(MDParameterSet mdParameterSet, REMDParameterSet remdParameterSet) {
		super(mdParameterSet);
		this.methodType = ParameterSet.METHOD_REMD;
		this.remdParameterSet = remdParameterSet;
	}

	/**
	 * Merge REMD tasks representing a single replica each
	 * @param remdTasks REMD tasks to merge
	 * @return Merged REMD task
	 */
	public static REMDTask merge(List<REMDTask> remdTasks) {
		int numberOfReplica = remdTasks.size();
		REMDTask firstRemdTask = remdTasks.get(0);
		REMDTask mergedRemdTask = new REMDTask(
				firstRemdTask.getName(), 
				firstRemdTask.getDescription(), 
				firstRemdTask.getMDParameterSet(), 
				firstRemdTask.getREMDParameterSet());
		
		//copy MD task info
		mergedRemdTask.setBoundaryConditions(firstRemdTask.getBoundaryConditions());
		mergedRemdTask.setCalculationTypes(firstRemdTask.getCalculationTypes());
		mergedRemdTask.setSimulatedConditionSet(firstRemdTask.getSimulatedConditionSet());
		mergedRemdTask.setSoftware(firstRemdTask.getSoftware());
		mergedRemdTask.setPlatform(firstRemdTask.getComputingEnvironment());
		mergedRemdTask.setTaskExecution(firstRemdTask.getTaskExecution());
		
		//adjust number of resources to represent 
		// multiple replicas run in parallel
		TaskExecution exec = mergedRemdTask.getTaskExecution();
		exec.setNumberOfCPUs(numberOfReplica * exec.getNumberOfCPUs());
		exec.setNumberOfGPUs(numberOfReplica * exec.getNumberOfGPUs());
		mergedRemdTask.setTaskExecution(exec);
		mergedRemdTask.getREMDParameterSet().setNumberOfReplica(numberOfReplica);
		
		//update number of exchanges and files
		ArrayList<String> inputFiles = new ArrayList<String>();
		ArrayList<String> outputFiles = new ArrayList<String>();
		int numberOfExchanges = 0;
		for (REMDTask task : remdTasks){
			numberOfExchanges += task.getREMDParameterSet().getNumberOfExchanges();
			if (task.getInputFiles()!=null)
				inputFiles.addAll(task.getInputFiles());
			if (task.getOutputFiles()!=null)
				outputFiles.addAll(task.getOutputFiles());
		}
		numberOfExchanges = numberOfExchanges / 2;
		mergedRemdTask.getREMDParameterSet().setNumberOfExchanges(numberOfExchanges);
		//set files
		mergedRemdTask.setInputFiles(inputFiles);
		mergedRemdTask.setOutputFiles(outputFiles);
		
		//update number of replicas
		mergedRemdTask.getREMDParameterSet().setNumberOfReplica(numberOfReplica);
		
		return mergedRemdTask;
	}
	
	/**
	 * Get REMD-specific method and parameters
	 * @return REMD-specific method and parameters
	 */
	@XmlElement(name="parameterSet")
	public REMDParameterSet getREMDParameterSet() {
		return remdParameterSet;
	}
	
	/**
	 * Set REMD-specific method and parameters
	 * @param remdParameterSet REMD-specific method and parameters
	 */
	public void setREMDParameterSet(REMDParameterSet remdParameterSet) {
		this.remdParameterSet = remdParameterSet;
	}

	@Override
	public List<ParameterSet> getParameterSets() {
		List<ParameterSet> methods = new ArrayList<ParameterSet>();
		methods.add(remdParameterSet);
		methods.add(mdParamSet);
		return methods;
	}

}
