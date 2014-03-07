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

package edu.utah.bmi.ibiomes.pub;

import java.io.IOException;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.irods.jargon.core.pub.domain.Resource;

/**
 * Used to retrieve information about the resources (servers) available for a given iRODS zone.
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="server")
public class ServerMetadata implements Serializable {
	
	private static final long serialVersionUID = 648785986679028111L;
	
	private String _name;
	private String _class;
	private String _comments;
	private long _createDate;
	private long _modifyDate;
	private long _freeSpace;
	private String _info;
	private String _location;
	private String _status;
	private String _type;
	private String _vaultPath;
	private String _zone;

	public ServerMetadata(){
		
	}
	
	/**
	 * Constructs object to store iRODS resource information
	 * @param name Name
	 * @param resClass Class
	 * @param comments Comments
	 * @param creationDate Creation timestamp
	 * @param modificationDate Modification timestamp
	 * @param freeSpace Free space available
	 * @param info Information
	 * @param location Location (hostname)
	 * @param status Status
	 * @param type Type
	 * @param vaultPath Path to the vault (where the files are stored)
	 * @param zone Name of the zone
	 */
	public ServerMetadata(String name, String resClass, String comments, long creationDate, 
			long modificationDate, long freeSpace, String info, 
			String location, String status, String type, String vaultPath, String zone){
		
		_name = name;
		_class = resClass;
		_comments = comments;
		_createDate = creationDate;
		_modifyDate = modificationDate;
		_freeSpace = freeSpace;
		_info = info;
		_location = location;
		_status = status;
		_type = type;
		_vaultPath = vaultPath;
		_zone = zone;
	}
	

	/**
	 * Get iBIOMES resource information
	 * @param irodsResource iRODS resource
	 * @throws IOException 
	 */
	public ServerMetadata(Resource irodsResource)
	{
		if (irodsResource != null)
		{
			this.setComments(irodsResource.getComment());
			this.setCreateDate(irodsResource.getCreateTime().getTime());
			this.setFreeSpace(irodsResource.getFreeSpace());
			this.setInfo(irodsResource.getInfo());
			this.setLocation(irodsResource.getLocation());
			this.setModifyDate(irodsResource.getModifyTime().getTime());
			this.setName(irodsResource.getName());
			this.setResourceClass(irodsResource.getResourceClass());
			this.setStatus(irodsResource.getStatus());
			this.setType(irodsResource.getType());
			this.setVaultPath(irodsResource.getVaultPath());
			this.setZone(irodsResource.getZone().getZoneName());
		}
	}

	/**
	 * Dump server info to the console
	 */
	public void dumpInfo()
	{
		System.out.println("name = " + _name);
		System.out.println("class = " + _class);
		System.out.println("type = " + _type);
		System.out.println("zone = " + _zone);
		System.out.println("status = " + _status);
		System.out.println("info = " + _info);
		System.out.println("freeSpace = " + _freeSpace);
		System.out.println("location = " + _location);
		System.out.println("vaultPath = " + _vaultPath);
		System.out.println("comments = " + _comments);
		System.out.println("createTime = " + _createDate);
		System.out.println("modifyTime = " + _modifyDate);
	}

	/**
	 * Get resource name
	 * @return Resource name
	 */
	@XmlAttribute(name="name")
	public String getName() {
		return _name;
	}
	/**
	 * Set server name
	 * @param name Server name
	 */
	public void setName(String name) {
		this._name = name;
	}	

	/**
	 * Get resource class
	 * @return Resource class
	 */
	@XmlAttribute(name="class")
	public String getResourceClass() {
		return _class;
	}

	/**
	 * Get comments
	 * @return Comments
	 */
	@XmlElement(name="comments")
	public String getComments() {
		return _comments;
	}

	/**
	 * Get resource creation timestamp
	 * @return Resource creation timestamp
	 */
	@XmlAttribute(name="created")
	public long getCreationDate() {
		return _createDate;
	}

	/**
	 * Get resource modification timestamp
	 * @return Modification timestamp
	 */
	@XmlAttribute(name="modified")
	public long getModificationDate() {
		return _modifyDate;
	}

	/**
	 * Get amount of free space available
	 * @return Resource free space
	 */
	@XmlElement(name="freeSpace")
	public long getFreeSpace() {
		return _freeSpace;
	}

	/**
	 * Get resource information
	 * @return Resource information
	 */
	@XmlElement(name="info")
	public String getInfo() {
		return _info;
	}

	/**
	 * Get resource location (hostname)
	 * @return Resource location
	 */
	@XmlAttribute(name="location")
	public String getLocation() {
		return _location;
	}

	/**
	 * Get resource status
	 * @return Resource status
	 */
	@XmlElement(name="status")
	public String getStatus() {
		return _status;
	}

	/**
	 * Get resource type (e.g unix file system)
	 * @return Resource type
	 */
	@XmlAttribute(name="type")
	public String getType() {
		return _type;
	}

	/**
	 * Get iRODS vault path (physical path)
	 * @return Vault path
	 */
	@XmlElement(name="vaultPath")
	public String getVaultPath() {
		return _vaultPath;
	}

	/**
	 * Get name of the zone
	 * @return Zone name
	 */
	@XmlAttribute(name="zone")
	public String getZone() {
		return _zone;
	}

	public void setResourceClass(String resClass) {
		this._class = resClass;
	}

	public void setComments(String comments) {
		this._comments = comments;
	}

	public void setCreateDate(long date) {
		_createDate = date;
	}

	public void setModifyDate(long date) {
		_modifyDate = date;
	}

	public void setFreeSpace(long space) {
		_freeSpace = space;
	}

	public void setInfo(String info) {
		this._info = info;
	}

	public void setLocation(String location) {
		this._location = location;
	}

	public void setStatus(String status) {
		this._status = status;
	}

	public void setType(String type) {
		this._type = type;
	}

	public void setVaultPath(String path) {
		_vaultPath = path;
	}

	public void setZone(String zone) {
		this._zone = zone;
	}
}
