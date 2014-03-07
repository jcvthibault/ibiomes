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

package edu.utah.bmi.ibiomes.experiment.comp;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.quantity.Pressure;
import edu.utah.bmi.ibiomes.quantity.Temperature;

/**
 * Molecular system environment (reference temperature, pressure, and apparent pH)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="molecularSystemEnvironment")
public class SimulatedConditionSet implements MetadataMappable
{
	private Pressure referencePressure = null;
	private Temperature referenceTemperature = null;
	private Pressure initialPressure = null;
	private Temperature initialTemperature = null;

	public SimulatedConditionSet(){
	}
	
	/**
	 * Create new condition set
	 * @param pressure Pressure
	 * @param temperature Temperature
	 */
	public SimulatedConditionSet(Pressure pressure, Temperature temperature){
		this.referencePressure = pressure;
		this.referenceTemperature = temperature;
	}
	
	/**
	 * Get reference pressure at which the system is maintained
	 * @return Reference pressure
	 */
	public Pressure getReferencePressure() {
		return referencePressure;
	}
	
	/**
	 * Set reference pressure at which the system is maintained
	 * @param pressure Reference pressure
	 */
	public void setReferencePressure(Pressure pressure) {
		this.referencePressure = pressure;
	}

	/**
	 * Get reference temperature at which the system is maintained
	 * @return Reference temperature
	 */
	public Temperature getReferenceTemperature() {
		return referenceTemperature;
	}
	
	/**
	 * Set reference temperature at which the system is maintained
	 * @param temperature Reference temperature
	 */
	public void setReferenceTemperature(Temperature temperature) {
		this.referenceTemperature = temperature;
	}

	public Pressure getInitialPressure() {
		return initialPressure;
	}

	public void setInitialPressure(Pressure initialPressure) {
		this.initialPressure = initialPressure;
	}

	public Temperature getInitialTemperature() {
		return initialTemperature;
	}

	public void setInitialTemperature(Temperature initialTemperature) {
		this.initialTemperature = initialTemperature;
	}
	
	public MetadataAVUList getMetadata(){
		MetadataAVUList metadata = new MetadataAVUList();
		if (this.referencePressure!=null)
			metadata.add(new MetadataAVU(
					MethodMetadata.REFERENCE_PRESSURE, 
					String.valueOf(this.referencePressure.getValue()),
					this.referencePressure.getUnit()));
		if (this.referenceTemperature!=null)
			metadata.add(new MetadataAVU(
					MethodMetadata.REFERENCE_TEMPERATURE, 
					String.valueOf(this.referenceTemperature.getValue()),
					this.referenceTemperature.getUnit()));
		if (this.initialPressure!=null)
			metadata.add(new MetadataAVU(
					MethodMetadata.INITIAL_PRESSURE, 
					String.valueOf(this.initialPressure.getValue()),
					this.initialPressure.getUnit()));
		if (this.initialTemperature!=null)
			metadata.add(new MetadataAVU(
					MethodMetadata.INITIAL_TEMPERATURE, 
					String.valueOf(this.initialTemperature.getValue()),
					this.initialTemperature.getUnit()));
		
		return metadata;
	}
	
}
