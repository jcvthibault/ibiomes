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
 * Method metadata
 * @author Julien Thibault
 *
 */
public class MethodMetadata extends IbiomesDictionary {

	/**
	 * Simulation method (MD, QM, Monte-Carlo, etc.)
	 */
	public static final String COMPUTATIONAL_METHOD_NAME = "METHOD";
	/**
	 * Method description
	 */
	public static final String COMPUTATIONAL_METHOD_DESCRIPTION = "METHOD_DESCRIPTION";
	/**
	 * Boundary conditions
	 */
	public static final String BOUNDARY_CONDITIONS = "BOUNDARY_CONDITIONS";
	
	/**
	 * Reference temperature (in Kelvins) at which the system is maintained
	 */
	public static final String REFERENCE_TEMPERATURE = "REFERENCE_TEMPERATURE";
	/**
	 * Initial temperature of the system
	 */
	public static final String INITIAL_TEMPERATURE = "INITIAL_TEMPERATURE";

	/**
	 * Reference pressure (in bars) at which the system is maintained
	 */
	public static final String REFERENCE_PRESSURE = "REFERENCE_PRESSURE";
	/**
	 * Initial pressure of the system
	 */
	public static final String INITIAL_PRESSURE = "INITIAL_PRESSURE";
	/**
	 * Apparent pH
	 */
	public static final String APPARENT_PH = "APPARENT_PH";
	/**
	 * Calculation types (e.g. dynamics, optimization, frequency, NMR)
	 */
	public static final String CALCULATION = "CALCULATION";
	
	/* ============================ MOLECULAR DYNAMICS METADATA ===================================== */
	/**
	 * Force field
	 */
	public static final String FORCE_FIELD = "FORCE_FIELD";
	/**
	 * Type of solvent
	 */
	public static final String SOLVENT_TYPE = "SOLVENT";
	/**
	 * Implicit solvent model
	 */
	public static final String IMPLICIT_SOLVENT_MODEL = "IMPLICIT_SOLVENT_MODEL";
	/**
	 * Length of time-step
	 */
	public static final String TIME_STEP_LENGTH = "TIME_STEP_LENGTH";
	/**
	 * Number of time steps executed
	 */
	public static final String TIME_STEP_COUNT = "TIME_STEP_COUNT";
	/**
	 * Simulated time (in micro-seconds)
	 */
	public static final String SIMULATED_TIME = "TIME_LENGTH";
	/**
	 * Whether the system has constraints or not
	 */
	public static final String HAS_CONSTRAINTS = "HAS_CONSTRAINTS";	
	/**
	 * Constraint algorithm (SHAKE, RATTLE...)
	 */
	public static final String CONSTRAINT_ALGORITHM = "CONSTRAINT_ALGORITHM";
	/**
	 * Constraint target (e.g. hydrogen bonds)
	 */
	public static final String CONSTRAINT_TARGET = "CONSTRAINT_TARGET";
	/**
	 * Restraint type
	 */
	public static final String RESTRAINT_TYPE = "RESTRAINT_TYPE";
	/**
	 * Restraint target
	 */
	public static final String RESTRAINT_TARGET = "RESTRAINT_TARGET";
	/**
	 * Whether the system has restrains or not
	 */
	public static final String HAS_RESTRAINTS = "HAS_RESTRAINTS";
	/**
	 * Electrostatics modeling (Particle-Mesh Ewald, cutoff)
	 */
	public static final String ELECTROSTATICS_MODELING = "ELECTROSTATICS";
	/**
	 * PME interpolation order
	 */
	public static final String PME_INTERPOLATION = "PME_INTERPOLATION";
	/**
	 * PME Ewald coefficient
	 */
	public static final String PME_EWALD_COEFFICIENT = "PME_EWALD_COEFFICIENT";
	/**
	 * Ensemble modeling
	 */
	public static final String ENSEMBLE_MODELING = "ENSEMBLE";
	
	/**
	 * Thermostat algorithm (temperature regulation)
	 */
	public static final String THERMOSTAT_ALGORITHM = "THERMOSTAT_ALGORITHM";
	/**
	 * Thermostat time constant
	 */
	public static final String THERMOSTAT_TIME_CONSTANT = "THERMOSTAT_TIME_CONSTANT";
	/**
	 * Barostat algorithm (e.g. Berendsen)
	 */
	public static final String BAROSTAT_ALGORITHM = "BAROSTAT_ALGORITHM";
	/**
	 * Barostat type (e.g. isotropic, anisotropic)
	 */
	public static final String BAROSTAT_TYPE = "BAROSTAT_TYPE";
	/**
	 * Barostat time constant
	 */
	public static final String BAROSTAT_TIME_CONSTANT = "BAROSTAT_TIME_CONSTANT";
	
	/**
	 * Unit shape (cubic box, sphere, octahedron, etc.)
	 */
	public static final String UNIT_SHAPE = "UNIT_SHAPE";
	/**
	 * Molecular mechanics integrator.
	 */
	public static final String MM_INTEGRATOR = "MM_INTEGRATOR";
	/**
	 * MD sampling method 
	 */
	public static final String ENHANCED_SAMPLING_METHOD_NAME = "ENHANCED_SAMPLING_METHOD_NAME";
	/**
	 * Number of replicas used for the sampling method 
	 */
	public static final String ENHANCED_SAMPLING_METHOD_REPLICA_COUNT = "ENHANCED_SAMPLING_METHOD_REPLICA_COUNT";
	/**
	 * Number of exchanges between replicas for the sampling method 
	 */
	public static final String REMD_EXCHANGE_COUNT = "REMD_EXCHANGE_COUNT";
	/**
	 * Random seed for Langevin Dynamics
	 */
	public static final String LANGEVIN_RANDOM_SEED = "LANGEVIN_RANDOM_SEED";
	/**
	 * Friction coefficient for Langevin Dynamics
	 */
	public static final String LANGEVIN_COLLISION_FREQUENCY = "LANGEVIN_COLLISION_FREQUENCY";
	/**
	 *Noise term amplitude for Stochastics Dynamics
	 */
	public static final String STOCHASTICS_NOISE_TERM_AMPLITUDE = "STOCHASTICS_NOISE_TERM_AMPLITUDE";
	/**
	 * MD minimization method (conjugate gradient, steepest descent, etc.)
	 */
	public static final String MD_MINIMIZATION = "MD_MINIMIZATION";
	/**
	 * Cutoff for electrostatics calculations (in Angstroms)
	 */
	public static final String CUTOFF_ELECTROSTATICS = "CUTOFF_ELECTROSTATICS";
	/**
	 * Cutoff for Van der Waals calculations (in Angstroms)
	 */
	public static final String CUTOFF_VAN_DER_WAALS = "CUTOFF_VAN_DER_WAALS";

	
	/* ============================ QUANTUM-MECHANICS METADATA ===================================== */
	
	/**
	 * Spin multiplicity (spin multiplicity is given by 2S+1, where S is the total electron spin for the molecule).
	 */
	public static final String QM_SPIN_MULTIPLICITY = "QM_SPIN_MULTIPLICITY";
	
	/**
	 * Specific name of the QM method (e.g. MP2, B3LYP, SCF)
	 */
	public static final String QM_METHOD_NAME = "QM_METHOD_NAME";
	/**
	 * Level of theory in QM calculations (e.g. Moeller-Plesset, Hartree-Fock, DFT)
	 */
	public static final String QM_LEVEL_OF_THEORY = "QM_LEVEL_OF_THEORY";
	
	/**
	 * Exchange functional (for DFT methods mainly)
	 */
	public static final String QM_EXCHANGE_FUNCTIONAL = "QM_EXCHANGE_FUNCTIONAL";
	
	/**
	 * Correlation functional (for DFT methods mainly)
	 */
	public static final String QM_CORRELATION_FUNCTIONAL = "QM_CORRELATION_FUNCTIONAL";

	/**
	 * Exchange-correlation functional (for DFT methods mainly)
	 */
	public static final String QM_EXCHANGE_CORRELATION = "QM_EXCHANGE_CORRELATION";
	/**
	 * Basis set
	 */
	public static final String QM_BASIS_SET = "QM_BASIS_SET";
	/**
	 * Basis set family
	 */
	public static final String QM_BASIS_SET_FAMILY = "QM_BASIS_SET_FAMILY";
	/**
	 * Alpha spin electron count
	 */
	public static final String COUNT_ELECTRON_APLHA = "COUNT_ELECTRON_ALPHA";
	/**
	 * Beta spin electron count
	 */
	public static final String COUNT_ELECTRON_BETA = "COUNT_ELECTRON_BETA";
	/**
	 * Total electron count
	 */
	public static final String COUNT_ELECTRON = "COUNT_ELECTRON";

	/* ============================ QM/MM METADATA ===================================== */

	/**
	 * Electrostatics model in QM region for QM/MM
	 */
	public static final String QM_ELECTROSTATICS_MODELING = "QM_ELECTROSTATICS_MODELING";
	/**
	 * QM region definition for QM/MM
	 */
	public static final String QM_REGION_DEFINITION = "QM_REGION_DEFINITION";
	/**
	 * Treatment of QM/MM boundary
	 */
	public static final String QMMM_BOUNDARY_TREATMENT = "QMMM_BOUNDARY_TREATMENT";
	
	/* ============================ QUANTUM MD METADATA ===================================== */
	
	/**
	 * Quatum MD method (e.g. Born-Oppenheimer MD)
	 */
	public static final String QMD_METHOD = "QMD_METHOD";
	

	/**
	 * Get list of method metadata attributes
	 * @return list of topology metadata attributes
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<String> getMetadataAttributes() throws IllegalArgumentException, IllegalAccessException {
		return MethodMetadata.getMetadataAttributes(MethodMetadata.class);
	}
}
