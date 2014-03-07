
var avusGetURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/collection/avus';
var avusAddURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/collection/avus/add';
var avusDeleteURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/collection/avus/delete';


/**
 * Get list of AVUs for a collection
 */
function getCollectionAVUs(uri) {

	$("#error").hide();
	$('#collection-avu-loading').show();
	
    $.ajax({
    url: avusGetURL,
    dataType: 'json',
    data: {"uri" : uri},
    success: function(obj){
    	var html = "<table><tr><th class='first'></th><th>Attribute</th><th>Value</th><th>Unit</th></tr>";
    	var i = 0;
    	while( i < obj.length )
    	{
    		var avu = obj[i];
    		var rowClass = "row-a";
    		if (i % 2 == 1)
    			rowClass = "row-b";
    		
    		html += "<tr class='"+ rowClass + "'>";
    		html += "   <td class='first'>";
    		html += "		<input type='image' src='images/icons/delete_small.png' title='remove' onclick=\"askConfirmationDelete('Delete?','"+uri+"','avu_attribute"+i+"','avu_value"+i+"','avu_unit"+i+"')\" />";
    		html += "	</td>";
    		html += "   <td><strong>"+avu.attribute+"<strong>";
    		html += "		<input id='avu_attribute"+i+"' type='hidden' value='"+avu.attribute+"'/></td>";
    		html += "   <td>"+avu.value;
    		html += "   	<input id='avu_value"+i+"' type='hidden' value='"+avu.value+"'/></td>";
    		html += "   <td>"+avu.unit;
    		html += "   	<input id='avu_unit"+i+"' type='hidden' value='"+avu.unit+"'/></td>";
    		html += "</tr>";
    		i++;
    	}
    	html += "</table>";

    	$("#collection-avu-table").html(html);
    	
      },
    error: function() { 
    	showErrorDialog("Error: could not load the list of AVUs for this collection");
    }
  });
  $('#collection-avu-loading').hide();
}

/**
 * Add AVU to collection
 */
function addCollectionAVU(uri, attributeId, valueId, unitId) {

	$("#error").hide();
	
	var attribute = document.getElementById(attributeId).value;
	var value = document.getElementById(valueId).value;
	var unit = "";
	var unitInput = document.getElementById(unitId);
	if (unitInput!=null)
		unit = unitInput.value;
	
    $.ajax({
    url: avusAddURL,
    dataType: 'json',
    data: {	"uri" : uri,
    		"attribute" : attribute,
    		"value" : value,
    		"unit" : unit},
    success: function(obj){
    	getCollectionAVUs(uri);
    	showInfoDialog("AVU successfully added.");
      },
    error: function() { 
    	showErrorDialog("This AVU could not be added. Make sure you have the right permissions.");
    }
  });
}

/**
 * Delete AVU from collection
 */
function deleteCollectionAVU(uri, attributeId, valueId, unitId) {

	$("#error").hide();
	
	var attribute = document.getElementById(attributeId).value;
	var value = document.getElementById(valueId).value;
	var unit = "";
	var unitInput = document.getElementById(unitId);
	if (unitInput!=null)
		unit = unitInput.value;
	
	$.ajax({
    url: avusDeleteURL,
    dataType: 'json',
    data: {	"uri" : uri,
			"attribute" : attribute,
			"value" : value,
    		"unit" : unit},
    success: function(obj){
    	getCollectionAVUs(uri);
      },
    error: function() { 
    	showErrorDialog("This AVU could not be deleted. Make sure you have the right permissions.");
    }
  });
}

/**
 * Display confirmation message
 */
function askConfirmationDelete(confirmationString, uri, attributeId, valueId, unitId)
{
	var dialog_buttons = {};
	dialog_buttons['Ok'] = function(){ 
		$(this).dialog('close');
		deleteCollectionAVU(uri, attributeId, valueId, unitId);
	};
	dialog_buttons['Cancel'] = function(){ $(this).dialog('close'); };
	
	var html = "";
	
	//html += "<div class='ui-state-highlight'>";
	html += "	<p style='text-align:left'>";
	html += "		<span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;'></span>&nbsp;";
	html += 		confirmationString;
	html += "	</p>";
	//html += "</div>";
	
	$("#confirm-message").html(html);
	$("#confirm-message").dialog({ 
		resizable: false,
		autoOpen: false,
		modal: true,
		buttons: dialog_buttons 
	});
	$("#confirm-message").dialog("open");
}

