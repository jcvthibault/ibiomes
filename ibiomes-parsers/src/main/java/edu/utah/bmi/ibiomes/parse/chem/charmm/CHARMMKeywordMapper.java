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

package edu.utah.bmi.ibiomes.parse.chem.charmm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.comp.Constraint;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.SimulatedConditionSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Barostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.ElectrostaticsModel;
import edu.utah.bmi.ibiomes.experiment.comp.mm.LangevinThermostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Thermostat;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMTask;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMTask;
import edu.utah.bmi.ibiomes.quantity.Frequency;
import edu.utah.bmi.ibiomes.quantity.Pressure;
import edu.utah.bmi.ibiomes.quantity.Temperature;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * Map AMBER parameter keywords to metadata
 * @author Julien Thibault
 *
 */
public class CHARMMKeywordMapper {


	/**
	 * Map AMBER parameter keywords to metadata
	 * @param parameters List of AMBER parameters and values
	 * @throws Exception
	 */
	public static ExperimentTask mapKeysToTask(HashMap<String, List<String>> parameters)
	{
		String methodName = ParameterSet.METHOD_MD;
		Barostat barostat = null;
		Thermostat thermostat = null;
		Pressure refPressure = null;
		Temperature refTemperature = null;
		ElectrostaticsModel electro = null;
		String bc = null;
		List<Constraint> constraints = new ArrayList<Constraint>();
		String solvent = null;
		String ensemble = null;
		String levelOfTheory = null;
		Frequency collisionFreq = null;
		double nsteps = 0.0;
		TimeLength dt = null, time = null;
		SimulatedConditionSet simulatedConditionSet = null;
		
		//initialize software info
		Software software = new Software(Software.CHARMM);
		
		List<String> entries = null;
		
		//DYNAmics conmmand (MD run)
		entries = parameters.get("dynamics");
		if (entries == null)
			entries = parameters.get("dyna");
		
		if (entries != null) 
		{			
			//integrator is first parameter for DYNAmics line
			String integrator = entries.get(0);
			if (integrator.equals("verlet") || integrator.equals("orig")){
				integrator = MDParameterSet.INTEGRATOR_VERLET;
			}
			else if (integrator.equals("vverlet") || integrator.equals("vver") || integrator.equals("vv2")){
				integrator = MDParameterSet.INTEGRATOR_VELOCITY_VERLET;
			}
			else if (integrator.startsWith("leap")){
				integrator = MDParameterSet.INTEGRATOR_LEAP_FROG;
			}
			
			//time step length
			int index = entries.indexOf("timestep");
			if (index == -1)
				index = entries.indexOf("timestp");
			if (index == -1)
				index = entries.indexOf("time");
				
			if (index>-1){
				dt = new TimeLength(Double.parseDouble(entries.get(index+1)), TimeLength.Picosecond);
			}
			
			//number of steps/simulated time
			index = entries.indexOf("nstep");
			if (index == -1)
				index = entries.indexOf("nste");
				
			if (index>-1){
				nsteps = Double.parseDouble(entries.get(index+1));
				if (dt != null){
					time = new TimeLength(dt.getValue() * nsteps, dt.getUnit());
				}
			}
			
			//Reference temperature and pressure
			index = entries.indexOf("pref");
			if (index == -1)
				index = entries.indexOf("preference");
			if (index > -1){
				refPressure = new Pressure(Double.parseDouble(entries.get(index+1)), Pressure.Atmosphere);
			}
			index = entries.indexOf("reft");
			if (index > -1)
				refTemperature = new Temperature(Double.parseDouble(entries.get(index+1)), Temperature.Kelvin);
		
			if (refPressure!=null || refTemperature!=null)
				simulatedConditionSet = new SimulatedConditionSet(refPressure, refTemperature);

			//Thermostat
			index = entries.indexOf("hoover");
			if (index > -1)
				thermostat = new Thermostat(Thermostat.THERMOSTAT_NOSE_HOOVER);
			else {
				index = entries.indexOf("tcon");
				if (index == -1)
					index = entries.indexOf("tcons");
				if (index == -1)
					index = entries.indexOf("tconstant");
				if (index > -1)
					thermostat = new Thermostat(Thermostat.THERMOSTAT_BERENDSEN);
			}

			//Barostat
			index = entries.indexOf("pcon");
			if (index == -1)
				index = entries.indexOf("pcons");
			if (index == -1)
				index = entries.indexOf("pconstant");
			if (index > -1)
				barostat = new Barostat(Barostat.BAROSTAT_BERENDSEN);
			
			//collision frequency
			index = entries.indexOf("pgam");
			if (index == -1)
				index = entries.indexOf("pgamma");
			if (index > -1)
				collisionFreq = new Frequency(Double.parseDouble(entries.get(index+1)), "ps^-1");
		}
		
		//SHAKE
		entries = parameters.get("shake");		
		if (entries != null) 
		{			
			String shakeType = entries.get(0);
			if (!shakeType.equals("off")){
				constraints.add(new Constraint(Constraint.SHAKE));
			}
			
		}
		
		//LANGEVIN
		entries = parameters.get("langevin");
		if (entries == null)
			entries = parameters.get("lang");
		if (entries != null){			
			methodName = ParameterSet.METHOD_LANGEVIN_DYNAMICS;
		}
		
		//QM/MM
		entries = parameters.get("quantum");
		if (entries == null)
			entries = parameters.get("quan");
		if (entries != null){			
			methodName = ParameterSet.METHOD_QMMM;
			
		}		

		MDParameterSet mdMethod = null;
		QMParameterSet qmMethod = null;
		
		if (methodName.equals(ParameterSet.METHOD_MD) || 
				methodName.equals(ParameterSet.METHOD_LANGEVIN_DYNAMICS) || 
				methodName.equals(ParameterSet.METHOD_QMMM)){
			
			mdMethod = new MDParameterSet();
			
			if (methodName.equals(ParameterSet.METHOD_LANGEVIN_DYNAMICS)){
				thermostat = new LangevinThermostat();
				((LangevinThermostat)thermostat).setCollisionFrequency(collisionFreq);
			}
			mdMethod.setThermostat(thermostat);

			mdMethod.setBarostat(barostat);
			mdMethod.setEnsemble(ensemble);
			mdMethod.setElectrostatics(electro);
			mdMethod.setConstraints(constraints);
			//mdMethod.setForceField(forceField);
			mdMethod.setSolventType(solvent);
			mdMethod.setTimeStepLength(dt);
			mdMethod.setSimulatedTime(time);
			
			if (!methodName.equals(ParameterSet.METHOD_QMMM)){
				MDTask task = new MDTask(mdMethod);
				task.setSoftware(software);
				task.setSimulatedConditionSet(simulatedConditionSet);
				task.setBoundaryConditions(bc);
				return task;
			}
		}
		else if (methodName.equals(ParameterSet.METHOD_QM) || methodName.equals(ParameterSet.METHOD_QMMM))
		{
			qmMethod = new QMParameterSet();
			qmMethod.setSpecificMethodName(levelOfTheory);
			
			if (methodName.equals(ParameterSet.METHOD_QM)){
				QMTask task = new QMTask(qmMethod);
				task.setSoftware(software);
				task.setSimulatedConditionSet(simulatedConditionSet);
				return task;
			}
		}
		else if (methodName.equals(ParameterSet.METHOD_QMMM))
		{
			QMMMParameterSet qmmmMethod = new QMMMParameterSet();
			QMMMTask task = new QMMMTask(mdMethod, qmMethod, qmmmMethod);
			task.setSoftware(software);
			task.setSimulatedConditionSet(simulatedConditionSet);
			return task;
		}
		return null;
	}
}
