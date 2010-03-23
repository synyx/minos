<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>

<%@ taglib prefix="skillz" tagdir="/WEB-INF/tags/skillz" %>
<%@ taglib prefix="c" tagdir="/WEB-INF/tags/core" %>

<minos:system-message />

<h2><spring:message code="skillz.resume.personal" /></h2>
<form:form modelAttribute="resume" action="resumes">
	<table class="form">
		<tr>
			<td class="label"><spring:message code="name" />:</td>
			<td>${resume.subject.firstname} ${resume.subject.lastname}</td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.resume.title" />:</td>
			<td><form:input path="title" /></td>
			<td><form:errors path="title" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.resume.position" />:</td>
			<td><form:input path="position" /></td>
			<td><form:errors path="position" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="birthday" />:</td>
			<td><form:input path="birthday"/></td>
			<td><form:errors path="birthday" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.resume.foreignLanguages" />:</td>
			<td><form:input path="foreignLanguages"/></td>
			<td><form:errors path="foreignLanguages" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.resume.certifications" />:</td>
			<td><form:textarea path="certifications" cssClass="textarea" cols="70" rows="8" /></td>
			<td><form:errors path="certifications" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.resume.publications" />:</td>
			<td><form:textarea path="publications" cssClass="textarea" cols="70" rows="8" /></td>
			<td><form:errors path="publications" /></td>
		</tr>
		<tfoot>	
			<tr>
				<td></td>
				<td colspan="3">
					<input type="submit" value="<spring:message code="core.ui.save" />" />
				</td>
			</tr>
		</tfoot>
	</table>
</form:form>

<script>
$(".projectDescription > p.skillzProjectName").click(function() {
	$("p > p").animate({
		   height: 'toggle', opacity: 'toggle'
	 }, "slow");
});
</script>

<h2 class="iconized references"><spring:message code="skillz.references" /></h2>
<table class="form">
	<core:forEach items="${resume.references}" var="reference">
		<tr>
			<td width="1%" nowrap="nowrap" valign="top">
				<fmt:format value="${reference.start}" pattern="MMMM yyyy"/> - 
				<core:choose>
					<core:when test="${reference.isStillActive}">
						<spring:message code="today" />
					</core:when>
					<core:otherwise>
						<fmt:format value="${reference.end}" pattern="MMMM yyyy" />
					</core:otherwise>
				</core:choose>
			</td>
			<td class="projectDescription">
				<p class="skillzProjectName">${reference.project.name}</p>
				<core:if test="${!reference.omitProjectDescription}">
					<p class="skillzProjectDescription">${reference.project.description}</p>
				</core:if>
				<core:if test="${not empty reference.additionalDescription}">
					<p class="skillzProjectDescription">${reference.additionalDescription}</p>
				</core:if>
				<core:if test="${not empty reference.responsibilities}">
					<p class="skillzProjectName"><spring:message code="skillz.responsibilities" />:</p>
					<ul>
						<core:forEach items="${reference.responsibilities}" var="responsibility">
							<li>${responsibility.name}</li>
						</core:forEach>
					</ul>
				</core:if>
			</td>
			<td valign="top" nowrap="nowrap">
				<c:imageLink href="reference/form?id=${reference.id}" imageUrl="/images/skillz/reference_edit.png" altKey="skillz.reference.edit" />
				<c:imageLink href="reference/delete?id=${reference.id}" imageUrl="/images/skillz/reference_delete.png" altKey="skillz.reference.delete" />
			</td>
		</tr>
	</core:forEach>
	<tfoot>
		<tr>
			<td colspan="3">
			<c:imageLink href="reference/form" imageUrl="/images/skillz/reference_add.png" altKey="skillz.reference.new" />
		</tr>
	</tfoot>
</table>

<h2><spring:message code="skillz.skillMatrix" /></h2>
<skillz:matrix matrix="${resume.skillz}" levels="${levels}" />