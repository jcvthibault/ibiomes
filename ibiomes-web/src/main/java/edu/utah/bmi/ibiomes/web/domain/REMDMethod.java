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

package edu.utah.bmi.ibiomes.web.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * REMD method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="method")
public class REMDMethod  extends MDMethod {

	private int numberOfReplica;
	private List<String> samplingMethods;
	private int numberOfExchanges;

	public REMDMethod(){
		super();
		this.name = Method.METHOD_REMD;
		this.timeDependent = true;
	}
	
	/**
	 * Get number of replica
	 * @return Number of replica
	 */
	public int getNumberOfReplica() {
		return numberOfReplica;
	}

	/**
	 * Set number of replica
	 * @param numberOfReplica Number of replica
	 */
	public void setNumberOfReplica(int numberOfReplica) {
		this.numberOfReplica = numberOfReplica;
	}

	/**
	 * Get sampling methods
	 * @return sampling methods
	 */
	public List<String> getSamplingMethods() {
		return samplingMethods;
	}

	/**
	 * Set sampling method
	 * @param samplingMethod Sampling methods
	 */
	public void setSamplingMethods(List<String> samplingMethods) {
		this.samplingMethods = samplingMethods;
	}

	/**
	 * Get number of exchanges
	 * @return Number of exchanges
	 */
	public int getNumberOfExchanges() {
		return numberOfExchanges;
	}

	/**
	 * Set number of exchanges
	 * @param numberOfExchanges Number of exchanges
	 */
	public void setNumberOfExchanges(int numberOfExchanges) {
		this.numberOfExchanges = numberOfExchanges;
	}
	
	/**
	 * Create REMD method from list of AVUs
	 * @param metadata
	 */
	public REMDMethod(MetadataAVUList metadata) {
		super(metadata);
		
		if (metadata.containsAttribute(MethodMetadata.ENHANCED_SAMPLING_METHOD_REPLICA_COUNT)){
			try{
				this.setNumberOfReplica(Integer.parseInt(metadata.getValue(MethodMetadata.ENHANCED_SAMPLING_METHOD_REPLICA_COUNT)));
			}
			catch(NumberFormatException e){
			}
		}
		if (metadata.containsAttribute(MethodMetadata.REMD_EXCHANGE_COUNT))
		{
			try{
				this.setNumberOfExchanges(Integer.parseInt(metadata.getValue(MethodMetadata.REMD_EXCHANGE_COUNT)));
			}
			catch(NumberFormatException e){
			}
		}		
		this.setSamplingMethods(metadata.getValues(MethodMetadata.ENHANCED_SAMPLING_METHOD_NAME));
		
		this.name = Method.METHOD_REMD;
		this.timeDependent = true;
	}

}
