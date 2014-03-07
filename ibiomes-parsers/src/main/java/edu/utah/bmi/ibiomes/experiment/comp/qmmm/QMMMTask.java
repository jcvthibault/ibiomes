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

package edu.utah.bmi.ibiomes.experiment.comp.qmmm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;

/**
 * Molecular dynamics task.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="task")
public class QMMMTask extends ExperimentTask {

	private MDParameterSet mdParameterSet;
	private QMParameterSet qmParameterSet;
	private QMMMParameterSet qmmmParameterSet;

	@SuppressWarnings(value = { "unused" })
	private QMMMTask(){
		super();
	}	
	/**
	 * New QM/MM task
	 * @param name Task name
	 * @param description Task description
	 * @param mdParameterSet MD method/parameters
	 * @param qmParameterSet QM method/parameters
	 * @param qmmmParameterSet QM/MM method/parameters
	 */
	public QMMMTask(String name, String description, MDParameterSet mdParameterSet, QMParameterSet qmParameterSet, QMMMParameterSet qmmmParameterSet) {
		super(name, description, ParameterSet.METHOD_QMMM);
		this.mdParameterSet = mdParameterSet;
		this.qmParameterSet = qmParameterSet;
		this.qmmmParameterSet = qmmmParameterSet;
	}
	
	/**
	 * New QM/MM task
	 * @param mdParameterSet MD method/parameters
	 * @param qmParameterSet QM method/parameters
	 * @param qmmmParameterSet QM/MM method/parameters
	 */
	public QMMMTask(MDParameterSet mdParameterSet, QMParameterSet qmParameterSet, QMMMParameterSet qmmmParameterSet) {
		super(null, null, ParameterSet.METHOD_QMMM);
		this.mdParameterSet = mdParameterSet;
		this.qmParameterSet = qmParameterSet;
		this.qmmmParameterSet = qmmmParameterSet;
	}
	/**
	 * Get MD method and parameters
	 * @return MD method
	 */
	@XmlElement(name="parameterSet")
	public MDParameterSet getMdParameterSet() {
		return mdParameterSet;
	}
	
	/**
	 * Get QM method and parameters
	 * @return QM method
	 */
	@XmlElement(name="parameterSet")
	public QMParameterSet getQmParameterSet() {
		return qmParameterSet;
	}
	
	/**
	 * Get QM/MM method and parameters
	 * @return QM/MM method
	 */
	@XmlElement(name="parameterSet")
	public QMMMParameterSet getQmmmParameterSet() {
		return qmmmParameterSet;
	}
	
	@Override
	public List<ParameterSet> getParameterSets() {
		List<ParameterSet> methods = new ArrayList<ParameterSet>();
		methods.add(mdParameterSet);
		methods.add(qmParameterSet);
		methods.add(qmmmParameterSet);
		return methods;
	}
}
