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
 * Thermostat
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="thermostat")
public class Thermostat implements MetadataMappable{
	
	/**
	 * Andersen thermsotat
	 */
	public static final String THERMOSTAT_ANDERSEN = "Andersen";
	/**
	 * Berendsen thermsotat
	 */
	public static final String THERMOSTAT_BERENDSEN = "Berendsen";
	/**
	 * Langevin thermsotat
	 */
	public static final String THERMOSTAT_LANGEVIN = "Langevin";
	/**
	 * Velocity Verlet thermsotat
	 */
	public static final String THERMOSTAT_V_RESCALE = "V-rescale";
	/**
	 * Nose thermsotat
	 */
	public static final String THERMOSTAT_NOSE = "Nose";
	/**
	 * Nose-Hoover thermsotat
	 */
	public static final String THERMOSTAT_NOSE_HOOVER = "Nose-Hoover";
	/**
	 * Nose-Poincare thermsotat
	 */
	public static final String THERMOSTAT_NOSE_POINCARE = "Nose-Poincare";
	/**
	 * No thermsotat
	 */
	public static final String THERMOSTAT_NONE = "No";
	
	
	private String name;
	private TimeLength timeConstant=null;
	
	public Thermostat(){
	}
	
	/**
	 * Constructor
	 * @param name Thermostat name
	 */
	public Thermostat(String name){
		this.name = name;
	}
	
	/**
	 * Get thermostat name
	 * @return Thermostat name
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}
	
	/**
	 * Set thermostat name
	 * @param name Thermostat name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get thermostat time constant
	 * @return Thermostat time constant
	 */
	public TimeLength getTimeConstant() {
		return timeConstant;
	}
	
	/**
	 * Set thermostat time constant
	 * @param timeConstant Thermostat time constant
	 */
	public void setTimeConstant(TimeLength timeConstant) {
		this.timeConstant = timeConstant;
	}
	

	public MetadataAVUList getMetadata(){
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.name!=null && this.name.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.THERMOSTAT_ALGORITHM, String.valueOf(this.name)));
		
		if (this.timeConstant!=null)
			metadata.add(new MetadataAVU(MethodMetadata.THERMOSTAT_TIME_CONSTANT, 
					String.valueOf(this.timeConstant.getValue()),
					this.timeConstant.getUnit()));
		
		return metadata;
	}
}
