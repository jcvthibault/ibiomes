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

package edu.utah.bmi.ibiomes.web;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Default iRODS connection parameters to use for the web portal. 
 * If enabled, the login page will not ask for the user to enter this information.
 * @author Julien Thibault, University of Utah
 *
 */
public class DefaultIrodsConnectionParameters {
	
	private boolean enabled = false;
	private String host = null;
	private String zone = null;
	private int port = 1247;
	private String defaultResource = null;
	
	public DefaultIrodsConnectionParameters(){
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	@Autowired
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getHost() {
		return host;
	}
	@Autowired
	public void setHost(String host) {
		this.host = host;
	}
	public String getZone() {
		return zone;
	}
	@Autowired
	public void setZone(String zone) {
		this.zone = zone;
	}
	public int getPort() {
		return port;
	}
	@Autowired
	public void setPort(int port) {
		this.port = port;
	}
	public String getDefaultResource() {
		return defaultResource;
	}
	@Autowired
	public void setDefaultResource(String defaultResource) {
		this.defaultResource = defaultResource;
	}
}
