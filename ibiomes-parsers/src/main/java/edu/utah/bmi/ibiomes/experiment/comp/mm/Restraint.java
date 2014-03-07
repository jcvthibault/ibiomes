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

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Restraint definition. Restraints on bonds or angle allow deviations from a desired value.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class Restraint implements MetadataMappable {
	
	/**
	 *  Position restraint
	 */
	public final static String RESTRAINT_POSITION = "Position";
	/**
	 *  Dihedral restraint
	 */
	public final static String RESTRAINT_DIHEDRAL = "Dihedral";
	/**
	 *  Distance restraint
	 */
	public final static String RESTRAINT_DISTANCE = "Distance";
	/**
	 *  Plane-base dihedral restraint
	 */
	public final static String RESTRAINT_PLANE_BASE = "Plane-base";
	
	private String target;
	private String type;
	
	public Restraint(){
	}

	/**
	 * New restraint
	 * @param type Type of restraint
	 */
	public Restraint(String type){
		this.type = type;
	}
	
	/**
	 * New restraint
	 * @param type Type of restraint
	 * @param target Target of restraint
	 */
	public Restraint(String type, String target){
		this.target = target;
		this.type = type;
	}
	
	/**
	 * Get restraint target
	 * @return Restraint target
	 */
	public String getTarget() {
		return target;
	}
	
	/**
	 * Set restraint target
	 * @param target Restraint target
	 */
	public void setTarget(String target){
		this.target = target;
	}
	
	/**
	 * Get type of restraint
	 * @return Restraint type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set restraint type
	 * @param type Restraint type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Get metadata
	 */
	public MetadataAVUList getMetadata() 
	{
		MetadataAVUList metadata = new MetadataAVUList();
		if (this.type!=null && this.type.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.RESTRAINT_TYPE, this.type));
		if (this.target!=null && this.target.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.RESTRAINT_TARGET, this.target));
		return metadata;
	}
}
