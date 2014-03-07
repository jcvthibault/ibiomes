package edu.utah.bmi.ibiomes.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.DataNotFoundException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.exception.OverwriteException;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.query.JargonQueryException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.metadata.ExperimentMetadata;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESExperimentAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.web.Utils;
import edu.utah.bmi.ibiomes.web.domain.Simulation;

/**
 * Search simulations in iRODS file system
 * @author Julien Thibault
 *
 */
public class CollectionController  extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	private HttpSession session = null;
	private IRODSAccount irodsAccount = null;
	private IBIOMESCollectionAO ibiomesCollAO = null;
	private IBIOMESExperimentAO ibiomesExpAO = null;
	private IBIOMESFileAO ibiomesFileAO = null;
	private IRODSFileFactory ifactory = null;
	private DataObjectAO dAO = null;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		session = request.getSession();
		String message = (String)request.getParameter("message");

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
			try{
				ibiomesCollAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
				ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
				ibiomesExpAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
				dAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
				ifactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
				
				String target = "/subcollection.jsp";
				
				IBIOMESCollection coll = null;
				if (uri!=null)
					coll = ibiomesCollAO.getCollectionFromPath(uri);
				else {
					coll = ibiomesCollAO.getCollectionByID(collectionId);
				}
				
				boolean canRead = ibiomesCollAO.isReadable(uri);
				boolean canWrite = ibiomesCollAO.isWritable(uri);
				
							
				ModelAndView mav = new ModelAndView();
				
				//if regular directory
				if (!coll.isExperiment())
				{
					//look for parent directory which corresponds to an experiment
					try{
						IBIOMESCollection parentExperiment = ibiomesExpAO.getRootExperiment(uri);
						if (parentExperiment != null){
							//if this directory is a subfolder in an experiment, go to the experiment
							coll = parentExperiment;
						}
					} catch (Exception e) {
						//TODO
					}
				}
				
				//if experiment
				if (coll.isExperiment())
				{
					Simulation simulation = new Simulation(coll);
					mav.addObject("simulation", simulation);
					
					//check if a file has to be loaded in JMol
					mav = findJMolStructure(mav, coll);
				}
				
				mav.addObject("collection", coll);
				
				if (message!=null && message.length()>0)
					mav.addObject("message", message);
				
				//go to correct view
				if (coll.isExperiment())
					target = "/collection.jsp";
				else
					target = "/subcollection.jsp";
				
				mav.setViewName(target);
				
				//return collection path as HTML navigation links
				String htmlNav = null;
				if (irodsAccount.isAnonymousAccount())
					htmlNav  = Utils.getNavigationLinkDiabled(coll.getAbsolutePath());
				else
					htmlNav  = Utils.getNavigationLink(coll.getAbsolutePath());
				mav.addObject("navLink", htmlNav);
				
				mav.addObject("canRead", canRead);
				mav.addObject("canWrite", canWrite);
				
				return mav;
			}
			catch (Exception e) {
				ModelAndView mav = new ModelAndView("/error.do");
				mav.addObject("exception", e);
				return mav;
			}
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
	 * Get a local copy of the file
	 * @param iFile
	 * @param dAO
	 * @return
	 * @throws OverwriteException
	 * @throws DataNotFoundException
	 * @throws JargonException
	 */
	 private String copyFileLocally(IRODSFile iFile, DataObjectAO dAO) throws OverwriteException, DataNotFoundException, JargonException
	 {
		String relativePath = session.getAttribute("USER_DIR") + "/" + iFile.getAbsolutePath().replaceAll("/", "_");
		String localFilePath = getServletContext().getRealPath("/") + "/" + relativePath;
		
		File localFile = new File(localFilePath);
		
		//check if the file has already been copied
		if (!localFile.exists())
	    {
			//if user has read access to the file
			if (iFile.canRead()){
				DataTransferOperations dataTransfer = irodsAccessObjectFactory.getDataTransferOperations(irodsAccount);
				dataTransfer.getOperation(iFile, localFile, null, null);
			}
			else {
				return null;
			}
		}
		return relativePath;
	 }

	/**
	 * 
	 * @param mav
	 * @param coll
	 * @return
	 * @throws JargonException
	 * @throws JargonQueryException
	 */
	private ModelAndView findJMolStructure(ModelAndView mav, IBIOMESCollection coll) throws JargonException, JargonQueryException
	{
		String jmolFilePath = coll.getMetadata().getValue(ExperimentMetadata.MAIN_3D_STRUCTURE_FILE);
      	String jmolFileDesc = "";
      	if (jmolFilePath!=null && jmolFilePath.length()>0)
      	{
      		try{
	         	IBIOMESFile jmolFile = ibiomesFileAO.getFileByPath(coll.getAbsolutePath() + "/" + jmolFilePath);
				String jmolDescription = jmolFile.getMetadata().getValue(FileMetadata.FILE_DESCRIPTION);
				if (jmolDescription.length()==0)
					jmolDescription = jmolFile.getName();
				
				IRODSFile iFile = ifactory.instanceIRODSFile(jmolFile.getAbsolutePath());
				
				//if (iFile.canRead()){
				String jmolLocalFilePath = copyFileLocally(iFile, dAO);
				jmolFileDesc = jmolDescription;
				
				mav.addObject("jmolFileLocalPath", jmolLocalFilePath);
				mav.addObject("jmolFileUri", coll.getAbsolutePath() + "/" + jmolFilePath);
				mav.addObject("jmolFileDescription", jmolFileDesc);
			}
			catch(Exception e){
				//for debug
			}
      	}
      	return mav;
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
