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

package edu.utah.bmi.ibiomes.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/services/jmol")
public class JmolService
{	
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
	 * Add the items to the list of Jmol files
	 * @param urisStr List or URIs
	 */
	@RequestMapping(value="/add", method = RequestMethod.GET)
	@ResponseBody
	public void addToCart(@RequestParam("uris") String urisStr) 
	{
		List<String> items = getListFromSessionAndCreateIfNotThere();
		String[] uris = urisStr.split("\\,");
		for (int i=0; i<uris.length;i++){
			items.add(uris[i]);
		}
		request.getSession().setAttribute("jmolFilesCount", listCart().size());
	}

	/**
	 * List the contents of the cart as a list of file names
	 * @return
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public List<String> listCart() {
		List<String> items = getListFromSessionAndCreateIfNotThere();
		return items;
	}
	
	/**
	 * Get the number of items in the list
	 * @return
	 */
	@RequestMapping(value="/count", method = RequestMethod.GET)
	@ResponseBody
	public int countCartItems() {
		return (Integer)request.getSession().getAttribute("jmolFilesCount");
	}

	/**
	 * Clear the list
	 */
	@RequestMapping(value="/clear", method = RequestMethod.GET)
	@ResponseBody
	public void clearCart() {
		List<String> items = getListFromSession();
		if (items != null) {
			items.clear();
		}
		request.getSession().setAttribute("jmolFilesCount", 0);
	}

	/**
	 * Delete items from cart
	 * @param fileUris
	 */
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	@ResponseBody
	public void deleteFromList(@RequestParam("uris") String fileUris) {
		@SuppressWarnings("unchecked")
		List<String> items = (List<String>)request.getSession().getAttribute("jmolFiles");
		if (items != null && fileUris!=null) {
			String[] uris = fileUris.split("\\,");
			for(int f=0; f<uris.length;f++){
				items.remove(uris[f]);
			}
		}
		request.getSession().setAttribute("jmolFilesCount", listCart().size());
	}

	/**
	 * Get file list from session if present, but don't create one if not present (saves storing unnecesary session state)
	 * @return File list if stored in session, or <code>null</code> if not stored
	 */
	private List<String> getListFromSession() {
		@SuppressWarnings("unchecked")
		List<String> items = (List<String>)request.getSession().getAttribute("jmolFiles");
		return items;
	}

	private List<String> getListFromSessionAndCreateIfNotThere() {
		@SuppressWarnings("unchecked")
		List<String> items = (List<String>)request.getSession().getAttribute("jmolFiles");
		if (items == null) {
			//log.info("no shopping cart, create one");
			items = new ArrayList<String>();
			request.getSession().setAttribute("jmolFiles", items);
		}
		return items;

	}
}

