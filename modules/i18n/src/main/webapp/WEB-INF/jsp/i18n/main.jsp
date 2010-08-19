<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>


<h1><spring:message code="i18n.main.title"/></h1>
<hr /> <br />

<p>
<a href="<spring:url value="/web/i18n/basenames"/>"><spring:message code="i18n.basenames.title"/></a>
<br />

<br />
<a href="<spring:url value="/web/i18n/reinitialize"/>"><spring:message code="i18n.reinitialize.title"/></a>
<br />
<a href="<spring:url value="/web/i18n/import"/>"><spring:message code="i18n.import.title"/></a>
<br />
<a href="<spring:url value="/web/i18n/export"/>"><spring:message code="i18n.export.title"/></a>



</p>