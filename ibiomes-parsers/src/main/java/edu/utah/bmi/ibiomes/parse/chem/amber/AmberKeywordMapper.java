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

package edu.utah.bmi.ibiomes.parse.chem.amber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.comp.ConjugateGradientParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.Constraint;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.SimulatedConditionSet;
import edu.utah.bmi.ibiomes.experiment.comp.SteepestDescentParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.min.MinimizationParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.min.MinimizationTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Barostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.ElectrostaticsModel;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Ensemble;
import edu.utah.bmi.ibiomes.experiment.comp.mm.LangevinThermostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.PMEModel;
import edu.utah.bmi.ibiomes.experiment.comp.mm.REMDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.REMDTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Restraint;
import edu.utah.bmi.ibiomes.experiment.comp.mm.SamplingMethod;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Thermostat;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMTask;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QuantumMDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QuantumMDTask;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMTask;
import edu.utah.bmi.ibiomes.quantity.Distance;
import edu.utah.bmi.ibiomes.quantity.Frequency;
import edu.utah.bmi.ibiomes.quantity.Pressure;
import edu.utah.bmi.ibiomes.quantity.Temperature;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * Map AMBER parameter keywords to metadata
 * @author Julien Thibault
 *
 */
public class AmberKeywordMapper {
		
	/**
	 * Map AMBER parameter keywords to metadata
	 * @param parameters List of AMBER parameters and values
	 * @param parameterSectionLabels Parameter section labels
	 * @throws Exception
	 */
	public static List<ExperimentTask> mapToTasks(HashMap<String, String> parameters, 
			List<String> parameterSectionLabels)
	{
		String value = null;
		String methodName = ParameterSet.METHOD_MD;
		Barostat barostat = null;
		Thermostat thermostat = null;
		String ensembleType = null;
		ElectrostaticsModel electro = null;
		String implicitSolvent = null;
		String bc = null;
		List<Constraint> constraints = new ArrayList<Constraint>();
		List<Restraint> restraints = new ArrayList<Restraint>();
		SimulatedConditionSet env = null;
				
		String solvent = null;
		String ensemble = null;
		Frequency collisionFrequency = null;
		double nsteps = 0.0;
		TimeLength dt = null, time = null;
		Distance cutoffElectrostatics = null, cutoffVdW = null;
		
		boolean isREMD = false;
		int remdNumberOfExchanges = 0;
		String remdType = null;
		
		int qmSpin = 0;
		int qmCharge = 0;
		String levelOfTheory = null, basisSet = null;
		String qmSpace = null;
		String qmmmInteractionType = null;
		List<Constraint> qmConstraints = null;
		ElectrostaticsModel qmElectrostatics = null;
		String quantumMdMethodName = null;

		// TIME
		value = parameters.get("dt");
		if (value != null){
			 dt = new TimeLength(Double.parseDouble(value), TimeLength.Picosecond);
		}
		value = parameters.get("nstlim");
		if (value != null){
			nsteps = Double.parseDouble(value);
			// number of exchanges for REMD
			value = parameters.get("numexchg");
			if (value != null){
				remdNumberOfExchanges = Integer.parseInt(value);
				isREMD = true;
				if (remdNumberOfExchanges>0){
					nsteps = nsteps * (double)remdNumberOfExchanges;
				}
			}
			if (dt != null){
				time = new TimeLength(dt.getValue() * nsteps, dt.getUnit());
			}
		}
		
		// LANGEVIN DYNAMICS
		// STOCHASTICS DYNAMICS FRICTION COEF
		value = parameters.get("gamma_ln");
		if (value != null){
			collisionFrequency = new Frequency(Double.parseDouble(value), "ps^-1");
			methodName = ParameterSet.METHOD_LANGEVIN_DYNAMICS;
		}
		
		// BOUNDARY CONDITIONS
		value = parameters.get("ntb");
		if (value != null){
			if (value.equals("0"))
				bc = ParameterSet.BC_NON_PERIODIC;
			else bc = ParameterSet.BC_PERIODIC;
		}

		//BAROSTAT
		value = parameters.get("ntp");
		if (value != null){
			if (value.equals("0")){
				//barostat = new Barostat(Barostat.BAROSTAT_NONE);
			}
			else {
				//default barostat is Berendsen
				String barostatAlgorithm = Barostat.BAROSTAT_BERENDSEN;
				if (value.equals("1")){
					barostat = new Barostat(barostatAlgorithm, Barostat.BAROSTAT_TYPE_ISOTROPIC);
				}
				else if (value.equals("2")){
					barostat = new Barostat(barostatAlgorithm, Barostat.BAROSTAT_TYPE_ANISOTROPIC);
				}
				else if (value.equals("3")){
					barostat = new Barostat(barostatAlgorithm, Barostat.BAROSTAT_TYPE_SEMIISOTROPIC);
				}
				value = parameters.get("taup");
				if (barostat != null && value != null){
					barostat.setTimeConstant(new TimeLength(Double.parseDouble(value), TimeLength.Femtosecond));
				}
			}
		}
				
		//THERMOSTAT
		value = parameters.get("ntt");
		boolean isLangevin = false;
		if (value != null){
			if (value.equals("1")){
				thermostat = new Thermostat(Thermostat.THERMOSTAT_BERENDSEN);
			}
			else if (value.equals("2")){
				thermostat = new Thermostat(Thermostat.THERMOSTAT_ANDERSEN);
			}
			else if (value.equals("3")){
				thermostat = new LangevinThermostat();
				isLangevin = true;
			}
		}
		else {
			if (findPhraseInSectionLabels("berendsen", parameterSectionLabels)){
				thermostat = new Thermostat(Thermostat.THERMOSTAT_BERENDSEN);
			}
			else if (findPhraseInSectionLabels("andersen", parameterSectionLabels)){
				thermostat = new Thermostat(Thermostat.THERMOSTAT_ANDERSEN);
			}
			else if (findPhraseInSectionLabels("langevin", parameterSectionLabels)){
				thermostat = new LangevinThermostat();
				isLangevin = true;
			}
		}
		value = parameters.get("tautp");
		if (thermostat != null && value != null){
			thermostat.setTimeConstant(new TimeLength(Double.parseDouble(value), TimeLength.Femtosecond));
		}
		//random seed for Langevin dynamics
		if (isLangevin){
			value = parameters.get("ig");
			if (value != null){
				long randomSeed = Long.parseLong(value);
				((LangevinThermostat)thermostat).setRandomSeed(randomSeed);
			}
			((LangevinThermostat)thermostat).setCollisionFrequency(collisionFrequency);
		}
		
		//ENSEMBLE TYPE
		if (thermostat==null && barostat==null){
			ensembleType = Ensemble.NVE;
		}
		else if (thermostat != null && barostat == null){
			ensembleType = Ensemble.NVT;
		}
		else if (thermostat != null && barostat != null){
			ensembleType = Ensemble.NPT;
		}

		//SOLVENT
		value = parameters.get("igb");
		if (value != null){
			solvent = ParameterSet.SOLVENT_IMPLICIT;
			if (value.equals("1"))
				implicitSolvent = ParameterSet.IMPLICIT_MODEL_GB_HCT;
			else if (value.equals("2") || value.equals("5"))
				implicitSolvent = ParameterSet.IMPLICIT_MODEL_GB_OBC;
			else if (value.equals("7") || value.equals("8"))
				implicitSolvent = ParameterSet.IMPLICIT_MODEL_GB_N;
			else if (value.equals("10"))
				implicitSolvent = ParameterSet.IMPLICIT_MODEL_PB;
			else if (value.equals("0") || value.equals("6")){
				//in vacuum
				solvent = null;
			}
		}else{
			//in vacuum or explicit
		}
		
		//ENVIRONMENT
		if (parameters.get("temp0")!=null || parameters.get("pres0")!=null){
			Temperature temperature=null;
			Pressure pressure=null;
			value = parameters.get("temp0");
			if (value != null && value.length()>0){
				temperature = new Temperature(Double.parseDouble(value));
			}
			value = parameters.get("pres0");
			if (value != null && value.length()>0){
				pressure = new Pressure(Double.parseDouble(value));
			}
			env = new SimulatedConditionSet(pressure, temperature);

			value = parameters.get("tempi");
			if (value != null && value.length()>0){
				double iTemp = Double.parseDouble(value);
				if (iTemp > 0.0)
					env.setInitialTemperature(new Temperature(iTemp));
				/*//if initial dynamics run
				int ntx = 1;
				value = parameters.get("ntx");
				if (value!=null)
					ntx = Integer.parseInt(value);
				if (ntx<3){
					if (iTemp > 0.0)
						env.setInitialTemperature(new Temperature(iTemp));
				}
				else env.setInitialTemperature(new Temperature(iTemp));*/
			}
			value = parameters.get("presi");
			if (value != null && value.length()>0){
				env.setInitialPressure(new Pressure(Double.parseDouble(value)));
			}
		}
		
		//ELECTROSTATICS
		value = parameters.get("ew_type");
		//if regular ewald is specifically specified or non-periodic BC
		if ( (value != null && value.equals("1")) 
				|| (value == null && bc.equals(ParameterSet.BC_NON_PERIODIC)) ){
			electro = new ElectrostaticsModel(ElectrostaticsModel.CLASSIC_EWALD);
		}
		else {
			electro = new PMEModel();
			value = parameters.get("interpolation order");
			if (value == null) value = parameters.get("order");
			if (value != null)
				((PMEModel)electro).setInterpolationOrder(Integer.parseInt(value));
			value = parameters.get("ewald coefficient");
			if (value == null) value = parameters.get("ew_coeff");
			if (value != null)
				((PMEModel)electro).setEwaldCoefficient(Double.parseDouble(value));
		}
		
		//CUTOFF
		value = parameters.get("es_cutoff");
		if (value == null) value = parameters.get("cutoff");
		if (value != null)
			cutoffElectrostatics = new Distance(Double.parseDouble(value), Distance.Angstrom);
		
		value = parameters.get("vdw_cutoff");
		if (value != null){
			cutoffVdW = new Distance(Double.parseDouble(value), Distance.Angstrom);
		}
		value = parameters.get("cut");
		if (cutoffElectrostatics==null){
			if (value == null && implicitSolvent == null) value = "8.0";
			cutoffElectrostatics = new Distance(Double.parseDouble(value), Distance.Angstrom);
		}
		if (cutoffVdW==null){
			if (value == null && implicitSolvent == null) value = "8.0";
			cutoffVdW = new Distance(Double.parseDouble(value), Distance.Angstrom);
		}
		electro.setCutoff(cutoffElectrostatics);
		
		//CONSTRAINTS
		value = parameters.get("ntc");
		if (value != null && !value.equals("1")){
			Constraint shakeConstraint = new Constraint(Constraint.SHAKE);
			if (value.equals("2")){
				shakeConstraint.setTarget(Constraint.TARGET_BONDS_TO_HYDROGEN);
			}
			else if (value.equals("3")){
				shakeConstraint.setTarget(Constraint.TARGET_ALL_BONDS);
			}
			constraints.add(shakeConstraint);
		}
		
		//TODO RESTRAINTS
		value = parameters.get("nmropt");
		if (value != null && !value.equals("0")){
			Restraint restraint = new Restraint();
			restraints.add(restraint);
		}
		
		//REPLICA EXCHANGE
		if (isREMD){
			value = parameters.get("rem");
			if (value != null){
				if (value.equals("0")){
					isREMD = false;
				}
				else if (value.equals("1")){
					remdType = SamplingMethod.REMD_TEMPERATURE;
				}
				else if (value.equals("3")){
					remdType = SamplingMethod.REMD_HAMILTONIAN;
				}
				else if (value.equals("-1")){
					remdType = SamplingMethod.REMD_MULTI_DIMENSION;
				}
				else {
					remdType = SamplingMethod.REMD;
				}
			}
		}
		
		// QM/MM or AB initio MD (SEBOMD)
		if (parameters.get("qmmask")!=null || parameters.get("iqmatoms")!=null 
				|| parameters.get("qm_theory")!=null)
		{
			//QM level of theory
			value = parameters.get("qm_theory");
			
			//if SEBOMD
			if (value != null && value.toUpperCase().equals("SEBOMD")){
				methodName = ParameterSet.METHOD_QUANTUM_MD;
				quantumMdMethodName = QuantumMDParameterSet.BORN_OPPENHEIMER_MD;
				value = parameters.get("hamiltonian");
				if (value != null)
					levelOfTheory = value.toUpperCase();
				else levelOfTheory = "PM3";
				value = parameters.get("charge");
				if (value != null)
					qmCharge = Integer.parseInt(value);
			}
			else //QM/MM
			{		
				if (value != null) {
					levelOfTheory = value.toUpperCase();
					if (levelOfTheory.equals("EXTERN")){
						levelOfTheory = parameters.get("method");
						if (levelOfTheory != null)
							levelOfTheory = levelOfTheory.toUpperCase();
						basisSet = parameters.get("basis");
					}
				}
				else 
					levelOfTheory = "PM3";
				
				methodName = ParameterSet.METHOD_QMMM;
				
				//QM region definition
				value = parameters.get("qmmask");
				if (value != null)
					qmSpace = value;
				else {
					value = parameters.get("iqmatoms");
					if (value != null)
						qmSpace = value;
				}
				
				
				//QM charge
				value = parameters.get("qmcharge");
				if (value != null)
					qmCharge = Integer.parseInt(value);
	
				//QM spin
				value = parameters.get("spin");
				if (value != null)
					qmSpin = Integer.parseInt(value);
				
				// electrostatics for QM-MM and QM-QM
				Distance qmCutoff = null;
				value = parameters.get("qmcut");
				if (value != null)
					qmCutoff = new Distance(Double.parseDouble(value), Distance.Angstrom);
				else qmCutoff = cutoffElectrostatics;
	
				boolean qmPME = true;
				value = parameters.get("qm_pme");
				if (value != null && !value.equals("false"))
					qmPME = value.equals("1");
				value = parameters.get("qm_ewald");
				int qmEwald = ( bc.equals(ParameterSet.BC_NON_PERIODIC) ? 0 : 1 );
				if (value != null)
					qmEwald = Integer.parseInt(value);
				if (qmEwald == 0)
					qmElectrostatics = new ElectrostaticsModel(ElectrostaticsModel.CUT_OFF);
				else{
					if (qmPME)
						qmElectrostatics = new PMEModel();
					else 
						qmElectrostatics = new ElectrostaticsModel(ElectrostaticsModel.CLASSIC_EWALD);
				}
				qmElectrostatics.setCutoff(qmCutoff);
				
				//constraints in QM region
				value = parameters.get("qmshake");
				if (value != null){
					qmConstraints = new ArrayList<Constraint>();
					Constraint constraint = new Constraint(Constraint.SHAKE);
					constraint.setTarget(Constraint.TARGET_BONDS_TO_HYDROGEN_QM);
					qmConstraints.add(constraint);
				}
				//treatment of QM-MM boundary region 
				value = parameters.get("qmmm_int");
				int qmmmInterId = 1;
				if (value != null)
					qmmmInterId = Integer.parseInt(value);
				switch (qmmmInterId){
					case 0: 
						qmmmInteractionType = "No electrostatic interaction between QM and MM atoms in the direct space sum";
						break;
					case 1: 
						qmmmInteractionType = "QM-MM interactions in direct space are calculated in the same way for all of the various semi-empirical hamiltonians.";
						break;
					case 2: 
						qmmmInteractionType = "Extra Gaussian terms that are introduced in AM1, PM3, or derived methods to improve the core-core repulsion term in QM-QM interactions are also included for the QM-MM interactions";
						break;
					case 3: 
						qmmmInteractionType = "Reformulated QM core-MM charge potential at the QM-MM interface";
						break;
					case 4: 
						qmmmInteractionType = null;
						break;
					case 5: 
						qmmmInteractionType = "Mechanical embedding";
						break;
					default: break;
				}
			}
		}

		List<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		
		
		// MINIMIZATION
		String isMinimizationStr = parameters.get("imin");
		if (isMinimizationStr != null && isMinimizationStr.equals("1"))
		{
			//number of cycles
			int maxcyc = 1;
			int ncyc = 10;
			String maxCyclesStr = parameters.get("maxcyc");
			if (maxCyclesStr!=null)
				maxcyc = Integer.parseInt(maxCyclesStr);
			String nCyclesStr = parameters.get("ncyc");
			if (nCyclesStr!=null)
				ncyc = Integer.parseInt(nCyclesStr);
			
			//minimization method
			value = parameters.get("ntmin");
			if (value == null || value.equals("1")){
				tasks.add(new MinimizationTask(new SteepestDescentParameterSet(ncyc)));
				tasks.add(new MinimizationTask(new ConjugateGradientParameterSet(maxcyc)));
			}
			else if (value.equals("0")){
				tasks.add(new MinimizationTask(new ConjugateGradientParameterSet(maxcyc)));
			}
			else if (value.equals("2")){
				tasks.add(new MinimizationTask(new SteepestDescentParameterSet(maxcyc)));
			}
			else if (value.equals("3")){
				tasks.add(new MinimizationTask(new MinimizationParameterSet("XMIN", maxcyc)));
			}
			else if (value.equals("4")){
				tasks.add(new MinimizationTask(new MinimizationParameterSet("LMOD", maxcyc)));
			}
			
			for (ExperimentTask task : tasks){
				MinimizationParameterSet min = ((MinimizationTask)task).getMinimizationMethod();
				min.setConstraints(constraints);
				min.setSolventType(solvent);
				if (implicitSolvent!=null)
					min.setImplicitSolventModel(implicitSolvent);

				task.setSimulatedConditionSet(env);
				task.setBoundaryConditions(bc);
			}
			return tasks;
		}
		else
		{
			MDParameterSet mdMethod = null;
			QMParameterSet qmMethod = null;
			QMMMParameterSet qmmmMethod = null;
			REMDParameterSet remdMethod = null;
			QuantumMDParameterSet quantumMdMethod = null;
		
			if (methodName.equals(ParameterSet.METHOD_MD) || 
				methodName.equals(ParameterSet.METHOD_REMD) || 
				methodName.equals(ParameterSet.METHOD_LANGEVIN_DYNAMICS) || 
				methodName.equals(ParameterSet.METHOD_QUANTUM_MD) || 
				methodName.equals(ParameterSet.METHOD_QMMM)){

				mdMethod = new MDParameterSet();
					
				if (barostat!=null)
					mdMethod.setBarostat(barostat);
				if (thermostat!=null)
					mdMethod.setThermostat(thermostat);
				mdMethod.setEnsemble(ensemble);
				mdMethod.setElectrostatics(electro);
				mdMethod.setConstraints(constraints);
				mdMethod.setRestraints(restraints);
				mdMethod.setSolventType(solvent);
				mdMethod.setTimeStepLength(dt);
				mdMethod.setNumberOfSteps((int)nsteps);
				mdMethod.setSimulatedTime(time);
				
				mdMethod.setCutoffForVanDerWaals(cutoffVdW);
				mdMethod.setEnsemble(ensembleType);
				
				if (isREMD){
					remdMethod = new REMDParameterSet();					
					SamplingMethod samplingMethod = new SamplingMethod(remdType);
					remdMethod.setNumberOfExchanges(remdNumberOfExchanges);
					remdMethod.setSamplingMethod(samplingMethod);
				}
				
				if (implicitSolvent!=null)
					mdMethod.setImplicitSolventModel(implicitSolvent);
				
				if (!methodName.equals(ParameterSet.METHOD_QMMM) && 
						!methodName.equals(ParameterSet.METHOD_QUANTUM_MD)){
					if (!isREMD){
						//classic MD
						MDTask task = new MDTask(mdMethod);
						task.setSimulatedConditionSet(env);
						task.setBoundaryConditions(bc);
						tasks.add(task);
					}
					else {
						//REMD
						REMDTask task = new REMDTask(mdMethod, remdMethod);
						task.setSimulatedConditionSet(env);
						task.setBoundaryConditions(bc);
						tasks.add(task);
					}
				}
			}
			
			if (methodName.equals(ParameterSet.METHOD_QM) || 
					methodName.equals(ParameterSet.METHOD_QMMM) || 
					methodName.equals(ParameterSet.METHOD_QUANTUM_MD))
			{
				qmMethod = new QMParameterSet();
				qmMethod.setSpecificMethodName(levelOfTheory);
				qmMethod.setBasisSet(basisSet);
				qmMethod.setSpinMultiplicity(qmSpin);
				qmMethod.setTotalCharge(qmCharge);
				
				if (methodName.equals(ParameterSet.METHOD_QM)){
					QMTask task = new QMTask(qmMethod);
					task.setSimulatedConditionSet(env);
					task.setBoundaryConditions(bc);
					tasks.add(task);
				}
			}
			
			if (methodName.equals(ParameterSet.METHOD_QMMM))
			{
				qmmmMethod = new QMMMParameterSet();
				qmmmMethod.setQmSpaceDefinition(qmSpace);
				qmmmMethod.setBoundaryTreatment(qmmmInteractionType);
				qmmmMethod.setQmElectrostatics(qmElectrostatics);
				qmmmMethod.setQmConstraints(qmConstraints);
				QMMMTask task = new QMMMTask(mdMethod, qmMethod, qmmmMethod);
				task.setSimulatedConditionSet(env);
				task.setBoundaryConditions(bc);
				tasks.add(task);
			}
			
			if (methodName.equals(ParameterSet.METHOD_QUANTUM_MD))
			{
				quantumMdMethod = new QuantumMDParameterSet(quantumMdMethodName);
				QuantumMDTask task = new QuantumMDTask(qmMethod, mdMethod, quantumMdMethod);
				task.setSimulatedConditionSet(env);
				task.setBoundaryConditions(bc);
				tasks.add(task);
			}
			
			return tasks;
		}
	}
	
	private static boolean findPhraseInSectionLabels(String phrase, List<String> parameterSectionLabels){
		if (parameterSectionLabels!=null){
			int s=0;
			while (s<parameterSectionLabels.size()){
				if (parameterSectionLabels.get(s).toLowerCase().matches(".*" + phrase.toLowerCase() + ".*"))
					return true;
				s++;
			}
		}
		return false;
	}
}
