
var getAclURL = contextPath + '/rest/services/permissions/list';
var updateAclURL = contextPath + '/rest/services/permissions/update';
var updateInheritFlagAclURL = contextPath + '/rest/services/permissions/setinherit';
var removeAccessURL = contextPath + '/rest/services/permissions/remove';
var resetAclURL = contextPath + '/rest/services/permissions/reset';

/**
 * Get list of AVUs for a file
 * @param uri File URI
 */
function getAcl(uri) {

	showLoadingBarSmall("acl-table");
	
    $.ajax({
    url: getAclURL,
    dataType: 'json',
    data: {"uri" : uri},
    success: function(obj){
    	
    	var html = "<table>";
    	html += "<tr><th class='first'>Users</th><th>Zone</th><th>Permissions</th><th></th></tr>";
    	var i = 0;
    	while( i < obj.permissions.length )
    	{
    		var ac = obj.permissions[i];
    		var rowClass = "row-a";
    		if (i % 2 == 1)
    			rowClass = "row-b";
    		
    		html += "<tr class='"+ rowClass + "'>";
    		html += "	<td class='first'><strong>" + ac.username + "</strong</td>";
    		html += "	<td>" + ac.zone + "</td>";
    		html += "	<td>" + ac.permission + "</td>";
    		html += "	<td><input type='checkbox' name='userACL' value='" + ac.username + "'/></td>";
    		html += "</tr>";
    		
    		i++;
    	}
    	html += "</table>";

    	hideLoadingBar("acl-table");
    	$("#acl-table").html(html);
    	
      },
    error: function() { 
    	hideLoadingBar("acl-table");
    	showErrorDialog("Error: could not load the list of permissions for '"+uri+"'");
    }
  });
}

/**
 * Update ACL
 * @param uri
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function updateAcl(uri, formId) {
	
	showLoadingInModal();
	
	var form = $("#"+formId).serializeArray();
	var users = "";
	var permission = "";
	var recursive = "false";
	var p = 0;
	while (p < form.length){
		if (form[p].name == "userACL")
			users = form[p].value;
		else if (form[p].name == "permissionACL")
			permission = form[p].value;
		else if (form[p].name == "recursiveACL")
			recursive = "true";
		p++;
	}
	
    $.ajax({
    url: updateAclURL,
    dataType: 'json',
    data: {	
    	"uri" : uri,
    	"users" : users,
    	"permission" : permission,
    	"recursive" : recursive
    },
    success: function(obj){
    	hideLoadingInModal();
    	getAcl(uri);
      },
    error: function() {
    	hideLoadingInModal();
    	showErrorDialog("Permissions for '"+uri+"' could not be updated.");
    }
  });
}

/**
 * Remove access
 * @param uri
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function removePermissions(uri, formId) {
	
	var form = $("#"+formId).serializeArray();
	var users = "";
	var p = 0;
	while (p < form.length){
		if (form[p].name == "userACL")
			users += form[p].value + ",";
		p++;
	}
	users = users.substring(0,users.length-1);
	
    $.ajax({
    url: removeAccessURL,
    dataType: 'json',
    data: {	
    	"uri" : uri,
    	"users" : users
    },
    success: function(obj){
    	showInfoDialog("Permissions successfully removed");
    	getAcl(uri);
      },
    error: function() { 
    	showErrorDialog("Access to '"+uri+"' for '"+users+"' could not be removed.");
    }
  });
}

/**
 * Update inherit flag
 * @param uri
 */
function updateInheritFlag(uri, formId) {
	
	var form = $("#"+formId).serializeArray();
	var users = "";
	var permission = "";
	var p = 0;
	while (p < form.length){
		if (form[p].name == "userACL")
			users = form[p].value;
		else if (form[p].name == "permissionACL")
			permission = form[p].value;
		p++;
	}
	
    $.ajax({
    url: updateInheritFlagAclURL,
    dataType: 'json',
    data: {	
    	"uri" : uri,
    	"users" : users,
    	"permission" : permission
    },
    success: function(obj){
    	showInfoDialog("Permissions successfully updated");
    	getAcl(uri);
      },
    error: function() { 
    	showErrorDialog("Permissions for '"+uri+"'could not be updated.");
    }
  });
}

/**
 * Reset ACL (leave only permissions for owners)
 * @param uri
 */
function resetAcl(uri) {
	
	$.ajax({
    url: resetAclURL,
    dataType: 'json',
    data: {	"uri" : uri},
    success: function(obj){
    	showInfoDialog("Permissions successfully reset");
      },
    error: function() { 
    	showErrorDialog("Permissions for '"+uri+"' could not be updated.");
    }
  });
}





