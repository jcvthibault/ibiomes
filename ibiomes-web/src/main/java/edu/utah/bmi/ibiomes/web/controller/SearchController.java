package edu.utah.bmi.ibiomes.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.metadata.MetadataSqlConnector;

/**
 * Search simulations in iRODS file system
 * @author Julien Thibault
 *
 */
public class SearchController extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession(true);
		IRODSAccount account = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (account != null)
		{
			WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			MetadataSqlConnector sql = (MetadataSqlConnector) springContext.getBean("metadataSqlConnector");
			
			ModelAndView mav = new ModelAndView("search.jsp");

			mav.addObject("userName", account.getUserName());
			
			mav.addObject("softwareList", sql.getSoftwareList());
			mav.addObject("methodList", sql.getMethodList());
			mav.addObject("moleculeList", sql.getMoleculeTypeList());
			mav.addObject("formatList", sql.getFileFormatList());
			// MD-specific dictionaries					
			mav.addObject("electrostaticsList", sql.getMdElectrostaticsList());
			mav.addObject("boundaryConditionList", sql.getBoundaryConditionList());
			mav.addObject("constraintList", sql.getMdConstraintList());
			mav.addObject("solventList", sql.getSolventTypeList());
			mav.addObject("samplingMethodList", sql.getMDSamplingMethods());
			
			/*//delete previous search results stored in session
			Object searchObject = session.getAttribute(SESSION_SEARCH_OBJECT);
			if (searchObject!=null){
				session.removeAttribute(SESSION_SEARCH_OBJECT);
			}*/
			return mav;
		}
		else {
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