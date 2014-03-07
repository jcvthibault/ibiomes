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

package edu.utah.bmi.ibiomes.experiment.comp.qm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;

/**
 * Quantum mechanics method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="parameterSet")
public class QMParameterSet extends ParameterSet {

	private String specificMethodName;
	private String levelOfTheory;
	private List<String> basisSets;
	private int spinMultiplicity;
	private int totalCharge;
	private String exchangeCorrelationFn;
	private List<String> pseudopotentials;
	private boolean frozenCore;
	
	/** 
	 * NMR (Nuclear Magnetic Resonance) calculations
	 */
	public final static String CALCULATION_NMR = "NMR calculations";
	/** 
	 * IRC (Intrinsic Reaction Coordinate)
	 */
	public final static String CALCULATION_IRC = "IRC calculations";
	/** 
	 * Geometry optimization
	 */
	public final static String QM_GEOMETRY_OPTIMIZATION = "Geometry optimization";
	/** 
	 * Scan
	 */
	public final static String CALCULATION_SCAN = "Scan";
	/** 
	 * Stability
	 */
	public final static String CALCULATION_STABILITY = "Stability calculations";
	/** 
	 * Frequency
	 */
	public final static String CALCULATION_FREQUENCY = "Frequency calculations";
	/** 
	 * Single point energy (SPE) calculation
	 */
	public static final String CALCULATION_ENERGY_SP = "SPE calculations";
	/**
	 * Calculate derivatives of the energy
	 */
	public static final String CALCULATION_ENERGY_GRADIENT = "Energy gradient calculations";
	/**
	 * Analytic hessians
	 */
	public static final String CALCULATION_HESSIAN = "Hessian";
	/**
	 * Raman intensity calculations
	 */
	public static final String CALCULATION_RAMAN = "Raman intensity calculations";
	/**
	 * Global Monte-Carlo optimization
	 */
	public static final String QM_OPTIMIZATION_MONTE_CARLO = "Monte-Carlo optimization";
	/**
	 * Locate saddle point / transition state
	 */
	public static final String CALCULATION_SADDLE = "Saddle point";
	
	/**
	 * Constructor
	 */
	public QMParameterSet(){
		this.name = ParameterSet.METHOD_QM;
	}

	/**
	 * Get specific method name (e.g. MP2, B3LYP)
	 * @return Specific method name
	 */
	@XmlAttribute(name="name")
	public String getSpecificMethodName() {
		return specificMethodName;
	}

	/**
	 * Set specific method name (e.g. MP2, B3LYP)
	 * @param specificMethodName Specific method name
	 */
	public void setSpecificMethodName(String specificMethodName) {
		this.specificMethodName = specificMethodName;
	}

	/**
	 * Get basis sets
	 * @return Basis sets
	 */
	@XmlElementWrapper(name="basisSets")
	@XmlElement(name="basisSet")
	public List<String> getBasisSets() {
		return basisSets;
	}

	/**
	 * Set list of basis sets
	 * @param basisSets Basis sets
	 */
	public void setBasisSets(List<String> basisSets) {
		this.basisSets = basisSets;
	}
	
	/**
	 * Set basis set
	 * @param basisSet Basis set
	 */
	public void setBasisSet(String basisSet) {
		if (basisSet!=null){
			this.basisSets = new ArrayList<String>();
			this.basisSets.add(basisSet);
		}
	}

	/**
	 * Get spin multiplicity
	 * @return Spin multiplicity
	 */
	public int getSpinMultiplicity() {
		return spinMultiplicity;
	}

	/**
	 * Set spin multiplicity
	 * @param spinMultiplicity Spin multiplicity
	 */
	public void setSpinMultiplicity(int spinMultiplicity) {
		this.spinMultiplicity = spinMultiplicity;
	}
	
	/**
	 * Get exchange correlational function
	 * @return Exchange correlational function
	 */
	public String getExchangeCorrelationFn() {
		return exchangeCorrelationFn;
	}

	/**
	 * Set exchange correlational function
	 * @param exchangeCorrelationFn Exchange correlational function
	 */
	public void setExchangeCorrelationFn(String exchangeCorrelationFn) {
		this.exchangeCorrelationFn = exchangeCorrelationFn;
	}

	/**
	 * Get total charge
	 * @return Total charge
	 */
	public int getTotalCharge() {
		return totalCharge;
	}

	/**
	 * Set total charge
	 * @param totalCharge Total charge
	 */
	public void setTotalCharge(int totalCharge) {
		this.totalCharge = totalCharge;
	}
	
	/**
	 * Get level of theory (e.g. MP, DFT, HF)
	 * @return Level of theory
	 */
	public String getLevelOfTheory() {
		return this.levelOfTheory;
	}

	/**
	 * Setlevel of theory
	 * @param levelOfTheory Level of theory
	 */
	public void setLevelOfTheory(String levelOfTheory) {
		this.levelOfTheory = levelOfTheory;
	}

	/**
	 * Get pseudopotentials
	 * @return List of pseudopotentials
	 */
	public List<String> getPseudoPotentials() {
		return this.pseudopotentials;
	}
	
	/**
	 * Set pseudopotentials
	 * @param pseudopotentials List of pseudopotentials
	 */
	public void setPseudoPotentials(List<String> pseudopotentials) {
		this.pseudopotentials = pseudopotentials;
	}

	/**
	 * Whether frozen core is used
	 * @return True if frozen core calculations
	 */
	public boolean isFrozenCore() {
		return frozenCore;
	}

	/**
	 * Set whether frozen core calculations are performed
	 * @param frozenCore True if frozen core calculations
	 */
	public void setFrozenCore(boolean frozenCore) {
		this.frozenCore = frozenCore;
	}
	
	/**
	 * Get list of metadata representing this QM method
	 */
	@Override
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = super.getMetadata();
		
		if (this.basisSets!=null && this.basisSets.size()>0){
			for (String bs : this.basisSets){
				metadata.add(new MetadataAVU(MethodMetadata.QM_BASIS_SET, bs));
			}
		}
		
		if (this.specificMethodName!=null && this.specificMethodName.length()>0){
			metadata.add(new MetadataAVU(MethodMetadata.QM_METHOD_NAME, this.specificMethodName));
		}
		
		if (this.levelOfTheory!=null && this.levelOfTheory.length()>0){
			metadata.add(new MetadataAVU(MethodMetadata.QM_LEVEL_OF_THEORY, this.levelOfTheory));
		}
		
		metadata.add(new MetadataAVU(MethodMetadata.QM_SPIN_MULTIPLICITY, String.valueOf(this.spinMultiplicity)));
		metadata.add(new MetadataAVU(TopologyMetadata.TOTAL_MOLECULE_CHARGE, String.valueOf(this.totalCharge)));
		
		if (this.exchangeCorrelationFn!=null && this.exchangeCorrelationFn.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.QM_EXCHANGE_CORRELATION, this.exchangeCorrelationFn));

		return metadata;
	}

}
