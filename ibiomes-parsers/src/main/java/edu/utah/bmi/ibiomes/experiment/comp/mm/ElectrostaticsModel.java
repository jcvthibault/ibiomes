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
import edu.utah.bmi.ibiomes.quantity.Distance;

/**
 * Electrostatics interaction modeling
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="electrostaticsModel")
public class ElectrostaticsModel implements MetadataMappable {
	/**
	 * Cut-off
	 */
	public static final String CUT_OFF = "Cut-off";
	/**
	 * Classic Ewald
	 */
	public static final String CLASSIC_EWALD = "Classic Ewald";
	/**
	 * Particle Mesh Ewald (PME)
	 */
	public static final String PME = "PME";
	/**
	 * PPPME
	 */
	public static final String PPPME = "PPPME";
	/**
	 * Shift
	 */
	public static final String SHIFT = "Shift";
	/**
	 * Switch
	 */
	public static final String SWITCH = "Switch";
	/**
	 * Reaction field
	 */
	public static final String REACTION_FIELD = "Reaction field";
	/**
	 * Generalized reaction field
	 */
	public static final String GENERALIZED_REACTION_FIELD = "Generalized reaction field";
	
	protected String name;
	protected Distance cutoff;
	

	public ElectrostaticsModel(){
	}
	
	/**
	 * New electrostatics model
	 * @param name Name of the model
	 */
	public ElectrostaticsModel(String name){
		this.name = name;
	}
	
	/**
	 * Get electrostatics model name
	 * @return Electrostatics model name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set electrostatics model name
	 * @param name Electrostatics model name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Geet cutoff
	 * @return Cutoff
	 */
	public Distance getCutoff() {
		return cutoff;
	}

	/**
	 * Set cutoff
	 * @param cutoff Cutoff
	 */
	public void setCutoff(Distance cutoff) {
		this.cutoff = cutoff;
	}
	
	@Override
	public String toString(){
		return this.name;
	}

	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.name!=null && this.name.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.ELECTROSTATICS_MODELING, this.name));
		
		if (this.cutoff!=null){
			MetadataAVU avu = new MetadataAVU(MethodMetadata.CUTOFF_ELECTROSTATICS, String.valueOf(this.cutoff.getValue()));
			if (this.cutoff.getUnit()!=null)
				avu.setUnit(this.cutoff.getUnit());
			metadata.add(avu);
		}
		
		return metadata;
	}
	
	

}
