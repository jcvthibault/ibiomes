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

package edu.utah.bmi.ibiomes.topo;

import java.text.DecimalFormat;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;

/**
 * Compound / small molecule (structural information)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="molecule")
public class Compound extends Molecule
{	
	/**
	 * Create compound with list of atoms
	 * @param name Compound name
	 * @param atoms List of atoms
	 */
	public Compound(String name, List<Atom> atoms) {
		super(name);
		this.atoms = atoms;
		this.type = MolecularSystem.TYPE_COMPOUND;
	}
	/**
	 * Create empty compound with a name
	 * @param name Name of the compound
	 */
	public Compound(String name){
		super(name);
		this.type = MolecularSystem.TYPE_COMPOUND;
	}
	
	/**
	 * Create empty compound
	 */
	public Compound(){
		this.type = MolecularSystem.TYPE_COMPOUND;
	}

	@Override
	public float getCharge() {
		return this.charge;
	}
	
	@XmlElement(name="stoichiometry")
	@Override
	public String getAtomicCompositionCompact(){
		return super.getAtomicCompositionCompact();
	}

	@XmlElementWrapper(name="atoms")
	@XmlElement(name="atom")
	@Override
	public List<Atom> getAtoms(){
		return this.atoms;
	}
	
	/**
	 * Get metadata about compound structure
	 * @return Metadata
	 * @throws Exception
	 */
	@Override
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList supermetadata = super.getMetadata();
		
		MetadataAVUList metadata = new MetadataAVUList();
		metadata.addAll(supermetadata);
		
		//get chemical compound metadata

		DecimalFormat sixdForm = new DecimalFormat("#.######");
		String molWeight = sixdForm.format(this.getAtomicWeight());
		metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_ATOMIC_WEIGHT, molWeight));
		metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_ATOMIC_COMPOSITION, this.getAtomicComposition()));
		metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_ATOMIC_COMPOSITION2, this.getAtomicCompositionCompact()));
		metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_TYPE, MolecularSystem.TYPE_COMPOUND));
		
		return metadata;
	}
	
}
