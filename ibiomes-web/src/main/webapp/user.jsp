<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<link type="text/css" rel="stylesheet" href="style/css/ibiomes.css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" /> 

<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/users.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript" src="js/resources.js"></script>
<script type="text/javascript">
$(document).ready(function() 
{
	var username = "<c:out value="${user.name}" />";
	getUserGroupsForUser(username);
	getUploadsForUser(username);
	
	$( "#chgPwdDialog" ).dialog({
		height: 230,
        width: 500,
        autoOpen: false,
		modal: true,
        buttons: {
        	Update: function() {
        		updateUserPassword(username, 'currentpwd', 'newpwd1', 'newpwd2');
        		$( this ).dialog( "close" );
            },
            Cancel: function() {
        		$( this ).dialog( "close" );
            }
        }
	});
	$( "#chgPwdDialogOpen" ).click(function() {
        $( "#chgPwdDialog" ).dialog("open");
    });
	
});
</script>

<title>iBIOMES</title>
	
</head>

<body>
<!-- wrap starts here -->
<div id="wrap">

	<div id="header"><div id="header-content">	
		<h1 id="logo"><a href="index.do" title="">i<span class="gray">BIOMES</span><span style="font-size:0.6em"><i>&nbsp;&nbsp;Repository</i></span></a></h1>
		<h2 id="slogan">Integrated Biomolecular Simulations</h2>
		
		<!-- Menu Tabs -->
		<ul>
			<li><a href="index.do" id="current">Home</a></li>
			<li><a href="search.do">Search</a></li>
			<li><a href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>
			<li><a href="cart.do"><h:cart/></a></li>
		</ul>
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
	<br/>
	
	<!-- content-wrap starts here -->
	<div id="content-wrap">
	<div id="content">
		
		<div id="sidebar">
		
			<div class="sidebox">
				<h1>Quick search</h1>
				<form action="searchresults.do" class="searchform" method="post">
					<p>
					<input name="keyword" class="field" type="text"/>&nbsp;&nbsp;
  					<input style="border:0" type="image" src="images/icons/search_small.png" title="search"/>
			        <input type="hidden" name="type" value="keyword"/>
					</p>
				</form>
			</div>
			
			<div class="sidebox">
				<h1 class="clear">Quick links</h1>
				<ul class="sidemenu">
					<c:if test="${not empty sessionScope.homeDirectory}">
						<li><a href="collection.do?uri=<c:out value="${sessionScope.homeDirectory}" />">
								<img class="icon" src="images/icons/user_small.png" title="home directory" style="vertical-align:middle"/>&nbsp;&nbsp;Home directory
						</a></li>
						<li><a href="experimentsetmanager.do">
							<img class="icon" src="images/icons/folder_full_small.png" title="experiment sets" style="vertical-align:middle"/>&nbsp;&nbsp;Experiment sets
						</a></li>
					</c:if>
					<li><a href="search.do">
						<img class="icon" src="images/icons/search_small.png" title="search" style="vertical-align:middle"/>&nbsp;&nbsp;Search simulations
					</a></li>
					<li><a href="documentation.jsp">
						<img class="icon" src="images/icons/help_small.png" title="doucmentation" style="vertical-align:middle"/>&nbsp;&nbsp;Documentation
					</a></li>
					<li><a href="about.jsp">
						<img class="icon" src="images/icons/info_small.png" title="about us" style="vertical-align:middle"/>&nbsp;&nbsp;About us
					</a></li>
				</ul>
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
				<h1><img class="icon" src="images/icons/user.png"/>&nbsp;
				<c:choose>
				    <c:when test="${ user.name eq sessionScope.SPRING_SECURITY_CONTEXT.userName}">MY PROFILE</c:when>
					<c:otherwise>USER PROFILE</c:otherwise>
				</c:choose>
				&nbsp;[<c:out value="${ user.name}" />]</h1>
				
				<!-- ========  GENERAL INFO ========= -->
				
				<table class="layout-tight">
					<tr>
						<td style="text-align:right"><strong>Info</strong></td>
				        <td style="width:15px"></td>
						<td><c:out value="${user.info}" /></td>
					</tr>
					<tr>
						<td style="text-align:right"><strong>Comments</strong></td>
				        <td></td>
						<td><c:out value="${user.comment}" /></td>
					</tr>
					<tr>
				        <td style="text-align:right"><strong>DN</strong></td>
				        <td></td>
						<td><c:out value="${user.userDN}" /></td>
				    </tr>
				    <tr>
				        <td style="text-align:right"><strong>Member since</strong></td>
				        <td></td>
						<td>
							<fmt:formatDate var="createDate" value="${user.createTime}" pattern="dd MMMM yyyy" />
							<c:out value="${createDate}" />
						</td>
				    </tr>
				</table>
				
				<!-- ========  PWD CHANGE ========= -->
			
		    	<c:if test="${ user.name eq sessionScope.SPRING_SECURITY_CONTEXT.userName}">
					<table>
					    <tr>
					        <td><a class="link" id="chgPwdDialogOpen" style="cursor:pointer" href="#">Change my password</a>
					        	<div id="chgPwdDialog" title="Update iBIOMES password">
									<table>
										<tr style="height:10px"><td style="border:0px;" colspan="3"></td></tr>
						        		<tr>
							            	<td style="border:0px;"><img class="icon" src="images/icons/info_small.png" title="Current iBIOMES password"/></td>
						            		<td style="border:0px;"><strong>Current password</strong></td>
							            	<td style="border:0px;"><input type="password" id="currentpwd" value=""/></td>
							        	</tr>
							        	<tr>
							            	<td style="border:0px;"><img class="icon" src="images/icons/info_small.png" title="New iBIOMES password"/></td>
						            		<td style="border:0px;"><strong>New password</strong></td>
							            	<td style="border:0px;"><input type="password" id="newpwd1" value=""/></td>
							       		</tr>
							        	<tr>
							            	<td style="border:0px;"><img class="icon" src="images/icons/info_small.png" title="New iBIOMES password"/></td>
						            		<td style="border:0px;"><strong>New password (confirm)</strong></td>
							            	<td style="border:0px;"><input type="password" id="newpwd2" value=""/></td>
							       		</tr>
							       	</table>
						       	</div>
					        </td>
					    </tr>
					</table>
				</c:if>
			</div>
			<br/>
			<div class="postnext">
				<!-- ========  GROUP MEMBERSHIPS ========= -->
				<c:choose>
			    <c:when test="${ user.name eq sessionScope.SPRING_SECURITY_CONTEXT.userName}">
					<h3>My groups</h3>
				</c:when>
				<c:otherwise><h3><i><c:out value="${user.name}" /></i>'s group memberships</h3></c:otherwise>
				</c:choose>
				<div id="user-memberships"></div>
			</div>
			<br/>
			<div class="postnext">
				<!-- ========  LIST OF UPLOADS ========= -->
				
				<c:choose>
			    <c:when test="${ user.name eq sessionScope.SPRING_SECURITY_CONTEXT.userName}">
					<h3>My latest uploads</h3>
				</c:when>
				<c:otherwise><h3>Latest uploads by <i><c:out value="${user.name}" /></i></h3></c:otherwise>
				</c:choose>
				<br/>
				<div id="user-uploads"></div>
				<div id="uploads-loading" style="text-align:center;"><img src="animations/loading50.gif"/></div>
		    
			</div>
			
	    	<br />			
			
			<div id="error-message" title="Error"></div>
			<div id="info-message" title="Info"></div>
			<div id="confirm-message" title="Confirm"></div>
	
	</div>								
		
	<!-- content-wrap ends here -->		
	</div></div>

<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
