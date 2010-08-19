<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>


<h1><spring:message code="i18n.basename.title"/>: <c:out value="${basename}"/></h1>
<hr /> <br />


<display:table id="localeInformation" name="localeInformations" requestURI="" >


<spring:url value="/web/i18n/basenames/${basename}/messages/${localeInformation.locale}" var="url"/>

	<minos:column titleKey="i18n.basename.locale" property="locale"/>
	<minos:column titleKey="i18n.basename.countNew" property="countNew"/>
	<minos:column titleKey="i18n.basename.countUpdated" property="countUpdated"/>
	<minos:column titleKey="i18n.basename.countUnchanged" property="countUnchanged"/>
	<minos:column titleKey="i18n.basename.countTotal" sortProperty="countTotal">
		<a href="${url}">${localeInformation.countTotal}</a>
	</minos:column>

	<display:footer>
	<tr>
		<td colspan="5">
		
			<p><spring:message code="i18n.basename.newlanguage.title"/></p>
			<form method="post">
				<input type="text" name="lang" value="" id="newlanguage_name"/> <label for="newlanguage_name"><spring:message code="i18n.basename.newlanguage.title"/></label>
				<br />
				<input type="submit" value="<spring:message code="i18n.basename.newlanguage.button"/>"/>
			</form>
		</td>
	</tr>
	</display:footer>

</display:table>


<br />
<a href="<spring:url value="/web/i18n/basenames"/>">&laquo; <spring:message code="i18n.basenames.title"/></a>