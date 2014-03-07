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

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.comp.Constraint;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.ElectrostaticsModel;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;

/**
 * Quantum/Molecular mechanics method/parameter set.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="parameterSet")
public class QMMMParameterSet extends ParameterSet {

	private String qmSpaceDefinition;
	private String boundaryTreatment;
	private List<Constraint> qmConstraints = null;
	private ElectrostaticsModel qmElectrostatics = null;

	/**
	 * Constructor
	 */
	public QMMMParameterSet(){
		this.name = ParameterSet.METHOD_QMMM;
	}

	/**
	 * Get QM partition definition
	 * @return QM space definition
	 */
	public String getQmSpaceDefinition() {
		return qmSpaceDefinition;
	}

	/**
	 * Set QM partition definition
	 * @param qmSpaceDefinition QM space definition
	 */
	public void setQmSpaceDefinition(String qmSpaceDefinition) {
		this.qmSpaceDefinition = qmSpaceDefinition;	
	}

	/**
	 * Get QM/MM boundary treatment type
	 * @return Boundary treatment type
	 */
	public String getBoundaryTreatment() {
		return boundaryTreatment;
	}

	/**
	 * Set QM/MM boundary treatment type
	 * @param boundaryTreatment Boundary treatment type
	 */
	public void setBoundaryTreatment(String boundaryTreatment) {
		this.boundaryTreatment = boundaryTreatment;
	}
	
	/**
	 * Get constraints on QM space
	 * @return List of constraints
	 */
	public List<Constraint> getQmConstraints() {
		return qmConstraints;
	}

	/**
	 * Set constraints on QM space
	 * @param qmConstraints List of constraints
	 */
	public void setQmConstraints(List<Constraint> qmConstraints) {
		this.qmConstraints = qmConstraints;
	}

	/**
	 * Get electrostatics model used for QM-QM and QM-MM long range interactions
	 * @return Electrostatics model
	 */
	public ElectrostaticsModel getQmElectrostatics() {
		return qmElectrostatics;
	}

	/**
	 * Set electrostatics model used for QM-QM and QM-MM long range interactions
	 * @param qmElectrostatics Electrostatics model
	 */
	public void setQmElectrostatics(ElectrostaticsModel qmElectrostatics) {
		this.qmElectrostatics = qmElectrostatics;
	}
	
	/**
	 * Get list of metadata representing this QM method
	 */
	@Override
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = super.getMetadata();
		
		metadata.removeAVUWithAttribute(MethodMetadata.COMPUTATIONAL_METHOD_NAME);
		metadata.add(new MetadataAVU(MethodMetadata.COMPUTATIONAL_METHOD_NAME, ParameterSet.METHOD_QMMM));
				
		if (this.qmConstraints != null){
			for (Constraint constraint : qmConstraints){
				metadata.addAll(constraint.getMetadata());
			}
		}
		
		if (this.qmSpaceDefinition != null)
			metadata.add(new MetadataAVU(MethodMetadata.QM_REGION_DEFINITION, qmSpaceDefinition));
		
		if (this.qmElectrostatics != null)
			metadata.add(new MetadataAVU(MethodMetadata.QM_ELECTROSTATICS_MODELING, qmElectrostatics.getName()));
		
		if (this.boundaryTreatment != null){
			metadata.add(new MetadataAVU(MethodMetadata.QMMM_BOUNDARY_TREATMENT, boundaryTreatment));
		}
		
		return metadata;
	}
}
