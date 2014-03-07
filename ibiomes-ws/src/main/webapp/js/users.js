
var usersURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/users';
var usergroupsURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/usergroups';
var updatePwdURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/users/$/updatepwd';


/**
 * Get list of users
 */
function getUsers() {
    $.ajax({
    url: usersURL,
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
    url: usersURL + "/" + username,
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
    	showErrorDialog("Error: cannot retrieve user information.");
    }
  });
}

/**
 * Get list of group memberships for the given user
 */
function getUserGroupsForUser(username) {
    $.ajax({
    url: usersURL + "/" + username + "/usergroups",
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
	    		html += "<li><a class=\"link\" href=\"user.do?name=\""+ group.name +"\">"+ group.name +"</a></li>";
	    		i++;
	    	}
	    	html += "</ul>";
	    	$("#user-memberships").html(html);
    	}
    	else html = "<p>No membership found.</p>";
    	
      },
    error: function() { 
    	$("#user-memberships").html("<p>The list of memberships could not be retrieved</p>");
    }
  });
}

/**
 * Get list of user groups
 */
function getUserGroups() {
    $.ajax({
    url: usergroupsURL,
    dataType: 'json',
    success: function(obj){
    	var html = "<table><tr><th class='first'>Type</th><th>Name</th><th>Zone</th></tr>";
    	var i = 0;
    	while( i < obj.length )
    	{
    		var group = obj[i];
    		
    		html += "<tr>";
    		html += "   <td class='first'><img class='icon' src='images/icons/users_small.png'/></td>";
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
 */
function updateUserPassword(username, oldPwd, newPwd1, newPwd2) {
	
	if (newPwd1!=newPwd2){
		showErrorDialog("Error: your new password doesnt match ");
	}
	else{
		
		var pwdUrl = updatePwdURL.replace("$",username);
		alert(pwdUrl);
		
	    $.ajax({
	    url: pwdUrl,
	    dataType: 'json',
	    data: {	
	    	"oldPwd" : oldPwd,
    		"newPwd" : newPwd1},
	    success: function(obj){
	    	
	    	showInfoDialog("Your password was successfully updated.");
	      },
	    error: function() { 
	    	showErrorDialog("Error: cannot update your pasword.");
	    }
	  });
	}
}

/**
 * Get list of latest uploads for the given user
 */
function getUploadsForUser(username) {
    $.ajax({
    url: usersURL + "/" + username + "/uploads",
    dataType: 'json',
    //data: data,
    success: function(obj){
    	
    	var html = "";
    	var i = 0;
    	if (obj != null && obj.length > 0)
    	{
    		html = "<table>";
        	html += "	<tr>";
        	html += "		<th class=\"first\" width=\"30px\">Info</th>";
        	html += "		<th>Name</th>";
        	html += "		<th>Method</th>";
        	html += "		<th>Molecule</th>";
        	html += "		<th>Created</th>";
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
	    			moleculeListStr.substring(0,moleculeListStr.length-2);
	    		}

	    		var rowClass = "row-a";
	    		if (i % 2 == 1)
	    			rowClass = "row-b";
	    		
	    		html += "<tr class='"+ rowClass + "'>";
	    		html += "	<td class=\"first\">";
	    		html += "		<a class=\"link\" href=\"collection.do?uri="+ sim.fileCollection.absolutePath+ "\">";
	    		html += "			<img class=\"icon\" src=\"images/icons/info_small.png\" title=\"View details\" />";
	    		html += "		</a>";
	    		html += "	</td>";
	    		html += "	<td>"+ sim.fileCollection.name +"/></td>";
	    		html += "	<td>"+ sim.method.name +"/></td>";
	    		html += "	<td>" + moleculeList + "</td>";
	    		html += "	<td>"+ sim.registrationDate+ "/></td>";
	    		html += "</tr>";    		

	    		i++;
	    	}
	    	html += "</table>";
	    	
    	}
    	else html = "<p>No data found.</p>";
    	
    	$("#user-uploads").html(html);
      },
    error: function() { 
    	$("#user-uploads").html("<p>The list of uploads could not be retrieved</p>");
    }
  });    
}



