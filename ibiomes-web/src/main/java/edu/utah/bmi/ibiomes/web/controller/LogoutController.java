package edu.utah.bmi.ibiomes.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Logout user from iRODS and JSP session
 * @author Julien Thibault, University of Utah
 *
 */
public class LogoutController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		//invalidate JSP session
		session.invalidate();
		
		ModelAndView mav = new ModelAndView("index.do");
		return mav;
	}

}
