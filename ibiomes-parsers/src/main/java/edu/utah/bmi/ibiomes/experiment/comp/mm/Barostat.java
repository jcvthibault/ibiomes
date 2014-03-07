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

package edu.utah.bmi.ibiomes.experiment.comp.mm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * Barostat
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="barostat")
public class Barostat implements MetadataMappable {

	/**
	 * Andersen barostat
	 */
	public static final String BAROSTAT_ANDERSEN = "Andersen";
	/**
	 * Berendsen barostat
	 */
	public static final String BAROSTAT_BERENDSEN = "Berendsen";
	/**
	 * Hoover barostat
	 */
	public static final String BAROSTAT_HOOVER = "Hoover";
	/**
	 * Parrinello-Rahman barostat
	 */
	public static final String BAROSTAT_PARINELLO_RAHMAN = "Parrinello-Rahman";
	/**
	 * Isotropic barostat
	 */
	public static final String BAROSTAT_TYPE_ISOTROPIC = "Isotropic";
	/**
	 * Anisotropic barostat
	 */
	public static final String BAROSTAT_TYPE_ANISOTROPIC = "Anisotropic";
	/**
	 * Semi-isotropic barostat
	 */
	public static final String BAROSTAT_TYPE_SEMIISOTROPIC = "Semi-isotropic";
	
	private String algorithm = null;
	private String type = null;
	private TimeLength timeConstant = null;
	
	public Barostat(){
	}
	
	/**
	 * Constructor
	 * @param algorithm Barostat algorithm
	 */
	public Barostat(String algorithm){
		this.algorithm = algorithm;
	}

	/**
	 * Constructor
	 * @param algorithm Barostat algorithm
	 * @param type Barostat type
	 */
	public Barostat(String algorithm, String type){
		this.algorithm = algorithm;
		this.type = type;
	}
	
	/**
	 * Get barostat algorithm
	 * @return Barostat algorithm
	 */
	@XmlAttribute
	public String getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * Set barostat algorithm
	 * @param algorithm Barostat algorithm
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * Get barostat type
	 * @return Barostat type
	 */
	@XmlAttribute
	public String getType() {
		return type;
	}

	/**
	 * Set barostat type
	 * @param type Barostat type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Get barostat time constant
	 * @return Barostat time constant
	 */
	public TimeLength getTimeConstant() {
		return timeConstant;
	}
	
	/**
	 * Set barostat time constant
	 * @param timeConstant Barostat time constant
	 */
	public void setTimeConstant(TimeLength timeConstant) {
		this.timeConstant = timeConstant;
	}

	public MetadataAVUList getMetadata(){
		MetadataAVUList metadata = new MetadataAVUList();
		if (this.timeConstant!=null)
			metadata.add(new MetadataAVU(MethodMetadata.BAROSTAT_TIME_CONSTANT, 
					String.valueOf(this.timeConstant.getValue()),
					this.timeConstant.getUnit()));
		if (this.algorithm!=null && this.algorithm.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.BAROSTAT_ALGORITHM, String.valueOf(this.algorithm)));
		if (this.type!=null && this.type.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.BAROSTAT_TYPE, String.valueOf(this.type)));
		return metadata;
	}
}
