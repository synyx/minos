<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>


<h1><spring:message code="i18n.main.title"/></h1>
<hr /> <br />

<p>

<security:authorize ifAllGranted="I18N_LIST_BASES">
<a href="<spring:url value="/web/i18n/basenames"/>"><spring:message code="i18n.basenames.title"/></a>
<br />
<br />
</security:authorize>

<security:authorize ifAllGranted="I18N_REINITIALIZE_MESSAGES">
<a href="<spring:url value="/web/i18n/reinitialize"/>"><spring:message code="i18n.reinitialize.title"/></a>
<br />
</security:authorize>

<security:authorize ifAllGranted="I18N_IMPORT_MESSAGES">
<a href="<spring:url value="/web/i18n/import"/>"><spring:message code="i18n.import.title"/></a>
<br />
</security:authorize>

<security:authorize ifAllGranted="I18N_EXPORT_MESSAGES">
<a href="<spring:url value="/web/i18n/export"/>"><spring:message code="i18n.export.title"/></a>
</security:authorize>

</p>