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

package edu.utah.bmi.ibiomes.experiment;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.SimulatedConditionSet;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Experiment task (job task) defined in a single file. 
 * In MD this could be the minimization step or the iterations as defined in the input file specifying the task. 
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="task")
public abstract class ExperimentTask implements MetadataMappable {
	
	protected String name = null;
	protected String description = null;
	protected String methodType = "Unknown";

	protected ComputingEnvironment computingEnvironment = null;
	protected Software software = null;
	protected TaskExecution taskExecution = null;
	protected List<String> inputFiles;
	protected List<String> outputFiles;
	protected List<String> calculationTypes;
	protected String boundaryConditions;

	protected SimulatedConditionSet simulatedConditionSet = null;
	
	protected ExperimentTask(){	
	}
	
	/**
	 * Create new task
	 * @param name Task name
	 * @param description Task description
	 * @param methodType Method type
	 */
	public ExperimentTask(String name, String description, String methodType) {
		this.name = name;
		this.description = description;
		this.methodType = methodType;
	}

	/**
	 * Get task name
	 * @return Task name
	 */
	@XmlElement
	public String getName() {
		return name;
	}

	/**
	 * Set task name
	 * @param name Task name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get description
	 * @return Description
	 */
	@XmlElement
	public String getDescription() {
		return description;
	}

	/**
	 * Set task description
	 * @param description Task description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Get method type
	 * @return Method type
	 */
	@XmlAttribute(name="type")
	public String getMethodType() {
		return methodType;
	}
	
	/**
	 * Get list of methods
	 * @return List of methods
	 */
	public abstract List<ParameterSet> getParameterSets();

	/**
	 * Get environment conditions
	 * @return Environment conditions
	 */
	@XmlElement
	public SimulatedConditionSet getSimulatedConditionSet() {
		return simulatedConditionSet;
	}
	
	/**
	 * Set environment conditions
	 * @param simulatedConditionSet Environment conditions
	 */
	public void setSimulatedConditionSet(SimulatedConditionSet simulatedConditionSet) {
		this.simulatedConditionSet = simulatedConditionSet;
	}

	
	/**
	 * Get the type of boundary conditions
	 * @return Type of boundary conditions
	 */
	public String getBoundaryConditions() {
		return boundaryConditions;
	}
	/**
	 * Set the type of boundary conditions
	 * @param boundaryConditions Type of boundary conditions
	 */
	public void setBoundaryConditions(String boundaryConditions) {
		this.boundaryConditions = boundaryConditions;
	}
	
	/**
	 * Get computing platform
	 * @return Platform
	 */
	@XmlElement
	public ComputingEnvironment getComputingEnvironment() {
		return this.computingEnvironment;
	}
	
	/**
	 * Set computing platform
	 * @param platform Platform
	 */
	public void setPlatform(ComputingEnvironment platform) {
		this.computingEnvironment = platform;
	}

	/**
	 * Get software
	 * @return Software
	 */
	public Software getSoftware() {
		return this.software;
	}

	/**
	 * Set software
	 * @param software Software
	 */
	public void setSoftware(Software software) {
		this.software = software;
	}
	
	/**
	 * Get execution information
	 * @return Execution information
	 */
	public TaskExecution getTaskExecution() {
		return taskExecution;
	}

	/**
	 * Set execution information
	 * @param taskExecution Execution information
	 */
	public void setTaskExecution(TaskExecution taskExecution) {
		this.taskExecution = taskExecution;
	}
	
	/**
	 * Get list of calculation types
	 * @return List of calculation types
	 */
	@XmlElementWrapper(name="calculations")
	@XmlElement(name="calculation")
	public List<String> getCalculationTypes() {
		return calculationTypes;
	}

	/**
	 * Set list of calculation types
	 * @param calculations List of calculation types
	 */
	public void setCalculationTypes(List<String> calculations) {
		this.calculationTypes = calculations;
	}
	
	/**
	 * Set calculation type
	 * @param calculation Calculation types
	 */
	public void setCalculationTypes(String calculation) {
		this.calculationTypes = new ArrayList<String>();
		this.calculationTypes.add(calculation);
	}

	/**
	 * Get input files
	 * @return List of input files
	 */
	@XmlElementWrapper(name="inputFiles")
	@XmlElement(name="file")
	public List<String> getInputFiles() {
		return inputFiles;
	}

	/**
	 * Set input files
	 * @param inputFiles Input files
	 */
	public void setInputFiles(List<String> inputFiles) {
		this.inputFiles = inputFiles;
	}

	/**
	 * Set input file
	 * @param inputFile Input file absolute path
	 */
	public void setInputFiles(String inputFile) {
		this.inputFiles = new ArrayList<String>();
		this.inputFiles.add(inputFile);
	}
	
	/**
	 * Get output files
	 * @return List of output files
	 */
	@XmlElementWrapper(name="outputFiles")
	@XmlElement(name="file")
	public List<String> getOutputFiles() {
		return outputFiles;
	}

	/**
	 * Set output files
	 * @param outputFiles List of output files
	 */
	public void setOutputFiles(List<String> outputFiles) {
		this.outputFiles = outputFiles;
	}

	/**
	 * Set output file
	 * @param outputFile Output file absolute path
	 */
	public void setOutputFiles(String outputFile) {
		this.outputFiles = new ArrayList<String>();
		this.outputFiles.add(outputFile);
	}
	
	/**
	 * Generate associated metadata
	 */
	@XmlTransient
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		if (name!=null && name.length()>0){
			metadata.add(new MetadataAVU(GeneralMetadata.EXPERIMENT_TITLE, name));
		}
		if (description!=null && description.length()>0){
			metadata.add(new MetadataAVU(GeneralMetadata.TASK_DESCRIPTION, description));
		}
		if (methodType!=null && methodType.length()>0){
			metadata.add(new MetadataAVU(MethodMetadata.COMPUTATIONAL_METHOD_NAME, methodType));
		}
		if (this.simulatedConditionSet!=null){
			metadata.addAll(simulatedConditionSet.getMetadata());
		}
		if (this.software!=null)
			metadata.addAll(this.software.getMetadata());
		
		if (this.computingEnvironment!=null)
			metadata.addAll(this.computingEnvironment.getMetadata());

		if (this.taskExecution!=null)
			metadata.addAll(this.taskExecution.getMetadata());
		
		if (this.calculationTypes!=null && this.calculationTypes.size()>0){
			for (String calculation : this.calculationTypes){
				metadata.add(new MetadataAVU(MethodMetadata.CALCULATION, calculation));
			}
		}
		if (this.boundaryConditions!=null)
			metadata.add(new MetadataAVU(MethodMetadata.BOUNDARY_CONDITIONS, this.boundaryConditions ));
		
		List<ParameterSet> methods = this.getParameterSets();
		if (methods != null && methods.size()>0){
			for (ParameterSet method : methods){
				metadata.addAll(method.getMetadata());
			}
		}
		return metadata;
	}
	
	@Override
	public String toString(){
		String result = methodType;
		result += "\nMethods:";
		if (this.getParameterSets()!=null && this.getParameterSets().size()>0){
			for (ParameterSet method : this.getParameterSets()){
				result += "\n\t" + method.getMetadata().toString();
			}
		}
		else result += "\n\tNo method available";
		return result;
	}


}
