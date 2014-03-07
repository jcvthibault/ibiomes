<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	$(document).ready(function() 
	{
		$("#username").focus();
		
		$("#guestLogin").click(function() {
			if ($('#guestLogin').is(':checked')) {
			    $("#credentials").hide();
			} else {
			    $("#credentials").show();
			} 
		});
	});
</script>

<link rel="stylesheet" href="style/css/ibiomes.css" type="text/css" />
<link type="text/css" href="style/smoothness/jquery-ui-1.8.20.custom.css" rel="Stylesheet" /> 

<title>iBIOMES</title>
	
</head>

<body>
<!-- wrap starts here -->
<div id="wrap">

	<div id="header"><div id="header-content">	
		
		<h1 id="logo"><a href="index.do" title="">i<span class="gray">BIOMES</span><span style="font-size:0.6em"><i>&nbsp;&nbsp;Repository</i></span></a></h1>	
		<h2 id="slogan">Disseminating simulation data to create knowledge</h2>		
		
		<!-- Menu Tabs -->
		<ul>
			<li><a href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>			
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
	
	<div class="headerphoto"></div>
	<br/>
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
		
		<div id="sidebar" >
		
			<div class="sidebox">
			
				<h1>Short About</h1>
				<p>iBIOMES is a distributed infrastructure for biomololecular
				  simulation data management and sharing.</p>
						
			</div>			

			<div class="sidebox">	
			
				<h1 class="clear">Contributors</h1>
				<ul class="sidemenu">
					<li><a href="http://www.bmi.utah.edu">University of Utah</a></li>
					<li><a href="http://www.chpc.utah.edu/~cheatham/">Cheatham Lab</a></li>
					<li><a href="http://www.chpc.utah.edu">CHPC</a></li>
				</ul>	
				
			</div>
		</div>	
	
		<div id="main">
		
			<div class="post">
				
				<h1>iBIOMES Access</h1>
			
				<h3>Login</h3>
				<div>
					<c:if test="${ not empty error }">
				   		<div class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="margin: 0 .8em;"> 
								<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> 
								<strong>Error:</strong> <c:out value="${error}" /></p>
							</div>
						</div>
					</c:if>
				</div>
				<form id="loginForm" method="post" action="login.do">
					<table>
						<tr>
							<td class="noborder" style="text-align:right"><label>Guest login</label></td>
							<td class="noborder">
								<input id="guestLogin" name="guestLogin" type="checkbox" value="true" />
							</td>
						</tr>
					</table>
					<table id="credentials">
						<tr>
							<td class="noborder" style="text-align:right"><label>Username</label></td>
							<td class="noborder"><img src="images/icons/info_small.png" title="Username" /></td>
							<td class="noborder"><input class="field" id="username" name="username" value="" type="text" size="30" /></td>
						</tr>
						<tr>
							<td class="noborder" style="text-align:right"><label>Password</label></td>
							<td class="noborder"><img src="images/icons/info_small.png" title="Password" /></td>
							<td class="noborder"><input class="field" name="password" value="" type="password" size="30" /></td>
						</tr>
					</table>
					<table>
						<c:choose>
					   		<c:when test="${ defaultIrodsConnection.enabled }">
								<tr id="presetLogin">
								<td class="noborder" colspan="3">
									<input type="hidden" name="host" id="host" value="${defaultIrodsConnection.host}"/>
									<input type="hidden" name="port" id="port" value="${defaultIrodsConnection.port}"/>
									<input type="hidden" name="zone" id="zone" value="${defaultIrodsConnection.zone}"/>
								   	<input type="hidden" name="resource" id="resource" value="${defaultIrodsConnection.defaultResource}"/>
							   	</td></tr>
					   		</c:when>
					   		<c:otherwise>
								<tr>
									<td class="noborder" style="text-align:right"><label>Host</label></td>
									<td class="noborder"><img src="images/icons/info_small.png" title="Name or IP address of the iRODS host" /></td>
									<td class="noborder"><input class="field" name="host" id="host" value="" size="30" /></td>
								</tr>
								<tr>
									<td class="noborder" style="text-align:right"><label>Port</label></td>
									<td class="noborder"><img src="images/icons/info_small.png" title="Port of the iRODS host" /></td>
									<td class="noborder"><input class="field" name="port" id="port" value="" size="30" /></td></tr>
								<tr>
									<td class="noborder" style="text-align:right"><label>Zone</label></td>
									<td class="noborder"><img src="images/icons/info_small.png" title="Name of the iRODS zone" /></td>
									<td class="noborder"><input class="field" name="zone" id="zone" value="" size="30" /></td></tr>
								<tr>
									<td class="noborder" style="text-align:right"><label>Resource</label></td>
									<td class="noborder"><img src="images/icons/info_small.png" title="Name of default iRODS resource" /></td>
									<td class="noborder"><input class="field" name="resource" id="resource" value="" size="30" /></td>
								</tr>
								<tr style="height:10px"></tr>
							</c:otherwise>
						</c:choose>
						<tr><td class="noborder" style="text-align:right" colspan="3"><input class="button" type="submit" value=" Login "/></td></tr>
					</table>
				</form>	
				<br/>		
			</div>
		</div>					
		
	<br/>
	<br/>
	<!-- content-wrap ends here -->		
	</div></div>
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
