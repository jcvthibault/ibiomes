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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.SimulatedConditionSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.quantity.Temperature;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;

/**
 * Experiment process group. A process group is defined as an ordered list of processes 
 * performed on the same molecule or molecular system.
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="processGroup")
public class ExperimentProcessGroup implements MetadataMappable {

	private MolecularSystem molecularSystem = null;
	private List<ExperimentProcess> processes = null;
	private String name = null;
	private String description = null;
	protected LocalDirectory fileDirectory;
	
	private final static String heatingRegex = ".*heat.*";
	private final static String equilibratingRegex = ".*equil.*";
	
	@SuppressWarnings(value = { "unused" })
	private ExperimentProcessGroup(){	
	}
	
	/**
	 * Create new process group
	 * @param name Process group name
	 * @param description Process group description
	 * @param molecularSystem Molecular system
	 * @param processes Processes
	 */
	public ExperimentProcessGroup(String name, String description, MolecularSystem molecularSystem, List<ExperimentProcess> processes){
		this.name = name;
		this.description = description;
		this.molecularSystem = molecularSystem;
		this.processes = processes;
	}
	
	/**
	 * Create new process group
	 * @param name Process group name
	 * @param description Process group description
	 * @param molecularSystem Molecular system
	 * @param process Process
	 */
	public ExperimentProcessGroup(String name, String description, MolecularSystem molecularSystem, ExperimentProcess process){
		this.name = name;
		this.description = description;
		this.molecularSystem = molecularSystem;
		List<ExperimentProcess> processes = new ArrayList<ExperimentProcess>();
		processes.add(process);
		this.processes = processes;
	}
	
	/**
	 * Get process group name
	 * @return Process group name
	 */
	@XmlElement
	public String getName() {
		return name;
	}
	
	/**
	 * Set process group name
	 * @param name Process group name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Get group description
	 * @return Description
	 */
	@XmlElement
	public String getDescription() {
		return description;
	}

	/**
	 * Set process group description
	 * @param description Process description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Get molecular system
	 * @return Molecular system
	 */
	@XmlTransient
	public MolecularSystem getMolecularSystem() {
		return molecularSystem;
	}
	
	/**
	 * Set molecular system
	 * @param system Molecular system
	 */
	public void setMolecularSystem(MolecularSystem system){
		this.molecularSystem = system;
	}
	
	/**
	 * Get list of processes
	 * @return List of processes
	 */
	@XmlTransient
	public List<ExperimentProcess> getProcesses() {
		return this.processes;
	}
	
	/**
	 * Set list of processes
	 * @param processes List of processes
	 */
	public void setProcesses(List<ExperimentProcess> processes){
		this.processes = processes;
	}
	
	/**
	 * Get summary
	 * @return Summary
	 */
	@XmlElement(name="tasksSummary")
	public SummaryExperimentTasks getTasksSummary(){
		return new SummaryExperimentTasks(this.getTasks());
	}
	
	/**
	 * Get list of tasks
	 * @return List of tasks
	 */
	@XmlTransient
	public List<ExperimentTask> getTasks() {
		List<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		if (this.processes!=null){
			for (ExperimentProcess process : processes){
				tasks.addAll(process.getTasks());
			}
		}
		return tasks;
	}
	
	/**
	 * Organize processes using common MD protocol. Try to group tasks into 
	 * 3 separate processes: minimization, equilibration, and production MD.
	 */
	public void organizeProcessesForMD()
	{
		List<ExperimentTask> tasks = this.getTasks();
		List<ExperimentProcess> newProcesses = new ArrayList<ExperimentProcess>();
		ExperimentProcess minimizationProcess = new ExperimentProcess("Minimization", "Minimization of initial structure");
		ExperimentProcess heatingProcess = new ExperimentProcess("Heating", "Heating of the system");
		ExperimentProcess equilProcess = new ExperimentProcess("Equilibration", "Equilibration of the system");
		ExperimentProcess mdMdProcess = new ExperimentProcess("Production MD", "Production molecular dynamics");
		ExperimentProcess remdProcess = new ExperimentProcess("Replica-exchange MD", "Production replica-exchange MD");
		ExperimentProcess qmmmProcess = new ExperimentProcess("QM/MM MD", "QM/MM simulations");
		ExperimentProcess qmProcess = new ExperimentProcess("QM", "QM calculations");
		ExperimentProcess qmdProcess = new ExperimentProcess("Quantum MD", "Quantum MD simulations");
		ExperimentProcess otherProcess = new ExperimentProcess("Other tasks", "");
		
		for (ExperimentTask task : tasks)
		{
			String methodType = task.getMethodType();
			if (methodType == null)
				otherProcess.addTask(task);
			else{
				//minimization
				if (methodType.equals(ParameterSet.METHOD_MINIMIZATION)){
					minimizationProcess.addTask(task);
				}
				//molecular dynamics
				else if (methodType.equals(ParameterSet.METHOD_MD) || 
						methodType.equals(ParameterSet.METHOD_LANGEVIN_DYNAMICS)){
					SimulatedConditionSet simulatedEnv = ((MDTask)task).getSimulatedConditionSet();
					boolean isHeating = false;
					if (simulatedEnv != null){
						Temperature t0 = simulatedEnv.getInitialTemperature();
						Temperature t1 = simulatedEnv.getReferenceTemperature();
						//heating
						if (t0!=null && t1!=null && t0.getValue()!=t1.getValue()){
							heatingProcess.addTask(task);
							isHeating = true;
						}
					}
					if (!isHeating){
						//use description text to infer equilibration vs heating vs production MD
						String taskDescription = task.getDescription();
						if (taskDescription != null && taskDescription.length()>0){
							taskDescription = taskDescription.toLowerCase();
							if (taskDescription.matches(equilibratingRegex)){
								equilProcess.addTask(task);
							}
							else if (taskDescription.matches(heatingRegex)){
								heatingProcess.addTask(task);
							}
							else{
								mdMdProcess.addTask(task);
							}
						}
						else{
							//production MD
							mdMdProcess.addTask(task);
						}
					}
				}
				else if (methodType.equals(ParameterSet.METHOD_REMD)){
					remdProcess.addTask(task);
				}
				else if (methodType.equals(ParameterSet.METHOD_QMMM)){
					qmmmProcess.addTask(task);
				}
				else if (methodType.equals(ParameterSet.METHOD_QUANTUM_MD)){
					qmdProcess.addTask(task);
				}
				else if (methodType.equals(ParameterSet.METHOD_QM)){
					qmProcess.addTask(task);
				}
				else //other
					otherProcess.addTask(task);
			}
		}
		if (minimizationProcess.getTasks()!=null)
			newProcesses.add(minimizationProcess);
		
		if (heatingProcess.getTasks()!=null)
			newProcesses.add(heatingProcess);

		if (equilProcess.getTasks()!=null)
			newProcesses.add(equilProcess);
		
		if (mdMdProcess.getTasks()!=null)
			newProcesses.add(mdMdProcess);
		
		if (remdProcess.getTasks()!=null)
			newProcesses.add(remdProcess);
		
		if (qmmmProcess.getTasks()!=null)
			newProcesses.add(qmmmProcess);
		
		if (qmProcess.getTasks()!=null)
			newProcesses.add(qmProcess);
		
		if (qmdProcess.getTasks()!=null)
			newProcesses.add(qmdProcess);
		
		if (otherProcess.getTasks()!=null)
			newProcesses.add(otherProcess);
		
		this.setProcesses(newProcesses);
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
			metadata.add(new MetadataAVU(GeneralMetadata.EXPERIMENT_DESCRIPTION, description));
		}
		//add molecular system info
		if (molecularSystem!=null){
			metadata.addAll(molecularSystem.getMetadata());
		}
		//add computational tasks summary
		metadata.addAll(this.getTasksSummary().getMetadata());
		
		return metadata;
	}
	
	@Override
	public String toString(){
		String result = "";
		result += "Molecular system:";
		if (molecularSystem!=null){
			result += "\n\t" + this.molecularSystem.toString();
		}
		else result += "\n\tNo system available";
		result += "\nProcesses:";
		if (processes!=null && processes.size()>0){
			for (ExperimentProcess p : processes){
				result += "\n\t" + p.getMetadata().toString();
			}
		}
		else result += "\n\tNo task available";
		return result;
	}
}
