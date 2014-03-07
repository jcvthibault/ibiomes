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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Constraints (fixed values). Requirements (e.g bond length, angle) that the system is forced to satisfy.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class Constraint implements MetadataMappable {

	/**
	 *  SHAKE
	 */
	public final static String SHAKE = "SHAKE";
	/**
	 *  M-SHAKE
	 */
	public final static String M_SHAKE = "M-SHAKE";
	/**
	 *  LINCS
	 */
	public final static String LINCS = "LINCS";
	/**
	 *  RATTLE
	 */
	public final static String RATTLE = "RATTLE";
	/**
	 *  SETTLE
	 */
	public final static String SETTLE = "SETTLE";
	/**
	 *  SHAPE
	 */
	public final static String SHAPE = "SHAPE";
	/**
	 *  Constraint on all bonds
	 */
	public final static String TARGET_ALL_BONDS = "All bonds";
	/**
	 *  Constraint on bonds to hydrogen
	 */
	public final static String TARGET_BONDS_TO_HYDROGEN = "Bonds to hydrogen";
	/**
	 *  Constraint on bonds to hydrogen in QM partition (for QM/MM only)
	 */
	public final static String TARGET_BONDS_TO_HYDROGEN_QM = "Bonds to hydrogen in QM region";
	
	private String algorithm;
	private String target;

	public Constraint(){
		
	}
	
	/**
	 * Constructor
	 * @param algorithm Constraint algorithm (e.g. SHAKE)
	 */
	public Constraint(String algorithm){
		this.algorithm = algorithm;
	}
	
	/**
	 * Get constraint algorithm (e.g. SHAKE)
	 * @return Constraint algorithm (e.g. SHAKE)
	 */
	@XmlAttribute
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * Set constraint algorithm (e.g. SHAKE)
	 * @param algorithm Constraint algorithm (e.g. SHAKE)
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * Get constraint target
	 * @return Constraint target
	 */
	@XmlAttribute
	public String getTarget() {
		return target;
	}
	
	/**
	 * Set constraint target
	 * @param target Constraint target
	 */
	public void setTarget(String target){
		this.target = target;
	}
	
	/**
	 * Get constraint metadata
	 * @return Metadata (AVU list)
	 */
	public MetadataAVUList getMetadata()
	{		
		MetadataAVUList metadata = new MetadataAVUList();
		metadata.add(new MetadataAVU(MethodMetadata.CONSTRAINT_ALGORITHM, this.algorithm));
		if (this.target!=null && this.target.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.CONSTRAINT_TARGET, this.target));
		return metadata;
	}
}
