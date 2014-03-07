package edu.utah.bmi.ibiomes.web.controller.operation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Servlet implementation class BiosimCollectionReader
 */
public class FileCollectionAction extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		String target;
		
		try{
			//check authentication
			IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (irodsAccount != null)
			{
				String action = request.getParameter("action");
	
				ModelAndView mav = new ModelAndView();
				
				if (action.equals("animation")){
					target = "/jmol.do";
				}
				else if (action.equals("comparison")){
					target = "/jmol.do?script=comparison";
				}
				else if (action.equals("delete")){
					target = "/delete.do";
				}
				else if (action.equals("index")){
					target = "/indexFile.do";
				}
				else if (action.equals("hide")){
					target = "/hideFile.do";
				}
				else if (action.equals("addtocart")){
					target = "/shoppingCart.do?action=add";
				}
				else if (action.equals("removefromcart")){
					target = "/shoppingCart.do?action=remove";
				}
				else if (action.equals("clearcart")){
					target = "/shoppingCart.do?action=clear";
				}
				else throw new Exception("Command '"+ action +"' unknown!");
				
				
				mav.setViewName(target);
				return mav;
			}
			else 
			{
				ModelAndView mav = new ModelAndView("index.do");
				return mav;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("/error.do");
			mav.addObject("exception", e);
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
