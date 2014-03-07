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

package edu.utah.bmi.ibiomes.experiment.comp.min;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;

/**
 * Minimization task.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="task")
public class MinimizationTask extends ExperimentTask {

	private MinimizationParameterSet minimizationMethod;

	@SuppressWarnings(value = { "unused" })
	private MinimizationTask(){
		super();
	}	
	
	/**
	 * New Minimization task
	 * @param name Task name
	 * @param description Task description
	 * @param minimizationMethod Minimization methods/parameters
	 */
	public MinimizationTask(String name, String description, MinimizationParameterSet minimizationMethod) {
		super(name, description, ParameterSet.METHOD_MINIMIZATION);
		if (minimizationMethod != null)
		this.minimizationMethod = minimizationMethod;
	}
	
	/**
	 * New Minimization task
	 * @param minimizationMethod Minimization methods/parameters
	 */
	public MinimizationTask(MinimizationParameterSet minimizationMethod) {
		super(null, null, ParameterSet.METHOD_MINIMIZATION);
		if (minimizationMethod != null)
		this.minimizationMethod = minimizationMethod;
	}
	
	/**
	 * Get minimization method
	 * @return minimization method
	 */
	@XmlElement(name="parameterSet")
	public MinimizationParameterSet getMinimizationMethod(){
		return minimizationMethod;
	}
	
	@Override
	public List<ParameterSet> getParameterSets() {
		List<ParameterSet> methods = new ArrayList<ParameterSet>();
		if (minimizationMethod!=null)
			methods.add(minimizationMethod);
		return methods;
	}
	
	
}
