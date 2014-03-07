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

import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractProcessGroupSetFile;
import edu.utah.bmi.ibiomes.quantity.TimeLength;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.MolecularSystemFactory;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcess;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.TaskExecution;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMTask;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.CalculationMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;

/**
 * Gaussian output file (log).
 * @author Julien Thibault
 *
 */
public class GaussianOutputFile extends AbstractProcessGroupSetFile
{
	private static final long serialVersionUID = 4670631232886183863L;
	public static final String FILE_FORMAT = LocalFile.FORMAT_GAUSSIAN_LOG;
	
	private static final String terminationMarker = "Normal termination";
	private static final String stoichiometryMarker = "Stoichiometry";
	private static final String basisSetMarker = "Standard basis: ";
	private static final String chargeAndMultiplicityMarker = "[Cc]harge(\\s)*\\=(\\s)*(\\-)?\\d(\\s)*[Mm]ultiplicity(\\s)*\\=(\\s)*\\d";
	private static final String topologyMarker = "Symbolic(\\s)+Z\\-[Mm]atrix\\:";
	private static final String electronCountMarker = "\\s*\\d+ alpha electrons\\s*\\d+ beta electrons";
	private static final String energyStateMarker = "SCF Done: ";
	private static final String executionTimeMarker = "Job cpu time: ";

	private String timestamp = null;
	private Software software = null; 
	private String stoichiometry = null;
	private String boundaryConditions = ParameterSet.BC_NON_PERIODIC;
	private String termination = TaskExecution.TERMINATION_STATUS_ERROR;
	private int electronCountAlpha = 0;
	private int electronCountBeta = 0;
	private int electronCount = 0;
	private double[] energy = null;
	private int[] energyCycles = null;
	private double energyFinal = 0.0;
	private double executionTime;
	
	/**
	 * Default constructor for Gaussian log files.
	 * @param localPath Path to the file
	 * @throws Exception 
	 */
	public GaussianOutputFile(String localPath) throws Exception
	{
		super(localPath, FILE_FORMAT);
		try{
			parseFile();
			
		} catch(Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			try {
				if (IBIOMESConfiguration.getInstance().isOutputToConsole())
					System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a Gaussian output file.");
				if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
					e.printStackTrace();
			} catch (Exception e1) {
			}
		}
	}
	
	public String getTimestamp() {
		return this.timestamp;
	}

	public int getAlphaSpinElectronCount() {
		return this.electronCountAlpha;
	}

	public int getBetaSpinElectronCount() {
		return this.electronCountBeta;
	}

	public int getTotalElectronCount() {
		return this.electronCount;
	}

	public double getFinalEnergy() {
		return this.energyFinal;
	}

	public double[] getEnergy() {
		return this.energy;
	}

	public String getStoichiometry() {
		return this.stoichiometry;
	}

	/**
	 * Get termination status
	 * @return Termination status
	 */
	public String getTermination() {
		return this.termination;
	}
	
	/**
	 * Get file metadata and GAUSSIAN-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception{
		
		//get parameter/topology file metadata
		MetadataAVUList metadata = super.getMetadata();
		
		//get GAUSSIAN-specific metadata
		metadata.add(new MetadataAVU(PlatformMetadata.PROGRAM_TERMINATION, this.termination));
		metadata.addAll(this.software.getMetadata());
		//metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_ATOMIC_COMPOSITION, this.stoichiometry));
		metadata.add(new MetadataAVU(MethodMetadata.COUNT_ELECTRON_APLHA, String.valueOf(this.electronCountAlpha)));
		metadata.add(new MetadataAVU(MethodMetadata.COUNT_ELECTRON_BETA, String.valueOf(this.electronCountBeta)));
		metadata.add(new MetadataAVU(MethodMetadata.COUNT_ELECTRON, String.valueOf(this.electronCount)));
		if (energyFinal!=0.0)
			metadata.add(new MetadataAVU(CalculationMetadata.ENERGY_TOTAL_MINIMIZATION, String.valueOf(this.energyFinal)));

		return metadata;
	}
	
	private void parseFile() throws Exception
	{
		String basisSet = null;
		String multiplicity = null;
		String charge = null;
		String method = ParameterSet.METHOD_QM;
		String levelOfTheory = null;
		this.software = new Software(Software.GAUSSIAN);
		ArrayList<MolecularSystem> systems = new ArrayList<MolecularSystem>();
		ArrayList<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		
		/*
		 
		 Two-electron integral symmetry is turned off.
		   493 basis functions,   928 primitive gaussians,   493 cartesian basis functions
		   104 alpha electrons      104 beta electrons
		       nuclear repulsion energy      2566.4625907207 Hartrees.
		 
		 Job cpu time:  0 days  0 hours  0 minutes 44.5 seconds.
 
		 */
		IBIOMESFileReader br = null;
	    String line = null;
	    boolean found = false;
	    
	    try{
			ArrayList<String> relatedFiles = new ArrayList<String>();
			relatedFiles.add(this.getCanonicalPath());
			
	    	br =  new IBIOMESFileReader(this);
	    	 
		    //get Gaussian version and timestamp
		    //
		    // ******************************************
		    // Gaussian 03:  AM64L-G03RevE.01 11-Sep-2007
		    //                21-Feb-2009 
		    // ******************************************
			while (( line = br.readLine()) != null && !found)
			{
				line = line.trim();
				if (line.startsWith("***********************"))
				{
					line = br.readLine().trim();
					String[] values = line.split("\\s+");
					software.setVersion(values[1].substring(0,values[1].length()-1));
					software.setExecutableName(values[2]);
					timestamp = br.readLine().trim();	
							
					found = true;
				}
			}
			
			if (found == false){
				br.close();
				br = new IBIOMESFileReader(this);
			}
			else found = false;
			
			
			// Total charge and spin multiplicity
			//
			// e.g. Charge =  0 Multiplicity = 2
			
			while (( line = br.readLine()) != null && !found)
			{
				line = line.trim();
				if (line.matches(chargeAndMultiplicityMarker))
				{
					String[] values = line.split("\\s+");
					charge = values[2];
					multiplicity = values[5];
					found = true;
				}
			}

			br.close();
			br = new IBIOMESFileReader(this);
			found = false;

			// topology (atom types and coordinates)
			while (( line = br.readLine()) != null && !found)
			{
				line = line.trim();
				if (line.matches(topologyMarker))
				{
					found = true;
					int id = 0;
			        ArrayList<Atom> atomList = new ArrayList<Atom>();
					while (( line = br.readLine()).trim().length()>0)
					{
						line = line.trim();
						if (!line.matches(chargeAndMultiplicityMarker)){
							String[] values = line.trim().split("\\s+");
							if (values.length<4)
								break;
							Atom atom = new Atom(id, values[0]+id, values[0]);
							try{
								Coordinate3D coord = new Coordinate3D(
										Double.parseDouble(values[1]),
										Double.parseDouble(values[2]),
										Double.parseDouble(values[3]));
								atom.setCoordinates(coord);
				        		atomList.add(atom);
							} catch (NumberFormatException e){
								break;
							}
						}
					}
			        MolecularSystemFactory moleculeFactory = new MolecularSystemFactory();
			        MolecularSystem molecularSystem = moleculeFactory.identifyMoleculesUsingAtoms(atomList, true);
					molecularSystem.setDefinitionFiles(this.getCanonicalPath());
					systems.add(molecularSystem);
				}
			}
			
			if (found == false){
				br.close();
				br = new IBIOMESFileReader(this);
			}
			else found = false;
			
			// get chemical composition
		    //
			//  e.g.  Stoichiometry  C26H29NO2  C18H15P C25H18FeN5S(2)
			//    
			//
			while (( line = br.readLine()) != null && !found)
			{
				line = line.trim();
				if (line.startsWith(stoichiometryMarker)){
					String[] values = line.split("\\s+");
					String stoichiometryRaw = values[1];
					StringBuilder strBuilder = new StringBuilder();
					strBuilder.append(stoichiometryRaw.substring(0,1));
					String currentChar = null;
					for (int c=1; c<stoichiometryRaw.length();c++){
					
						currentChar = stoichiometryRaw.substring(c,c+1);
						if (currentChar.equals("("))
							break;
						
						String prevChar = stoichiometryRaw.substring(c-1,c);
						
						if (!prevChar.matches("\\d") && currentChar.matches("\\d")){
							strBuilder.append(":");
						}
						else if (prevChar.matches("\\d") && !currentChar.matches("\\d")){
							strBuilder.append(" ");
						}
						else if (prevChar.matches("[A-Za-z]") && currentChar.matches("[A-Z]")){
							strBuilder.append(":1 ");
						}
						else if (prevChar.matches("[a-z]")){
							strBuilder.append(":");
						}
						strBuilder.append(currentChar);
					}
					
					this.stoichiometry = strBuilder.toString();
					if (this.stoichiometry.substring(this.stoichiometry.length()-1).matches("[A-Za-z]")){
						this.stoichiometry += ":1";
					}
					found = true;
				}
			}
			
			if (found == false){
				br.close();
				br =  new IBIOMESFileReader(this);
			}
			else found = false;
			
			// Electron count
			//
			// e.g. 64 alpha electrons       64 beta electrons
			
			while (( line = br.readLine()) != null && !found)
			{
				line = line.trim();
				if (line.matches(electronCountMarker))
				{
					String[] values = line.split("\\s+");
					electronCountAlpha = Integer.parseInt(values[0]);
					electronCountBeta = Integer.parseInt(values[3]);
					electronCount = electronCountAlpha + electronCountBeta;
					found = true;
				}
			}
			
			if (found == false){
				br.close();
				br =  new IBIOMESFileReader(this);
			}
			else found = false;
						
			// Basis set
			//
			// e.g. Standard basis: 6-31G(d) (6D, 7F)
			while (( line = br.readLine()) != null && !found)
			{
				line = line.trim();
				if (line.startsWith(basisSetMarker))
				{
					basisSet = line.substring(basisSetMarker.length());
					found = true;
				}
			}
			
			if (found == false){
				br.close();
				br =  new IBIOMESFileReader(this);
			}
			else found = false;
			
			// Normal termination?
			//
			// e.g. Normal termination of Gaussian 03 at Sun Feb 22 00:18:44 2009.
			while (( line = br.readLine()) != null && !found)
			{
				line = line.trim();
				if (line.startsWith(terminationMarker))
				{
					this.termination = TaskExecution.TERMINATION_STATUS_NORMAL;
				}
			}
			br.close();
			
			//execution time
			//
			//e.g. "Job cpu time:  0 days  0 hours 10 minutes 24.8 seconds."
			br =  new IBIOMESFileReader(this);
			while (( line = br.readLine()) != null)
			{
				line = line.trim();
				if (line.startsWith(executionTimeMarker))
				{
					line = line.substring(executionTimeMarker.length()).trim();
					String[] timeValues = line.split("\\s+");
					this.executionTime += 
							Double.parseDouble(timeValues[0]) * 365.0 * 24.0
							+ Double.parseDouble(timeValues[2]) * 24.0
							+ Double.parseDouble(timeValues[4])
							+ Double.parseDouble(timeValues[6]) / 60.0;
					this.executionTime = Math.round(this.executionTime);
				}
			}
			br.close();
			
			// Evolution of energy
			//
			// e.g. SCF Done:  E(RM062X) =  -344.910796138     A.U. after   17 cycles
			br =  new IBIOMESFileReader(this);
			ArrayList<String> energyStrList = new ArrayList<String>();
			ArrayList<String> energyNCyclesStrList = new ArrayList<String>();
			while (( line = br.readLine()) != null)
			{
				line = line.trim();
				if (line.startsWith(energyStateMarker))
				{
					String[] values = line.split("\\s+");
					energyStrList.add(values[4]);
					energyNCyclesStrList.add(values[7]);
				}
			}
			int e=0;
			energy = new double[energyStrList.size()];
			for (String energyStr : energyStrList){
				energy[e] = Double.parseDouble(energyStr);
				e++;
			}
			e=0;
			energyCycles = new int[energyNCyclesStrList.size()];
			for (String energyStr : energyNCyclesStrList){
				energyCycles[e] = Integer.parseInt(energyStr);
				e++;
			}
			if (energy.length>0)
				energyFinal = energy[energy.length-1];
	
			//add corresponding method
			
			if (method.equals(ParameterSet.METHOD_QM))
			{
				QMParameterSet qm = new QMParameterSet();
				if (levelOfTheory!=null)
					qm.setSpecificMethodName(levelOfTheory);
				if (basisSet!=null)
					qm.setBasisSet(basisSet);
				qm.setTotalCharge(Integer.parseInt(charge));
				qm.setSpinMultiplicity(Integer.parseInt(multiplicity));
				
				QMTask task = new QMTask(qm);
				task.setSoftware(this.software);
				task.setBoundaryConditions(boundaryConditions);
				task.setOutputFiles(relatedFiles);
				
				TaskExecution execInfo = new TaskExecution(this.termination);
				if (executionTime>0.0)
					execInfo.setExecutionTime(new TimeLength(executionTime, TimeLength.Minute));
				//TODO parse number of CPUs
				execInfo.setNumberOfCPUs(0);
				task.setTaskExecution(execInfo);
				
				tasks.add(task);
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
			if (br!=null)
				try {
					br.close();
				} catch (IOException e1) {
				}
			throw e;
		}
	}
	
	
}
