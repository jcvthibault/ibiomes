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

package edu.utah.bmi.ibiomes.experiment.comp.mm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.quantity.Distance;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * Molecular dynamics method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="parameterSet")
public class MDParameterSet extends ParameterSet
{
	/**
	 * Leap-Frog integrator
	 */
	public static final String INTEGRATOR_LEAP_FROG = "Leap-Frog";
	/**
	 * Velocity Verlet integrator
	 */
	public static final String INTEGRATOR_VELOCITY_VERLET = "Velocity Verlet";
	/**
	 * Verlet integrator
	 */
	public static final String INTEGRATOR_VERLET = "Verlet";
	/**
	 * Euler integrator
	 */
	public static final String INTEGRATOR_EULER = "Euler";
	
	protected List<Restraint> restraints;
	protected List<String> forceFields;
	protected Thermostat thermostat;
	protected Barostat barostat;
	protected String ensemble;
	protected ElectrostaticsModel electrostatics;
	protected String integrator;
	protected TimeLength simulatedTime;
	protected Distance cutoffForVanDerWaals = null;
	protected String unitShape;
	protected TimeLength timeStepLength;
	protected int numberOfSteps;
	
	/**
	 * Constructor
	 */
	public MDParameterSet(){
		this.name = ParameterSet.METHOD_MD;
	}

	/**
	 * Get list of force fields
	 * @return List of force fields
	 */
	@XmlElementWrapper(name="forceFields")
	@XmlElement(name="forceField")
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
	 * Set force field
	 * @param forceField Force fields
	 */
	public void setForceField(String forceField) {
		this.forceFields = new ArrayList<String>();
		this.forceFields.add(forceField);
	}
	/**
	 * Get thermostat
	 * @return Thermostat
	 */
	@XmlElementRefs({
		@XmlElementRef(name="thermostat", type = Thermostat.class),
		@XmlElementRef(name="thermostat", type = LangevinThermostat.class)})
	public Thermostat getThermostat() {
		return thermostat;
	}

	/**
	 * Set thermostat
	 * @param thermostat Thermostat
	 */
	public void setThermostat(Thermostat thermostat) {
		this.thermostat = thermostat;
	}

	/**
	 * Get barostat
	 * @return Barostat
	 */
	@XmlElement(name="barostat")
	public Barostat getBarostat() {
		return barostat;
	}

	/**
	 * Set the barostat
	 * @param barostat Barostat
	 */
	public void setBarostat(Barostat barostat) {
		this.barostat = barostat;
	}

	/**
	 * Get ensemble type
	 * @return Ensemble type
	 */
	public String getEnsemble() {
		return ensemble;
	}

	/**
	 * Set ensemble type
	 * @param ensemble Ensemble type
	 */
	public void setEnsemble(String ensemble) {
		this.ensemble = ensemble;
	}

	/**
	 * Get electrostatics model.
	 * @return Electrostatics model.
	 */
	@XmlElementRefs({
		@XmlElementRef(name="electrostaticsModel", type = ElectrostaticsModel.class),
		@XmlElementRef(name="electrostaticsModel", type = PMEModel.class)})
	public ElectrostaticsModel getElectrostatics() {
		return electrostatics;
	}

	/**
	 * Set electrostatics model.
	 * @param model Electrostatics model.
	 */
	public void setElectrostatics(ElectrostaticsModel model) {
		this.electrostatics = model;
	}
	
	/**
	 * Get cutoff value for Van der Waals
	 * @return Cutoff value for Van der Waals
	 */
	public Distance getCutoffForVanDerWaals() {
		return cutoffForVanDerWaals;
	}

	/**
	 * Set cutoff value for Van der Waals
	 * @param cutoffForVdW Cutoff value for Van der Waals
	 */
	public void setCutoffForVanDerWaals(Distance cutoffForVdW) {
		this.cutoffForVanDerWaals = cutoffForVdW;
	}

	/**
	 * Get molecular mechanics integrator.
	 * @return Molecular mechanics integrator.
	 */
	public String getIntegrator() {
		return integrator;
	}

	/**
	 * Set molecular mechanics integrator.
	 * @param integrator Molecular mechanics integrator
	 */
	public void setIntegrator(String integrator) {
		this.integrator = integrator;
	}
	/**
	 * Get unit shape
	 * @return Unit shape
	 */
	public String getUnitShape() {
		return unitShape;
	}

	/**
	 * Get simulated time
	 * @return Simulated time
	 */
	public TimeLength getSimulatedTime() {
		return simulatedTime;
	}

	/**
	 * Set simulated time
	 * @param simulatedTime Simulated time
	 */
	public void setSimulatedTime(TimeLength simulatedTime) {
		this.simulatedTime = simulatedTime;
	}
	/**
	 * Set unit shape
	 * @param unitShape Unit shape
	 */
	public void setUnitShape(String unitShape) {
		this.unitShape = unitShape;
	}

	/**
	 * Get number of steps
	 * @return Number of steps/iterations
	 */
	public int getNumberOfSteps() {
		return this.numberOfSteps;
	}

	/**
	 * Set number of steps/iterations
	 * @param numberOfSteps Number of steps
	 */
	public void setNumberOfSteps(int numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}

	/**
	 * Get time step length
	 * @return Time step length
	 */
	public TimeLength getTimeStepLength() {
		return timeStepLength;
	}

	/**
	 * Set time step length
	 * @param timeStepLength Time step length
	 */
	public void setTimeStepLength(TimeLength timeStepLength) {
		this.timeStepLength = timeStepLength;
	}

	/**
	 * Get restraints
	 * @return Restraints
	 */
	@XmlElementWrapper(name="restraints")
	@XmlElement(name="restraint")
	public List<Restraint> getRestraints() {
		return restraints;
	}

	/**
	 * Set restraints
	 * @param restraints Restraints
	 */
	public void setRestraints(List<Restraint> restraints) {
		this.restraints = restraints;
	}

	/**
	 * Set restraint
	 * @param restraint Restraint
	 */
	public void setRestraint(Restraint restraint) {
		this.restraints = new ArrayList<Restraint>();
		this.restraints.add(restraint);
	}
	
	/**
	 * Get MD metadata
	 */
	@Override
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = super.getMetadata();
		
		if (this.forceFields!=null && this.forceFields.size()>0){
			for (String ff : this.forceFields){
				metadata.add(new MetadataAVU(MethodMetadata.FORCE_FIELD, ff));
			}
		}
		if (this.barostat!=null)
			metadata.addAll(this.barostat.getMetadata());

		if (this.thermostat!=null)
			metadata.addAll(this.thermostat.getMetadata());
		
		if (this.electrostatics!=null)
			metadata.addAll(electrostatics.getMetadata());
		
		if (this.ensemble!=null && this.ensemble.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.ENSEMBLE_MODELING, this.ensemble));
		
		if (this.integrator!=null && this.integrator.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.MM_INTEGRATOR, this.integrator));
		
		if (this.simulatedTime!=null)
			metadata.add(new MetadataAVU(MethodMetadata.SIMULATED_TIME, 
					String.valueOf(this.simulatedTime.getValue()),
					this.simulatedTime.getUnit()));
		
		if (this.timeStepLength!=null)
			metadata.add(new MetadataAVU(MethodMetadata.TIME_STEP_LENGTH, String.valueOf(this.timeStepLength.getValue()), this.timeStepLength.getUnit()));
		
		if (this.numberOfSteps>0)
			metadata.add(new MetadataAVU(MethodMetadata.TIME_STEP_COUNT, String.valueOf(this.numberOfSteps)));
		
		if (this.unitShape!=null && this.unitShape.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.UNIT_SHAPE, this.unitShape));
		
		if (this.restraints!=null && this.restraints.size()>0){
			for (Restraint restraint : restraints){
				metadata.addAll(restraint.getMetadata());
			}
			metadata.add(new MetadataAVU(MethodMetadata.HAS_RESTRAINTS, "true"));
		}
		
		if (this.cutoffForVanDerWaals!=null)
			metadata.add(new MetadataAVU(MethodMetadata.CUTOFF_VAN_DER_WAALS, 
					String.valueOf(this.cutoffForVanDerWaals.getValue()),
					this.cutoffForVanDerWaals.getUnit()));
		
		return metadata;
	}
}

