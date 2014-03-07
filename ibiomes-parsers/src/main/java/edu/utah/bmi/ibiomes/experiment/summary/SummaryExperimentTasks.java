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

package edu.utah.bmi.ibiomes.experiment.summary;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.min.MinimizationParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Barostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.ElectrostaticsModel;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Thermostat;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * Summarizes a list of computational tasks
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="tasksSummary")
public class SummaryExperimentTasks implements MetadataMappable {
	private TimeLength executionTime = null;
	private TimeLength simulatedTime = null;
	private int timeStepCount = 0;
	private List<String> methods = null;
	private List<String> taskDescriptions = null;
	private List<String> qmMethods = null;
	private List<String> minimizationMethods = null;
	private List<String> forceFields = null;
	private List<String> basisSets = null;
	private List<String> calculations = null;
	private List<String> thermostats = null;
	private List<String> barostats = null;
	private List<String> electrostaticsModels = null;
	private int numberOfTasks = 0;
	private long startTimestamp;
	private long endTimestamp;
	private int maxNumberOfCPUs = 0;
	private int maxNumberOfGPUs = 0;
	private SummarySoftwarePackageUses softwarePackageUsesSummary = null;
	
	@SuppressWarnings(value = { "unused" })
	private SummaryExperimentTasks(){	
	}
	
	/**
	 * Create new summary of methods/parameters for the list of provided tasks
	 * @param tasks List of computational tasks
	 */
	public SummaryExperimentTasks(List<ExperimentTask> tasks)
	{
		this.methods = new ArrayList<String>();
		this.taskDescriptions = new ArrayList<String>();
		this.qmMethods = new ArrayList<String>();
		this.minimizationMethods = new ArrayList<String>();
		this.forceFields = new ArrayList<String>();
		this.basisSets = new ArrayList<String>();
		this.calculations = new ArrayList<String>();
		this.barostats = new ArrayList<String>();
		this.thermostats = new ArrayList<String>();
		this.electrostaticsModels = new ArrayList<String>();

		if (tasks!=null){
			//software use summary
			this.softwarePackageUsesSummary = new SummarySoftwarePackageUses(tasks);
			
			this.setNumberOfTasks(tasks.size());
			for (ExperimentTask task : tasks)
			{
				//general methods
				String method = task.getMethodType();
				if (method!=null && !this.methods.contains(method))
					this.methods.add(method);
				
				//descriptions
				String desc = task.getDescription();
				if (desc!=null && !this.taskDescriptions.contains(desc))
					this.taskDescriptions.add(desc);
				
				//execution info
				if (task.getTaskExecution()!=null){
					TimeLength taskExecTime = task.getTaskExecution().getExecutionTime();
					if (taskExecTime!=null){
						if (this.executionTime==null)
							this.executionTime = new TimeLength(taskExecTime.getValue(), taskExecTime.getUnit());
						else this.executionTime.setValue(
							this.executionTime.getValue() + taskExecTime.getValue());
					}
					if (this.startTimestamp==0)
						this.startTimestamp = task.getTaskExecution().getStartTimestamp();
					else if (task.getTaskExecution().getStartTimestamp()>0 
							&& task.getTaskExecution().getStartTimestamp() < this.startTimestamp)
						this.startTimestamp = task.getTaskExecution().getStartTimestamp();
					
					if (task.getTaskExecution().getEndTimestamp() > this.endTimestamp)
						this.endTimestamp = task.getTaskExecution().getEndTimestamp();
					
					//get max number of CPUs and GPUs used at once
					if (task.getTaskExecution().getNumberOfCPUs()>this.maxNumberOfCPUs)
						this.maxNumberOfCPUs = task.getTaskExecution().getNumberOfCPUs();
					if (task.getTaskExecution().getNumberOfGPUs()>this.maxNumberOfGPUs)
						this.maxNumberOfGPUs = task.getTaskExecution().getNumberOfGPUs();
					
				}
				//calculations
				List<String> calculationsTask = task.getCalculationTypes();
				if (calculationsTask!=null){
					for (String calculation : calculationsTask){
						if (!this.calculations.contains(calculation))
							this.calculations.add(calculation);
					}
				}
				//method specific details
				if (task.getParameterSets()!=null)
				{
					for (ParameterSet paramSet : task.getParameterSets())
					{	
						//Molecular dynamics
						if (paramSet.getName().equals(ParameterSet.METHOD_MD) || paramSet.getName().equals(ParameterSet.METHOD_LANGEVIN_DYNAMICS))
						{
							MDParameterSet mdParamSet = (MDParameterSet)paramSet;

							//time step counts
							this.timeStepCount += mdParamSet.getNumberOfSteps();
							
							//simulate time
							if (mdParamSet.getSimulatedTime()!=null){
								TimeLength simulatedTimeTask = mdParamSet.getSimulatedTime();

								if (simulatedTimeTask!=null)
								{
									if (this.simulatedTime==null){
										this.simulatedTime = new TimeLength(simulatedTimeTask.getValue(), simulatedTimeTask.getUnit());
									}
									else {
										this.simulatedTime.setValue(
											this.simulatedTime.getValue() + simulatedTimeTask.getValue());
									}
								}
							}
							//force fields
							if (mdParamSet.getForceFields()!=null){
								List<String> forceFields = mdParamSet.getForceFields();
								if (forceFields!=null){
									for (String ff : forceFields){
										if (!this.forceFields.contains(ff))
											this.forceFields.add(ff);
									}
								}
							}
							//electrostatics
							ElectrostaticsModel electrostatics = mdParamSet.getElectrostatics();
							if (electrostatics!=null && !this.electrostaticsModels.contains(electrostatics.getName())){
								this.electrostaticsModels.add(electrostatics.getName());
							}
							//barostats
							Barostat barostat = mdParamSet.getBarostat();
							if (barostat!=null && !this.barostats.contains(barostat.getAlgorithm())){
								this.barostats.add(barostat.getAlgorithm());
							}
							//thermostats
							Thermostat thermostat = mdParamSet.getThermostat();
							if (thermostat!=null && !this.thermostats.contains(thermostat.getName())){
								this.thermostats.add(thermostat.getName());
							}
						}
						// QM
						else if (paramSet.getName().equals(ParameterSet.METHOD_QM))
						{
							QMParameterSet qmParamSet = (QMParameterSet)paramSet;
							//basis sets
							if (qmParamSet.getBasisSets()!=null){
								List<String> basisSets = qmParamSet.getBasisSets();
								if (basisSets!=null){
									for (String bs : basisSets){
										if (!this.basisSets.contains(bs))
											this.basisSets.add(bs);
									}
								}
							}
							//calculations
							String lot = qmParamSet.getSpecificMethodName();
							if (lot!=null && !this.qmMethods.contains(lot))
								this.qmMethods.add(lot);
						}
						// minimization
						else if (paramSet.getName().equals(ParameterSet.METHOD_MINIMIZATION))
						{
							MinimizationParameterSet minParamSet = (MinimizationParameterSet)paramSet;
							//minimization methods
							if (minParamSet.getMethodName()!=null){
								String minMethod = minParamSet.getMethodName();
								if (minMethod!=null){
									if (!this.minimizationMethods.contains(minMethod))
											this.minimizationMethods.add(minMethod);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Get number of tasks represented by this summary
	 * @return Number of tasks represented by this summary
	 */
	@XmlAttribute(name="numberOfTasks")
	public int getNumberOfTasks() {
		return numberOfTasks;
	}

	/**
	 * Set number of tasks represented by this summary
	 * @param numberOfTasks Number of tasks represented by this summary
	 */
	public void setNumberOfTasks(int numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}

	/**
	 * Get total execution time
	 * @return Total execution time
	 */
	public TimeLength getExecutionTime() {
		return executionTime;
	}

	/**
	 * Set total execution time
	 * @param executionTime Total execution time
	 */
	public void setExecutionTime(TimeLength executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * Get simulated time
	 * @return Simulated time
	 */
	public TimeLength getSimulatedTime() {
		return simulatedTime;
	}

	/**
	 * Set simulated time
	 * @param simulatedTime Simulated time
	 */
	public void setSimulatedTime(TimeLength simulatedTime) {
		this.simulatedTime = simulatedTime;
	}

	/**
	 * Get general methods (e.g. minimization, MD, QM)
	 * @return List of general methods
	 */
	@XmlElementWrapper(name="methods")
	@XmlElement(name="method")
	public List<String> getMethods() {
		return methods;
	}

	/**
	 * Set general methods (e.g. minimization, MD, QM)
	 * @param methods List of general methods
	 */
	public void setMethods(List<String> methods) {
		this.methods = methods;
	}
	
	/**
	 * Get tasks descriptions
	 * @return tasks descriptions
	 */
	@XmlElementWrapper(name="tasksDescriptions")
	@XmlElement(name="tasksDescription")
	public List<String> getTaskDescriptions() {
		return taskDescriptions;
	}

	/**
	 * Set tasks descriptions
	 * @param taskDescriptions tasks descriptions
	 */
	public void setTaskDescriptions(List<String> taskDescriptions) {
		this.taskDescriptions = taskDescriptions;
	}
	
	/**
	 * Get summary of software packages used
	 * @return Summary of software packages used
	 */
	@XmlElement(name="softwarePackageUsesSummary")
	public SummarySoftwarePackageUses getSoftwarePackageUseSummary() {
		return softwarePackageUsesSummary;
	}

	/**
	 * Set summary of software packages used
	 * @param softwarePackageUseSummary Summary of software packages used
	 */
	public void setSoftwarePackageUseSummary(SummarySoftwarePackageUses softwarePackageUseSummary) {
		this.softwarePackageUsesSummary = softwarePackageUseSummary;
	}
	
	/**
	 * Get QM methods
	 * @return List of QM methods
	 */
	@XmlElementWrapper(name="qmMethods")
	@XmlElement(name="qmMethod")
	public List<String> getQmMethods() {
		return qmMethods;
	}

	/**
	 * Set QM methods
	 * @param qmMethods List of QM methods
	 */
	public void setQmMethods(List<String> qmMethods) {
		this.qmMethods = qmMethods;
	}

	/**
	 * Get minimization methods
	 * @return List of minimization methods
	 */
	@XmlElementWrapper(name="minimizationMethods")
	@XmlElement(name="minimizationMethod")
	public List<String> getMinimizationMethods() {
		return minimizationMethods;
	}

	/**
	 * Set minimization methods
	 * @param minimizationMethods List of minimization methods
	 */
	public void setMinimizationMethods(List<String> minimizationMethods) {
		this.minimizationMethods = minimizationMethods;
	}

	/**
	 * Get total number of time steps
	 * @return Total number of time steps
	 */
	public int getTimeStepCount() {
		return timeStepCount;
	}

	/**
	 * Set total number of time steps
	 * @param timeStepCount Total number of time steps
	 */
	public void setTimeStepCount(int timeStepCount) {
		this.timeStepCount = timeStepCount;
	}
	
	/**
	 * Get force fields
	 * @return Force fields
	 */
	@XmlElementWrapper(name="forceFields")
	@XmlElement(name="forceField")
	public List<String> getForceFields() {
		return forceFields;
	}

	/**
	 * Set force fields
	 * @param forceFields Force fields
	 */
	public void setForceFields(List<String> forceFields) {
		this.forceFields = forceFields;
	}

	/**
	 * Get basis sets
	 * @return List of basis sets
	 */
	@XmlElementWrapper(name="basisSets")
	@XmlElement(name="basisSet")
	public List<String> getBasisSets() {
		return basisSets;
	}

	/**
	 * Set basis sets
	 * @param basisSets List of basis sets
	 */
	public void setBasisSets(List<String> basisSets) {
		this.basisSets = basisSets;
	}

	/**
	 * Get calculations
	 * @return List of QM calculations
	 */
	@XmlElementWrapper(name="calculations")
	@XmlElement(name="calculation")
	public List<String> getCalculations() {
		return calculations;
	}
	
	/**
	 * Set calculations
	 * @param calculations List of calculations
	 */
	public void setCalculations(List<String> calculations) {
		this.calculations = calculations;
	}
	
	/**
	 * Get list of thermostats
	 * @return List of thermostats
	 */
	@XmlElementWrapper(name="thermostats")
	@XmlElement(name="thermostat")
	public List<String> getThermostats() {
		return thermostats;
	}

	/**
	 * Set list of thermostats
	 * @param thermostats List of thermostats
	 */
	public void setThermostats(List<String> thermostats) {
		this.thermostats = thermostats;
	}

	/**
	 * Get list of barostats
	 * @return List of barostats
	 */
	@XmlElementWrapper(name="barostats")
	@XmlElement(name="barostat")
	public List<String> getBarostats() {
		return barostats;
	}

	/**
	 * Set list of barostats
	 * @param barostats List of barostats
	 */
	public void setBarostats(List<String> barostats) {
		this.barostats = barostats;
	}

	/**
	 * Get list of electrostatics models
	 * @return List of electrostatics models
	 */
	@XmlElementWrapper(name="electrostaticsModels")
	@XmlElement(name="electrostaticsModel")
	public List<String> getElectrostaticsModels() {
		return electrostaticsModels;
	}

	/**
	 * Set list of electrostatics models
	 * @param electrostaticsModels List of electrostatics models
	 */
	public void setElectrostaticsModels(List<String> electrostaticsModels) {
		this.electrostaticsModels = electrostaticsModels;
	}

	/**
	 * Get epoch timestamp for task execution start time
	 * @return Task start timestamp
	 */
	public long getStartTimestamp() {
		return startTimestamp;
	}

	/**
	 * Set epoch timestamp for task execution start time
	 * @param startTimestamp Task execution start timestamp
	 */
	public void setStartTimestamp(long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}
	
	/**
	 * Get epoch timestamp for task execution end time
	 * @return Task execution end timestamp
	 */
	public long getEndTimestamp() {
		return endTimestamp;
	}

	/**
	 * Set epoch timestamp for task execution end time
	 * @param endTimestamp Task execution end timestamp
	 */
	public void setEndTimestamp(long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
	
	/**
	 * Get max number of CPUs for one task
	 * @return max number of CPUs for one task
	 */
	public int getMaxNumberOfCPUs() {
		return maxNumberOfCPUs;
	}

	/**
	 * Set max number of CPUs for one task
	 * @param maxNumberOfCPUs
	 */
	public void setMaxNumberOfCPUs(int maxNumberOfCPUs) {
		this.maxNumberOfCPUs = maxNumberOfCPUs;
	}

	/**
	 * Get max number of GPUs for one task
	 * @return max number of GPUs for one task
	 */
	public int getMaxNumberOfGPUs() {
		return maxNumberOfGPUs;
	}

	/**
	 * Set max number of GPUs for one task
	 * @param maxNumberOfGPUs max number of GPUs for one task
	 */
	public void setMaxNumberOfGPUs(int maxNumberOfGPUs) {
		this.maxNumberOfGPUs = maxNumberOfGPUs;
	}
	
	/**
	 * Generate associated metadata
	 */
	@XmlTransient
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.methods!=null){
			
			//remove minimization method if other methods available
			if ( methods.size()>1){
				int i = methods.indexOf(ParameterSet.METHOD_MINIMIZATION);
				if (i>-1)
					methods.remove(i);

				//remove MD and QM if QM/MM available
				if ( methods.size()>1){
					if ( methods.contains(ParameterSet.METHOD_QMMM) ){
						i = methods.indexOf(ParameterSet.METHOD_MD);
						if (i>-1)
							methods.remove(i);
						i = methods.indexOf(ParameterSet.METHOD_QM);
						if (i>-1)
							methods.remove(i);
					}
				}
			}
			
			for (String m : this.methods){
				metadata.add(new MetadataAVU(MethodMetadata.COMPUTATIONAL_METHOD_NAME, m));
			}
		}
		if (this.taskDescriptions!=null && this.taskDescriptions.size()>0){
			for (String m : this.taskDescriptions){
				metadata.add(new MetadataAVU(GeneralMetadata.TASK_DESCRIPTION, m));
			}
		}
		if (this.softwarePackageUsesSummary!=null){
			metadata.addAll(this.softwarePackageUsesSummary.getMetadata());
		}
		if (this.qmMethods!=null){
			for (String m : this.qmMethods){
				metadata.add(new MetadataAVU(MethodMetadata.QM_METHOD_NAME, m));
			}
		}
		if (this.basisSets!=null){
			for (String m : this.basisSets){
				metadata.add(new MetadataAVU(MethodMetadata.QM_BASIS_SET, m));
			}
		}
		if (this.calculations!=null){
			for (String m : this.calculations){
				metadata.add(new MetadataAVU(MethodMetadata.CALCULATION, m));
			}
		}
		if (this.simulatedTime!=null){
			if (this.simulatedTime.getValue()>0.0){
				metadata.add(new MetadataAVU(
						MethodMetadata.SIMULATED_TIME, 
						String.valueOf(this.simulatedTime.getValue()), 
						this.simulatedTime.getUnit()));
			}
		}
		if (this.forceFields!=null){
			for (String m : this.forceFields){
				metadata.add(new MetadataAVU(MethodMetadata.FORCE_FIELD, m));
			}
		}
		if (this.minimizationMethods!=null){
			for (String m : this.minimizationMethods){
				metadata.add(new MetadataAVU(MethodMetadata.MD_MINIMIZATION, m));
			}
		}
		
		if (this.thermostats!=null){
			for (String m : this.thermostats){
				metadata.add(new MetadataAVU(MethodMetadata.THERMOSTAT_ALGORITHM, m));
			}
		}
		if (this.barostats!=null){
			for (String m : this.barostats){
				metadata.add(new MetadataAVU(MethodMetadata.BAROSTAT_ALGORITHM, m));
			}
		}
		if (this.electrostaticsModels!=null){
			for (String m : this.electrostaticsModels){
				metadata.add(new MetadataAVU(MethodMetadata.ELECTROSTATICS_MODELING, m));
			}
		}
		if (this.executionTime!=null){
			if (this.executionTime.getValue()>0.0){
				metadata.add(new MetadataAVU(
						PlatformMetadata.EXECUTION_TIME, 
						String.valueOf(this.executionTime.getValue()), 
						this.executionTime.getUnit()));
			}
		}
		if (this.startTimestamp>0)
			metadata.add(new MetadataAVU(PlatformMetadata.TASK_START_TIMESTAMP, String.valueOf(this.startTimestamp)));
		
		if (this.endTimestamp>0)
			metadata.add(new MetadataAVU(PlatformMetadata.TASK_END_TIMESTAMP, String.valueOf(this.endTimestamp)));
		
		if (this.timeStepCount>0)
			metadata.add(new MetadataAVU(MethodMetadata.TIME_STEP_COUNT, String.valueOf(this.timeStepCount)));
		
		if (this.maxNumberOfCPUs>0){
			metadata.add(new MetadataAVU(PlatformMetadata.NUMBER_CPUS, String.valueOf(this.maxNumberOfCPUs)));
		}
		if (this.maxNumberOfGPUs>0){
			metadata.add(new MetadataAVU(PlatformMetadata.NUMBER_GPUS, String.valueOf(this.maxNumberOfGPUs)));
		}
		
		return metadata;
	}

}
