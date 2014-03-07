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

package edu.utah.bmi.ibiomes.experiment.comp.qm;

/**
 * Level of theory for Quantum Mechanics methods
 * @author Julien Thibault, University of Utah
 *
 */
public class LevelOfTheory {

	/** 
	 * Hartree-Fock (HF)
	 */
	public final static String QM_METHOD_HARTREE_FOCK = "Hartree-Fock";
	/** 
	 * Multi-configuration SCF (MC-SCF)
	 */
	public final static String QM_METHOD_MC_SCF = "Hartree-Fock";	
	/** 
	 * Configuration Interaction (CI)
	 */
	public final static String QM_METHOD_CI = "CI";
	/** 
	 * Configuration Interaction (CI)
	 */
	public final static String QM_METHOD_CISD = "CISD";
	/** 
	 * Configuration Interaction (CI)
	 */
	public final static String QM_METHOD_QCISD = "QCISD";
	/** 
	 * Configuration Interaction (CI)
	 */
	public final static String QM_METHOD_CISDT = "CISDT";
	/** 
	 * Configuration Interaction (CI)
	 */
	public final static String QM_METHOD_CISDTQ = "CISDTQ";
	/** 
	 * Configuration Interaction (CI)
	 */
	public final static String QM_METHOD_MRCISD = "MRCISD";
	
	//Coupled-Cluster methods
	public final static String QM_METHOD_CC = "CC";
	public final static String QM_METHOD_LCCD = "LCCD";
	public final static String QM_METHOD_CCD = "CC";
	public final static String QM_METHOD_LCCSD = "LCCSD";
	public final static String QM_METHOD_CCSD = "CCSD";
	public final static String QM_METHOD_LR_CCSD = "LR CCSD";
	public final static String QM_METHOD_CCSDT = "CCSDT";
	public final static String QM_METHOD_CCSD_T = "CCSD T";
	public final static String QM_METHOD_CCSD2 = "CCSD2";
	public final static String QM_METHOD_CCSDT2 = "CCSDT2";
	public final static String QM_METHOD_CCSDTQ = "CCSDTQ";
	public final static String QM_METHOD_LR_CCSDT = "LR_CCSDT";
	public final static String QM_METHOD_CR_CCSD_T = "CR_CCSD_T";
	public final static String QM_METHOD_MR_CCSD = "MR_CCSD";
		
	/** 
	 * Moeller-Plesset (MP)
	 */
	public final static String QM_METHOD_MP2 = "MP2";
	/** 
	 * Moeller-Plesset (MP)
	 */
	public final static String QM_METHOD_MP3 = "MP3";
	/** 
	 * Moeller-Plesset (MP)
	 */
	public final static String QM_METHOD_MP4 = "MP4";
	/** 
	 * Moeller-Plesset (MP)
	 */
	public final static String QM_METHOD_MP5 = "MP5";
		
	/** 
	 * Density Functional Theory (DFT)
	 */
	public final static String QM_METHOD_DFT = "DFT";
	/**
	 * Semi-empirical methods
	 */
	public final static String QM_METHOD_SEMI_EMPIRICAL = "Semi-empirical";
	/**
	 * Perturbation theory (Moeller-Plesset)
	 */
	public static final String QM_PERTURBATION_THEORY = "Perturbation theory";
	
	
	private String name;

	/**
	 * Get name of the level of theory
	 * @return Name of the level of theory
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name of the level of theory
	 * @param name Name of the level of theory
	 */
	public void setName(String name) {
		this.name = name;
	}
}
