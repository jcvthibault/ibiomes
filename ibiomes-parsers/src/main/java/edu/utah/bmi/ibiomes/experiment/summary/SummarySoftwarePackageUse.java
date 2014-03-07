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

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;

/**
 * Summarizes software package use
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="softwarePackageSummary")
public class SummarySoftwarePackageUse implements MetadataMappable {
	private String name;
	private List<String> fullNames;
	private List<String> versions;
	private List<String> executables;
	
	@SuppressWarnings(value = { "unused" })
	private SummarySoftwarePackageUse(){
	}
	
	/**
	 * Initialize new summary
	 * @param name Software package name
	 */
	public SummarySoftwarePackageUse(String name){
		this.name = name;
		this.fullNames = new ArrayList<String>();
		this.versions = new ArrayList<String>();
		this.executables = new ArrayList<String>();
	}
	
	/**
	 * Get software package name
	 * @return Software package name
	 */
	@XmlAttribute(name="name")
	public String getName() {
		return this.name;
	}

	/**
	 * Set software package name
	 * @param name Software package name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get list of versions used
	 * @return List of versions used
	 */
	@XmlElementWrapper(name="softwareVersions")
	@XmlElement(name="softwareVersion")
	public List<String> getVersions() {
		return versions;
	}
	
	/**
	 * Set list of versions used
	 * @param versions List of versions used
	 */
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

	/**
	 * Get list of full names
	 * @return List of full names
	 */
	@XmlElementWrapper(name="softwareNameAndVersions")
	@XmlElement(name="softwareNameAndVersion")
	public List<String> getFullNames() {
		return this.fullNames;
	}

	/**
	 * Get list of executables used
	 * @return List of executables used
	 */
	@XmlElementWrapper(name="softwareExecutables")
	@XmlElement(name="softwareExecutable")
	public List<String> getExecutables() {
		return executables;
	}

	/**
	 * Set list of executables used
	 * @param executables List of executables used
	 */
	public void setExecutables(List<String> executables) {
		this.executables = executables;
	}
	
	/**
	 * Generate associated metadata
	 */
	@XmlTransient
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.name!=null){
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME, this.name));
		}
		/*if (this.versions!=null){
			for (String m : this.versions){
				metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_VERSION, m));
			}
		}*/
		if (this.fullNames!=null){
			for (String m : this.fullNames){
				metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME_W_VERSION, m));
			}
		}
		if (this.executables!=null){
			for (String m : this.executables){
				metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_EXEC_NAME, m));
			}
		}
		return metadata;
	}

}
