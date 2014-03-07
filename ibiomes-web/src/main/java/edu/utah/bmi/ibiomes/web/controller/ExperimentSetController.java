package edu.utah.bmi.ibiomes.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.sql.DataSource;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionList;
import edu.utah.bmi.ibiomes.pub.IBIOMESExperimentAO;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSet;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSetAO;

/**
 * Search simulations in iRODS file system
 * @author Julien Thibault
 *
 */
public class ExperimentSetController  extends AbstractController {
	
	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	private HttpSession session = null;
	
	private DataSource getDataSource(ServletContext context){
    	WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(context);
    	return (DataSource)wContext.getBean("dataSource");
    }
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {

		session = request.getSession();
		String setIdStr = request.getParameter("id");
		try{
			long id  = 0;
			try{
				id = Long.parseLong(setIdStr);
			} catch (NumberFormatException e){
				ModelAndView mav = new ModelAndView("/index.do");
				mav.addObject("error", "Invalid experiment set ID");
				return mav;
			}
			
			//check authentication
			IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (irodsAccount != null)
			{
				ModelAndView mav = new ModelAndView("/experimentset.jsp");
				
				IBIOMESExperimentSetAO setAO = new IBIOMESExperimentSetAO(irodsAccessObjectFactory, irodsAccount, this.getDataSource(getServletContext()));
				IBIOMESExperimentAO expAO = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
				
				List<String> experimentPaths = setAO.listExperimentsInSet(id);
				IBIOMESCollectionList experiments = expAO.getExperimentsFromPath(experimentPaths);
				mav.addObject("experimentList", experiments);
				IBIOMESExperimentSet experimentSet = setAO.getExperimentSet(id);
				if (experimentSet!=null)
				{
					mav.addObject("set", experimentSet);
					//check write permissions
					boolean isOwner = (experimentSet.getOwner().equals(irodsAccount.getUserName()));
					mav.addObject("isOwner", isOwner);
					
					return mav;
				}
				else {
					mav = new ModelAndView("/exception.jsp");
					mav.addObject("errorTitle", "Experiment set");
					mav.addObject("errorMsg", "Cannot retrieve experiment set information. Make sure you have read permission.");
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
		} catch(Exception exc) {
			exc.printStackTrace();
			ModelAndView mav = new ModelAndView("/exception.jsp");
			mav.addObject("errorTitle", "Experiment set");
			mav.addObject("errorMsg", "Cannot retrieve experiment set information (" + setIdStr + "): " + exc.getLocalizedMessage());
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
