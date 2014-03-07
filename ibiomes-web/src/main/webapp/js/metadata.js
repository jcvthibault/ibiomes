
/**
 * Get list of metadata attributes available in the catalog
 */
function getMetadataCatalog(metadataCatalogURL) {
    $.ajax({
    url: metadataCatalogURL,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	var html = "<table><tr><th class='first'>Code</th><th>Term</th><th>Definition</th></tr>";
    	var i = 0;
    	while( i < obj.length )
    	{
    		var attr = obj[i];
    		
    		var rowClass = "row-a";
    		if (i % 2 == 1)
    			rowClass = "row-b";
    		
    		html += "<tr class='"+ rowClass + "'>";
    		html += "   <td class='first'><strong>"+attr.code+"<strong></td><td>"+attr.term+"</td><td>"+attr.definition+"</td>";
    		html += "</tr>";
    		i++;
    	}
    	html += "</table>";

    	$("#metadata-table").html(html);
    	$("#metadata-loading").hide();
    	
      },
    error: function() { 
    	showErrorDialog("Error: could not load the metadata catalog");
    }
  });
}

/**
 * Load catalog of metadata into dropdownlist
 * @param attributeDiv
 * @param valueDiv
 * @param unitInput
 * @param buttonDiv
 */
function loadMetadataCatalogForAdd(attributeDiv, valueDiv, unitInput, buttonDiv, metadataCatalogURL){
	$.ajax({
	    url: metadataCatalogURL,
	    dataType: 'json',
	    //data: data,
	    success: function(obj){
	    	var html = "<select id=\"attrlist\" onchange=\"getValuesForAttribute('attrlist', '"+valueDiv+"', '"+buttonDiv+"', '"+metadataCatalogURL+"');\" style=\"width:200px\">";
	    	html += "<option value=''></option>";
	    	var i = 0;
	    	while( i < obj.length )
	    	{
	    		var attr = obj[i];
	    		html += "<option value='"+attr.code+"' title='"+attr.definition+"'>"+ attr.term + "</option>";
	    		i++;
	    	}
	    	html += "</select>";
	    	
	    	$("#"+attributeDiv).html(html);	
	    	$("#"+buttonDiv).hide();
	    	
	      },
	    error: function() { 
	    	showErrorDialog("Error: could not load the metadata catalog");
	    }
	  });
}



/**
 * Get metadata values allowed for a given attribute (select)
 */
function getValuesForAttribute(attributeInput, valuesDiv, button, metadataCatalogURL) 
{
	var code = document.getElementById(attributeInput).value;
	if (code.length>0)
	{
		var mdValuesUrl =  metadataCatalogURL + '$/values';
		mdValuesUrl =  mdValuesUrl.replace("$",code);
		
	    $.ajax({
	    url: mdValuesUrl,
	    dataType: 'json',
	    success: function(obj){
	    	var html = "";
	    	if (obj != null && obj.length>0){
		    	html = "<select style='width:200px' id='vallist'>";
		    	var i = 0;
		    	while( i < obj.length )
		    	{
		    		var val = obj[i];
		    		if (i==0)
		    			html += "<option value='"+val.term+"' selected='selected' title='"+val.definition+"'>"+ val.term + "</option>";
		    		else
		    			html += "<option value='"+val.term+"' title='"+val.definition+"'>"+ val.term + "</option>";
		    		i++;
		    	}
		    	html += "</select>";
	    	}
	    	else{
	    		html = "<input type='text' id='vallist' style='width:200px'>";
	    	}
	    	$("#"+valuesDiv).html(html);
	    	$("#"+button).show();	
	      },
	    error: function() { 
	    	showErrorDialog("Error: could not load the value dictionary for '"+code+"'");
	    }
	  });
	}
	else $("#"+button).hide();
}

/**
 * populate input for autocomplete
 * @param inputId
 * @param dictionaryUrl
 */
function loadAutocompleteAVU(inputId, dictionaryUrl)
{
	$("#"+ inputId).autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: dictionaryUrl,
				dataType: "json",
				data: {
					term: request.term
				},
				success: function( data ) {
					response( $.map( data, function( item ) {
						return {
							label: item.term,
							value: item.term
						};
					}));
				}
			});
		},
	});
}
