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

import edu.utah.bmi.ibiomes.experiment.comp.min.MinimizationParameterSet;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Conjugate gradient method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="parameterSet")
public class ConjugateGradientParameterSet extends MinimizationParameterSet
{	
	/**
	 * Constructor
	 */
	public ConjugateGradientParameterSet(){
		super(ParameterSet.METHOD_CONJUGATE_GRADIENT);
	}
	/**
	 * Constructor
	 * @param numberOfIterations Number of iterations
	 */
	public ConjugateGradientParameterSet(int numberOfIterations){
		super(ParameterSet.METHOD_CONJUGATE_GRADIENT, numberOfIterations);
	}
	
	/**
	 * Get metadata
	 */
	@Override
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = super.getMetadata();
		return metadata;
	}
}
