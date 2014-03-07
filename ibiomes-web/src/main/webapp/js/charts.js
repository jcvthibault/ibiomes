
var getTmpPasswordURL = contextPath + '/rest/services/user/getTmpPassword';
var getPlotURL = contextPath + '/rest/services/plot';

/**
 * 
 * @param appletDivId
 * @param uri
 * @param chartType
 * @param title
 * @param axis
 * @param units
 * @param width
 * @param height
 */
function loadPlotImage(divId, uri, chartType, title, axis, units, width, height)
{
	showLoadingBarLarge('chartAppletLoading');
	$("#"+divId).empty();
	
	//parse axis labels
	var axisValues = null;
	var unitsValues = null;
	var xTitle=null, yTitle=null, zTitle=null;
	var xUnit=null, yUnit=null, zUnit=null;
	
	if (axis != null){
		axisValues = axis.split(",");
		if (axisValues.length>0) xTitle =  axisValues[0];
		if (axisValues.length>1) yTitle =  axisValues[1];
		if (axisValues.length>2) zTitle =  axisValues[2];
	}
	if (xTitle == null) xTitle = "x";
	if (yTitle == null) yTitle = "y";
	if (zTitle == null) zTitle = "z";
	
	//parse units
	if (units != null){
		unitsValues = units.split(",");
		if (unitsValues.length>0) xUnit =  unitsValues[0];
		if (unitsValues.length>1) yUnit =  unitsValues[1];
		if (unitsValues.length>2) zUnit =  unitsValues[2];
	}
	if (xUnit != null && xUnit.length>0)
		xTitle = xTitle + " ("+ xUnit +")";
	if (yUnit != null && yUnit.length>0)
		yTitle = yTitle + " ("+ yUnit +")";
	if (zUnit != null && zUnit.length>0)
		zTitle = zTitle + " ("+ zUnit +")";
	
	$.ajax({
	    url: getPlotURL,
	    dataType: 'json',
	    data: {	
	    	"uri" : uri,
	    	"type" : chartType,
	    	"title" : title,
	    	"xTitle" : xTitle,
	    	"yTitle" : yTitle,
	    	"zTitle" : zTitle,
	    	"width" : width,
	    	"height" : height
	    },
	    success: function(obj){
	    	hideLoadingBar('chartAppletLoading');
	    	if (obj.success){
	    		var imagePath = obj.data;
	    		$("#"+divId).html("<image src='"+imagePath+"'/>");
	    	}
	    	else showErrorDialog(obj.message, divId);
	      },
	    error: function() { 
	    	hideLoadingBar('chartAppletLoading');
	    	showErrorDialog("Server error: could not generate a plot for this file", divId);
	    }
	  });
}
	
/**
 * Load chart applet
 * @param appletDivId
 */
function loadChartApplet(appletDivId, zone, host, port, user, defaultStorageResource, password, fileUrl, chartType, title, labels, units) {
	
	var appletDiv = $("#"+appletDivId);
	appletDiv.empty();
	
	var appletTagDiv = document.createElement('div');
	appletTagDiv.setAttribute('id', 'appletTagDiv');
	
	var a = document.createElement('applet');
	appletTagDiv.appendChild(a);
	a.setAttribute('code', 'edu/utah/bmi/ibiomes/plot/IBIOMESPlotApplet.class');
	a.setAttribute('codebase', contextPath + '/applets');
	a.setAttribute('archive', 'ibiomes-plot.jar,jcommon-1.0.17.jar,jfreechart-1.0.14.jar');
	a.setAttribute('width', 700);
	a.setAttribute('height', 550);

	var p = document.createElement('param');
	p.setAttribute('name', 'title');
	p.setAttribute('value', title);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'file');
	p.setAttribute('value', fileUrl);
	a.appendChild(p);
	
	if (chartType != null && chartType.length>0){
		p = document.createElement('param');
		p.setAttribute('name', 'chartType');
		p.setAttribute('value', chartType);
		a.appendChild(p);
	}
	
	p = document.createElement('param');
	p.setAttribute('name', 'axis');
	p.setAttribute('value', labels);
	a.appendChild(p);

	p = document.createElement('param');
	p.setAttribute('name', 'units');
	p.setAttribute('value', units);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'host');
	p.setAttribute('value', host);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'port');
	p.setAttribute('value', port);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'zone');
	p.setAttribute('value', zone);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'user');
	p.setAttribute('value', user);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'defaultStorageResource');
	p.setAttribute('value', defaultStorageResource);
	a.appendChild(p);
	
	p = document.createElement('param');
	p.setAttribute('name', 'password');
	p.setAttribute('value', password);
	a.appendChild(p);
	
	appletDiv.append(appletTagDiv);
}

/**
 *
 * @param appletDivId
 * @param zone
 * @param host
 * @param port
 * @param user
 * @param defaultStorageResource
 * @param password
 * @param fileUrl
 * @param chartType
 * @param title
 * @param labels
 * @param units
 */
function requestTmpPwdAndLoadChartApplet(appletDivId, zone, host, port, user, defaultStorageResource, fileUrl, chartType, title, labels, units) {
	
	$('#chartAppletLoading').show();
	
    $.ajax({
    url: getTmpPasswordURL,
    dataType: 'json',
    success: function(obj){
    	$("#chartAppletLoading").hide();
    	if (obj.success){
    		var password = obj.data;
    		loadChartApplet(appletDivId, zone, host, port, user, defaultStorageResource, password, fileUrl, chartType, title, labels, units);
    	}
    	else showErrorInline(obj.message, appletDivId);
      },
    error: function() { 
    	$("#chartAppletLoading").hide();
    	showErrorInline("Error: could not load the list of AVUs for this file", appletDivId);
    }
  });
}

