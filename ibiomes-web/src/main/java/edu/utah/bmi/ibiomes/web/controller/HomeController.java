package edu.utah.bmi.ibiomes.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.web.DefaultIrodsConnectionParameters;

public class HomeController extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//check session
		HttpSession session = request.getSession();
		if (session == null){
			ModelAndView mav = new ModelAndView("login.jsp");
			return mav;
		}
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			ModelAndView mav = new ModelAndView("index.jsp");
			return mav;
		}
		else 
		{
			ModelAndView mav = new ModelAndView("login.jsp");
			DefaultIrodsConnectionParameters defaultIrodsConnection 
				= (DefaultIrodsConnectionParameters)this.getApplicationContext().getBean("defaultIrodsConnection");
			mav.addObject("defaultIrodsConnection", defaultIrodsConnection);
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
