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

package edu.utah.bmi.ibiomes.parse.chem.amber;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;
import edu.utah.bmi.ibiomes.metadata.TrajectoryMetadata;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractTrajectoryFile;
import ucar.nc2.NetcdfFile;

/**
 * AMBER NetCDF file handler (trajectories)
 * @author Julien Thibault, University of Utah
 *
 */
public class AmberNetcdfFile extends AbstractTrajectoryFile
{
	private static final long serialVersionUID = 5915963358867528302L;
	private static final String NETCDF_MAGIC_NUMBER = "CDF";
	private int nSteps = 0;
	private int nAtoms = 0;
	private int spatialDimensions = 0;
	private String applicationName;
	private Software software;
	
	/**
	 * Create new reference to NetCDF file
	 * @param localPath Path to local file
	 * @throws Exception
	 */
	public AmberNetcdfFile(String localPath) throws Exception 
	{	
		super(localPath, LocalFile.FORMAT_AMBER_TRAJ_NETCDF);
		//check format
		boolean isNetCDF = checkFormat(localPath);
		if (!isNetCDF)
			this.format = LocalFile.FORMAT_UNKNOWN;
	}

	/**
	 * Create new reference to NetCDF file and parse header if specified
	 * @param localPath Path to local file
	 * @param parse Parse flag
	 * @throws Exception
	 */
	public AmberNetcdfFile(String localPath, boolean parse) throws Exception 
	{
		super(localPath, LocalFile.FORMAT_AMBER_TRAJ_NETCDF);
		
		boolean isNetCDF = checkFormat(localPath);
		if (!isNetCDF)
			this.format = LocalFile.FORMAT_UNKNOWN;
		
		if (parse)
			this.parse();
	}
	
	/**
	 * Parse NetCDF file
	 */
	private void parse(){
		try{
			//open NetCDF file to read metadata only
			NetcdfFile netcdffile = NetcdfFile.open(this.getAbsolutePath());
			
			//dimensions
			List<Dimension> dims = netcdffile.getDimensions();
			for (Dimension dim : dims)
			{
				if (dim.getName().toLowerCase().equals("spatial")){
					this.spatialDimensions = dim.getLength();
				}
				else if (dim.getName().toLowerCase().equals("atom")){
					this.nAtoms = dim.getLength();
				}
				else if (dim.getName().toLowerCase().equals("frame")){
					this.nSteps = dim.getLength();
				}
			}
			
			/*//variables
			List<Variable> vars = netcdffile.getVariables();
			for (Variable var : vars){
				
			}*/
			
			//attributes
			List<Attribute> globalAtributes = netcdffile.getGlobalAttributes();
			for (Attribute attr : globalAtributes)
			{
				if (attr.getName().toLowerCase().equals("title")){
					this.description = attr.getStringValue();
				}
				else if (attr.getName().toLowerCase().equals("application")){
					this.software = new Software(attr.getStringValue());
				}
				else if (attr.getName().toLowerCase().equals("program")){
	
				}
				else if (attr.getName().toLowerCase().equals("programversion")){
					if (this.software!=null)
						this.software.setVersion(attr.getStringValue());
				}
			}
					
			//close file
			netcdffile.close();
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			e.printStackTrace();
		}
	}

	/**
	 * Get the number of frames stored in this file
	 */
	public int getNumberOfFrames() {
		return nSteps;
	}

	public int getNumberOfAtoms() {
		return nAtoms;
	}

	public int getSpatialDimensions() {
		return spatialDimensions;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public static boolean checkFormat(String path) {
		IBIOMESFileReader fileReader = null;
		try {
			File file = new File(path);
			fileReader = new IBIOMESFileReader(file);
			String hex = fileReader.readFirstBytesDecompressed(4);
			return (hex.startsWith(NETCDF_MAGIC_NUMBER));
			
		} catch (Exception e) {
			if (fileReader!=null)
				try {
					fileReader.close();
				} catch (IOException e1) {
				}
			return false;
		}
	}
	
	@Override
	public MetadataAVUList getMetadata() throws Exception
	{
		MetadataAVUList metadata = super.getMetadata();
		
		metadata.add(new MetadataAVU(TopologyMetadata.SPATIAL_DIMENSIONS, String.valueOf(spatialDimensions)));
		metadata.add(new MetadataAVU(TopologyMetadata.COUNT_ATOMS, String.valueOf(nAtoms)));
		metadata.add(new MetadataAVU(TrajectoryMetadata.TIME_STEP_COUNT, String.valueOf(nSteps)));
		if (this.software!=null)
			metadata.addAll(this.software.getMetadata());
		
		return metadata;
	}
}
