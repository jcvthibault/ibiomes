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

package edu.utah.bmi.ibiomes.topo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.utah.bmi.ibiomes.dictionaries.AtomicElement;
import edu.utah.bmi.ibiomes.dictionaries.PeriodicTable;
import edu.utah.bmi.ibiomes.topo.bio.Biomolecule;
import edu.utah.bmi.ibiomes.topo.bio.BiomoleculeFactory;
import edu.utah.bmi.ibiomes.topo.bio.Residue;
import edu.utah.bmi.ibiomes.topo.bio.Biomolecule.BiomoleculeType;
import edu.utah.bmi.ibiomes.topo.bio.Residue.ResidueType;

/**
 * Factory for molecular systems (solvent + solute molecules)
 * @author Julien Thibault, University of Utah
 *
 */
public class MolecularSystemFactory {

	private final Logger logger = Logger.getLogger(MolecularSystemFactory.class);
	private double bondLengthMargin = 0.08d;
	
	/**
	 * New molecule factory.
	 */
	public MolecularSystemFactory(){
	}

	/**
	 * New molecular system factory.
	 * @param bondLengthMargin Extra bond length allowed to determine if 2 atoms are connected.
	 */
	public MolecularSystemFactory(double bondLengthMargin){
		this.bondLengthMargin = bondLengthMargin;
	}
	
	/**
	 * Set list of bonded atoms
	 * @param atoms
	 */
	private boolean setBondedAtoms(List<Atom> atoms){
		int a1 = 0;
		int a2 = 0;
		while (a1<atoms.size()){
			ArrayList<Atom> bondedAtoms = new ArrayList<Atom>();
			Atom atom1 = atoms.get(a1);
			if (atom1.getCoordinates()==null) return false;
			a2 = 0;
			//System.out.println(atom1.getName()+atom1.getId()+  " [" +atom1.getElement().getSymbol()+ "]");
			while (a2<atoms.size()){
				if (a1 != a2){
					Atom atom2 = atoms.get(a2);
					if (atom2.getCoordinates()==null) return false;
					double distance = getAtomDistance(atom1, atom2);
					double cutoff = getBondLengthCutoff(atom1, atom2);
					if (distance <= (cutoff+bondLengthMargin)){
						bondedAtoms.add(atom2);
						//System.out.println("\t"+atom2.getName()+atom2.getId() +  " [" +atom2.getElement().getSymbol()+ "] ("+distance +" < " + (cutoff+bondLengthMargin) +")");
					}
				}
				a2++;
			}
			a1++;
			atom1.setBondedAtoms(bondedAtoms);
		}
		return true;
	}
	
	/**
	 * Identify molecules based on atom coordinates. 
	 * @param atoms Atoms
	 * @return List of molecules
	 */
	public MolecularSystem identifyMoleculesUsingAtoms(List<Atom> atoms, boolean setBonds){
		
		MolecularSystem system = new MolecularSystem();
		List<Molecule> molecules = new ArrayList<Molecule>();
		//set atom connectivity
		boolean success = true;
		if (setBonds)
			success = this.setBondedAtoms(atoms);
		if (!success){
			logger.warn("Connectivity information could not be found - single molecule assumed.");
			molecules.add(new Compound(null, atoms)); 
		}
		else {
			while (atoms.size()>0)
			{
				Compound compound = new Compound();
				//visit connected atoms and add reference to molecule
				visitAtom(atoms.get(0), compound);
				
				//remove atoms that were connected
				for (Atom atom : compound.getAtoms()){
					atoms.remove(atom);
				}
				compound.setName(compound.getAtomicCompositionCompact());
				molecules.add(compound);
			}
		}
		system.setSoluteMolecules(molecules);
		return system;
	}
	
	/**
	 * Identify molecules based on atom coordinates. 
	 * @param residues List of residues
	 * @param setBonds Flag to set bonds or not
	 * @return List of molecules
	 * @throws IOException 
	 */
	public MolecularSystem identifyMoleculesUsingResidues(List<Residue> residues, boolean setBonds) throws IOException{
		
		List<Ion> ions = new ArrayList<Ion>();
		List<Molecule> solventMolecules = new ArrayList<Molecule>();
		ArrayList<Molecule> soluteMolecules = new ArrayList<Molecule>();
		MolecularSystem system = new MolecularSystem();
		
		//find atom connectivity based on coordinates if necessary
		boolean success = true;
		if (setBonds){
			for (Residue r : residues){
				success = this.setBondedAtoms(r.getAtoms());
			}
		}
		if (!success){
			logger.warn("Connectivity information could not be found - using residue chains info only to identify molecules.");
			return new MolecularSystem(residues); 
		}
		else {
			int r = 0;
			while (r < residues.size())
			{
				Residue residue = residues.get(r);
				if (residue.getType() == ResidueType.ION){
					ions.add(new Ion(residue));
				}
				else if (residue.getType() == ResidueType.WATER){
					solventMolecules.add(new Water());
				}
				else //build molecule using multiple residues
				{
					boolean connected = true;
					BiomoleculeType moleculeType = BiomoleculeType.UNKNOWN;
					List<Residue> moleculeResidues = new ArrayList<Residue>();
					moleculeResidues.add(residues.get(r));
					while ((r < (residues.size()-1)) && connected)
					{
						residue = residues.get(r);
						Residue nxtResidue = residues.get(r+1);
						ResidueType currentType = residue.getType();
						
						//determine molecule type
						if (currentType == ResidueType.AMINO_ACID) {
							moleculeType = BiomoleculeType.PROTEIN;
						}
						else if (currentType == ResidueType.NUCLEIC_ACID_BASE){
							if (moleculeType != BiomoleculeType.RNA && moleculeType != BiomoleculeType.DNA)
								moleculeType = BiomoleculeType.NUCLEIC_ACID;
						}
						else if (currentType == ResidueType.RNA_BASE){
							moleculeType = BiomoleculeType.RNA;
						}
						else if (currentType == ResidueType.DNA_BASE){
							moleculeType = BiomoleculeType.DNA;
						}
						
						//connected to next residue in the chain?
						connected = checkResidueConnection(nxtResidue, residue);
						
						if (connected){
							moleculeResidues.add(nxtResidue);
							r++;
						}
					}
					
					if (moleculeResidues.size()==1){
						//if single-atom residue with charge -> ion
						if (	residue.getAtoms()!=null 
								&& residue.getAtoms().size()==1
								&& residue.getAtoms().get(0).getCharge()!=0.0)
						{
							ions.add(new Ion(residue.getAtoms().get(0)));
						}
						else{
							Compound compound = new Compound(residue.getCode(), residue.getAtoms());
							soluteMolecules.add(compound);
						}
					}
					else {
						Biomolecule biomolecule = BiomoleculeFactory.getMoleculeFromType(moleculeType, moleculeResidues);
						soluteMolecules.add(biomolecule);
					}
				}
				r++;
			}

			//TODO if molecules appear more than certain time consider as solvent?
			system.setSolventMolecules(solventMolecules);
			system.setIons(ions);
			system.setSoluteMolecules(soluteMolecules);
		}
		return system;
	}
	
	/**
	 * Check if 2 residues are connected by looking at bonded atoms
	 * @param residue1
	 * @param residue2
	 * @return True if there is at least one atom bond between the 2 residues
	 */
	private boolean checkResidueConnection(Residue residue1, Residue residue2) {
		for (Atom r1Atom : residue1.getAtoms()){
			List<Atom> r1AtomBonds = r1Atom.getBondedAtoms();
			if (r1AtomBonds!=null){
				for (Atom bondedAtom : r1AtomBonds){
					if (bondedAtom.getResidue()!=null && bondedAtom.getResidue().equals(residue2))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Visit atoms to assign molecule references 
	 * @param atom Atom
	 * @param compound Molecule
	 */
	private void visitAtom(Atom atom, Compound compound){
		//if this atom is not assigned to a molecule yet
		if (atom.getMolecule()==null){
			compound.addAtom(atom);
			atom.setMolecule(compound);
			//visit boneded atoms
			if (atom.getBondedAtoms()!=null){
				for (Atom bondedAtom : atom.getBondedAtoms()){
					visitAtom(bondedAtom, compound);
				}
			}
		}
	}
	
	/**
	 * Calculate distance between 2 atoms based on their coordinates
	 * @param atom1 Atom 1
	 * @param atom2 Atom 2
	 * @return Distance in angstroms
	 */
	private double getAtomDistance(Atom atom1, Atom atom2) {
		Coordinate3D c1 = atom1.getCoordinates();
		Coordinate3D c2 = atom2.getCoordinates();
		double d = Math.sqrt( Math.pow((c1.getX()-c2.getX()), 2) + Math.pow((c1.getY()-c2.getY()), 2) + Math.pow((c1.getZ()-c2.getZ()), 2));
		return d;
	}

	/**
	 * Return optimal covalent bond distance based on the element types of atom1 
	 * and atom2. When multiple hybridizations are possible the longest possible 
	 * bond length is used.
	 * Unless otherwise noted values taken from:
	 * - Huheey, pps. A-21 to A-34; T.L. Cottrell, "The Strengths of Chemical Bonds," 
	 *       2nd ed., Butterworths, London, 1958; 
	 * - B. deB. Darwent, "National Standard Reference Data Series," National Bureau of Standards, 
	 *       No. 31, Washington, DC, 1970; S.W. Benson, J. Chem. Educ., 42, 502 (1965).
	 * Can be found on the web at:
	 * - http://www.wiredchemist.com/chemistry/data/bond_energies_lengths.html
	 * @param atom1 Atom 1
	 * @param atom2 Atom 2
	 * @return Cutoff for the length of the bond between atoms 1 and 2
	 */
	private double getBondLengthCutoff(Atom atom1, Atom atom2)
	{
		AtomicElement atomType1 = atom1.getElement();
		AtomicElement atomType2 = atom2.getElement();
		int a1=-1, a2=-1;
		if (atomType1!=null)
			a1 = atomType1.getAtomicNumber();
		if (atomType2!=null)
			a2 = atomType2.getAtomicNumber();
		
		// Default cutoff
		double cut = 1.60;
		if (a1 == a2) {
			// Self
			String atomType1Name = atomType1.getName();
			if (atomType1Name.equals(PeriodicTable.HYDROGEN)) 			return 0.74;
			else if (atomType1Name.equals(PeriodicTable.CARBON)) 		return 1.54;
			else if (atomType1Name.equals(PeriodicTable.NITROGEN)) 		return 1.45;
			else if (atomType1Name.equals(PeriodicTable.OXYGEN)) 		return 1.48;
			else if (atomType1Name.equals(PeriodicTable.PHOSPHORUS)) 	return 2.21;
			else if (atomType1Name.equals(PeriodicTable.SULFUR)) 		return 2.05; // S-S gas-phase value; S=S is 1.49
			else logger.warn("Use default bond length cutoff for '"+atom1.getType()+"-"+atom2.getType() + "'");
		} else {
			AtomicElement e1, e2;
			
			if (a1 < a2) {
				e1 = atomType1;
				e2 = atomType2;
			} else {
				e1 = atomType2;
				e2 = atomType1;
			}
			String e1Name = e1.getName();
			String e2Name = e2.getName();
			if (e1Name.equals(PeriodicTable.HYDROGEN)){ // Bonds to H
					if (e2Name.equals(PeriodicTable.CARBON))			return 1.09;
					else if (e2Name.equals(PeriodicTable.NITROGEN))		return 1.01;
					else if (e2Name.equals(PeriodicTable.OXYGEN))		return 0.96;
					else if (e2Name.equals(PeriodicTable.PHOSPHORUS))	return 1.44;
					else if (e2Name.equals(PeriodicTable.SULFUR))		return 1.34;
					else if (e2Name.equals(PeriodicTable.IRON))			return 1.60;
					else logger.warn("Use default bond length cutoff for '"+atom1.getType()+"-"+atom2.getType() + "'");
			}
			else if (e1Name.equals(PeriodicTable.CARBON)){ // Bonds to C
				if (e2Name.equals(PeriodicTable.NITROGEN))				return 1.47;
				else if (e2Name.equals(PeriodicTable.OXYGEN))     		return 1.43;
				else if (e2Name.equals(PeriodicTable.FLUORINE))   		return 1.35;
				else if (e2Name.equals(PeriodicTable.PHOSPHORUS)) 		return 1.84;
				else if (e2Name.equals( PeriodicTable.SULFUR))    		return 1.82;
				else if (e2Name.equals(PeriodicTable.CHLORINE))   		return 1.77;
				else if (e2Name.equals(PeriodicTable.BROMINE))    		return 1.94;
				else if (e2Name.equals(PeriodicTable.IRON))  	  		return 1.90; //TODO Better value?
				else logger.warn("Use default bond length cutoff for '"+atom1.getType()+"-"+atom2.getType() + "'");
			}
			else if (e1Name.equals(PeriodicTable.NITROGEN)){ // Bonds to N
				if (e2Name.equals(PeriodicTable.OXYGEN))    			return 1.40;
				else if (e2Name.equals(PeriodicTable.FLUORINE))  		return 1.36;
				else if (e2Name.equals(PeriodicTable.PHOSPHORUS)) 		return 1.71; // Avg over all nX-pX from gaff.dat
				else if (e2Name.equals(PeriodicTable.SULFUR))    		return 1.68; // Postma & Vos, Acta Cryst. (1973) B29, 915
				else if (e2Name.equals(PeriodicTable.CHLORINE))  		return 1.75;
				else logger.warn("Use default bond length cutoff for '"+atom1+"-"+atom2 + "'");
			}
			else if (e1Name.equals(PeriodicTable.OXYGEN)){ // Bonds to O
				if (e2Name.equals(PeriodicTable.FLUORINE))  			return 1.42;
				else if (e2Name.equals(PeriodicTable.PHOSPHORUS)) 		return 1.63;
				else if (e2Name.equals(PeriodicTable.SULFUR))    		return 1.48;
				else logger.warn("Use default bond length cutoff for '"+atom1.getType()+"-"+atom2.getType() + "'");
			}
			else if (e1Name.equals(PeriodicTable.FLUORINE)){ // Bonds to F
				if (e2Name.equals(PeriodicTable.PHOSPHORUS)) 			return 1.54;
				else if (e2Name.equals(PeriodicTable.SULFUR))    		return 1.56;
				else logger.warn("Use default bond length cutoff for '"+atom1.getType()+"-"+atom2.getType() + "'");
			}
			else if (e1Name.equals(PeriodicTable.PHOSPHORUS)){ // Bonds to P
				if (e2Name.equals(PeriodicTable.SULFUR))  				return 1.86;
				else if (e2Name.equals(PeriodicTable.CHLORINE)) 		return 2.03;
				else logger.warn("Use default bond length cutoff for '"+atom1.getType()+"-"+atom2.getType() + "'");
			}
			else if (e1Name.equals(PeriodicTable.SULFUR)){ // Bonds to S
				if (e2Name.equals(PeriodicTable.CHLORINE)) 				return 2.07;
				else logger.warn("Use default bond length cutoff for '"+atom1.getType()+"-"+atom2.getType() + "'");
			}
			else logger.warn("Use default bond length cutoff for '"+atom1.getType()+"-"+atom2.getType() + "'");
		}
		return cut;
	}
}
