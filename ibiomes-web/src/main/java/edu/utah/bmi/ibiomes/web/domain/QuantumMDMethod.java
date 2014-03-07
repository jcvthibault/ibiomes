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

package edu.utah.bmi.ibiomes.web.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;

/**
 * Quantum molecular dynamics access object
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="method")
public class QuantumMDMethod extends Method {

	private List<String> quantumMDMethods;
	
	private List<String> levelsOfTheory;
	private List<String> methodNames;
	private List<String> basisSets;
	private int[] spinMultiplicities;
	private double[] totalCharges;
	private List<String> exchangeCorrelationFns;
	private List<String> calculationTypes;

	private List<String> forceFields;
	private List<String> restraints;
	private List<String> unitShapes;
	private List<String> thermostats;
	private List<String> minimizations;
	private List<String> barostats;
	private List<String> ensembles;
	private List<String> electrostatics;
	private List<String> integrators;
	private List<String> samplingMethods;
	private double simulatedTime;
	private double timeStepLength;
	
	/**
	 * Constructor
	 */
	public QuantumMDMethod(){
		this.name = Method.METHOD_QUANTUM_MD;
		this.timeDependent = true;
	}

	/**
	 * Get QMD method names
	 * @return QMD method names
	 */
	public List<String> getQuantumMDMethods() {
		return quantumMDMethods;
	}

	/**
	 * Set QMD method names
	 * @param quantumMDMethods QMD method names
	 */
	public void setQuantumMDMethods(List<String> quantumMDMethods) {
		this.quantumMDMethods = quantumMDMethods;
	}
	
	/**
	 * Get minimization methods
	 * @return Minimization methods
	 */
	public List<String> getMinimizations() {
		return minimizations;
	}

	/**
	 * Set minimization methods
	 * @param minimizations Minimization methods
	 */
	public void setMinimizations(List<String> minimizations) {
		this.minimizations = minimizations;
	}

	
	/**
	 * Get list of force fields
	 * @return List of force fields
	 */
	public List<String> getForceFields() {
		return forceFields;
	}

	/**
	 * Set list of force fields
	 * @param forceFields List of force fields
	 */
	public void setForceFields(List<String> forceFields) {
		this.forceFields = forceFields;
	}

	/**
	 * Get molecular mechanics integrators
	 * @return Molecular mechanics integrators
	 */
	public List<String> getIntegrators() {
		return integrators;
	}

	/**
	 * Set molecular mechanics integrator.
	 * @param integrator Molecular mechanics integrator
	 */
	public void setIntegrators(List<String> integrators) {
		this.integrators = integrators;
	}

	/**
	 * Get unit shape
	 * @return Unit shape
	 */
	public List<String> getUnitShapes() {
		return unitShapes;
	}

	/**
	 * Set unit shapes
	 * @param unitShape Unit shapes
	 */
	public void setUnitShapes(List<String> unitShapes) {
		this.unitShapes = unitShapes;
	}

	/**
	 * Get thermostat
	 * @return Thermostat
	 */
	public List<String> getThermostats() {
		return thermostats;
	}

	/**
	 * Set thermostats
	 * @param thermostats Thermostats
	 */
	public void setThermostats(List<String> thermostats) {
		this.thermostats = thermostats;
	}

	/**
	 * Get barostats
	 * @return Barostats
	 */
	public List<String> getBarostats() {
		return barostats;
	}

	/**
	 * Set the barostat
	 * @param barostat Barostat
	 */
	public void setBarostats(List<String> barostats) {
		this.barostats = barostats;
	}

	/**
	 * Get ensemble types
	 * @return Ensemble types
	 */
	public List<String> getEnsembles() {
		return ensembles;
	}

	/**
	 * Set ensemble type
	 * @param ensemble Ensemble type
	 */
	public void setEnsembles(List<String> ensembles) {
		this.ensembles = ensembles;
	}

	/**
	 * Get electrostatics model.
	 * @return Electrostatics model.
	 */
	public List<String> getElectrostatics() {
		return electrostatics;
	}

	/**
	 * Set electrostatics models
	 * @param electrostatics Electrostatics models
	 */
	public void setElectrostatics(List<String> electrostatics) {
		this.electrostatics = electrostatics;
	}

	/**
	 * Get simulated time
	 * @return Simulated time
	 */
	public double getSimulatedTime() {
		return simulatedTime;
	}

	/**
	 * Set simulated time
	 * @param simulatedTime Simulated time
	 */
	public void setSimulatedTime(double simulatedTime) {
		this.simulatedTime = simulatedTime;
	}

	/**
	 * Get time step length
	 * @return Time step length
	 */
	public double getTimeStepLength() {
		return timeStepLength;
	}

	/**
	 * Get sampling method
	 * @return sampling method
	 */
	public List<String> getSamplingMethods() {
		return samplingMethods;
	}

	/**
	 * Set sampling method
	 * @param samplingMethod Sampling method
	 */
	public void setSamplingMethods(List<String> samplingMethods) {
		this.samplingMethods = samplingMethods;
	}
	
	/**
	 * Set time step length
	 * @param timeStepLength Time step length
	 */
	public void setTimeStepLength(double timeStepLength) {
		this.timeStepLength = timeStepLength;
	}


	public List<String> getRestraints() {
		return restraints;
	}

	public void setRestraints(List<String> restraints) {
		this.restraints = restraints;
	}

	/**
	 * Get level of theory
	 * @return Level of theory
	 */
	public List<String> getLevelsOfTheory() {
		return levelsOfTheory;
	}

	/**
	 * Set the level of theory
	 * @param levelsOfTheory Level of theory
	 */
	public void setLevelsOfTheory(List<String> levelsOfTheory) {
		this.levelsOfTheory = levelsOfTheory;
	}

	public List<String> getMethodNames() {
		return methodNames;
	}

	public void setMethodNames(List<String> methodNames) {
		this.methodNames = methodNames;
	}
	
	/**
	 * Get basis sets
	 * @return Basis sets
	 */
	public List<String> getBasisSets() {
		return basisSets;
	}

	/**
	 * Set list of basis sets
	 * @param basisSets Basis sets
	 */
	public void setBasisSets(List<String> basisSets) {
		this.basisSets = basisSets;
	}

	/**
	 * Get spin multiplicites
	 * @return Spin multiplicities
	 */
	public int[] getSpinMultiplicities() {
		return spinMultiplicities;
	}

	/**
	 * Set spin multiplicities
	 * @param spinMultiplicity Spin multiplicities
	 */
	public void setSpinMultiplicities(int[] spinMultiplicities) {
		this.spinMultiplicities = spinMultiplicities;
	}
	
	/**
	 * Get exchange correlational function
	 * @return Exchange correlational function
	 */
	public List<String> getExchangeCorrelationFns() {
		return exchangeCorrelationFns;
	}

	/**
	 * Set exchange correlational functions
	 * @param exchangeCorrelationFns Exchange correlational functions
	 */
	public void setExchangeCorrelationFns(List<String> exchangeCorrelationFns) {
		this.exchangeCorrelationFns = exchangeCorrelationFns;
	}

	/**
	 * Get total charges
	 * @return Total charges
	 */
	public double[] getTotalCharges() {
		return totalCharges;
	}

	/**
	 * Set total charge
	 * @param totalCharge Total charge
	 */
	public void setTotalCharges(double[] totalCharges) {
		this.totalCharges = totalCharges;
	}
	
	/**
	 * Get list of calculation types
	 * @return List of calculation types
	 */
	public List<String> getCalculationTypes() {
		return calculationTypes;
	}

	/**
	 * Set list of calculation types
	 * @param calculations List of calculation types
	 */
	public void setCalculationTypes(List<String> calculations) {
		this.calculationTypes = calculations;
	}
	
	/**
	 * Construct QM/MM method based on a list of iBIOMES metadata
	 * @param metadata List of iBIOMES metadata
	 */
	public QuantumMDMethod(MetadataAVUList metadata)
	{
		super(metadata);
		
		//Quantum MD
		this.setQuantumMDMethods(metadata.getValues(MethodMetadata.QMD_METHOD));

		// QM metadata
		this.setBasisSets(metadata.getValues(MethodMetadata.QM_BASIS_SET));
		this.setLevelsOfTheory(metadata.getValues(MethodMetadata.QM_LEVEL_OF_THEORY));
		this.setMethodNames(metadata.getValues(MethodMetadata.QM_METHOD_NAME));
		this.setCalculationTypes(metadata.getValues(MethodMetadata.CALCULATION));
		this.setExchangeCorrelationFns(metadata.getValues(MethodMetadata.QM_EXCHANGE_CORRELATION));
		List<String> values = metadata.getValues(MethodMetadata.QM_SPIN_MULTIPLICITY);
		if (values!=null && values.size()>0){
			int v=0;
			int[] spinMult = new int[values.size()];
			for(String value : values){
				try{
					spinMult[v] = Integer.parseInt(value);
				} catch (NumberFormatException e){
					//skip
				}
				v++;
			}
			this.setSpinMultiplicities(spinMult);
		}
		values = metadata.getValues(TopologyMetadata.TOTAL_MOLECULE_CHARGE);
		if (values!=null && values.size()>0){
			int v=0;
			double[] charges = new double[values.size()];
			for(String value : values){
				try{
					charges[v] = Double.parseDouble(value);
				} catch (NumberFormatException e){
					//skip
				}
				v++;
			}
			this.setTotalCharges(charges);
		}

		// MD metadata
		this.setBarostats(metadata.getValues(MethodMetadata.BAROSTAT_ALGORITHM));
		this.setThermostats(metadata.getValues(MethodMetadata.THERMOSTAT_ALGORITHM));
		this.setElectrostatics(metadata.getValues(MethodMetadata.ELECTROSTATICS_MODELING));
		this.setEnsembles(metadata.getValues(MethodMetadata.ENSEMBLE_MODELING));
		this.setUnitShapes(metadata.getValues(MethodMetadata.UNIT_SHAPE));
		if (metadata.containsAttribute(MethodMetadata.SIMULATED_TIME))
			this.setSimulatedTime(Double.parseDouble(metadata.getValue(MethodMetadata.SIMULATED_TIME)));
		if (metadata.containsAttribute(MethodMetadata.TIME_STEP_LENGTH))
			this.setTimeStepLength(Double.parseDouble(metadata.getValue(MethodMetadata.TIME_STEP_LENGTH)));
		
		//generic metadata
		this.name = Method.METHOD_QUANTUM_MD;
		this.timeDependent = false;
	}
}
