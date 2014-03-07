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

package edu.utah.bmi.ibiomes.experiment;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;

/**
 * Generic task.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="task")
public class GenericTask extends ExperimentTask {

	private ParameterSet method;

	@SuppressWarnings(value = { "unused" })
	private GenericTask(){
		super();
	}	
	
	/**
	 * New generic task
	 * @param name Task name
	 * @param description Task description
	 * @param method Method/parameters
	 */
	public GenericTask(String name, String description, ParameterSet method) {
		super(name, description, method.getName());
		this.method = method;
	}

	/**
	 * New generic task
	 * @param method Method/parameters
	 */
	public GenericTask(ParameterSet method) {
		super(null, null, method.getName());
		this.method = method;
	}

	/**
	 * Get method and parameters
	 * @return Method
	 */
	@XmlElement(name="parameterSet")
	public ParameterSet getMethod() {
		return method;
	}

	@Override
	public List<ParameterSet> getParameterSets() {
		List<ParameterSet> methods = new ArrayList<ParameterSet>();
		methods.add(method);
		return methods;
	}
}
