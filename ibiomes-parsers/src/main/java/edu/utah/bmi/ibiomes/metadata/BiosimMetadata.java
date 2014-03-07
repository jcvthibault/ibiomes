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

package edu.utah.bmi.ibiomes.metadata;

/**
 * Metadata attribute-value pair used to describe biomolecular simulation-derived files.
 * @author Julien Thibault
 *
 */
@Deprecated
public class BiosimMetadata {

	/**
	 * Prefix used for biomolecular simulation related metadata names 
	 */
	public static final String BIOSIM_PREFIX = "";
	

	/* ============================ QUANTUM-MECHANICS METADATA ===================================== */
	
	

	/* ============================ BASIS SET METADATA ===================================== */
	
	/**
	 * Minimal basis set family
	 */
	public static final String QM_BASIS_SET_MINIMAL = BIOSIM_PREFIX + "QM_BASIS_SET_MINIMAL";
	/**
	 * Pople basis set family
	 */
	public static final String QM_BASIS_SET_POPLE = BIOSIM_PREFIX + "QM_BASIS_SET_POPLE";
	/**
	 * Correlation-consistent basis set family
	 */
	public static final String QM_BASIS_SET_CORR_CONSIST = BIOSIM_PREFIX + "QM_BASIS_SET_CORR_CONSIST";
	/**
	 * Other split-valence basis sets
	 */
	public static final String QM_BASIS_SET_SPLIT_VALENCE = BIOSIM_PREFIX + "QM_BASIS_SET_SPLIT_VALENCE";
	/**
	 * Other basis sets
	 */
	public static final String QM_BASIS_SET_OTHER = BIOSIM_PREFIX + "QM_BASIS_SET_OTHER";
	
	
	/* ============================ FILE/DIRECTOY METADATA ===================================== */
	/**
	 * Type of file (trajectory, topology, etc.)
	 */
	public static final String IBIOMES_FILE_TYPE = BIOSIM_PREFIX + "FILE_TYPE";
	/**
	 * File
	 */
	public static final String FILE = "FILE";
	/**
	 * Directory representing an experiment
	 */
	public static final String DIRECTORY_EXPERIMENT = "EXPERIMENT";
	/**
	 * Directory representing a process group
	 */
	public static final String DIRECTORY_EXPERIMENT_PROCESS_GROUP = "EXPERIMENT_PROCESS_GROUP";
	/**
	 * Directory representing a process
	 */
	public static final String DIRECTORY_EXPERIMENT_PROCESS = "EXPERIMENT_PROCESS";
	
	
	private String _attribute;
	private String _value;
	
	public String getAttribute() {
		return _attribute;
	}
	public String getValue() {
		return _value;
	}
	
	/**
	 * Create new simulation metadata attribute-value pair
	 * @param attribute
	 * @param value
	 */
	public BiosimMetadata(String attribute, String value){
		_attribute = attribute;
		_value = value;
	}
	
	/**
	 * Convert object to string array
	 * @return Metadata record as a string array
	 */
	public String[] toArray(){
		return new String[]{_attribute, _value};
	}
	
}
