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

package edu.utah.bmi.ibiomes.io;

/**
 * File system information
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESFileSystem {
	
	private String protocol;
	private String hostname;
	private int port;
	private String instanceId;
	private String directoryId;	
	
	/**
	 * Get file system protocol
	 * @return File system protocol
	 */
	public String getProtocol() {
		return protocol;
	}
	/**
	 * Set file system protocol
	 * @param protocol File system protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	/**
	 * Get hostname/IP
	 * @return Hostname or host IP
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * Set hostname or host IP
	 * @param hostname Hostname or host IP
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	/**
	 * Get port.
	 * @return Port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * Set port.
	 * @param port Port.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * Get file system instance ID on the host.
	 * @return File system instance ID.
	 */
	public String getInstanceId() {
		return instanceId;
	}
	/**
	 * Set file system instance ID on the host.
	 * @param instanceId File system instance ID.
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	/**
	 * Get directory ID in the file system
	 * @return Directory ID
	 */
	public String getDirectoryId() {
		return directoryId;
	}
	
	/**
	 * Set directory ID in the file system
	 * @param directoryId Directory ID
	 */
	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
		
	}
	
	@Override
	public String toString(){
		String result = this.protocol + "://" + hostname;
		if (port>0)
			result += ":"+port;
		if (instanceId!=null && instanceId.length()>0)
			result += "/"+instanceId;
		
		return result;
	}
	
}
