
var getUsersURL = contextPath + '/rest/services/users';
var getUserUploadsURL = contextPath + '/rest/services/users/$/uploads';
var getGroupsForUserURL = contextPath + '/rest/services/users/$/usergroups';
var getUserGroupsURL = contextPath + '/rest/services/usergroups';
var getUsersInGroupURL = contextPath + '/rest/services/usergroups/$/users';
var updatePwdURL = contextPath + '/rest/services/users/$/updatepwd';


/**
 * Get list of users
 */
function getUsers() {
    $.ajax({
    url: getUsersURL,
    dataType: 'json',
    success: function(obj){
    	var html = "<table><tr><th class='first'>Type</th><th>Name</th><th>Info</th><th>Comment</th></tr>";
    	var i = 0;
    	while( i < obj.length )
    	{
    		var user = obj[i];
    		
    		var usertype = "user";
    		if (user.type == "rodsgroup")
    			usertype = "users";
    		
    		html += "<tr>";
    		html += "   <td class='first'><img class='icon' src='images/icons/"+ usertype +"_small.png'/></td>";
    		html += "   <td><strong>"+user.name+"<strong></td><td>"+user.info+"</td><td>"+user.comments+"</td>";
    		html += "</tr>";
    		i++;
    	}
    	html += "</table>";

    	$("#user-table").html(html);
    	
      },
    error: function() { 
    	$("#user-table").html("<p>The list of users could not be retrieved</p>");
    }
  });
}

/**
 * Get user information
 */
function getUser(username) {
    $.ajax({
    url: getUsersURL + "/" + username,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	
    	$("#user-name").html(obj.name);
    	$("#user-info").html(obj.info);
    	$("#user-comments").html(obj.comments);
    	$("#user-dn").html(obj.dn);
    	$("#user-creationTime").html(obj.creationTime);
    	
      },
    error: function() { 
    	showErrorDialog("Error: cannot retrieve user information for '"+username+"'.");
    }
  });
}

/**
 * Get list of group memberships for the given user
 */
function getUserGroupsForUser(username) {
	
	var grpUrl = getGroupsForUserURL.replace("$",username);
	
    $.ajax({
    url: grpUrl,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	
    	var html = "<ul>";
    	var i = 0;
    	if (obj != null && obj.length > 0)
    	{
	    	while( i < obj.length )
	    	{
	    		var group = obj[i];
	    		html += "<li><a class=\"link\" href=\"user.do?name="+ group.name + "\">"+ group.name +"</a></li>";
	    		i++;
	    	}
	    	html += "</ul>";
	    	$("#user-memberships").html(html);
    	}
    	else html = "<p>No membership found.</p>";
    	
      },
    error: function() { 
    	$("#user-memberships").html("<p>The list of memberships for '"+username+"' could not be retrieved</p>");
    }
  });
}

/**
 * Get list of latest uploads for the given user
 * @param username
 */
function getUploadsForUser(username) {
	
	var uploadsUrl = getUserUploadsURL.replace("$",username);
	
    $.ajax({
    url: uploadsUrl,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	
    	var html = "";
    	var i = 0;
    	if (obj != null && obj.length > 0)
    	{
    		html = "<table>";
        	html += "	<tr>";
        	html += "		<th>Experiment</th>";
        	html += "		<th>Software</th>";
        	html += "		<th>Method</th>";
        	html += "		<th>Molecule</th>";
        	html += "		<th>Published</th>";
        	html += "	</tr>";
        	
	    	while( i < obj.length )
	    	{
	    		var sim = obj[i];
	    		
	    		var moleculeListStr = "";
	    		var m = 0;
	    		var moleculeList = sim.molecularSystem.moleculeTypes;
	    		if (moleculeList != null && moleculeList.length>0){
	    			for (m=0; m<moleculeList.length; m++){
	    				moleculeListStr += moleculeList[m] + " / ";
	    			}
	    			moleculeListStr = moleculeListStr.substring(0,moleculeListStr.length-3);
	    		}
	    		var softwareListStr = "";
    			var softwareList = sim.softwarePackages;
	    		if (softwareList!=null && softwareList.length>0){
	    			for (m=0; m<softwareList.length; m++){
	    				softwareListStr += softwareList[m] + ", ";
	    			}
	    			softwareListStr = softwareListStr.substring(0,softwareListStr.length-2);
	    		}
	    		var methodStr = "?";
				if (sim.method != null)
					methodStr = sim.method.name;
	    		var rowClass = "row-a";
	    		if (i % 2 == 1)
	    			rowClass = "row-b";
	    		
	    		html += "<tr class='"+ rowClass + "'>";
	    		html += "	<td class='first'>";
	    		html += "		<a class='link' href='collection.do?uri="+ sim.fileCollection.absolutePath+ "'>";
	    		html += "			<img class='icon' src='images/icons/folder_full_small.png' title='Go to experiment page...' />";
	    		html += "		</a>";
	    		html += "		<a class='link' href='collection.do?uri="+ sim.fileCollection.absolutePath+ "'>";
	    		html += "		" + sim.title;
	    		html += "		</a>";
	    		html += "	</td>";
	    		html += "	<td>"+ softwareListStr +"</td>";
	    		html += "	<td>"+ methodStr +"</td>";
	    		html += "	<td>" + moleculeListStr + "</td>";
	    		html += "	<td>"+ sim.registrationDate+ "</td>";
	    		html += "</tr>";    		

	    		i++;
	    	}
	    	html += "</table>";
	    	
    	}
    	else html = "<p>No data found.</p>";
    	
    	$("#user-uploads").html(html);
    	$("#uploads-loading").hide();
      },
    error: function() { 
    	$("#user-uploads").html("<p>The list of uploads by '"+username+"' could not be retrieved</p>");
    	$("#uploads-loading").hide();
    }
  });    
}

/**
 * Get list of user groups
 */
function getUserGroups() {
    $.ajax({
    url: getUserGroupsURL,
    dataType: 'json',
    success: function(obj){
    	var html = "<table><tr><th class='first'>Type</th><th>Name</th><th>Zone</th></tr>";
    	var i = 0;
    	while( i < obj.length )
    	{
    		var group = obj[i];
    		
    		html += "<tr>";
    		html += "   <td class='first'><a href=\"user.do?name="+ group.name +"\"><img class='icon' src='images/icons/users_small.png'/></a></td>";
    		html += "   <td><strong>"+group.name+"<strong></td><td>"+group.zone+"</td>";
    		html += "</tr>";
    		i++;
    	}
    	html += "</table>";

    	$("#usergroup-table").html(html);
    	
      },
    error: function() { 
    	$("#usergroup-table").html("<p>Error: cannot retrieve user groups.</p>");
    }
  });
}

/**
 * Update user password
 * @param username
 * @param oldPwd
 * @param newPwd1
 * @param newPwd2
 */
function updateUserPassword(username, oldPwd, newPwd1, newPwd2) {
	
	if ($("#"+newPwd1).val()!= $("#"+newPwd2).val()){
		showErrorDialog("Error: check your new password.");
	}
	else{
		var oldPwdVal = $("#"+oldPwd).val();
		var newPwdVal = $("#"+newPwd1).val();
		
		var pwdUrl = updatePwdURL.replace("$",username);
		
	    $.ajax({
	    url: pwdUrl,
	    dataType: 'json',
	    data: {	
	    	"username" : username,
	    	"oldPwd" : oldPwdVal,
    		"newPwd" : newPwdVal},
	    success: function(obj)
	    {
			$("#"+oldPwd).val("");
			$("#"+newPwd1).val("");
			$("#"+newPwd2).val("");
			
	    	if (obj.success)
	    		showInfoDialog(obj.message);
	    	else 
	    		showErrorDialog(obj.message);
	      },
	    error: function() { 
	    	showErrorDialog("Error service: cannot update pasword.");
	    }
	  });
	}
}

