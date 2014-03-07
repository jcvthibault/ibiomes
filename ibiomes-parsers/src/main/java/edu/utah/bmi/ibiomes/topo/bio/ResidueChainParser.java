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

package edu.utah.bmi.ibiomes.topo.bio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.compressors.CompressorException;

import edu.utah.bmi.ibiomes.topo.MolecularSystem;

/**
 * Class used to parse and manipulate residue chains.
 * @author Julien Thibault
 *
 */
public class ResidueChainParser 
{
	private String[] _residues;
	private String _chain;
	
	private static final String UNKNOWN_RESIDUE = "?";
	
	private static final String PROTEIN = "Protein";
	private static final String DNA = "DNA";
	private static final String RNA = "RNA";
	private static final String NUCLEIC_ACID = "Nucleic acid";
	private static final String UNKNOWN = "UNKNOWN";

	private ResidueCodeMapper _residueCodeMap;
	
	public static final String ION_REGEX = "(C[lL](\\-)?)|(M[gG](\\+)?)|(N[aA](\\+)?)|(IP)|(C[aA](\\+)?)|(Zn(\\+)?)|(K\\+)";
	public final static String WATER_REGEX = "(WAT)|(SOL)|(H2O)|(HOH)|(TIP.*)";
	
	protected static final String rnaBaseRegex = "((R[AUCG][35]?))";
	protected static final String dnaBaseRegex = "((D[ATCG][35]?))";
	protected static final String aminoAcidRegex = "((ACE)|(ALA)|(ARG)|(ASN)|(ASP)|(CYS)|(GLN)|(GLU)|(GLY)|(HID)|(HIE)|(HIS)|(ILE)|(LEU)|(LYS)|(MET)|(NME)|(PHE)|(PRO)|(SER)|(THR)|(TRP)|(TYR)|(VAL))";
	protected static final String nucleotideBaseRegex = "([ACGTU][35]?)";
	
	protected static final String rnaRegex = "(^R[AUCG][35]? )+";
	protected static final String dnaRegex = "(^D[ATCG][35]? )+";
	protected static final String nucleotideRegex = "(^[ACGTU][35]? )+";
	protected static final String proteinRegex = "((((ACE)|(ALA)|(ARG)|(ASN)|(ASP)|(CYS)|(GLN)|(GLU)|(GLY)|(HID)|(HIE)|(HIS)|(ILE)|(LEU)|(LYS)|(MET)|(NME)|(PHE)|(PRO)|(SER)|(THR)|(TRP)|(TYR)|(VAL))( )?)+)";
	
	/**
	 * Constructs residue chain from a string
	 * @param chain
	 * @throws IOException 
	 * @throws CompressorException 
	 */
	public ResidueChainParser(String chain) throws IOException, CompressorException {
		_chain = chain;
		_residues = chain.split("\\s+");
		_residueCodeMap = ResidueCodeMapper.getInstance();
	}
	
	/**
	 * Constructs residue chain from list of residue objects
	 * @param residues List of residues
	 * @throws IOException 
	 * @throws CompressorException 
	 */
	public ResidueChainParser(List<Residue> residues) throws IOException, CompressorException
	{
		_residues = new String[residues.size()];
		_chain = "";
		
		int r = 0;
		for (Residue res : residues){
			_residues[r] = res.getCode();
			_chain += res.getCode() + " ";
			r++;
		}
		_chain = _chain.trim();
		_residueCodeMap = ResidueCodeMapper.getInstance();
	}
	
	/**
	 * Get chain of residues
	 * @return String representing the chain of residues
	 */
	public String getResidueChain()
	{
		return this._chain;
	}
	
	/**
	 * Get array of residues
	 * @return Array of residue codes
	 */
	public String[] getResidueList()
	{
		return this._residues;
	}
	
	
	/**
	 * Get normalized chain of residues
	 * @return chain of residues
	 * @throws IOException 
	 */
	public List<String> parseChains() throws IOException
	{		
		List<String> normalizedChains = new ArrayList<String>();
		
		String normalizedChain = new String();
		String currentType = UNKNOWN;

		for (int r=0; r<_residues.length; r++)
		{
			//AMINO ACID
			if (_residues[r].matches(aminoAcidRegex)){
				if (!currentType.equals(PROTEIN) && !currentType.equals(UNKNOWN)){
					normalizedChains.add(currentType + ": " + normalizedChain);
					normalizedChain = new String();
				}
				currentType = PROTEIN;
				normalizedChain += " " + _residues[r];
			}
			//Nucleic acid: RNA or DNA
			else if (_residues[r].matches(nucleotideBaseRegex) )
			{
				currentType = NUCLEIC_ACID;
				normalizedChain += _residues[r].replaceAll("[35]", "");
				if (_residues[r].endsWith("3")){
					normalizedChains.add(currentType + ": " + normalizedChain);
					normalizedChain = new String();
					currentType = UNKNOWN;
				}
			}
			//RNA
			else if (_residues[r].matches(rnaBaseRegex)){
				currentType = RNA;
				normalizedChain += _residues[r].replaceAll("[R35]", "");
				if (_residues[r].endsWith("3")){
					normalizedChains.add(currentType + ": " + normalizedChain);
					normalizedChain = new String();
					currentType = UNKNOWN;
				}
			}
			//DNA
			else if (_residues[r].matches(dnaBaseRegex)){
				currentType = DNA;
				normalizedChain += _residues[r].replaceAll("[D35]", "");
				if (_residues[r].endsWith("3")){
					normalizedChains.add(currentType + ": " + normalizedChain);
					normalizedChain = new String();
					currentType = UNKNOWN;
				}
			}
			//TERMINAL RESIDUE
			else if (_residues[r].matches("TER")){
				normalizedChains.add(currentType + ": " + normalizedChain);
				normalizedChain = new String();
			}
			//UNKNOWN RESIDUE
			else {
				if (currentType.equals(RNA) || currentType.equals(DNA) || currentType.equals(NUCLEIC_ACID))
					normalizedChain += UNKNOWN_RESIDUE;
				else normalizedChain += " " + UNKNOWN_RESIDUE;
			}
		}
		
		if (normalizedChain.length()>0 && !normalizedChain.trim().equals(UNKNOWN_RESIDUE))
			normalizedChains.add(currentType + ":" + normalizedChain);
		
		return normalizedChains;
	}
	
	/**
	 * Get type of molecule (DNA, RNA and/or Protein)
	 * @return type of molecule
	 */
	public ArrayList<String> getMoleculeTypes()
	{
		ArrayList<String> moleculeTypes = new ArrayList<String>();
		String residueChain = this.getResidueChain();
		
		if (residueChain.length()==0){
			moleculeTypes.add("Unknown");
		}
		
		if (residueChain.matches(".*" + nucleotideRegex + ".*"))
		{
			if (residueChain.matches(".*" + dnaRegex + ".*")){
				moleculeTypes.add(MolecularSystem.TYPE_DNA);
			}
			else if (residueChain.matches(".*" + rnaRegex + ".*")){
				moleculeTypes.add(MolecularSystem.TYPE_RNA);
			}
			else moleculeTypes.add(MolecularSystem.TYPE_NUCLEIC_ACID);
		}
		else {
			if (residueChain.matches(".*" + dnaRegex + ".*")){
				moleculeTypes.add(MolecularSystem.TYPE_DNA);
			}
			if (residueChain.matches(".*" + rnaRegex + ".*")){
				moleculeTypes.add(MolecularSystem.TYPE_RNA);
			}
		}
		if (residueChain.matches(".*" + proteinRegex + ".*")){
			moleculeTypes.add(MolecularSystem.TYPE_PROTEIN);
		}
		return moleculeTypes;
	}
	

	/**
	 * Finde non-standard residues in the chain
	 * @return List of non-standard residues
	 */
	public List<String> findNonStandardResidues(){
		
		ArrayList<String> list = new ArrayList<String>();
		for (String res : _residues)
		{
			if (_residueCodeMap.getStandardCode(res) == null)
			{
				list.add(res);
			}
		}
		return list;
	}
}
