package edu.utah.bmi.ibiomes.web.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;

/**
 * Quantum mechanics method
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="method")
public class QMMethod extends Method {

	private List<String> levelsOfTheory;
	private List<String> methodNames;
	private List<String> basisSets;
	private int[] spinMultiplicities;
	private double[] totalCharges;
	private List<String> exchangeCorrelationFns;
	private List<String> calculationTypes;
	
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
	public QMMethod(){
		this.name = Method.METHOD_QM;
		this.timeDependent = false;
	}

	public List<String> getMethodNames() {
		return methodNames;
	}

	public void setMethodNames(List<String> methodNames) {
		this.methodNames = methodNames;
	}
	/**
	 * Get level of theory
	 * @return Level of theory
	 */
	public List<String> getLevelsOfTheory() {
		return levelsOfTheory;
	}

	/**
	 * Set the level of theory
	 * @param levelsOfTheory Level of theory
	 */
	public void setLevelsOfTheory(List<String> levelsOfTheory) {
		this.levelsOfTheory = levelsOfTheory;
	}

	/**
	 * Get basis sets
	 * @return Basis sets
	 */
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
	 * Get spin multiplicites
	 * @return Spin multiplicities
	 */
	public int[] getSpinMultiplicities() {
		return spinMultiplicities;
	}

	/**
	 * Set spin multiplicities
	 * @param spinMultiplicity Spin multiplicities
	 */
	public void setSpinMultiplicities(int[] spinMultiplicities) {
		this.spinMultiplicities = spinMultiplicities;
	}
	
	/**
	 * Get exchange correlational function
	 * @return Exchange correlational function
	 */
	public List<String> getExchangeCorrelationFns() {
		return exchangeCorrelationFns;
	}

	/**
	 * Set exchange correlational functions
	 * @param exchangeCorrelationFns Exchange correlational functions
	 */
	public void setExchangeCorrelationFns(List<String> exchangeCorrelationFns) {
		this.exchangeCorrelationFns = exchangeCorrelationFns;
	}

	/**
	 * Get total charges
	 * @return Total charges
	 */
	public double[] getTotalCharges() {
		return totalCharges;
	}

	/**
	 * Set total charge
	 * @param totalCharge Total charge
	 */
	public void setTotalCharges(double[] totalCharges) {
		this.totalCharges = totalCharges;
	}
	
	/**
	 * Get list of calculation types
	 * @return List of calculation types
	 */
	public List<String> getCalculationTypes() {
		return calculationTypes;
	}

	/**
	 * Set list of calculation types
	 * @param calculations List of calculation types
	 */
	public void setCalculationTypes(List<String> calculations) {
		this.calculationTypes = calculations;
	}
	
	/**
	 * Construct QM method based on a list of iBIOMES metadata
	 * @param metadata List of iBIOMES metadata
	 */
	public QMMethod(MetadataAVUList metadata)
	{
		super(metadata);
		
		this.setBasisSets(metadata.getValues(MethodMetadata.QM_BASIS_SET));
		this.setLevelsOfTheory(metadata.getValues(MethodMetadata.QM_LEVEL_OF_THEORY));
		this.setMethodNames(metadata.getValues(MethodMetadata.QM_METHOD_NAME));
		this.setCalculationTypes(metadata.getValues(MethodMetadata.CALCULATION));
		this.setExchangeCorrelationFns(metadata.getValues(MethodMetadata.QM_EXCHANGE_CORRELATION));
		
		List<String> values = metadata.getValues(MethodMetadata.QM_SPIN_MULTIPLICITY);
		if (values!=null && values.size()>0){
			int v=0;
			int[] spinMult = new int[values.size()];
			for(String value : values){
				try{
					spinMult[v] = Integer.parseInt(value);
				} catch (NumberFormatException e){
					//skip
				}
				v++;
			}
			this.setSpinMultiplicities(spinMult);
		}
		
		values = metadata.getValues(TopologyMetadata.TOTAL_MOLECULE_CHARGE);
		if (values!=null && values.size()>0){
			int v=0;
			double[] charges = new double[values.size()];
			for(String value : values){
				try{
					charges[v] = Double.parseDouble(value);
				} catch (NumberFormatException e){
					//skip
				}
				v++;
			}
			this.setTotalCharges(charges);
		}
		

		this.name = Method.METHOD_QM;
		this.timeDependent = false;
	}

}
