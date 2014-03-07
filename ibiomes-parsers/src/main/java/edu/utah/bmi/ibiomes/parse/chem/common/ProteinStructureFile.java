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

package edu.utah.bmi.ibiomes.parse.chem.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractTopologyFile;
import edu.utah.bmi.ibiomes.parse.chem.TopologyFile;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.bio.Residue;

/**
 * Protein Structure File (PSF). It contains all of the molecule-specific 
 * information needed to apply a particular force field to a molecular system.
 * 
 * @author Julien Thibault
 *
 */
public class ProteinStructureFile extends AbstractTopologyFile implements TopologyFile {

	private static final long serialVersionUID = -3492445943007448648L;
	private static final String REMARKS_MARKER = "REMARKS";
	private String compoundInfo;
	private String id;
	
	/**
	 * Load PSF file
	 * @param pathname File path
	 * @throws Exception 
	 */
	public ProteinStructureFile(String pathname) throws Exception {
		super(pathname, FORMAT_PSF);
		this.molecularSystems = parseFile();
	}
	
	/**
	 * Set PSF ID
	 * @param id PSF ID
	 */
	public void setPSFID(String id){
		this.id = id;
	}
	
	/**
	 * Get PSF ID
	 * @return PSF ID
	 */
	public String getPSFID(){
		return this.id;
	}

	/**
	 * Set compound info
	 * @param compnd compound info
	 */
	public void setCompoundInfo(String compnd){
		compoundInfo = compnd;
	}
	
	/**
	 * Get compound info
	 * @return compound info
	 */
	public String getCompoundInfo(){
		return compoundInfo;
	}
	
	/** 
	 * Get topology metadata and PSF specific metadata.
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		
		MetadataAVUList metadata = super.getMetadata();
		
		List<MolecularSystem> models = this.getMolecularSystems();
		if (models != null && models.size()>0){
			metadata.addAll(models.get(0).getMetadata());
			/*for (MetadataAVU molmeta : mol.getMetadata()){
				metadata.add(new MetadataAVU(molmeta.getAttribute(), molmeta.getValue()));
			}*/
		}
		if (id!=null && id.length()>0)
			metadata.add(new MetadataAVU(TopologyMetadata.STRUCTURE_REF_ID, "PSF:"+ id));
		
		return metadata;
	}
	
	/**
	 * Parse PSF file
	 * @return Molecule
	 * @throws Exception 
	 * @throws IOException
	 */
	private List<MolecularSystem> parseFile() throws Exception
	{	
		IBIOMESFileReader br = null;
		try{
			br = new IBIOMESFileReader(this);
			
			ArrayList<Residue> residues = new ArrayList<Residue>();
			Residue currResidue = null;
			int currResId = 0;
			String remarks = "";
			String title = "";
			boolean sectionStart = false;
			
			String line;
			
			//reach 'title' section
			while ((line = br.readLine()) != null  && !sectionStart)
			{
				if (line.trim().endsWith("!NTITLE"))
					sectionStart = true;
			}
			//read remarks
			while ((line = br.readLine()) != null  && line.trim().startsWith(REMARKS_MARKER))
			{
				line = line.trim();
				remarks += " " + line.substring(REMARKS_MARKER.length()+1);
			}
			remarks = remarks.trim();
			
			// ATOMS
			//===================================================================================
			//reach 'atom' section
			while ((line = br.readLine()) != null  && !sectionStart)
			{
				if (line.trim().endsWith("!NATOM"))
					sectionStart = true;
			}
			// read atom lines
			// The fields in the atom section are atom ID, segment name, residue ID, residue name, 
			// atom name, atom type, charge, mass, and an unused 0. PSF files may be in either CHARMM 
			// or X-PLOR format, with the CHARMM format using an integer rather than a name for the atom type.
			while ((line = br.readLine()) != null  && line.trim().length()>0)
			{
				line = line.trim();
				String[] values = line.split("\\s+");
				
				//atom ID
				int id = Integer.parseInt(values[0]);
				//atom name
				String name = values[4];
				//atom name
				String atomtype = values[5];
				//residue name
				String resName = values[3];
				//residue ID
				int resId = Integer.parseInt(values[2]);
				//charge
				float charge = Float.parseFloat(values[6]);
				//mass
				double mass = Double.parseDouble(values[7]);
				
				//create atom
				Atom atom = new Atom(id, name, charge, mass, atomtype);
				//create new residue and/or add atom to existing residue
				if (resId != currResId){
					currResidue = new Residue(resName);
					residues.add(currResidue);
					currResId = resId;
				}
				currResidue.addAtom(atom);
			}
			br.close();
			
			//create molecule
			MolecularSystem system = new MolecularSystem(residues);
			system.setName(title);
			system.setDescription(remarks);
			system.setDefinitionFiles(this.getCanonicalPath());
			
			List<MolecularSystem> systems = new ArrayList<MolecularSystem>();
			systems.add(system);
			
			if (this.getDescription()==null)
				this.setDescription(title);
			
			return systems;
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a Protein Structure File.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			if (br!=null)
				try {
					br.close();
				} catch (IOException e1) {
				}
			throw e;
		}
	}
}
