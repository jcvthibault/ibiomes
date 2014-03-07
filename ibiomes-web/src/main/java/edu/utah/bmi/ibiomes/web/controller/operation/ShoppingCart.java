package edu.utah.bmi.ibiomes.web.controller.operation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.AvuData;
import org.irods.jargon.datautils.shoppingcart.FileShoppingCart;
import org.irods.jargon.datautils.shoppingcart.ShoppingCartEntry;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.history.LogEntry;
import edu.utah.bmi.ibiomes.metadata.ExperimentMetadata;

/**
 * Add simulation metadata to iBIOMES file/collection
 * @author Julien Thibault
 *
 */
public class ShoppingCart extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	private HttpSession session;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		session = request.getSession(true);
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			String uris[] = request.getParameterValues("uri");
			String target = request.getParameter("dispatchto");
			String action = request.getParameter("action");
			
			ModelAndView mav = new ModelAndView(target);
			
			if (uris == null){
				String message = "No item was added to your cart.";
				mav.addObject("error", message);
				return mav;
			}
			
			if (action.equals("add")){
				for (int i=0;i<uris.length;i++){
					addToCart(uris[i]);
				}
				String message = "New items were successfully added to your cart.";
				mav.addObject("message", message);
			}
			else if (action.equals("remove"))
			{
				for (int i=0;i<uris.length;i++){
					deleteFromCart(uris[i]);
				}
				mav.addObject("message", "Items were removed from cart.");
			}
			else if (action.equals("clear")){
				clearCart();
				mav.addObject("message", "Cart list was cleared.");
			}
			else if (action.equals("list")){
				mav.addObject("shoppingList", listCart());
			}

			return mav;
		}
		else 
		{
			ModelAndView mav = new ModelAndView("index.do");
			return mav;
		}
	}
	
	/**
	 * Add the item to the shopping cart
	 * @param irodsFileAbsolutePath <code>String</code> with the absolute path to the iRODS file to add to the cart
	 * @param irodsAccount <code>IRODSAccount</code> for which the cart will be associated
	 * @return
	 */
	public void addToCart(String irodsFileAbsolutePath) {
		FileShoppingCart fileShoppingCart = getCartFromSessionAndCreateIfNotThere();
		fileShoppingCart.addAnItem(ShoppingCartEntry.instance(irodsFileAbsolutePath));
		session.setAttribute("shoppingCartItemCount", listCart().size());
	}

	/**
	 * List the contents of the cart as a list of file names
	 * @return
	 */
	public List<String> listCart() {
		List<String> results = new ArrayList<String>();
		FileShoppingCart fileShoppingCart = getCartFromSession();
		if (fileShoppingCart != null) {
			results = fileShoppingCart.getShoppingCartFileList();
		}
		return results;
	}

	/**
	 * Clear the files in the cart
	 */
	public void clearCart() {
		FileShoppingCart fileShoppingCart = getCartFromSession();
		if (fileShoppingCart != null) {
			fileShoppingCart.clearCart();
		}
		session.setAttribute("shoppingCartItemCount", 0);
	}

	public void deleteFromCart(String fileName) {
		FileShoppingCart shoppingCart = (FileShoppingCart)session.getAttribute("shoppingCart");
		if (shoppingCart != null) {
			shoppingCart.removeAnItem(fileName);
		}
		session.setAttribute("shoppingCartItemCount", listCart().size());
	}

	/**
	 * Get shopping cart from session if present, but don't create one if not present (saves storing unnecesary session state)
	 * @return FileShoppingCart if stored in session, or <code>null</code> if not stored
	 */
	private FileShoppingCart getCartFromSession() {
		FileShoppingCart shoppingCart = (FileShoppingCart)session.getAttribute("shoppingCart");
		return shoppingCart;
	}

	private FileShoppingCart getCartFromSessionAndCreateIfNotThere() {
		FileShoppingCart shoppingCart = (FileShoppingCart)session.getAttribute("shoppingCart");
		if (shoppingCart == null) {
			//log.info("no shopping cart, create one");
			shoppingCart = FileShoppingCart.instance();
			session.setAttribute("shoppingCart", shoppingCart);
		}
		return shoppingCart;

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