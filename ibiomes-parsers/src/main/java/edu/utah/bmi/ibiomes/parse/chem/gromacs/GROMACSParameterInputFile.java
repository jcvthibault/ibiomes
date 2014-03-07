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

package edu.utah.bmi.ibiomes.parse.chem.gromacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.comp.ConjugateGradientParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.Constraint;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.SimulatedConditionSet;
import edu.utah.bmi.ibiomes.experiment.comp.SteepestDescentParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.min.MinimizationParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.min.MinimizationTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Barostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.ElectrostaticsModel;
import edu.utah.bmi.ibiomes.experiment.comp.mm.LangevinThermostat;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Restraint;
import edu.utah.bmi.ibiomes.experiment.comp.mm.Thermostat;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMTask;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMTask;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractParameterFile;
import edu.utah.bmi.ibiomes.quantity.Frequency;
import edu.utah.bmi.ibiomes.quantity.Pressure;
import edu.utah.bmi.ibiomes.quantity.Temperature;
import edu.utah.bmi.ibiomes.quantity.TimeLength;

/**
 * GROMACS parameter input file (.mdp)
 * Defines the parameters for running the simulation, including time step, 
 * type of simulation, electrostatics, van der Waals, temperature coupling, 
 * pressure coupling, etc.
 * 
 * @author Julien Thibault, University of Utah
 *
 */
public class GROMACSParameterInputFile extends AbstractParameterFile
{
	private static final long serialVersionUID = 244857191937798175L;
	private HashMap<String, String> parameters;
	
	/**
	 * Default constructor.
	 * @param pathname Log file path.
	 * @throws Exception
	 */
	public GROMACSParameterInputFile(String pathname) throws Exception
	{
		super(pathname, LocalFile.FORMAT_GROMACS_MDP);
		parameters = new HashMap<String, String>();
		parseFile();
	}

	/**
	 * Get Parameter input metadata and GROMACS-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception
	{	
		MetadataAVUList metadata = super.getMetadata();
		return metadata;
	}
	
	/**
	 * Parse GROMACS parameter file
	 * @param filePath
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		IBIOMESFileReader br = null;
	    String line = null;
		try{
			br = new IBIOMESFileReader(this);
		    
	        while (( line = br.readLine()) != null)
	        {
	        	line = line.trim();
	        	if (line.length()>0 && line.indexOf('=')>0 && !line.startsWith(";"))
	        	{
	    			String[] values = line.split("\\=");
	    			String attribute = values[0].trim().toLowerCase();
	    			String value = null;
	    			if (values.length>1){
	    				value = values[1].trim().toLowerCase();
		    			//check if there's a comment at the end of the line
		    			int commentIndex = value.indexOf(';');
		    			if (commentIndex!=-1){
		    				value = value.substring(0, commentIndex).trim();
		    			}
	    			}
	    			parameters.put(attribute, value);
	    		 }
	        }
	        br.close();
	        
	        ExperimentTask task = mapKeysToTask();
			task.setSoftware(new Software(Software.GROMACS));
			task.setInputFiles(this.getCanonicalPath());
	        this.tasks = new ArrayList<ExperimentTask>();
	        this.tasks.add(task);
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a GROMACS parameter file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			if (br!=null)
				try {
					br.close();
				} catch (IOException e1) {
				}
			throw e;
		}
	}

	private ExperimentTask mapKeysToTask()
	{
		String value = null;
		TimeLength dt = null, time = null;
		double nsteps = 0.0;
		Frequency frictionCoeff = null;
		String methodName = null;
		String minMethodName = null;
		String integrator = null;
		String barostat = null, thermostat = null;
		String boundaryConditions = null;
		String solvent = null;
		String implicitSolvent = null;
		ElectrostaticsModel electrostaticsModel = null;
		List<Constraint> constraints = new ArrayList<Constraint>();
		String basisSet = null;
		String qmMethodName = null;
		int spinMultiplicity = 1;
		int totalCharge = 0;
		SimulatedConditionSet env = null;

		Software software = new Software(Software.GROMACS);
		
		//CONSTRAINTS
		value = parameters.get("lincs_order");
		if (value != null){
			constraints.add(new Constraint(Constraint.LINCS));
		}
		value = parameters.get("shake_tol");
		if (value != null){
			constraints.add(new Constraint(Constraint.SHAKE));
		}
		
		//RESTRAINTS
		if (parameters.get("disre")!=null && parameters.get("orire")!=null){
			List<Restraint> restraints = new ArrayList<Restraint>();
			value = parameters.get("disre");
			if (value != null){
				restraints.add(new Restraint(Restraint.RESTRAINT_DISTANCE));
			}
			value = parameters.get("orire");
			if (value != null){
				restraints.add(new Restraint(Restraint.RESTRAINT_POSITION));
			}
		}
		
		//SOLVENT
		value = parameters.get("implicit_solvent");
		if (value != null && !value.equals("no")){
			solvent = ParameterSet.SOLVENT_IMPLICIT;
			value = parameters.get("gb_algorithm");
			if (value != null){
				if (value.equals("hct"))
					implicitSolvent = "GB HCT";
				else if (value.equals("obc"))
					implicitSolvent = "GB OBC";
				else if (value.equals("still"))
					implicitSolvent = "GB Still";
			}
		}
		
		// TIME
		value = parameters.get("dt");
		if (value != null && value.length()>0){
			dt = new TimeLength(Double.parseDouble(value), TimeLength.Picosecond);
		}
		value = parameters.get("nsteps");
		if (value != null && value.length()>0){
			 nsteps = Double.parseDouble(value);
			 if (dt != null){
				 time = new TimeLength(dt.getValue() * nsteps, dt.getUnit());
			 }
		}

		// BOUNDARY CONDITIONS
		value = parameters.get("pbc");
		if (value != null){
			if (value.equals("no"))
				boundaryConditions = ParameterSet.BC_NON_PERIODIC;
			else boundaryConditions = ParameterSet.BC_PERIODIC;
		}
		
		//MM INTEGRATOR & METHOD
		value = parameters.get("integrator");
		if (value != null)
		{
			//type of MD method
			if (value.equals("sd") || value.equals("sdl") || value.equals("bd")){
				methodName = ParameterSet.METHOD_LANGEVIN_DYNAMICS;
				// friction coefficient
				value = parameters.get("bd-fric");
				if (value != null){
					frictionCoeff = new Frequency(Double.parseDouble(value));
				}
			}
			else if (value.equals("steep")){
				methodName = ParameterSet.METHOD_MINIMIZATION;
				minMethodName = ParameterSet.METHOD_STEEPEST_DESCENT;
			}
			else if (value.equals("cg")){
				methodName = ParameterSet.METHOD_MINIMIZATION;
				minMethodName = ParameterSet.METHOD_CONJUGATE_GRADIENT;
			}
			else if (value.equals("md") || value.equals("md-vv") || value.equals("md-vv-avek")){
				methodName = ParameterSet.METHOD_MD;
			}
			else {
				methodName = value;				
			}
			
			//actual integrators
			if (value.equals("md") || value.equals("sd") || value.equals("sdl"))
				integrator = MDParameterSet.INTEGRATOR_LEAP_FROG;
			else if (value.equals("md-vv") || value.equals("md-vv-avek"))
				integrator = MDParameterSet.INTEGRATOR_VELOCITY_VERLET;
			else if (value.equals("bd"))
				integrator = MDParameterSet.INTEGRATOR_EULER;
			else
				integrator = value;
		}

		//ENVIRONMENT
		if (parameters.get("ref_t")!=null && parameters.get("ref_p")!=null){
			Temperature temperature=null;
			Pressure pressure=null;
			double pH = 0.0;
			value = parameters.get("ref_t");
			if (value != null && value.length()>0){
				String[] values = value.split("\\s+");
				temperature = new Temperature(Double.parseDouble(values[0]));
			}
			value = parameters.get("ref_p");
			if (value != null && value.length()>0){
				String[] values = value.split("\\s+");
				pressure = new Pressure(Double.parseDouble(values[0]));
			}
			env = new SimulatedConditionSet(pressure, temperature);
		}
		
		// ENSEMBLE, BAROSTAT, AND THERMOSTAT
		value = parameters.get("pcoupl");
		if (value != null && !value.equals("no"))
		{
			if (value.equals("berendsen"))
				barostat = Barostat.BAROSTAT_BERENDSEN;
			else if (value.equals("parrinello-rahman"))
				barostat = Barostat.BAROSTAT_PARINELLO_RAHMAN;
			else 
				barostat = value;
		}
		
		value = parameters.get("tcoupl");
		if (value != null && !value.equals("no"))
		{
			if (value.equals("berendsen"))
				thermostat = Thermostat.THERMOSTAT_BERENDSEN;
			else if (value.equals("nose-hoover"))
				thermostat = Thermostat.THERMOSTAT_NOSE_HOOVER;
			else if (value.equals("v-rescale"))
				thermostat = Thermostat.THERMOSTAT_V_RESCALE;
			else 
				thermostat = value;
		}
		
		//ELECTROSTATICS
		value = parameters.get("coulomb_type");
		if (value != null)
		{
			String electrostatics = null;
			if (value.equals("cut-off"))
				electrostatics = ElectrostaticsModel.CUT_OFF;
			else if (value.equals("ewald"))
				electrostatics = ElectrostaticsModel.CLASSIC_EWALD;
			else if (value.equals("pme"))
				electrostatics = ElectrostaticsModel.PME;
			else if (value.equals("pppm"))
				electrostatics = ElectrostaticsModel.PPPME;
			else if (value.startsWith("reaction-field"))
				electrostatics = ElectrostaticsModel.REACTION_FIELD;
			else if (value.startsWith("generalized-reaction-field"))
				electrostatics = ElectrostaticsModel.GENERALIZED_REACTION_FIELD;
			else if (value.equals("shift"))
				electrostatics = ElectrostaticsModel.SHIFT;
			else if (value.equals("switch"))
				electrostatics = ElectrostaticsModel.SWITCH;
			else 
				electrostatics = value;
			electrostaticsModel = new ElectrostaticsModel(electrostatics);
		}
		
		// QM/MM
		value = parameters.get("qmmm");
		if (value != null && value.equals("yes"))
		{
			methodName = ParameterSet.METHOD_QMMM;
			
			//level of theory
			value = parameters.get("qmmethod");
			if (value == null)
				qmMethodName = "RHF";
			else qmMethodName = value.toUpperCase();
			
			//basis set
			value = parameters.get("qmbasis");
			if (value == null)
				basisSet = "STO-3G";
			else basisSet = value.toUpperCase();
			
			//spin multiplicity
			value = parameters.get("qmmult");
			if (value == null)
				spinMultiplicity = 1;
			else spinMultiplicity =  Integer.parseInt(value);
			
			//total charge
			value = parameters.get("qmcharge");
			if (value == null)
				totalCharge = 0;
			else totalCharge = Integer.parseInt(value);
		}
		
		ExperimentTask task = null;
		
		// MINIMIZATION
		if (methodName.equals(ParameterSet.METHOD_MINIMIZATION))
		{
			MinimizationParameterSet min = null;
			if (minMethodName.equals(ParameterSet.METHOD_CONJUGATE_GRADIENT)){
				min = new ConjugateGradientParameterSet((int)nsteps);
			}
			else if (minMethodName.equals(ParameterSet.METHOD_STEEPEST_DESCENT)){
				min = new SteepestDescentParameterSet((int)nsteps);
			}
			else {
				min = new MinimizationParameterSet(minMethodName, (int)nsteps);
			}
			min.setConstraints(constraints);
			min.setSolventType(solvent);
			if (implicitSolvent!=null)
				min.setImplicitSolventModel(implicitSolvent);
			
			task = new MinimizationTask(min);
			task.setSimulatedConditionSet(env);
			task.setBoundaryConditions(boundaryConditions);
			task.setSoftware(software);
			return task;
		}
		else // MD, QM, or QM/MM
		{			
			MDParameterSet mdMethod = null;
			QMParameterSet qmMethod = null;
			QMMMParameterSet qmmmMethod = null;

			if (!methodName.equals(ParameterSet.METHOD_QM))
			{
				mdMethod = new MDParameterSet();
				
				if (methodName.equals(ParameterSet.METHOD_LANGEVIN_DYNAMICS)){
					LangevinThermostat langevTherm = new LangevinThermostat();
					langevTherm.setCollisionFrequency(frictionCoeff);
					mdMethod.setThermostat(langevTherm);
				}
				else if (thermostat!=null)
					mdMethod.setThermostat(new Thermostat(thermostat));
				
				if (barostat!=null)
					mdMethod.setBarostat(new Barostat(barostat));
				mdMethod.setElectrostatics(electrostaticsModel);
				mdMethod.setIntegrator(integrator);
				mdMethod.setConstraints(constraints);
				mdMethod.setSolventType(solvent);
				mdMethod.setTimeStepLength(dt);
				mdMethod.setNumberOfSteps((int)nsteps);
				mdMethod.setSimulatedTime(time);
				if (implicitSolvent!=null)
					mdMethod.setImplicitSolventModel(implicitSolvent);
				
				if (!methodName.equals(ParameterSet.METHOD_QMMM)){
					task = new MDTask(mdMethod);
				}
			}
			if (!methodName.equals(ParameterSet.METHOD_MD))
			{
				qmMethod = new QMParameterSet();
				qmMethod.setSpecificMethodName(qmMethodName);
				qmMethod.setBasisSet(basisSet);
				qmMethod.setSpinMultiplicity(spinMultiplicity);
				qmMethod.setTotalCharge(totalCharge);
				
				if (methodName.equals(ParameterSet.METHOD_QM)){
					task = new QMTask(qmMethod);
				}
			}
			if (methodName.equals(ParameterSet.METHOD_QMMM))
			{
				qmmmMethod = new QMMMParameterSet();
				task = new QMMMTask(mdMethod, qmMethod, qmmmMethod);
			}
			
			task.setSoftware(software);
			task.setBoundaryConditions(boundaryConditions);
			task.setSimulatedConditionSet(env);
			
			return task;
		}
	}
}
