
var getCollectionAVUsURL = contextPath + '/rest/services/collection/avus';
var addCollectionAVUsURL = contextPath + '/rest/services/collection/avus/add';
var updateCollectionAVUsURL = contextPath + '/rest/services/collection/avus/update';
var deleteCollectionAVUsURL = contextPath + '/rest/services/collection/avus/delete';
var getLatestCollectionsURL = contextPath + '/rest/services/collections/latest';
var getSubdirectoriesURL = contextPath + '/rest/services/collections/under';
var updateExperimentMethodURL = contextPath + '/rest/services/collection/updatemethod';
var updateExperimentTopologyURL = contextPath + '/rest/services/collection/updatetopology';
var renameExperimentURL = contextPath + '/rest/services/collection/rename';
var getReferencingExperimentSetsURL = contextPath + '/rest/services/collection/sets';
var getExperimentImagesURL = contextPath + '/rest/services/thumbnail/experiment/analysis';
var getExperimentAnalysisLinksURL  = contextPath + '/rest/services/experiment/analysis/links';

/**
 * Get full list of AVUs for a collection
 */
function getCollectionAVUs(uri) {

	$('#collection-avu-loading').show();
	
    $.ajax({
    url: getCollectionAVUsURL,
    dataType: 'json',
    data: {"uri" : uri},
    success: function(obj){
    	if (obj.success){
	    	var data = obj.data;
	    	var html = "";
	    	if (data != null && data.length>0){
		    	html = "<table><tr><th class='first'></th><th>Attribute</th><th>Value</th><th>Unit</th></tr>";
		    	var i = 0;
		    	while( i < data.length )
		    	{
		    		var avu = data[i];
		    		var avuAttribute = avu.attribute.term;
		    		var avuAttributeDef = avu.attribute.definition;
		    		var avuValue = avu.value.term;
		    		var avuUnit = avu.unit;
		    		if (avuUnit == null)
		    			avuUnit = "";
		    		
		    		var rowClass = "row-a";
		    		if (i % 2 == 1)
		    			rowClass = "row-b";
		    		
		    		html += "<tr class='"+ rowClass + "'>";
		    		html += "   <td class='first'>";
		    		html += "		<input type='image' src='images/icons/delete_small.png' title='remove' onclick=\"askConfirmationDeleteCollectionAVUs('Delete?','"+uri+"','avu_attribute"+i+"','avu_value"+i+"','avu_unit"+i+"')\" />";
		    		html += "	</td>";
		    		if (avu.attribute.standard){
		    			html += "   <td><img class='icon' src='images/icons/info_small.png' title='"+avuAttributeDef+"'/> <strong>"+avuAttribute+"<strong>";
		    		} else {
		    			html += "   <td><strong>"+avu.attribute.code+"<strong>";
		    		}
		    		html += "		<input id='avu_attribute"+i+"' type='hidden' value='"+avu.attribute.code+"'/></td>";
		    		html += "   <td>"+avu.value.code;
		    		html += "   	<input id='avu_value"+i+"' type='hidden' value='"+avuValue +"'/></td>";
		    		html += "   <td>"+avuUnit;
		    		html += "   	<input id='avu_unit"+i+"' type='hidden' value='"+avuUnit+"'/></td>";
		    		html += "</tr>";
		    		i++;
		    	}
		    	html += "</table>";
	    	}
	    	else html = "<p>No metadata available for this collection.</p>";
    		
	    	$("#collection-avu-table").html(html);
	    	$('#collection-avu-loading').hide();
    	}
    	else {
    		$('#collection-avu-loading').hide();
    		showErrorDialog(obj.message);
    	}
    	
      },
    error: function() { 
    	showErrorInline("Error: could not load the list of AVUs for this collection", "collection-avu-table");
    	$('#collection-avu-loading').hide();
    }
  });
}

/**
 * Get list of AVUs for a collection
 */
function getCollectionAvuListForAttribute(uri, attribute, targetId) {
	
	var target = $("#"+targetId);
	target.empty();
	
    $.ajax({
    url: getCollectionAVUsURL,
    dataType: 'json',
    data: {
    	"uri" : uri,
    	"attribute" : attribute
    },
    success: function(obj){
    	if (obj.success){
	    	var data = obj.data;
	    	var html = "";
	    	var i = 0;
	    	while( i < data.length )
	    	{
	    		var avu = data[i];
	    		html += "<option value='"+avu.value.code+"' title='"+avu.value.term+"'>"+avu.value.term+"</option>";
	    		i++;
	    	}
	    	target.html(html);
    	}
	    else showErrorDialog(obj.message);
      },
    error: function() { 
    	showErrorDialog("Error: could not load the list of values for attribute '"+attribute+"'");
    }
  });
}

/**
 * Get list of AVUs for a collection
 */
function getCollectionAvuRowsForAttribute(uri, attribute, targetId) {
	
	var target = $("#"+targetId);
	target.empty();
	
    $.ajax({
    url: getCollectionAVUsURL,
    dataType: 'json',
    data: {
    	"uri" : uri,
    	"attribute" : attribute
    },
    success: function(obj){
    	if (obj.success){
	    	var data = obj.data;
	    	var html = "<table class='layout-tight'>";
	    	var i = 0;
	    	while( i < data.length )
	    	{
	    		var avu = data[i];
	    		var avuId = targetId + '_' + avu.attribute.code + '_' + i;
	    		var attrId = avuId + '_attribute';
	    		var valId = avuId + '_value';
	    		html += "<tr>";
	    		html += "<td><img class='icon pointer' src='images/icons/delete_small.png' title='remove' onclick=\"deleteCollectionAvuFromTable('"+uri+"','"+avu.attribute.code+"','"+avu.value.term+"','"+targetId+"')\"/><td>";
	    		html += "<td><input id='"+attrId+"' type='hidden' value='"+avu.attribute.code+"'/>";
	    		html += "<td><input id='"+valId+"'  type='hidden' value='"+avu.value.term+"'/>";
	    		html += "<td>"+avu.value.term+"<td>";
	    		html += "</tr>";
	    		i++;
	    	}
	    	html += "</table>";
	    	target.html(html);
    	}
	    else showErrorDialog(obj.message);
      },
    error: function() { 
    	showErrorDialog("Error: could not load the list of values for attribute '"+attribute+"'");
    }
  });
}

/**
 * Add AVU to collection
 * @param uri Collection URI
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function addCollectionAVU(uri, attributeId, valueId, unitId) {
	
	var attribute = $("#"+attributeId).val();
	var value = $("#"+valueId).val();
	var unit = "";
	var unitInput = $("#"+unitId);
	if (unitInput!=null)
		unit = unitInput.val();
	
    $.ajax({
    url: addCollectionAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
    		"attribute" : attribute,
    		"value" : value,
    		"unit" : unit},
    success: function(obj){
    	if (!obj.success){
    		showErrorDialog(obj.message);
    	}
    	else showInfoDialog("Metadata successfully added.");
    	getCollectionAVUs(uri);
      },
    error: function() { 
    	showErrorDialog("The AVU ["+attribute+" = "+value+"] could not be added. Make sure you have the right permissions.");
    }
  });
}

/**
 * Add AVU to collection and 
 * @param uri Collection URI
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function addCollectionAvuAggregate(uri, attribute, valueId, targetId, delimiter, nValues) 
{	
	var value = "";
	var i=0;
	for (i=0; i < nValues; i++){
		value += $("#"+valueId+i).val() + delimiter;
	}
	value = value.substring(0, value.length - delimiter.length);
	
    $.ajax({
    url: addCollectionAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
    		"attribute" : attribute,
    		"value" : value},
    success: function(obj){
    	if (!obj.success){
    		showErrorDialog(obj.message);
    	}
    	getCollectionAvuListForAttribute(uri, attribute, targetId);
      },
    error: function() { 
    	showErrorDialog("The AVU ["+attribute+" = "+value+"] could not be added. Make sure you have the right permissions.");
    }
  });
}

/**
 * Add AVU to collection where the attribute can have multiple values
 * @param uri Collection URI
 * @param attribute AVU attribute
 * @param valueId 
 * @param unitId
 * @param targetId
 */
function addCollectionAvuToList(uri, attribute, valueId, targetId) {
	
	var value = $("#"+valueId).val();
	
    $.ajax({
    url: addCollectionAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
    		"attribute" : attribute,
    		"value" : value},
    success: function(obj){
    	if (!obj.success){
    		showErrorDialog(obj.message);
    	} 
    	getCollectionAvuListForAttribute(uri, attribute, targetId);
      },
    error: function() { 
    	showErrorDialog("The AVU ["+attribute+" = "+value+"] could not be added. Make sure you have the right permissions.");
    }
  });
}

/**
 * Add AVU to collection where the attribute can have multiple values
 * @param uri Collection URI
 * @param attribute AVU attribute
 * @param valueId 
 * @param unitId
 * @param targetId
 */
function addCollectionAvuToTable(uri, attribute, valueId, targetId) {
	
	var value = $("#"+valueId).val();
		
    $.ajax({
    url: addCollectionAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
    		"attribute" : attribute,
    		"value" : value},
    success: function(obj){
    	if (!obj.success){
    		showErrorDialog(obj.message);
    	} 
    	getCollectionAvuRowsForAttribute(uri, attribute, targetId);
      },
    error: function() { 
    	showErrorDialog("The AVU ["+attribute+" = "+value+"] could not be added. Make sure you have the right permissions.");
    }
  });
}

/**
 * Add AVU to collection
 * @param uri Collection URI
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function updateCollectionAVU(uri, attributeId, valueId, unitId) {

	var attribute = $("#"+attributeId).val();
	var value = $("#"+valueId).val();
	var unit = "";
	var unitInput = $("#"+unitId);
	if (unitInput!=null)
		unit = unitInput.val();
	
    $.ajax({
    url: updateCollectionAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
    		"attribute" : attribute,
    		"value" : value,
    		"unit" : unit},
    success: function(obj){
    	if (!obj.success){
    		showErrorDialog(obj.message);
    	}
    	else showInfoDialog("Metadata successfully updated.");
    	getCollectionAVUs(uri);
      },
    error: function() { 
    	showErrorDialog("The AVU ["+attribute+" = "+value+"] could not be added. Make sure you have the right permissions.");
    }
  });
}

/**
 * Add AVUs to collection
 * @param uri Collection URI
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function updateCollectionAVUs(uri, formId)
{
	var form = $("#"+formId).serializeArray();
	var formStr = "";
	var p = 0;
	while (p < form.length){
		formStr += form[p].name + "=" + form[p].value + "||";
		p++;
	}
	formStr = formStr.substring(0, formStr.length-2);
		
    $.ajax({
	    url: updateCollectionAVUsURL,
	    dataType: 'json',
	    data: {	
	    	"uri" : uri,
	    	"string" : formStr
	    	},
	    success: function(obj){
	    	if (!obj.success){
	    		showErrorDialog(obj.message);
	    	}
	    	else showInfoDialog("Metadata successfully updated.");
	      },
	    error: function() { 
	    	showErrorDialog("The metadata could not be added. Make sure you have the right permissions.");
	    }
	});
}

/**
 * Delete AVU from collection
 */
function deleteCollectionAVU(uri, attributeId, valueId, unitId) {
	
	var attribute = document.getElementById(attributeId).value;
	var value = document.getElementById(valueId).value;
	var unit = "";
	var unitInput = document.getElementById(unitId);
	if (unitInput!=null)
		unit = unitInput.value;
	
	$.ajax({
    url: deleteCollectionAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
			"attribute" : attribute,
			"value" : value,
    		"unit" : unit},
    success: function(obj){
    	if (!obj.success){
    		showErrorDialog(obj.message);
    	}
    	getCollectionAVUs(uri);
      },
    error: function() { 
    	showErrorDialog("This AVU could not be deleted. Make sure you have the right permissions.");
    }
  });
}

/**
 * Delete AVU from collection from table
 */
function deleteCollectionAvuFromTable(uri, attribute, value, tableId) {
		
	$.ajax({
    url: deleteCollectionAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
			"attribute" : attribute,
			"value" : value},
    success: function(obj){
    	if (obj.success)
    		getCollectionAvuRowsForAttribute(uri, attribute, tableId);
    	else showErrorDialog(obj.message);
      },
    error: function() { 
    	showErrorDialog("This AVU could not be deleted.");
    }
  });
}

/**
 * Delete AVU from collection
 */
function deleteCollectionAvuFromList(uri, attribute, valueId) {
	
	var value = ($("#"+ valueId).val() || []).join('||');
		
	$.ajax({
    url: deleteCollectionAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
			"attribute" : attribute,
			"value" : value},
    success: function(obj){
    	if (obj.success)
    		getCollectionAvuListForAttribute(uri, attribute, valueId);
    	else showErrorDialog(obj.message);
      },
    error: function() { 
    	showErrorDialog("This AVU could not be deleted.");
    }
  });
}

/**
 * Get list of subdirectories in given collection
 */
function getCollectionSubdirectories(uri) {
	
	$("#subdirectories-loading").show();
	
    $.ajax({
    url: getSubdirectoriesURL,
    dataType: 'json',
    data: {"uri" : uri},
    success: function(obj){
    	if (obj.success){
	    	var data = obj.data;
	    	if (data != null && data.length>0)
	    	{
	    		var i = 0;
		    	var html = "<table><tr style='height:0px'>";
		    	while( i < data.length )
		    	{
		    		var subdir = data[i];
		    		    		
		    		var rest = i % 3; 
		    		if (rest == 0)
		    			html += "</tr><tr>";
		    		
		    		html += "<td>";
		    		html += 	"<a class='link' href='collection.do?uri="+ subdir.absolutePath +"'>";
		    		html += 		"<img class='icon' src='images/icons/folder_full.png' />"+ subdir.name;
		    		html += 	"</a>";
		    		html += "</td>";
		    		
		    		if (rest == 1)
		    			html += "<td/>";
		    		else if (rest == 2)
		    			html += "<td/><td/>";
		  
		    		i++;
		    	}
		    	html += "</tr></table>";
		
		    	$("#subdirectories-table").html(html);
	    	}
		    else {
		    	$("#subdirectories-table").html("<p>No sub-directory available</p>");
		    }
	    	$("#subdirectories-loading").hide();
    	}
    	else {
    		$("#subdirectories-loading").hide();
    		showErrorDialog(obj.message);
    	}
      },
    error: function() { 
    	$("#subdirectories-loading").hide();
    	showErrorInline("Error: could not load the list of subdirectories for this collection", "subdirectories-table");
    }
  });
    
}

/**
 * Display confirmation message
 */
function askConfirmationDeleteCollectionAVUs(confirmationString, uri, attributeId, valueId, unitId)
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

/**
 * Get list of latest uploads for the given user
 */
function getLatestUploads(divId) {
	
	showLoadingBarLarge(divId);

    $.ajax({
    url: getLatestCollectionsURL,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	if (obj.success){
	    	var data = obj.data;
	    	var html = "";
	    	var i = 0;
	    	if (data != null && data.length>0)
	    	{
	    		html = "<table>";
	        	html += "	<tr>";
	        	html += "		<th>Experiment</th>";
	        	html += "		<th>Software</th>";
	        	html += "		<th style='text-align:center'>Method</th>";
	        	html += "		<th style='text-align:center'>Molecule</th>";
	        	html += "		<th style='text-align:center'>Owner</th>";
	        	html += "		<th style='text-align:center'>Published</th>";
	        	html += "	</tr>";
	        	
		    	while( i < data.length )
		    	{
		    		var sim = data[i];
		    		
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
		    		var rowClass = "row-a";
		    		if (i % 2 == 1)
		    			rowClass = "row-b";
		    		
		    		html += "<tr class='"+ rowClass + "'>";
		    		html += "	<td class='first'>";
		    		html += "		<a class='link' href='collection.do?uri="+ sim.fileCollection.absolutePath+ "' title='Go to experiment page..'>";
		    		html += "		" + sim.title;
		    		html += "		</a>";
		    		html += "	</td>";
		    		html += "	<td>"+ softwareListStr +"</td>";
		    		html += "	<td style='text-align:center'>"+ sim.method.name +"</td>";
		    		html += "	<td style='text-align:center'>" + moleculeListStr + "</td>";
		    		html += "	<td style='text-align:center'><a class='link' href='user.do?name="+ sim.fileCollection.owner + "'>"+ sim.fileCollection.owner +"</a></td>";
		    		html += "	<td style='text-align:center'>"+ sim.registrationDate+ "</td>";
		    		html += "</tr>";    		
	
		    		i++;
		    	}
		    	html += "</table>";
	    	}
	    	else html = "<p>No data found.</p>";
	
	    	hideLoadingBar(divId);
	    	$("#"+divId).html(html);
    	}
    	else {
	    	hideLoadingBar(divId);
    		showErrorDialog(obj.message);
    	}
      },
    error: function(jqXHR, textStatus, errorThrown) { 
    	hideLoadingBar(divId);
	    if (!isPageBeingRefreshed && textStatus != 'abort'){
	    	showErrorInline("Error: the list of uploads could not be retrieved", "user-uploads");
	    }
    }
  });
}

/**
 * Update method/parameter-related metadata from given file (e.g. MD input file)
 * @param uri Collection URI
 * @param fileUri File URI
 */
function updateMethodFromFile(uri, fileUriInputId) {
	
	var fileUri = $("#"+fileUriInputId).val();
	if (fileUri != null && fileUri.length>0)
	{
		
		$.ajax({
		    url: updateExperimentMethodURL,
		    dataType: 'json',
		    data: {	"uri" : uri,
					"fileUri" : fileUri},
		    success: function(obj){
		    	if (obj.success)
		    		showInfoDialog("Metadata successfully updated.");
		    	else showErrorDialog(obj.message);
		      },
		    error: function() { 
		    	showErrorDialog("The metadata could not be updated. Make sure you have the right permissions.");
		    }
		});
	}
	else {
		showErrorDialog("Please select a file");
	}
}

/**
 * Update topology-related metadata from given file (e.g. PDB file)
 * @param uri Collection URI
 * @param fileUri File URI
 */
function updateTopologyFromFile(uri, fileUriInputId) {
	
	var fileUri = $("#"+fileUriInputId).val();
	if (fileUri != null && fileUri.length>0)
	{
		$.ajax({
		    url: updateExperimentTopologyURL,
		    dataType: 'json',
		    data: {	"uri" : uri,
					"fileUri" : fileUri},
		    success: function(obj){
		    	if (obj.success)
		    		showInfoDialog("Metadata successfully updated.");
		    	else showErrorDialog(obj.message);
		      },
		    error: function() { 
		    	showErrorDialog("The metadata could not be updated. Make sure you have the right permissions.");
		    }
	    });
	}
	else {
		showErrorDialog("Please select a file");
	}
}

/**
 * Rename collection
 * @param uri Collection URI
 * @param newName New collection name
 */
function renameCollection(uri, newNameId)
{
	var newName = $("#"+newNameId).val();
	if (newName.length > 0){
		$.ajax({
		    url: renameExperimentURL,
		    dataType: 'json',
		    data: {	"uri" : uri,
					"name" : newName},
		    success: function(obj){
		    	if (obj.success)
		    		window.location = contextPath + "/editcollection.do?uri="+uri.substring(0, uri.lastIndexOf('/')+1) + newName;
		    	else showErrorDialog(obj.message);
		      },
		    error: function() { 
		    	showErrorDialog("The collection could not be renamed. Make sure the new name is valid.");
		    }
	    });
	}
	else showErrorDialog("The collection name is not valid.");
}

/**
 * Get list of experiment sets referencing this experiment
 * @param uri Collection URI
 */
function loadReferencingExperimentSets(uri, divId)
{
	showLoadingBarLarge(divId);
	
	$.ajax({
	    url: getReferencingExperimentSetsURL,
	    dataType: 'json',
	    data: {	"uri" : uri },
	    success: function(obj)
	    {
	    	if (obj.success){
		    	var html = "";
		    	var data = obj.data;
		    	var i = 0;
		    	if (data != null && data.length > 0)
		    	{
		    		html = "<table>";
		        	html += "	<tr>";
		        	html += "		<th class='first'>Set</th>";
		        	html += "		<th>Description</th>";
		        	html += "		<th>Public</th>";
		        	html += "		<th>Owner</th>";
		        	html += "	</tr>";
		        	
			    	while( i < data.length ){
			    		var set = data[i];
			    		var rowClass = "row-a";
			    		if (i % 2 == 1)
			    			rowClass = "row-b";
			    		var publicStr = "No";
			    		if (set.isPublic)
			    			publicStr = "Yes";
			    		html += "<tr class='"+ rowClass + "'>";
			    		html += "	<td class='first'>";
			    		html += "		<a class='link' href='experimentset.do?id="+ set.id+ "' title='Go to experiment set...'>" + set.name + "</a>";
			    		html += "	</td>";
			    		html += "	<td><i>"+ set.description +"</i></td>";
			    		html += "	<td>" + publicStr + "</td>";
			    		html += "	<td><a class='link' href='user.do?name="+ set.owner + "'>"+ set.owner +"</a></td>";
			    		html += "</tr>";    		
	
			    		i++;
			    	}
			    	html += "</table>";
		    	}
		    	else html = "<p>No experiment set found.</p>";
		    	
		    	hideLoadingBar(divId);
		    	$("#"+divId).html(html);
	    	}
	    	else {
		    	hideLoadingBar(divId);
		    	showErrorDialog(obj.message);
	    	}
	    },
    	error: function(jqXHR, textStatus, errorThrown) { 
	    	hideLoadingBar(divId);
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	showErrorDialog("The list of refrencing experiment sets could not be retrieved");
		    }
	    }
    });
}

/**
 * 
 * @param uri
 * @param divId
 */
function loadThumbnailsForAnalysis(uri, divId){
	
	showLoadingBarSmall(divId);
	
	$.ajax({
	    url: getExperimentImagesURL,
	    dataType: 'json',
	    data: {	"uri" : uri },
	    success: function(obj)
	    {
			if (obj.success){
		    	var html = "";
		    	var data = obj.data;
		    	var i = 0;
		    	if (data!=null && data.length > 0)
		    	{
					while( i < data.length )
				    {
						var image = data[i];
						html += '<a class="link" href="rest/services/download?uri='+image.irodsDataURI+'" target="_blank">';
						html += '    <img src="'+image.thumbnailUrl+'" title="'+image.description+'" style="height:100px;border:1px solid #DADADA"/>';
						html += '</a>';
						i++;
				    }
		    	}
		    	else html = "<p>No image available</p>";

		    	hideLoadingBar(divId);
		    	$("#"+divId).html(html);
		    }
	    	else {
	    		hideLoadingBar(divId);
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	hideLoadingBar(divId);
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	showErrorDialog("Server error: the list of images could not be retrieved");
		    }
	    }
    });
}

/**
 * 
 * @param uri
 * @param divId
 */
function loadLinksForAnalysis(uri, divId){
	
	showLoadingBarSmall(divId);
	
	$.ajax({
	    url: getExperimentAnalysisLinksURL,
	    dataType: 'json',
	    data: {	"uri" : uri },
	    success: function(obj)
	    {
			if (obj.success){
		    	var html = "";
		    	var data = obj.data;
		    	var i = 0;
		    	if (data!=null && data.length > 0)
		    	{
		    		html += '<table class="layout">';
		    		while( i < data.length ){
						var link = data[i];
						html += "<tr><td>";
						if (isJmolFormat(link.format)){
							html += "<img class='icon' src='images/icons/mol_tiny.png' title='Display 3D structure'/>";
						}
						else if (link.format == "CSV" ){
							html += "<img class='icon' src='images/icons/chart_small.png' title='Plot graph...'/>";
						}
						else if (isImageFormat(link.format)) {
							html += "<img class='icon' src='images/icons/image_small.png' title='View image...'/>";
						}
						else if (link.format == "PDF"){
							html += "<img class='icon' src='images/icons/pdf_tiny.png' title='Download PDF document...'/>";
						}
						else {
							html += "<img class='icon' src='images/icons/full_page_small.png' title='View file details...'/>";
						}
						html += '</td><td><a class="link" href="file.do?uri='+link.irodsDataURI+'" title="'+link.name+'">';
						html += link.description;
						html += '</a></td></tr>';
						i++;
				    }
		    		html += '</table>';
		    	}
		    	else html = "<p>No link available</p>";

		    	hideLoadingBar(divId);
		    	$("#"+divId).html(html);
		    }
	    	else {
	    		hideLoadingBar(divId);
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
	    	hideLoadingBar(divId);
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	showErrorDialog("Server error: the list of images could not be retrieved");
		    }
	    }
    });
}