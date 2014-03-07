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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * Task execution information
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="taskExecution")
public class TaskExecution implements MetadataMappable {

	public static final String TERMINATION_STATUS_NORMAL = "normal";
	public static final String TERMINATION_STATUS_ERROR = "error";
	public static final String TERMINATION_STATUS_UNKNOWN = "unknown";
	
	private int numberOfCPUs = 0;
	private int numberOfGPUs = 0;
	private String terminationSatus = TERMINATION_STATUS_UNKNOWN;
	private TimeLength executionTime = null;
	private long startTimestamp;
	private long endTimestamp;
	
	public TaskExecution(){
	}
	
	/**
	 * New task execution
	 * @param terminationStatus Termination status
	 */
	public TaskExecution(String terminationStatus){
		this.terminationSatus = terminationStatus;
		this.numberOfCPUs = 1;
	}
	
	/**
	 * Get number of CPUs
	 * @return Number of CPUs
	 */
	public int getNumberOfCPUs() {
		return numberOfCPUs;
	}

	/**
	 * Set number of CPUs
	 * @param numberOfCPUs Number of CPUs
	 */
	public void setNumberOfCPUs(int numberOfCPUs) {
		this.numberOfCPUs = numberOfCPUs;
	}

	/**
	 * Get number of GPUs
	 * @return Number of GPUs
	 */
	public int getNumberOfGPUs() {
		return numberOfGPUs;
	}

	/**
	 * Set number of CGPUs
	 * @param numberOfGPUs Number of GPUs
	 */
	public void setNumberOfGPUs(int numberOfGPUs) {
		this.numberOfGPUs = numberOfGPUs;
	}
	
	/**
	 * Check whether the task was terminated without error
	 * @return False if an error occurred, true otherwise.
	 */
	@XmlAttribute
	public String getTerminationStatus() {
		return terminationSatus;
	}

	/**
	 * Set termination status
	 * @param terminationSatus Termination status
	 */
	public void setTerminationStatus(String terminationSatus) {
		this.terminationSatus = terminationSatus;
	}

	/**
	 * Get execution time
	 * @return Execution time
	 */
	public TimeLength getExecutionTime() {
		return executionTime;
	}
	
	/**
	 * Set execution time
	 * @param executionTime Execution time
	 */
	public void setExecutionTime(TimeLength executionTime) {
		this.executionTime = executionTime;
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
	 * Get platform metadata
	 */
	public MetadataAVUList getMetadata() {
		MetadataAVUList metadata = new MetadataAVUList();
		if (this.numberOfCPUs>0)
			metadata.add(new MetadataAVU(PlatformMetadata.NUMBER_CPUS, String.valueOf(this.numberOfCPUs)));
		if (this.numberOfGPUs>0)
			metadata.add(new MetadataAVU(PlatformMetadata.NUMBER_GPUS, String.valueOf(this.numberOfGPUs)));
		if (this.startTimestamp>0)
			metadata.add(new MetadataAVU(PlatformMetadata.TASK_START_TIMESTAMP, String.valueOf(this.startTimestamp)));
		if (this.endTimestamp>0)
			metadata.add(new MetadataAVU(PlatformMetadata.TASK_END_TIMESTAMP, String.valueOf(this.endTimestamp)));
		if (this.terminationSatus != null && this.terminationSatus.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.PROGRAM_TERMINATION, this.terminationSatus));
		if (this.executionTime!=null)
			metadata.add(new MetadataAVU(PlatformMetadata.EXECUTION_TIME, String.valueOf(executionTime.getValue()), executionTime.getUnit()));
		
		return metadata;
	}
}
