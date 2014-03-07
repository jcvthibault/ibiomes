
var getResourceListURL = contextPath + '/rest/services/resources';
var setDefaultResourceURL = contextPath + '/rest/services/resources/select';

/**
 * Get list of resources available
 */
function loadListOfResources(defaultResc)
{
	$.ajax({
	    url: getResourceListURL,
	    dataType: 'json',
	    success: function(obj){
	    	var html = "<option value=''>default</option>";
	    	var i = 0;
	    	while( i < obj.length )
	    	{
	    		var resc = obj[i];
	    		html += "<option value='"+resc+"'";
	    		if (defaultResc != null && resc == defaultResc)
	    			html += " selected='selected' ";
	    		html += ">" + resc + "</option>";
	    		i++;
	    	}
	    	$("#resourceList").html(html);
	      },
	    error: function() { 
	    	showErrorDialog("Error: cannot retrieve list of resources");
	    }
    });
}

/**
 * Set resource as default
 */
function setDefaultResource() 
{
	var resc = $("#resourceList").val();
	if (resc != null && resc != ""){
	
	    $.ajax({
		    url: setDefaultResourceURL + "/" + resc,
		    dataType: 'json',
		    //data: data,
		    success: function(rescName){
		    	if (rescName != null)
		    		showInfoDialog("New default resource: '"+ rescName + "'");
		    	else
		    		showErrorDialog("Error: cannot set the default resource to '"+ resc + "'");
		      },
		    error: function() { 
		    	showErrorDialog("Error: cannot set the default resource to '"+ resc + "'");
		    }
		});
	}
}


