/*
 * iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2013  Julien Thibault, University of Utah
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

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;

/**
 * Ab-initio MD task
 * @author Julien Thibault - University of Utah, BMI
 *
 */
@XmlRootElement(name="task")
public class QuantumMDTask extends QMTask {

	protected QuantumMDParameterSet aimdParamSet = null;
	protected MDParameterSet mdParameterSet = null;
	
	@SuppressWarnings(value = { "unused" })
	private QuantumMDTask() {
		super();
		this.methodType = ParameterSet.METHOD_QUANTUM_MD;
	}
	
	/**
	 * New QMD task
	 * @param qmParameters QM-specific method/settings
	 */
	public QuantumMDTask(QMParameterSet qmParameters) {
		super(qmParameters);
		this.methodType = ParameterSet.METHOD_QUANTUM_MD;
	}
	
	/**
	 * New QMD task
	 * @param qmParameters QM-specific QMD method/settings
	 * @param mdParameterSet MD-specific method/settings
	 * @param aimdParamSet QMD-specific method/settings
	 */
	public QuantumMDTask(QMParameterSet qmParameters, MDParameterSet mdParameterSet, QuantumMDParameterSet aimdParamSet) {
		super(qmParameters);
		this.methodType = ParameterSet.METHOD_QUANTUM_MD;
		this.aimdParamSet = aimdParamSet;
		this.mdParameterSet = mdParameterSet;
	}
	
	/**
	 * Get QMD-specific method and parameters
	 * @return QMD-specific method and parameters
	 */
	@XmlElement(name="parameterSet")
	public QuantumMDParameterSet getQMDParameterSet() {
		return aimdParamSet;
	}
	
	/**
	 * Set QMD-specific method and parameters
	 * @param aimdParamSet QMD-specific method and parameters
	 */
	public void setQMDParameterSet(QuantumMDParameterSet aimdParamSet) {
		this.aimdParamSet = aimdParamSet;
	}

	/**
	 * Get MD method and parameters
	 * @return MD method
	 */
	@XmlElement(name="parameterSet")
	public MDParameterSet getMDParameterSet() {
		return mdParameterSet;
	}
	
	/**
	 * Set MD method and parameters
	 * @param mdParameterSet
	 */
	public void setMDParameterSet(MDParameterSet mdParameterSet){
		this.mdParameterSet = mdParameterSet; 
	}
	
	@Override
	public List<ParameterSet> getParameterSets() {
		List<ParameterSet> methods = new ArrayList<ParameterSet>();
		methods.add(qmMethod);
		methods.add(aimdParamSet);
		methods.add(mdParameterSet);
		return methods;
	}

}
