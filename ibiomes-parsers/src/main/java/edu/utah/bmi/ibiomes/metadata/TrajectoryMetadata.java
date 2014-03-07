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
 * Trajectory metadata
 * @author Julien Thibault
 *
 */
public class TrajectoryMetadata extends IbiomesDictionary {

	/**
	 * Total number of time frames in the simulation
	 */
	public static final String TIME_STEP_COUNT = "TIME_STEP_COUNT";
	/**
	 * Specifies the first time frame that is represented by the associated averaged structure or trajectory.
	 */
	public static final String TIME_STEP_START = "TIME_STEP_START";
	/**
	 * Specifies the last time frame that is represented by the associated averaged structure or trajectory.
	 */
	public static final String TIME_STEP_END = "TIME_STEP_END";

	/**
	 * Get list of trajectory metadata attributes
	 * @return list of topology metadata attributes
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<String> getMetadataAttributes() throws IllegalArgumentException, IllegalAccessException {
		return TrajectoryMetadata.getMetadataAttributes(TrajectoryMetadata.class);
	}
}
