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

/**
 * Sampling methods for Molecular Dynamics
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class SamplingMethod implements MetadataMappable {
	
	/**
	 * Umbrella sampling
	 */
	public static final String UMBRELLA_SAMPLING = "Umbrella sampling";
	/**
	 * Replica exchange
	 */
	public static final String REMD = "Replica exchange";
	/**
	 * Hamiltonian replica-exchange
	 */
	public static final String REMD_HAMILTONIAN = "Hamiltonian replica exchange";
	/**
	 * Temperature replica-exchange
	 */
	public static final String REMD_TEMPERATURE	= "Temperature replica exchange";
	/**
	 * Multi-dimension replica-exchange
	 */
	public static final String REMD_MULTI_DIMENSION = "Multi-dimension replica exchange";
	/**
	 * Metadynamics
	 */
	public static final String METADYNAMICS = "Metadynamics";
	/**
	 * Adaptive Force Bias
	 */
	public static final String ADAPTIVE_FORCE_BIAS = "Adaptive Force Bias";
	/**
	 * Accelerated MD
	 */
	public static final String ACCELERATED_MD = "Accelerated MD";
	/**
	 * Swarm-of-trajectories
	 */
	public static final String SWARM_OF_TRAJECTORIES = "Swarm-of-trajectories";
	/**
	 * Neural networks
	 */
	public static final String NEURAL_NETWORKS = "Neural networks";
	/**
	 * Markov chain model
	 */
	public static final String MARKOV_CHAIN = "Markov chain model";
	/**
	 * Multi-state Bennett Acceptance Ratio (MBAR)
	 */
	public static final String MBAR = "Multi-state Bennett Acceptance Ratio";
	/**
	 * Steered dynamics
	 */
	public static final String STEERED_DYNAMICS = "Steered dynamics";
	/**
	 * Nudge elastic band
	 */
	public static final String NUDGE_ELASTIC_BAND = "Nudge elastic band";
	
	
	private String name;
	
	public SamplingMethod(){
	}

	/**
	 * Constructor
	 * @param name Sampling method name
	 */
	public SamplingMethod(String name){
		this.name = name;
	}
	
	/**
	 * Get sampling method name
	 * @return Sampling method name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set sampling method name
	 * @param name Sampling method name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get metadata
	 */
	public MetadataAVUList getMetadata() {
		MetadataAVUList metadata = new MetadataAVUList();
		if (this.name!=null && this.name.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.ENHANCED_SAMPLING_METHOD_NAME, this.name));
		return metadata;
	}	
}
