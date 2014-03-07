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

package edu.utah.bmi.ibiomes.parse.chem.gromacs;

import java.util.ArrayList;
import java.util.List;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ChemicalFile;
import edu.utah.bmi.ibiomes.topo.bio.Biomolecule;

/**
 * GROMACS gro file (.gro). Files with the gro file extension contain a 
 * molecular structure in Gromos87 format. gro files can be used as 
 * trajectory by simply concatenating files.
 * C format: "%5d%-5s%5s%5d%8.3f%8.3f%8.3f%8.4f%8.4f%8.4f".
 * Fortran format: (i5,2a5,i5,3f8.3,3f8.4).
 * 
 * @author Julien Thibault, University of Utah
 *
 */
public class GROMACSGroFile extends ChemicalFile
{
	private static final long serialVersionUID = 244857191937798175L;
	private List<Biomolecule> molecules;
	
	/**
	 * Default constructor.
	 * @param pathname Topology file path.
	 * @throws Exception
	 */
	public GROMACSGroFile(String pathname) throws Exception
	{
		super(pathname, LocalFile.FORMAT_GROMACS_GRO);
		molecules = new ArrayList<Biomolecule>();
		parseFile();
	}	

	/**
	 * Get topology metadata and GROMACS-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception
	{
		MetadataAVUList metadata = super.getMetadata();
		
		return metadata;
	}
	
	/**
	 * Get molecule topology
	 * @return Molecule topology
	 * @throws Exception
	 */
	public List<Biomolecule> getMolecules() {
		return molecules;
	};
	
	/**
	 * Parse GROMACS parameter file
	 * @param filePath
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		IBIOMESFileReader br =  new IBIOMESFileReader(this);
	    String line = null;
	    
        while (( line = br.readLine()) != null)
        {
        	//TODO
        }
        
        br.close();
	}
}
