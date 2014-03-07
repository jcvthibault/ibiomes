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

package edu.utah.bmi.ibiomes.parse.chem.gamess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractProcessGroupSetFile;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcess;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.comp.qm.LevelOfTheory;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMTask;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.MolecularSystemFactory;

/**
 * GAMESS input file (.inp)
 * @author Julien Thibault, University of Utah
 *
 */
public class GAMESSInputFile extends AbstractProcessGroupSetFile
{
	private static final long serialVersionUID = -3745984028206526495L;
	
	private final static String END_CMD = "$END";

	private HashMap<String, String> parameters;
	
	/**
	 * Default constructor.
	 * @param pathname Topology file path.
	 * @throws Exception
	 */
	public GAMESSInputFile(String pathname) throws Exception
	{
		super(pathname, LocalFile.FORMAT_GAMESS_INPUT);
		parameters = new HashMap<String, String>();
		parseFile();
	}

	/**
	 * Load GAUSSIAN topology file
	 * @param filePath
	 * @throws Exception 
	 */
	private void parseFile() throws Exception 
	{
	    String line = null;
	    ArrayList<String> dataLines = new ArrayList<String>();
	    IBIOMESFileReader br = null;
	    try{
			br = new IBIOMESFileReader(this);
			while (( line = br.readLine()) != null)
	        {
	        	line = line.trim();
	        	if (line.length()>0)
	        	{
	        		if (line.startsWith("$DATA"))
	        		{
	        			//System.out.println("DATA:");
	        			while (( line = br.readLine()) != null && !line.equals(END_CMD))
	        	        {
	        				line = line.trim();
	            			//System.out.println("\t" + line);
	            			dataLines.add(line);
	        	        }
	        		}
	        		else if (line.startsWith("$"))
	        		{
	        			boolean endFound = false;
	        			String cmd = "";
	        			int index = line.indexOf(' ');
	        			String cmdName = line.substring(1, index);
	        			line = line.substring(index);
	        			
	        			if (line.endsWith(END_CMD)){
	        				endFound = true;
	        				line = line.substring(0, line.length() - END_CMD.length());
	        			}
	    				cmd += line + " ";
	    				
	        			while (!endFound && ( line = br.readLine()) != null)
	        	        {
	        				line = line.trim();
	        				if (line.endsWith(END_CMD)){
	            				endFound = true;
	            				line = line.substring(0, line.length() - END_CMD.length());
	            			}
	        				cmd += line + " ";
	            			
	        	        }
	        			cmd = cmd.trim();
	        			//System.out.println(cmdName + " > " + cmd);
	        			
	        			String[] values = cmd.split("\\s+");
	        			for (int v=0;v<values.length;v++)
	        			{
	        				String value = values[v];
	        				String[] pair = value.split("\\s*\\=\\s*");
	        				parameters.put(pair[0].toLowerCase(), pair[1].toLowerCase());
	        				//System.out.println("\t" + pair[0] + " = " + pair[1]);
	        			}
	        		}
	        	}
	        }
			br.close(); 
			
			//build process groups
			MolecularSystem molecularSystem = mapDataToTopology(dataLines);
			List<ExperimentTask> tasks = mapParametersToMethod();
    		ExperimentProcess process = new ExperimentProcess(null, null, tasks);
    		ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, null, molecularSystem, process);
    		this.processGroups = new ArrayList<ExperimentProcessGroup>();
    		this.processGroups.add(processGroup);
	    }
	    catch (Exception e){
	    	this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a GAMESS input file.");
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

	private List<ExperimentTask> mapParametersToMethod() throws IOException
	{
		QMParameterSet method = new QMParameterSet();
		String value = null;
		String mpLevel = parameters.get("mplevl");
		String dft = value = parameters.get("dfttyp");
		
		//method
		value = parameters.get("scftyp");
		if (value != null)
		{
			String methodName = null;
			String levelOfTheory = null;
			List<String> levelsOfTheory = new ArrayList<String>();
			
			//Moller-Plesset
			if (mpLevel!=null && !mpLevel.equals("0")){
				methodName = "MP" + mpLevel;
				levelOfTheory = LevelOfTheory.QM_PERTURBATION_THEORY;
			}
			//DFT
			else if (dft != null && !dft.equals("none")){
				levelOfTheory = LevelOfTheory.QM_METHOD_DFT;
				methodName = dft.toUpperCase();
				method.setExchangeCorrelationFn(methodName);
			}
			//other
			else methodName = value.toUpperCase();

			method.setSpecificMethodName(methodName);
			if (levelsOfTheory!=null)
				method.setLevelOfTheory(levelOfTheory);
		}
		
		//basis set
		value = parameters.get("gbasis");
		if (value != null){
			List<String> basisSets = new ArrayList<String>();
			basisSets.add(value);
			method.setBasisSets(basisSets);
		}
		
		//type of run
		value = parameters.get("runtyp");
		List<String> calculationTypes = new ArrayList<String>();
		if (value != null){
			if (value.equals("energy"))
				calculationTypes.add(QMParameterSet.CALCULATION_ENERGY_SP);
			else if (value.equals("gradient"))
				calculationTypes.add(QMParameterSet.CALCULATION_ENERGY_GRADIENT);
			else if (value.equals("optimize"))
				calculationTypes.add(QMParameterSet.QM_GEOMETRY_OPTIMIZATION);
			else if (value.equals("irc"))
				calculationTypes.add(QMParameterSet.CALCULATION_IRC);
			else if (value.equals("sadpoint"))
				calculationTypes.add(QMParameterSet.CALCULATION_SADDLE);
			else if (value.equals("globop"))
				calculationTypes.add(QMParameterSet.QM_OPTIMIZATION_MONTE_CARLO);
			else if (value.equals("nmr"))
				calculationTypes.add(QMParameterSet.CALCULATION_NMR);
			else if (value.equals("raman"))
				calculationTypes.add(QMParameterSet.CALCULATION_RAMAN);
			else if (value.equals("raman"))
				calculationTypes.add(value);
		}
		
		//charge and spin multiplicity
		value = parameters.get("mult");
		if (value != null){
			method.setSpinMultiplicity(Integer.valueOf(value));
		}
		value = parameters.get("icharg");
		if (value != null){
			method.setTotalCharge(Integer.valueOf(value));
		}
		
		QMTask task = new QMTask(method);
		task.setSoftware(new Software(Software.GAMESS));
		task.setCalculationTypes(calculationTypes);
		ArrayList<String> relatedFiles = new ArrayList<String>();
		relatedFiles.add(this.getCanonicalPath());
		task.setInputFiles(relatedFiles);
		ArrayList<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		tasks.add(task);
		return tasks;
	}
	
	private MolecularSystem mapDataToTopology(ArrayList<String> data) throws IOException
	{
		//1st line is decription
		String desc = data.get(0);
		if (desc.length()>0)
			this.description = desc;
		
		//atoms (start from second line and skip last line containing $END)
		boolean isZMatrix = false;
		List<String[]> zMatrix = new ArrayList<String[]>();
		ArrayList<Atom> atomList = new ArrayList<Atom>();
		for (int a=2;a<data.size()-1;a++){
			String[] values = data.get(a).split("\\s+");
			Atom atom = new Atom(a-1, values[0], values[0]);
			if (values.length>4 && !isZMatrix){
				atom.setCoordinates(
						new Coordinate3D(
								Double.parseDouble(values[2]), 
								Double.parseDouble(values[3]), 
								Double.parseDouble(values[4])));
			}
			else // Z-matrix
    		{
    			isZMatrix = true;
    			zMatrix.add(values);
    		}
			atomList.add(atom);
		}
		//convert z-matrix to Cartesian. Example:
        // C
        // H   1 B1
        // C   1 B2   2 A1
        // H   3 B3   1 A2   2 D
        if (isZMatrix)
        {
        	int first_atoms[] = new int[atomList.size()];
        	double distances[] = new double[atomList.size()];
        	int second_atoms[] = new int[atomList.size()];
        	double angles[] = new double[atomList.size()];
        	int third_atoms[] = new int[atomList.size()];
        	double dihedrals[] = new double[atomList.size()];
        	boolean usesStupidFormat = true;;
        	
        	for (int a=1;a<atomList.size();a++)
        	{
        		String[] lineSplit = zMatrix.get(a);
        		if (lineSplit.length<3){
        			usesStupidFormat = true;
        			break;
        		}
	        	first_atoms[a] = Integer.parseInt(lineSplit[1])-1;
        		//parse distances
        		double d = Double.parseDouble(lineSplit[2]);
        		distances[a] = d;
        		//parse angles
        		if (lineSplit.length>4){
        			second_atoms[a] = Integer.parseInt(lineSplit[3])-1;
        			double angle = Double.parseDouble(lineSplit[4]);
	        		angles[a] = angle;
        			//parse dihedrals
	        		if (lineSplit.length>6){
	        			third_atoms[a] = Integer.parseInt(lineSplit[5])-1;
	        			double dihedral = Double.parseDouble(lineSplit[6]);
	        			dihedrals[a] = dihedral;
	        		}
        		}
	        }
        	if (usesStupidFormat){
        		for (int a=1;a<atomList.size();a++)
            	{
        			String[] lineSplit = zMatrix.get(a);
	        		//parse distances
	        		double d = Double.parseDouble(lineSplit[1]);
	        		distances[a] = d;
	        		if (a==1)
	        			first_atoms[a] = 0;
	        		else if (a==2){
	        			first_atoms[a] = Integer.parseInt(lineSplit[5])-1;
	        			second_atoms[a] = Integer.parseInt(lineSplit[6])-1;
	        			double angle = Double.parseDouble(lineSplit[3]);
		        		angles[a] = angle;
	        		}
	        		else {
	        			first_atoms[a] = Integer.parseInt(lineSplit[7])-1;
	        			second_atoms[a] = Integer.parseInt(lineSplit[8])-1;
	        			third_atoms[a] = Integer.parseInt(lineSplit[9])-1;
	        			double angle = Double.parseDouble(lineSplit[3]);
		        		angles[a] = angle;
		        		double dihedral = Double.parseDouble(lineSplit[5]);
	        			dihedrals[a] = dihedral;
	        		}
            	}
        	}
        	//get coordinates
        	Coordinate3D[] coords = Coordinate3D.loadFromZMatrix(distances, first_atoms, angles, second_atoms, dihedrals, third_atoms);
        	for (int a=0;a<atomList.size();a++){
        		atomList.get(a).setCoordinates(coords[a]);
        		//System.out.println(atomList.get(a).getName() + " " + (float)coords[a].getX() + " " + (float)coords[a].getY() + " " + (float)coords[a].getZ());
        	}
        }
        
        MolecularSystemFactory moleculeFactory = new MolecularSystemFactory();
		MolecularSystem molecularSystem = moleculeFactory.identifyMoleculesUsingAtoms(atomList, true);
		molecularSystem.setDefinitionFiles(this.getCanonicalPath());
		return molecularSystem;
	}
}
