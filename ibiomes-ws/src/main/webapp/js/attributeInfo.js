var attributesURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/metadata/attributes';
var attributesAddURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/metadata/attributes/add';
var attributesDelURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/metadata/attributes/delete';
var attributesUpdURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/metadata/attributes/update';

var valuesURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/metadata/values';
var valuesDelURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/metadata/values/delete';
var valuesAddURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/metadata/values/add';
var valuesUpdURL = 'http://juliens-grid-node.chpc.utah.edu:8080/ibiomes-ws/rest/metadata/values/update';

function getAttributes() {
    $.ajax({
    url: attributesURL,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	var html = "<select id='attrlist' onchange='getValuesByAttribute();getAttributeInfo();'>";
    	var i = 0;
    	while( i < obj.metadataAttribute.length )
    	{
    		var attr = obj.metadataAttribute[i];
    		if (i==0)
    			html += "<option value='"+attr.code+"' selected='selected'>"+ attr.term + "</option>";
    		else 
    			html += "<option value='"+attr.code+"'>"+ attr.term + "</option>";
    		i++;
    	}
    	html += "</select>";
    	html += " <img class='icon' src='../images/icons/delete.png'/>";
    	html += " <img class='icon' src='../images/icons/page_process.png'/>";
    	$("#attributes").html(html);
    	getAttributeInfo(); 
      },
    failure: function() { 
    	$("#error-attr").html("Error!!");
    }
  });
}

function getAttributeInfo()
{
	var code = $("#attrlist").val();
	
    $.ajax({
    url: attributesURL + "/" + code,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	$("#attrdetails").show();
    	$("#attrcode").text(obj.code);
    	$("#attrterm").text(obj.term);
    	$("#attrdef").text(obj.definition);
      },
    failure: function() { 
    	$("#error-attr").html("Error!!");
    }
  });
}

function getValuesByAttribute() 
{
	var code = $("#attrlist").val();
	
    $.ajax({
    url: attributesURL + "/" + code + "/values",
    dataType: 'json',
    //data: data,
    success: function(obj){
    	var html = "<select id='vallist' onchange='getValueInfo();'>";
    	var i = 0;
    	while( i < obj.metadataValue.length )
    	{
    		var attr = obj.metadataValue[i];
    		if (i==0)
    			html += "<option value='"+attr.code+"' selected='selected'>"+ attr.term + "</option>";
    		else
    			html += "<option value='"+attr.code+"'>"+ attr.term + "</option>";
    		i++;
    	}
    	html += "</select>";  	
    	html += " <img class='icon' src='../images/icons/delete.png'/>";
    	html += " <img class='icon' src='../images/icons/page_process.png'/>";
    	$("#values").html(html);
    	if (i==0)
    		$("#valdetails").hide();
    	else {
    		$("#valdetails").show();
    		getValueInfo();
    	}
      },
    failure: function() { 
    	$("#error-val").html("Error!!");
    }
  });
}

function getValuesByTerm() 
{
	var term = $("#searchval").val();
	
    $.ajax({
    url: valuesURL + "/search/" + term,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	var html = "<select id='allvalues' onchange='getValueInfo();'>";
    	var i = 0;
    	while( i < obj.metadataValue.length )
    	{
    		var attr = obj.metadataValue[i];
    		if (i==0)
    			html += "<option value='"+attr.code+"' selected='selected'>"+ attr.term + "</option>";
    		else
    			html += "<option value='"+attr.code+"'>"+ attr.term + "</option>";
    		i++;
    	}
    	html += "</select>";  	
    	html += " <img class='icon' src='../images/icons/delete.png'/>";
    	html += " <img class='icon' src='../images/icons/page_process.png'/>";
    	$("#values-list").html(html);
    	if (i>0){
    		getValueInfo();
    	}
      },
    failure: function() { 
    	$("#error-val").html("Error!!");
    }
  });
}

function getAllValues() 
{
    $.ajax({
    url: valuesURL ,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	var html = "<select id='allvalues' onchange='getValueInfo();'>";
    	var i = 0;
    	while( i < obj.metadataValue.length )
    	{
    		var attr = obj.metadataValue[i];
    		if (i==0)
    			html += "<option value='"+attr.code+"' selected='selected'>"+ attr.term + "</option>";
    		else
    			html += "<option value='"+attr.code+"'>"+ attr.term + "</option>";
    		i++;
    	}
    	html += "</select>";  	
    	html += " <img class='icon' src='../images/icons/delete.png'/>";
    	html += " <img class='icon' src='../images/icons/page_process.png'/>";
    	$("#values-list").html(html);
    	if (i>0){
    		getValueInfo();
    	}
      },
    failure: function() { 
    	$("#error-val").html("Error!!");
    }
  });
}

function getValueInfo() 
{
	var code = $("#vallist").val();
	
    $.ajax({
    url: valuesURL + "/" + code,
    dataType: 'json',
    //data: data,
    success: function(obj){
    	$("#valdetails").show();
    	$("#valcode").text(obj.code);
    	$("#valterm").text(obj.term);
    	$("#valdef").text(obj.definition);
      },
    failure: function() { 
    	$("#error-val").html("Error!!");
    }
  });
}



$(document).ready(function() {
	  getAttributes();
	  getAllValues();
});

