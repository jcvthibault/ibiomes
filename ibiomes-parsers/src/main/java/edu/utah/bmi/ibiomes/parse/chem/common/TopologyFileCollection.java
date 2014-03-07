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

package edu.utah.bmi.ibiomes.parse.chem.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.TopologyFile;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;

/**
 * Utility class to find topology files and appropriate file formats
 * @author Julien Thibault, University of Utah
 *
 */
public class TopologyFileCollection{

	public final static String[] TOPOLOGY_FORMATS_AMBER = {
		LocalFile.FORMAT_AMBER_PARMTOP, 
		LocalFile.FORMAT_PDB
	};
	public final static String[] TOPOLOGY_FORMATS_CHARMM = {
		LocalFile.FORMAT_PDB, 
		LocalFile.FORMAT_PSF 
	};
	public final static String[] TOPOLOGY_FORMATS_GAMESS = {
		LocalFile.FORMAT_GAMESS_INPUT, 
		LocalFile.FORMAT_PDB 
		};
	public final static String[] TOPOLOGY_FORMATS_GAUSSIAN = {
		LocalFile.FORMAT_GAUSSIAN_COM, 
		LocalFile.FORMAT_PDB 
	};
	public final static String[] TOPOLOGY_FORMATS_GROMACS = {
		LocalFile.FORMAT_GROMACS_TOP, 
		LocalFile.FORMAT_PDB  
	};
	public final static String[] TOPOLOGY_FORMATS_NAMD = {
		LocalFile.FORMAT_AMBER_PARMTOP,
		LocalFile.FORMAT_PSF, 
		LocalFile.FORMAT_PDB
	};
	public final static String[] TOPOLOGY_FORMATS_NWCHEM = {
		LocalFile.FORMAT_PDB, 
		LocalFile.FORMAT_NWCHEM_INPUT, 
		LocalFile.FORMAT_NWCHEM_TOPOLOGY, 
		LocalFile.FORMAT_NWCHEM_OUTPUT 
	};

	public final static String[] TOPOLOGY_FORMATS_COMMON = {
		LocalFile.FORMAT_PDB, 
		LocalFile.FORMAT_MOL2, 
		LocalFile.FORMAT_SDF, 
		LocalFile.FORMAT_PSF
		};
	
	/**
	 * Find main topology file in list of files
	 * @param allowedFormats List of allowed formats
	 * @param allFiles List of files grouped by format
	 * @return Main topology file
	 * @throws Exception
	 */
	public static TopologyFile findMainTopologyFile(String[] allowedFormats, HashMap<String, ArrayList<LocalFile>>allFiles) throws Exception
	{
		TopologyFile mainTopologyFile = null;
		//if no format specified used common ones as default (e.g. PDB)
		if (allowedFormats == null || allowedFormats.length==0)
			allowedFormats = TOPOLOGY_FORMATS_COMMON;
		
		//retrieve list of files that can be chosen as main topology file for this collection
		List<List<LocalFile>> fileGroupList = new ArrayList<List<LocalFile>>();
		for (String format : allowedFormats){
			List<LocalFile> fileGroup = allFiles.get(format);
			if (fileGroup!=null && fileGroup.size()>0){
				fileGroupList.add(fileGroup);
			}
		}
		
		if (fileGroupList.size()>0)
		{
			//check if the main topology file has be set already
			int f=0;
			while (mainTopologyFile == null && f<fileGroupList.size()){
				List<LocalFile> fileGroup = fileGroupList.get(f);
				
				for (LocalFile file : fileGroup){
					if (file.getAssignedClasses()!=null && file.getAssignedClasses().contains(FileMetadata.FILE_CLASS_TOPOLOGY_MAIN)){
						mainTopologyFile = (TopologyFile) file;
						break;
					}
				}
				f++;
			}
			
			//if not, choose file that meets the criteria and that has the largest 
			// number of atoms (to avoid stripped topology) and the most AVUs
			if (mainTopologyFile == null){
				int nAtoms = -1;
				int nAVUs = -1;
				for (List<LocalFile> fileGroup : fileGroupList){
					for (LocalFile file : fileGroup){
						String nAtomsStr = file.getMetadata().getValue(TopologyMetadata.COUNT_ATOMS);
						if (nAtomsStr.length()>0){
							int nAtomsInFile = Integer.parseInt(nAtomsStr);
							int nAVUsInFile = file.getMetadata().getTopologyMetadata().size();
							if (nAtomsInFile > nAtoms || (nAtomsInFile==nAtoms && nAVUsInFile > nAVUs)){
								mainTopologyFile = (TopologyFile) file;
								nAtoms = nAtomsInFile;
								nAVUs = nAVUsInFile;
							}
						}
					}
				}
			}
		}
		return mainTopologyFile;
	}
	
	/**
	 * Find the largest molecular system defined in one of the given topology files
	 * @param softwareContext Software context
	 * @param allFiles List of parsed files
	 * @return Largest molecular system
	 * @throws Exception
	 */
	public static MolecularSystem findLargestMolecularSystem(
			String softwareContext, 
			HashMap<String, ArrayList<LocalFile>>allFiles) throws Exception 
	{
		String[] allowedFormats = getTopologyFormatsForSoftware(softwareContext);
		TopologyFile topologyFile = findMainTopologyFile(allowedFormats, allFiles);
		if (topologyFile!=null){
			List<MolecularSystem> systems = topologyFile.getMolecularSystems();
			MolecularSystem system = findLargestMolecularSystem(systems);
			return system;
		}
		else return null;
	}

	/**
	 * Get list of topology file formats for a given software package
	 * @param softwareContext Name of the software package
	 * @return List of topology file formats
	 */
	public static String[] getTopologyFormatsForSoftware(String softwareContext)
	{
		if (softwareContext==null)
			return TOPOLOGY_FORMATS_COMMON;
		
		softwareContext = softwareContext.toUpperCase();
		if (softwareContext.equals(Software.AMBER.toUpperCase())){
			return TOPOLOGY_FORMATS_AMBER;
		}
		else if (softwareContext.equals(Software.CHARMM.toUpperCase())){
			return TOPOLOGY_FORMATS_CHARMM;
		}
		else if (softwareContext.equals(Software.GAMESS.toUpperCase())){
			return TOPOLOGY_FORMATS_GAMESS;
		}
		else if (softwareContext.equals(Software.GAUSSIAN.toUpperCase())){
			return TOPOLOGY_FORMATS_GAUSSIAN;
		}
		else if (softwareContext.equals(Software.GROMACS.toUpperCase())){
			return TOPOLOGY_FORMATS_GROMACS;
		}
		else if (softwareContext.equals(Software.NAMD.toUpperCase())){
			return TOPOLOGY_FORMATS_NAMD;
		}
		else if (softwareContext.equals(Software.NWCHEM.toUpperCase())){
			return TOPOLOGY_FORMATS_NWCHEM;
		}
		else return TOPOLOGY_FORMATS_COMMON;
	}

	/**
	 * Find the largest molecular system in a list of systems
	 * @param systems List of molecular systems
	 * @return Largest system
	 */
	public static MolecularSystem findLargestMolecularSystem(List<MolecularSystem> systems) {
		MolecularSystem biggestSystem = null;
		if (systems!=null){
			if (systems.size()==1)
				biggestSystem = systems.get(0);
			else{
				biggestSystem = systems.get(0);
				int maxSystemSize = systems.get(0).getAtomCount();
				for (int s=1; s<systems.size(); s++){
					MolecularSystem system = systems.get(s);
					int nAtoms = system.getAtomCount();
					if (nAtoms>maxSystemSize){
						maxSystemSize = nAtoms;
						biggestSystem = systems.get(s);
					}
				}
			}
		}
		return biggestSystem;
	}
}
