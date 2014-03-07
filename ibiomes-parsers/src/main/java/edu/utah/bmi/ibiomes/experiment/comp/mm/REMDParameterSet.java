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

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Replica-exchange molecular dynamics parameters/method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="parameterSet")
public class REMDParameterSet extends ParameterSet
{	
	private int numberOfReplica;
	private SamplingMethod samplingMethod;
	private HashMap<String, List<Object>> dimensions;
	private int numberOfExchanges;
	
	/**
	 * Constructor
	 */
	public REMDParameterSet(){
		super(ParameterSet.METHOD_REMD);
		this.numberOfReplica = 1;
		this.samplingMethod = new SamplingMethod(SamplingMethod.REMD);
	}

	/**
	 * Create new REMD parameter set
	 * @param numberOfReplica Number of replica
	 * @param dimensions Dimensions participating in the exchange
	 */
	public REMDParameterSet(int numberOfReplica, HashMap<String, List<Object>> dimensions){
		super(ParameterSet.METHOD_REMD);
		this.dimensions = dimensions;
		this.numberOfReplica = numberOfReplica;
		this.samplingMethod = new SamplingMethod(SamplingMethod.REMD);
	}
	
	/**
	 * Get dimensions of REMD (name and values)
	 * @return Dimensions
	 */
	@XmlTransient
	public HashMap<String, List<Object>> getDimensions() {
		return dimensions;
	}

	/**
	 * Set dimensions
	 * @param dimensions Dimensions
	 */
	public void setDimensions(HashMap<String, List<Object>> dimensions) {
		this.dimensions = dimensions;
	}
	
	/**
	 * Get dimension names
	 * @return Dimension names
	 */
	@XmlElementWrapper(name="dimensions")
	@XmlElement(name="dimension")
	public Set<String> getDimensionsNames(){
		Set<String> labels = null;
		if (this.dimensions!=null)
			labels = this.dimensions.keySet();
		return labels;
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
	 * Get sampling method
	 * @return sampling method
	 */
	public SamplingMethod getSamplingMethod() {
		return samplingMethod;
	}

	/**
	 * Set sampling method
	 * @param samplingMethod Sampling method
	 */
	public void setSamplingMethod(SamplingMethod samplingMethod) {
		this.samplingMethod = samplingMethod;
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
	 * Get REMD metadata
	 */
	@Override
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = super.getMetadata();

		if (this.samplingMethod!=null)
			metadata.addAll(this.samplingMethod.getMetadata());
		
		if (this.numberOfReplica>0){
			metadata.add(new MetadataAVU(
					MethodMetadata.ENHANCED_SAMPLING_METHOD_REPLICA_COUNT, 
					String.valueOf(numberOfReplica)));
		}
		if (this.numberOfExchanges>0)
			metadata.add(new MetadataAVU(MethodMetadata.REMD_EXCHANGE_COUNT, String.valueOf(this.numberOfExchanges)));
		
		return metadata;
	}

}

