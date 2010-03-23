<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<minos:system-message />

<h2><spring:message code="skillz.resumes" /></h2>

<form method="post">
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
	<minos:column titleKey="skillz.matrix.template" sortProperty="skillz.template.name">
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
</form>
