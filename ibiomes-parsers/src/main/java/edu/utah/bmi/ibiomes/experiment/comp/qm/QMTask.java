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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;

/**
 * Molecular dynamics task.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="task")
public class QMTask extends ExperimentTask {

	protected QMParameterSet qmMethod;

	protected QMTask(){
		super();
	}	
	
	/**
	 * New QM task
	 * @param name Task name
	 * @param description Task description
	 * @param method QM method/parameters
	 */
	public QMTask(String name, String description, QMParameterSet method) {
		super(name, description, ParameterSet.METHOD_QM);
		this.qmMethod = method;
	}

	/**
	 * New QM task
	 * @param method QM method/parameters
	 */
	public QMTask(QMParameterSet method) {
		super(null, null, ParameterSet.METHOD_QM);
		this.qmMethod = method;
	}

	/**
	 * Get QM method and parameters
	 * @return MD method
	 */
	@XmlElement(name="parameterSet")
	public QMParameterSet getQMMethod() {
		return qmMethod;
	}
	
	@Override
	public List<ParameterSet> getParameterSets() {
		List<ParameterSet> methods = new ArrayList<ParameterSet>();
		methods.add(qmMethod);
		return methods;
	}
}
