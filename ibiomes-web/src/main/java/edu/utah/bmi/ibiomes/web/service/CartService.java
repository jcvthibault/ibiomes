package edu.utah.bmi.ibiomes.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.datautils.shoppingcart.FileShoppingCart;
import org.irods.jargon.datautils.shoppingcart.ShoppingCartEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/services/cart")
public class CartService
{	
	@Autowired(required = true)  
    private HttpServletRequest request;   
    public HttpServletRequest getRequest() {  
        return request;  
    }
    
    @Autowired(required = true)  
    private IRODSAccessObjectFactory irodsAccessObjectFactory;
	public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	}

	/**
	 * Add the item to the shopping cart
	 * @param irodsFileAbsolutePath <code>String</code> with the absolute path to the iRODS file to add to the cart
	 * @param irodsAccount <code>IRODSAccount</code> for which the cart will be associated
	 * @return
	 */
	@RequestMapping(value="/add", method = RequestMethod.GET)
	@ResponseBody
	public void addToCart(@RequestParam("uris") String urisStr) 
	{
		FileShoppingCart fileShoppingCart = getCartFromSessionAndCreateIfNotThere();
		String[] uris = urisStr.split("\\,");
		for (int i=0; i<uris.length;i++){
			fileShoppingCart.addAnItem(ShoppingCartEntry.instance(uris[i]));
		}
		request.getSession().setAttribute("shoppingCartItemCount", listCart().size());
	}

	/**
	 * List the contents of the cart as a list of file names
	 * @return
	 */
	@RequestMapping(value="/list", method = RequestMethod.GET)
	@ResponseBody
	public List<String> listCart() {
		List<String> results = new ArrayList<String>();
		FileShoppingCart fileShoppingCart = getCartFromSession();
		if (fileShoppingCart != null) {
			results = fileShoppingCart.getShoppingCartFileList();
		}
		return results;
	}
	
	/**
	 * Get the number of items in the cart
	 * @return
	 */
	@RequestMapping(value="/count", method = RequestMethod.GET)
	@ResponseBody
	public int countCartItems() {
		return (Integer)request.getSession().getAttribute("shoppingCartItemCount");
	}

	/**
	 * Clear the files in the cart
	 */
	@RequestMapping(value="/clear", method = RequestMethod.GET)
	@ResponseBody
	public void clearCart() {
		FileShoppingCart fileShoppingCart = getCartFromSession();
		if (fileShoppingCart != null) {
			fileShoppingCart.clearCart();
		}
		request.getSession().setAttribute("shoppingCartItemCount", 0);
	}

	/**
	 * Delete items from cart
	 * @param fileUris
	 */
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	@ResponseBody
	public void deleteFromCart(@RequestParam("uris") String fileUris) {
		FileShoppingCart shoppingCart = (FileShoppingCart)request.getSession().getAttribute("shoppingCart");
		if (shoppingCart != null && fileUris!=null) {
			String[] uris = fileUris.split("\\,");
			for(int f=0; f<uris.length;f++){
				shoppingCart.removeAnItem(uris[f]);
			}
		}
		request.getSession().setAttribute("shoppingCartItemCount", listCart().size());
	}

	/**
	 * Get shopping cart from session if present, but don't create one if not present (saves storing unnecesary session state)
	 * @return FileShoppingCart if stored in session, or <code>null</code> if not stored
	 */
	private FileShoppingCart getCartFromSession() {
		FileShoppingCart shoppingCart = (FileShoppingCart)request.getSession().getAttribute("shoppingCart");
		return shoppingCart;
	}

	private FileShoppingCart getCartFromSessionAndCreateIfNotThere() {
		FileShoppingCart shoppingCart = (FileShoppingCart)request.getSession().getAttribute("shoppingCart");
		if (shoppingCart == null) {
			//log.info("no shopping cart, create one");
			shoppingCart = FileShoppingCart.instance();
			request.getSession().setAttribute("shoppingCart", shoppingCart);
		}
		return shoppingCart;

	}
}

