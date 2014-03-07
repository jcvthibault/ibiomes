
$(function () {

	$("#tabs").tabs();
	
	$("[id^=accordion]").accordion();
	
	$("[id^=accordion]").hide();
	
	$("#fileTree").jstree({ 
		"themes" : {
			"theme" : "default",
			"dots" : true,
			"icons" : true
		},
		"plugins" : [ "themes", "html_data", "ui", "crrm" ]
	})
	.bind("select_node.jstree", function (event, data) {
		var selectedId = data.rslt.obj.attr("id");
		alert(selectedId);
		selectDirectory(selectedId);
    });
	
	function selectDirectory(id){
		$("[id^=accordion]").hide();
		$("#"+id).show();
	}
});

