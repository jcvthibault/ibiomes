/**
 * 
 */
package edu.utah.bmi.ibiomes.web.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Adapter to intercept and close IRODS connections after request processing
 * 
 * @author Mike Conway - DICE (www.irods.org)
 * 
 */
public class SessionClosingInterceptor extends HandlerInterceptorAdapter {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (irodsAccessObjectFactory != null){
			try{
				//System.out.println("[iBIOMES] Closing iRODS connection ("+request.getRequestURI()+")... ");
				irodsAccessObjectFactory.closeSession();
				//System.out.println("[iBIOMES] Done!");
			} catch (JargonException e){
				e.printStackTrace();
			}
		}
		super.postHandle(request, response, handler, modelAndView);
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
