package edu.utah.bmi.ibiomes.web.controller.operation;

import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Delete simulation file/collections from the iRODS file system
 * @author Julien Thibault
 *
 */
public class Delete extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			try{
				String[] uris = request.getParameterValues("uri");
				String target = (String)request.getParameter("dispatchto");
				if (target == null || target.length()==0)
					target = "/index.do";
				
				boolean success = false;
				
				//if no uri provided, error message
				if (uris == null){
					ModelAndView mav = new ModelAndView(target);
					String message = "No file were deleted.";
					mav.addObject("error", message);
					return mav;
				}
				
				//remove files (deletion)
				IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
				for (int f=0;f<uris.length;f++)
				{
					IRODSFile file = factory.instanceIRODSFile(uris[f]);
					//try to remove it
					success = file.deleteWithForceOption();
					
					if (!success)
						logger.error("Could not delete file or collection "+ uris[f]);
				}
				//TODO return message of success or failure
				//
					
				ModelAndView mav = new ModelAndView(target);
				
				if (success)
					mav.addObject("message", "Object successfully deleted.");
				else
					mav.addObject("error", "At least one file could not be deleted.");
				return mav;
			}
			catch(Exception e){
				e.printStackTrace();
				ModelAndView mav = new ModelAndView("/error.do");
				mav.addObject("exception", e);
				return mav;
			}
		}
		else 
		{
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
