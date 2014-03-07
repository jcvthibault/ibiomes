
/**
 * Trigger action on files
 * @param actionSelectId
 * @param formId
 */
function triggerFileAction(actionSelectId, formId)
{
	var formString = $("#"+formId).serialize();
	var action = $("#"+actionSelectId).val();
	
	if (action == "animation"){
		target = "jmol.do?" + formString;
		window.open(target);
	}
	else if (action == "comparison"){
		target = "jmol.do?script=comparison&" + formString;
		window.open(target);
	}
	else if (action == "delete"){
		deleteFiles(formId);
	}
	else if (action == "unregister"){
		unregisterFiles(formId);
	}
	else if (action == "addtocart"){
		addItemsToCart(formId);
	}
	else if (action == "removefromcart"){
		removeItemsFromCart(formId);
	}
	else if (action == "clearcart"){
		clearCart();
	}
	else if (action == "hide"){
		hideFiles(formId);
	}
	else if (action == "present"){
		presentFilesAsAnalysis(formId);
	}
}
