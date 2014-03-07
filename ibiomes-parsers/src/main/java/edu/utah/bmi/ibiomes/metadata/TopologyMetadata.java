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

import java.util.List;

import edu.utah.bmi.ibiomes.dictionaries.IbiomesDictionary;

/**
 * Topology metadata
 * @author Julien Thibault
 *
 */
public class TopologyMetadata extends IbiomesDictionary {

	/**
	 * Molecule type (protein, DN, RNA, etc.)
	 */
	public static final String MOLECULE_TYPE = "MOLECULE_TYPE";
	/**
	 * Molecule that is part of the solvent (e.g. H2O)
	 */
	public static final String SOLVENT_MOLECULE = "SOLVENT_MOLECULE";
	/**
	 * Name/description of the system
	 */
	public static String MOLECULAR_SYSTEM_DESCRIPTION = "MOLECULAR_SYSTEM_DESCRIPTION";
	/**
	 * Name/description of the system
	 */
	public static String MOLECULE_DESCRIPTION = "MOLECULE_DESCRIPTION";
	/**
	 * Total molecule charge.
	 */
	public static final String TOTAL_MOLECULE_CHARGE = "TOTAL_MOLECULE_CHARGE";
	/**
	 * Residue chain
	 */
	public static final String RESIDUE_CHAIN = "RESIDUE_CHAIN";
	/**
	 * Normalized residue chain
	 */
	public static final String RESIDUE_CHAIN_NORM = "RESIDUE_CHAIN_NORM";
	/**
	 * Non-standard residue
	 */
	public static final String RESIDUE_NON_STD = "RESIDUE_NON_STD";
	/**
	 * Number of atoms
	 */
	public static final String COUNT_ATOMS = "COUNT_ATOM";
	/**
	 * Number of water molecules
	 */
	public static final String COUNT_SOLVENT = "COUNT_SOLVENT";
	/**
	 * Number of ions
	 */
	public static final String COUNT_IONS = "COUNT_ION";
	/**
	 * Type of ion (e.g. Na+, K+)
	 */
	public static final String ION_TYPE = "ION_TYPE";
	/**
	 * Atomic weight of the molecule
	 */
	public static final String MOLECULE_ATOMIC_WEIGHT = "MOLECULE_ATOMIC_WEIGHT";
	/**
	 * Atomic composition (syntax: 'elt1:n1 elt2:n2 ...')
	 */
	public static final String MOLECULE_ATOMIC_COMPOSITION = "MOLECULE_ATOMIC_COMPOSITION";
	/**
	 * Atomic composition (compact format: 'elt1[n1]elt2[n2] ...')
	 */
	public static final String MOLECULE_ATOMIC_COMPOSITION2 = "MOLECULE_ATOMIC_COMPOSITION2";
	/**
	 * Chemical formula
	 */
	public static final String CHEMICAL_FORMULA = "CHEMICAL_FORMULA";
	/**
	 * Atom chain
	 */
	public static final String ATOM_CHAIN = "ATOM_CHAIN";
	/**
	 * Structure reference ID. Format: 'database:ID' (e.g., pdb:1lMVI, pubchem:449459).
	 */
	public static final String STRUCTURE_REF_ID	= "STRUCTURE_REF_ID";
	/**
	 * Spatial dimensions (e.g. '3' if 3D coordinates)
	 */
	public static final String SPATIAL_DIMENSIONS = "SPATIAL_DIMENSIONS";
	
	/* ============================ STRUCTURE METADATA ===================================== */
	
	/**
	 * Specifies if the structure is averaged or just a snapshot
	 */
	public static final String STRUCTURE_IS_AVERAGED = "STRUCTURE_IS_AVERAGED";
	/**
	 * Specifies which clustering algorithm was used to generate the structure
	 */
	public static final String STRUCTURE_FROM_CLUSTER = "STRUCTURE_CLUSTER_ALGORITM";
	/**
	 * Specifies if the structure is the result of a simulation or an experiment.
	 */
	public static final String STRUCTURE_IS_SIMULATED = "STRUCTURE_IS_SIMULATED";
	
	/**
	 * Get list of topology metadata attributes
	 * @return list of topology metadata attributes
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<String> getMetadataAttributes() throws IllegalArgumentException, IllegalAccessException {
		return TopologyMetadata.getMetadataAttributes(TopologyMetadata.class);
	}
}
