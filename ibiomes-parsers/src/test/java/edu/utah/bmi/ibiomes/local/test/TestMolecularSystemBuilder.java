package edu.utah.bmi.ibiomes.local.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.Molecule;
import edu.utah.bmi.ibiomes.topo.bio.Biomolecule;
import edu.utah.bmi.ibiomes.topo.bio.Residue;

public class TestMolecularSystemBuilder
{
	private final Logger logger = Logger.getLogger(TestMolecularSystemBuilder.class);
	
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
			
			"MET GLN ILE PHE VAL LYS THR LEU THR GLY LYS THR ILE THR LEU GLU VAL GLU PRO SER ASP THR " +
			"TER" +
			" ILE GLU ASN VAL LYS ALA LYS ILE GLN ASP LYS GLU GLY ILE PRO PRO ASP GLN GLN ARG LEU ILE PHE ALA GLY" +
			" LYS GLN LEU GLU ASP GLY ARG THR LEU SER ASP TYR ASN ILE GLN LYS GLU SER THR LEU HSE LEU VAL LEU ARG" +
			" LEU ARG GLY GLY",
			
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
			
			"G5 G C T C G T G T A G C T C A T T A G C T C C G A G C C3 WAT WAT WAT WAT WAT Na+ Na+", 
			
			"RES1 RES2"
		};
	
	private String[] normalizedChains = {
			"GCCCGGAUAGCUCAGUCGGUAGAGCA?" +
			"CAGACU?UU?A?CUGAGGGUCCAGGGU?C" +
			"AAGUCCCUGUUCGGGCGCCAGUGUGACUCU" +
			"GGUAACUAGAGAUCCCUCAGACCACUCUAG" +
			"ACGGUGUAAAAAUCUCUAGCAGUGGCGCCC" +
			"GAACAGGGACUUUAAAGUGAAAGUAACAGG" +
			"GA",
			
			"GGCUCGUGUAGCUCAUUAGCUCCGAGCC" +
			"SER GLY PRO ARG PRO ARG GLY THR ARG GLY LYS GLY ARG ARG ILE ARG ARG",
			
			"MET GLN ILE PHE VAL LYS THR LEU THR GLY LYS THR ILE THR LEU GLU VAL GLU PRO SER ASP THR " +
			"TER" +
			" ILE GLU ASN VAL LYS ALA LYS ILE GLN ASP LYS GLU GLY ILE PRO PRO ASP GLN GLN ARG LEU ILE PHE ALA GLY" +
			" LYS GLN LEU GLU ASP GLY ARG THR LEU SER ASP TYR ASN ILE GLN LYS GLU SER THR LEU HSE LEU VAL LEU ARG" +
			" LEU ARG GLY GLY",
			
			"MET GLN ILE PHE VAL LYS THR LEU THR GLY LYS THR ILE THR LEU GLU VAL GLU PRO SER ASP THR " +
			"ILE GLU ASN VAL LYS ALA LYS ILE GLN ASP LYS GLU GLY ILE PRO PRO ASP GLN GLN ARG LEU ILE PHE ALA GLY" +
			" LYS GLN LEU GLU ASP GLY ARG THR LEU SER ASP TYR ASN ILE GLN LYS GLU SER THR LEU HSE LEU VAL LEU ARG" +
			" LEU ARG GLY GLY",
			
			"GGCUCGUGUAGCUCAUUAGCUCCGAGCC " +
			"SER GLY PRO ARG PRO ARG GLY THR ARG GLY LYS GLY ARG ARG ILE ARG ARG",
			
			"GGCAGCCCGAGCC " +
			"SER GLY PRO ARG PRO ARG GLY THR ARG GLY LYS GLY ARG ARG ILE ARG ARG",
			
			"GGCTCGTGTAGCTCATTAGCTCCGAGCC " +
			"SER GLY PRO ARG PRO ARG GLY THR ARG GLY LYS GLY ARG ARG ILE ARG ARG",
			
			"GGCTCGTGTAGCTCATTAGCTCCGAGCC",
			
			"??"
		};
	
	@Test
	public void testResidueChains() throws Exception
	{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE,true);
		for (int c=0;c<chains.length;c++)
		{
			logger.debug("========================================================");
			logger.info(chains[c]);
			MolecularSystem system = new MolecularSystem(buildResidueList(chains[c].split(" ")));
			logger.info(system.toString());
			for (Molecule mol : system.getSoluteMolecules()){
				logger.info("   "  + mol.toString());
				if (mol instanceof Biomolecule){
					String residuesStr = "        ";
					Biomolecule biomol = (Biomolecule)mol;
					for (Residue r : biomol.getResidues()){
						residuesStr += r.getCode() + " ";
					}
					logger.debug(residuesStr);
				}
			}
		}
	}
	
	private static List<Residue> buildResidueList(String[] residueChain) throws IOException{
		List<Residue> residues = new ArrayList<Residue>();
		for (int r=0;r<residueChain.length;r++){
			residues.add(new Residue(residueChain[r]));
		}
		return residues;
	}
}
