package edu.utah.bmi.ibiomes.web.controller.operation;

import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.CollectionNotEmptyException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSFileSystem;
import org.irods.jargon.core.pub.IRODSRegistrationOfFilesAO;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Delete simulation file/collections from the iRODS file system
 * @author Julien Thibault
 *
 */
public class Unregister extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		try{
			//check authentication
			IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (irodsAccount != null)
			{
				String[] uris = request.getParameterValues("uri");
				String target = (String)request.getParameter("dispatchto");
				if (target == null || target.length()==0)
					target = "/index.do";
				
				boolean success = false;
				
				//if no uri provided, error message
				if (uris == null){
					ModelAndView mav = new ModelAndView(target);
					String message = "No file were unregistered.";
					mav.addObject("error", message);
					return mav;
				}
	
				
				//remove files (unregister)
				IRODSRegistrationOfFilesAO ireg = irodsAccessObjectFactory.getIRODSRegistrationOfFilesAO(irodsAccount);
				IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
				
				for (int f=0;f<uris.length;f++)
				{
					String uri = uris[f];
					IRODSFile file = factory.instanceIRODSFile(uri);
					
					if (file.isDirectory()){
						target = "/index.do";
						try{
							success = ireg.unregisterCollection(uri, true);
						}
						catch(CollectionNotEmptyException e){
							logger.error("Cannot unregister non-empty collection " + uri);
							ModelAndView mav = new ModelAndView(target);
							mav.addObject("error", "Cannot unregister non-empty collection " + uri);
							return mav;
						}
						if (!success){
							logger.error("Could not delete collection "+ uri);
						}
					}
					else {
						target = (String)request.getParameter("dispatchto");
						if (target == null || target.length()==0)
							target = "/editcollection.do?uri="+uri.substring(0,uri.lastIndexOf('/'));
	
						success = ireg.unregisterDataObject(uri);
						
						if (!success){
							logger.error("Could not delete file "+ uri);
						}
					}
				}
	
				ModelAndView mav = new ModelAndView(target);
				if (success)
					mav.addObject("message", "Object(s) successfully deleted.");
				else
					mav.addObject("error", "At least one file or collection could not be deleted.");
				return mav;
			}
			else 
			{
				ModelAndView mav = new ModelAndView("index.do");
				return mav;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("/error.do");
			mav.addObject("exception", e);
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
