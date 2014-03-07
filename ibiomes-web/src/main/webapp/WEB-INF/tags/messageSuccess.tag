<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="message" required="true" %>

<div class="ui-widget">
	<div class="ui-state-highlight ui-corner-all" style="margin: 0 .8em;"> 
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
		<c:out value="${message}" /></p>
	</div>
</div>