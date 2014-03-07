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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.log4j.Logger;

import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Molecule;

/**
 * Molecule residue 
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="residues")
public class Residue {

	private final Logger logger = Logger.getLogger(Residue.class);
	
	private static final String TERM_RESIDUE_REGEX = "(TER)";
	private static final String ION_REGEX = "(C[lL](\\-)?)|(N[aA](\\+)?)|(IP)|(C[aA](\\+)?)|(Zn(\\+)?)|(K(\\+)?)|(M[gG](\\+)?)";
	private static final String WATER_REGEX = "(WAT)|(SOL)|(H2O)|(HOH)|(TIP.*)";
	private static final String RNA_BASE_REGEX = "((R[AUCG][35]?))";
	private static final String DNA_BASE_REGEX = "((D[ATCG][35]?))";
	private static final String AMINO_ACID_REGEX = "((ACE)|(ALA)|(ARG)|(ASN)|(ASP)|(CYS)|(GLN)|(GLU)|(GLY)|(HID)|(HIE)|(HIS)|(ILE)|(LEU)|(LYS)|(MET)|(NME)|(PHE)|(PRO)|(SER)|(THR)|(TRP)|(TYR)|(VAL))";
	private static final String NUCLEOTIDE_REGEX = "([ACGTU][35]?)";
	
	/**
	 * Residue type enumeration
	 * @author Julien Thibault, University of Utah
	 *
	 */
	public enum ResidueType 
	{ 
		AMINO_ACID("Amino acid"), 
		DNA_BASE("DNA nucleotide"), 
		RNA_BASE("RNA nucleotide"), 
		NUCLEIC_ACID_BASE("Nucleotide"), 
		WATER("Water"), ION("Ion"), 
		UNKNOWN("Unknown"), 
		TERMINAL("Terminal base"); 
		private String type;
		ResidueType(String type){
			this.type=type;
		}
		@Override
		public String toString(){
			return type;
		}
	};
	
	private ArrayList<Atom> atoms;
	private String _code;
	private String _name;
	private String standardCode;
	private ResidueType type;
	private boolean isStandard;
	private Molecule molecule;
	
	/**
	 * Create residue with given code (infers type of residue)
	 * @param code Code of the residue
	 * @throws CompressorException 
	 * @throws IOException 
	 */
	public Residue(String code) {
		atoms = new ArrayList<Atom>();
		this.setCode(code);
		_name = null;
	}
	
	/**
	 * Get residue code
	 * @return Residue code
	 */
	@XmlAttribute(name="code")
	public String getCode(){
		return _code;
	}
	
	/**
	 * Set residue code
	 * @param resCode residue code
	 * @throws CompressorException 
	 */
	public void setCode(String resCode) {
		
		_code = resCode;
		
		try {
			ResidueCodeMapper codeMapper = ResidueCodeMapper.getInstance();
			this.standardCode = codeMapper.getStandardCode(_code);
		} catch (IOException e){
			logger.error("Config file with residue list not found! (" + e.getMessage() + ")");
		} catch (CompressorException e){
			logger.error("Config file with residue list not readable! (" + e.getMessage() + ")");
		}
		
		if (resCode.matches(ION_REGEX)){
			this.type = ResidueType.ION;
			this.setIsStandard(true);
			this.standardCode = resCode;
		}
		else if (resCode.matches(WATER_REGEX)){
			this.type = ResidueType.WATER;
			this.setIsStandard(true);
			this.standardCode = "WAT";
		}
		else if (resCode.matches(RNA_BASE_REGEX)){
			this.type = ResidueType.RNA_BASE;
			this.setIsStandard(standardCode!=null);
			
		}
		else if (resCode.matches(DNA_BASE_REGEX)){
			this.type = ResidueType.DNA_BASE;
			this.setIsStandard(standardCode!=null);
		}
		else if (resCode.matches(AMINO_ACID_REGEX)){
			this.type = ResidueType.AMINO_ACID;
			this.setIsStandard(standardCode!=null);
		}
		else if (resCode.matches(NUCLEOTIDE_REGEX)){
			this.type = ResidueType.NUCLEIC_ACID_BASE;
			this.setIsStandard(standardCode!=null);
		}
		else if (resCode.matches(TERM_RESIDUE_REGEX)){
			this.type = ResidueType.TERMINAL;
			this.setIsStandard(standardCode!=null);
		}
		else {
			this.type = ResidueType.UNKNOWN;
			this.setIsStandard(standardCode!=null);
		}
	}

	@XmlAttribute(name="type")
	public ResidueType getType() {
		return type;
	}
	
	public void setType(ResidueType type) {
		this.type = type;
	}
	/**
	 * Get residue name
	 * @return Residue name
	 */
	@XmlAttribute(name="name")
	public String getName(){
		return _name;
	}
	/**
	 * Set residue name
	 */
	public void setName(String resName) {
		_name = resName;
	}

	@XmlAttribute(name="standardCode")
	public String getStandardCode() {
		return standardCode;
	}

	/**
	 * private no-arg constructor for JAXB
	 */
	@SuppressWarnings("unused")
	private Residue() {
	}
	
	/**
	 * Add new atom to the residue
	 * @param a Atom
	 */
	public void addAtom(Atom a){
		atoms.add(a);
		a.setResidue(this);
	}
	
	/**
	 * Get list of atoms
	 * @return List of atoms
	 */
	@XmlElementWrapper(name="atoms")
	@XmlElement(name="atom")
	public List<Atom> getAtoms(){
		return atoms;
	}
	
	/**
	 * Get string representing the chain of atoms
	 * @return List of atoms
	 */
	@XmlElement(name="atomChain")
	public String getChainOfAtoms(){
		String chain = "";
		for (Atom a : atoms){
			chain += a.getName() + " ";
		}
		return chain.trim();
	}
	
	/**
	 * String representation of the residue
	 */
	public String toString(){
		
		return (this._name + " [" + this._code+ "]: " + this.atoms.size() + " atoms -> " + this.getChainOfAtoms());
	}

	/**
	 * Check whether the residue is standard or not
	 * @return True if the residue is standard
	 * @throws IOException 
	 */
	@XmlAttribute(name="standard")
	public boolean isStandard() {
		return isStandard;
	}
	
	/**
	 * 
	 * @param isStandard
	 */
	public void setIsStandard(boolean isStandard){
		this.isStandard = isStandard;
	}

	/**
	 * Get reference to molecule containing this atom
	 * @return Molecule
	 */
	public Molecule getMolecule() {
		return molecule;
	}

	/**
	 * Set containing molecule
	 * @param molecule Molecule
	 */
	public void setMolecule(Molecule molecule) {
		this.molecule = molecule;
	}
}
