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
package edu.utah.bmi.ibiomes.parse.chem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;

/**
 * Abstract file that contains topology information
 * @author Julien Thibault, University of Utah
 *
 */
public abstract class AbstractTopologyFile extends ChemicalFile implements TopologyFile {

	private static final long serialVersionUID = 7876666307922974914L;
	protected List<MolecularSystem> molecularSystems;
	
	/**
	 * New topology file
	 * @param localPath Path to local file
	 * @throws IOException
	 */
	public AbstractTopologyFile(String localPath) throws IOException {
		super(localPath);
		if (!this.exists())
			throw new FileNotFoundException("File " + localPath + " does not exist!");
	}
	
	/**
	 * New topology file
	 * @param localPath Path to local file
	 * @param fileFormat File format
	 * @throws IOException
	 */
	public AbstractTopologyFile(String localPath, String fileFormat) throws IOException {
		super(localPath,fileFormat);
		if (!this.exists())
			throw new FileNotFoundException("File " + localPath + " does not exist!");
	}

	/**
	 * Get molecular systems
	 * @return Molecular systems
	 */
	public List<MolecularSystem> getMolecularSystems() {
		return molecularSystems;
	}

	/**
	 * Set molecular systems
	 * @param molecularSystems Molecular systems
	 */
	public void setMolecularSystems(List<MolecularSystem> molecularSystems) {
		this.molecularSystems = molecularSystems;
	}
	
	/**
	 * Get metadata
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		
		MetadataAVUList metadata = new MetadataAVUList();
		if (super.getMetadata()!=null)
			metadata.addAll(super.getMetadata());
		
		if (this.molecularSystems != null && molecularSystems.size()>0){
			/*for (MolecularSystem system : molecularSystems){
				metadata.addAll(system.getMetadata());
			}*/
			metadata.addAll(molecularSystems.get(0).getMetadata());
		}
		return metadata;
	}
}
