package edu.utah.bmi.ibiomes.web.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Molecular dynamics method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="method")
public class MDMethod extends Method
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
	
	protected List<String> forceFields;
	protected List<String> restraints;
	protected List<String> unitShapes;
	protected List<String> thermostats;
	protected List<String> minimizations;
	protected List<String> barostats;
	protected List<String> ensembles;
	protected List<String> electrostatics;
	protected List<String> integrators;
	protected List<String> samplingMethods;
	protected double simulatedTime;
	protected double timeStepLength;
	
	/**
	 * Constructor
	 */
	public MDMethod()
	{
		this.name = Method.METHOD_MD;
		this.timeDependent = true;
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
	 * Set time step length
	 * @param timeStepLength Time step length
	 */
	public void setTimeStepLength(double timeStepLength) {
		this.timeStepLength = timeStepLength;
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

	public List<String> getRestraints() {
		return restraints;
	}

	public void setRestraints(List<String> restraints) {
		this.restraints = restraints;
	}
	
	/**
	 * Construct MD method based on a list of iBIOMES metadata
	 * @param metadata List of iBIOMES metadata
	 */
	public MDMethod(MetadataAVUList metadata)
	{
		super(metadata);
		
		this.name = Method.METHOD_MD;
		this.timeDependent = true;

		this.setMinimizations(metadata.getValues(MethodMetadata.MD_MINIMIZATION));
		this.setBarostats(metadata.getValues(MethodMetadata.BAROSTAT_ALGORITHM));
		this.setRestraints(metadata.getValues(MethodMetadata.RESTRAINT_TYPE));
		this.setThermostats(metadata.getValues(MethodMetadata.THERMOSTAT_ALGORITHM));
		this.setElectrostatics(metadata.getValues(MethodMetadata.ELECTROSTATICS_MODELING));
		this.setEnsembles(metadata.getValues(MethodMetadata.ENSEMBLE_MODELING));
		this.setForceFields(metadata.getValues(MethodMetadata.FORCE_FIELD));
		this.setUnitShapes(metadata.getValues(MethodMetadata.UNIT_SHAPE));
		this.setIntegrators(metadata.getValues(MethodMetadata.MM_INTEGRATOR));
		if (metadata.containsAttribute(MethodMetadata.SIMULATED_TIME))
			this.setSimulatedTime(Double.parseDouble(metadata.getValue(MethodMetadata.SIMULATED_TIME)));
		if (metadata.containsAttribute(MethodMetadata.TIME_STEP_LENGTH))
			this.setTimeStepLength(Double.parseDouble(metadata.getValue(MethodMetadata.TIME_STEP_LENGTH)));
		this.setSamplingMethods(metadata.getValues(MethodMetadata.ENHANCED_SAMPLING_METHOD_NAME));
	}
}
