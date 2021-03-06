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

package edu.utah.bmi.ibiomes.experiment.comp.min;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Minimization method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="parameterSet")
public class MinimizationParameterSet extends ParameterSet {

	protected String methodName;
	protected int numberOfSteps;
	
	@SuppressWarnings(value = { "unused" })
	private MinimizationParameterSet(){
		super();
	}	
	
	/**
	 * Create new minimization method
	 * @param methodName Minimization method name
	 */
	public MinimizationParameterSet(String methodName){
		super();
		this.name = ParameterSet.METHOD_MINIMIZATION;
		this.methodName = methodName;
	}
	
	/**
	 * Create new minimization method
	 * @param methodName Minimization method name
	 * @param numberOfIterations Number of iterations
	 */
	public MinimizationParameterSet(String methodName, int numberOfIterations){
		super();
		this.name = ParameterSet.METHOD_MINIMIZATION;
		this.methodName = methodName;
		this.numberOfSteps = numberOfIterations;
	}

	/**
	 * Get minimization method name
	 * @return Minimization method name
	 */
	@XmlAttribute(name="name")
	public String getMethodName() {
		return this.methodName;
	}

	/**
	 * Set minimization method name
	 * @param methodName Minimization method name
	 */
	public void setMethodNames(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Get number of steps
	 * @return Number of steps/iterations
	 */
	public int getNumberOfSteps() {
		return this.numberOfSteps;
	}

	/**
	 * Set number of steps/iterations
	 * @param numberOfSteps Number of steps/iterations
	 */
	public void setNumberOfSteps(int numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}
	
	/**
	 * Get minimization metadata
	 */
	@Override
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = super.getMetadata();
		if (this.methodName!=null && methodName.length()>0){
			metadata.add(new MetadataAVU(MethodMetadata.MD_MINIMIZATION, methodName));
		}
		return metadata;
	}
}
