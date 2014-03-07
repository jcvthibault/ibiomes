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

import java.io.IOException;

import org.apache.commons.compress.compressors.CompressorException;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.auth.AuthResponse;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSAccessObjectFactoryImpl;
import org.irods.jargon.core.pub.IRODSFileSystem;


/**
 * Connection to iRODS file system
 * @author Julien Thibault, University of Utah
 *
 */
public class IRODSConnector {

	private IRODSAccount account = null;
	private IRODSFileSystem fileSystem = null;
	
	/**
	 * Connect to iRODS file system using given parameters
	 * @param host the iRODS server domain name
	 * @param port the port on the iRODS server
	 * @param userName the user name
	 * @param password the password
	 * @param homeDirectory home directory on the iRODS virtual file system
	 * @param zone the IRODS zone
	 * @param defaultStorageResource default storage resource
	 * @throws IOException
	 * @throws JargonException 
	 */
	public IRODSConnector(
				String host,
				int port,
				String userName,
				String password,
				String homeDirectory,
				String zone,
				String defaultStorageResource) throws IOException, JargonException
	{
		this.fileSystem = IRODSFileSystem.instance();
		AuthResponse authResponse = null;
		if (defaultStorageResource==null)
			defaultStorageResource = "";
		IRODSAccount irodsAccount = IRODSAccount.instance(host, port, userName, password, homeDirectory, zone, defaultStorageResource);
		IRODSAccessObjectFactory accessAO = IRODSAccessObjectFactoryImpl.instance(this.fileSystem.getIrodsSession());
		authResponse = accessAO.authenticateIRODSAccount(irodsAccount);
		this.account = authResponse.getAuthenticatedIRODSAccount();
		
	}
	
	/**
	 * Connect to iRODS using environment file
	 * @param password Password
	 * @throws JargonException
	 * @throws IOException
	 * @throws CompressorException 
	 */
	public IRODSConnector(String password) throws JargonException, IOException, CompressorException
	{
		IRODSEnvironmentFile envFile = IRODSEnvironmentFile.instance();
		this.fileSystem = IRODSFileSystem.instance();
		AuthResponse authResponse = null;
		IRODSAccount irodsAccount = IRODSAccount.instance (
				envFile.getIrodsHost(), 
				envFile.getIrodsPort(), 
				envFile.getIrodsUserName(), 
				password, envFile.getIrodsHome(), 
				envFile.getIrodsZone(), 
				envFile.getIrodsDefResource());
		IRODSAccessObjectFactory accessAO = IRODSAccessObjectFactoryImpl.instance(this.fileSystem.getIrodsSession());
		authResponse = accessAO.authenticateIRODSAccount(irodsAccount);
		this.account = authResponse.getAuthenticatedIRODSAccount();
	}
	
	/**
	 * Connect to iRODS using environment file
	 * @param password Password
	 * @param server Server
	 * @throws JargonException
	 * @throws IOException
	 * @throws CompressorException 
	 */
	public IRODSConnector(String username, String password, String server) throws JargonException, IOException, CompressorException
	{
		IRODSEnvironmentFile envFile = IRODSEnvironmentFile.instance();
		if (server == null)
			server = envFile.getIrodsDefResource();
		this.fileSystem = IRODSFileSystem.instance();
		AuthResponse authResponse = null;
		if (username==null)
			username = envFile.getIrodsUserName();
		IRODSAccount irodsAccount = IRODSAccount.instance(
				envFile.getIrodsHost(), 
				envFile.getIrodsPort(), 
				username, 
				password, envFile.getIrodsHome(), 
				envFile.getIrodsZone(), 
				server);
		IRODSAccessObjectFactory accessAO = IRODSAccessObjectFactoryImpl.instance(this.fileSystem.getIrodsSession());
		authResponse = accessAO.authenticateIRODSAccount(irodsAccount);
		this.account = authResponse.getAuthenticatedIRODSAccount();
	}

	/**
	 * Connect to iRODS anonymously
	 * @param host Host
	 * @param port Port
	 * @param zone Zone
	 * @param defaultStorageResource Default storage resource
	 * @throws JargonException 
	 */
	public IRODSConnector(String host, int port, String homeDirectory, String zone, String defaultStorageResource) throws JargonException {
		if (defaultStorageResource==null)
			defaultStorageResource = "";
		IRODSAccount irodsAccount = IRODSAccount.instanceForAnonymous(host, port, homeDirectory, zone, defaultStorageResource);
		this.fileSystem = IRODSFileSystem.instance();
		AuthResponse authResponse = null;
		IRODSAccessObjectFactory accessAO = IRODSAccessObjectFactoryImpl.instance(this.fileSystem.getIrodsSession());
		authResponse = accessAO.authenticateIRODSAccount(irodsAccount);
		this.account = authResponse.getAuthenticatedIRODSAccount();
	}

	/**
	 * Get iRODS user account information
	 * @return reference to iRODS user account
	 */
	public IRODSAccount getAccount() {
		return this.account;
	}

	/**
	 * Get iRODS file system information
	 * @return reference to iRODS file system
	 */
	public IRODSFileSystem getFileSystem() {
		return this.fileSystem;
	}

}
