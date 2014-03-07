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

/**
 * Software access object
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class Software implements MetadataMappable {
	/**
	 *  AMBER MD software package
	 */
	public final static String AMBER = "AMBER";
	/**
	 *  CHARMM MD software package
	 */
	public final static String CHARMM = "CHARMM";
	/**
	 *  LAMMPS MD software package
	 */
	public final static String LAMMPS = "LAMMPS";
	/**
	 *  GROMACS MD software package
	 */
	public final static String GROMACS = "GROMACS";
	/**
	 *  NAMD MD software package
	 */
	public final static String NAMD = "NAMD";
	/**
	 *  NWChem QM software package
	 */
	public final static String NWCHEM = "NWChem";
	/**
	 *  GAUSSIAN QM software package
	 */
	public final static String GAUSSIAN = "GAUSSIAN";
	/**
	 *  GAMESS QM software package
	 */
	public final static String GAMESS = "GAMESS";
	
	private String name;
	private String version;
	private String executableName;
	
	public Software(){
	}
	
	/**
	 * Create software object based on metadata
	 * @param metadata List of metadata
	 */
	public Software(MetadataAVUList metadata){
		String softwareName = metadata.getValue(PlatformMetadata.SOFTWARE_NAME);
		String softwareVersion = metadata.getValue(PlatformMetadata.SOFTWARE_VERSION);
		String execName = metadata.getValue(PlatformMetadata.SOFTWARE_EXEC_NAME);
		if (softwareName.length()>0)
			this.setName(softwareName);
		else this.setName("UNKNOWN");
		if (softwareVersion.length()>0)
			this.setVersion(softwareVersion);
		if (execName.length()>0)
			this.setExecutableName(execName);
	}
	
	/**
	 * Constructor
	 * @param name Software package name
	 */
	public Software(String name){
		this.name = name;
	}
	
	/**
	 * Get software name
	 * @return Software name
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}
	/**
	 * Set software name
	 * @param name Software name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get full name with version
	 * @return Full name with version
	 */
	public String getFullName() {
		String fullName = this.name;
		if (this.version!=null && this.version.length()>0)
			fullName += " " + version;
		return fullName;
	}
	
	/**
	 * Get software version
	 * @return Software version
	 */
	@XmlAttribute
	public String getVersion() {
		return version;
	}
	
	/**
	 * Set software version
	 * @param version Software version
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * Get executable name
	 * @return Executable name
	 */
	@XmlAttribute
	public String getExecutableName() {
		return executableName;
	}
	
	/**
	 * Set executable name
	 * @param executableName Executable name
	 */
	public void setExecutableName(String executableName) {
		this.executableName = executableName;
	}

	/**
	 * Get software metadata
	 */
	public MetadataAVUList getMetadata() {
		
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.name!=null && this.name.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME, this.name));
		
		String fullName = this.getFullName();
		if (fullName!=null && fullName.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME_W_VERSION, fullName));
		
		if (this.version!=null && this.version.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_VERSION, this.version));
		
		if (this.executableName!=null && this.executableName.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_EXEC_NAME, this.executableName));
		
		return metadata;
	}
}
