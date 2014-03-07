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

package edu.utah.bmi.ibiomes.parse.chem.charmm;

import java.io.IOException;
import java.util.ArrayList;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ChemicalFile;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;
import edu.utah.bmi.ibiomes.topo.bio.Biomolecule;
import edu.utah.bmi.ibiomes.topo.bio.Residue;

/**
 * CHARMM coordinate (topology) file (.crd). 
 * Define the standard Cartesian coordinates of the atoms in the system.
 * 
 * @author Julien Thibault, University of Utah
 *
 */
@Deprecated
public class CHARMMCoordinateFile extends ChemicalFile
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 298011009260903858L;
	private Biomolecule _molecule;
	
	/**
	 * Default constructor.
	 * @param pathname Topology file path.
	 * @throws Exception
	 */
	public CHARMMCoordinateFile(String pathname) throws Exception
	{
		super(pathname, LocalFile.FORMAT_CHARMM_CRD);
		_molecule = parseFile();
	}	

	/**
	 * Get topology metadata and CHARMM-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception
	{	
		MetadataAVUList metadata = super.getMetadata();
		
		//get topology-specific metadata
		if (_molecule != null)
			metadata.addAll(this.getMolecule().getMetadata());
		
		return metadata;
	}
	
	/**
	 * Get molecule topology
	 * @return Molecule topology
	 * @throws Exception
	 */
	public Biomolecule getMolecule() throws Exception {
		return _molecule;
	};
	
	/**
	 * Load CHARMM topology file. 
	 * @param filePath
	 * @throws Exception
	 */
	private Biomolecule parseFile() throws Exception 
	{
		/*
		 CHARMm coordinate files contain information about the location of each atom in Cartesian (3D) space. The format of the ASCII (CARD) CHARMm coordinate files is:
		    * Title line(s)
		    * Number of atoms in file
		    * Coordinate line (one for each atom in the file)
		
		The coordinate lines contain specific information about each atom in the model and consist of the following fields:
		    * Atom number (sequential)
		    * Residue number (specified relative to first residue in the PSF)
		    * Residue name
		    * Atom type
		    * X-coordinate
		    * Y-coordinate
		    * Z-coordinate
		    * Segment identifier
		    * Residue identifier
		    * Weighting array value
		
		The FORTRAN FORMAT statement for the coordinate lines is:
		
		I5, I5, 1X, A4, 1X, A4, 3(F10.5), 1X, A4, 1X, A4, F10.5
		 */
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
			int nLines = 0;
			
			//reach 'coordinate' section (integer that repreesnts the number of lines)
			while ((line = br.readLine()) != null  && !sectionStart)
			{
				if (line.trim().matches("\\d+")){
					sectionStart = true;
					nLines = Integer.parseInt(line.trim());
				}
			}
			
			// read atom lines
			for (int l=0;l<nLines;l++)
			{
				line = br.readLine();
				line = line.trim();
				String[] values = line.split("\\s+");
				
				//atom ID
				int id = Integer.parseInt(values[0]);
				//atom name
				String name = values[3];
				//atom type
				String atomtype = values[3];
				//residue name
				String resName = values[2];
				//residue ID
				int resId = Integer.parseInt(values[1]);
				//coordinates
				double x = Double.parseDouble(values[5]);
				double y = Double.parseDouble(values[6]);
				double z = Double.parseDouble(values[7]);
				
				//create atom
				Atom atom = new Atom(id,name, atomtype);
				Coordinate3D coord = new Coordinate3D(x,y,z);
				atom.setCoordinates(coord);
				
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
			Biomolecule molecule = new Biomolecule(this.getName(), residues);
			molecule.setDescription(remarks);
			molecule.setName(title);
			
			if (this.getDescription()==null)
				this.setDescription(title);
			
			return molecule;
	    }
	    catch (Exception e){
	    	this.format = LocalFile.FORMAT_UNKNOWN;
	    	if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a CHARMM coordinate file.");
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
