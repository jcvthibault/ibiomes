package edu.utah.bmi.ibiomes.metadata;

import java.util.List;

import edu.utah.bmi.ibiomes.dictionaries.IbiomesDictionary;

public class CalculationMetadata extends IbiomesDictionary {

	/**
	 * Total energy of system after minimization
	 */
	public static final String ENERGY_TOTAL_MINIMIZATION = "ENERGY_TOTAL_MINIMIZATION";

	/**
	 * Total energy for the system after final time step
	 */
	public static final String ENERGY_TOTAL_FINAL = "MD_ENERGY_TOTAL_FINAL";

	/**
	 * Total energy for the system averaged over time
	 */
	public static final String ENERGY_TOTAL_AVERAGE = "MD_ENERGY_TOTAL_AVERAGE";

	/**
	 * Kinetic energy for the system after final time step
	 */
	public static final String ENERGY_KINETIC_FINAL = "MD_ENERGY_KINETIC_FINAL";

	/**
	 * Kinetic energy for the system averaged over time
	 */
	public static final String ENERGY_KINETIC_AVERAGE = "MD_ENERGY_KINETIC_AVERAGE";

	/**
	 * Potential energy for the system after final time step
	 */
	public static final String ENERGY_POTENTIAL_FINAL = "MD_ENERGY_POTENTIAL_FINAL";

	/**
	 * Potential energy for the system averaged over time
	 */
	public static final String ENERGY_POTENTIAL_AVERAGE = "MD_ENERGY_POTENTIAL_AVERAGE";
	
	/**
	 * Get list of calculation metadata attributes
	 * @return list of calculation metadata attributes
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<String> getMetadataAttributes() throws IllegalArgumentException, IllegalAccessException {
		return CalculationMetadata.getMetadataAttributes(CalculationMetadata.class);
	}
}
