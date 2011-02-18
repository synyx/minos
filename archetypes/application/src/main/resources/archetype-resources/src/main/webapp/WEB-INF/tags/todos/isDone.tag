<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="value" type="java.lang.Boolean" required="true" %>
<c:choose>
	<c:when test="${value}">
		<span class="icon todo-done"><spring:message code="todos.item.done.yes" /></span>
	</c:when>
	<c:otherwise>
		<span class="icon todo-not-done"><spring:message code="todos.item.done.no" /></span>
	</c:otherwise>
</c:choose>