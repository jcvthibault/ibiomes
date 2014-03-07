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

import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractMoleculeDefinitionFile;

/**
 * GROMACS include topology file (.itp).
 * This file format is used to define individual (or multiple) components 
 * of a topology as a separate file. This is particularly of use if there 
 * is a molecule that is used frequently. The file contains only the information 
 * for the particular molecule.
 * These .itp files are then included (using the include file mechanism) within 
 * the .top file to define the entire system's topology.
 * 
 * @author Julien Thibault, University of Utah
 *
 */
public class GROMACSIncludeTopologyFile extends AbstractMoleculeDefinitionFile
{
	private static final long serialVersionUID = 244857191937798175L;
	
	/**
	 * Default constructor.
	 * @param pathname Topology file path.
	 * @throws Exception
	 */
	public GROMACSIncludeTopologyFile(String pathname) throws Exception
	{
		super(pathname, LocalFile.FORMAT_GROMACS_ITP);
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
	 * Parse GROMACS parameter file
	 * @param filePath
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		try{
			List<GROMACSTopologySection> sections = GROMACSTopologyReader.readSections(new IBIOMESFileReader(this));
			this.molecules = GROMACSTopologyReader.readMolecules(sections);
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a GROMACS Include Topology file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
		}
	}
}
