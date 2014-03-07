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

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.dictionaries.AtomicElement;
import edu.utah.bmi.ibiomes.dictionaries.PeriodicTable;
import edu.utah.bmi.ibiomes.topo.bio.Residue;

/**
 * Atom (type, physical properties and related modeling properties)
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="atom")
public class Atom {

	private int id;
	private String name;
	//TODO change to DoubleQuantity to keep track of unit
	private double mass;
	//TODO change to FloatQuantity to keep track of unit
	private float charge;
	private String type;
	private AtomicElement element;
	private Residue residue;
	private Molecule molecule;
	private Coordinate3D coordinates;
	private List<Atom> bondedAtoms;
	
	@SuppressWarnings("unused")
	private Atom(){
	}
	/**
	 * Create new atom
	 * @param id
	 * @param name
	 * @param charge
	 * @param mass
	 * @param type
	 */
	public Atom(int id, String name, float charge, double mass, String type){
		this.init(id, name, charge, mass, type, null);
	}
	
	/**
	 * Create new atom
	 * @param id Atom ID
	 * @param name Atom name
	 * @param type Atom type
	 */
	public Atom(int id, String name, String type){
		this.init(id, name, 0, 0, type, null);
	}
	
	/**
	 * Create new atom
	 * @param id Atom ID
	 * @param name Atom name
	 * @param charge Atom charge
	 * @param mass Atom mass
	 * @param type Atom type
	 * @param element Element definition
	 */
	public Atom(int id, String name, float charge, double mass, String type, AtomicElement element){
		this.init(id, name, charge, mass, type, element);
	}
	
	private void init(int id, String name, float charge, double mass, String type, AtomicElement element){
		this.id = id;
		this.name = name;
		this.mass = mass;
		this.charge = charge;
		this.element = element;
		this.type = type;
		if (this.element == null){
			PeriodicTable table = PeriodicTable.getInstance();
			AtomicElement elementDef = table.findElementForAtom(this);
			if (elementDef != null)
				this.element = elementDef;
		}
	}
	
	@XmlAttribute(name="id")
	public int getId() {
		return id;
	}
	
	/**
	 * Get atom name
	 * @return Atom name
	 */
	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}

	/**
	 * Gt atom mass
	 * @return Atom mass
	 */
	@XmlAttribute(name="mass")
	public double getMass() {
		return mass;
	}

	/**
	 * Set atom mass
	 * @param mass Atom mass
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	/**
	 * Get atom charge
	 * @return Atom charge
	 */
	@XmlAttribute(name="charge")
	public float getCharge() {
		return this.charge;
	}
	
	/**
	 * Set atom charge
	 * @param charge Atom charge
	 */
	public void setCharge(float charge) {
		this.charge = charge;		
	}

	/**
	 * Get atom type
	 * @return Atom type
	 */
	@XmlAttribute(name="type")
	public String getType() {
		return type;
	}

	/**
	 * Set atom type
	 * @param type Atom type
	 */
	public void setType(String type) {
		this.type = type;
		if (this.element == null){
			PeriodicTable table = PeriodicTable.getInstance();
			AtomicElement elementDef = table.findElementForAtom(this);
			if (elementDef != null)
				this.element = elementDef;
		}
	}
	
	/**
	 * Get element definition for this atom
	 * @return Element
	 */
	@XmlElement
	public AtomicElement getElement() {
		return element;
	}

	/**
	 * Set element definition for this atom
	 * @param element Element
	 */
	public void setElement(AtomicElement element) {
		this.element = element;
	}
	
	/**
	 * Get 3D coordinates of atom
	 * @return 3D coordinates
	 */
	@XmlElement(name="coordinates")
	public Coordinate3D getCoordinates(){
		return coordinates;
	}

	/**
	 * Set initial 3D coordinates
	 * @param coords 3D coordinates.
	 */ 
	public void setCoordinates(Coordinate3D coords){
		coordinates = coords;
	}
	
	/**
	 * Set initial 3D coordinates
	 * @param x X
	 * @param y Y
	 * @param z Z
	 */
	public void setCoordinates(double x, double y, double z){
		coordinates = new Coordinate3D(x,y,z);
	}

	/**
	 * Get the residue referencing this atom
	 * @return Residue
	 */
	@XmlTransient
	public Residue getResidue() {
		return this.residue;
	}

	/**
	 * Set residue
	 * @param residue
	 */
	public void setResidue(Residue residue) {
		this.residue = residue;
	}
	
	/**
	 * Get the molecule referencing this atom
	 * @return Molecule
	 */
	@XmlTransient
	public Molecule getMolecule() {
		return this.molecule;
	}

	/**
	 * Set reference to molecule containing this atom
	 * @param molecule Molecule
	 */
	public void setMolecule(Molecule molecule) {
		this.molecule = molecule;
	}
	
	/**
	 * Get list of atoms bonded to this atom
	 * @return List of atoms
	 */
	@XmlTransient
	public List<Atom> getBondedAtoms() {
		return bondedAtoms;
	}

	/**
	 * Set list of atoms bonded to this atom
	 * @param bondedAtoms List of atoms
	 */
	public void setBondedAtoms(List<Atom> bondedAtoms) {
		this.bondedAtoms = bondedAtoms;
	}
	
	public String toString(){
		String string = "name="+this.name + " | type=" + this.type + " | mass=" + this.mass + " | chg=" + this.charge;
		if (this.residue!=null)
			string += " | residue=" + this.residue.getName();
		if (this.element!=null)
			string += " | elt=" + this.element.getSymbol();
		return string;
	}

}
