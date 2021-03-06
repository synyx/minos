<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<html>
<head>
	<script type="text/javascript" src="<c:url value="/js/jquery-1.4.2.min.js" />"> </script>
	<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.8.4.custom.min.js" />"> </script>
	<script type="text/javascript" src="<c:url value="/js/jquery.simpleautogrow-0.1.1.js" />"> </script>
</head>
<body>
<h1><spring:message code="i18n.basename.title"/>: <c:out value="${basename}"/></h1>
<hr /> <br />


<display:table id="localeInformation" name="localeInformations" requestURI="" >

	<minos:column titleKey="i18n.basename.locale" property="locale"/>
	<minos:column titleKey="i18n.basename.newlanguage.required" sortProperty="required">
		<c:if test="${localeInformation.required}">
			<img src="<spring:url value='/images/core/okay.png' />" />
		</c:if>
	</minos:column>
		
	<minos:column titleKey="i18n.basename.countNew" sortProperty="countNew">

		<spring:url value="/web/i18n/basenames/${localeInformation.basename}/messages/${localeInformation.locale}?filter=new" var="url"/>
		<a href="${url}">${localeInformation.countNew}</a>
	</minos:column>
	<minos:column titleKey="i18n.basename.countUpdated" sortProperty="countUpdated">
	
		<spring:url value="/web/i18n/basenames/${localeInformation.basename}/messages/${localeInformation.locale}?filter=updated" var="url"/>
		<a href="${url}">${localeInformation.countUpdated}</a>	
	</minos:column>
	<minos:column titleKey="i18n.basename.countUnchanged" property="countUnchanged"/>
	<minos:column titleKey="i18n.basename.countTotal" sortProperty="countTotal">
	
		<spring:url value="/web/i18n/basenames/${localeInformation.basename}/messages/${localeInformation.locale}" var="url"/>
		<a href="${url}">${localeInformation.countTotal}</a>
	</minos:column>
	
	<minos:column titleKey="i18n.basename.actions" sortable="false">
		
		<c:if test="${localeInformation.deletable}">
		<security:authorize ifAllGranted="I18N_DELETE_LANGUAGES">		
		<a href="<spring:url value="/web/i18n/basenames/${localeInformation.basename}/messages/${localeInformation.locale}/deleteconfirmation"/>"><spring:message code="i18n.basename.deleteLanguage.link"/></a>
		</security:authorize>
		</c:if>
	</minos:column>


</display:table>

<security:authorize ifAllGranted="I18N_CREATE_LANGUAGES">
<h3><spring:message code="i18n.basename.newlanguage.title"/></h3>
	<form:form modelAttribute="newLanguage" method="post">
			
		<table>
			<tr>
				<td><label for="newlanguage_lang"><spring:message code="i18n.basename.newlanguage.lang"/></label></td>
				<td><form:input path="realLocale" id="newlanguage_lang"/> </td>
				<td><small><spring:message code="i18n.basename.newlanguage.lang.hint"/></small></td>
			</tr>
			<tr>
				<td><label for="newlanguage_required"><spring:message code="i18n.basename.newlanguage.required"/></label></td>
				<td><form:checkbox path="required" id="newlanguage_required"/> </td>
				<td><small><spring:message code="i18n.basename.newlanguage.required.hint"/></small></td>
			</tr>
			<tr>
				<td colspan="3">
					<form:hidden path="basename"/>
					<input type="submit" value="<spring:message code="i18n.basename.newlanguage.button"/>"/>	
				</td>
			</tr>
		</table>
				
	</form:form>
</security:authorize>

<security:authorize ifAllGranted="I18N_UPLOAD_MESSAGES">
<a href="<spring:url value="/web/i18n/basenames/${basename}/import"/>"><spring:message code="i18n.basenames.import.title"/></a>
</security:authorize>
<br/>
<br />
<a href="<spring:url value="/web/i18n/basenames"/>">&laquo; <spring:message code="i18n.basenames.title"/></a>
</body>
</html>
