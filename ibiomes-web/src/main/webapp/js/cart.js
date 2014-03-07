
var getCartItemsURL = contextPath + '/rest/services/cart/list';
var addCartItemURL = contextPath + '/rest/services/cart/add';
var deleteCartItemsURL = contextPath + '/rest/services/cart/delete';
var clearCartItemsURL = contextPath + '/rest/services/cart/clear';
var getNumberItemsInCartURL = contextPath + '/rest/services/cart/count';

/**
 * Get list of items in shopping cart
 */
function loadCartItems() {
	$("#cartItems").hide();
	$("#cartItemsLoading").show();
	$("#cartItemList").empty();
	
    $.ajax({
	    url: getCartItemsURL,
	    dataType: 'json',
	    success: function(obj){
	    	var html = "";
	    	var i = 0;
	    	if (obj != null) {
		    	while( i < obj.length )
		    	{
		    		var item = obj[i];
		    		html += "<option value='"+ item +"'>" + item + "</option>";
		    		i++;
		    	}
		    	$("#nitems-cart").html(" ("+ obj.length +")");
	    	}
	    	else $("#nitems-cart").html("");
	    	
	    	$("#cartItemsLoading").hide();
	    	$("#cartItems").show();
	    	$("#cartItemList").append(html);
	    },
	    error: function() { 
	    	showErrorDialog("Error: could not load the list of items in the cart");
	    	$("#cartItemsLoading").hide();
	    	$("#cartItems").show();
	    }
    });
}

/**
 * Add item to shopping cart
 * @param formId ID of the form with URIs of items (file/collection) to add to the cart
 */
function addItemsToCart(formId) {

	var form = $("#"+formId).serializeArray();
	
	var uris = "";
	var p = 0;
	while (p < form.length){
		if (form[p].name == "uri")
			uris += form[p].value + ",";
		p++;
	}
	uris = uris.substring(0,uris.length-1);
    
	$.ajax({
    url: addCartItemURL,
    dataType: 'json',
    data: {	"uris" : uris},
    success: function(obj){
    	refreshNumberOfItemsInCart();
    	showInfoDialog("Items successfully added to your cart.");
      },
    error: function() { 
    	showErrorDialog("These items could not be added to your cart. Make sure you have the right permissions.");
    }
  });
}

/**
 * Add single item to shopping cart
 * @param uri URI of item (file/collection) to add to the cart
 */
function addItemToCart(uri) {
    
	$.ajax({
    url: addCartItemURL,
    dataType: 'json',
    data: {	"uris" : uri},
    success: function(obj){
    	refreshNumberOfItemsInCart();
    	showInfoDialog("Item successfully added to your cart.");
      },
    error: function() { 
    	showErrorDialog("This item could not be added to your cart. Make sure you have the right permissions.");
    }
  });
}

/**
 * Remove item(s) from cart
 * @param uris
 */
function removeItemsFromCart() {
	
	var uris = ($("#cartItemList").val() || []).join(',');
	if (uris != null && uris.length>0)
	{
		$.ajax({
		    url: deleteCartItemsURL,
		    dataType: 'json',
		    data: {	"uris" : uris},
		    success: function(obj){
		    	loadCartItems();
		      },
		    error: function() { 
		    	showErrorDialog("The items '"+uris+"' could not be removed from the cart.");
		    }
		});
	}
	else{
		showErrorDialog("Select the items you want to delete first");
	}
}

/**
 * Remove all the item from the cart
 */
function clearCart() {
	
	$.ajax({
    url: clearCartItemsURL,
    dataType: 'json',
    success: function(obj){
    	loadCartItems();
      },
    error: function() { 
    	showErrorDialog("The cart could not be cleared.");
    }
  });
}

/**
 * Get the number of items in the cart and update GUI
 */
function refreshNumberOfItemsInCart() 
{
	$("#nitems-cart").empty();
	
	$.ajax({
	    url: getNumberItemsInCartURL,
	    dataType: 'json',
	    success: function(nItems){
	    	$("#nitems-cart").html(" ("+ nItems +")");
	      },
	    error: function() { 
	    	//showErrorDialog("Cannot retrieve the number of items in the cart");
	    }
	});
}

/**
 * Load shopping cart applet
 * @param appletDivId
 */
function loadCartApplet(appletDivId, code, codebase, archive, width, height, zone, host, port, user, defaultStorageResource, password, key){
		
	var appletDiv = $("#"+appletDivId);
	appletDiv.empty();
	
	var appletTagDiv = document.createElement('div');
	appletTagDiv.setAttribute('id', 'appletTagDiv');
	
	var a = document.createElement('applet');
	appletTagDiv.appendChild(a);
	a.setAttribute('code', code);
	a.setAttribute('codebase', codebase);
	a.setAttribute('archive', archive);
	a.setAttribute('width', width);
	a.setAttribute('height', height);
	
	var p = document.createElement('param');
	p.setAttribute('name', 'mode');
	p.setAttribute('value', 2);
	a.appendChild(p);

	p = document.createElement('param');
	p.setAttribute('name', 'displayMode');
	p.setAttribute('value', 3);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'host');
	p.setAttribute('value', host);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'port');
	p.setAttribute('value', port);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'zone');
	p.setAttribute('value', zone);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'user');
	p.setAttribute('value', user);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'defaultStorageResource');
	p.setAttribute('value', defaultStorageResource);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'password');
	p.setAttribute('value', password);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'key');
	p.setAttribute('value', key);
	a.appendChild(p);
	
	appletDiv.append(appletTagDiv);

}






