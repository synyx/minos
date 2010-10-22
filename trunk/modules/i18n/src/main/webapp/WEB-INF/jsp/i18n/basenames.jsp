<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>


<h1><spring:message code="i18n.basenames.title"/></h1>
<hr /> <br />


<ul>
<c:forEach items="${basenames}" var="basename">

<li><a href="<spring:url value="/web/i18n/basenames/${basename}"/>"><c:out value="${basename}"/></a></li>

</c:forEach>


</ul>

<br />

<a href="<spring:url value="/web/i18n/"/>">&laquo; <spring:message code="i18n.main.title"/></a>