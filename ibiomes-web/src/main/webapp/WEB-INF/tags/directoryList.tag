
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="subdirectories" required="true" type="java.util.List" %>

<table>
<tr style="height:0px">
<c:forEach items="${subdirectories}" var="subdir" varStatus="i">
	<c:set var="rest" value="${(i.count-1) % 3}" /> 
	<c:if test="${rest eq 0}">
		</tr><tr>
	</c:if>
	<td>
		<a class="link" href="collection.do?uri=<c:out value="${subdir.absolutePath}" />">
			<img class="icon" src="images/icons/folder_full.png"/><c:out value="${subdir.name}" />
		</a>
	</td>
	
	<c:choose>
	<c:when test="${rest eq 1}">
		<td/>
	</c:when>
	<c:when test="${rest eq 2}">
		<td/><td/>
	</c:when>
	</c:choose>
	
</c:forEach>
</tr>
</table>