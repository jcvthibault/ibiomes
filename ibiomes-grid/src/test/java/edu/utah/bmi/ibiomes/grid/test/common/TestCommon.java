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

package edu.utah.bmi.ibiomes.grid.test.common;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Common variables and methods for the test suites 
 * @author Julien Thibault, University of Utah
 *
 */
public class TestCommon {
	
	public final static String TEST_DATA_DIR = System.getenv("IBIOMES_HOME") + "/test";
	public final static String TEST_IBIOMES_CONFIG_FILE = TEST_DATA_DIR + "/config/ibiomes-parser.properties";
	

	public final static String PROP_IRODS_ENV_FILE_PATH = "irodsEnvPath";
	
	public final static String PROP_IRODS_HOST 		= "irodsTestHost";
	public final static String PROP_IRODS_PORT 		= "irodsTestPort";
	public final static String PROP_IRODS_ZONE 		= "irodsTestZone";
	
	public final static String PROP_IRODS_TEST_USER1 		= "irodsTestUser1";
	public final static String PROP_IRODS_TEST_USER2 		= "irodsTestUser2";
	public final static String PROP_IRODS_TEST_USER_PWD1	= "irodsTestUserPwd1";
	public final static String PROP_IRODS_TEST_USER_PWD2	= "irodsTestUserPwd2";
	public static final String PROP_IRODS_TEST_USER_HOME1 	= "irodsTestUserHomeDir1";
	public static final String PROP_IRODS_TEST_USER_HOME2 	= "irodsTestUserHomeDir2";

	public final static String PROP_IRODS_COLL1 	= "irodsCollection1";
	public final static String PROP_IRODS_COLL2 	= "irodsCollection2";
	
	public static final String PROP_LOCAL_FILE_1 = "localFile1";
	public static final String PROP_IRODS_FILE_1 = "localFile1Destination";
	public static final String PROP_LOCAL_FILE_2 = "localFile2";
	public static final String PROP_IRODS_FILE_2 = "localFile2Destination";

	public static final String PROP_LOCAL_EXPERIMENT_1 = "localExperiment1";
	public static final String PROP_IRODS_EXPERIMENT_1 = "localExperiment1Destination";
	
	private static Properties props;
	
	private static void initProperties(){
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
			props = (Properties)context.getBean("testProperties");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String name) {
		if (props==null)
			initProperties();
        return props.getProperty(name);
    }
}
