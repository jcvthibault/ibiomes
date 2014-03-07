package edu.utah.bmi.ibiomes.grid.test;

import java.io.IOException;
import java.util.ArrayList;

import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;

import edu.utah.bmi.ibiomes.grid.test.common.TestIRODSConnector;
import edu.utah.bmi.ibiomes.security.AccessControl;
import edu.utah.bmi.ibiomes.security.AccessControlList;
import edu.utah.bmi.ibiomes.security.IRODSConnector;

public class ACLTest {
	
	public static void main(String[] args) throws IOException, JargonException
	{
		IRODSConnector cnx = TestIRODSConnector.getTestConnector();
		
		IRODSFileFactory factory = cnx.getFileSystem().getIRODSFileFactory(cnx.getAccount());
		IRODSFile file = factory.instanceIRODSFile("/ibiomesZone/home/jthibault/1BIV");
		AccessControlList acl = AccessControlList.getACL(file, cnx.getFileSystem().getIRODSAccessObjectFactory(), cnx.getAccount());
		ArrayList<AccessControl> acls = acl.getPermissions();
		
		System.out.println("");
		for (AccessControl a : acls){
			System.out.println(a.toString());
		}
		
		
		
		if (file.canRead()){
				//DataTransferOperations dataTransfer = irodsAccessObjectFactory.getDataTransferOperations(irodsAccount);
				//dataTransfer.getOperation(iFile, localFile, null, null);
				System.out.println("Can read!");
		}
		else {
			System.out.println("Cannot read!");
		}
		
	}
}
