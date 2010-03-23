<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://www.joda.org/joda/time/tags" %>

<%@ taglib prefix="minos" tagdir="/WEB-INF/tags/core" %>

<script type="text/javascript">
<!--
$(function() {
	$(".monthdatepicker").datepicker({ dateFormat: 'mm/yy' });
});
//-->
</script>

<h2><spring:message code="skillz.reference" /></h2>
<minos:form modelAttribute="reference" action="/web/skillz/resume/references">
	<table id="reference" class="form">
		<tr>
			<td class="label"><spring:message code="skillz.reference.from" />:</td>
			<td><form:input path="start" cssClass="monthdatepicker" /></td>
			<td class="label"><spring:message code="skillz.reference.to" />:</td>
			<td><form:input path="end" cssClass="monthdatepicker" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.project" />:</td>
			<td>
				<form:select path="project">
					<form:option value="" label="" />
					<form:options items="${projects}" itemLabel="name" itemValue="id" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.responsibilities" />:</td>
			<td colspan="3">
				<form:checkboxes items="${responsibilities}" path="responsibilities" />
			</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.reference.additionalDescription" />:</td>
			<td colspan="3">
				<form:textarea path="additionalDescription" cssClass="textarea" cols="70" rows="8" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<form:checkbox path="omitProjectDescription" />
				<spring:message code="skillz.reference.omitProjectDescription" />
			</td>
		</tr>
	</table>
	<input type="submit" value="<spring:message code="core.ui.save" />" />
	<a href="../../resume#tabs-2"><spring:message code="core.ui.cancel" /></a>
</minos:form>