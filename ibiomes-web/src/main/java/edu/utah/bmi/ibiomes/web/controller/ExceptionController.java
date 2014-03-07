package edu.utah.bmi.ibiomes.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.FileNotFoundException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ExceptionController extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
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
				ModelAndView mav = new ModelAndView("exception.jsp");
				
				Throwable exception = (Throwable)request.getAttribute("exception");
				if (exception == null)
					exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
				
				if (exception != null)
				{
					if (exception instanceof FileNotFoundException)
					{
						mav.addObject("errorTitle", "Error: file or directory not found");
						mav.addObject("errorMsg", "The file or directory requested could not be found. Make sure you have the appropriate permissions.");
					}
					else if (exception instanceof JargonException)
					{
						mav.addObject("errorTitle", "Error: iRODS exception");
						mav.addObject("errorMsg", "Jargon exception: " + exception.getLocalizedMessage());
					}
					else {
						mav.addObject("errorTitle", "Error (unknown)");
						mav.addObject("errorMsg", exception.getLocalizedMessage());
					}
					mav.addObject("errorTrace", getCustomStackTrace(exception));
				}
				else {
					mav.addObject("errorTitle", "Error (unknown)");
					mav.addObject("errorMsg", "No description avaialble.");
				}
				
				return mav;
			}
			else 
			{
				ModelAndView mav = new ModelAndView("index.do");
				return mav;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("exception.jsp");
			mav.addObject("errorTitle", "Error in error handler");
			mav.addObject("errorMsg", e.getLocalizedMessage());
			return mav;
		}
	}
	
	/**
	 * 
	 * @param aThrowable
	 * @return
	 */
	private static String getCustomStackTrace(Throwable aThrowable) 
	{
	    //add the class name and any message passed to constructor
	    final StringBuilder result = new StringBuilder( "Error: " );
	    result.append(aThrowable.toString());
	    final String NEW_LINE = "<br/>";
	    result.append(NEW_LINE);

	    //add each element of the stack trace
	    for (StackTraceElement element : aThrowable.getStackTrace() ){
	      result.append( element );
	      result.append( NEW_LINE );
	    }
	    return result.toString();
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
