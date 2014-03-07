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

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessControlException;

import org.apache.log4j.Logger;

import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberLeapLogFile;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberLibraryFile;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberMdInputFile;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberMdOutputFile;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberParameterTopologyFile;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberRestartFile;
import edu.utah.bmi.ibiomes.parse.chem.amber.AmberTrajectoryFileFactory;
import edu.utah.bmi.ibiomes.parse.chem.charmm.CHARMMCoordinateFile;
import edu.utah.bmi.ibiomes.parse.chem.charmm.CHARMMInputFile;
import edu.utah.bmi.ibiomes.parse.chem.charmm.CHARMMOutputFile;
import edu.utah.bmi.ibiomes.parse.chem.common.DCDTrajectoryFile;
import edu.utah.bmi.ibiomes.parse.chem.common.Mol2File;
import edu.utah.bmi.ibiomes.parse.chem.common.PDBFile;
import edu.utah.bmi.ibiomes.parse.chem.common.ProteinStructureFile;
import edu.utah.bmi.ibiomes.parse.chem.common.SDFFile;
import edu.utah.bmi.ibiomes.parse.chem.gamess.GAMESSInputFile;
import edu.utah.bmi.ibiomes.parse.chem.gamess.GAMESSOutputFile;
import edu.utah.bmi.ibiomes.parse.chem.gaussian.GaussianInputFile;
import edu.utah.bmi.ibiomes.parse.chem.gaussian.GaussianOutputFile;
import edu.utah.bmi.ibiomes.parse.chem.gromacs.GROMACSGroFile;
import edu.utah.bmi.ibiomes.parse.chem.gromacs.GROMACSIncludeTopologyFile;
import edu.utah.bmi.ibiomes.parse.chem.gromacs.GROMACSParameterInputFile;
import edu.utah.bmi.ibiomes.parse.chem.gromacs.GROMACSSystemTopologyFile;
import edu.utah.bmi.ibiomes.parse.chem.gromacs.GROMACSTrajectoryFile;
import edu.utah.bmi.ibiomes.parse.chem.namd.NAMDConfigurationFile;
import edu.utah.bmi.ibiomes.parse.chem.namd.NAMDStandardOutputFile;
import edu.utah.bmi.ibiomes.parse.chem.nwchem.NWChemInputFile;
import edu.utah.bmi.ibiomes.parse.chem.nwchem.NWChemOutputFile;

/**
 * File factory based on file format.
 * @author Julien Thibault
 *
 */
public class LocalFileFactory {

	private final Logger logger = Logger.getLogger(LocalFileFactory.class);
	
	private static LocalFileFactory _factory;

	private final static String PDF_FILE_EXT	= "(pdf)";
	private final static String TXT_FILE_EXT	= "(txt)";
	private final static String CSV_FILE_EXT	= "(csv)";

	private final static String PNG_FILE_EXT	= "(png)";
	private final static String JPG_FILE_EXT	= "(jpe?g)";
	private final static String GIF_FILE_EXT	= "(gif)";
	private final static String TIF_FILE_EXT	= "(tiff?)";
	private final static String BMP_FILE_EXT	= "(bmp)";
	
	private final static String PDB_FILE_EXT	= "(pdb)";
	//private final static String CML_FILE_EXT	= "(cml)";
	private final static String SDF_FILE_EXT	= "(mol)|(sdf)";
	private final static String MOL2_FILE_EXT	= "(mol2)";
	
	private final static String DCD_FILE_EXT	= "(dcd)";
	private final static String PSF_FILE_EXT	= "(psf)";
	
	private final static String TCL_FILE_EXT	= "(tcl)";
	
	private final static String GNUPLOT_FILE_EXT	= "(gnu)";
	
	private final static String NWCHEM_FILE_EXT_INPUT		= "(nw)";
	private final static String NWCHEM_FILE_EXT_OUTPUT 		= "(out)|(output)|(log)";
	//private final static String NWCHEM_FILE_EXT_TOPOLOGY	= "(top)";
	//private final static String NWCHEM_FILE_EXT_RESTART		= "(rst)";
	
	private final static String AMBER_FILE_EXT_PRMTOP 	    = "(prmtop)|(top)|(topo)|(parm)|(parm7)";
	private final static String AMBER_FILE_EXT_TRAJECTORY   = "(traj)|(nc)|(x)|(mdcrd)";
	private final static String AMBER_FILE_EXT_MDIN 		= "(in)|(inp)|(mdin)";
	private final static String AMBER_FILE_EXT_MDOUT 		= "(out)|(mdout)";
	private final static String AMBER_FILE_EXT_RESTART		= "(rst)|(restart)|(restrt)";
	private final static String AMBER_FILE_EXT_OFF	    	= "(lib)|(off)";
	private final static String AMBER_PTRAJ_FILE_EXT    	= "(trajin)|(ptraj)";
	
	private final static String GROMACS_FILE_EXT_TOP   		= "(top)";
	private final static String GROMACS_FILE_EXT_MDP   		= "(mdp)";
	private final static String GROMACS_FILE_EXT_ITP   		= "(itp)";
	private final static String GROMACS_FILE_EXT_TRAJ  		= "(traj)";
	//private final static String GROMACS_FILE_EXT_NDX  		= "(ndx)";
	//private final static String GROMACS_FILE_EXT_GRO  		= "(gro)";

	private final static String CHARMM_FILE_EXT_CRD   		= "(crd)";
	private final static String CHARMM_FILE_EXT_OUT   		= "(out)";
	private final static String CHARMM_FILE_EXT_INP   		= "(inp)";
	
	private final static String NAMD_FILE_EXT_CONF   		= "(conf)";
	private final static String NAMD_FILE_EXT_LOG   		= "(out)";
	
	private final static String GAUSSIAN_FILE_EXT_INPUT 	= "(com)|(gjf)";
	private final static String GAUSSIAN_FILE_EXT_OUTPUT 	= "(log)";
	private final static String GAUSSIAN_FILE_EXT_CHK 		= "(chk)|(fchk)";
	//private final static String GAUSSIAN_FILE_EXT_RESTART	= "(rst)";
	//private final static String GAUSSIAN_FILE_EXT_CUBE   	= "(cube)|(cube2)";

	private final static String GAMESS_FILE_EXT_INPUT 		= "(inp)";
	private final static String GAMESS_FILE_EXT_OUTPUT 		= "(out)";

	private final static String COMPRESSED_FILE_EXT   		= "(gz(ip)?)|(bz(ip)?(2)?)";
	private final static String ZIP_FILE_EXT   				= "(zip)";
	private final static String TAR_FILE_EXT   				= "(tar(\\.("+COMPRESSED_FILE_EXT+"))?)|(t("+COMPRESSED_FILE_EXT+"))";
	

	/**
	 * Private constructor
	 */
	private LocalFileFactory()
	{ }
	
	/**
	 * Get unique instance of the file factory
	 * @return Local file factory
	 */
	public static LocalFileFactory instance()
	{
		if (_factory == null){
		    _factory = new LocalFileFactory();
		}
		return _factory;
	}
	
	/**
	 * Check if the give file is an archive
	 * @param fileName Name of the file
	 * @return True if its an archive
	 */
	public boolean isArchiveFile(String fileName){
		if (fileName.matches(".*(" + TAR_FILE_EXT + ")")){
			return true;
		}
		return false;
	}

	/**
	 * Check if the file is a compressed file
	 * @param fileName  Name of the file
	 * @return True if it is compressed
	 */
	public boolean isCompressedFile(String fileName){
		if (fileName.matches(".*" + COMPRESSED_FILE_EXT) || fileName.matches(".*" + ZIP_FILE_EXT)){
			return true;
		}
		return false;
	}
	
	/**
	 * Instantiate correct file based on file extension and the context given the software that was used to generate/manage the files
	 * @param localPath Path to local file
	 * @param softwareContext Software context
	 * @return Local file reference
	 * @throws IBIOMESFileParserException 
	 * @throws FileNotFoundException 
	 */
	public LocalFile getFile(String localPath, String softwareContext) throws IBIOMESFileParserException, FileNotFoundException
	{
		String fileExtension = null;
		LocalFile file = null;
		
		Path filePath = Paths.get(localPath);
		if (!Files.exists(filePath)){
			throw new FileNotFoundException("File " + localPath + " does not exist!");
		}
		else if (!Files.isReadable(filePath)){
			throw new AccessControlException("File " + localPath + " is not readable!");
		}

		try{
			//archive file (dont try to parse)
			if (isArchiveFile(localPath)){
				return new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_TAR);
			}
			
			//get file extension
			fileExtension = getFileExtension(localPath.substring(localPath.lastIndexOf('/')));
			fileExtension = fileExtension.toLowerCase();
			
			//check image formats
			if (!isValidFile(file)){
				if (ImageFile.findImageFormat(localPath) != LocalFile.FORMAT_UNKNOWN){
					file = new ImageFile(localPath);
				}
			}
			
			// search generic/standard file formats from file extension (e.g. .pdb, .csv)
			if (!isValidFile(file))
			{
				fileExtension = fileExtension.toLowerCase();
				
				if (fileExtension.matches(CSV_FILE_EXT)){
					file = new CSVFile(localPath);
				}
				else if(fileExtension.matches(SDF_FILE_EXT)){
					//TODO use CDK format
					file = new SDFFile(localPath);
				}
				else if(fileExtension.matches(MOL2_FILE_EXT)){
					//TODO use CDK format
					file = new Mol2File(localPath);
				}
				else if(fileExtension.matches(PDB_FILE_EXT)){
					//TODO use CDK format
					file = new PDBFile(localPath);
				}
				else if(fileExtension.matches(TCL_FILE_EXT)){
					file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_TCL_SCRIPT, LocalFile.TYPE_CODE);
				}
				else if(fileExtension.matches(TXT_FILE_EXT)){
					file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_TXT, LocalFile.TYPE_DOCUMENT);
				}
				else if(fileExtension.matches(PDF_FILE_EXT)){
					file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_PDF, LocalFile.TYPE_DOCUMENT);
				}
				else if(fileExtension.matches(GNUPLOT_FILE_EXT)){
					file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_GNUPLOT, LocalFile.TYPE_CODE);
				}
			}
			
			// look for software-specific formats first
			if (!isValidFile(file) && (softwareContext != null && softwareContext.length()>0))
			{
				softwareContext = softwareContext.toUpperCase();
				
				if (softwareContext.equals(Software.AMBER.toUpperCase())){
					file = getAmberFile(localPath, fileExtension);
				}
				else if (softwareContext.equals(Software.GROMACS.toUpperCase())){
					file = getGromacsFile(localPath, fileExtension);
				}
				else if (softwareContext.equals(Software.CHARMM.toUpperCase())){
					file = getCharmmFile(localPath, fileExtension);
				}
				else if (softwareContext.equals(Software.NWCHEM.toUpperCase())){
					file = getNWChemFile(localPath, fileExtension);
				}
				else if (softwareContext.equals(Software.GAUSSIAN.toUpperCase())){
					file = getGaussianFile(localPath, fileExtension);
				}
				else if (softwareContext.equals(Software.GAMESS.toUpperCase())){
					file = getGamessFile(localPath, fileExtension);
				}
				else if (softwareContext.equals(Software.NAMD.toUpperCase())){
					file = getNamdFile(localPath, fileExtension);
				}
			}
			
			//go through each software context if not specified
			if (!isValidFile(file) && softwareContext==null)
			{
				file = getAmberFile(localPath, fileExtension);
				if (!isValidFile(file))
					file = getGaussianFile(localPath, fileExtension);
				if (!isValidFile(file))
					file = getGromacsFile(localPath, fileExtension);
				if (!isValidFile(file))
					file = getCharmmFile(localPath, fileExtension);
				if (!isValidFile(file))
					file = getNWChemFile(localPath, fileExtension);
				if (!isValidFile(file))
					file = getGamessFile(localPath, fileExtension);
				if (!isValidFile(file))
					file = getNamdFile(localPath, fileExtension);
			}

			//if no matching format, unknown format
			if (!isValidFile(file)){
				file = new DefaultLocalFileImpl(localPath);
			}
			
			return file;
		}
		catch (Exception e){
			throw new IBIOMESFileParserException(e);
		}
	}
	
	/**
	 * Check if given file is valid (format is known)
	 * @param file Local file
	 * @return True if valid
	 */
	private boolean isValidFile(LocalFile file){
		return (file!=null && file.getFormat()!=null && !file.getFormat().equals(LocalFile.FORMAT_UNKNOWN));
	}
	
	/**
	 * Get file instance from specified format
	 * @param localPath Path to local file
	 * @param fileFormat File format
	 * @return Local file reference
	 * @throws Exception
	 */
	public LocalFile getFileInstanceFromFormat(String localPath, String fileFormat) throws Exception
	{
		Path filePath = Paths.get(localPath);
		if (!Files.exists(filePath)){
			throw new FileNotFoundException("File " + localPath + " does not exist!");
		}
		else if (!Files.isReadable(filePath))
			throw new AccessControlException("File " + localPath + " is not readable!");
		
		LocalFile file = null;
		String originalFormat = fileFormat;
		fileFormat = fileFormat.toLowerCase();
		
		try{
			//AMBER 
			if (fileFormat.equals(LocalFile.FORMAT_AMBER_PARMTOP.toLowerCase())){
				file = new AmberParameterTopologyFile(localPath);
			}
			else if (  fileFormat.equals(LocalFile.FORMAT_AMBER_TRAJ.toLowerCase()) 
					|| fileFormat.equals(LocalFile.FORMAT_AMBER_TRAJ_NETCDF.toLowerCase())
					|| fileFormat.equals(LocalFile.FORMAT_AMBER_TRAJ_BIN.toLowerCase())
					|| fileFormat.equals(LocalFile.FORMAT_AMBER_TRAJ_ASCII.toLowerCase())){
				AmberTrajectoryFileFactory amberTrajFactory = new AmberTrajectoryFileFactory();
				file = amberTrajFactory.getTrajectoryFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_AMBER_MDIN.toLowerCase())){
				file = new AmberMdInputFile(localPath);
			} 
			else if (fileFormat.equals(LocalFile.FORMAT_AMBER_MDOUT.toLowerCase())){
				file = new AmberMdOutputFile(localPath);
			} 
			else if (fileFormat.equals(LocalFile.FORMAT_AMBER_RESTART.toLowerCase())){
				file = new AmberRestartFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_AMBER_OFF.toLowerCase())){
				file = new AmberLibraryFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_AMBER_LEAP_LOG.toLowerCase())){
				file = new AmberLeapLogFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_PTRAJ_SCRIPT.toLowerCase()))
			{
				file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_PTRAJ_SCRIPT, LocalFile.TYPE_CHEMICAL);
			} 
			
			//GROMACS 
			else if (fileFormat.equals(LocalFile.FORMAT_GROMACS_TOP.toLowerCase())){
				file = new GROMACSSystemTopologyFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_GROMACS_ITP.toLowerCase())){
				file = new GROMACSIncludeTopologyFile(localPath);
			} 
			else if (fileFormat.equals(LocalFile.FORMAT_GROMACS_MDP.toLowerCase())){
				file = new GROMACSParameterInputFile(localPath);
			} 
			else if (fileFormat.equals(LocalFile.FORMAT_GROMACS_RESTART.toLowerCase())){
				file = new DefaultLocalFileImpl(localPath,LocalFile.FORMAT_GROMACS_RESTART,LocalFile.TYPE_CHEMICAL);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_GROMACS_TOP.toLowerCase())){
				file = new GROMACSSystemTopologyFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_GROMACS_TRAJ.toLowerCase())){
				file = new GROMACSTrajectoryFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_GROMACS_GRO.toLowerCase())){
				file = new GROMACSGroFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_GROMACS_NDX.toLowerCase())){
				file = new DefaultLocalFileImpl(localPath,  LocalFile.FORMAT_GROMACS_NDX, LocalFile.TYPE_CHEMICAL);
			}
			
			//CHARMM 
			else if (fileFormat.equals(LocalFile.FORMAT_CHARMM_INP.toLowerCase())){
				file = new CHARMMInputFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_CHARMM_OUT.toLowerCase())){
				file = new CHARMMOutputFile(localPath);
			} 
			else if (fileFormat.equals(LocalFile.FORMAT_CHARMM_CRD.toLowerCase())){
				file = new CHARMMCoordinateFile(localPath);
			}
			
			//NAMD 
			else if (fileFormat.equals(LocalFile.FORMAT_NAMD_CONFIGURATION.toLowerCase())){
				file = new NAMDConfigurationFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_NAMD_LOG.toLowerCase())){
				file = new NAMDStandardOutputFile(localPath);
			}
			
			//NWCHEM 
			else if (fileFormat.equals(LocalFile.FORMAT_NWCHEM_INPUT.toLowerCase())){
				file = new NWChemInputFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_NWCHEM_OUTPUT.toLowerCase())){
				file = new NWChemOutputFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_NWCHEM_TOPOLOGY.toLowerCase())){
				file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_NWCHEM_TOPOLOGY, LocalFile.TYPE_CHEMICAL);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_NWCHEM_RESTART.toLowerCase())){
				file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_NWCHEM_RESTART, LocalFile.TYPE_CHEMICAL);
			}
			
			//GAUSSIAN
			else if (fileFormat.equals(LocalFile.FORMAT_GAUSSIAN_CHK.toLowerCase())){
				file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_GAUSSIAN_CHK, LocalFile.TYPE_CHEMICAL);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_GAUSSIAN_CUBE.toLowerCase())){
				file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_GAUSSIAN_CUBE, LocalFile.TYPE_CHEMICAL);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_GAUSSIAN_COM.toLowerCase())){
				file = new GaussianInputFile(localPath);
			} 
			else if (fileFormat.equals(LocalFile.FORMAT_GAUSSIAN_LOG.toLowerCase())){
				file = new GaussianOutputFile(localPath);
			} 
			else if (fileFormat.matches(LocalFile.FORMAT_GAUSSIAN_RESTART.toLowerCase())){
				file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_GAUSSIAN_RESTART, LocalFile.TYPE_CHEMICAL);
			}
			
			//GAMESS
			else if (fileFormat.equals(LocalFile.FORMAT_GAMESS_INPUT.toLowerCase())){
				file = new GAMESSInputFile(localPath);
			} 
			else if (fileFormat.equals(LocalFile.FORMAT_GAMESS_OUTPUT.toLowerCase())){
				file = new GAMESSOutputFile(localPath);
			} 
			
			//COMMON MD FORMATS
			else if (fileFormat.equals(LocalFile.FORMAT_PSF.toLowerCase())){
				file = new ProteinStructureFile(localPath);
			}
			else if (fileFormat.equals(LocalFile.FORMAT_DCD_TRAJECTORY.toLowerCase())){
				file = new DCDTrajectoryFile(localPath);
			} 
			
			//IMAGE FORMAT
			else if (fileFormat.equals(LocalFile.FORMAT_JPEG.toLowerCase())
					||fileFormat.equals(LocalFile.FORMAT_PNG.toLowerCase())
					||fileFormat.equals(LocalFile.FORMAT_TIF.toLowerCase())
					||fileFormat.equals(LocalFile.FORMAT_GIF.toLowerCase())
					||fileFormat.equals(LocalFile.FORMAT_BMP.toLowerCase())){
				file = new ImageFile(localPath);
			}
			
			// OTHERS
			else if (fileFormat.equals(LocalFile.FORMAT_CSV.toLowerCase())){
				file = new CSVFile(localPath);
			}
			else if(fileFormat.equals(LocalFile.FORMAT_PDB.toLowerCase())){
				file = new PDBFile(localPath);
			}
			else if(fileFormat.equals(LocalFile.FORMAT_PDB.toLowerCase())){
				file = new PDBFile(localPath);
			}
			
			//DEFAULT
			else file = new DefaultLocalFileImpl(localPath, originalFormat);
			
			return file;
		}
		catch (Exception e){
			throw new IBIOMESFileParserException(e);
		}
	}
	
	
	/**
	 * Instantiate AMBER file
	 * @param localPath Path to local file
	 * @return Local AMBER file reference
	 * @throws Exception 
	 */
	public LocalFile getAmberFile(String localPath, String fileExtension) throws Exception
	{
		LocalFile file = null;
		fileExtension = fileExtension.toLowerCase();
		
		if (fileExtension.matches(AMBER_FILE_EXT_PRMTOP)){
			file = new AmberParameterTopologyFile(localPath);
		}
		else if (fileExtension.matches(AMBER_FILE_EXT_TRAJECTORY)){
			AmberTrajectoryFileFactory amberTrajFactory = new AmberTrajectoryFileFactory();
			file = amberTrajFactory.getTrajectoryFile(localPath);
		}
		else if (fileExtension.matches(AMBER_FILE_EXT_MDIN)){
			file = new AmberMdInputFile(localPath);
		}
		else if (fileExtension.matches(AMBER_FILE_EXT_MDOUT)){
			file = new AmberMdOutputFile(localPath);
		}
		else if (fileExtension.matches(AMBER_FILE_EXT_RESTART)){
			file = new AmberRestartFile(localPath);
		}
		else if (fileExtension.matches(AMBER_FILE_EXT_OFF)){
			file = new AmberLibraryFile(localPath);
		}
		else if (fileExtension.matches(AMBER_PTRAJ_FILE_EXT)){
			file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_PTRAJ_SCRIPT, LocalFile.TYPE_CHEMICAL);
		} 
		
		return file;
	}
	
	/**
	 * Instantiate CHARMM file
	 * @param localPath Path to local file
	 * @return Local CHARMM file reference
	 * @throws Exception 
	 */
	public LocalFile getCharmmFile(String localPath, String fileExtension) throws Exception
	{
		LocalFile file = null;
		fileExtension = fileExtension.toLowerCase();
		
		if (fileExtension.matches(CHARMM_FILE_EXT_CRD)){
			file = new CHARMMCoordinateFile(localPath);
		}
		else if (fileExtension.matches(DCD_FILE_EXT)){
			file = new DCDTrajectoryFile(localPath);
		}
		else if (fileExtension.matches(CHARMM_FILE_EXT_INP)){
			file = new CHARMMInputFile(localPath);
		} 
		else if (fileExtension.matches(CHARMM_FILE_EXT_OUT)){
			file = new CHARMMOutputFile(localPath);
		}
		
		return file;
	}
	
	/**
	 * Instantiate GROMACS file
	 * @param localPath Path to local file
	 * @return Local GROMACS file reference
	 * @throws Exception 
	 */
	public LocalFile getGromacsFile(String localPath, String fileExtension) throws Exception
	{
		LocalFile file = null;
		fileExtension = fileExtension.toLowerCase();
		
		if (fileExtension.matches(GROMACS_FILE_EXT_TOP)){
			file = new GROMACSSystemTopologyFile(localPath);
		}
		else if (fileExtension.matches(GROMACS_FILE_EXT_TRAJ)){
			file = new GROMACSTrajectoryFile(localPath);
		}
		else if (fileExtension.matches(GROMACS_FILE_EXT_ITP)){
			file = new GROMACSIncludeTopologyFile(localPath);
		} 
		else if (fileExtension.matches(GROMACS_FILE_EXT_MDP)){
			file = new GROMACSParameterInputFile(localPath);
		}
		else if (fileExtension.matches(PSF_FILE_EXT)){
			file = new ProteinStructureFile(localPath);
		}
		//TODO implement these files
		/*else if (fileExtension.matches(GROMACS_FILE_EXT_GRO)){
			file = new GROMACSGroFile(localPath);
		}
		else if (fileExtension.matches(GROMACS_FILE_EXT_NDX)){
			file = new DefaultLocalFileImpl(localPath,LocalFile.FORMAT_GROMACS_NDX, LocalFile.TYPE_CHEMICAL);
		}*/
		
		return file;
	}
	
	/**
	 * Instantiate NAMD file
	 * @param localPath Path to local file
	 * @return Local NAMD file reference
	 * @throws Exception 
	 */
	public LocalFile getNamdFile(String localPath, String fileExtension) throws Exception
	{
		LocalFile file = null;
		fileExtension = fileExtension.toLowerCase();
		
		if (fileExtension.matches(PSF_FILE_EXT)){
			file = new ProteinStructureFile(localPath);
		}
		else if (fileExtension.matches(AMBER_FILE_EXT_PRMTOP)){
			file = new AmberParameterTopologyFile(localPath);
		}
		else if (fileExtension.matches(DCD_FILE_EXT)){
			file = new DCDTrajectoryFile(localPath);
		}
		else if (fileExtension.matches(NAMD_FILE_EXT_CONF)){
			file = new NAMDConfigurationFile(localPath);
		}
		else if (fileExtension.matches(NAMD_FILE_EXT_LOG)){
			file = new NAMDStandardOutputFile(localPath);
		}
		
		return file;
	}
	
	/**
	 * Instantiate NWChem file
	 * @param localPath Path to local file
	 * @return Local NWChem file reference
	 * @throws Exception 
	 */
	public LocalFile getNWChemFile(String localPath, String fileExtension) throws Exception
	{
		LocalFile file = null;
		fileExtension = fileExtension.toLowerCase();
		
		if (fileExtension.matches(NWCHEM_FILE_EXT_INPUT)){
			file = new NWChemInputFile(localPath);
		}
		else if (fileExtension.matches(NWCHEM_FILE_EXT_OUTPUT)){
			file = new NWChemOutputFile(localPath);
		}
		//TODO implement these files
		/*
		else if (fileExtension.matches(NWCHEM_FILE_EXT_TOPOLOGY)){
			file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_NWCHEM_TOPOLOGY,LocalFile.TYPE_CHEMICAL);
		}
		else if (fileExtension.matches(NWCHEM_FILE_EXT_RESTART)){
			file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_NWCHEM_RESTART,LocalFile.TYPE_CHEMICAL);
		}*/
		return file;
	}
	
	/**
	 * Instantiate Gaussian file
	 * @param localPath Path to local file
	 * @return Local Gasussian file reference
	 * @throws Exception 
	 */
	public LocalFile getGaussianFile(String localPath, String fileExtension) throws Exception
	{
		LocalFile file = null;
		fileExtension = fileExtension.toLowerCase();
		
		if (fileExtension.matches(GAUSSIAN_FILE_EXT_CHK)){
			file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_GAUSSIAN_CHK, LocalFile.TYPE_CHEMICAL);
		}
		else if (fileExtension.matches(GAUSSIAN_FILE_EXT_INPUT)){
			file = new GaussianInputFile(localPath);
		} 
		else if (fileExtension.matches(GAUSSIAN_FILE_EXT_OUTPUT)){
			file = new GaussianOutputFile(localPath);
		}
		//TODO implement these files
		/*
		else if (fileExtension.matches(GAUSSIAN_FILE_EXT_RESTART)){
			file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_GAUSSIAN_RESTART, LocalFile.TYPE_CHEMICAL);
		}
		else if (fileExtension.matches(GAUSSIAN_FILE_EXT_CUBE)){
			file = new DefaultLocalFileImpl(localPath, LocalFile.FORMAT_GAUSSIAN_CUBE, LocalFile.TYPE_CHEMICAL);
		}*/
		
		return file;
	}
	
	/**
	 * Instantiate GAMESS file
	 * @param localPath Path to local file
	 * @return Local GAMESS file reference
	 * @throws Exception 
	 */
	public LocalFile getGamessFile(String localPath, String fileExtension) throws Exception
	{
		LocalFile file = null;
		fileExtension = fileExtension.toLowerCase();
		
		if (fileExtension.matches(GAMESS_FILE_EXT_INPUT)){
			file = new GAMESSInputFile(localPath);
		} 
		else if (fileExtension.matches(GAMESS_FILE_EXT_OUTPUT)){
			file = new GAMESSOutputFile(localPath);
		}
		return file;
	}

	/**
	 * Get file format (from extension)
	 * @param filename File name
	 * @return File format
	 */
	public static String getFileExtension(String filename)
	{
		String fileformat = LocalFile.FORMAT_UNKNOWN;
		int index = filename.lastIndexOf('.');
		if (index>-1){
			String fileExtension = filename.substring(index+1);
			//check that file extension is not a number (files series) or compression
			if (fileExtension.matches("\\d+") || fileExtension.matches(COMPRESSED_FILE_EXT))
			{
				filename = filename.substring(0, index);
				index = filename.lastIndexOf('.');
				if (index>-1)
				{
					fileExtension = filename.substring(index+1);
					//check that file extension is not a number (files series) or compression
					if (fileExtension.matches("\\d+") || fileExtension.matches(COMPRESSED_FILE_EXT))
					{
						filename = filename.substring(0, index);
						index = filename.lastIndexOf('.');
						if (index>-1){
							return filename.substring(index+1).toUpperCase();
						}
						else return fileformat;
					}
					else return fileExtension.toUpperCase();
				}
				else return fileformat;
			}
			else return fileExtension.toUpperCase();
		}
		return fileformat;
   }
	
	/**
	 * Guess file format based on file extension only
	 * @param fileName File name
	 * @return File format
	 * @throws Exception
	 */
	public String getFileFormatFromExtension(String fileName) throws Exception
	{
		String fileExtension = null;
		
		//get file format from extension
		fileExtension = getFileExtension(fileName);
		fileExtension = fileExtension.toLowerCase();
		
		//check image formats
		if (fileExtension.matches(JPG_FILE_EXT)){
			return LocalFile.FORMAT_JPEG;
		}
		else if (fileExtension.matches(PNG_FILE_EXT)){
			return LocalFile.FORMAT_PNG;
		}
		else if (fileExtension.matches(GIF_FILE_EXT)){
			return LocalFile.FORMAT_GIF;
		} 
		else if (fileExtension.matches(TIF_FILE_EXT)){
			return LocalFile.FORMAT_TIF;
		} 
		else if (fileExtension.matches(BMP_FILE_EXT)){
			return LocalFile.FORMAT_BMP;
		} 
		
		//check common chemical file formats
		else if(fileExtension.matches(SDF_FILE_EXT)){
			return LocalFile.FORMAT_SDF;
		}
		else if(fileExtension.matches(MOL2_FILE_EXT)){
			return LocalFile.FORMAT_MOL2;
		}
		else if(fileExtension.matches(PDB_FILE_EXT)){
			return LocalFile.FORMAT_PDB;
		}
		
		//check other common file formats
		else if (fileExtension.matches(CSV_FILE_EXT)){
			return LocalFile.FORMAT_CSV;
		}
		else if(fileExtension.matches(TCL_FILE_EXT)){
			return LocalFile.FORMAT_TCL_SCRIPT;
		}
		else if(fileExtension.matches(TXT_FILE_EXT)){
			return LocalFile.FORMAT_TXT;
		}
		else if(fileExtension.matches(PDF_FILE_EXT)){
			return LocalFile.FORMAT_PDF;
		}
		else if(fileExtension.matches(GNUPLOT_FILE_EXT)){
			return LocalFile.FORMAT_GNUPLOT;
		}
		else {
			return LocalFile.FORMAT_UNKNOWN;
		}
	}
}
