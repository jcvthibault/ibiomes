package edu.utah.bmi.ibiomes.web.domain;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.TaskExecution;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;

/**
 * Platform/hardware access object
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class ExecutionInformation 
{
	private List<String> operatingSystems;
	private List<String> cpuArchitectures;
	private List<String> gpuArchitectures;
	private List<String> programs;
	private int numberOfCPUs = 1;
	private int numberOfGPUs = 0;
	private boolean errors = false;
	private double executionTime = 0.0;
	private String dateStartTask;
	private String dateEndTask;
	private List<String> resourceDomains;
	
	public ExecutionInformation(){
	}
	
	/**
	 * Create platform based on a list of iBIOMES metadata
	 * @param metadata List of iBIOMES metadata
	 */
	public ExecutionInformation(MetadataAVUList metadata){
		
		this.setOperatingSystems(metadata.getValues(PlatformMetadata.OPERATING_SYSTEM));
		
		//hardware architectures
		this.setCpuArchitectures(metadata.getValues(PlatformMetadata.CPU_ARCHITECTURE));
		this.setGpuArchitectures(metadata.getValues(PlatformMetadata.GPU_ARCHITECTURE));
		this.setResourceDomains(metadata.getValues(PlatformMetadata.RESOURCE_DOMAIN));
		this.setPrograms(metadata.getValues(PlatformMetadata.SOFTWARE_EXEC_NAME));
		
		String nCpusStr = metadata.getValue(PlatformMetadata.NUMBER_CPUS);
		if (nCpusStr!=null && nCpusStr.length()>0){
			try{
				this.numberOfCPUs = Integer.parseInt(nCpusStr);
			} catch (NumberFormatException e){
			}
		}
		String nGpusStr = metadata.getValue(PlatformMetadata.NUMBER_GPUS);
		if (nGpusStr!=null && nGpusStr.length()>0){
			try{
				this.numberOfGPUs = Integer.parseInt(nGpusStr);
			} catch (NumberFormatException e){
			}
		}
		
		//execution times and dates
		String execDate = metadata.getValue(PlatformMetadata.TASK_START_TIMESTAMP);
		if (execDate!=null && execDate.length()>0){
			try{
				this.dateStartTask = (new Date(Long.parseLong(execDate)*1000)).toString();
			} catch (Exception e){
			}
		}execDate = metadata.getValue(PlatformMetadata.TASK_END_TIMESTAMP);
		if (execDate!=null && execDate.length()>0){
			try{
				this.dateEndTask = (new Date(Long.parseLong(execDate)*1000)).toString();
			} catch (Exception e){
			}
		}
		
		if (metadata.hasPair(new MetadataAVU(PlatformMetadata.PROGRAM_TERMINATION, TaskExecution.TERMINATION_STATUS_ERROR)))
			this.errors = true;
		
		//TODO get execution time in hours
		//if (PlatformMetadata.EXECUTION_TIME)
		//	metadata.add(new MetadataAVU(PlatformMetadata.EXECUTION_TIME, String.valueOf(executionTime.getValue()), executionTime.getUnit()));
		
	}
	
	/**
	 * Get operating systems
	 * @return Operating systems
	 */
	public List<String> getOperatingSystems() {
		return operatingSystems;
	}
	/**
	 * Set operating systems
	 * @param operatingSystem Operating systems
	 */
	public void setOperatingSystems(List<String> operatingSystems) {
		this.operatingSystems = operatingSystems;
	}
	/**
	 * Get resource domain
	 * @return Resource domain
	 */
	public List<String> getResourceDomains() {
		return resourceDomains;
	}
	/**
	 * Set resource domain 
	 * @param resourceDomain Resource domain
	 */
	public void setResourceDomains(List<String> resourceDomains) {
		this.resourceDomains = resourceDomains;
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
	 * Get execution time
	 * @return Execution time
	 */
	public double getExecutionTime() {
		return executionTime;
	}
	
	/**
	 * Set execution time (in hours)
	 * @param executionTime Execution time
	 */
	public void setExecutionTime(double executionTime) {
		this.executionTime = executionTime;
	}
	
	/**
	 * Get task execution start time
	 * @return Task start timestamp
	 */
	public String getDateStartTask() {
		return dateStartTask;
	}

	/**
	 * Set task execution start time
	 * @param dateStartTask Task execution start timestamp
	 */
	public void setDateStartTask(String dateStartTask) {
		this.dateStartTask = dateStartTask;
	}
	
	/**
	 * Get timestamp for task execution end time
	 * @return Task execution end timestamp
	 */
	public String getDateEndTask() {
		return dateEndTask;
	}

	/**
	 * Set timestamp for task execution end time
	 * @param dateEndTask Task execution end timestamp
	 */
	public void setDateEndTask(String dateEndTask) {
		this.dateEndTask = dateEndTask;
	}

	public List<String> getCpuArchitectures() {
		return cpuArchitectures;
	}

	public void setCpuArchitectures(List<String> cpuArchitectures) {
		this.cpuArchitectures = cpuArchitectures;
	}

	public List<String> getGpuArchitectures() {
		return gpuArchitectures;
	}

	public void setGpuArchitectures(List<String> gpuArchitectures) {
		this.gpuArchitectures = gpuArchitectures;
	}

	public boolean hasErrors() {
		return errors;
	}

	public void hasErrors(boolean hasErrors) {
		this.errors = hasErrors;
	}

	public List<String> getPrograms() {
		return programs;
	}

	public void setPrograms(List<String> programs) {
		this.programs = programs;
	}
	
}
