package edu.utah.bmi.ibiomes.web.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.protovalues.FilePermissionEnum;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.utah.bmi.ibiomes.security.AccessControlList;


@Controller
@RequestMapping(value = "/services/permissions")
public class PermissionService {

	public static final String INHERIT = "INHERIT";
	public static final String NO_INHERIT = "NO_INHERIT";

	@Autowired(required = true)  
    private HttpServletRequest request;   
    public HttpServletRequest getRequest() {  
        return request;  
    }
    
    @Autowired(required = true)  
    private IRODSAccessObjectFactory irodsAccessObjectFactory;
	public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	}
    
	/**
	 * Retrieve ACL for a file or collection
	 * @param uri Collection path
	 * @return Collection ACL
	 * @throws Exception 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public  AccessControlList getPermissions(
			@RequestParam("uri")  String uri) throws Exception
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			AccessControlList acl = AccessControlList.getACL(uri, irodsAccessObjectFactory, irodsAccount);
			return acl;
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Update ACL
	 * @param uri
	 * @param aclUsers
	 * @param aclPermission
	 * @param aclRecursive
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	public  AccessControlList updatePermissions(
			@RequestParam("uri")  String uri,
			@RequestParam("users")  String aclUsersStr,
			@RequestParam("permission") String aclPermission,
			@RequestParam(value="recursive", required=false, defaultValue="y") String aclRecursive) 
					throws Exception
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			
			String[] aclUsers = aclUsersStr.split("\\,");
			
			IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			IRODSFile iFile = fileFactory.instanceIRODSFile(uri);
			
			// if its a collection
			if (iFile.isDirectory()){
			
				boolean recursive = false;
				if (aclRecursive != null && aclRecursive.matches("([Yy](es)?)|([Tt](rue)?)"))
					recursive = true;
				
				CollectionAO cAO = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
				
				if (aclUsers != null && aclUsers.length>0 && aclPermission != null && aclPermission.length()>0){
					
					if (aclPermission.equals(FilePermissionEnum.READ.toString())){
						for (int i=0; i < aclUsers.length; i++){
							cAO.setAccessPermissionRead(irodsAccount.getZone(), uri, aclUsers[i], recursive);
						}
					}
					else if (aclPermission.equals(FilePermissionEnum.WRITE.toString())){
						for (int i=0; i < aclUsers.length; i++){
							cAO.setAccessPermissionWrite(irodsAccount.getZone(), uri, aclUsers[i], recursive);
						}
					}
					else if (aclPermission.equals(FilePermissionEnum.OWN.toString())){
						for (int i=0; i < aclUsers.length; i++){
							cAO.setAccessPermissionOwn(irodsAccount.getZone(), uri, aclUsers[i], recursive);
						}
					}
					else if (aclPermission.equals(FilePermissionEnum.NONE.toString())){
						for (int i=0; i < aclUsers.length; i++){
							cAO.removeAccessPermissionForUser(irodsAccount.getZone(), uri, aclUsers[i], recursive);
						}
					}
				}
				
			}
			else // file ACL
			{
				DataObjectAO dAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
				
				if (aclUsers != null && aclUsers.length>0 && aclPermission != null && aclPermission.length()>0){
					
					if (aclPermission.equals(FilePermissionEnum.READ.name())){
						for (int i=0; i < aclUsers.length; i++){
							dAO.setAccessPermissionRead(null, uri, aclUsers[i]);
						}
					}
					else if (aclPermission.equals(FilePermissionEnum.WRITE.name())){
						for (int i=0; i < aclUsers.length; i++){
							dAO.setAccessPermissionWrite(null, uri, aclUsers[i]);
						}
					}
					else if (aclPermission.equals(FilePermissionEnum.OWN.name())){
						for (int i=0; i < aclUsers.length; i++){
							dAO.setAccessPermissionOwn(null, uri, aclUsers[i]);
						}
					}
					else if (aclPermission.equals(FilePermissionEnum.NONE.toString())){
						for (int i=0; i < aclUsers.length; i++){
							dAO.removeAccessPermissionsForUser(irodsAccount.getZone(), uri, aclUsers[i]);
						}
					}
				}
			}
			
			AccessControlList acl = AccessControlList.getACL(uri, irodsAccessObjectFactory, irodsAccount);
			
			return acl;
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Remove permissions for specified users
	 * @param uri
	 * @param aclUsers
	 * @param aclPermission
	 * @param aclRecursive
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	@ResponseBody
	public  AccessControlList removePermissions(
			@RequestParam("uri")  String uri,
			@RequestParam("users")  String aclUsersStr,
			@RequestParam(value="recursive", required=false, defaultValue="y") String aclRecursive) 
					throws Exception
	{
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			
			IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			IRODSFile iFile = fileFactory.instanceIRODSFile(uri);
			
			String[] aclUsers = aclUsersStr.split("\\,");
			
			// if its a collection
			if (iFile.isDirectory()){
				
				boolean recursive = false;
				if (aclRecursive != null && aclRecursive.matches("([Yy](es)?)|([Tt](rue)?)"))
					recursive = true;
				
				CollectionAO cAO = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
				
				if (aclUsers != null && aclUsers.length>0 ){
					for (int i=0; i < aclUsers.length; i++){
						cAO.removeAccessPermissionForUser(irodsAccount.getZone(), uri, aclUsers[i], recursive);
					}
				}
				
			}
			else // file ACL
			{
				DataObjectAO dAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
				
				if (aclUsers != null && aclUsers.length>0){
					for (int i=0; i < aclUsers.length; i++){
						dAO.removeAccessPermissionsForUser(irodsAccount.getZone(), uri, aclUsers[i]);
					}
				}
			}
			
			AccessControlList acl = AccessControlList.getACL(uri, irodsAccessObjectFactory, irodsAccount);
			
			return acl;
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Set ACL inherit flag
	 * @param uri
	 * @param aclPermission Inherit or not
	 * @param aclRecursive 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/setinherit", method = RequestMethod.GET)
	@ResponseBody
	public  boolean updatePermissionInherit(
			@RequestParam("uri")  String uri,
			@RequestParam("permission") String aclPermission,
			@RequestParam(value="recursive", required=false, defaultValue="y") String aclRecursive)
					throws Exception
	{		
		try{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			
			IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			IRODSFile iFile = fileFactory.instanceIRODSFile(uri);
			
			boolean recursive = false;
			if (aclRecursive != null && aclRecursive.matches("([Yy](es)?)|([Tt](rue)?)"))
				recursive = true;
			
			// if its a collection
			if (iFile.isDirectory()){
			
				CollectionAO cAO = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
			
				if (aclPermission != null && aclPermission.equals(INHERIT)){
					cAO.setAccessPermissionInherit(irodsAccount.getZone(), uri, recursive);
					
				}
				else if (aclPermission != null && aclPermission.equals(NO_INHERIT)){
					cAO.setAccessPermissionToNotInherit(irodsAccount.getZone(), uri, recursive);
				}
				
				//acl = AccessControlList.getACL(uri, irodsAccessObjectFactory, irodsAccount);
			}
			
			return aclPermission.equals(INHERIT);
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * Reset ACL  (leave only permissions for owners)
	 * @param uri
	 * @param aclUsers
	 * @param aclPermission
	 * @param aclRecursive
	 * @return
	 * @throws IOException
	 * @throws JargonException
	 */
	/*@RequestMapping(value = "/reset", method = RequestMethod.GET)
	@ResponseBody
	public  AccessControlList resetPermissions(
			@RequestParam("uri")  String uri,
			@RequestParam(value="recursive", required=false, defaultValue="y") String aclRecursive) 
					throws IOException, JargonException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		
		IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
		IRODSFile iFile = fileFactory.instanceIRODSFile(uri);
		
		boolean recursive = false;
		if (aclRecursive != null && aclRecursive.matches("([Yy](es)?)|([Tt](rue)?)"))
			recursive = true;
		
		AccessControlList acl = AccessControlList.getACL(uri, irodsAccessObjectFactory, irodsAccount);
		ArrayList<AccessControl> permissions = acl.getPermissions();
		for (AccessControl  ac : permissions)
		{
			String user = ac.getUsername();
			boolean isOwner = false;
			int a = 0;
			while (!isOwner && a < permissions.size()){
				if (permissions.get(a).getUsername().equals(user)
						&& permissions.get(a).getPermission().equals(FilePermissionEnum.OWN.name())
			}
			
			
			//TODO
			????
			
			// if its a collection
			if (iFile.isDirectory()){
			
				CollectionAO cAO = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
				
				
				
			}
			else // file ACL
			{
				DataObjectAO dAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
				
				
			}
		}
	
		AccessControlList acl = AccessControlList.getACL(uri, irodsAccessObjectFactory, irodsAccount);
		
		return acl;
	}*/
}
