
/**
 * Display error message
 */
function showErrorDialog(msg){

	$( "#error-message").dialog({
		resizable: false,
		autoOpen: false,
		modal: true,
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	var html = "";
	html += "<div class='ui-state-error'>";
	html += "	<p style='text-align:left'>";
	html += "		<span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>&nbsp;";
	html += 		msg;
	html += "	</p>";
	html += "</div>";
	
	$("#error-message").html(html);
	$("#error-message").dialog("open");
}

/**
 * Display info message
 */
function showInfoDialog(msg){

	$( "#info-message").dialog({
		resizable: false,
		autoOpen: false,
		modal: true,
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	var html = "";
	html += "<p style='text-align:left'>";
	html += 	msg;
	html += "</p>";
	
	$("#info-message").html(html);
	$("#info-message").dialog("open");
}

/**
 * Display confirmation message
 */
function askConfirmation(confirmationString)
{
	var dialog_buttons = {};
	dialog_buttons['Ok'] = function(){ 
		$(this).dialog('close');
		return true;
	};
	dialog_buttons['Cancel'] = function(){ 
		$(this).dialog('close'); 
		return false;
	};
	
	var html = "<p>" + confirmationString + "</p>";

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
 * Display error message in given location
 * @param message Error message
 * @param divId
 */
function showErrorInline(message, divId){
	
	var html = "";
	html += "<div class='ui-widget'>";
	html += "<div class='ui-state-error ui-corner-all' style='margin: 0 .8em;'> ";
	html += "	<p><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span> ";
	html += "	"+ message +" </p>";
	html += "</div>";
	html += "</div>";
	$("#"+divId).html(html);
}

/**
 * Display informational message in given location
 * @param message Message
 * @param divId
 */
function showInfoInline(message, divId)
{	
	var html = "";
	html += "<div class='ui-widget'>";
	html += "<div class='ui-state-highlight ui-corner-all' style='margin: 0 .8em;'> ";
	html += "	<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;'></span> ";
	html += "		"+ message;
	html += "	</p>";
	html += "</div>";
	html += "</div>";
	$("#"+divId).html(html);
}

/**
 * Display loader in div
 */
function showLoadingBarLarge(divId){
	$("#"+divId).html('<div style="text-align:center;"><img src="animations/loading50.gif"/></div>');
}

/**
 * Display loader in div
 */
function showLoadingBarSmall(divId){
	$("#"+divId).html('<div style="text-align:center;"><img src="animations/loading26.gif"/></div>');
}

/**
 * Remove loader in div
 */
function hideLoadingBar(divId){
	$("#"+divId).empty();
}

/**
 * Display loader in modal window
 */
function showLoadingInModal(text){
	if (text==null)
		text = '';
	else text = '<h3>'+text+'</h3>';
	var newDiv = $('<div  id="loadingDialog" title="Loading"></div>'); 
	newDiv.html('<div style="text-align:center;">'+text+'<img src="animations/loading50.gif"/></div>');
	newDiv.dialog({
		height: 450,
        width: 450,
        autoOpen: true,
		modal: true
	});
}

/**
 * Remove loader in modal window
 */
function hideLoadingInModal(){
	$('#loadingDialog').dialog('close');
	$('#loadingDialog').remove();
}
