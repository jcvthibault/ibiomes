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
 * Platform metadata (hardware/software)
 * @author Julien Thibault
 *
 */
public class PlatformMetadata extends IbiomesDictionary {

	/**
	 * Software package name
	 */
	public static final String SOFTWARE_NAME = "SOFTWARE_NAME";
	/**
	 * Software package version
	 */
	public static final String SOFTWARE_VERSION = "SOFTWARE_VERSION";
	/**
	 * Software package name with version
	 */
	public static final String SOFTWARE_NAME_W_VERSION = "SOFTWARE_NAME_W_VERSION";
	/**
	 * Software executable name
	 */
	public static final String SOFTWARE_EXEC_NAME = "SOFTWARE_EXEC_NAME";
	
	/**
	 * Operating system family
	 */
	public static final String OPERATING_SYSTEM = "OP_SYS";
	/**
	 * Operating system distribution 
	 */
	public static final String OPERATING_SYSTEM_VERSION = "OP_SYS_VERSION";
	/**
	 * CPU architecture 
	 */
	public static final String CPU_ARCHITECTURE = "CPU_ARCHITECTURE";
	/**
	 * GPU architecture 
	 */
	public static final String GPU_ARCHITECTURE = "GPU_ARCHITECTURE";
	/**
	 * General machine/cluster architecture
	 */
	public static final String MACHINE_ARCHITECTURE = "MACHINE_ARCHITECTURE";
	/**
	 * Hardware make 
	 */
	public static final String HARDWARE_MAKE = "HARDWARE_MAKE";
	
	/**
	 * Program termination status (e.g. normal)
	 */
	public static final String PROGRAM_TERMINATION = "PROGRAM_TERMINATION";
	
	/**
	 * Number of CPUs used for a particular run
	 */
	public static final String NUMBER_CPUS = "NUMBER_CPUS";

	/**
	 * Number of GPUs used for a particular run
	 */
	public static final String NUMBER_GPUS = "NUMBER_GPUS";

	/**
	 * Resource domain (e.g. NICS Kraken, Utah CHPC)
	 */
	public static final String RESOURCE_DOMAIN = "RESOURCE_DOMAIN";
	/**
	 * Execution time
	 */
	public static final String EXECUTION_TIME = "EXECUTION_TIME";
	/**
	 * Task execution start timestamp
	 */
	public static final String TASK_START_TIMESTAMP = "TASK_START_TIMESTAMP";
	/**
	 * Task execution end timestamp
	 */
	public static final String TASK_END_TIMESTAMP = "TASK_END_TIMESTAMP";
		
	/**
	 * Get list of topology metadata attributes
	 * @return list of topology metadata attributes
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<String> getMetadataAttributes() throws IllegalArgumentException, IllegalAccessException {
		return PlatformMetadata.getMetadataAttributes(PlatformMetadata.class);
	}
}
