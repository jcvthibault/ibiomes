<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<h:meta/>

<link rel="stylesheet" href="style/css/ibiomes.css" type="text/css" />
<link type="text/css" rel="Stylesheet" href="style/smoothness/jquery-ui-1.10.4.custom.css" /> 


<script type="text/javascript">
	var contextPath = '<c:out value="${pageContext.request.contextPath}"/>';
</script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/cart.js"></script>
<script type="text/javascript" src="js/dialogs.js"></script>
<script type="text/javascript" src="js/resources.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	
	var code = 'org.irods.jargon.idrop.lite.iDropLiteApplet.class';
	var codebase = contextPath + '/applets';
	var archive = 'idrop-lite-2.0.0-jar-with-dependencies.jar';
	var width = "798px";
	var height = "500px";
	
	loadCartItems();
	
	$("#checkoutButton").click(function() {
		$('#cartApplet').empty();
		loadCartApplet('cartApplet', code, codebase, archive, width, height, '<c:out value="${sessionScope.SPRING_SECURITY_CONTEXT.zone}"/>', '<c:out value="${sessionScope.SPRING_SECURITY_CONTEXT.host}"/>', '<c:out value="${sessionScope.SPRING_SECURITY_CONTEXT.port}"/>', '<c:out value="${sessionScope.SPRING_SECURITY_CONTEXT.userName}"/>', '', '<c:out value="${password}"/>', '<c:out value="${key}"/>');
	});
	
	$("#deleteItemButton").click(function() {
		removeItemsFromCart();
	});

	$("#clearItemsButton").click(function() {
		$('#cartApplet').empty();
		clearCart();
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
			<li><a href="index.do">Home</a></li>
			<li><a href="search.do">Search</a></li>
			<li><a href="documentation.jsp">Documentation</a></li>
			<li><a href="about.jsp">About</a></li>
			<li><a href="cart.do" id="current"><h:cart/></a></li>
		</ul>
		
		<!-- login/logout link -->
		<h:login/>
	
	</div></div>
	
	<br/>
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">		
	
		<div id="main" style="width:100%">
				    
			<div class="post">
				<h1>Shopping cart</h1>
				<p><strong>Files in your shopping cart</strong></p>
				<div id="cartItemsLoading" style="text-align:center;display:none;"><img src="animations/loading26.gif"></img></div>
				<div id="cartItems">
					<table>
						<tr>
							<td rowspan="3">
								<select id="cartItemList" name="uri" multiple="multiple" style="width:580px" size="5"></select>
							</td>
							<td><input id="deleteItemButton" style="width:70px;" type='button' value=' Delete ' title='remove selected file(s) from shopping cart' /></td>
						</tr>
						<tr>
	    					<td><input id="clearItemsButton" style="width:70px;" type='button' value=' Clear all ' title='remove all files from shopping cart' /></td>
	    				</tr>
						<tr>
							<td><input id="checkoutButton" style="width:70px;" type='button' value=' Checkout ' title='checkout cart' /></td>
						</tr>
					</table>
				</div>
				<br/>
			</div>
		    
			<div>
	       		<c:if test="${ not empty error }">
					<br/>
			   		<h:messageError message="${error}" />
				</c:if>
				<c:if test="${ not empty message }">
					<br/>
			   		<h:messageSuccess message="${message}" />
				</c:if>
		    </div>
			<br/>
			
			<div id="cartApplet" style="border:1px solid #AAAAAA;background-color:#bbb;vertical-align:middle;text-align:center;width:800px;">
			</div>
			<br/><br/>
		</div>			
		
	<!-- for jquery dialogs -->
	
	<div id="error-message" title="Error"></div>
	<div id="info-message" title="Info"></div>
	<div id="confirm-message" title="Confirm"></div>
	
	<!-- content-wrap ends here -->
	</div></div>
	
<!-- wrap ends here -->
</div>

<!-- footer -->
<h:footer/>

</body>
</html>
