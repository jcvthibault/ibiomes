
var getFileAVUsURL = contextPath + '/rest/services/file/avus';
var addFileAVUsURL = contextPath + '/rest/services/file/avus/add';
var updateFileAVUsURL = contextPath + '/rest/services/file/avus/update';
var deleteFileAVUsURL = contextPath + '/rest/services/file/avus/delete';
var updateFileFormatURL = contextPath + '/rest/services/file/updateformat';

var updateFileDisplayFlagURL = contextPath + '/rest/services/file/display';
var getHiddenFilesURL = contextPath + '/rest/services/experiment/files/hidden';
var updateFileAnalysisFlagURL = contextPath + '/rest/services/file/data';
var getAnalysisFilesURL = contextPath + '/rest/services/experiment/files/analysis';

var deleteFilesURL = contextPath + '/rest/services/file/delete';
var unregisterFilesURL = contextPath + '/rest/services/file/unregister';

var getFilesUnderCollectionURL = contextPath + '/rest/services/collection/files';
var getFilesInFormatUnderCollectionURL = contextPath + '/rest/services/collection/files/byformat';

var downloadFileURL = contextPath + '/rest/services/download';

/**
 * Get list of AVUs for a file
 * @param uri File URI
 */
function getFileAVUs(uri) {

	$('#file-avu-loading').show();
	
    $.ajax({
	    url: getFileAVUsURL,
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
			    		html += "		<input type='image' src='images/icons/delete_small.png' title='remove' onclick=\"askConfirmationDeleteFileAVU('Delete?','"+uri+"','avu_attribute"+i+"','avu_value"+i+"','avu_unit"+i+"')\" />";
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
		    	else html = "<p>No metadata available for this file.</p>";
	
		    	$('#file-avu-loading').hide();
		    	$("#file-avu-table").html(html);
	    	}
	    	else {
	    		$('#file-avu-loading').hide();
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function() { 
	    	$("#file-avu-loading").hide();
	    	showErrorInline("Error: could not load the list of AVUs for this file", "file-avu-table");
	    }
    });
}

/**
 * Add AVU to file
 * @param uri
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function addFileAVU(uri, attributeId, valueId, unitId) {
	
	var attribute = document.getElementById(attributeId).value;
	var value = document.getElementById(valueId).value;
	var unit = "";
	var unitInput = document.getElementById(unitId);
	if (unitInput!=null)
		unit = unitInput.value;
	
    $.ajax({
    url: addFileAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
    		"attribute" : attribute,
    		"value" : value,
    		"unit" : unit},
    success: function(obj){
    	if (!obj.success){
    		showErrorDialog(obj.message);
    	}
    	else showInfoDialog("AVU successfully added.");
    	getFileAVUs(uri);
    	
      },
    error: function() { 
    	showErrorDialog("This AVU could not be added. Make sure you have the right permissions.");
    }
  });
}

/**
 * Update file AVU
 * @param uri
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function updateFileAVU(uri, attributeId, valueId, unitId) {
	
	var attribute = document.getElementById(attributeId).value;
	var value = document.getElementById(valueId).value;
	var unit = "";
	var unitInput = document.getElementById(unitId);
	if (unitInput!=null)
		unit = unitInput.value;
	
    $.ajax({
    url: updateFileAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
    		"attribute" : attribute,
    		"value" : value,
    		"unit" : unit},
    success: function(obj){
    	if (!obj.success){
    		showErrorDialog(obj.message);
    	}
    	else showInfoDialog("AVU successfully updated.");
    	getFileAVUs(uri);
      },
    error: function() { 
    	showErrorDialog("This AVU could not be updated. Make sure you have the right permissions.");
    }
  });
}

/**
 * Update file AVUs
 * @param uri File URI
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function updateFileAVUs(uri, formId)
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
	    url: updateFileAVUsURL,
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
	    	showErrorDialog("The metadata could not be updated. Make sure you have the right permissions.");
	    }
	});
}

/**
 * Delete AVU from file
 * @param uri
 * @param attributeId
 * @param valueId
 * @param unitId
 */
function deleteFileAVU(uri, attributeId, valueId, unitId) {
	
	var attribute = document.getElementById(attributeId).value;
	var value = document.getElementById(valueId).value;
	var unit = "";
	var unitInput = document.getElementById(unitId);
	if (unitInput!=null)
		unit = unitInput.value;
	
	$.ajax({
    url: deleteFileAVUsURL,
    dataType: 'json',
    data: {	"uri" : uri,
			"attribute" : attribute,
			"value" : value,
    		"unit" : unit},
    success: function(obj){
    	if (!obj.success){
    		showErrorDialog(obj.message);
    	}
    	getFileAVUs(uri);
      },
    error: function() { 
    	showErrorDialog("This AVU could not be deleted. Make sure you have the right permissions.");
    }
  });
}

/**
 * Display confirmation message when delete file AVU
 */
function askConfirmationDeleteFileAVU(confirmationString, uri, attributeId, valueId, unitId)
{
	var dialog_buttons = {};
	dialog_buttons['Ok'] = function(){ 
		$(this).dialog('close');
		deleteFileAVU(uri, attributeId, valueId, unitId);
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
 * Update file format
 * @param uri File URI
 * @param fileFormat New file format
 */
function updateFileFormat(uri, fileFormat) {
	
	$.ajax({
    url: updateFileFormatURL,
    dataType: 'json',
    data: {	"uri" : uri,
			"format" : fileFormat},
    success: function(obj){
    	getFileAVUs(uri);
      },
    error: function() { 
    	showErrorDialog("The format of this file could not be updated. Make sure you have the right permissions.");
    }
  });
}

/**
 * Hide file in the list of public files
 */
function hideFiles(formId)
{
	var uris = "";
	var form = $("#"+formId).serializeArray();
	var p = 0;
	while (p < form.length){
		if (form[p].name == "uri")
			uris += form[p].value + ",";
		p++;
	}
	uris = uris.substring(0,uris.length-1);
	
	if (uris.length>0)
	{
		$.ajax({
			url: updateFileDisplayFlagURL,
		    dataType: 'json',
		    data: {	
		    	"uris" : uris,
		    	"visible" : "false"
		    },
		    success: function(obj){
		    	loadCollectionFiles(formId, "false");
		      },
		    error: function() { 
		    	showErrorDialog("Server error: the files could not be hidden.");
		    }
		});
	}
	else{
		showErrorDialog("Select the files you want to hide first");
	}
}


/**
 * Set file as visible instead of hidden
 * @param uri File URI
 */
function removeHiddenFlag(collectionUri, hiddenFileListId) {
	
	var uris = ($("#"+ hiddenFileListId).val() || []).join(',');
	if (uris != null && uris.length>0)
	{
		$.ajax({
	    url: updateFileDisplayFlagURL,
	    dataType: 'json',
	    data: {	"uris" : uris,
				"visible" : "true"},
	    success: function(obj){
	    	loadHiddenFiles(collectionUri, hiddenFileListId);
	      },
	    error: function() { 
	    	showErrorDialog("The file settings could not be updated. Make sure you have the right permissions.");
	    }
	  });
	}
	else{
		showErrorDialog("Select the files you want to update first");
	}
}

/**
 * 
 * @param collectionUri
 * @param visibleFileListId
 * @param hiddenFileListId
 */
function loadHiddenFiles(collectionUri, hiddenFileListId) {

	$("#"+hiddenFileListId).empty();
	
	$.ajax({
	    url: getHiddenFilesURL,
	    dataType: 'json',
	    data: {
	    	"uri" : collectionUri
	    },
	    success: function(obj){
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";		    	
		    	var i = 0;
		    	while( i < data.length ){
		    		var file = data[i];
		    		html += "<option value='"+collectionUri+"/"+ file.name +"' title='"+file.name+"'>"+ file.name +"</option>";
		    		i++;
		    	}
		    	$("#"+hiddenFileListId).append(html);
	    	}
	    	else {
	    		showErrorDialog(obj.message);
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	showErrorDialog("Server error: could not retrieve the list of hidden files");
		    }
	    }
	});
}

/**
 * Set analysis flag for selected files so that they are presented in the main page as analysis data.
 * @param formId
 */
function presentFilesAsAnalysis(formId)
{
	var uris = "";
	var form = $("#"+formId).serializeArray();
	var p = 0;
	while (p < form.length){
		if (form[p].name == "uri")
			uris += form[p].value + ",";
		p++;
	}
	uris = uris.substring(0,uris.length-1);
	
	if (uris.length>0)
	{
		$.ajax({
		    url: updateFileAnalysisFlagURL,
		    dataType: 'json',
		    data: {	
		    	"uris" : uris,
		    	"isdata" : "true"
		    },
		    success: function(obj){
		    	//loadCollectionFiles(formId, "false");
		    	showInfoDialog("The selected files were successfully saved as analysis data");
		      },
		    error: function() { 
		    	showErrorDialog("The files could not be updated. Make sure you have the right permissions.");
		    }
		});
	}
	else{
		showErrorDialog("No selected file!");
	}
}

/**
 * Remove file data flag for csv or img files (to present on main page or not)
 * @param collectionUri Experiment URI
 */
function removeAnalysisFlag(collectionUri, dataFileListId) {
	
	var uris = ($("#"+ dataFileListId).val() || []).join(',');
	if (uris != null && uris.length>0)
	{
		$.ajax({
		    url: updateFileAnalysisFlagURL,
		    dataType: 'json',
		    data: {	"uris" : uris,
					"isdata" : "false"},
		    success: function(obj){
		    	loadAnalysisFiles(collectionUri, dataFileListId);
		      },
		    error: function() { 
		    	showErrorDialog("The file settings could not be updated. Make sure you have the right permissions.");
		    }
		});
	}
	else{
		showErrorDialog("Select the files you want to update first");
	}
}

/**
 * Load list of analysis files
 * @param collectionUri
 * @param dataFileListId
 */
function loadAnalysisFiles(collectionUri, dataFileListId) {

	$("#"+dataFileListId).empty();
	
	$.ajax({
	    url: getAnalysisFilesURL,
	    dataType: 'json',
	    data: {
	    	"uri" : collectionUri
	    },
	    success: function(obj)
	    {
	    	if (obj.success){
		    	var data = obj.data;
		    	var i = 0;
		    	var html = "";
		    	if (data != null){
			    	while( i < data.length ){
			    		var file = data[i];
			    		html += "<option value='"+file.absolutePath +"' >"+ file.absolutePath.substring(collectionUri.length+1) +"</option>";
			    		i++;
			    	}
			    	$("#"+dataFileListId).append(html);
		    	}
	    	}
	    	else showErrorDialog(obj.message);
	    },
	    error: function(jqXHR, textStatus, errorThrown) { 
		    if (!isPageBeingRefreshed && textStatus != 'abort'){
		    	showErrorDialog("Error: could not load the list of analysis files for '"+collectionUri+"'");
		    }
	    }
	});
}

/**
 * Delete file(s)
 * @param uris File URIs
 * @param formId
 */
function deleteFiles(formId)
{	
	//askConfirmation("Are you sure you want to delete the selected files?");
	
	var uris = "";
	var form = $("#"+formId).serializeArray();
	var p = 0;
	while (p < form.length){
		if (form[p].name == "uri")
			uris += form[p].value + ",";
		p++;
	}
	uris = uris.substring(0,uris.length-1);
	
	if (uris.length>0)
	{
		$.ajax({
		    url: deleteFilesURL,
		    dataType: 'json',
		    data: {	
		    	"uris" : uris
		    	},
		    success: function(obj){
		    	loadCollectionFiles(formId, "false");
		    	if (!obj.success){
		    		showErrorDialog(obj.message);
		    	}
		      },
		    error: function() { 
		    	showErrorDialog("The files could not be deleted. Make sure you have the right permissions.");
		    }
		});
	}
	else{
		showErrorDialog("Select the files you want to delete first");
	}
}

/**
 * Unregister file(s)
 * @param uris File URIs
 * @param formId
 */
function unregisterFiles(formId)
{	
	var uris = "";
	var form = $("#"+formId).serializeArray();
	var p = 0;
	while (p < form.length){
		if (form[p].name == "uri")
			uris += form[p].value + ",";
		p++;
	}
	uris = uris.substring(0,uris.length-1);
	
	if (uris.length>0)
	{
		$.ajax({
		    url: unregisterFilesURL,
		    dataType: 'json',
		    data: {	
		    	"uris" : uris
		    	},
		    success: function(obj){
		    	loadCollectionFiles(formId, "false");
		    	if (!obj.success){
		    		showErrorDialog(obj.message);
		    	}
		      },
		    error: function() { 
		    	showErrorDialog("The files could not be unregistered. Make sure you have the right permissions.");
		    }
		});
	}
	else{
		showErrorDialog("Select the files you want to unregister first");
	}
}

/**
 * Load list of files in collection
 * @param uri Collection URI
 */
function loadCollectionFiles(formId, recursive) 
{
	var form = $("#"+formId).serializeArray();
	var p = 0;
	var collectionUri = null;
	var fileFormat = "";
	var canWrite = false;

	while (p < form.length){
		if (form[p].name == "collectionUri")
			collectionUri = form[p].value;
		else if (form[p].name == "fileFormat")
			fileFormat = form[p].value;
		else if (form[p].name == "canWrite")
			canWrite = (form[p].value == 'true');
		p++;
	}
	if (fileFormat == 'Other')
		fileFormat = 'Unknown';
	
	$("#" + formId).empty();
	$("#"+formId+"Loading").show();
	
    $.ajax({
	    url: getFilesInFormatUnderCollectionURL,
	    dataType: 'json',
	    data: {
	    	"uri" : collectionUri,
	    	"formats" : fileFormat,
	    	"recursive" : recursive
	    },
	    success: function(obj)
	    {	
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data!=null && data.length>0)
		    	{
		    		html += displayFilesInSameFormat(formId, data, fileFormat, collectionUri, canWrite);
		    	}
		    	else{
		    		//remove accordion tab for this group of files
		    		var content = $("#"+formId+"TabContent");
		    		var head = $("#"+formId+"TabHeader");
		    		content.add(head).fadeOut('slow',function(){$(this).remove();});
		    	}
	
		    	$("#"+formId+"Loading").hide();
		    	$("#" + formId).html(html);
	    	}
	    	else {
	    		$("#"+formId+"Loading").hide();
	    		showErrorInline(obj.message);
	    	}
	    	
	      },
	    error: function() { 
	    	$("#"+formId+"Loading").hide();
	    	showErrorInline("Error: could not load the list of files", formId);
	    }
	});
}

/**
 * Load list of files in collection for edit mode
 * @param uri Collection URI
 */
function loadCollectionFilesForEdit(formId, recursive) 
{	
	var form = $("#"+formId).serializeArray();
	var p = 0;
	var collectionUri = null;
	var fileFormat = "";
	var canWrite = false;
	
	while (p < form.length){
		if (form[p].name == "collectionUri")
			collectionUri = form[p].value;
		else if (form[p].name == "fileFormat")
			fileFormat = form[p].value;
		else if (form[p].name == "canWrite")
			canWrite = (form[p].value == 'true');
		p++;
	}
	if (fileFormat == 'Other')
		fileFormat = 'Unknown';
	
	$("#" + formId).empty();
	$("#"+formId+"Loading").show();
	
    $.ajax({
	    url: getFilesInFormatUnderCollectionURL,
	    dataType: 'json',
	    data: {
	    	"uri" : collectionUri,
	    	"formats" : fileFormat,
	    	"recursive" : recursive
	    },
	    success: function(obj)
	    {	
	    	if (obj.success){
		    	var data = obj.data;
		    	var html = "";
		    	if (data!=null && data.length>0)
		    	{
		    		html = displayFilesInSameFormat(data, fileFormat, collectionUri, canWrite);
		    	}
		    	else{
		    		//remove accordion tab for this group of files
		    		var content = $("#"+formId+"TabContent");
		    		var head = $("#"+formId+"TabHeader");
		    		content.add(head).fadeOut('slow',function(){$(this).remove();});
		    	}
	
		    	$("#"+formId+"Loading").hide();
		    	$("#" + formId).html(html);
	    	}
	    	else {
	    		$("#"+formId+"Loading").hide();
	    		showErrorInline(obj.message);
	    	}
	      },
	    error: function() { 
	    	$("#"+formId+"Loading").hide();
	    	showErrorInline("Error: could not load the list of files", formId);
	    }
	});
}

/**
 * 
 * @param files
 * @param fileFormat
 * @param collectionUri
 * @param canWrite
 * @returns
 */
function displayFilesInSameFormat(formId, files, fileFormat, collectionUri, canWrite)
{
	var html = "<table style='width:100%;margin:0px'>";
	html += "      <tr><td style='width:100%;text-align:right'>";
	html += "			<select id='action"+ formId + "' name='action'>";
	if (isJmolFormat(fileFormat)){
    	html += "			<option value='comparison' title='Compare selected structures in Jmol'>Compare structures</option>";
		html += "			<option value='animation' title='Create multi-frame animation in Jmol with selected structures'>Create animation</option>";
	}
	html += "				<option value='addtocart'>Add to shopping cart</option>";
	if (canWrite){
		html += "			<option value='present'>Present as analysis result</option>";
		html += "			<option value='hide'>Hide file(s)</option>";
		html += "			<option value='delete'>Delete file(s)</option>";
		html += "			<option value='unregister'>Unregister file(s)</option>";
	}
	html += "			</select>&nbsp;";
	html += "			<input type='button' class='button' value=' Go ' onclick=\"triggerFileAction('action"+formId+"','"+ formId +"')\" />";
	html += "        </td>";
	html += "     </tr>";
	html += "     <tr>";
	html += "        <td>";
	
	html += "<table style='margin-left:0px'><tr>";
	html += "   <td class='first'>";
	html += "		<input id='selectAll"+formId+"' type='checkbox' onclick=\"checkAllBoxes('selectAll"+formId+"','"+formId+"','uri');\"/>";
	html += "	</td>";
	html += "	<td><strong>FILE</strong></td>";
	html += "	<td style='text-align:right'><strong>SIZE</strong></td>";
	html += "	<td style='text-align:right'></td>";
	html += "</tr>";
	html += "<tr style='height:0px'>";
	html += "	<td colspan='5'>";
	html += "		<input type='hidden' name='fileFormat' value='"+fileFormat + "'/>";
	html += "		<input type='hidden' name='collectionUri' value='"+collectionUri+"'/>";
	html += "		<input type='hidden' name='canWrite' value='"+canWrite+"'/>";
	html += "	</td>";
	html += "</tr>";
	
	var i = 0;				    	
	while( i < files.length )
	{
		var fileInfo = files[i];

		html += "<tr>";
		html += "   <td class='first'>";
		html += "		<input name='uri' type='checkbox' value='" + fileInfo.absolutePath + "'/>";
		html += "	</td>";
		html += "   <td >" + fileInfo.name + "</td>";
		html += "   <td style='text-align:right;'>" + getFriendlyFileSize(parseInt(fileInfo.size)) +"</td>";
		html += "   <td style='text-align:right;'>";
		
		if (isJmolFormat(fileFormat))
		{
			html += "<a href='jmol.do?uri=" + fileInfo.absolutePath + "' target='_blank'>";
			html += "<img class='icon' src='images/icons/mol.png' title='display 3D structure...'/></a>";
		}
		else if (fileFormat == "CSV" )
		{
			//html += "<a class='pointer' onclick=\"showPlotForSelectedFile('" + fileInfo.absolutePath + "')\">";
			html += "<a class='pointer' href='file.do?uri=" + fileInfo.absolutePath + "')\">";
			html += "<img class='icon' src='images/icons/chart.png' title='plot graph...'/></a>";
		}
		
		if (isImageFormat(fileFormat)) 
		{
			html += "<a href='"+downloadFileURL+"?uri=" + fileInfo.absolutePath + "' target='_blank'>";
			html += "<img class='icon' src='images/icons/image.png' title='Download image...'/></a>";
		}
		else
		{
			html += "<a href='"+downloadFileURL+"?uri=" + fileInfo.absolutePath + "' target='_blank'>";
			html += "<img class='icon' src='images/icons/download.png' title='Download file...'/></a>";
		}
		
		if (canWrite)
		{
			html += "<a href='editfile.do?uri=" + fileInfo.absolutePath + "'>";
			html += "<img class='icon' src='images/icons/page_process.png' title='edit file properties...'/></a>";
		}

		html += "</td></tr>";
		i++;
	}
	
	html += "</table>";
	
	html += "</td></tr>";
	html += "</table>";
	
	return html;
}

/**
 * Check all check boxes
 */
function checkAllBoxes(checkBoxId, formId, name){
	var isChecked = $('#'+checkBoxId).is(":checked");
	$('#'+formId+' input[name='+name+']').prop('checked', isChecked);
}
