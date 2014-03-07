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
import edu.utah.bmi.ibiomes.parse.chem.TrajectoryFile;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.bio.Residue;

/**
 * Protein Data Bank (PDB) file
 * @author Julien Thibault
 *
 */
public class PDBFile extends AbstractTopologyFile implements TrajectoryFile {

	private static final long serialVersionUID = -1315255659740299479L;
	private String pdbId;
	private String remarks = "";
	private String compoundInformation = "";
	private String title = "";
	private String user = "";
	
	/**
	 * Load PDB file
	 * @param pathname File path
	 * @throws Exception 
	 * @throws CDKException 
	 */
	public PDBFile(String pathname) throws Exception {
		super(pathname, FORMAT_PDB);
		this.molecularSystems = parseFile();
		
		/*PDBReader reader = new PDBReader(new FileReader(pathname));
		ChemFile chemFile = (ChemFile)reader.read((ChemObject)new ChemFile());
		List<IAtomContainer> containersList = ChemFileManipulator.getAllAtomContainers(chemFile);
		for (int c=0; c< containersList.size(); c++) {
			IAtomContainer container = containersList.get(c);
			IMolecularFormula formula = MolecularFormulaManipulator.getMolecularFormula(container);
			double mass = MolecularFormulaManipulator.getNaturalExactMass(formula);
			System.out.println("[" + container.getAtomCount() + "] Mass=" + mass + " Formula="+MolecularFormulaManipulator.getString(formula));
		}*/
	}
	
	/** 
	 * Get topology metadata and PDB specific metadata.
	 * @throws Exception 	 */
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
		if (pdbId != null && pdbId.length()>0)
			metadata.add(new MetadataAVU(TopologyMetadata.STRUCTURE_REF_ID, "PDB:"+ pdbId));
		
		return metadata;
	}
	
	/**
	 * Set PDB ID
	 * @param id PDB ID
	 */
	public void setPdbID(String id){
		this.pdbId = id;
	}
	
	/**
	 * Get PDB ID
	 * @return PDB ID
	 */
	public String getPdbID(){
		return this.pdbId;
	}
	
	/**
	 * Parse PDB file
	 * @return Molecule represented in this file
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
			
			String line;
			
			try
			{
				while ((line = br.readLine()) != null) 
				{
					// REMARK line
					//===================================================================================
					if (line.startsWith("REMARK ")){
						remarks += line.substring(10).trim() + "\n";
					}
					// TITLE line
					//===================================================================================
					else if (line.startsWith("TITLE ")){
						title += line.substring(10).trim() + "\n";
					}
					// COMPND line
					//===================================================================================
					else if (line.startsWith("COMPND ")){
						compoundInformation += line.substring(7).trim() + "\n";
					}
					//===================================================================================
					else if (line.startsWith("USER ")){
						
					}
					//===================================================================================
					else if (line.startsWith("SEQRES ")){
						
					}
					//===================================================================================
					else if (line.startsWith("HET ")){
						
					}
					//===================================================================================
					else if (line.startsWith("HELIX ")){
						
					}
					//===================================================================================
					else if (line.startsWith("HYDBND ")){
						
					}
					//===================================================================================
					else if (line.startsWith("HYDBND ")){
						
					}
					// ATOM line
					//===================================================================================
					else if (line.startsWith("ATOM ") || line.startsWith("HETATM"))
					{				
						//atom ID
						int id = Integer.parseInt(line.substring(7,11).trim());
						//atom name
						String name = line.substring(12,16).trim();
						//residue name
						String resName = line.substring(17,20).trim();
						//residue ID
						int resId = Integer.parseInt(line.substring(23,26).trim());
						//mass
						double mass = 0;
						//charge
						float charge = 0;
						if (line.length()>80 && line.substring(78,80).trim().length()>0){
							charge = Float.parseFloat(line.substring(78,80).trim());
						}
						double x=0, y=0, z=0;
						String atomtype = "?";
						
						if (line.length()>79){
							x = Double.parseDouble(line.substring(30,38).trim());
							y = Double.parseDouble(line.substring(39,46).trim());
							z = Double.parseDouble(line.substring(47,54).trim());
							atomtype = line.substring(76,79).trim();
						}
						
						//create atom
						Atom atom = new Atom(id,name,charge,mass,atomtype);
						//create coordinates
						Coordinate3D coord = new Coordinate3D(x,y,z);
						atom.setCoordinates(coord);
						//create new residue and/or add atom to existing residue
						if (resId != currResId){
							currResidue = new Residue(resName);
							residues.add(currResidue);
							currResId = resId;
							currResidue.setIsStandard(!line.startsWith("HETATM"));
						}
						currResidue.addAtom(atom);
					}
					else if (line.startsWith("TER "))
					{
						
					}
					//===================================================================================
					else if (line.startsWith("CONECT")){
						
					}
					//===================================================================================
					else if (line.startsWith("ENDMDL")){
						//end of the model... keep only info for the 1st one (same topology)
						break;
					}
					else {
						
					}
				}
			} catch(Exception e){
				//TODO add flag if parsing fails?
			}
			br.close();
			
			//create molecule
			MolecularSystem system = new MolecularSystem(residues);
			system.setName(this.getName());
			system.setDescription(compoundInformation);
			system.setName(title);
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
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a PDB file.");
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
	
	/**
	 * Get the number of frames stored in this file
	 */
	public int getNumberOfFrames() {
		return 1;
	}

	/**
	 * Get remarks
	 * @return Remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Set remarks
	 * @param remarks Remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Get compound information
	 * @return Compound information
	 */
	public String getCompoundInformation() {
		return compoundInformation;
	}

	/**
	 * Set compound information
	 * @param compoundInformation Compound information
	 */
	public void setCompoundInformation(String compoundInformation) {
		this.compoundInformation = compoundInformation;
	}

	/**
	 * Get title
	 * @return Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 * @param title Title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get user
	 * @return User
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Set user info
	 * @param user User info
	 */
	public void setUser(String user) {
		this.user = user;
	}

}
