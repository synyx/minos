<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<%@ taglib prefix="skillz" tagdir="/WEB-INF/tags/skillz" %>

<h2><spring:message code="skillz.resumes" /></h2>

<form:form method="get">
	<select name="selectFilter">
		<c:forEach items="${resumeFilters}" var="filter">
			<option value="${filter.messageKey}"<c:if test="${filter.messageKey eq resumeFilter.messageKey}"> selected="selected"</c:if>>
				<spring:message code="${filter.messageKey}" />
			</option>
		</c:forEach>
	</select>
	<input type="submit" />
</form:form>
<form:form method="get" commandName="resumeFilter">
	<c:forEach items="${resumeFilter.parameters.rawParameters}" var="parameter" varStatus="status">
		<skillz:filter-parameter filterParam="${parameter}" index="${status.index}" />
	</c:forEach>
	<input type="hidden" name="filterName" value="${resumeFilter.messageKey}" />
	<input type="submit" />
</form:form>

<form:form method="post">
<display:table id="resume" name="resumes" requestURI="resumes" >
	<minos:column class="checkbox" style="width: 1%">
		<input type="checkbox" name="resumes" value="${resume.id}" />
	</minos:column>
	<minos:column titleKey="name" sortProperty="resume.subject.lastname">
		<a href="resumes/${resume.id}">${resume.subject.lastname}, ${resume.subject.firstname}</a>
	</minos:column>
	<minos:column property="position" titleKey="skillz.resume.position" />
	<minos:column property="lastModifiedDate" class="date" />
	<minos:column property="lastModifiedBy" />
	<minos:column titleKey="skillz.template" sortProperty="skillz.template.name">
		${resume.skillz.template.name}
	</minos:column>
	<minos:column class="actions">
		<a href="resumes/${resume.id}"><img src="<c:url value="/images/skillz/resume_edit.png" />" alt="Edit" /></a>
	</minos:column>
	<display:footer>
		<tr>
			<td></td>
			<td colspan="4">
				<select name="template">
					<c:forEach var="template" items="${templates}">
						<option value="${template.id}">${template.name}</option>
					</c:forEach>
				</select>
				<input type="submit" />
			</td>
		</tr>
	</display:footer>
</display:table>
</form:form>
