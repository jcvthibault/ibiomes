<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="message" required="true" %>

<div class="ui-widget">
	<div class="ui-state-error ui-corner-all" style="margin: 0 .8em;"> 
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> 
		<strong>Error:</strong> <c:out value="${message}" /></p>
	</div>
</div>