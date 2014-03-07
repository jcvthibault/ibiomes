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

package edu.utah.bmi.ibiomes.parse.chem.nwchem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.dictionaries.AtomicElement;
import edu.utah.bmi.ibiomes.dictionaries.PeriodicTable;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcess;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMTask;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMTask;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractProcessGroupSetFile;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.MolecularSystemFactory;

/**
 * NWChem input file (.nw).
 * @author Julien Thibault
 *
 */
public class NWChemInputFile extends AbstractProcessGroupSetFile
{
	private static final long serialVersionUID = 4602698589229011944L;
	private PeriodicTable periodicTable;
	
	/**
	 * Default constructor.
	 * @param pathname Input file path.
	 * @throws Exception
	 */
	public NWChemInputFile(String pathname) throws Exception {
		super(pathname, FORMAT_NWCHEM_INPUT);
		
		periodicTable = PeriodicTable.getInstance();
		try{
			parseFile();
		}
		catch(Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a PDB file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
		}
	}
	
	/**
	 * Load NWChem input file
	 * @param filePath
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
	    // retrieve geometries
    	List<MolecularSystem> molecularSystems = parseGeometries();
    	//retrieve geometry assignments
    	List<String> geometryAssignments = parseGeometryAssignments();
    	//retrieve titles
    	List<String> titles = parseTitles();
	    // retrieve basis sets
    	List<String> basisSets = parseBasisSets();
    	//retrieve tasks
    	List<ExperimentTask> tasks = parseTasks(basisSets, titles);
    	//retrieve charge
    	List<Double> charges = parseCharges();
    	
    	/*for (String title : titles){
    		System.out.println("Title: " + title);
    	}
    	for (MolecularSystem sys : molecularSystems){
    		System.out.println("MOLECULE\n" + sys.getMetadata());
    	}
    	for (ExperimentTask method : tasks){
    		System.out.println("METHOD\n" + method.getMetadata());
    	}*/

		this.processGroups = new ArrayList<ExperimentProcessGroup>();
		//if multiple systems, create multiple processes
    	if (molecularSystems.size()>1)
    	{
    		//if n methods = n systems assume 1 for 1
    		if (molecularSystems.size() == tasks.size()){
    			int m=0;
    			for (MolecularSystem system : molecularSystems){
    				
    				ExperimentProcessGroup processGroup = new ExperimentProcessGroup(
    						null, 
    						tasks.get(m).getDescription(), 
    						system, 
    						new ExperimentProcess(null, tasks.get(m).getDescription(), tasks.get(m)));
    				m++;
    				this.processGroups.add(processGroup);
    			}
    		}
    		else{
    			//TODO handle 'set geometry' markers to assign correct geometry to the methods
    		}
    	}
    	else {
    		MolecularSystem molecularSystem = null;
    		if (molecularSystems.size()>0){
    			molecularSystem = molecularSystems.get(0);
    			molecularSystem.setDefinitionFiles(this.getCanonicalPath());
    		}
    		String processDescription = "";
    		if (titles.size()>0){
    			for (String title : titles){
    				processDescription += title + "; ";
    			}
    			processDescription = processDescription.substring(0,processDescription.length()-2);
    		}
    		ExperimentProcess process = new ExperimentProcess(null, processDescription, tasks);
    		ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, processDescription, molecularSystem, process);
    		processGroups.add(processGroup);
    	}
	}
	
	/**
	 * Parse charges
	 * @return List of charges
	 */
	private List<Double> parseCharges() {
		IBIOMESFileReader br = null;
		String line = null;
		ArrayList<Double> charges =  new ArrayList<Double>();
		try{
			br =  new IBIOMESFileReader(this);
		    while (( line = br.readLine()) != null){
			    line = line.trim().toLowerCase();
			    if (line.startsWith("charge ")){
			    	charges.add(Double.parseDouble(line.substring(7).trim()));
			    }
		    }
		    br.close();
		    return charges;
		}
		catch (Exception e){
			e.printStackTrace();
			if (br!=null){
				try {
					br.close();
				} catch (IOException e1) {
				}
			}
			return null;
		}
	}

	/**
	 * Parse list of basis sets
	 * @return List of basis sets
	 */
	private List<String> parseBasisSets()
	{
		IBIOMESFileReader br = null;
		String line = null;
		ArrayList<String> basisSets =  new ArrayList<String>();
		try{
			br =  new IBIOMESFileReader(this);
		    while (( line = br.readLine()) != null)
		    {
		    	line = line.trim().toLowerCase();
		    	if (line.startsWith("basis"))
		    	{
		    		line = br.readLine().trim().toLowerCase();
		    		while (!line.equals("end"))
		    		{
		    			String[] lineSplit = line.split("( )+");
		    			if (lineSplit.length>2){
		    				String basisSet = lineSplit[lineSplit.length-1].replaceAll("\"", "");
		    				if (!basisSets.contains(basisSet)){
			    				basisSets.add(basisSet);
		    				}
		    			}
		    			line = br.readLine().trim().toLowerCase();
		    		}
		    	}
		    }
		    br.close();
		    return basisSets;
		}
		catch (Exception e){
			e.printStackTrace();
			if (br!=null){
				try {
					br.close();
				} catch (IOException e1) {
				}
			}
			return null;
		}
	}
	
	/**
	 * Parse titles
	 * @return Titles
	 */
	private List<String> parseTitles(){
		IBIOMESFileReader br = null;
	    ArrayList<String> titles = new ArrayList<String>();
	    String line = null;
		try{
			br =  new IBIOMESFileReader(this);
		    while (( line = br.readLine()) != null){
		    	line = line.trim();
			    if (line.toLowerCase().matches("title .+")){
			    	titles.add(line.substring(line.indexOf(" ")+1).replaceAll("\\\"", "")); 
			    }
		    }
		    br.close();
		    return titles;
		}
		catch (Exception e){
			e.printStackTrace();
			if (br!=null){
				try {
					br.close();
				} catch (IOException e1) {
				}
			}
			return null;
		}
	}
	
	/**
	 * Parse geometry assignments
	 * @return Geometry assignments
	 */
	private List<String> parseGeometryAssignments(){
		IBIOMESFileReader br = null;
	    ArrayList<String> geos = new ArrayList<String>();
	    String line = null;
		try{
			br =  new IBIOMESFileReader(this);
		    while (( line = br.readLine()) != null){
		    	line = line.trim();
			    if (line.toLowerCase().startsWith("set geometry ")){
			    	geos.add(line.substring(("set geometry ").length()).trim()); 
			    }
		    }
		    br.close();
		    return geos;
		}
		catch (Exception e){
			e.printStackTrace();
			if (br!=null){
				try {
					br.close();
				} catch (IOException e1) {
				}
			}
			return null;
		}
	}
	
	/**
	 * Retrieve geometries
	 * @return List of molecular systems (geometries)
	 * @throws Exception 
	 */
	private List<MolecularSystem> parseGeometries() throws Exception
	{
		IBIOMESFileReader br = null;
	    ArrayList<MolecularSystem> molecularSystems = new ArrayList<MolecularSystem>();
	    
		try{
			br =  new IBIOMESFileReader(this);
			String line = null;
		    while (( line = br.readLine()) != null)
		    {   
		    	line = line.trim().toLowerCase();
		    	if (line.startsWith("geometry"))
		    	{
		    		String systemName = null;
		    		//get geometry ID if available
		    		if (line.length()>("geometry").length()){
		    			String[] geometryValues = line.split("\\s+");
		    			systemName = geometryValues[1];
		    		}
		    			
		    	    ArrayList<Atom> atomList = new ArrayList<Atom>();
		    	    int a = 0;
		    		boolean isZMatrix = false;
			    	String originalLine = br.readLine().trim();
			    	line = originalLine.toLowerCase();
			        List<String[]> zMatrix = null;
		    		while (!line.equals("end") && !line.equals("variables"))
		    		{
		    			if (line.startsWith("zmatrix")){
		    				isZMatrix = true;
		    				zMatrix = new ArrayList<String[]>();
		    			}
		    			else if (!line.startsWith("symmetry"))
		    			{
				    		String[] lineSplit = originalLine.split("( )+");
							String atomName = lineSplit[0];
							String atomType = atomName;
							
							//TODO use NWChem atom type dictionary
							if (atomType.startsWith("bq"))
								atomType = atomType.substring(2);
							else if (atomType.startsWith("X") && atomType.matches("[A-Z][A-Z].*"))
								atomType = atomType.substring(1);
							
							AtomicElement element = periodicTable.getElementBySymbol(atomType);
							String symbol = atomType;
							if (element != null)
								symbol = element.getSymbol();
							Atom atom = new Atom(a, atomName, symbol);
							
							// Cartesian coordinates
							if (!isZMatrix){
								double x = Double.parseDouble(lineSplit[1]);
								double y = Double.parseDouble(lineSplit[2]);
								double z = Double.parseDouble(lineSplit[3]);
								atom.setCoordinates( x, y , z);
							}
							// Z-matrix
							else {
			        			zMatrix.add(lineSplit);
							}
							atomList.add(atom);
							a++;
		    			}
		    			originalLine = br.readLine().trim();
						line = originalLine.toLowerCase();
		    		}
		    		if (isZMatrix)
			        {
			        	int first_atoms[] = new int[atomList.size()];
			        	double distances[] = new double[atomList.size()];
			        	String distancesStr[] = new String[atomList.size()];
			        	int second_atoms[] = new int[atomList.size()];
			        	double angles[] = new double[atomList.size()];
			        	String anglesStr[] = new String[atomList.size()];
			        	int third_atoms[] = new int[atomList.size()];
			        	double dihedrals[] = new double[atomList.size()];
			        	String dihedralsStr[] = new String[atomList.size()];
			        	
			        	boolean usesVariables = false;
			        	for (a=1;a<atomList.size();a++)
			        	{
			        		String[] zLine = zMatrix.get(a);
			        		first_atoms[a] = Integer.parseInt(zLine[1])-1;
			        		//parse distances
			        		try{ 
			        			int d = Integer.parseInt(zLine[2]);
			        			distances[a] = d;
			        		} catch (NumberFormatException e) {
			        			usesVariables = true;
			        			distancesStr[a] = zLine[2];
			        		}
			        		//parse angles
			        		if (zLine.length>4){
			        			second_atoms[a] = Integer.parseInt(zLine[3])-1;
			        			try{ 
				        			int angle = Integer.parseInt(zLine[4]);
				        			angles[a] = angle;
				        		} catch (NumberFormatException e) {
				        			usesVariables = true;
				        			anglesStr[a] = zLine[4];
				        		}
			        			//parse dihedrals
				        		if (zLine.length>6){
				        			third_atoms[a] = Integer.parseInt(zLine[5])-1;
				        			try{ 
					        			int dihedral = Integer.parseInt(zLine[6]);
					        			dihedrals[a] = dihedral;
					        		} catch (NumberFormatException e) {
					        			usesVariables = true;
					        			dihedralsStr[a] = zLine[6];
					        		}
				        		}
			        		}
			        	}
			        	//parse variables for z-matrix distances, angles and dihedrals if necessary
			        	if (usesVariables){
			        		HashMap<String, Double> varHash = new HashMap<String, Double>();
				        	int nVars = 0;
					        while (( line = br.readLine()) != null)
					        {
					        	line = line.trim();
					        	if (line.length()>0 && !line.equals("end")){
					        		String[] values = line.split("\\s+");
					        		if (values.length<2){
					        			br.close();
					        			throw new IOException("Cannot find variable values for Z-matrix");
					        		}
					        		String varName = values[0];
					        		double varValue = Double.parseDouble(values[1]);
					        		varHash.put(varName, varValue);
					        		nVars++;
					        	}
					        	else if (nVars>0)
					        		break;
					        }
					        //replace variables with values
					        for (a=1;a<atomList.size();a++){
					        	if (distancesStr[a]!=null)
					        		distances[a] = varHash.get(distancesStr[a]);
					        	if (anglesStr[a]!=null)
					        		angles[a] = varHash.get(anglesStr[a]);
					        	if (dihedralsStr[a]!=null)
					        		dihedrals[a] = varHash.get(dihedralsStr[a]);
					        }
				        }
				        
			        	//get coordinates
			        	Coordinate3D[] coords = Coordinate3D.loadFromZMatrix(distances, first_atoms, angles, second_atoms, dihedrals, third_atoms);
			        	for (a=0;a<atomList.size();a++){
			        		atomList.get(a).setCoordinates(coords[a]);
			        		//System.out.println(atomList.get(a).getName() + " " + (float)coords[a].getX() + " " + (float)coords[a].getY() + " " + (float)coords[a].getZ());
			        	}
			        }
		    		if (atomList!=null && atomList.size()>0){
		    			MolecularSystemFactory moleculeFactory = new MolecularSystemFactory();
		    			MolecularSystem molecularSystem = moleculeFactory.identifyMoleculesUsingAtoms(atomList, true);
		    			molecularSystem.setName(systemName);
		    			molecularSystems.add(molecularSystem);
					}
		    	 }
		    }
		    br.close();
		    return molecularSystems;
		}
		catch (Exception e){
			if (br!=null){
				try {
					br.close();
				} catch (IOException e1) {
				}
			}
			throw e;
		}
	}
	
	/**
	 * Parse tasks
	 * @return List of methods/tasks
	 */
	private List<ExperimentTask> parseTasks(List<String> basisSets, List<String> titles){
		IBIOMESFileReader br = null;
	    ArrayList<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		ArrayList<String> relatedFiles = new ArrayList<String>();
	    String line = null;
	    //TODO parse PBC
	    String boundaryConditions = ParameterSet.BC_NON_PERIODIC;
	    	    	    
		try{
			int nTasks = 0;
			relatedFiles.add(this.getCanonicalPath());
			br =  new IBIOMESFileReader(this);
			while (( line = br.readLine()) != null)
			{
				line = line.toLowerCase().trim();
				if (line.startsWith("task "))
				{
					String qmMethodName = null;
					
					String[] lineSplit = line.split("( )+");
					
					if ( lineSplit[1].equals("md")){
						qmMethodName = null;
						MDParameterSet md = new MDParameterSet();
						MDTask task = new MDTask(md);
						if (titles!=null && titles.size()>nTasks){
							task.setDescription(titles.get(nTasks));
						}
						task.setSoftware(new Software(Software.NWCHEM));
						task.setInputFiles(relatedFiles);
						task.setBoundaryConditions(boundaryConditions);
						tasks.add(task);
					}
					else if (lineSplit[1].equals("qmmm"))
					{
						qmMethodName = lineSplit[2].toUpperCase();
						QMMMParameterSet qmmm = new QMMMParameterSet();
						MDParameterSet md = new MDParameterSet();
						QMParameterSet qm = new QMParameterSet();
						qm.setBasisSets(basisSets);
						qm.setSpecificMethodName(qmMethodName);
						QMMMTask task = new QMMMTask(md, qm, qmmm);
						if (titles!=null && titles.size()>nTasks){
							task.setDescription(titles.get(nTasks));
						}
						task.setSoftware(new Software(Software.NWCHEM));
						task.setInputFiles(relatedFiles);
						task.setBoundaryConditions(ParameterSet.BC_NON_PERIODIC);
						tasks.add(task);
						//TODO skip if level of theory is not known? (e.g. avoid ESP)
						
						nTasks++;
					}
					else if (lineSplit[1].equals("prepare"))
					{
						//TODO do nothing?
					}
					else if ((lineSplit.length>2) && (lineSplit[2].equals("property")))
					{
						//TODO do nothing?
					}
					else
					{
						QMParameterSet qm = new QMParameterSet();
						qmMethodName = lineSplit[1].toUpperCase();
						//if TCE engine
						if (qmMethodName.equals("tce"))
						{
							IBIOMESFileReader brTCE = new IBIOMESFileReader(this);
							boolean found = false;
							String lineTce = null;
							while (( lineTce = brTCE.readLine()) != null && !found)
							{
								lineTce = line.toLowerCase().trim();
								if (lineTce.equals("tce")){
									lineTce = brTCE.readLine().toLowerCase().trim();
									qmMethodName = lineTce.toUpperCase();
									found = true;
								}
							}
							brTCE.close();
							if (!found){
								qmMethodName = "CCSD"; //default method for TCE
							}
						}
						qm.setSpecificMethodName(qmMethodName);
						//TODO add level of theory if known
						
						// check calculation type
					    ArrayList<String> calculations = new ArrayList<String>();
						if (line.toLowerCase().matches("task .+ optimize( .+)?"))
							calculations.add(QMParameterSet.QM_GEOMETRY_OPTIMIZATION);
						else if (line.toLowerCase().matches("task .+ freq(uencies)?( .+)?"))
					    	calculations.add(QMParameterSet.CALCULATION_FREQUENCY);
						else if (line.toLowerCase().matches("task .+ hessian( .+)?"))
					    	calculations.add(QMParameterSet.CALCULATION_HESSIAN);
						else if (line.toLowerCase().matches("task .+ saddle( .+)?"))
					    	calculations.add(QMParameterSet.CALCULATION_SADDLE);
						else if (line.toLowerCase().matches("task .+ gradient( .+)?"))
					    	calculations.add(QMParameterSet.CALCULATION_ENERGY_GRADIENT);
						else if (line.toLowerCase().matches("task .+ energy( .+)?"))
					    	calculations.add(QMParameterSet.CALCULATION_ENERGY_SP);
						else
					    	calculations.add(QMParameterSet.CALCULATION_ENERGY_SP);
						
						qm.setBasisSets(basisSets);
						QMTask task = new QMTask(qm);
						if (titles!=null && titles.size()>nTasks){
							task.setDescription(titles.get(nTasks));
						}
						task.setCalculationTypes(calculations);
						task.setBoundaryConditions(boundaryConditions);
						task.setSoftware(new Software(Software.NWCHEM));
						task.setInputFiles(relatedFiles);
						tasks.add(task);
						
						nTasks++;
					}
				}
			}
			br.close();
			return tasks;
		}
		catch (Exception e){
			e.printStackTrace();
			if (br!=null){
				try {
					br.close();
				} catch (IOException e1) {
				}
			}
			return null;
		}
	}
}
