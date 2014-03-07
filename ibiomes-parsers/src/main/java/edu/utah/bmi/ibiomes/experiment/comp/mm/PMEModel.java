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
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Particle-Mesh Ewald electrostatics model
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="electrostaticsModel")
public class PMEModel extends ElectrostaticsModel {
	
	private int interpolationOrder = 0;
	private double ewaldCoefficient = 0.0;
	
	public PMEModel(){
		this.name = ElectrostaticsModel.PME;
	}

	/**
	 * New PME model
	 * @param interpolationOrder Interpolation order
	 * @param ewaldCoefficient Ewald coefficient
	 */
	public PMEModel(int interpolationOrder, double ewaldCoefficient){
		this.name = ElectrostaticsModel.PME;
		this.interpolationOrder = interpolationOrder;
		this.ewaldCoefficient = ewaldCoefficient;
	}
	
	/**
	 * Get interpolation order
	 * @return Interpolation order
	 */
	public int getInterpolationOrder() {
		return interpolationOrder;
	}

	/**
	 * Set interpolation order
	 * @param interpolationOrder Interpolation order
	 */
	public void setInterpolationOrder(int interpolationOrder) {
		this.interpolationOrder = interpolationOrder;
	}

	/**
	 * Get Ewald coefficient
	 * @return Ewald coefficient
	 */
	public double getEwaldCoefficient() {
		return ewaldCoefficient;
	}

	/**
	 * Set Ewald coefficient
	 * @param ewaldCoefficient Ewald coefficient
	 */
	public void setEwaldCoefficient(double ewaldCoefficient) {
		this.ewaldCoefficient = ewaldCoefficient;
	}
	
	@Override
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = super.getMetadata();
		
		if (this.interpolationOrder>0)
			metadata.add(new MetadataAVU(MethodMetadata.PME_INTERPOLATION, String.valueOf(this.interpolationOrder)));
		
		if (this.ewaldCoefficient>0.0)
			metadata.add(new MetadataAVU(MethodMetadata.PME_EWALD_COEFFICIENT, String.valueOf(this.ewaldCoefficient)));
		
		return metadata;
	}

}
