<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="root" required="false" %>

<c:set var="defaultResource">${sessionScope.SPRING_SECURITY_CONTEXT.defaultStorageResource}</c:set>
<c:set var="userName">${sessionScope.SPRING_SECURITY_CONTEXT.userName}</c:set>
<c:choose>
	<c:when test="${empty root}">
		
		<p id="login">
			<c:choose>
			<c:when test="${not empty userName}">
				<c:if test="${userName != 'anonymous'}">
					<a class="link" href="user.do?name=<c:out value="${userName}" />"><img class="icon" src="images/icons/user_small.png" title="My profile"/></a>
				</c:if>
				 <c:out value="${userName}" /> | <a class="link" href="logout.do">logout</a>
				<c:if test="${userName != 'anonymous'}">
					&nbsp;&nbsp;&nbsp;
				 	<select id="resourceList" onchange="setDefaultResource()">
				 		<option value="">default</option>
					 	<c:forEach items="${sessionScope.resourceList}" var="resc" >
					 		<c:choose>
					 			<c:when test="${not empty defaultResource and defaultResource eq resc}">
					 				<option value="<c:out value="${resc}"/>" selected="selected"><c:out value="${resc}"/></option>
					 			</c:when>
					 			<c:otherwise>
					 				<option value="<c:out value="${resc}"/>"><c:out value="${resc}"/></option>
					 			</c:otherwise>
					 		</c:choose>
					 	</c:forEach>
					 </select>
				</c:if>
			</c:when>
			<c:otherwise>
				Not logged in | <a class="link" href="login.do">login</a>
			</c:otherwise>
			</c:choose>
		</p>
	</c:when>
	<c:otherwise>
		<p id="login">
			<c:choose>
			<c:when test="${not empty userName}">
				<a class="link" href="<c:out value="${root}" />user.do?name=<c:out value="${userName}" />"><img class="icon" src="<c:out value="${root}" />images/icons/user_small.png" title="My profile"/></a>
				 <c:out value="${userName}" /> | <a class="link" href="<c:out value="${root}" />logout.do">logout</a>
			</c:when>
			<c:otherwise>
				Not logged in | <a class="link" href="<c:out value="${root}" />login.do">login</a>
			</c:otherwise>
			</c:choose>
		</p>
	</c:otherwise>
</c:choose>