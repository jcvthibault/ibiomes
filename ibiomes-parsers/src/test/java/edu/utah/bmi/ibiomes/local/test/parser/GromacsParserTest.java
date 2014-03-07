/* iBIOMES - Integrated Biomolecular Simulations
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

package edu.utah.bmi.ibiomes.local.test.parser;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.gromacs.GROMACSIncludeTopologyFile;
import edu.utah.bmi.ibiomes.parse.chem.gromacs.GROMACSParameterInputFile;
import edu.utah.bmi.ibiomes.parse.chem.gromacs.GROMACSSystemTopologyFile;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.topo.Molecule;

public class GromacsParserTest {
	
	private final Logger logger = Logger.getLogger(GromacsParserTest.class);
	
	String[] mdpFiles = {
			"FAD/input/fad.heatup-300K.mdp", 
			"methanol/grompp.mdp",  
			"nmr1/grompp.mdp", 
			"nmr2/grompp.mdp",
			"speptide/em.mdp",
			"speptide/pr.mdp",
			"speptide/full.mdp", 
			"qmmm_protein/LT.mdp",
			"qmmm_pyp/pyp.mdp",
			"water/grompp.mdp"
		};
	String[] itpFiles = {
			"FAD/input/ions.itp", 
			"FAD/input/spc.itp", 
			"FAD/data/FAD.itp",
			"methanol/methanol.itp"
		};
	String[] topoFiles = {
			"FAD/input/fad.top",
			"nmr1/topol.top",
			"nmr2/topol.top",
			"methanol/topol.top",
			"water/topol.top"
		};
	String[] collections = {"FAD", "nmr1", "nmr2", "methanol", "water", "qmmm_protein", "qmmm_pyp"};

	public GromacsParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testGromacsMDP() throws Exception{
		
		MetadataAVUList md = null;
		LocalFileFactory ffactory = LocalFileFactory.instance();
		
		for (int f=0; f<mdpFiles.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + "/gromacs/" + mdpFiles[f];
			System.out.println("Parsing GROMACS MDP file " + inputFile);
			GROMACSParameterInputFile mdp  = new GROMACSParameterInputFile(inputFile);
			md = mdp.getMetadata();
			logger.info(md.toString());
			
			LocalFile inpGen  = ffactory.getFile(inputFile, Software.GROMACS);
			md = inpGen.getMetadata();
			logger.debug(md.toString());
			logger.debug("----------------------------------------");
		}
	}

	@Test
	public void testGromacsITP() throws Exception{

		MetadataAVUList md = null;
		LocalFileFactory ffactory = LocalFileFactory.instance();
		
		for (int f=0; f<itpFiles.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + "/gromacs/" + itpFiles[f];
			System.out.println("Parsing GROMACS ITP file " + inputFile);
			GROMACSIncludeTopologyFile itpFile  = new GROMACSIncludeTopologyFile(inputFile);
			md = itpFile.getMetadata();
			logger.debug(md.toString());
			List<Molecule> molecules = itpFile.getMolecules();
			for (Molecule mol : molecules){
				logger.debug(mol.getMetadata().toString());
			}
			md = itpFile.getMetadata();
			logger.info(md.toString());
			
			LocalFile inpGen  = ffactory.getFile(inputFile, Software.GROMACS);
			md = inpGen.getMetadata();
			logger.debug(md.toString());
			logger.debug("----------------------------------------");
		}
	}

	@Test
	public void testGromacsTopo() throws Exception{
		
		MetadataAVUList md = null;
		LocalFileFactory ffactory = LocalFileFactory.instance();
		
		for (int f=0; f<topoFiles.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + "/gromacs/" + topoFiles[f];
			System.out.println("Parsing GROMACS topology file " + inputFile);
			GROMACSSystemTopologyFile top  = new GROMACSSystemTopologyFile(inputFile);
			md = top.getMetadata();
			logger.info(md.toString());
			
			LocalFile inpGen  = ffactory.getFile(inputFile, Software.GROMACS);
			logger.debug("----------------------------------------");
			md = inpGen.getMetadata();
			logger.debug(md.toString());
		}
	}

	@Test
	public void testGromacsCollection() throws Exception
	{
		MetadataAVUList md = null;
		
		for (int c=0; c<collections.length; c++)
		{
			String inputDir = TestCommon.TEST_DATA_DIR + "/gromacs/" + collections[c];
			System.out.println("Parsing GROMACS experiment " + inputDir);
			ExperimentFactory factory = new ExperimentFactory(inputDir);
			LocalDirectory coll = factory.parseDirectoryForMetadata(Software.GROMACS, null, null);
			md = coll.getMetadata();
			logger.info(md.toString());
		}
	}
}
