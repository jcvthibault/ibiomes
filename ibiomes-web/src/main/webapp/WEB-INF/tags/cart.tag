<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

Cart<span id="nitems-cart">
<c:if test="${not empty sessionScope.shoppingCartItemCount}">
	(<c:out value="${sessionScope.shoppingCartItemCount}" />)
</c:if>
</span>