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
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractProcessGroupSetFile;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.MolecularSystemFactory;

/**
 * NWChem output file (.nw)
 * @author Julien Thibault
 *
 */
public class NWChemOutputFile extends AbstractProcessGroupSetFile
{
	private static final long serialVersionUID = -3739880311794657734L;
	private static final String timestampMarker = "\\s*date(\\s*)\\=(\\s*).*";
	private static final String scfCalculationTypeMarker = "\\s*SCF calculation type:.*";
	private static final String titleMarker = "Northwest Computational Chemistry Package (NWChem)";
	private static final String topologyMarker = "No\\.\\s+Tag\\s+Charge\\s+X\\s+Y\\s+Z";
	private static final String mdOutputMarker = "MOLECULAR DYNAMICS";
	private static final String forceFieldMarker = "Force field\\s+[a-z]+";
	private static final String basisSetsMarker = "Tag\\s+Description\\s+Shells\\s+Functions and Types";
	
	private PeriodicTable periodicTable = null;
			
	private String method = ParameterSet.METHOD_QM;
	private Software software = null; 
	private int charge = 0;
	private int multiplicity = 0;
	private String levelOfTheory = null;
	private String exchangeFunct = null;
	private String correlationFunct = null;
	private String timestamp = null;
	private String wavefunctionType = null;
	private int electronCountAlpha = 0;
	private int electronCountBeta = 0;
	private int electronCount = 0;
	private int nAtoms = 0;
	private String forceField = null;

	/**
	 * Default constructor.
	 * @param pathname Input file path.
	 * @throws Exception
	 */
	public NWChemOutputFile(String pathname) throws Exception {
		super(pathname, FORMAT_NWCHEM_OUTPUT);
		periodicTable = PeriodicTable.getInstance();
		try{
			parseFile();
		}
		catch(Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as an NWChem output file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
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

	/**
	 * Get topology metadata and NWChem-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception{
		
		//get parameter/topology file metadata
		MetadataAVUList metadata = super.getMetadata();

		//get NWChem-specific metadata
		metadata.addAll(this.software.getMetadata());
		
		if (method.equals(ParameterSet.METHOD_MD))
		{
			//TODO metadata for MD
		}
		else //QM
		{
			if (electronCount>0){
				metadata.add(new MetadataAVU(MethodMetadata.COUNT_ELECTRON_APLHA, String.valueOf(this.electronCountAlpha)));
				metadata.add(new MetadataAVU(MethodMetadata.COUNT_ELECTRON_BETA, String.valueOf(this.electronCountBeta)));
				metadata.add(new MetadataAVU(MethodMetadata.COUNT_ELECTRON, String.valueOf(this.electronCount)));
			}
		}
		
		return metadata;
	}
	
	/**
	 * Load NWChem output file
	 * @param filePath
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		boolean isMDOutput = false;
		this.software = new Software(Software.NWCHEM);
		
		//QM/MM
		/*
        QM/MM Interface Parameters
        --------------------------
		operation             task:operation        optimize                                                      
		reference energy      qmmm:uqmatm             -84.000000                                                  
		bqzone radius         qmmm:bqzone               1.000000                                                  
		excluded bq's         qmmm:bq_exclude       none                                                          
		link atom type        qmmm:linkatm          halogens                                                      
		optimization method   qmmm:optimization     bfgs                                                          
		qmmm:region 0         qm                                                            
		ecp on link atoms     qmmm:link_ecp         auto                                                          
		interface api         qmmm:interface        qm              
		*/

		ArrayList<String> basisSets =  new ArrayList<String>();
		
		IBIOMESFileReader br = null;
	    String line = null;
	    boolean found = false;
	    
	    try{
			List<MolecularSystem> systems = new ArrayList<MolecularSystem>();
			ArrayList<String> relatedFiles = new ArrayList<String>();
			relatedFiles.add(this.getCanonicalPath());
	    	br = new IBIOMESFileReader(this);
	    	
	    	//skip empty lines
	    	boolean isEmpty = true;
	    	while (isEmpty){
	    		line = br.readLine().trim();
	    		if (line.length()>0)
	    			isEmpty = false;
	    	}
	    	
	    	//check if its a an MD ouput
			if (line.startsWith(mdOutputMarker)){
				isMDOutput = true;
				method = ParameterSet.METHOD_MD;
				found = true;
			}
			
			//MD info
			if (isMDOutput)
			{
				//force field
				found = false;
				while (( line = br.readLine()) != null && !found)
				{
					line = line.trim();
					if (line.matches(forceFieldMarker)){
						forceField = line.substring("Force field ".length()).trim().toUpperCase();
						found = true;
					}
				}
			}
			//QM info
			else {
		    	found = false;
		    	
		    	//title / software version
				while (( line = br.readLine()) != null && !found)
				{
					line = line.trim();
					if (line.startsWith(titleMarker)){
						software.setVersion(line.substring(titleMarker.length()).trim());
						found = true;
					}
				}
		    	
		    	if (found == false){
					br.close();
					throw new IOException("NWChem output file header (software package version) not found!");
				}
		    		
		    	//timestamp
				while (( line = br.readLine()) != null && !found)
				{
					line = line.trim();
					if (line.matches(timestampMarker)){
						String[] splitLine = line.split("\\=");
						timestamp = splitLine[1].trim();
						found = true;
					}
				}
				
				if (found == false){
					br.close();
					br = new IBIOMESFileReader(this);
				}
				else found = false;
				
				//topology info
				
				while (( line = br.readLine()) != null && !found)
				{
					line = line.trim();
					if (line.matches(topologyMarker)){
						line = br.readLine(); //skip one line
						ArrayList<Atom> atomList = new ArrayList<Atom>();
						int a = 0;
						while (( line = br.readLine()) != null && line.trim().length()>0){
							String[] lineSplit = line.trim().split("\\s+");
							String atomName = lineSplit[1];
							String atomType = atomName;
							if (atomType.startsWith("bq"))
								atomType = atomType.substring(2);
							else if (atomType.startsWith("X") && atomType.matches("[A-Z][A-Z].*"))
								atomType = atomType.substring(1);
							
							AtomicElement element = periodicTable.getElementBySymbol(atomType);
							String symbol = atomType;
							if (element != null)
								symbol = element.getSymbol();
							Atom atom = new Atom(a, atomName, symbol);
							atom.setCharge(Float.parseFloat(lineSplit[2]));
							double x = Double.parseDouble(lineSplit[3]);
							double y = Double.parseDouble(lineSplit[4]);
							double z = Double.parseDouble(lineSplit[5]);
							atom.setCoordinates( x, y , z);
							atomList.add(atom);
							a++;
						}
						if (atomList!=null && atomList.size()>0){
							MolecularSystemFactory moleculeFactory = new MolecularSystemFactory();
			    			MolecularSystem molecularSystem = moleculeFactory.identifyMoleculesUsingAtoms(atomList, true);
			    			molecularSystem.setDefinitionFiles(this.getCanonicalPath());
			    			systems.add(molecularSystem);
					        
						}
						found = true;
					}
				}
				
				if (found == false){
					br.close();
					br = new IBIOMESFileReader(this);
				}
				else found = false;
				
				
				//basis set info
				
				while (( line = br.readLine()) != null && !found)
				{
					line = line.trim();
					if (line.matches(basisSetsMarker)){
						line = br.readLine(); //skip one line
						while (( line = br.readLine()) != null && line.trim().length()>0){
							String[] lineSplit = line.trim().split("\\s+");
							String basisSet = lineSplit[1];
							if (!basisSets.contains(basisSet)){
			    				basisSets.add(basisSet);
		    				}
						}
						found = true;
					}
				}
				
				if (found == false){
					br.close();
					br = new IBIOMESFileReader(this);
				}
				else found = false;
								
				// General info (method, charge, spin multiplicity, number of electrons)	
				
				while (( line = br.readLine()) != null && !found)
				{
					line = line.trim();
					if (line.matches(scfCalculationTypeMarker))
					{
						//method
						String[] values = line.split("\\:");
						levelOfTheory = values[1].trim();
						//wavefunction type
						line = br.readLine().trim();
						values = line.split("\\:");
						wavefunctionType = values[1].trim();
						//number of atoms
						line = br.readLine().trim();
						values = line.split("\\:");
						nAtoms = Integer.parseInt(values[1].trim());
						//number of electrons
						line = br.readLine().trim();
						values = line.split("\\:");
						electronCount = Integer.parseInt(values[1].trim());
						//number of alpha electrons
						line = br.readLine().trim();
						values = line.split("\\:");
						electronCountAlpha = Integer.parseInt(values[1].trim());
						//number of beta electrons
						line = br.readLine().trim();
						values = line.split("\\:");
						electronCountBeta = Integer.parseInt(values[1].trim());
						//charge
						line = br.readLine().trim();
						values = line.split("\\:");
						charge = Integer.parseInt(values[1].trim());
						//spin multiplicity
						line = br.readLine().trim();
						values = line.split("\\:");
						multiplicity = Integer.parseInt(values[1].trim());
						
						found = true;
					}
				}
				
				if (found == false){
					br.close();
					br = new IBIOMESFileReader(this);
				}
				else found = false;
			}
				
			br.close();
			
			//add corresponding method

			ArrayList<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
			if (method.equals(ParameterSet.METHOD_QM))
			{
				QMParameterSet qm = new QMParameterSet();
				if (levelOfTheory!=null)
					qm.setSpecificMethodName(levelOfTheory);
				if (basisSets!=null)
					qm.setBasisSets(basisSets);
				qm.setTotalCharge(charge);
				qm.setSpinMultiplicity(multiplicity);
				
				QMTask task = new QMTask(qm);
				task.setSoftware(software);
				task.setOutputFiles(relatedFiles);
				tasks.add(task);
			}
			else {
				MDParameterSet md = new MDParameterSet();
				if (forceField!=null)
					md.setForceField(forceField);

				MDTask task = new MDTask(md);
				task.setSoftware(software);
				task.setOutputFiles(relatedFiles);
				tasks.add(task);
			}
			
			//create processes with molecular systems and methods
			this.processGroups = new ArrayList<ExperimentProcessGroup>();
			//if multiple systems, create multiple processes
	    	if (systems.size()>1)
	    	{
	    		//if n methods = n systems assume 1 for 1
	    		if (systems.size() == tasks.size()){
	    			int m=0;
	    			for (MolecularSystem system : systems){
	    				
	    				ExperimentProcessGroup processGroup = new ExperimentProcessGroup(
	    						null, 
	    						tasks.get(m).getDescription(), 
	    						system, 
	    						new ExperimentProcess(null, tasks.get(m).getDescription(), tasks.get(m)));
	    				m++;
	    				this.processGroups.add(processGroup);
	    				//System.out.println(process.toString());
	    			}
	    		}
	    		else{
	    			//TODO handle 'set geometry' markers to assign correct geometry to the methods
	    		}
	    	}
	    	else {
	    		MolecularSystem molecularSystem = null;
	    		if (systems.size()>0)
	    			molecularSystem = systems.get(0);
	    		ExperimentProcess process = new ExperimentProcess(null, null, tasks);
	    		ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, null, molecularSystem, process);
	    		processGroups.add(processGroup);
	    	}
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
