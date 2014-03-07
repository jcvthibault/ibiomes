
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


