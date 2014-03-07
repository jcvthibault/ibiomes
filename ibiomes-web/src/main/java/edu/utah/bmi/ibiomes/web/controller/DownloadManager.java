package edu.utah.bmi.ibiomes.web.controller;

import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.UserAO;
import org.irods.jargon.datautils.datacache.DataCacheServiceFactory;
import org.irods.jargon.datautils.datacache.DataCacheServiceFactoryImpl;
import org.irods.jargon.datautils.shoppingcart.FileShoppingCart;
import org.irods.jargon.datautils.shoppingcart.ShoppingCartService;
import org.irods.jargon.datautils.shoppingcart.ShoppingCartServiceImpl;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Donwload simulation file/collections from the iRODS file system
 * @author Julien Thibault
 *
 */
public class DownloadManager extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	private HttpSession session;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		session = request.getSession(true);
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			if (irodsAccount.isAnonymousAccount()){
				ModelAndView mav = new ModelAndView("index.do");
				mav.addObject("error", "Sorry, the download service is currently not supported for anonymous users.");
				return mav;
			}
			
			String target = "/shoppingcart.jsp";

			//getting shopping cart from session
			DataCacheServiceFactory dataCacheServiceFactory = new DataCacheServiceFactoryImpl(irodsAccessObjectFactory);
			ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(irodsAccessObjectFactory, irodsAccount, dataCacheServiceFactory);
			FileShoppingCart fileShoppingCart = (FileShoppingCart)session.getAttribute("shoppingCart");

			// create an arbitrary key for this shopping cart
			String key = "";
			String shoppingCartFile = null;
			if (fileShoppingCart != null) {
				key = String.valueOf(System.currentTimeMillis());
				shoppingCartFile = shoppingCartService.serializeShoppingCartAsLoggedInUser(fileShoppingCart, key);
			}
			
			String password = null;
			if (!irodsAccount.isAnonymousAccount()){
				UserAO userAO = irodsAccessObjectFactory.getUserAO(irodsAccount);
				password = userAO.getTemporaryPasswordForConnectedUser();
			}
			ModelAndView mav = new ModelAndView(target);
			mav.addObject("password", password);
			mav.addObject("key", key);
			
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