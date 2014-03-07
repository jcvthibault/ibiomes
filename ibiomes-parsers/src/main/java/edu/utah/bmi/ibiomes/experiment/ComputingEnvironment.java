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

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;

/**
 * Platform/hardware access object
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class ComputingEnvironment implements MetadataMappable
{
	private String operatingSystem;
	private String resourceDomain;
	private String cpuArchitecture;
	private String gpuArchitecture;
	private String machineArchitecture;
	

	public ComputingEnvironment(){
	}
	
	/**
	 * Create platform based on a list of iBIOMES metadata
	 * @param metadata List of iBIOMES metadata
	 */
	public ComputingEnvironment(MetadataAVUList metadata){
		this.setOperatingSystem(metadata.getValue(PlatformMetadata.OPERATING_SYSTEM));
		this.setCpuArchitecture(metadata.getValue(PlatformMetadata.CPU_ARCHITECTURE));
		this.setGpuArchitecture(metadata.getValue(PlatformMetadata.GPU_ARCHITECTURE));
		this.setMachineArchitecture(metadata.getValue(PlatformMetadata.MACHINE_ARCHITECTURE));
		this.setResourceDomain(metadata.getValue(PlatformMetadata.RESOURCE_DOMAIN));
	}
	
	/**
	 * Get operating system
	 * @return Operating system
	 */
	public String getOperatingSystem() {
		return operatingSystem;
	}
	/**
	 * Set operating system
	 * @param operatingSystem Operating system
	 */
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	/**
	 * Get resource domain
	 * @return Resource domain
	 */
	public String getResourceDomain() {
		return resourceDomain;
	}
	/**
	 * Set resource domain 
	 * @param resourceDomain Resource domain
	 */
	public void setResourceDomain(String resourceDomain) {
		this.resourceDomain = resourceDomain;
	}


	public String getMachineArchitecture() {
		return machineArchitecture;
	}

	public void setMachineArchitecture(String machineArchitecture) {
		this.machineArchitecture = machineArchitecture;
	}
	
	/**
	 * Get CPU architecture
	 * @return CPU architecture
	 */
	public String getCpuArchitecture() {
		return cpuArchitecture;
	}
	/**
	 * Set CPU architecture
	 * @param architecture CPU architecture
	 */
	public void setCpuArchitecture(String architecture) {
		this.cpuArchitecture = architecture;
	}
	/**
	 * Get GPU architecture
	 * @return GPU architecture
	 */
	public String getGpuArchitecture() {
		return gpuArchitecture;
	}
	/**
	 * Set GPU architecture
	 * @param architecture GPU architecture
	 */
	public void setGpuArchitecture(String architecture) {
		this.gpuArchitecture = architecture;
	}
	
	/**
	 * Get platform metadata
	 */
	public MetadataAVUList getMetadata() {
		MetadataAVUList metadata = new MetadataAVUList();
		if (this.cpuArchitecture!=null && this.cpuArchitecture.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.CPU_ARCHITECTURE, this.cpuArchitecture));
		if (this.gpuArchitecture!=null && this.gpuArchitecture.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.GPU_ARCHITECTURE, this.gpuArchitecture));
		if (this.operatingSystem!=null && this.operatingSystem.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.OPERATING_SYSTEM, this.operatingSystem));
		if (this.machineArchitecture!=null && this.machineArchitecture.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.MACHINE_ARCHITECTURE, this.machineArchitecture));
		if (this.resourceDomain!=null && this.resourceDomain.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.RESOURCE_DOMAIN, this.resourceDomain));
		return metadata;
	}

	
}
