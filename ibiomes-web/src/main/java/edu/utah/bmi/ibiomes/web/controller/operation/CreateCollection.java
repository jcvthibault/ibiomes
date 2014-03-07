package edu.utah.bmi.ibiomes.web.controller.operation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Create new iRODS collection
 * @author Julien Thibault, University of Utah
 *
 */
public class CreateCollection extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			String uri = request.getParameter("uri");
			String dirName = request.getParameter("directory");
			String target = request.getParameter("dispatchto");
			if (target == null || target.length()==0){
				target = "collection.do?uri="+uri;
			}
			
			ModelAndView mav = null;

			try{
				IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
				IRODSFile file = factory.instanceIRODSFile(uri + "/" + dirName);
				
				if (!file.exists()){
					file.mkdir();
					mav = new ModelAndView(target);
				}
				else {
					mav = new ModelAndView(target);
					mav.addObject("error", "Directory " + file.getCanonicalPath() + " already exists");
				}
				
			} catch(Exception e){
				mav = new ModelAndView(target);
				mav.addObject("error", e.getLocalizedMessage());
			}
			return mav;
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
