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

package edu.utah.bmi.ibiomes.experiment.comp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Computational method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="parameterSet")
public class ParameterSet implements MetadataMappable {
	/**
	 *  Molecular mechanics method
	 */
	public static final String METHOD_MM =  "Molecular mechanics";
	/**
	 *  Molecular dynamics method
	 */
	public final static String METHOD_MD = "Molecular dynamics";
	/**
	 *  REMD task
	 */
	public final static String METHOD_REMD = "Replica-exchange MD";
	
	/**
	 *  Langevin dynamics
	 */
	public final static String METHOD_LANGEVIN_DYNAMICS = "Langevin dynamics";
	
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
	 *  Quantum molecular dynamics (Ab initio and semi-empirical MD) method
	 */
	public final static String METHOD_QUANTUM_MD = "Quantum MD";
	/**
	 * Minimization
	 */
	public static final String METHOD_MINIMIZATION = "Minimization";
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

	/**
	 *  GB (HCT) implicit solvent
	 */
	public final static String IMPLICIT_MODEL_GB_HCT 	= "GB HCT";
	/**
	 *  GB (OBC) implicit solvent
	 */
	public final static String IMPLICIT_MODEL_GB_OBC 	= "GB OBC";
	/**
	 *  GBn) implicit solvent
	 */
	public final static String IMPLICIT_MODEL_GB_N 	= "GBn";
	/**
	 *  PB implicit solvent
	 */
	public final static String IMPLICIT_MODEL_PB 		= "PB";
	
	protected String name;
	protected String solventType;
	protected List<Constraint> constraints;
	protected String implicitSolventModel;
	
	public ParameterSet(){
	}
	
	/**
	 * Create new named method
	 * @param name Name of the method
	 */
	public ParameterSet(String name) {
		this.name = name;
	}

	/**
	 * Get method name
	 * @return Method name
	 */
	@XmlAttribute(name="type")
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
	 * Get solvent type
	 * @return Solvent type
	 */
	public String getSolventType() {
		return solventType;
	}
	
	/**
	 * Set solvent type
	 * @param solventType Solvent type
	 */
	public void setSolventType(String solventType) {
		this.solventType = solventType;
	}
	
	/**
	 * Get implicit solvent model
	 * @return Implicit solvent model
	 */
	public String getImplicitSolventModel() {
		return implicitSolventModel;
	}

	/**
	 * Set implicit solvent model
	 * @param implicitSolventModel Implicit solvent model
	 */
	public void setImplicitSolventModel(String implicitSolventModel) {
		this.implicitSolventModel = implicitSolventModel;
	}
	
	/**
	 * Get list of constraints
	 * @return List of constraints
	 */
	@XmlElementWrapper(name="constraints")
	@XmlElement(name="constraint")
	public List<Constraint> getConstraints() {
		return constraints;
	}
	
	/**
	 * Set the list of constraints
	 * @param constraints List of constraints
	 */
	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	/**
	 * Set a constraint
	 * @param constraint Constraint
	 */
	public void setConstraints(Constraint constraint) {
		this.constraints = new ArrayList<Constraint>();
		this.constraints.add(constraint);
	}
	
	/**
	 * Get metadata representation of the method
	 * @return List of metadata
	 */
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		/*if (this.name!=null && this.name.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.SIMULATION_METHOD, this.name ));*/
		if (this.solventType!=null)
			metadata.add(new MetadataAVU(MethodMetadata.SOLVENT_TYPE, this.solventType ));
		if (this.implicitSolventModel!=null && this.implicitSolventModel.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.IMPLICIT_SOLVENT_MODEL, this.implicitSolventModel ));
		if (this.constraints != null && this.constraints.size()>0){
			for (Constraint constraint : this.constraints){
				metadata.addAll(constraint.getMetadata());
			}
			metadata.add(new MetadataAVU(MethodMetadata.HAS_CONSTRAINTS, "true"));
		}
		return metadata;
	}
}
