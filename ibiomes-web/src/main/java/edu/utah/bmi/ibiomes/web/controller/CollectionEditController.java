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

package edu.utah.bmi.ibiomes.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.protovalues.FilePermissionEnum;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.UserAO;
import org.irods.jargon.core.pub.domain.User;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.web.domain.Method;
import edu.utah.bmi.ibiomes.web.domain.Simulation;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESExperimentAO;
import edu.utah.bmi.ibiomes.metadata.MetadataSqlConnector;
import edu.utah.bmi.ibiomes.web.Utils;

/**
 * Edit collection
 * @author Julien Thibault
 *
 */
public class CollectionEditController  extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	private IRODSAccount irodsAccount = null;
	private IBIOMESCollectionAO ibiomesCollAO = null;
	private IBIOMESExperimentAO ibiomesExpAO = null;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String target = null;
		boolean isExperiment = false;
		
		HttpSession session = request.getSession();
		
		String uri = request.getParameter("uri");
		String id = request.getParameter("id");
		int collectionId = -1;
		
		if (uri!=null && uri.length()>0){
			if (uri.endsWith("/"))
				uri = uri.substring(0, uri.length()-1);
		}
		else {
			uri = null;
			if (id!=null && id.length()>0){
				collectionId = Integer.parseInt(id);
			}
		}
		
		//check authentication
		irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			if (irodsAccount.isAnonymousAccount()){
				ModelAndView mav = new ModelAndView("index.do");
				mav.addObject("error","Anonymous users are not authorized to access this page.");
				return mav;
			}
			String message = (String)request.getParameter("message");
			IBIOMESCollection coll = null;
			CollectionAO caoFactory = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
			ibiomesCollAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
			ibiomesExpAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
			
			try{
				if (uri!=null)
					coll = ibiomesCollAO.getCollectionFromPath(uri);
				else {
					coll = ibiomesCollAO.getCollectionByID(collectionId);
				}
			} catch(Exception exc) {
				ModelAndView mav = new ModelAndView("/exception.jsp");
				mav.addObject("errorTitle", "Collection not found!");
				mav.addObject("errorMsg", "Cannot edit collection '" + uri + "': " + exc.getLocalizedMessage());
				return mav;
			}
			
			//check the type of directory/collection
			if (coll.isExperiment()){
				target = "/editcollection.jsp";
				isExperiment = true;
			}
			else
				target = "/editsubcollection.jsp";
			         	
			//return collection path as HTML navigation links
			String htmlNav  = Utils.getNavigationLink(uri);

			//if no write access, error
			if (!ibiomesCollAO.isWritable(uri)){
				ModelAndView mav = new ModelAndView("/exception.jsp");
				mav.addObject("errorTitle", "Not authorized!");
				mav.addObject("errorMsg", "You do not have write privileges for collection " + uri);
				return mav;
			}
			
			//else
			ModelAndView mav = new ModelAndView(target);
			mav.addObject("navLink", htmlNav);
			mav.addObject("zone", irodsAccount.getZone());
			
			if (isExperiment)
			{
				Simulation simulation = new Simulation(coll);
				mav.addObject("simulation", simulation);
				
				//get list of topology files
				List<String> topologyFileList = ibiomesExpAO.getTopologyFilesInExperiment(uri);
				int i = 0;
				for (String fileAbsPath : topologyFileList){
					topologyFileList.set(i, fileAbsPath.substring(uri.length()+1));
					i++;
				}
				mav.addObject("topologyFileList", topologyFileList);
				
				//get list of md/qm parameter files
				List<String> paramFileList = ibiomesExpAO.getParameterFilesInExperiment(uri);
				i = 0;
				for (String fileAbsPath : paramFileList){
					paramFileList.set(i, fileAbsPath.substring(uri.length()+1));
					i++;
				}
				mav.addObject("paramFileList", paramFileList);
	         	
				//get list of JMol files
				List<String> jmolFileNames = ibiomesExpAO.getJmolFilesInExperiment(uri);
				i = 0;
				for (String fileAbsPath : jmolFileNames){
					jmolFileNames.set(i, fileAbsPath.substring(uri.length()+1));
					i++;
				}
				mav.addObject("jmolFileList", jmolFileNames);
				
				//get list of possible metadata
				//retrieve dictionaries from SQL DB
				WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
				MetadataSqlConnector sql = (MetadataSqlConnector) springContext.getBean("metadataSqlConnector");

				mav.addObject("softwareList", sql.getSoftwareList());
				mav.addObject("structureDbList", sql.getStructureDbList());
				mav.addObject("literatureDbList", sql.getLiteratureDbList());
				mav.addObject("methodList", sql.getMethodList());
				mav.addObject("moleculeTypeList", sql.getMoleculeTypeList());
				
				Method simMethod = simulation.getMethod();
				String hasMDMetadata = "false";
				String hasQMMetadata = "false";
				
				if (isExperiment && simMethod != null && simMethod.getName() != null)
				{
					if (simMethod.getName().equals(Method.METHOD_QUANTUM_MD)){
						mav.addObject("quantumMdList", sql.getQuantumMdMethodList());
					}
					
					if (	simMethod.getName().equals(Method.METHOD_MD) || 
							simMethod.getName().equals(Method.METHOD_LANGEVIN_DYNAMICS) ||
							simMethod.getName().equals(Method.METHOD_REMD) ||
							simMethod.getName().equals(Method.METHOD_QMMM) || 
							simMethod.getName().equals(Method.METHOD_QUANTUM_MD))
					{
						// MD-specific metadata					
						mav.addObject("electrostaticsList", sql.getMdElectrostaticsList());
						mav.addObject("boundaryConditionList", sql.getBoundaryConditionList());
						mav.addObject("unitShapeList", sql.getMdUnitShapeList());
						mav.addObject("constraintList", sql.getMdConstraintList());
						mav.addObject("ensembleList", sql.getEnsembleList());
						mav.addObject("thermostatList", sql.getThermostatList());
						mav.addObject("barostatList", sql.getBarostatList());
						mav.addObject("solventList", sql.getSolventTypeList());
						mav.addObject("samplingMethodList", sql.getMDSamplingMethods());
						
						hasMDMetadata = "true";
					}
					if (simMethod.getName().equals(Method.METHOD_QM) 
							|| simMethod.getName().equals(Method.METHOD_QMMM)
							|| simMethod.getName().equals(Method.METHOD_QUANTUM_MD))
					{
						// QM-specific metadata
						mav.addObject("qmMethodList", sql.getQmMethodFamilyList());
						hasQMMetadata = "true";
					}
				}
				mav.addObject("hasQMMetadata", hasQMMetadata);
				mav.addObject("hasMDMetadata", hasMDMetadata);
			}
			else //regular directory
			{
				//look for parent directory which corresponds to an experiment
				IBIOMESCollection parent = ibiomesExpAO.getRootExperiment(uri);
				if (parent != null){
					mav.addObject("rootExperiment", new Simulation(parent));
					Simulation simulation = new Simulation(coll);
					mav.addObject("simulation", simulation);
				}
			}
			
			mav.addObject("collection", coll);
			
			//check permissions
			boolean inherits = caoFactory.isCollectionSetForPermissionInheritance(uri);
			mav.addObject("inheritPermissions", inherits);
			mav.addObject("permissionREAD", FilePermissionEnum.READ);
			mav.addObject("permissionWRITE", FilePermissionEnum.WRITE);
			mav.addObject("permissionOWN", FilePermissionEnum.OWN);

			//get user list
			UserAO userAO = irodsAccessObjectFactory.getUserAO(irodsAccount);
			List<User> users = userAO.findAll();
			mav.addObject("userList", users);
			
			if (message!=null && message.length()>0)
				mav.addObject("message", message);
			
			return mav;
		}
		else 
		{
			if (session!=null)
				session.invalidate();
			ModelAndView mav = new ModelAndView("index.do");
			return mav;
		}
	}
	 
	/**
	 * @return the irodsAccessObjectFactory
	 */
	public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	}

	/**
	 * @param irodsAccessObjectFactory
	 *            the irodsAccessObjectFactory to set
	 */
	public void setIrodsAccessObjectFactory(
			IRODSAccessObjectFactory irodsAccessObjectFactory) {
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
	}
}
