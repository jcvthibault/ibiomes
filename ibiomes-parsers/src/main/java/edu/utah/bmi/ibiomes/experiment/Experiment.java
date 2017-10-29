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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryMolecularSystems;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;

/**
 * Virtual experiment
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="experiment")
public class Experiment
{
	protected String name;
	protected String publisher;
	protected String publicationDate;
	protected String description;
	protected String externalURL;
	protected String owner;
	protected String rootDirectoryPath;
	protected Long timestamp;
	protected List<ExperimentProcessGroup> processGroups;
	protected LocalDirectory fileDirectory;
	
	protected Experiment(){
	}
	
	/**
	 * Create new named experiment
	 * @param name Experiment name
	 */
	public Experiment(String name){
		this.name = name;
		init();
	}

	/**
	 * 
	 * @param fileDirectory Local file directory associated to this experiment
	 */
	public Experiment(LocalDirectory fileDirectory){
		this.fileDirectory = fileDirectory;
		this.name = fileDirectory.getName();
		init();
	}

	private void init() {
		this.publisher = System.getProperty("user.name");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.publicationDate = dateFormat.format(new Date());
	}
	/**
	 * Get experiment name
	 * @return Experiment name
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * Set experiment name
	 * @param name Experiment name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get description
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description 
	 * @param description Description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@XmlAttribute
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@XmlAttribute
	public String getPublicationDate() {
		return publicationDate;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	@XmlAttribute
	public String getRootDirectoryPath() {
		if (this.fileDirectory==null)
			return null;
		return this.fileDirectory.getAbsolutePath();
	}

	public void setExternalURL(String externalUrl) {
		this.externalURL = externalUrl;
	}
	
	@XmlAttribute
	public String getExternalURL() {
		return this.externalURL;
	}
	/**
	 * Get list of experiment process groups
	 * @return list of experiment process groups
	 */
	@XmlTransient
	public List<ExperimentProcessGroup> getProcessGroups(){
		return processGroups;
	}
	
	/**
	 * Set process groups
	 * @param processGroups Process groups
	 */
	public void setProcessGroups(List<ExperimentProcessGroup> processGroups){
		this.processGroups = processGroups;
	}
	
	/**
	 * Get list of tasks
	 * @return List of tasks
	 */
	@XmlTransient
	public List<ExperimentTask> getTasks() {
		List<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		if (this.processGroups!=null){
			for (ExperimentProcessGroup processGroup : processGroups){
				tasks.addAll(processGroup.getTasks());
			}
		}
		return tasks;
	}
	
	/**
	 * Get list of molecular systems
	 * @return List of molecular systems
	 */
	@XmlTransient
	public List<MolecularSystem> getMolecularSystems() {
		List<MolecularSystem> systems = new ArrayList<MolecularSystem>();
		if (this.processGroups!=null){
			for (ExperimentProcessGroup processGroup : processGroups){
				if (processGroup.getMolecularSystem()!=null)
					systems.add(processGroup.getMolecularSystem());
			}
		}
		return systems;
	}
	

	/**
	 * Get file directory
	 * @return File directory
	 */
	@XmlTransient
	public LocalDirectory getFileDirectory() {
		return fileDirectory;
	}

	/**
	 * Set file directory
	 * @param fileDirectory File directory
	 */
	public void setFileDirectory(LocalDirectory fileDirectory) {
		this.fileDirectory = fileDirectory;
	}
	
	/**
	 * Get tasks summary
	 * @return Tasks summary
	 */
	@XmlElement(name="tasksSummary")
	public SummaryExperimentTasks getTasksSummary(){
		return new SummaryExperimentTasks(this.getTasks());
	}

	/**
	 * Get systems summary
	 * @return Systems summary
	 */
	@XmlElement(name="molecularSystemsSummary")
	public SummaryMolecularSystems getMolecularSystemsSummary(){
		return new SummaryMolecularSystems(this.getMolecularSystems());
	}
	
	/**
	 * Get experiment metadata
	 * @throws Exception 
	 */
	@XmlTransient
	public MetadataAVUList getMetadata() throws Exception{

		MetadataAVUList metadata = this.fileDirectory.getMetadata();
		//metadata.add(new MetadataAVU(GeneralMetadata.IBIOMES_FILE_TYPE, BiosimMetadata.DIRECTORY_EXPERIMENT));
		
		if (this.processGroups!=null){
			for (ExperimentProcessGroup processGroup : this.processGroups){
				List<ExperimentTask> tasks = processGroup.getTasks();
				if (tasks!=null){
					for (ExperimentTask task : tasks){
						//software information
						Software sw = task.getSoftware();
						if (sw!=null){
							if (sw.getName()!=null)
								metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME, sw.getName()));
							if (sw.getExecutableName()!=null)
								metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_EXEC_NAME, sw.getExecutableName()));
						}
					}
				}				
			}
		}
		return metadata;
	}

}
