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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Access control
 * @author Julien Thibault, University of Utah
 *
 */
public class AccessControl implements Comparable<AccessControl> {
	private String username;
	private String zone;
	private String permission;
	
	public AccessControl(){
	}
	
	/**
	 * Constructs new permission container
	 * @param userName Username or group
	 * @param zone
	 * @param permission
	 */
	public AccessControl(String userName, String zone, String permission)
	{
		this.username = userName;
		this.permission = permission;
		this.zone = zone;
	}
	
	public String toString(){
		return ("Permission: " + getUsername() + "#" + getZone() + " : " + getPermission());
	}
	
	@XmlAttribute(name="username")
	public String getUsername() {
		return username;
	}
	@XmlAttribute(name="zone")
	public String getZone() {
		return zone;
	}
	@XmlValue
	public String getPermission() {
		return permission;
	}

	public int compareTo(AccessControl o) {
		return this.username.compareTo(o.getUsername());
	}
}
