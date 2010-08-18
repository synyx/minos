<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>


<h1><spring:message code="i18n.basename.title"/>: <c:out value="${basename}"/></h1>
<hr /> <br />


<display:table id="localeInformation" name="localeInformations" requestURI="" >

<c:choose>
<c:when test="${localeInformation.locale.default}">
	<spring:url value="/web/i18n/basenames/${basename}/messages/" var="url"/>
</c:when>
<c:otherwise>
<spring:url value="/web/i18n/basenames/${basename}/messages/${localeInformation.locale}" var="url"/>
</c:otherwise>
</c:choose>

	<minos:column titleKey="i18n.basename.locale" property="locale"/>
	<minos:column titleKey="i18n.basename.countNew" property="countNew"/>
	<minos:column titleKey="i18n.basename.countUpdated" property="countUpdated"/>
	<minos:column titleKey="i18n.basename.countUnchanged" property="countUnchanged"/>
	<minos:column titleKey="i18n.basename.countTotal" sortProperty="countTotal">
		<a href="${url}">${localeInformation.countTotal}</a>
	</minos:column>

</display:table>


<ul>
<c:forEach items="${localeInformations}" var="localeInformation">

<li>


</li>

</c:forEach>

</ul>

<br />
<a href="<spring:url value="/web/i18n/basenames"/>">&laquo; <spring:message code="i18n.basenames.title"/></a>