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

package edu.utah.bmi.ibiomes.dictionaries;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import edu.utah.bmi.ibiomes.topo.Atom;

/**
 * Periodic table information and lookup methods.
 * @author Julien Thibault
 *
 */
public class PeriodicTable {

	private final Logger logger = Logger.getLogger(PeriodicTable.class);
	
	private HashMap<String, AtomicElement> elementsHashBySymbol;
	private HashMap<String, AtomicElement> elementsHashByName;
	private HashMap<Integer, AtomicElement> elementsHashByAtomicNumber;
	
	private static PeriodicTable table;	
	
	public static final String HYDROGEN = "Hydrogen";
	public static final String HELIUM = "Helium";
	public static final String LITHIUM = "Lithium";
	public static final String BERYLLIUM = "Beryllium";
	public static final String BORON = "Boron";
	public static final String CARBON = "Carbon";
	public static final String NITROGEN = "Nitrogen";
	public static final String OXYGEN = "Oxygen";
	public static final String FLUORINE = "Fluorine";
	public static final String NEON = "Neon";
	public static final String SODIUM = "Sodium";
	public static final String MAGNESIUM = "Magnesium";
	public static final String ALUMINIUM = "Aluminium";
	public static final String SILICON = "Silicon";
	public static final String PHOSPHORUS = "Phosphorus";
	public static final String SULFUR = "Sulfur";
	public static final String CHLORINE = "Chlorine";
	public static final String ARGON = "Argon";
	public static final String POTASSIUM = "Potassium";
	public static final String CALCIUM = "Calcium";
	public static final String SCANDIUM = "Scandium";
	public static final String TITANIUM = "Titanium";
	public static final String VANADIUM = "Vanadium";
	public static final String CHROMIUM = "Chromium";
	public static final String MANGANESE = "Manganese";
	public static final String IRON = "Iron";
	public static final String COBALT = "Cobalt";
	public static final String NICKEL = "Nickel";
	public static final String COPPER = "Copper";
	public static final String ZINC = "Zinc";
	public static final String GALLIUM = "Gallium";
	public static final String GERMANIUM = "Germanium";
	public static final String ARSENIC = "Arsenic";
	public static final String SELENIUM = "Selenium";
	public static final String BROMINE = "Bromine";
	public static final String KRYPTON = "Krypton";
	public static final String RUBIDIUM = "Rubidium";
	public static final String STRONTIUM = "Strontium";
	public static final String YTTRIUM = "Yttrium";
	public static final String ZIRCONIUM = "Zirconium";
	public static final String NIOBIUM = "Niobium";
	public static final String MOLYBDENUM = "Molybdenum";
	public static final String TECHNETIUM = "Technetium";
	public static final String RUTHENIUM = "Ruthenium";
	public static final String RHODIUM = "Rhodium";
	public static final String PALLADIUM = "Palladium";
	public static final String SILVER = "Silver";
	public static final String CADMIUM = "Cadmium";
	public static final String INDIUM = "Indium";
	public static final String TIN = "Tin";
	public static final String ANTIMONY = "Antimony";
	public static final String TELLURIUM = "Tellurium";
	public static final String IODINE = "Iodine";
	public static final String XENON = "Xenon";
	public static final String CAESIUM = "Caesium";
	public static final String BARIUM = "Barium";
	public static final String LANTHANUM = "Lanthanum";
	public static final String CERIUM = "Cerium";
	public static final String PRASEODYMIUM = "Praseodymium";
	public static final String NEODYMIUM = "Neodymium";
	public static final String PROMETHIUM = "Promethium";
	public static final String SAMARIUM = "Samarium";
	public static final String EUROPIUM = "Europium";
	public static final String GADOLINIUM = "Gadolinium";
	public static final String TERBIUM = "Terbium";
	public static final String DYSPROSIUM = "Dysprosium";
	public static final String HOLMIUM = "Holmium";
	public static final String ERBIUM = "Erbium";
	public static final String THULIUM = "Thulium";
	public static final String YTTERBIUM = "Ytterbium";
	public static final String LUTETIUM = "Lutetium";
	public static final String HAFNIUM = "Hafnium";
	public static final String TANTALUM = "Tantalum";
	public static final String TUNGSTEN = "Tungsten";
	public static final String RHENIUM = "Rhenium";
	public static final String OSMIUM = "Osmium";
	public static final String IRIDIUM = "Iridium";
	public static final String PLATINUM = "Platinum";
	public static final String GOLD = "Gold";
	public static final String MERCURY = "Mercury";
	public static final String THALLIUM = "Thallium";
	public static final String LEAD = "Lead";
	public static final String BISMUTH = "Bismuth";
	public static final String POLONIUM = "Polonium";
	public static final String ASTATINE = "Astatine";
	public static final String RADON = "Radon";
	public static final String FRANCIUM = "Francium";
	public static final String RADIUM = "Radium";
	public static final String ACTINIUM = "Actinium";
	public static final String THORIUM = "Thorium";
	public static final String PROTACTINIUM = "Protactinium";
	public static final String URANIUM = "Uranium";
	public static final String NEPTUNIUM = "Neptunium";
	public static final String PLUTONIUM = "Plutonium";
	public static final String AMERICIUM = "Americium";
	public static final String CURIUM = "Curium";
	public static final String BERKELIUM = "Berkelium";
	public static final String CALIFORNIUM = "Californium";
	public static final String EINSTEINIUM = "Einsteinium";
	public static final String FERMIUM = "Fermium";
	public static final String MENDELEVIUM = "Mendelevium";
	public static final String NOBELIUM = "Nobelium";
	public static final String LAWRENCIUM = "Lawrencium";
	public static final String RUTHERFORDIUM = "Rutherfordium";
	public static final String DUBNIUM = "Dubnium";
	public static final String SEABORGIUM = "Seaborgium";
	public static final String BOHRIUM = "Bohrium";
	public static final String HASSIUM = "Hassium";
	public static final String MEITNERIUM = "Meitnerium";
	public static final String DARMSTADTIUM = "Darmstadtium";
	public static final String ROENTGENIUM = "Roentgenium";
	public static final String COPERNICIUM = "Copernicium";
	public static final String UNUNTRIUM = "Ununtrium";
	public static final String UNUNQUADIUM = "Ununquadium";
	public static final String UNUNPENTIUM = "Ununpentium";
	public static final String UNUNHEXIUM = "Ununhexium";
	public static final String UNUNSEPTIUM = "Ununseptium";
	public static final String UNUNOCTIUM = "Ununoctium";
	
	/**
	 * Get instance of periodic table.
	 * @return Periodic table
	 */
	public static PeriodicTable getInstance(){
		if (table == null)
			table = new PeriodicTable();
		return table;
	}
	
	/**
	 * Find element by symbol
	 * @param symbol Element symbol
	 * @return Element
	 */
	public AtomicElement getElementBySymbol(String symbol){
		return elementsHashBySymbol.get(symbol.toLowerCase());
	}

	/**
	 * Find element by name
	 * @param name Element name
	 * @return Element
	 */
	public AtomicElement getElementByName(String name){
		return elementsHashByName.get(name.toLowerCase());
	}

	/**
	 * Find element by atomic number
	 * @param atomicNumber Element atomic number
	 * @return Element
	 */
	public AtomicElement getElementByAtomicNumber(int atomicNumber){
		return elementsHashByAtomicNumber.get(atomicNumber);
	}
	
	/**
	 * Find element using atom information. Lookup is made by symbol, then by mass, 
	 * and finally by symbol using only the first letter of the atom name 
	 * @param atom Atom
	 * @return Element
	 */
	public AtomicElement findElementForAtom(Atom atom){
		String atomType = atom.getType();
		atomType = atomType.replaceAll("[\\-\\+\\d]+", "");
		AtomicElement element = this.getElementBySymbol(atomType);
		if (element == null && atom.getMass()>0)
			element = this.getElementByMass(atom.getType(), atom.getMass());
		if (element == null && atomType.length()>1)
		{
			element = this.getElementBySymbol(atomType.substring(0,1));
			if (element!=null){
				System.out.println("Warning: atom '" + atomType + "' was mapped to element '"+element.getSymbol()+"' using only the first letter of the atom type.");
				logger.warn("Atom '" + atomType + "' was mapped to element '"+element.getSymbol()+"' using only the first letter of the atom type.");
			}
		}
		return element;
	}
	
	/**
	 * Populate periodic table
	 */
	private PeriodicTable()
	{
		this.elementsHashBySymbol = new HashMap<String, AtomicElement>();
		this.elementsHashByName = new HashMap<String, AtomicElement>();
		this.elementsHashByAtomicNumber = new HashMap<Integer, AtomicElement>();
		
		this.elementsHashBySymbol.put("h",  new AtomicElement(1, HYDROGEN,  "H" ,   1.00794,    AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("he", new AtomicElement(2, HELIUM,    "He",   4.002602,   AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("li", new AtomicElement(3, LITHIUM,   "Li",   6.941,      AtomicElement.ALKALI_METAL));
		this.elementsHashBySymbol.put("be", new AtomicElement(4, BERYLLIUM,  "Be",   9.012182,   AtomicElement.ALKALINE_EARTH_METAL));
		this.elementsHashBySymbol.put("b",  new AtomicElement(5, BORON,     "B",   10.811,      AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("c",  new AtomicElement(6,  CARBON,    "C",   12.0107,     AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("n",  new AtomicElement(7,  NITROGEN,  "N",   14.0067,     AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("o",  new AtomicElement(8,  OXYGEN,    "O",   15.9994,     AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("f",  new AtomicElement(9,  FLUORINE,  "F",   18.9984032,  AtomicElement.HALOGEN));
		this.elementsHashBySymbol.put("ne", new AtomicElement(10, NEON,      "Ne",  20.1797,     AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("na", new AtomicElement(11, SODIUM,    "Na",  22.98976,    AtomicElement.ALKALI_METAL));
		this.elementsHashBySymbol.put("mg", new AtomicElement(12, MAGNESIUM, "Mg",  24.305,      AtomicElement.ALKALINE_EARTH_METAL));
		this.elementsHashBySymbol.put("al", new AtomicElement(13, ALUMINIUM, "Al",  26.9815386,  AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("si", new AtomicElement(14, SILICON,   "Si",  28.0855,     AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("p",  new AtomicElement(15, PHOSPHORUS,"P",   30.973762,   AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("s",  new AtomicElement(16, SULFUR,    "S",   32.065,      AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("cl", new AtomicElement(17, CHLORINE,  "Cl",  35.453,      AtomicElement.HALOGEN));
		this.elementsHashBySymbol.put("ar", new AtomicElement(18, ARGON,     "Ar",  39.948,      AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("k",  new AtomicElement(19, POTASSIUM, "K",   39.0983,     AtomicElement.ALKALI_METAL));
		this.elementsHashBySymbol.put("ca", new AtomicElement(20, CALCIUM,   "Ca",  40.078,      AtomicElement.ALKALINE_EARTH_METAL));
		this.elementsHashBySymbol.put("sc", new AtomicElement(21, SCANDIUM,  "Sc",  44.955912,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("ti", new AtomicElement(22, TITANIUM,  "Ti",  47.867,      AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("v",  new AtomicElement(23, VANADIUM,  "V",   50.9415,     AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("cr", new AtomicElement(24, CHROMIUM,  "Cr",  51.9961,     AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("mn", new AtomicElement(25, MANGANESE, "Mn",  54.938045,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("fe", new AtomicElement(26, IRON,      "Fe",  55.845,      AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("co", new AtomicElement(27, COBALT,    "Co",  58.933195,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("ni", new AtomicElement(28, NICKEL,    "Ni",  58.6934,     AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("cu", new AtomicElement(29, COPPER,    "Cu",  63.546,      AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("zn", new AtomicElement(30, ZINC,      "Zn",  65.38,       AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("ga", new AtomicElement(31, GALLIUM,   "Ga",  69.723,      AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("ge", new AtomicElement(32, GERMANIUM, "Ge",  72.63,       AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("as", new AtomicElement(33, ARSENIC,   "As",  74.9216,     AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("se", new AtomicElement(34, SELENIUM,   "Se",  78.96,       AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("br", new AtomicElement(35, BROMINE,   "Br",  79.904,      AtomicElement.HALOGEN));
		this.elementsHashBySymbol.put("kr", new AtomicElement(36, KRYPTON,   "Kr",  83.798,      AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("rb", new AtomicElement(37, RUBIDIUM,  "Rb",  85.4678,     AtomicElement.ALKALI_METAL));
		this.elementsHashBySymbol.put("sr", new AtomicElement(38, STRONTIUM, "Sr",  87.62,       AtomicElement.ALKALINE_EARTH_METAL));
		this.elementsHashBySymbol.put("y",  new AtomicElement(39, YTTRIUM,   "Y",   88.90585,    AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("zr", new AtomicElement(40, ZIRCONIUM, "Zr",  91.224,      AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("nb", new AtomicElement(41, NIOBIUM,   "Nb",  92.90638,    AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("mo", new AtomicElement(42, MOLYBDENUM,"Mo",  95.96,       AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("tc", new AtomicElement(43, TECHNETIUM,"Tc",  98.0,        AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("ru", new AtomicElement(44, RUTHENIUM, "Ru", 101.07,       AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("rh", new AtomicElement(45, RHODIUM,   "Rh", 102.9055,     AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("pd", new AtomicElement(46, PALLADIUM, "Pd", 106.42,       AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("ag", new AtomicElement(47, SILVER,    "Ag", 107.8682,     AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("cd", new AtomicElement(48, CADMIUM,   "Cd", 112.41,       AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("in", new AtomicElement(49, INDIUM,    "In", 114.82,       AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("sn", new AtomicElement(50, TIN,       "Sn", 118.710,      AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("sb", new AtomicElement(51, ANTIMONY,  "Sb", 121.75,       AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("te", new AtomicElement(52, TELLURIUM, "Te", 127.60,       AtomicElement.NON_METAL));
		this.elementsHashBySymbol.put("i",  new AtomicElement(53, IODINE,     "I", 126.9045,     AtomicElement.HALOGEN));
		this.elementsHashBySymbol.put("xe", new AtomicElement(54, XENON,     "Xe", 131.29,       AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("cs", new AtomicElement(55, CAESIUM,       "Cs", 132.9054,  AtomicElement.ALKALI_METAL));
		this.elementsHashBySymbol.put("ba", new AtomicElement(56, BARIUM,       "Ba", 137.33,    AtomicElement.ALKALINE_EARTH_METAL));
		this.elementsHashBySymbol.put("la", new AtomicElement(57, LANTHANUM,    "La", 138.9055,  AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("ce", new AtomicElement(58, CERIUM,       "Ce", 140.12,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("pr", new AtomicElement(59, PRASEODYMIUM, "Pr", 140.9077,  AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("Nd", new AtomicElement(60, NEODYMIUM,    "Nd", 144.24,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("pm", new AtomicElement(61, PROMETHIUM,   "Pm", 145.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("sm", new AtomicElement(62, SAMARIUM,     "Sm", 150.36,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("eu", new AtomicElement(63, EUROPIUM,     "Eu", 151.96,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("gd", new AtomicElement(64, GADOLINIUM,   "Gd", 157.25,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("tb", new AtomicElement(65, TERBIUM,      "Tb", 158.93,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("dy", new AtomicElement(66, DYSPROSIUM,   "Dy", 162.50,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("ho", new AtomicElement(67, HOLMIUM,      "Ho", 164.93,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("er", new AtomicElement(68, ERBIUM,       "Er", 167.26,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("tm", new AtomicElement(69, THULIUM,      "Tm", 168.93,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("yb", new AtomicElement(70, YTTERBIUM,    "Yb", 173.04,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("lu", new AtomicElement(71, LUTETIUM,     "Lu", 174.97,    AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("hf", new AtomicElement(72, HAFNIUM,      "Hf", 178.49,    AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("ta", new AtomicElement(73, TANTALUM,    "Ta", 180.9479,  AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put( "w", new AtomicElement(74, TUNGSTEN,      "W", 183.95,    AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("re", new AtomicElement(75, RHENIUM,      "Re", 186.207,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("os", new AtomicElement(76, OSMIUM,       "Os", 190.2,     AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("ir", new AtomicElement(77, IRIDIUM,      "Ir", 192.22,    AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("pt", new AtomicElement(78, PLATINUM,     "Pt", 195.08,    AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("au", new AtomicElement(79, GOLD,         "Au", 196.9665,  AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("hg", new AtomicElement(80, MERCURY,      "Hg", 200.59,    AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("tl", new AtomicElement(81, THALLIUM,     "Tl", 204.383,   AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("pb", new AtomicElement(82, LEAD,         "Pb", 207.2,     AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("bi", new AtomicElement(83, BISMUTH,      "Bi", 208.9804,  AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("po", new AtomicElement(84, POLONIUM,     "Po", 209.0,     AtomicElement.METAL_OTHER));
		this.elementsHashBySymbol.put("at", new AtomicElement(85, ASTATINE,     "At", 210.0,     AtomicElement.HALOGEN));
		this.elementsHashBySymbol.put("rn", new AtomicElement(86, RADON,        "Rn", 222.0,     AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("fr", new AtomicElement(87, FRANCIUM,     "Fr", 223.0,     AtomicElement.ALKALI_METAL));
		this.elementsHashBySymbol.put("ra", new AtomicElement(88, RADIUM,       "Ra", 226.0,     AtomicElement.ALKALINE_EARTH_METAL));
		this.elementsHashBySymbol.put("ac", new AtomicElement(89, ACTINIUM,     "Ac", 227.0,     AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("th", new AtomicElement(90, THORIUM,      "Th", 232.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("pa", new AtomicElement(91, PROTACTINIUM, "Pa", 231.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("u",  new AtomicElement(92, URANIUM,       "U", 238.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("np", new AtomicElement(93, NEPTUNIUM,    "Np", 237.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("pu", new AtomicElement(94, PLUTONIUM,    "Pu", 244.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("am", new AtomicElement(95, AMERICIUM,    "Am", 243.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("cm", new AtomicElement(96, CURIUM,       "Cm", 247.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("bk", new AtomicElement(97, BERKELIUM,    "Bk", 247.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("cf", new AtomicElement(98, CALIFORNIUM,  "Cf", 251.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("es", new AtomicElement(99, EINSTEINIUM,  "Es", 252.0,     AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("fm", new AtomicElement(100, FERMIUM,       "Fm", 257.0,   AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("md", new AtomicElement(101, MENDELEVIUM,   "Md", 258.0,   AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("no", new AtomicElement(102, NOBELIUM,      "No", 259.0,   AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("lr", new AtomicElement(103, LAWRENCIUM,    "Lr", 262.0,   AtomicElement.RARE_EARTH_ELEMENT));
		this.elementsHashBySymbol.put("rf", new AtomicElement(104, RUTHERFORDIUM, "Rf", 263.1,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("db", new AtomicElement(105, DUBNIUM,       "Db", 262.1,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("sg", new AtomicElement(106, SEABORGIUM,    "Sg", 266.1,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("bh", new AtomicElement(107, BOHRIUM,       "Bh", 264.1,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("hs", new AtomicElement(108, HASSIUM,       "Hs", 269.1,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("mt", new AtomicElement(109, MEITNERIUM,    "Mt", 268.1,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("ds", new AtomicElement(110, DARMSTADTIUM,  "Ds", 272.1,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("rg", new AtomicElement(111, ROENTGENIUM,   "Rg", 272.1,   AtomicElement.TRANSITION_METAL));
		this.elementsHashBySymbol.put("cn", new AtomicElement(112, COPERNICIUM, "Cn", 277.0,   AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("uut", new AtomicElement(113, UNUNTRIUM,   "Uut", 284.0,   AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("uuq", new AtomicElement(114, UNUNQUADIUM, "Uuq", 289.0,   AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("uup", new AtomicElement(115, UNUNPENTIUM,  "Uup", 288.0,   AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("uuh", new AtomicElement(116, UNUNHEXIUM,  "Uuh", 292.0,   AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("uus", new AtomicElement(117, UNUNSEPTIUM, "Uus", 292.0,   AtomicElement.NOBLE_GAS));
		this.elementsHashBySymbol.put("uuo", new AtomicElement(118, UNUNOCTIUM,  "Uuo", 294.0,   AtomicElement.NOBLE_GAS));
		
		Collection<AtomicElement> elements = elementsHashBySymbol.values();
		for (AtomicElement e : elements){
			this.elementsHashByAtomicNumber.put(e.getAtomicNumber(), e);
			this.elementsHashByName.put(e.getName().toLowerCase(), e);
		}
	
	}
	
	/**
	 * Get atomic element based on atom name and mass 
	 * @param atomName Atom name
	 * @param mass Atom mass
	 * @return Atomic element
	 */
	public AtomicElement getElementByMass(String atomName, double mass)
	{
		char c1 = atomName.charAt(0);
		switch (c1) {
		case 'a':
		case 'A':
			if (mass > 24.0 && mass <= 28.0)
				return this.getElementBySymbol("al"); // 13 ! Aluminum
			else if (mass > 35.0 && mass <= 40.0) 
				return this.getElementBySymbol("ar"); // 18 !Argon
			else if(mass > 73.0 && mass <= 77.0) 
				return this.getElementBySymbol("as"); // 33 !Arsenic
			else if(mass > 106.0 && mass <= 109.0) 
				return this.getElementBySymbol("ag"); // 47 !Silver
			else if(mass > 195.0 && mass <= 199.0) 
				return this.getElementBySymbol("au"); // 79 !Gold
			else if(mass > 208.0 && mass <= 212.0) 
				return this.getElementBySymbol("at"); // 85 !Astatine
			break;

		case 'b':
		case 'B':
			if (mass > 8.0 && mass <= 10.0) 
				return this.getElementBySymbol("be"); // 4 !Beryllium
			else if(mass > 10.0 && mass <= 12.0) 
				return this.getElementBySymbol("b"); // 5 !Boron
			else if(mass > 77.0 && mass <= 81.0) 
				return this.getElementBySymbol("br"); // 35 !Bromine
			else if(mass > 135.0 && mass <= 139.0) 
				return this.getElementBySymbol("ba"); // 56 !Barium
			else if(mass > 207.0 && mass <= 211.0) 
				return this.getElementBySymbol("bi"); // 83 !Bismuth
			break;

		case 'c':
		case 'C':
			if (mass > 10.0 && mass <= 14.0) 
				return this.getElementBySymbol("c"); //=  6 !Carbon
			else if(mass > 33.0 && mass <= 37.0) 
				return this.getElementBySymbol("cl"); // 17 !Chlorine
			else if(mass > 38.0 && mass <= 42.0) 
				return this.getElementBySymbol("ca"); // 20 !Calcium
			else if(mass > 50.0 && mass <= 54.0) 
				return this.getElementBySymbol("cr"); // 24 !Chromium
			else if(mass > 57.0 && mass <= 61.0) 
				return this.getElementBySymbol("co"); // 27 !Cobalt
			else if(mass > 61.0 && mass <= 65.0) 
				return this.getElementBySymbol("cu"); // 29 !Copper
			else if(mass > 110.0 && mass <= 114.0) 
				return this.getElementBySymbol("cd"); // 48 !Cadmium
			else if(mass > 131.0 && mass <= 135.0) 
				return this.getElementBySymbol("cs"); // 55 !Cesium
			break; 

		case 'f':
		case 'F':
			if (mass > 17.0 && mass <= 21.0) 
				return this.getElementBySymbol("f"); // 9 !Fluorine
			else if(mass > 54.0 && mass <= 58.0) 
				return this.getElementBySymbol("fe"); // 26 !Iron
			else if(mass > 218.0 && mass <= 228.0) 
				return this.getElementBySymbol("fr"); // 87 !Francium
			break;

		case 'g':
		case 'G':
			if (mass > 67.0 && mass <= 71.0) 
				return this.getElementBySymbol("ga"); //  31 !Gallium
			else if(mass > 71.0 && mass <= 75.0) 
				return this.getElementBySymbol("ge"); // 32 !Germanium
			break;

		case 'h':
		case 'H':
			if(mass > 0.0 && mass <= 2.0) 
				return this.getElementBySymbol("h"); // 1 !Hydrogen
			else if(mass > 3.0 && mass <= 5.0) 
				return this.getElementBySymbol("he"); // 2 !Helium
			else if(mass > 176.0 && mass <= 180.0) 
				return this.getElementBySymbol("hf"); // 72 !Hafnium
			else if(mass > 198.0 && mass <= 202.0) 
				return this.getElementBySymbol("hg"); // 80 !Mercury
			break;

		case 'i':
		case 'I':
			if(mass > 112.0 && mass <= 116.0) 
				return this.getElementBySymbol("in"); //49 !Indium
			else if(mass > 125.0 && mass <= 129.0) 
				return this.getElementBySymbol("i"); // 53 !Iodine
			else if(mass > 190.0 && mass <= 194.0) 
				return this.getElementBySymbol("ir"); // 77 !Iridium
			break;

		case 'k':
		case 'K':
			if(mass > 37.0 && mass <= 41.0) 
				return this.getElementBySymbol("k"); //19 !Potassium
			else if(mass > 77.0 && mass <= 86.0) 
				return this.getElementBySymbol("kr"); //36 !Krypton
			break;

		case 'l':
		case 'L':
			if(mass > 6.0 && mass <= 8.0) 
				return this.getElementBySymbol("li"); //3 !Lithium
			break;

		case 'm':
		case 'M':
			if(mass > 22.0 && mass <= 26.0) 
				return this.getElementBySymbol("mg"); //12 !Magnesium
			else if(mass > 53.0 && mass <= 57.0) 
				return this.getElementBySymbol("mn"); //25 !Manganese
			else if(mass > 94.0 && mass <= 98.0) 
				return this.getElementBySymbol("mo"); //42 !Molybdenum
			break;

		case 'n':
		case 'N':
			if(mass > 13.0 && mass <= 15.0) 
				return this.getElementBySymbol("n"); //7 !Nitrogen
			else if(mass > 19.0 && mass <= 22.0) 
				return this.getElementBySymbol("ne"); //10 !Neon
			else if(mass > 22.1 && mass <= 23.0) 
				return this.getElementBySymbol("na"); //11 !Sodium
			else if(mass > 57.0 && mass <= 61.0) 
				return this.getElementBySymbol("ni"); //28 !Nickel
			else if(mass > 95.0 && mass <= 99.0) 
				return this.getElementBySymbol("nb"); //41 !Niobium
			break;

		case 'o':
		case 'O':
			if(mass > 14.0 && mass <= 18.0) 
				return this.getElementBySymbol("o"); //8 !Oxygen
			else if(mass > 188.0 && mass <= 192.0) 
				return this.getElementBySymbol("os"); //76 !Osmium
			break;

		case 'p':
		case 'P':
			if(mass > 29.0 && mass <= 33.0) 
				return this.getElementBySymbol("p"); //15 !Phosphorus
			else if(mass > 104.0 && mass <= 108.0) 
				return this.getElementBySymbol("pd"); //46 !Palladium
			else if(mass > 193.0 && mass <= 197.0) 
				return this.getElementBySymbol("pt"); //78 !Platinum
			else if(mass > 205.0 && mass <= 208.0) 
				return this.getElementBySymbol("pb"); //82 !Lead
			else if(mass > 208.0 && mass <= 212.0) 
				return this.getElementBySymbol("po"); //84 !Polonium
			break;

		case 'r':
		case 'R':
			if(mass > 84.0 && mass <= 88.0) 
				return this.getElementBySymbol("rb"); //37 !Rubidium
			else if(mass > 99.0 && mass <= 102.0) 
				return this.getElementBySymbol("ru"); //44 !Ruthenium
			else if(mass > 102.0 && mass <= 105.0) 
				return this.getElementBySymbol("rh"); //45 !Rhodium
			else if(mass > 184.0 && mass <= 188.0) 
				return this.getElementBySymbol("re"); //75 !Rhenium
			else if(mass > 210.0 && mass <= 222.5) 
				return this.getElementBySymbol("rn"); //86 !Radon
			else if(mass > 223.0 && mass <= 229.0) 
				return this.getElementBySymbol("ra"); //88 !Radium
			break;

		case 's':
		case 'S':
			if(mass > 26.0 && mass <= 30.0) 
				return this.getElementBySymbol("si"); //14 !Silicon
			else if(mass > 30.0 && mass <= 34.0) 
				return this.getElementBySymbol("s"); //16 !Sulfur
			else if(mass > 43.0 && mass <= 47.0) 
				return this.getElementBySymbol("sc"); //21 !Scandium
			else if(mass > 77.0 && mass <= 81.0) 
				return this.getElementBySymbol("se"); //34 !Selenium
			else if(mass > 86.0 && mass <= 89.0) 
				return this.getElementBySymbol("sr"); //38 !Strontium
			else if(mass > 116.0 && mass <= 120.0) 
				return this.getElementBySymbol("sn"); //50 !Tin
			else if(mass > 120.0 && mass <= 124.0) 
				return this.getElementBySymbol("sb"); //51 !Antimony
			break;

		case 't':
		case 'T':
			if(mass > 46.0 && mass <= 50.0) 
				return this.getElementBySymbol("ti"); //22 !Titanium
			else if(mass > 96.0 && mass <= 100.0) 
				return this.getElementBySymbol("tc"); //43 !Technetium
			else if(mass > 125.0 && mass <= 130.0) 
				return this.getElementBySymbol("te"); //52 !Tellurium
			else if(mass > 179.0 && mass <= 183.0) 
				return this.getElementBySymbol("ta"); //73 !Tantalum
			else if(mass > 201.0 && mass <= 206.0) 
				return this.getElementBySymbol("tl"); //81 !Thallium
			break;

		case 'v':
		case 'V':
			if(mass > 49.0 && mass <= 53.0) 
				return this.getElementBySymbol("v"); //23 !Vanadium
			break;

		case 'w':
		case 'W':
			if(mass > 179.0 && mass <= 183.0) 
				return this.getElementBySymbol("w"); //74 !Tungsten
			break;

		case 'x':
		case 'X':
			if (mass > 127.0 && mass < 136.0)
				return this.getElementBySymbol("xe"); // 54 !Xenon 
			break;

		case 'z':
		case 'Z':
			if(mass > 61.0 && mass <= 69.0) 
				return this.getElementBySymbol("zn"); //30 !Zinc
			else if(mass > 89.0 && mass <= 93.0) 
				return this.getElementBySymbol("zr"); //40 !Zirconium
			break;

		default:
			return null;
		}
		return null;
	}
}
