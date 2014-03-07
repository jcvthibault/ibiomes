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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.quantity.Frequency;

/**
 * Langevin thermostat
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="thermostat")
public class LangevinThermostat extends Thermostat {
	
	private long randomSeed = -1;
	private Frequency collisionFrequency;
	
	/**
	 * Constructor
	 */
	public LangevinThermostat(){
		super(Thermostat.THERMOSTAT_LANGEVIN);
	}

	/**
	 * Get Langevin collision frequency
	 * @return Langevin collision frequency
	 */
	@XmlElement(name="collisionFrequency")
	public Frequency getCollisionFrequency() {
		return collisionFrequency;
	}

	/**
	 * Set Langevin collision frequency
	 * @param collisionFrequency Langevin collision frequency
	 */
	public void setCollisionFrequency(Frequency collisionFrequency) {
		this.collisionFrequency = collisionFrequency;
	}
	
	/**
	 * Get random seed for thermostat initialization
	 * @return Random seed for thermostat initialization
	 */
	@XmlElement(name="randomSeed")
	public long getRandomSeed() {
		return randomSeed;
	}

	/**
	 * Set random seed for thermostat initialization
	 * @param randomSeed Random seed for thermostat initialization
	 */
	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}
	
	@Override
	public MetadataAVUList getMetadata(){
		MetadataAVUList metadata = super.getMetadata();
		
		if (this.collisionFrequency!=null)
			metadata.add(new MetadataAVU(
					MethodMetadata.LANGEVIN_COLLISION_FREQUENCY, 
					String.valueOf(this.collisionFrequency.getValue()),
					this.collisionFrequency.getUnit()));
		
		if (this.randomSeed!=-1)
			metadata.add(new MetadataAVU(MethodMetadata.LANGEVIN_RANDOM_SEED, String.valueOf(randomSeed)));
		
		return metadata;
	}
}
