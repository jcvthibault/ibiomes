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

package edu.utah.bmi.ibiomes.parse.chem.gaussian;

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
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.comp.qm.LevelOfTheory;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMTask;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.MolecularSystemFactory;

/**
 * GAUSSIAN input file (.com)
 * @author Julien Thibault
 *
 */
public class GaussianInputFile extends AbstractProcessGroupSetFile
{
	private static final long serialVersionUID = -8329761308485275213L;
	
	private static final String MM_METHODS = "(uff)|(amber)|(dreiding)";
	private static final String QM_METHODS = "((u)|(r)|(ro))?((hf)|(mp2)|(mp4)|(ccsd)|(bd)|(casscf)|(cis)|(sac\\-ci)|(eomccsd)|(td))";
	private static final String DFT_METHODS = "((u)|(r)|(ro))?((lsda)|(bpv86)|(b3lyp)|(cam\\-b3lyp)|(b3pw91)|(mpw1pw91)||(pbepbe)|(hseh1pbe)|(hcth)|(tpsstpss)" +
			//TODO combinations & hybrids
			"|(m06hf)|(m062x)|(thcthhyb)|(hseh1pbe)|(hse2pbe)|(hsehpbe)|(pbeh1pbe)|(wb97xd)|(wb97)|(wb97x)|(tpssh)|(z3lyp)|(lc-wpbe))";
	private static final String SEMI_EMPIRICAL_METHODS = "(pm3)|(pm3mm)|(pm6)|(indo)|(cndo)|(am1)|(pddg)|(((u)?zindo))";
	private static final String BASIS_SETS = "([346]\\-[23][1]+.*)|(lanl.+)|(sto\\-[346]g)|((aug)?cc\\-p[dtq56]z.*)|(d95.*)|(cep\\-.*)|(sdd.*)";
	
	private static final String MODREDUNDANT_OPTION_LINE = "([XBADLO] (\\d+(\\.\\d+)? )+[AFBKRDHS]( \\d+(\\.\\d+)?)*\\s*)";
	private static final String CHARGE_SPIN_LINE_REGEX = "\\-?\\d+ \\d+";
	
	private String _levelOfTheory= null;
	private String exchangeCorrelation= null;
	private String methodName= null;
	private String _basisSet = null;
	private String _forceField = null;
	private String methodType = null;
	private QMParameterSet qmMethod = null;
	private MDParameterSet mdMethod = null;
	private ArrayList<String> calculations = new ArrayList<String>();
	boolean isModRedundant = false;
	
	/**
	 * Default constructor.
	 * @param pathname Topology file path.
	 * @throws IOException 
	 * @throws Exception
	 */
	public GaussianInputFile(String pathname) throws IOException
	{
		super(pathname, LocalFile.FORMAT_GAUSSIAN_COM);
		try{
			parseFile();
		} 
		catch(Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			try {
				if (IBIOMESConfiguration.getInstance().isOutputToConsole())
					System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a Gaussian input file.");
				if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
					e.printStackTrace();
			} catch (Exception e1) {
			}
			
		}
	}
	
	/**
	 * Load GAUSSIAN input file
	 * @param filePath
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		String charge = null;
		String multiplicity = null;
		String boundaryConditions = ParameterSet.BC_NON_PERIODIC;
		ArrayList<String> relatedFiles = new ArrayList<String>();
		relatedFiles.add(this.getCanonicalPath());
		this.software = Software.GAUSSIAN;
		boolean isRestart = false;
        ArrayList<Atom> atomList = null;

		ArrayList<MolecularSystem> systems = new ArrayList<MolecularSystem>();
		ArrayList<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		
		IBIOMESFileReader br = null;
		try{
			br = new IBIOMESFileReader(this);
		    String line = null;
		    String title = null;
		    String[] lineSplit = null;
		    
		    //find METHOD (basis set and level of theory)
	        while (( line = br.readLine()) != null){
	        	if (line.startsWith("#")) {
	        		//check if its a restart file
	        		if (line.matches(".*geom=check.*")){
	        			this.setFormat(FORMAT_GAUSSIAN_RESTART);
	        			isRestart = true;
	        		}
	        		parseMethod(line.trim());
	        		break;
	        	}
	        }
	        
		    //find TITLE
	        while (( line = br.readLine()) != null){
	        	if (line.trim().length()>0) {
	        		title = line.trim();
	        		break;
	        	}
	        }
	        
	        //find CHARGE and MULTIPLICITY
	        while (( line = br.readLine()) != null){
	        	if (line.trim().matches(CHARGE_SPIN_LINE_REGEX)) {
	        		String[] vals  = line.trim().split(" ");
	        		charge = vals[0];
	        		multiplicity = vals[1];
	        		break;
	        	}
	        }
	        
	        if (!isRestart)
	        {
		        //parse atom chain
		        int a = 0;
		        double x, y , z;
		        boolean isZMatrix = false;
		        List<String[]> zMatrix = new ArrayList<String[]>();
		        atomList = new ArrayList<Atom>();
		        while (( line = br.readLine()) != null)
		        {
		        	if (line.trim().length()>0){
		        		a++;
		        		lineSplit = line.trim().split("[ ]+");
		        		String atomType = lineSplit[0];
		        		//check that theres no other info behind the atom type
		        		//example: C(PDBName=C1,ResName=,ResNum=1)
		        		if (atomType.indexOf('(')>0){
		        			atomType = atomType.substring(0, atomType.indexOf('('));
		        		}
		        		//check if periodic boundary conditions flag
		        		if (atomType.equals("TV") || atomType.equals("tv")){
		        			boundaryConditions = ParameterSet.BC_PERIODIC;
		        		}
		        		else {
			        		Atom atom = new Atom(a, atomType, atomType);
			        		
			        		//if 3D Cartesian coordinates
			        		if (lineSplit.length==4 && !isZMatrix){
				        		x = Double.parseDouble(lineSplit[1]);
				        		y = Double.parseDouble(lineSplit[2]);
				        		z = Double.parseDouble(lineSplit[3]);
				        		atom.setCoordinates( x, y , z);
			        		}
			        		else // Z-matrix
			        		{
			        			isZMatrix = true;
			        			zMatrix.add(lineSplit);
			        		}
			        		atomList.add(atom);
		        		}
		        	}
		        	else if (a>0)
		        		break;
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
		        			double d = Double.parseDouble(zLine[2]);
		        			distances[a] = d;
		        		} catch (NumberFormatException e) {
		        			usesVariables = true;
		        			distancesStr[a] = zLine[2];
		        		}
		        		//parse angles
		        		if (zLine.length>4){
		        			second_atoms[a] = Integer.parseInt(zLine[3])-1;
		        			try{ 
			        			double angle = Double.parseDouble(zLine[4]);
			        			angles[a] = angle;
			        		} catch (NumberFormatException e) {
			        			usesVariables = true;
			        			anglesStr[a] = zLine[4];
			        		}
		        			//parse dihedrals
			        		if (zLine.length>6){
			        			third_atoms[a] = Integer.parseInt(zLine[5])-1;
			        			try{ 
			        				double dihedral = Double.parseDouble(zLine[6]);
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
				        	if (line.length()>0){
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
	        }
	       
	        //parse modredundant option lines
	        int mod = 0;
	        while (( line = br.readLine()) != null)
	        {
	        	line = line.trim();
	        	if (line.length()>0 && line.matches(MODREDUNDANT_OPTION_LINE))
	        	{
	        		mod++;
	        		if (line.substring(2).indexOf('S')>0)
	        			calculations.add(QMParameterSet.CALCULATION_SCAN);
	        		/*else if (line.substring(2).indexOf('F')>0)
	        			calculations.add(QMMethod.CALCULATION_);*/
	        		else
	    	        	calculations.add(QMParameterSet.CALCULATION_ENERGY_SP);
	        	}
	        	else if (mod>0)
	        		break;
	        }
	        //if (mod>0){
		    //    method.setCalculationTypes(calculations);
	        //}

	        br.close();
	        
	        if (!isRestart)
	        {
		        //create molecule object
		        MolecularSystemFactory moleculeFactory = new MolecularSystemFactory();
		        MolecularSystem molecularSystem = moleculeFactory.identifyMoleculesUsingAtoms(atomList, true);
				molecularSystem.setDefinitionFiles(this.getCanonicalPath());
				systems.add(molecularSystem);
	        }
	        
			//set method (QM or MD)
			if (methodType.equals(ParameterSet.METHOD_QM))
			{
				qmMethod = new QMParameterSet();
				qmMethod.setTotalCharge(Integer.parseInt(charge));
				qmMethod.setSpinMultiplicity(Integer.parseInt(multiplicity));
				qmMethod.setSpecificMethodName(methodName);
				qmMethod.setBasisSet(_basisSet);
				if (exchangeCorrelation!=null)
					qmMethod.setExchangeCorrelationFn(exchangeCorrelation);
				if (_levelOfTheory!=null){
					qmMethod.setLevelOfTheory(_levelOfTheory);
				}
				
				QMTask task = new QMTask(qmMethod);
				task.setCalculationTypes(calculations);
				task.setBoundaryConditions(boundaryConditions);
				task.setSoftware(new Software(software));
				task.setInputFiles(relatedFiles);
				tasks.add(task);
			}
			else if (methodType.equals(ParameterSet.METHOD_MD))
			{
				mdMethod = new MDParameterSet();
				if (_forceField!=null && _forceField.length()>0){
					List<String> forceFields = new ArrayList<String>();
					forceFields.add(_forceField);
					mdMethod.setForceFields(forceFields);
				}
				
				MDTask task = new MDTask(mdMethod);
				task.setSoftware(new Software(software));
				task.setBoundaryConditions(boundaryConditions);
				task.setCalculationTypes(calculations);
				task.setInputFiles(relatedFiles);
				tasks.add(task);
			}
			else {
				//TODO QM/MM????
			}
			
			//build process groups
			MolecularSystem molecularSystem = null;
    		if (systems.size()>0)
    			molecularSystem = systems.get(0);
    		ExperimentProcess process = new ExperimentProcess(null, null, tasks);
    		ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, null, molecularSystem, process);
    		this.processGroups = new ArrayList<ExperimentProcessGroup>();
    		this.processGroups.add(processGroup);
		}
		catch (Exception e){
			if (br!=null){
				try {
					br.close();
				} catch (IOException e2) {
				}
			}
			throw e;
		}
	}
	
	private void parseMethod(String line) throws IOException
	{
		//find basis set and level of theory
		this.findMethod(line);
				
		//if its QM
		if (methodType.equals(ParameterSet.METHOD_QM))
		{
			//find type of calculation
			this.findQmCalculationType(line);
		}
	}
	
	private void findMethod(String line) throws IOException
	{
		String[] lineParsed = line.trim().toLowerCase().split("( )|(\\=)|(\\/)");
		
		int i=0;
		boolean found = false;
		while (i<lineParsed.length && !found)
		{
			if (i==0 || !lineParsed[i-1].matches("guess"))
			{
				if (lineParsed[i].matches(QM_METHODS))
				{
					methodType = ParameterSet.METHOD_QM;
					methodName = lineParsed[i];
					_basisSet = findBasisSet(line);
					//TODO find level of theory...
					_levelOfTheory = null;
					found = true;
				}
				else if (lineParsed[i].matches(DFT_METHODS))
				{
					methodType = ParameterSet.METHOD_QM;
					_levelOfTheory = LevelOfTheory.QM_METHOD_DFT;
					methodName = lineParsed[i];
					exchangeCorrelation = lineParsed[i];
					_basisSet = findBasisSet(line);
					found = true;
				}
				else if (lineParsed[i].matches(SEMI_EMPIRICAL_METHODS))
				{
					methodType = ParameterSet.METHOD_QM;
					_levelOfTheory = ParameterSet.METHOD_SEMI_EMPIRICAL;
					methodName = lineParsed[i];
					found = true;
				}		
				
				else if (lineParsed[i].matches(MM_METHODS))
				{
					methodType = ParameterSet.METHOD_MD;
					_forceField = lineParsed[i];
					found = true;
				}
			}
			i++;
		}
		if (!found)
		{
			//default to QM
			System.out.println("Unknown method in line '"+line+"' in Gaussian input file ("+this.getAbsolutePath()+").");
			methodType = ParameterSet.METHOD_QM;
			methodName = null;
			_levelOfTheory = null;
		}
		
		//if multiple basis sets used, look at the end of the file
		if (_basisSet != null && _basisSet.equals("gen")){
			_basisSet = "Custom";
		}

	}
	
	private String findBasisSet(String line)
	{
		int end;
		int start = line.indexOf('/');
		//if the line is in the format "[...] level_of_theory/basis_set [...]"
		if (start > -1)
		{
			start++;
			end = line.indexOf(" ", start);
			if (end == -1)
				end = line.length();
			return line.substring(start, end);
		}
		else // basis_set [...] level_of_theory [...]
		{
			String[] tokens = line.split("\\s+");
			boolean found = false;
			int t=0;
			while (!found && t<tokens.length){
				if (tokens[t].matches(BASIS_SETS)){
					return tokens[t];
				}
				t++;
			}
		}
		return null;
	}
	
	/**
	 * parse type of QM calculations
	 * @param line
	 */
	private void findQmCalculationType (String line)
	{		
		String[] lineParsed = line.trim().toLowerCase().split(" ");
		int i=0;
		boolean cont = true;
		
		while ((i<lineParsed.length) && cont)
		{
			if (lineParsed[i].matches("scan")){
				calculations.add(QMParameterSet.CALCULATION_SCAN);
				cont = false;
			}
			else if (lineParsed[i].matches(".*modredundant.*")){
				isModRedundant = true;
				cont = false;
			}
			else if (lineParsed[i].startsWith("stable")){
				calculations.add(QMParameterSet.CALCULATION_STABILITY);
				cont = false;
			}
			else if (lineParsed[i].matches("nmr")){
				calculations.add(QMParameterSet.CALCULATION_NMR);
				cont = false;
			}
			else if (lineParsed[i].matches("freq(\\=.+)?")){
				calculations.add(QMParameterSet.CALCULATION_FREQUENCY);
				if (lineParsed[i].matches("freq\\=[n]+raman")){
					calculations.add(QMParameterSet.CALCULATION_RAMAN);
				}
				cont = false;
			}
			else if (lineParsed[i].matches("irc(\\=.+)?")){
				calculations.add(QMParameterSet.CALCULATION_IRC);
				cont = false;
			}
			else if (lineParsed[i].matches("opt(\\=.+)?")){
				calculations.add(QMParameterSet.QM_GEOMETRY_OPTIMIZATION);
			}
			i++;
		}
		//default is SPE
		if (!isModRedundant && calculations.size()==0){
			calculations.add(QMParameterSet.CALCULATION_ENERGY_SP);
		}
	}
}
