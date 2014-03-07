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

package edu.utah.bmi.ibiomes.cli;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.compress.compressors.CompressorException;
import org.irods.jargon.core.exception.AuthenticationException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;

import edu.utah.bmi.ibiomes.security.IRODSConnector;
import edu.utah.bmi.ibiomes.security.IRODSEnvironmentFile;

/**
 * Utility functions for iBIOMES command-line interface
 * @author Julien Thibault
 *
 */
public class CLIUtils {

	
	/**
	 * Connect to iBIOMES
	 * @return Connection
	 * @throws IOException
	 * @throws CompressorException 
	 */
	public static IRODSConnector getConnection() throws IOException, JargonException, CompressorException{	
		return getConnection(null);
	}

	/**
	 * Connect to iBIOMES
	 * @param password Password
	 * @return Connection
	 * @throws IOException
	 * @throws CompressorException 
	 */
	public static IRODSConnector getConnection(String password) throws IOException, JargonException, CompressorException{	
		return getConnection(null, password, null);
	}
	
	/**
	 * Connect to iBIOMES
	 * @param password Password
	 * @return Connection
	 * @throws IOException
	 * @throws CompressorException 
	 */
	public static IRODSConnector getConnection(String password, String server) throws IOException, JargonException, CompressorException{	
		return getConnection(null, password, server);
	}
	
	/**
	 * Connect to iBIOMES
	 * @param password Password
	 * @return Connection
	 * @throws IOException
	 * @throws CompressorException 
	 */
		
	public static IRODSConnector getConnection(String username, String password, String server) throws IOException, JargonException, CompressorException
	{	
		IRODSEnvironmentFile envFile = null;
		IRODSConnector cnx = null;
		
		Console objConsole = System.console();
		if (objConsole == null) {
            System.err.println("Console Object is not available.");
            System.exit(1);
        }
		
		System.out.println("Authentication... ");
		try{
			envFile = IRODSEnvironmentFile.instance();
		} catch (IOException e){
			System.out.println("IRODS user info file not found!");
			System.out.println("Make sure your .irodsEnv file exists in $HOME/.irods or $IBIOMES_HOME");
			System.exit(1);
		}
		
		//while login fails
		while (cnx == null)
		{
			//retrieve iRODS password
			if (password == null)
			{	
				if (username == null)
					username = envFile.getIrodsUserName();
				System.out.println("iBIOMES password for "+ username +":");
				char[] pwdArray = objConsole.readPassword();
				password = String.copyValueOf(pwdArray);
			}
			
			//try to connect to iRODS system using default settings
			try {
				cnx = new IRODSConnector(username, password, server);
				try{
					//try to login
					IRODSAccessObjectFactory aoFactory = cnx.getFileSystem().getIRODSAccessObjectFactory();
					aoFactory.getUserAO(cnx.getAccount());
				}
				catch (JargonException exc)
				{
					System.out.println("\nLogin failed. Try again.");
					cnx = null;
				}
			}
			catch (IOException ioe)
			{
				System.out.println("\nCould not find login info...");
				cnx = null;
			}
			catch (AuthenticationException ae)
			{
				System.out.println("\nLogin failed. Try again.");
				cnx = null;
			}
			password = null;
		}
		System.out.println("Authentication successful!");
		return cnx;
	}
	
	/**
	 * Connect to iRODS using parameters given in command-line interface.
	 * @return Connection object
	 * @throws IOException
	 * @throws JargonException
	 */
	public static IRODSConnector getConnectionFromCLI() throws IOException, JargonException
	{	
		Console objConsole = System.console();
		if (objConsole == null) {
            System.err.println("Console Object is not available.");
            System.exit(1);
        }
				
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		System.out.println("Host:");
		String host = in.readLine();
		
		System.out.println("Port [1247]:");
		String iPort = in.readLine();
		
		int port = 1247;
		if (iPort != null && iPort.length()>0)
			port = Integer.parseInt(iPort);
		
		System.out.println("Zone:");
		String zone = in.readLine();
		
		System.out.println("Default resource:");
		String server = in.readLine();
		
		System.out.println("Username:");
		String userName = in.readLine();
		
		//retrieve iRODS password
		System.out.println("Password:");
		char[] pwdArray = objConsole.readPassword();
		String password = String.copyValueOf(pwdArray);
			
		IRODSConnector cnx = new IRODSConnector(host, port, userName, password, "", zone, server);
		try{
			//try to login
			IRODSAccessObjectFactory aoFactory = cnx.getFileSystem().getIRODSAccessObjectFactory();
			aoFactory.getUserAO(cnx.getAccount());
			System.out.println("\nConnected! ("+ cnx.getAccount().toURI(false) + ")");
		}
		catch (JargonException exc)
		{
			System.out.println("Login info: "+ cnx.getAccount().getUserName() + "@" + cnx.getAccount().getHost() + ":"+ cnx.getAccount().getPort() +").");
			System.out.println("Login failed. Try again.");
			cnx = null;
		}
		return cnx;
	}
}
