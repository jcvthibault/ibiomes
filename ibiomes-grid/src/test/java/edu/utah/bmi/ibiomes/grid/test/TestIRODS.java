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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.IRODSSession;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.packinstr.TransferOptions;
import org.irods.jargon.core.packinstr.TransferOptions.ForceOption;
import org.irods.jargon.core.protovalues.UserTypeEnum;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSFileSystem;
import org.irods.jargon.core.pub.ResourceAO;
import org.irods.jargon.core.pub.UserAO;
import org.irods.jargon.core.pub.domain.DataObject;
import org.irods.jargon.core.pub.domain.Resource;
import org.irods.jargon.core.pub.domain.User;
import org.irods.jargon.core.pub.domain.UserFilePermission;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.transfer.DefaultTransferControlBlock;
import org.irods.jargon.core.transfer.TransferControlBlock;
import org.junit.Test;

import edu.utah.bmi.ibiomes.grid.test.common.TestCommon;
import edu.utah.bmi.ibiomes.grid.test.common.TestIRODSConnector;
import edu.utah.bmi.ibiomes.security.IRODSConnector;

/**
 * Test suite for basic access to iRODS system
 * @author Julien Thibault, University of Utah
 *
 */
public class TestIRODS {

	private static Logger logger = Logger.getLogger(TestIRODS.class);
	
	/**
	 * 
	 * @throws JargonException
	 * @throws JargonQueryException
	 * @throws IOException
	 */
	@Test
	public void testIRODSAccess() throws JargonException, JargonQueryException, IOException
	{
		String localFilePath1 = TestCommon.getProperty(TestCommon.PROP_LOCAL_FILE_1);
		String irodsFilePath1 = TestCommon.getProperty(TestCommon.PROP_IRODS_FILE_1);

		String localFilePath2 = TestCommon.getProperty(TestCommon.PROP_LOCAL_FILE_2);
		String irodsFilePath2 = TestCommon.getProperty(TestCommon.PROP_IRODS_FILE_2);

		String irodsCollectionPath1 = TestCommon.getProperty(TestCommon.PROP_IRODS_COLL1);
		String irodsCollectionPath2 = TestCommon.getProperty(TestCommon.PROP_IRODS_COLL2);
		
		testIRODSAccess1(0, localFilePath1, irodsFilePath1, irodsCollectionPath1);
		testIRODSAccess2(1, irodsFilePath1, irodsCollectionPath1);
		testIRODSClean(0, irodsFilePath1, irodsCollectionPath1);
	}
	
	/**
	 * 
	 * @param userId
	 * @param irodsFilePath
	 * @param irodsCollectionPath
	 * @throws IOException
	 * @throws JargonException
	 */
	private void testIRODSClean(int userId, String irodsFilePath, String irodsCollectionPath) throws IOException, JargonException {
		//connect to iRODS system
		IRODSConnector cnx = TestIRODSConnector.getTestConnector(userId);
		IRODSFileSystem fs = cnx.getFileSystem();
		IRODSAccount irodsAccount = cnx.getAccount();
		IRODSSession irodsSession  = fs.getIrodsSession();
		IRODSAccessObjectFactory aoFactory = fs.getIRODSAccessObjectFactory();
		//delete file
		IRODSFileFactory fileFactory = fs.getIRODSFileFactory(irodsAccount);
		IRODSFile irodsFile = fileFactory.instanceIRODSFile(irodsFilePath);
		System.out.println("Deleting iRODS file "+irodsFilePath+"...");
		boolean success = irodsFile.delete();
		if (!success){
			throw new IOException("User '"+irodsAccount.getUserName()+"' could not delete file "+ irodsFilePath);
		}
		//delete directory
		System.out.println("Deleting iRODS collection "+irodsCollectionPath+"...");
		CollectionAO cAO = aoFactory.getCollectionAO(irodsAccount);
		IRODSFile irodsCollection = cAO.instanceIRODSFileForCollectionPath(irodsCollectionPath);
		success = irodsCollection.delete();
		if (!success){
			throw new IOException("User '"+irodsAccount.getUserName()+"' could not delete directory "+ irodsCollectionPath);
		}
		//	Close session
		irodsSession.closeSession();
		
	}

	/**
	 * 
	 * @param userId
	 * @param localFilePath
	 * @param irodsFilePath
	 * @param collectionPath
	 * @throws JargonException
	 * @throws JargonQueryException
	 * @throws IOException
	 */
	private void testIRODSAccess1(
			int userId, 
			String localFilePath,
			String irodsFilePath, 
			String collectionPath) throws JargonException, JargonQueryException, IOException
	{		
		//connect to iRODS system
		IRODSConnector cnx = TestIRODSConnector.getTestConnector(userId);
		IRODSFileSystem fs = cnx.getFileSystem();
		IRODSAccount irodsAccount = cnx.getAccount();
		IRODSSession irodsSession  = fs.getIrodsSession();
		IRODSAccessObjectFactory aoFactory = fs.getIRODSAccessObjectFactory();

		// ===========================================================
		// Find users
		// ===========================================================
		UserAO userAO = aoFactory.getUserAO(irodsAccount);
		
		List<User> users = userAO.findAll();
		System.out.println("User '"+irodsAccount.getUserName()+"' listing all users...");
		for (User user : users){
			logger.debug(user.getName() + " [" + user.getUserType().getTextValue() + "]");
		}
		
		List<User> admins = userAO.findWhere("USER_GROUP_NAME like '"+ UserTypeEnum.RODS_ADMIN.getTextValue() +"'");
		System.out.println("User '"+irodsAccount.getUserName()+"' listing admin users...");
		for (User user : admins){
			logger.debug(user.getName() + " [" + user.getUserType().getTextValue() + "]");
		}

		// ===========================================================
		// Find resources
		// ===========================================================
		ResourceAO resAO = aoFactory.getResourceAO(irodsAccount);
		List<Resource> resources = resAO.findAll();
		System.out.println("User '"+irodsAccount.getUserName()+"' listing resources...");
		for (Resource resource : resources){
			logger.debug(resource.getName() + ": " + resource.getType());
		}
				
		// ===========================================================
		// Operations on file collections and data objects (directories)
		// ===========================================================
		CollectionAO cAO = aoFactory.getCollectionAO(irodsAccount);
		IRODSFile irodsCollection = cAO.instanceIRODSFileForCollectionPath(collectionPath);
		
		//create directory
		System.out.println("User '"+irodsAccount.getUserName()+"' creating directory "+collectionPath+"...");
		boolean success = irodsCollection.mkdir();
		if (!success){
			//throw new IOException("User '"+irodsAccount.getUserName()+"' could not create directory "+ collectionPath);
		}
		//retrieve iRODS collection info
		System.out.println("User '"+irodsAccount.getUserName()+"' retrieving iRODS collection "+collectionPath+"...");
		System.out.println("\tPath: "+irodsCollection.getCanonicalPath());
		System.out.println("\tResource: "+irodsCollection.getResource());
		System.out.println("\tParent: "+irodsCollection.getParent());
		//System.out.println("\tReadable: "+irodsCollection.canRead());
		//System.out.println("\tWritable: "+irodsCollection.canWrite());
				
		//list permissions
		List<UserFilePermission> permissions = cAO.listPermissionsForCollection(collectionPath);
		System.out.println("User '"+irodsAccount.getUserName()+"' listing permissions for "+collectionPath+"...");
		for (UserFilePermission p : permissions){
			logger.debug(p);
		}
		
		//prepare local file and destination for upload in iRODS
		IRODSFileFactory fileFactory = fs.getIRODSFileFactory(irodsAccount);
		IRODSFile irodsFile = fileFactory.instanceIRODSFile(irodsFilePath);
		File locaFile = new File(localFilePath);
	
		//upload file
		System.out.println("User '"+irodsAccount.getUserName()+"' uploading file "+localFilePath+" to "+irodsFilePath+"...");
		DataObjectAO data = aoFactory.getDataObjectAO(irodsAccount);
		DataTransferOperations dataTransfer = aoFactory.getDataTransferOperations(irodsAccount);
		TransferOptions transferOptions = new TransferOptions();
		transferOptions.setForceOption(ForceOption.USE_FORCE);
		TransferControlBlock transferCtrl = DefaultTransferControlBlock.instance();
		transferCtrl.setTransferOptions(transferOptions);
		dataTransfer.putOperation(locaFile, irodsFile, null, transferCtrl);
		
		//retrieve iRODS file info
		System.out.println("User '"+irodsAccount.getUserName()+"' retrieving iRODS file "+irodsFilePath+"...");
		irodsFile = fileFactory.instanceIRODSFile(irodsFilePath);
		System.out.println("\tPath: "+irodsFile.getCanonicalPath());
		System.out.println("\tSize: "+irodsFile.length());
		System.out.println("\tResource: "+irodsFile.getResource());
		System.out.println("\tParent: "+irodsFile.getParent());
		System.out.println("\tReadable: "+irodsFile.canRead());
		System.out.println("\tWritable: "+irodsFile.canWrite());
		
		//retrieve iRODS file
		System.out.println("User '"+irodsAccount.getUserName()+"' retrieving iRODS data object "+irodsFilePath+"...");
		DataObject dataFile = data.findByAbsolutePath(irodsFilePath);
		
		/*System.out.println("\nMetadata:");
		List<MetaDataAndDomainData> metadata = data.findMetadataValuesForDataObject(irodsFile);
		for (MetaDataAndDomainData m : metadata){
			System.out.println("\t" + m.getAvuAttribute() + " = " + m.getAvuValue().replaceAll("\\n", " "));
		}*/

		//	Close session
		irodsSession.closeSession();
	}
	
	/**
	 * 
	 * @param userId
	 * @param localFilePath
	 * @param irodsFilePath
	 * @throws JargonException
	 * @throws JargonQueryException
	 * @throws IOException
	 */
	private void testIRODSAccess2(
			int userId, 
			String irodsFilePath, 
			String collectionPath) throws JargonException, JargonQueryException, IOException
	{		
		//connect to iRODS system
		IRODSConnector cnx = TestIRODSConnector.getTestConnector(userId);
		IRODSFileSystem fs = cnx.getFileSystem();
		IRODSAccount irodsAccount = cnx.getAccount();
		IRODSSession irodsSession  = fs.getIrodsSession();
		IRODSAccessObjectFactory aoFactory = fs.getIRODSAccessObjectFactory();
				
		// ===========================================================
		// Operations on file collections and data objects (directories)
		// ===========================================================
		CollectionAO cAO = aoFactory.getCollectionAO(irodsAccount);
		IRODSFile irodsCollection = cAO.instanceIRODSFileForCollectionPath(collectionPath);
				
		//list permissions
		List<UserFilePermission> permissions = cAO.listPermissionsForCollection(collectionPath);
		System.out.println("User '"+irodsAccount.getUserName()+"' listing permissions for "+collectionPath+"...");
		for (UserFilePermission p : permissions){
			logger.debug(p);
		}
			
		//retrieve iRODS file info
		IRODSFileFactory fileFactory = aoFactory.getIRODSFileFactory(irodsAccount);
		System.out.println("User '"+irodsAccount.getUserName()+"' retrieving iRODS file "+irodsFilePath+"...");
		IRODSFile irodsFile = fileFactory.instanceIRODSFile(irodsFilePath);
		System.out.println("\tPath: "+irodsFile.getCanonicalPath());
		System.out.println("\tSize: "+irodsFile.length());
		System.out.println("\tResource: "+irodsFile.getResource());
		System.out.println("\tParent: "+irodsFile.getParent());
		System.out.println("\tReadable: "+irodsFile.canRead());
		System.out.println("\tWritable: "+irodsFile.canWrite());
		
		//retrieve iRODS file
		System.out.println("User '"+irodsAccount.getUserName()+"' retrieving iRODS data object "+irodsFilePath+"...");
		DataObjectAO data = aoFactory.getDataObjectAO(irodsAccount);
		DataObject dataFile = data.findByAbsolutePath(irodsFilePath);
		
		/*System.out.println("\nMetadata:");
		List<MetaDataAndDomainData> metadata = data.findMetadataValuesForDataObject(irodsFile);
		for (MetaDataAndDomainData m : metadata){
			System.out.println("\t" + m.getAvuAttribute() + " = " + m.getAvuValue().replaceAll("\\n", " "));
		}*/

		//	Close session
		irodsSession.closeSession();
	}
}

