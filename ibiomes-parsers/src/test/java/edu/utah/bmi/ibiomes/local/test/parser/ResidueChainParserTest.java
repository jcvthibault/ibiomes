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

import java.io.IOException;
import java.util.List;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.topo.bio.ResidueChainParser;

/**
 * Test suite for residue chain parser
 * @author Julien Thibault, University of Utah
 *
 */
public class ResidueChainParserTest
{
	private final Logger logger = Logger.getLogger(ResidueChainParserTest.class);
	
	private String[] chains = {
			"RG5 RC RC RC RG RG RA RU RA RG RC RU RC RA RG RU RC RG RG RU RA RG RA RG RC RA PSU " +
			"RC RA RG RA RC RU U8U RU RU T6A RA PSU RC RU RG RA RG RG RG RU RC RC RA RG RG RG RU PSU RC " +
			"RA RA RG RU RC RC RC RU RG RU RU RC RG RG RG RC RG RC RC RA3 RG5 RU RG RU RG RA RC RU RC RU " +
			"RG RG RU RA RA RC RU RA RG RA RG RA RU RC RC RC RU RC RA RG RA RC RC RA RC RU RC RU RA RG " +
			"RA RC RG RG RU RG RU RA RA RA RA RA RU RC RU RC RU RA RG RC RA RG RU RG RG RC RG RC RC RC " +
			"RG RA RA RC RA RG RG RG RA RC RU RU RU RA RA RA RG RU RG RA RA RA RG RU RA RA RC RA RG RG " +
			"RG RA3",
			
			"RG5 RG RC RU RC RG RU RG RU RA RG RC RU RC RA RU RU RA RG RC RU RC RC RG RA RG RC RC3 " +
			"SER GLY PRO ARG PRO ARG GLY THR ARG GLY LYS GLY ARG ARG ILE ARG ARG",
			
			"RG5 RG RC RU RC RG RU RG RU RA RG RC RU RC RA RU WTF1 RA RG RC RU RC RC RG RA RG RC RC3 " +
			"SER GLY WTF2 ARG PRO ARG GLY THR ARG GLY LYS GLY ARG ARG ILE ARG ARG",
			
			"MET GLN ILE PHE VAL LYS THR LEU THR GLY LYS THR ILE THR LEU GLU VAL GLU PRO SER ASP THR " +
			"ILE GLU ASN VAL LYS ALA LYS ILE GLN ASP LYS GLU GLY ILE PRO PRO ASP GLN GLN ARG LEU ILE PHE ALA GLY" +
			" LYS GLN LEU GLU ASP GLY ARG THR LEU SER ASP TYR ASN ILE GLN LYS GLU SER THR LEU HSE LEU VAL LEU ARG" +
			" LEU ARG GLY GLY",
			
			"G5 G C U C G U G U A G C U C A U U A G C U C C G A G C C3 " +
			"SER GLY PRO ARG PRO ARG GLY THR ARG GLY LYS GLY ARG ARG ILE ARG ARG",
			
			"G5 G C A G C C C G A G C C3 " +
			"SER GLY PRO ARG PRO ARG GLY THR ARG GLY LYS GLY ARG ARG ILE ARG ARG",
			
			"G5 G C T C G T G T A G C T C A T T A G C T C C G A G C C3 " +
			"SER GLY PRO ARG PRO ARG GLY THR ARG GLY LYS GLY ARG ARG ILE ARG ARG",
			
			"G5 G C T C G T G T A G C T C A T T A G C T C C G A G C C3"
		};

	public ResidueChainParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testResidueChains() throws IOException, CompressorException
	{
		for (int c=0;c<chains.length;c++)
		{
			logger.debug("========================================================");
			logger.info(chains[c]);
			ResidueChainParser parser = new ResidueChainParser(chains[c]);
			logger.debug("\nMolecule types: "+parser.getMoleculeTypes());
			logger.debug("\nMolecule chains:");
			List<String> chains = parser.parseChains();
			String chainsMol = "";
			for (String ch : chains){
				chainsMol += ch;
			}
			logger.debug(chainsMol);
			logger.debug("\nNon-standard residues:");
			List<String> nonStdResidues = parser.findNonStandardResidues();
			String rsList = "";
			for (String res : nonStdResidues){
				rsList += res + " ";
			}
			logger.debug(rsList);
		}
	}
}
