/*
 * iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2013  Julien Thibault, University of Utah
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

package edu.utah.bmi.ibiomes.experiment.comp.qm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Parameter set specific to quantum MD simulations (Ab-inito MD, semi-empirical MD)
 * @author Julien Thibault - University of Utah, BMI
 *
 */
@XmlRootElement(name="parameterSet")
public class QuantumMDParameterSet extends ParameterSet {

	private String quantumMDMethodName;
	
	/** 
	 * Car-Parrinello MD
	 */
	public final static String CAR_PARRINELLO_MD = "Car-Parrinello MD";
	/** 
	 * Born-Oppenheimer MD
	 */
	public final static String BORN_OPPENHEIMER_MD = "Born-Oppenheimer MD";
	/** 
	 * Path integral MD
	 */
	public final static String PATH_INTEGRAL_MD = "Path integral MD";
	
	/**
	 * New QMMD parameter set
	 */
	public QuantumMDParameterSet(){
		this.name = ParameterSet.METHOD_QUANTUM_MD;
	}

	/**
	 * New QMMD parameter set
	 * @param quantumMDMethodName Specific method name
	 */
	public QuantumMDParameterSet(String quantumMDMethodName){
		this.name = ParameterSet.METHOD_QUANTUM_MD;
		this.quantumMDMethodName = quantumMDMethodName;
	}
	
	/**
	 * Get specific method name (e.g. Car-Parrinello MD, Born-Oppenheimer MD)
	 * @return Specific method name
	 */
	@XmlAttribute(name="name")
	public String getMethodName() {
		return quantumMDMethodName;
	}

	/**
	 * Set specific method name (e.g. Car-Parrinello MD, Born-Oppenheimer MD)
	 * @param quantumMDMethodName Specific method name
	 */
	public void setMethodName(String quantumMDMethodName) {
		this.quantumMDMethodName = quantumMDMethodName;
	}
	
	@Override
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = super.getMetadata();
		
		if (this.quantumMDMethodName!=null && this.quantumMDMethodName.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.QMD_METHOD, this.quantumMDMethodName));

		return metadata;
	}
}
