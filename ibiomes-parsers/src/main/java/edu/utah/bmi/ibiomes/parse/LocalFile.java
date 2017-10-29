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

package edu.utah.bmi.ibiomes.parse;

import java.io.IOException;
import java.util.List;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Interface for local files that can be published in iRODS
 * @author Julien Thibault
 *
 */
public interface LocalFile {
	
	/** ASCII/unicode text file **/
	public final static String FORMAT_TXT = "Text";
	/** TAR file **/
	public static final String FORMAT_TAR = "TAR";
	/** PDF document **/
	public final static String FORMAT_PDF = "PDF";
	/** script **/
	public final static String FORMAT_SCRIPT = "Script";
	/** Comma-separated value file **/
	public final static String FORMAT_CSV = "CSV";
	/** XML file **/
	public final static String FORMAT_XML = "XML";
	/** HTML file **/
	public final static String FORMAT_HTML = "HTML";
	
	/** TCL script file **/
	public final static String FORMAT_TCL_SCRIPT = "TCL script";
	
	/** Chemical Markup Language **/
	public final static String FORMAT_CML = "CML";
	/** MDL molfile/Structure Data Format (SDF) **/
	public final static String FORMAT_SDF = "SDF";
	/** Mol2 file **/
	public final static String FORMAT_MOL2 = "Mol2";
	/** XYZ files **/
	public final static String FORMAT_XYZ = "XYZ";
	/** Protein Data Bank file **/
	public final static String FORMAT_PDB = "PDB";
	
	
	/** AMBER param/top file **/
	public final static String FORMAT_AMBER_PARMTOP = "AMBER parmtop";
	/** AMBER MD trajectory file (NetCDF) **/
	public final static String FORMAT_AMBER_TRAJ_NETCDF = "AMBER trajectory (NetCDF)";
	/** AMBER MD trajectory file (binary) **/
	public final static String FORMAT_AMBER_TRAJ_BIN = "AMBER trajectory (binary)";  
	/** AMBER MD trajectory file (Binpos) **/
	public final static String FORMAT_AMBER_TRAJ_BINPOS = "AMBER trajectory (Binpos)"; 
	/** AMBER MD trajectory file (ASCII) **/
	public final static String FORMAT_AMBER_TRAJ_ASCII = "AMBER trajectory (ASCII)";  
	/** AMBER MD trajectory file (unknown format) **/
	public final static String FORMAT_AMBER_TRAJ = "AMBER trajectory";  
	/** AMBER MD input file **/
	public final static String FORMAT_AMBER_MDIN = "AMBER MD input";
	/** AMBER MD output file **/
	public final static String FORMAT_AMBER_MDOUT = "AMBER MD output";
	/** AMBER restart file **/
	public final static String FORMAT_AMBER_RESTART = "AMBER restart";
	/** AMBER OFF library file **/
	public final static String FORMAT_AMBER_OFF = "AMBER library";
	/** AMBER Leap log file **/
	public final static String FORMAT_AMBER_LEAP_LOG = "AMBER Leap log";
	
	/** AMBER ptraj script **/
	public final static String FORMAT_PTRAJ_SCRIPT = "Ptraj/cpptraj script";
	
	
	/** LAMMPS data file (topology)**/
	public final static String FORMAT_LAMMPS_DATA = "LAMMPS data";
	/** LAMMPS MD trajectory file  **/
	public final static String FORMAT_LAMMPS_TRAJ = "LAMMPS trajectory";
	/** LAMMPS MD input file **/
	public final static String FORMAT_LAMMPS_IN = "LAMMPS MD input"; 
	/** LAMMPS MD output file **/
	public final static String FORMAT_LAMMPS_OUTPUT = "LAMMPS MD output"; 
	/** LAMMPS restart file **/
	public final static String FORMAT_LAMMPS_RESTART = "LAMMPS restart";
	
	
	/** GROMACS topology file **/
	public final static String FORMAT_GROMACS_TOP = "GROMACS system topology";
	/** GROMACS MD input file **/
	public final static String FORMAT_GROMACS_ITP = "GROMACS include topology";
	/** GROMACS trajectory file  **/
	public final static String FORMAT_GROMACS_TRAJ = "GROMACS trajectory";
	/** GROMACS MD input file **/
	public final static String FORMAT_GROMACS_MDP = "GROMACS MD input";
	/** GROMACS MD output file **/
	public final static String FORMAT_GROMACS_OUTPUT = "GROMACS MD output"; 
	/** GROMACS restart file **/
	public final static String FORMAT_GROMACS_RESTART = "GROMACS restart";
	/** GROMACS GRO coordinate file **/
	public final static String FORMAT_GROMACS_GRO = "GROMACS gro";
	/** GROMACS NDX index file **/
	public final static String FORMAT_GROMACS_NDX = "GROMACS index";
	
	
	/** GAUSSIAN com files (input) **/
	public final static String FORMAT_GAUSSIAN_COM = "GAUSSIAN com";
	/** GAUSSIAN log files (output) **/
	public final static String FORMAT_GAUSSIAN_LOG = "GAUSSIAN log";
	/** GAUSSIAN chk file **/
	public final static String FORMAT_GAUSSIAN_CHK = "GAUSSIAN chk";
	/** GAUSSIAN restart file **/
	public final static String FORMAT_GAUSSIAN_RESTART = "GAUSSIAN restart";
	/** GAUSSIAN cube file **/
	public final static String FORMAT_GAUSSIAN_CUBE = "GAUSSIAN cube";

	
	/** GAMESS input files **/
	public final static String FORMAT_GAMESS_INPUT = "GAMESS input";
	/** GAMESS output files **/
	public final static String FORMAT_GAMESS_OUTPUT = "GAMESS output";
	
	
	/** NWChem input file **/
	public final static String FORMAT_NWCHEM_INPUT = "NWChem input";
	/** NWChem output file **/
	public final static String FORMAT_NWCHEM_OUTPUT = "NWChem output";
	/** NWChem topology file **/
	public static final String FORMAT_NWCHEM_TOPOLOGY = "NWChem topology";
	/** NWChem restart file **/
	public static final String FORMAT_NWCHEM_RESTART = "NWChem restart";
	
	
	/** NAMD configuration file **/
	public final static String FORMAT_NAMD_CONFIGURATION = "NAMD configuration";
	/** NAMD standard output file **/
	public static final String FORMAT_NAMD_LOG = "NAMD standard output";
	

	/** DCD trajectory file **/
	public final static String FORMAT_DCD_TRAJECTORY = "DCD trajectory";
	/** Protein Structure File (PSF) **/
	public final static String FORMAT_PSF = "PSF";
	

	/** CHARMM coordinates file **/
	public final static String FORMAT_CHARMM_CRD = "CHARMM coordinates";
	/** CHARMM MD input file **/
	public final static String FORMAT_CHARMM_INP = "CHARMM input";
	/** CHARMM MD output file **/
	public final static String FORMAT_CHARMM_OUT = "CHARMM output";

	
	/** Unknown format **/
	public final static String FORMAT_UNKNOWN = "Unknown";
	
	
	/** JPEG image file **/
	public final static String FORMAT_JPEG = "JPEG";
	/** PNG image file **/
	public final static String FORMAT_PNG = "PNG";
	/** GIF image file **/
	public final static String FORMAT_GIF = "GIF";
	/** TIF image file **/
	public final static String FORMAT_TIF = "TIF";
	/** BMP image file **/
	public final static String FORMAT_BMP = "BMP";
	
	/** Gnuplot script file **/
	public final static String FORMAT_GNUPLOT = "Gnuplot";

	
	public final static String TYPE_AUDIO_VIDEO = "Audio/Video";
	public final static String TYPE_CHEMICAL 	= "Chemical file";
	public final static String TYPE_CODE		= "Code";	
	public final static String TYPE_DOCUMENT 	= "Document";
	public final static String TYPE_IMAGE 		= "Image";
	public final static String TYPE_PRESENTATION = "Presentation";
	public final static String TYPE_SPREADSHEET = "Spreadsheet";
	public final static String TYPE_WEB 		= "Web";
	public final static String TYPE_UNKNOWN 	= "Unknown";
	
	
	/**
	 * Return the set of metadata defined for this file.
	 * @return Set of metadata defined for this file
	 * @throws Exception 
	 */
	public MetadataAVUList getMetadata() throws Exception;
		
	/**
	 * Get file type (e.g. image, audio/video, chemical file)
	 * @return File type
	 */
	public String getFileType();
	
	/**
	 * Get file absolute path
	 * @return File path
	 */
	public String getAbsolutePath();

	/**
	 * Get file canonical path
	 * @return File canonical path
	 */
	public String getCanonicalPath() throws IOException;
	
	/**
	 * Get file name
	 * @return File name
	 */
	public String getName();

	/**
	 * Get path to parent directory
	 * @return path to parent directory
	 */
	public String getParent();
	
	/**
	 * Get file size
	 * @return File size
	 */
	public long length();

	/**
	 * Get timestamp
	 * @return File timestamp
	 */
	public long lastModified();
	
	/**
	 * Get file format  (e.g. PDB, AMBER, CSV)
	 * @return File format
	 */
	public String getFormat();

	/**
	 * Get relative path to file from project root directory
	 * @return Relative path
	 */
	public String getRelativePathFromProjectRoot();

	/**
	 * Set relative path to file from project root directory
	 * @param relativePathFromProjectRoot Relative path
	 */
	public void setRelativePathFromProjectRoot(String relativePathFromProjectRoot);

	/**
	 * Get external URL to file
	 * @return external URL to file
	 */
	public String getExternalURL();

	/**
	 * Set external URL to file
	 * @param externalURL external URL to file
	 */
	public void setExternalURL(String externalURL);
	
	/**
	 * Get description
	 * @return File description
	 */
	public String getDescription();

	/**
	 * Set file description
	 * @param fileDescription File description
	 */
	public void setDescription(String fileDescription);
	
	/**
	 * Get assigned classes (e.g. analysis, main topology, main structure)
	 * @return List of classes for this file
	 */
	public List<String> getAssignedClasses();

	/**
	 * Set assigned classes (e.g. analysis, main topology, main structure)
	 * @param assignedClasses List of classes for this file
	 */
	public void setAssignedClasses(List<String> assignedClasses);
	
	/**
	 * get list of extended attributes
	 * @return List of extended attributes
	 */
	public MetadataAVUList getExtendedAttributes();
	
	/**
	 * Set list of metadata that overrides native file metadata generated by file parsers.
	 * @param fileMetadata List of extended attributes
	 */
	public void setExtendedAttributes(MetadataAVUList fileMetadata);
}
