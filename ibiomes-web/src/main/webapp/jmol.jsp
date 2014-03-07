<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/D/tdxhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>
<%@ page buffer="12kb" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="jsmol/JSmol.min.nojq.js"></script>
<script type="text/javascript">
	var Info = {
	  addSelectionOptions: false,
	  color: "#FFFFFF",
	  debug: false,
	  height: 800,
	  width: 1000,
	  j2sPath: "jsmol/j2s", // HTML5 only
	  use: "HTML5",  // "HTML5" or "Java"
	  script: "",
      disableJ2SLoadMonitor: true,
	  disableInitialConsole: true,
	  deferApplet: false,
	  deferUncover: true
	};
	Jmol.setDocument(0);
</script>

<script type="text/javascript" src="js/resources.js"></script>
<script type="text/javascript">

function moveUpItem(){
 $('#jmolFiles option:selected').each(function(){
  $(this).insertBefore($(this).prev());
 });
}
function moveDownItem(){
 $('#jmolFiles option:selected').each(function(){
  $(this).insertAfter($(this).next());
 });
}
function getOrderedFileList(){
	var str = '';
	$('#jmolFiles option').each(function(){
		str += "'" + $(this).val() + "' ";
	});
	return str;
}

function getDisplayType(){
	return $("input[name='jmolDisplayType']:checked").val();
}

function getDisplayWater(){
	return $("input[name='jmolDisplayWater']:checked").val();
}

function getAnimType(){
	return $("input[name='jmolAnimType']:checked").val();
}
function getSelectedFiles(){
	var str = '';
	var selectedFiles = $('#jmolFiles').val() || [];
	var i=0;
	for (i=0;i<selectedFiles.length;i++){
		str += "'" + selectedFiles[i] + "' ";
	};
	return str;
}

function selectAllFrames(){
	$('#jmolFiles option').prop('selected', true);
	var newScript = 'load MODELS ' + getOrderedFileList() + '; ' + getDisplayType() + getDisplayWater() + 'frame all;';
	Jmol.script(bigJmol,newScript);
}

function deselectAllFrames(){
	$('#jmolFiles option').prop('selected', false);
	Jmol.script(bigJmol,'frame 0;');
}

function playAllFrames(){
	var newScript = 'load FILES ' + getOrderedFileList() + '; ' + getDisplayType() + getDisplayWater() + ' anim fps 4; ' + getAnimType();
	Jmol.script(bigJmol,newScript);
}

$(document).ready(function() {
	$("input[name='jmolDisplayType']").change(function(){ 
		Jmol.script(bigJmol,getDisplayType());
	});
	$("input[name='jmolDisplayWater']").change(function(){ 
		Jmol.script(bigJmol,getDisplayWater());
	});
	$('#jmolFiles').change(function(){
		var newScript = 'load MODELS ' + getSelectedFiles() + '; ' + getDisplayType() + getDisplayWater() + ' frame all; ';
		Jmol.script(bigJmol,newScript);
	});

	Info.script = "<c:out value="${script}" escapeXml="false"/>";
	Jmol.getApplet("bigJmol", Info);
	$("#jmolAppletDiv").html(Jmol.getAppletHtml(bigJmol));
});
</script>

<title>iBIOMES</title>

</head>

<body style="background-color:#000000; color:#FFFFFF; font-size:11px;" >
	<table>
		<tr>
			<td style="vertical-align:top; border:1px white solid;">
				<form>
					<table>
						<tr style="height:10px"></tr>
						<tr>
						<td style="vertical-align:top;">
							<strong>Representation</strong><br/><br/>
							<input type="radio" name="jmolDisplayType" value="select all; wireframe off; cpk off; cartoons;"/> Cartoon<br/>
							<input type="radio" name="jmolDisplayType" value="select all; cartoons off; wireframe off;cpk on;"/> CPK<br/>							
							<input type="radio" name="jmolDisplayType" value="select all; cartoons off; cpk off; wireframe 30; trace off; spacefill 75;"/> Ball and stick<br/>
							<input type="radio" name="jmolDisplayType" value="select all; cartoons off; cpk off; wireframe 40; trace off;" checked="checked"/> Wireframe<br/>
							<input type="radio" name="jmolDisplayType" value="select all; cartoons off; cpk off; wireframe off; trace 100;"/> Trace<br/>
						</td>
						<td style="width:20px"></td>
						<td style="vertical-align:top;">
							<strong>Water</strong><br/><br/>
							 <input type="radio" name="jmolDisplayWater" value="select all; restrict selected;" checked="checked"/> Show<br/>
							 <input type="radio" name="jmolDisplayWater" value="restrict not water;"/> Hide<br/>
						</td>
						<td style="width:20px"></td>
						<td style="vertical-align:top;">
							<strong>Animation</strong><br/><br/>
							<input type="radio" name="jmolAnimType" value="anim mode ONCE; anim on;" /> Once<br/>
					        <input type="radio" name="jmolAnimType" value="anim mode loop 0; anim on;" checked="checked"/> Loop<br/>
					        <input type="radio" name="jmolAnimType" value="anim mode PALINDROME; anim on;"/> Forward and reverse<br/><br/>
					        <img style="cursor:pointer" src="images/icons/Go_back.png" onclick="Jmol.script(bigJmol,'animation frame -1'); return false;" title="Previous frame" />
							<img style="cursor:pointer" src="images/icons/Stop.png" onclick="Jmol.script(bigJmol,'anim off;'); return false;"  title="Stop" />
							<img style="cursor:pointer" src="images/icons/Go.png" onclick="playAllFrames(); return false;" title="Play" />
							<img style="cursor:pointer" src="images/icons/Go_forward.png" onclick="Jmol.script(bigJmol,'animation frame NEXT;'); return false;" title="Next frame" />
						</td>
						<td style="width:20px"></td>
						<td style="vertical-align:top;">
							<table style="vertical-align:top;">
					        	<tr><td><strong>Files</strong></td><td></td></tr>
					        	<tr>
									<td>
										<select id="jmolFiles" multiple="multiple" style="height:130px">
											<c:forEach items="${fileListLocal}" var="file" varStatus="f">
												<option value="<c:out value="${file}"/>"><c:out value="${fileList[f.index]}"/></option>
											</c:forEach>
										</select>
									</td>
									<td>
										<button type="button" style="width:110px" onclick="moveUpItem(); return false;" title="Move selected file up">Move up</button><br/>
										<button type="button" style="width:110px" onclick="moveDownItem(); return false;" title="Move selected file down">Move down</button><br/><br/>
										<button type="button" style="width:110px" onclick="selectAllFrames(); return false;">Select all</button><br/>
										<button type="button" style="width:110px" onclick="deselectAllFrames(); return false;">Deselect all</button>
									</td>
								</tr>
					         </table>
					    </td>
					    </tr>
					</table>
		        </form>
			</td>
		</tr>
	</table>
	
	<table>
	<tr><td style="border:1px white solid;">
		<div id="jmolAppletDiv"></div>
	</td></tr>
	</table>
</body>


</html>
