<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags"%>

<%@ taglib prefix="skillz" tagdir="/WEB-INF/tags/skillz"%>
<%@ taglib prefix="c" tagdir="/WEB-INF/tags/core"%>

<script type="text/javascript">
	<!--
	$(function() {
		$("#tabs").tabs();
		
		$("#birthday").datepicker();
	});
	//-->
</script>

<div id="tabs">
<ul>
	<li><a href="#tabs-1"><spring:message code="skillz.resume.personal" /></a></li>
	<li><a href="#tabs-2" class="iconized references"><spring:message code="skillz.references" /></a></li>
	<li><a href="#tabs-3"><spring:message code="skillz.skillMatrix" /></a></li>
</ul>
<div id="tabs-1" class="tab">

<form:form modelAttribute="resume" action="resumes">
	<table class="form" style="float: left">
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
			<td><form:input path="birthday" /></td>
			<td><form:errors path="birthday" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message
				code="skillz.resume.foreignLanguages" />:</td>
			<td><form:input path="foreignLanguages" /></td>
			<td><form:errors path="foreignLanguages" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message
				code="skillz.resume.certifications" />:</td>
			<td><form:textarea path="certifications" cssClass="textarea"
				cols="70" rows="4" /></td>
			<td><form:errors path="certifications" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message
				code="skillz.resume.publications" />:</td>
			<td><form:textarea path="publications" cssClass="textarea"
				cols="70" rows="4" /></td>
			<td><form:errors path="publications" /></td>
		</tr>
		<tfoot>
			<tr>
				<td></td>
				<td colspan="3"><input type="submit"
					value="<spring:message code="core.ui.save" />" /></td>
			</tr>
		</tfoot>
	</table>
</form:form>
<div id="photo" style="width: 300px; float: right"><core:if
	test="${not empty resume.photo}">
	<table class="form" style="width: 100%">
		<tr>
			<td class="label"><spring:message code="skillz.resume.photo" />:
			</td>
			<td><img src="resumes/${resume.id}/photo" width="200px" /></td>
			<td><c:deleteLink href="resumes/${resume.id}/photo"
				imageUrl="/images/core/delete.png" /></td>
		</tr>
	</table>
</core:if> <form:form method="put" modelAttribute="resume" name="savePhoto"
	action="resumes/${resume.id}/photo" enctype="multipart/form-data">
	<table class="form" style="width: 100%">
		<core:if test="${empty resume.photo}">
			<tr>
				<td class="label"><spring:message code="skillz.resume.photo.new" />:</td>
			</tr>
		</core:if>
		<spring:hasBindErrors name="resume.photo">
			<core:if test="${not empty errors}">
				<tr>
					<td><form:errors path="photo" /></td>
				</tr>
			</core:if>

		</spring:hasBindErrors>
		<tr>
			<td><input type="file" name="photoBinary" /></td>
		</tr>
		<tr>
			<td><input type="submit"
				value="<spring:message code="core.ui.save" />" /></td>
		</tr>
	</table>
</form:form></div>

</div>
<div id="tabs-2" class="tab">

<jsp:include page="resume/references.jsp" />

</div>
<div id="tabs-3" class="tab">

<skillz:matrix id="skillzMatrix" matrix="${resume.skillz}" levels="${levels}" /></div>

</div>