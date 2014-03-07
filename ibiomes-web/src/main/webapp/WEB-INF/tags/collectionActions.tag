<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="collectionUri" required="true" %>
<%@ attribute name="canWrite" required="true" %>
<%@ attribute name="isExperiment" required="true" %>
<%@ attribute name="isEdit" required="true" %>

<script type="text/javascript">

	/**
	 * Trigger action on collections
	 */
	function triggerCollectionAction(actionSelectId, uri)
	{
		var action = $("#"+actionSelectId).val();
		
		if (action == "delete"){
			$( "#deleteDirDialog" ).dialog( "open" );
		}
		else if (action == "unregister"){
			$( "#unregisterDirDialog" ).dialog( "open" );
		}
		else if (action == "addtocart"){
			addItemToCart(uri);
		}
		else if (action == "xml"){
			window.open(contextPath + "/rest/services/collection/xml?uri="+uri, '_blank');
		}
		else if (action == "rename"){
			$( "#renameDirDialog" ).dialog( "open" );
		}
		else if (action == "create"){
			$( "#createDirDialog" ).dialog( "open" );
		}
		else if (action == "addToSet")
		{
			$.ajax({
			    url: getListOfExperimentSetsURL,
			    dataType: 'json',
			    success: function(obj){
			    	if (obj.success){
			    		var data = obj.data;
			    		if (data != null && data.length>0){
					    	$("#expSetList").empty();
			    			$("#expSetList").show();
					    	var html = "";
					    	var i = 0;
					    	while( i < data.length ){
					    		html += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
					    		i++;
					    	}
					    	$("#expSetList").html(html);
					    	$("#expSetListError").empty();
			    		}
			    		else {
			    			$("#expSetList").hide();
			    			$("#expSetListError").html("No experiment set found.");
			    		}
			    	}
			    	else {
		    			$("#expSetList").hide();
		    			$("#expSetListError").html(obj.message);
			    	}
			      },
			    error: function() { 
			    	$("#expSetListError").html("The list of experiment sets could not be retrieved");
			    }
		    });
			$( "#addToSetDialog").dialog( "open" );
		}
	}

	$(document).ready(function()
	{
		$( "#renameDirDialog" ).dialog({
			height: 200,
		    width: 300,
		    autoOpen: false,
			modal: true
		});
		$( "#deleteDirDialog" ).dialog({
			height: 250,
		    width: 400,
		    autoOpen: false,
			modal: true
		});
		$( "#unregisterDirDialog" ).dialog({
			height: 250,
		    width: 400,
		    autoOpen: false,
			modal: true
		});
		$( "#addToSetDialog" ).dialog({
			height: 200,
		    width: 400,
		    autoOpen: false,
			modal: true,
		    buttons: {
		        "Add": function() {
		        	var id = $("#expSetList").val();
		        	var path = "<c:out value="${collectionUri}" />";
		        	if (id != null){
			        	addExperimentToSet(id, path);
			            $( this ).dialog( "close" );
		        	}
		        	else {
		        		$("#expSetListError").html("Choose an experiment set first");
		        	}
		        },
		        Cancel: function() {
		            $( this ).dialog( "close" );
		        }
		    }
		});
	});
</script>

<!-- =========  LINKS FOR ACTIONS  ======== -->

<div style="width:100%;vertical-align:middle;height:60px">
	<table class="layout-tight" style="float:right"><tr>
		<td>
			<!-- Collection actions --> 
			<select id="collectionAction" name="collectionAction">
				<option value="addtocart">Add to shopping cart</option>
				<c:if test="${ isExperiment eq true }">
					<option value="xml">Get XML representation</option>
					<c:if test="${ canWrite eq true }">
						<option value="addToSet">Add to experiment set</option>
					</c:if>
				</c:if>
				<c:if test="${ canWrite eq true }">
					<option value="rename">Rename directory</option>
					<option value="delete">Delete collection</option>
					<option value="unregister">Unregister collection</option>
				</c:if>
			</select>
		</td>
		<td style="width:3px"></td>
		<td>
			<input type="button" class="button" value=" Go " onclick="triggerCollectionAction( 'collectionAction', '<c:out value="${collectionUri}" />')"/>
		</td>
		<c:choose>
	   		<c:when test="${ isEdit eq false }">
				<!-- Edit collection link --> 
				<c:choose>
			   		<c:when test="${ canWrite eq true }">
			   			<td style="width:10px">&nbsp;</td>
						<td>
							<a class="link" href="editcollection.do?uri=<c:out value="${collectionUri}" />" title="Edit collection properties">
								<img class="icon" src="images/icons/page_process.png"/>&nbsp;Edit settings
							</a>
						</td>
					</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>
				<td style="width:10px">&nbsp;</td>
				<td><a class="link" href="collection.do?uri=<c:out value="${collectionUri}" />" title="Back to public page">
						<img class="icon" src="images/icons/full_page.png"/>&nbsp;Public page
					</a>
				</td>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr style="height:5px"/>
	</table>
</div>

<!-- =========  MODAL WINDOWS  ======== -->

<!--   rename directory  -->
 <div id="renameDirDialog" title="Rename directory" >
	<p><strong>New directory name</strong></p>
	<p><input id="directoryName" type="text" style="width:200px"/></p>
	<p><br/>
		<input type="submit" class="button" value=" rename "
		 onclick="renameCollection('<c:out value="${collectionUri}"/>','directoryName')"/>
	</p>
</div>

<!--   add to set  -->
 <div id="addToSetDialog" title="Add experiment to set" >
	<p>Select a set</p>
	<p><select id="expSetList" style="width:180px"></select></p>
	<p><span id="expSetListError"></span></p>
</div>

<!--   delete directory  --> 
 <div id="deleteDirDialog" title="Delete directory" >
	<form action="delete.do" method="post" class="blank">
		<table style="margin:0; width:100%;">
   			<tr style="height:5px">
   				<td class="noborder" colspan="2"/>
   			</tr>
			<tr>
   				<td class="noborder"><img class="icon"  src="images/icons/delete_folder_large.png"/></td>
    			<td class="noborder" style="text-align:left;">
    				<p><strong>You are about to delete '<c:out value="${collectionUri}"/>'</strong></p>
					<p><br/>All the files contained by this collection will be deleted along with the metadata associated to it.
					<br/>Continue?</p>
				</td>
			</tr>
			<tr>
				<td class="noborder"><input type="hidden" name="uri" value="<c:out value="${collectionUri}" />"/></td>
				<td class="noborder"><input type="hidden" name="dispatchto" value="/index.do" /></td>
			</tr>
			<tr>
				<td style="border:0px;text-align:center;" colspan="2"><input class="button" type="submit" value="  Delete  "/></td>
			</tr>
   			<tr style="height:5px"><td class="noborder" colspan="2"/></tr>
   		</table>
	</form>
</div>

<!--   unregister directory  --> 
 <div id="unregisterDirDialog" title="Unregister directory" >
	<form action="unregister.do" method="post" class="blank">
		<table style="margin:0; width:100%;">
   			<tr style="height:5px">
   				<td class="noborder" colspan="2"/>
   			</tr>
			<tr>
   				<td class="noborder"><img class="icon"  src="images/icons/delete_folder_large.png"/></td>
    			<td class="noborder" style="text-align:left;">
    				<p><strong>You are about to unregister '<c:out value="${collectionUri}"/>'</strong></p>
					<p>Data will remain on disk but will not be accessible via iBIOMES anymore.
					<br/>Continue?</p>
				</td>
			</tr>
			<tr>
				<td class="noborder"><input type="hidden" name="uri" value="<c:out value="${collectionUri}" />"/></td>
				<td class="noborder"><input type="hidden" name="dispatchto" value="/index.do" /></td>
			</tr>
			<tr>
				<td style="border:0px;text-align:center;" colspan="2"><input class="button" type="submit" value="  Unregister  "/></td>
			</tr>
   			<tr style="height:5px"><td class="noborder" colspan="2"/></tr>
   		</table>
	</form>
</div>
