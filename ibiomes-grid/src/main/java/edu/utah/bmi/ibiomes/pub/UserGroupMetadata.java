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
import javax.xml.bind.annotation.XmlRootElement;

import org.irods.jargon.core.pub.domain.UserGroup;

/**
 * User group information
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="usergroup")
public class UserGroupMetadata implements Serializable, Comparable<UserGroupMetadata>
{
	private static final long serialVersionUID = 634592913116878203L;
	private String name;
	private String zone;
	
	public UserGroupMetadata(){
	}
	
	/**
	 * Construct object to store iRODS user group information
	 * @param name User group name
	 * @param zone Zone
	 */
	public UserGroupMetadata(String name, String zone){
		this.name = name;
		this.zone = zone;
	}
		
	/**
	 * Construct iRODS user object from account object
	 * @param group User group
	 */ 
	public UserGroupMetadata(UserGroup group){
		this.name = group.getUserGroupName();
		this.zone = group.getZone();
	}

	/**
	 * Dump user info to the console
	 */
	public void dumpInfo(){
		System.out.println("name = " + this.name);
		System.out.println("zone = " + this.zone);
	}
	
	/**
	 * Get user name
	 * @return User name
	 */
	@XmlAttribute(name="id")
	public String getName() {
		return this.name;
	}

	/**
	 * Get user zone
	 * @return Zone name
	 */
	@XmlAttribute(name="zone")
	public String getZone() {
		return this.zone;
	}
	
	public int compareTo(UserGroupMetadata o) {
		return this.name.compareTo(o.getName());
	}
}
