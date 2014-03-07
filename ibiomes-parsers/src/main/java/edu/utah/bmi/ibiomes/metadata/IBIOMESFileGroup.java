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

package edu.utah.bmi.ibiomes.metadata;

import java.util.Arrays;
import java.util.List;

import edu.utah.bmi.ibiomes.parse.LocalFile;

/**
 * File format groups in iBIOMES
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESFileGroup
{
	public final static String[] analysisFileFormats = {
		LocalFile.FORMAT_BMP, 
		LocalFile.FORMAT_PNG, 
		LocalFile.FORMAT_JPEG, 
		LocalFile.FORMAT_GIF, 
		LocalFile.FORMAT_TIF, 
		LocalFile.FORMAT_CSV
	};
	public final static String[] topologyFileFormats = {
		LocalFile.FORMAT_AMBER_PARMTOP,
		LocalFile.FORMAT_PDB,
		LocalFile.FORMAT_PSF,
		LocalFile.FORMAT_SDF,
		LocalFile.FORMAT_MOL2,
		LocalFile.FORMAT_GAUSSIAN_COM,
		LocalFile.FORMAT_NWCHEM_INPUT,
		LocalFile.FORMAT_GAMESS_INPUT
	};
	public final static String[] parameterFileFormats = {
		LocalFile.FORMAT_AMBER_MDIN,
		LocalFile.FORMAT_AMBER_MDOUT,
		LocalFile.FORMAT_GROMACS_MDP,
		LocalFile.FORMAT_CHARMM_INP,
		LocalFile.FORMAT_NAMD_CONFIGURATION,
		LocalFile.FORMAT_GAUSSIAN_COM,
		LocalFile.FORMAT_NWCHEM_INPUT,
		LocalFile.FORMAT_NWCHEM_OUTPUT,
		LocalFile.FORMAT_GAMESS_INPUT
	};
	public final static String[] jmolFileFormats = {
		LocalFile.FORMAT_PDB, 
		LocalFile.FORMAT_CML,
		LocalFile.FORMAT_GROMACS_GRO,
		LocalFile.FORMAT_GAUSSIAN_LOG,
		LocalFile.FORMAT_GAMESS_OUTPUT,
		LocalFile.FORMAT_NWCHEM_OUTPUT,
		LocalFile.FORMAT_XYZ,
		LocalFile.FORMAT_SDF
	};
	public final static String[] imageFileFormats = {
		LocalFile.FORMAT_BMP, 
		LocalFile.FORMAT_PNG, 
		LocalFile.FORMAT_JPEG, 
		LocalFile.FORMAT_GIF, 
		LocalFile.FORMAT_TIF
	};
	
	/**
	 * Check if the file format is part of the topology file format group
	 * @param fileFormat File format
	 * @return True if the format is part of the group
	 */
	public static boolean isTopologyFile(String fileFormat){
		List<String> list =  Arrays.asList(IBIOMESFileGroup.topologyFileFormats);
		return (list.contains(fileFormat));
	}
	
	/**
	 * Check if the file format is part of the parameter file format group
	 * @param fileFormat File format
	 * @return True if the format is part of the group
	 */
	public static boolean isParameterFile(String fileFormat){
		List<String> list =  Arrays.asList(IBIOMESFileGroup.parameterFileFormats);
		return (list.contains(fileFormat));
	}
	
	/**
	 * Check if the file format is part of the analysis file format group
	 * @param fileFormat File format
	 * @return True if the format is part of the group
	 */
	public static boolean isAnalysisFile(String fileFormat){
		List<String> list =  Arrays.asList(IBIOMESFileGroup.analysisFileFormats);
		return (list.contains(fileFormat));
	}
	
	/**
	 * Check if the file format is part of the Jmol file format group
	 * @param fileFormat File format
	 * @return True if the format is part of the group
	 */
	public static boolean isJmolFile(String fileFormat){
		List<String> list =  Arrays.asList(IBIOMESFileGroup.jmolFileFormats);
		return (list.contains(fileFormat));
	}
	
	/**
	 * Check if the file format is part of the image file format group
	 * @param fileFormat File format
	 * @return True if the format is part of the group
	 */
	public static boolean isImageFile(String fileFormat){
		List<String> list =  Arrays.asList(IBIOMESFileGroup.imageFileFormats);
		return (list.contains(fileFormat));
	}
}
