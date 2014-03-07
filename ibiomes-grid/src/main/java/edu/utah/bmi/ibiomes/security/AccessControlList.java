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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.protovalues.FilePermissionEnum;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.UserFilePermission;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;

/**
 * Access Control List (ACL)
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="permissions")
public class AccessControlList implements Serializable {

	private static final long serialVersionUID = 990410839613023674L;
	
	private ArrayList<AccessControl> _permissions;
	private String _filepath;
	private String _zone;
	
	/**
	 * Get permissions
	 * @return  List of permissions
	 */
	@XmlElement(name="permission")
	public ArrayList<AccessControl> getPermissions(){
		return _permissions;
	}
	/**
	 * Set permissions
	 * @param permissions List of permissions
	 */
	public void setPermissions(ArrayList<AccessControl> permissions){
		_permissions = permissions;
	}

	/**
	 * Get file path
	 * @return File path
	 */
	@XmlAttribute(name="file")
	public String getFilePath(){
		return _filepath;
	}
	/**
	 * Set file path
	 * @param filePath File path
	 */
	public void setFilePath(String filePath){
		_filepath = filePath;
	}
	
	/**
	 * Get zone
	 * @return Zone
	 */
	@XmlAttribute(name="zone")
	public String getZone(){
		return _zone;
	}
	/**
	 * Set zone
	 * @param zone Zone
	 */
	public void setZone(String zone){
		_zone = zone;
	}
	
	public AccessControlList(){
	}
	
	/**
	 * Get Access Control List from path
	 * @param path Path to file
	 * @param factory
	 * @param account iRODS account
	 * @return Access Control List
	 * @throws IOException
	 * @throws JargonException
	 */
	public static AccessControlList getACL(String path, IRODSAccessObjectFactory factory, IRODSAccount account) throws IOException, JargonException 
	{
		IRODSFileFactory fileFactory = factory.getIRODSFileFactory(account);
		IRODSFile file = fileFactory.instanceIRODSFile(path);
			
		return AccessControlList.getACL(file, factory, account);
	}
	
	/**
	 * Get Access Control List for given iRODS file
	 * @param file iRODS file
	 * @param factory
	 * @param account iRODS account
	 * @return Access Control List
	 * @throws IOException
	 * @throws JargonException
	 */
	public static AccessControlList getACL(IRODSFile file, IRODSAccessObjectFactory factory, IRODSAccount account) throws IOException, JargonException 
	{
		ArrayList<AccessControl> permissions;
		AccessControlList ACL = new AccessControlList();
		
		String filepath = file.getAbsolutePath();
		ACL.setFilePath(filepath);
		
		List<UserFilePermission> ufp;
		
		if (file.isDirectory()){
			ufp = queryCollectionACL(factory, account, filepath);
		}
		else {
			ufp = queryFileACL(factory, account, filepath);
		}
			
		if (ufp != null)
		{
			permissions = new ArrayList<AccessControl>();
			HashMap<String, AccessControl> userHash = new HashMap<String, AccessControl>();

			for (UserFilePermission fp : ufp) 
			{
				//System.out.println(fp);
				AccessControl newACL = new AccessControl(fp.getUserName(), fp.getUserZone(), fp.getFilePermissionEnum().toString());
				//keep highest permission code only
				AccessControl existingACL = userHash.get(fp.getUserName());
				if (existingACL==null || 
						FilePermissionEnum.valueOf(existingACL.getPermission()).getPermissionNumericValue() < fp.getFilePermissionEnum().getPermissionNumericValue())
					userHash.put(fp.getUserName(), newACL);
			}

			//return ACL
			Iterator<AccessControl> it = userHash.values().iterator();
			while (it.hasNext()){
				AccessControl acl = it.next();
				permissions.add(acl);
			}
			Collections.sort(permissions);
			ACL.setPermissions(permissions);
			
		} else ACL.setPermissions(null);	
		
		return ACL;
	}
	
	/**
	 * Retrieve ACL for this file.
	 * @param aoFactory
	 * @param account iRODS account
	 * @param filePath Path to file
	 * @return List of permissions
	 * @throws IOException
	 * @throws JargonException
	 */
	private static List<UserFilePermission> queryFileACL(IRODSAccessObjectFactory aoFactory, IRODSAccount account, String filePath) throws IOException, JargonException
	{
		DataObjectAO dAO = aoFactory.getDataObjectAO(account);
		return dAO.listPermissionsForDataObject(filePath);
	}
	
	/**
	 * Retrieve ACL for this collection.
	 * @param aoFactory 
	 * @param account iRODS account
	 * @param path Path to collection
	 * @return List of permissions
	 * @throws IOException
	 * @throws JargonException
	 */
	private static List<UserFilePermission> queryCollectionACL(IRODSAccessObjectFactory aoFactory, IRODSAccount account, String path ) throws IOException, JargonException
	{
		CollectionAO cAO = aoFactory.getCollectionAO(account);
		return cAO.listPermissionsForCollection(path);
	}
}
