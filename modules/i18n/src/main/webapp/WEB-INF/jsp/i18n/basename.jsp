<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>


<h1><spring:message code="i18n.basename.title"/>: <c:out value="${basename}"/></h1>
<hr /> <br />


<ul>
<c:forEach items="${locales}" var="locale">

<li><a href="<spring:url value="/web/i18n/basenames/${basename}/messages/${locale}"/>">

<c:choose>
<c:when test="${locale == null }">
	<spring:message code="i18n.basename.default"/>
</c:when>
<c:otherwise><c:out value="${locale}"/></c:otherwise>
</c:choose>

</a></li>

</c:forEach>

</ul>

<br />
<a href="<spring:url value="/web/i18n/basenames"/>">&laquo; <spring:message code="i18n.basenames.title"/></a>