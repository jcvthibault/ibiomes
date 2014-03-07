package edu.utah.bmi.ibiomes.grid.test;

import java.io.IOException;

import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.UserAO;

import edu.utah.bmi.ibiomes.grid.test.common.TestIRODSConnector;
import edu.utah.bmi.ibiomes.security.IRODSConnector;

public class TestUpdatePassword {

	/**
	 * @param args
	 * @throws JargonException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, JargonException {
		
		String oldPwd = "";
		String newPwd = "";
		
		IRODSConnector cnx = TestIRODSConnector.getTestConnector();
		IRODSAccessObjectFactory irodsAccessObjectFactory = cnx.getFileSystem().getIRODSAccessObjectFactory();
		
		UserAO userAO = irodsAccessObjectFactory.getUserAO(cnx.getAccount());
		userAO.changeAUserPasswordByThatUser(cnx.getAccount().getUserName(), oldPwd, newPwd);

	}

}
