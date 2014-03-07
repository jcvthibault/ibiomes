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

package edu.utah.bmi.ibiomes.parse.chem.namd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.comp.Constraint;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Barostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.ElectrostaticsModel;
import edu.utah.bmi.ibiomes.experiment.comp.mm.LangevinThermostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.PMEModel;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Thermostat;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMTask;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMTask;
import edu.utah.bmi.ibiomes.quantity.Frequency;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * Map AMBER parameter keywords to metadata
 * @author Julien Thibault
 *
 */
public class NAMDInputKeywordMapper {

	/**
	 * Map NAMD parameter keywords to method
	 * @param parameters List of NAMD parameters and values
	 * @throws Exception
	 */
	public static ExperimentTask mapToTask(HashMap<String, String> parameters)
	{
		String value = null;
		String methodName = ParameterSet.METHOD_MD;
		Barostat barostat = null;
		Thermostat thermostat = null;
		ElectrostaticsModel electro = null;
		String bc = null;
		List<Constraint> constraints = new ArrayList<Constraint>();
		String ensemble = null;
		String levelOfTheory = null;
		String implicitSolvent = null;
		double nsteps = 0.0;
		TimeLength dt = null, time = null;
		Constraint constraint = null;
		Frequency collisionFrequency = null;
				
		//time
		value = parameters.get("run");
		if (value !=null){
			nsteps = Integer.parseInt(value);
		}
		value = parameters.get("timestep");
		if (value !=null){
			dt = new TimeLength(Double.parseDouble(value), TimeLength.Picosecond);
			time = new TimeLength(dt.getValue() * nsteps, dt.getUnit());
		}
		
		//boundary conditions
		if (parameters.containsKey("cellbasisvector1")
			&& parameters.containsKey("cellbasisvector2")
			&& parameters.containsKey("cellbasisvector3"))
		{
			bc = ParameterSet.BC_PERIODIC;
		}
		else
			bc = ParameterSet.BC_NON_PERIODIC;
		
		//PME
		value = parameters.get("pme");
		if (value.equals("yes")){
			electro = new PMEModel();
			bc = ParameterSet.BC_PERIODIC;
		}
		else if (parameters.containsKey("cutoff"))
			electro = new ElectrostaticsModel(ElectrostaticsModel.CUT_OFF);
		
		//LANGEVIN
		value = parameters.get("langevin");
		if (value!=null && value.equals("on")){
			methodName = ParameterSet.METHOD_LANGEVIN_DYNAMICS;
			value = parameters.get("langevindamping");
			collisionFrequency = new Frequency(Double.parseDouble(value), "ps^-1");
			thermostat = new LangevinThermostat();
			((LangevinThermostat)thermostat).setCollisionFrequency(collisionFrequency);
		}

		//PRESSURE / TEMPERATURE
		value = parameters.get("berendsenpressure");
		if (value!=null && value.equals("yes")){
			barostat = new Barostat(Barostat.BAROSTAT_BERENDSEN);
		}
		
		//IMPLICIT SOLVENT
		value = parameters.get("gbis");
		if (value!=null && value.equals("on")){
			implicitSolvent = "GB OBC";
		}
		
		//CONSTRAINTS
		value = parameters.get("rigidbonds");
		if ( value!=null && !value.equals("off")){
			constraint = new Constraint();
			constraint.setTarget(value);
			value = parameters.get("usesettle");
			if (value!=null && value.equals("on"))
				constraint.setAlgorithm(Constraint.SETTLE);
			else
				constraint.setAlgorithm(Constraint.SHAKE);
		}
		
		MDParameterSet mdMethod = null;
		QMParameterSet qmMethod = null;
		
		if (methodName.equals(ParameterSet.METHOD_MD) || 
				methodName.equals(ParameterSet.METHOD_LANGEVIN_DYNAMICS) || 
				methodName.equals(ParameterSet.METHOD_QMMM)){
			
			mdMethod = new MDParameterSet();

			mdMethod.setBarostat(barostat);
			mdMethod.setThermostat(thermostat);
			mdMethod.setEnsemble(ensemble);
			mdMethod.setElectrostatics(electro);
			mdMethod.setConstraints(constraints);
			//mdMethod.setForceField(forceField);
			mdMethod.setTimeStepLength(dt);
			mdMethod.setNumberOfSteps((int)nsteps);
			mdMethod.setSimulatedTime(time);
			if (implicitSolvent!=null){
				mdMethod.setImplicitSolventModel(implicitSolvent);
				mdMethod.setSolventType(ParameterSet.SOLVENT_IMPLICIT);
			}
			
			if (!methodName.equals(ParameterSet.METHOD_QMMM)){
				MDTask task = new MDTask(mdMethod);
				task.setSoftware(new Software(Software.NAMD));
				task.setBoundaryConditions(bc);
				return task;
			}
		}
		if (methodName.equals(ParameterSet.METHOD_QM) || methodName.equals(ParameterSet.METHOD_QMMM))
		{
			qmMethod = new QMParameterSet();
			qmMethod.setSpecificMethodName(levelOfTheory);
			
			if (methodName.equals(ParameterSet.METHOD_QM))
			{
				QMTask task = new QMTask(qmMethod);
				task.setSoftware(new Software(Software.NAMD));
				task.setBoundaryConditions(bc);
				return task;
			}
		}
		if (methodName.equals(ParameterSet.METHOD_QMMM))
		{
			QMMMParameterSet qmmmMethod = new QMMMParameterSet();
			QMMMTask task = new QMMMTask(mdMethod, qmMethod, qmmmMethod);
			task.setSoftware(new Software(Software.NAMD));
			task.setBoundaryConditions(bc);
			return task;
		}
		return null;
	}
}
