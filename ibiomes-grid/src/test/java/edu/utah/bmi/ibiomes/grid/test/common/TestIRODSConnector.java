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

import java.io.IOException;

import org.irods.jargon.core.exception.JargonException;

import edu.utah.bmi.ibiomes.security.IRODSConnector;

/**
 * Utility class to get iRODS connections (anonymous or not) for testing purpose
 * @author Julien Thibault, University of Utah
 *
 */
public class TestIRODSConnector {
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws IOException
	 * @throws JargonException
	 */
	public static IRODSConnector getTestConnector(int userId) throws IOException, JargonException {
		if (userId==0)
			return getTestConnector();
		else return getTestConnector2();
	}
		
	
	/**
	 * Get connection (for test purpose only)
	 * @throws IOException 
	 * @throws JargonException 
	 */
	public static IRODSConnector getTestConnector() throws IOException, JargonException {
		
		String host = TestCommon.getProperty(TestCommon.PROP_IRODS_HOST);
		int port = 1247;
		try{
			String portStr = TestCommon.getProperty(TestCommon.PROP_IRODS_PORT);
			if (portStr!=null && portStr.length()>0)
				port = Integer.parseInt(portStr);
		}
		catch(NumberFormatException e){
		}
		String homeDirectory = "";
		String zone = TestCommon.getProperty(TestCommon.PROP_IRODS_ZONE);
		String defaultStorageResource = "";
		String userName = TestCommon.getProperty(TestCommon.PROP_IRODS_TEST_USER1);
		String password = TestCommon.getProperty(TestCommon.PROP_IRODS_TEST_USER_PWD1);
		
		IRODSConnector cnx = new IRODSConnector(host, port, userName, password, homeDirectory, zone, defaultStorageResource);
		//System.out.println("[iRODS] Home directory for "+ cnx.getAccount().getUserName()+": " + cnx.getAccount().getHomeDirectory());
		
		return cnx;
	}
	
	/**
	 * Get connection (for test purpose only)
	 * @throws IOException 
	 * @throws JargonException 
	 */
	public static IRODSConnector getTestConnector2() throws IOException, JargonException {
		
		String host = TestCommon.getProperty(TestCommon.PROP_IRODS_HOST);
		int port = 1247;
		try{
			String portStr = TestCommon.getProperty(TestCommon.PROP_IRODS_PORT);
			if (portStr!=null && portStr.length()>0)
				port = Integer.parseInt(portStr);
		}
		catch(NumberFormatException e){
		}
		String homeDirectory = "";
		String zone = TestCommon.getProperty(TestCommon.PROP_IRODS_ZONE);
		String defaultStorageResource = "";
		String userName = TestCommon.getProperty(TestCommon.PROP_IRODS_TEST_USER2);
		String password = TestCommon.getProperty(TestCommon.PROP_IRODS_TEST_USER_PWD2);
		
		IRODSConnector cnx = new IRODSConnector(host, port, userName, password, homeDirectory, zone, defaultStorageResource);
		//System.out.println("[iRODS] Home directory for "+ cnx.getAccount().getUserName()+": " + cnx.getAccount().getHomeDirectory());
		
		return cnx;
	}
	/**
	 * Get connection (for test purpose only)
	 * @throws IOException 
	 * @throws JargonException 
	 */
	public static IRODSConnector getAnonymousAccess() throws IOException, JargonException {
		
		String host = TestCommon.getProperty(TestCommon.PROP_IRODS_HOST);
		int port = 1247;
		try{
			String portStr = TestCommon.getProperty(TestCommon.PROP_IRODS_PORT);
			if (portStr!=null && portStr.length()>0)
				port = Integer.parseInt(portStr);
		}
		catch(NumberFormatException e){
		}
		String homeDirectory = "";
		String zone = TestCommon.getProperty(TestCommon.PROP_IRODS_ZONE);
		String defaultStorageResource = "";
		
		IRODSConnector cnx = new IRODSConnector(host, port, homeDirectory, zone, defaultStorageResource);
		//System.out.println("[iRODS] Home directory for "+ cnx.getAccount().getUserName()+": " + cnx.getAccount().getHomeDirectory());
		
		return cnx;
	}
	
}
