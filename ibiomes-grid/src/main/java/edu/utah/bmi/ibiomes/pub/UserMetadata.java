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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.domain.User;

/**
 * User information
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="user")
public class UserMetadata implements Serializable, Comparable<UserMetadata>
{
	private static final long serialVersionUID = 1346910971067484900L;
	
	private String name;
	private String type;
	private String zone;
	private String info;
	private String comment;
	private long createTime;
	private long modifyTime;
	
	public UserMetadata(){
	}
	
	/**
	 * Construct object to store iRODS user information
	 * @param name
	 * @param type
	 * @param zone
	 * @param info
	 * @param comments
	 * @param createTime
	 * @param modifyTime
	 */
	public UserMetadata(String name, String type, String zone, String info, 
			String comments, int createTime, int modifyTime){
		this.name = name;
		this.type = type;
		this.zone = zone;
		this.info = info;
		this.comment = comments;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}
	
	/**
	 * Construct iRODS user object from account object
	 * @param acc iRODS account
	 */ 
	public UserMetadata(IRODSAccount acc){
		name = acc.getUserName();
		zone = acc.getZone();
	}
	
	/**
	 * Construct iRODS user object from account object
	 * @param user User
	 */ 
	public UserMetadata(User user){
		name = user.getName();
		zone = user.getZone();
		type = user.getUserType().getTextValue();
		info = user.getInfo();
		comment = user.getComment();
		createTime = user.getCreateTime().getTime();
		modifyTime = user.getModifyTime().getTime();
	}

	/**
	 * Dump user info to the console
	 */
	public void dumpInfo(){
		System.out.println("name = " + name);
		System.out.println("type = " + type);
		System.out.println("zone = " + zone);
		System.out.println("info = " + info);
		System.out.println("comments = " + comment);
		System.out.println("createTime = " + createTime);
		System.out.println("modifyTime = " + modifyTime);
	}
	
	/**
	 * Get user name
	 * @return User name
	 */
	@XmlAttribute(name="id")
	public String getName() {
		return name;
	}

	/**
	 * Get user type (rodsuser, rodsadmin)
	 * @return User type
	 */
	@XmlAttribute(name="type")
	public String getType() {
		return type;
	}

	/**
	 * Get user zone
	 * @return Zone name
	 */
	@XmlAttribute(name="zone")
	public String getZone() {
		return zone;
	}

	/**
	 * Get user information
	 * @return User info
	 */
	@XmlElement(name="info")
	public String getInfo() {
		return info;
	}

	/**
	 * Get comments
	 * @return comments
	 */
	@XmlElement(name="comments")
	public String getComments() {
		return comment;
	}

	/**
	 * Get user account creation timestamp
	 * @return Creation timestamp
	 */
	@XmlAttribute(name="created")
	public long getCreationTime() {
		return createTime;
	}

	/**
	 * Get user account modification timestamp
	 * @return Modification timestamp
	 */
	@XmlAttribute(name="modified")
	public long getModificationTime() {
		return modifyTime;
	}

	public int compareTo(UserMetadata o) {
		return name.compareTo(o.getName());
	}
}
