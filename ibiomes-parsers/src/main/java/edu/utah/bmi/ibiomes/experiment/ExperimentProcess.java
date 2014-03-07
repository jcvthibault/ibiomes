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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;

/**
 * Experiment process. A process is defined as an ordered list of calculations 
 * performed on the same molecule or molecular system.
 * In MD this could be the process minimization-equilibration-production for a particular protein. 
 * In QM this could be a series of calculations (e.g. optimization, frequency) for a particular drug compound.
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="process")
public class ExperimentProcess implements MetadataMappable {

	private List<ExperimentTask> tasks = null;
	private String name = null;
	private String description = null;
	
	@SuppressWarnings(value = { "unused" })
	private ExperimentProcess(){	
	}
	
	/**
	 * Create new process
	 * @param name Process name
	 * @param description Process description
	 */
	public ExperimentProcess(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Create new process with multiple tasks
	 * @param name Process name
	 * @param description Process description
	 * @param tasks Tasks
	 */
	public ExperimentProcess(String name, String description, List<ExperimentTask> tasks){
		this.name = name;
		this.description = description;
		this.tasks = tasks;
	}
	
	/**
	 * Create new process with a single task
	 * @param name Process name
	 * @param description Process description
	 * @param task Task
	 */
	public ExperimentProcess(String name, String description, ExperimentTask task){
		this.name = name;
		this.description = description;
		List<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		tasks.add(task);
		this.tasks = tasks;
	}
	
	/**
	 * Get process name
	 * @return Process name
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}
	
	/**
	 * Set process name
	 * @param name Process name
	 */
	public void setName(String name){
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
	 * Set process description
	 * @param description Process description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Get list of tasks
	 * @return List of tasks
	 */
	@XmlTransient
	public List<ExperimentTask> getTasks() {
		return tasks;
	}
	
	/**
	 * Set list of tasks
	 * @param tasks List of tasks
	 */
	public void setTasks(List<ExperimentTask> tasks){
		this.tasks = tasks;
	}
	
	/**
	 * Add task to the process
	 * @param task Task
	 */
	public void addTask(ExperimentTask task) {
		if (this.tasks==null)
			this.tasks = new ArrayList<ExperimentTask>();
		this.tasks.add(task);
	}

	/**
	 * Get tasks summary
	 * @return Summary
	 */
	@XmlElement(name="tasksSummary")
	public SummaryExperimentTasks getTasksSummary(){
		return new SummaryExperimentTasks(this.getTasks());
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
		if (tasks!=null){
			for (ExperimentTask t : tasks){
				metadata.addAll(t.getMetadata());
			}
		}
		return metadata;
	}
	
	@Override
	public String toString(){
		String result = "";
		result += "Molecular system:";
		result += "\nTasks:";
		if (tasks!=null && tasks.size()>0){
			for (ExperimentTask t : tasks){
				result += "\n\t" + t.getMetadata().toString();
			}
		}
		else result += "\n\tNo task available";
		return result;
	}
}
