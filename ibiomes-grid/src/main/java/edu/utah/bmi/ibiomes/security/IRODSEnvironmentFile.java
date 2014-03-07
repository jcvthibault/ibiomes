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

package edu.utah.bmi.ibiomes.security;

import java.io.File;
import java.io.IOException;

import org.apache.commons.compress.compressors.CompressorException;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;

/**
 * iRODS environment file (contains user info and default iRODS connection parameters)
 * @author Julien Thibault, University of Utah
 *
 */
public class IRODSEnvironmentFile 
{
	private static IRODSEnvironmentFile envFile = null;
	
	private static final String defaultPath = System.getenv("HOME") + "/.irods/.irodsEnv";
	private static final String defaultPath2 = System.getenv("IBIOMES_HOME") + "/.irodsEnv";
	
	private static final String PARAM_USER_NAME = "irodsUserName";
	private static final String PARAM_PORT 		= "irodsPort";
	private static final String PARAM_HOST 		= "irodsHost";
	private static final String PARAM_HOME_DIR 	= "irodsHome";
	private static final String PARAM_CURR_DIR 	= "irodsCwd";
	private static final String PARAM_ZONE 		= "irodsZone";
	private static final String PARAM_DEF_RESC 	= "irodsDefResource";
	
	private static final String COMMENT_CHAR = "#";
	
	private String irodsUserName;
	private int irodsPort;
	private String irodsHost;
	private String irodsHome;
	private String irodsCwd;
	private String irodsZone;
	private String irodsDefResource = "";
	
	/**
	 * Constructor
	 * @param path Path to the iRODS environment file
	 * @throws IOException 
	 * @throws CompressorException 
	 */
	private IRODSEnvironmentFile(String path) throws IOException, CompressorException
	{
		IBIOMESFileReader br =  new IBIOMESFileReader(new File(path));
	    String line = null;
	    
        while (( line = br.readLine()) != null)
        {
        	line = line.trim();
        	if (line.length()>0 && !line.startsWith(COMMENT_CHAR))
        	{
        		String[] values = line.split("\\s+");
        		if (values.length==2)
        		{
	        		String attr = values[0];
	        		String value = values[1].replaceAll("'", "");
	        		
	        		if (attr.equals(PARAM_USER_NAME)) {
	        			irodsUserName = value;
	        		}
	        		else if (attr.equals(PARAM_CURR_DIR)) {
	        			irodsCwd = value;
	        		}
	        		else if (attr.equals(PARAM_DEF_RESC)) {
	        			irodsDefResource = value;
	        		}
	        		else if (attr.equals(PARAM_HOME_DIR)) {
	        			irodsHome = value;
	        		}
	        		else if (attr.equals(PARAM_HOST)) {
	        			irodsHost = value;
	        		}
	        		else if (attr.equals(PARAM_PORT)) {
	        			irodsPort = Integer.parseInt(value);
	        		}
	        		else if (attr.equals(PARAM_ZONE)) {
	        			irodsZone = value;
	        		}
        		}
        	}
        }
        br.close();
	}
	
	/**
	 * Load IRODS environment file
	 * @return IRODS environment file
	 * @throws IOException 
	 * @throws CompressorException 
	 */
	public static IRODSEnvironmentFile instance() throws IOException, CompressorException{
		if (envFile == null){
			try{
				envFile = new IRODSEnvironmentFile(defaultPath);
			} catch (IOException ioe) {
				envFile = new IRODSEnvironmentFile(defaultPath2);
			}
		}
		return envFile;
	}
	
	/**
	 * Load IRODS environment file
	 * @param filePath Path to the IRODS environment file
	 * @return IRODS environment file
	 * @throws IOException 
	 * @throws CompressorException 
	 */
	public static IRODSEnvironmentFile instance(String filePath) throws IOException, CompressorException{
		if (envFile == null){
			envFile = new IRODSEnvironmentFile(filePath);
		}
		return envFile;
	}

	/**
	 * Get iRODS username
	 * @return iRODS username
	 */
	public String getIrodsUserName() {
		return irodsUserName;
	}
	/**
	 * Sert iRODS username
	 * @param irodsUserName iRODS username
	 */
	public void setIrodsUserName(String irodsUserName) {
		this.irodsUserName = irodsUserName;
	}
	/**
	 * Get iRODS server port
	 * @return iRODS server port
	 */
	public int getIrodsPort() {
		return irodsPort;
	}
	/**
	 * Set iRODS server port
	 * @param irodsPort iRODS server port
	 */
	public void setIrodsPort(int irodsPort) {
		this.irodsPort = irodsPort;
	}
	/**
	 * Get iRODS host
	 * @return iRODS host
	 */
	public String getIrodsHost() {
		return irodsHost;
	}
	/**
	 * Set iRODS host
	 * @param irodsHost iRODS host
	 */
	public void setIrodsHost(String irodsHost) {
		this.irodsHost = irodsHost;
	}
	/**
	 * Get iRODS home directory for current user
	 * @return iRODS home directory for current user
	 */
	public String getIrodsHome() {
		return irodsHome;
	}
	/**
	 * Set iRODS home directory for current user
	 * @param irodsHome iRODS home directory for current user
	 */
	public void setIrodsHome(String irodsHome) {
		this.irodsHome = irodsHome;
	}
	/**
	 * Get iRODS working directory for current user
	 * @return iRODS working directory for current user
	 */
	public String getIrodsCwd() {
		return irodsCwd;
	}
	/**
	 * Set iRODS working directory for current user
	 * @param irodsCwd iRODS working directory for current user
	 */
	public void setIrodsCwd(String irodsCwd) {
		this.irodsCwd = irodsCwd;
	}
	/**
	 * Get iRODS zone
	 * @return iRODS zone 
	 */
	public String getIrodsZone() {
		return irodsZone;
	}
	/**
	 * Set iRODS zone
	 * @param irodsZone iRODS zone
	 */
	public void setIrodsZone(String irodsZone) {
		this.irodsZone = irodsZone;
	}
	/**
	 * Get iRODS default resource
	 * @return iRODS default resource
	 */
	public String getIrodsDefResource() {
		return irodsDefResource;
	}
	/**
	 * Set iRODS default resource
	 * @param irodsDefResource iRODS default resource
	 */
	public void setIrodsDefResource(String irodsDefResource) {
		this.irodsDefResource = irodsDefResource;
	}
}
