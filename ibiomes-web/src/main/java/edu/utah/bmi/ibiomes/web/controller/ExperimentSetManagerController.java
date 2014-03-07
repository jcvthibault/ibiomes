package edu.utah.bmi.ibiomes.web.controller;

import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Experiment set management
 * @author Julien Thibault
 *
 */
public class ExperimentSetManagerController  extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	private HttpSession session = null;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		session = request.getSession();
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			if (irodsAccount.isAnonymousAccount()){
				ModelAndView mav = new ModelAndView("index.do");
				mav.addObject("error","Anonymous users are not authorized to access this page.");
				return mav;
			}
			else {
				return new ModelAndView("experimentsetmanager.jsp");
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
	 * @return the irodsAccessObjectFactory
	 */
	public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	}

	/**
	 * @param irodsAccessObjectFactory
	 *            the irodsAccessObjectFactory to set
	 */
	public void setIrodsAccessObjectFactory(IRODSAccessObjectFactory irodsAccessObjectFactory) {
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
	}
}
