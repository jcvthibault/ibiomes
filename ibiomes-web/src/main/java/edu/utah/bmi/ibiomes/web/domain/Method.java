package edu.utah.bmi.ibiomes.web.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Computational method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="method")
public class Method {
	/**
	 *  Molecular dynamics method
	 */
	public final static String METHOD_MD = "Molecular dynamics";
	
	/**
	 *  Langevin dynamics
	 */
	public final static String METHOD_LANGEVIN_DYNAMICS = "Langevin dynamics";
	/**
	 *  Replica-exchange molecular dynamics
	 */
	public final static String METHOD_REMD = "Replica-exchange MD";
	/**
	 *  Semi-empirical method
	 */
	public final static String METHOD_SEMI_EMPIRICAL = "Semi-empirical";
	
	/**
	 *  Semi-classical method
	 */
	public final static String METHOD_SEMI_CLASSICAL = "Semi-classical";
	
	/**
	 *  Quantum mechanics method
	 */
	public final static String METHOD_QM = "Quantum mechanics";
	 
	/**
	 *  Hybrid QM/MM method
	 */
	public final static String METHOD_QMMM = "QM/MM";
	
	/**
	 *  Quantum molecular dynamics method
	 */
	public static final String METHOD_QUANTUM_MD = "Quantum MD";
	
	/**
	 *  Conjugate gradient method
	 */
	public final static String METHOD_CONJUGATE_GRADIENT = "Conjugate gradient";
	/**
	 *  Steepest descent method
	 */
	public final static String METHOD_STEEPEST_DESCENT = "Steepest descent";
	/**
	 * Periodic boundary conditions
	 */
	public static final String BC_PERIODIC = "Periodic";
	/**
	 * Non-periodic boundary conditions
	 */
	public static final String BC_NON_PERIODIC = "Non-periodic";
	/**
	 *  In vacuo (no solvent)
	 */
	public final static String SOLVENT_IN_VACUO = "In vacuo";
	/**
	 *  Implicit solvent
	 */
	public final static String SOLVENT_IMPLICIT = "Implicit";
	/**
	 *  Explicit solvent
	 */
	public final static String SOLVENT_EXPLICIT = "Explicit";
	
	protected String name;
	protected boolean timeDependent;
	protected String boundaryConditions;
	protected List<String> solventTypes;
	protected List<String> implicitSolventModels;
	protected List<String> constraints;
	
	public Method(){
	}
	
	/**
	 * Get method name
	 * @return Method name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set method name
	 * @param name Method name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Is the method time-dependent or not?
	 * @return True if it is time-dependent, false otherwise
	 */
	@XmlAttribute
	public boolean isTimeDependent() {
		return timeDependent;
	}
	/**
	 * Set the flag for time-dependency
	 * @param timeDependent True if it is time-dependent, false otherwise
	 */
	public void setTimeDependent(boolean timeDependent) {
		this.timeDependent = timeDependent;
	}
	
	/**
	 * Get the type of boundary conditions
	 * @return Type of boundary conditions
	 */
	@XmlAttribute
	public String getBoundaryConditions() {
		return boundaryConditions;
	}
	/**
	 * Set the type of boundary conditions
	 * @param boundaryConditions Type of boundary conditions
	 */
	public void setBoundaryConditions(String boundaryConditions) {
		this.boundaryConditions = boundaryConditions;
	}
	
	/**
	 * Get solvent types
	 * @return Solvent types
	 */
	@XmlElementWrapper(name="solventTypes")
	@XmlElement(name="solventType")
	public List<String> getSolventTypes() {
		return solventTypes;
	}
	
	/**
	 * Set solvent types
	 * @param solventTypes Solvent types
	 */
	public void setSolventTypes(List<String> solventTypes) {
		this.solventTypes = solventTypes;
	}

	/**
	 * Get implicit solvent models
	 * @return Implicit solvent models
	 */
	public List<String> getImplicitSolventModels() {
		return implicitSolventModels;
	}

	/**
	 * Set implicit solvent models
	 * @param implicitSolventModels Implicit solvent models
	 */
	@XmlElementWrapper(name="implicitSolventModels")
	@XmlElement(name="implicitSolventModel")
	public void setImplicitSolventModels(List<String> implicitSolventModels) {
		this.implicitSolventModels = implicitSolventModels;
	}
	
	/**
	 * Get list of constraints
	 * @return List of constraints
	 */
	@XmlElementWrapper(name="constraints")
	@XmlElement(name="constraint")
	public List<String> getConstraints() {
		return constraints;
	}
	
	/**
	 * Set the list of constraints
	 * @param constraints List of constraints
	 */
	public void setConstraints(List<String> constraints) {
		this.constraints = constraints;
	}
	
	/**
	 * Construct method object based on iBIOMES metadata
	 * @param metadata List of iBIOMES metadata
	 */
	public Method(MetadataAVUList metadata)
	{
		this.name = metadata.getValue(MethodMetadata.COMPUTATIONAL_METHOD_NAME);
		this.setBoundaryConditions(metadata.getValue(MethodMetadata.BOUNDARY_CONDITIONS));
		this.setSolventTypes(metadata.getValues(MethodMetadata.SOLVENT_TYPE));
		this.setImplicitSolventModels(metadata.getValues(MethodMetadata.IMPLICIT_SOLVENT_MODEL));	
		this.setConstraints(metadata.getValues(MethodMetadata.CONSTRAINT_ALGORITHM));
	}
}
