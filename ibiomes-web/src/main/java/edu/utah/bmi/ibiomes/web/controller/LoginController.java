package edu.utah.bmi.ibiomes.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.auth.AuthResponse;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSAccessObjectFactoryImpl;
import org.irods.jargon.core.pub.ResourceAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.web.DefaultIrodsConnectionParameters;

/**
 * Controller for iRODS logins
 * @author Julien Thibault
 *
 */
public class LoginController extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		ModelAndView mav = null;
		
		//already logged in?
		HttpSession session = request.getSession();
		if (session != null && session.getAttribute("SPRING_SECURITY_CONTEXT") != null){
			IRODSAccount account = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
			irodsAccessObjectFactory.closeSessionAndEatExceptions(account);
			session.removeAttribute("SPRING_SECURITY_CONTEXT");
			session.invalidate();
			//mav = new ModelAndView("index.do");
			//return mav;
		}

		// guest/anonymous login?
		String isGuest = request.getParameter("guestLogin");
		boolean isGuestLogin = (isGuest!=null && isGuest.equals("true"));

		// Get the credentials from the request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String irodsHost = request.getParameter("host");
		String irodsZone = request.getParameter("zone");
		String irodsResc = request.getParameter("resource");
		String irodsPortStr = request.getParameter("port");
		int irodsPort = 1247;
		if (irodsPortStr != null && irodsPortStr.length()>0){
			try{
				irodsPort = Integer.parseInt(irodsPortStr);
			} catch (NumberFormatException exc){
				mav = new ModelAndView("login.jsp");
				DefaultIrodsConnectionParameters defaultIrodsConnection 
					= (DefaultIrodsConnectionParameters)this.getApplicationContext().getBean("defaultIrodsConnection");
				mav.addObject("defaultIrodsConnection", defaultIrodsConnection);
				mav.addObject("error", "Port '"+irodsPortStr+"' is invalid. This should be an integer.");
				return mav;
			}
		}
		
		IRODSAccount irodsAccount = null;
		AuthResponse authResponse = null;
		
		//guest login
		if (isGuestLogin){
			try{
				irodsAccount = IRODSAccount.instanceForAnonymous(irodsHost, irodsPort, "", irodsZone, irodsResc);
				IRODSAccessObjectFactory accessAO = IRODSAccessObjectFactoryImpl.instance(irodsAccessObjectFactory.getIrodsSession());
				try {
					authResponse = accessAO.authenticateIRODSAccount(irodsAccount);
				} 
				catch(JargonException e) {
					e.printStackTrace();
					return throwError("Cannot connect anonymously. Exception: " + e.getLocalizedMessage());
				}
				
				session = request.getSession(true);
				session.setAttribute("webServiceURL", this.getWebServiceURL(session, request));
				session.setAttribute("SPRING_SECURITY_CONTEXT", authResponse.getAuthenticatedIRODSAccount());
				//create temporary directory for the user based on session ID
				String userDir = createTmpDirForUser(session);
				if (userDir==null)
					return throwError("Cannot create temporary directory for anonymous user");
				
				session.setAttribute("USER_DIR", userDir);
				mav = new ModelAndView("index.do");
				logger.info("[iBIOMES] New iRODS connection created for anonymous user");
				
				return mav;
			} catch(JargonException e)
			{
				e.printStackTrace();
				return throwError("Cannot connect anonymously");
			}
		}
		else if ((username==null || username.length()==0) && (password==null || password.length()==0))
		{
			//need to login, forward to login page
			mav = new ModelAndView("login.jsp");
			DefaultIrodsConnectionParameters defaultIrodsConnection 
				= (DefaultIrodsConnectionParameters)this.getApplicationContext().getBean("defaultIrodsConnection");
			mav.addObject("defaultIrodsConnection", defaultIrodsConnection);
			return mav;
		}
		else {
			//check credentials
			irodsAccount = IRODSAccount.instance(irodsHost, irodsPort, username, password, "", irodsZone, irodsResc);
			IRODSAccessObjectFactory accessAO = IRODSAccessObjectFactoryImpl.instance(irodsAccessObjectFactory.getIrodsSession());
			try {
				authResponse = accessAO.authenticateIRODSAccount(irodsAccount);
			} 
			catch(JargonException e) {
				e.printStackTrace();
				return throwError("Cannot authenticate user '"+username+"'. Check your login information.");
			}
			session = request.getSession(true);
			
			try{
				//create temporary directory for the user based on session ID
				String userDir = createTmpDirForUser(session);
				if (userDir==null)
					return throwError("Cannot create temporary directory for user " + username);
				
				session.setAttribute("USER_DIR", userDir);
				session.setAttribute("SPRING_SECURITY_CONTEXT", authResponse.getAuthenticatedIRODSAccount());
				
				//store list of available resources
				ResourceAO rescAO = irodsAccessObjectFactory.getResourceAO(irodsAccount);
				session.setAttribute("resourceList", rescAO.listResourceAndResourceGroupNames());

				System.out.println("[iBIOMES] New iRODS connection created for user '"+username+"'");
				
				mav = new ModelAndView("index.do");
				
				String homeDirectory = "/" + irodsAccount.getZone() + "/home/" + irodsAccount.getUserName();
				session.setAttribute("homeDirectory", homeDirectory);
				session.setAttribute("webServiceURL", this.getWebServiceURL(session, request));
				
				return mav;
			}
			catch(JargonException e)
			{
				e.printStackTrace();
				return throwError("Wrong credentials");
			}
		}
	}
	
	/**
	 * create temporary directory for the user based on session ID
	 * @return
	 */
	private String createTmpDirForUser(HttpSession session){
		String ibiomesPath = session.getServletContext().getRealPath("/");
		String userDir = "temp/" + session.getId();
		String tmpDir = ibiomesPath + "/" + userDir;
		File directory = new File(tmpDir);
		boolean success = directory.mkdirs();
		if (success)
			return userDir;
		else return null;
	}
	
	/**
	 * Retrieve web service application URL
	 * @return web service application URL
	 */
	private String getWebServiceURL(HttpSession session, HttpServletRequest request){
		String configUrl = (String)this.getApplicationContext().getBean("webServiceURL");
		if (configUrl!=null){
			return configUrl;
		}
		else{
			//assume the web service is on the same host as portal
			String url = request.getScheme() + "://" + request.getLocalName();
			if (request.getLocalPort()>-1){
				url += ":" + request.getLocalPort();
			}
			url+="/ibiomes-ws";
			return url;
		}
	}
	
	/**
	 * Redirect to error page
	 * @param error
	 * @return
	 */
	private ModelAndView throwError(String error){
		ModelAndView mav = new ModelAndView("login.jsp");
		DefaultIrodsConnectionParameters defaultIrodsConnection 
			= (DefaultIrodsConnectionParameters)this.getApplicationContext().getBean("defaultIrodsConnection");
		mav.addObject("defaultIrodsConnection", defaultIrodsConnection);
		mav.addObject("error", error);
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
