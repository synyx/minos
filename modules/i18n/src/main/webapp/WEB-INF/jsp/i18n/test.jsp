<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>


<h1><spring:message code="i18n.test.title"/></h1>
<hr /> <br />
<p><spring:message code="i18n.test.text"/></p>


<h1><spring:message code="i18n.test.availableMessages"/></h1>

<c:forEach items="${availableMessages}" var="messages">

<h3><spring:message code="i18n.test.availableMessages.basename"/> ${messages.key}</h3>
<p>
<textarea readonly="readonly" rows="20" cols="80" style="width:100%"><c:out value="${messages.value.fullInfo}"/></textarea>
</p>
</c:forEach>

<br />
<a href="<spring:url value="/web/i18n/"/>">&laquo; <spring:message code="i18n.main.title"/></a>