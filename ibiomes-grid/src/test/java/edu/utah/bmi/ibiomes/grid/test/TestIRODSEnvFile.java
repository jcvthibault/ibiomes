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

package edu.utah.bmi.ibiomes.grid.test;

import java.io.IOException;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.grid.test.common.TestCommon;
import edu.utah.bmi.ibiomes.security.IRODSEnvironmentFile;

/**
 * Test suite for iRODS environment file parser
 * @author Julien Thibault, University of Utah
 *
 */
public class TestIRODSEnvFile {

	private static Logger logger = Logger.getLogger(TestIRODSEnvFile.class);
	
	public TestIRODSEnvFile(){
	}
	
	/*@Test
	public void TestIRODSEnvFileLoadFromDefaultLocation() throws IOException, CompressorException
	{
		System.out.println("Loading iRODS environment file from default path...");
		IRODSEnvironmentFile file = IRODSEnvironmentFile.instance();
		logger.debug("\tUsername: " + file.getIrodsUserName());
		logger.debug("\tHost: " + file.getIrodsHost());
		logger.debug("\tPort: " + file.getIrodsPort());
		logger.debug("\tHome directory: " + file.getIrodsHome());
		logger.debug("\tCurrent directory: " + file.getIrodsCwd());
		logger.debug("\tDefault resource: " + file.getIrodsDefResource());
	}*/
	
	@Test
	public void TestIRODSEnvFileLoadFromGivenLocation() throws IOException, CompressorException
	{
		String path = TestCommon.getProperty(TestCommon.PROP_IRODS_ENV_FILE_PATH);
		
		if (path!=null && path.length()>0){
			System.out.println("Loading iRODS environment file " + path + "...");
			IRODSEnvironmentFile file = IRODSEnvironmentFile.instance(path);
			logger.debug("\tUsername: " + file.getIrodsUserName());
			logger.debug("\tHost: " + file.getIrodsHost());
			logger.debug("\tPort: " + file.getIrodsPort());
			logger.debug("\tHome directory: " + file.getIrodsHome());
			logger.debug("\tCurrent directory: " + file.getIrodsCwd());
			logger.debug("\tDefault resource: " + file.getIrodsDefResource());
		}
		else 
			System.out.println("WARNING: Could not find parameter " + TestCommon.PROP_IRODS_ENV_FILE_PATH + 
					" in property file");
	}
}
