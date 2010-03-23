<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://www.joda.org/joda/time/tags" %>

<h2>Reference</h2>
<spring:form modelAttribute="reference">
	<table class="form">
		<tr>
			<td class="label">From:</td>
			<td><spring:input path="start" /></td>
			<td class="label">To:</td>
			<td><spring:input path="end" /></td>
		</tr>
		<tr>
			<td class="label">Project:</td>
			<td>
				<spring:select path="project">
					<spring:option value="" label="" />
					<spring:options items="${projects}" itemLabel="name" itemValue="id" />
				</spring:select>
			</td>
		</tr>
		<tr>
			<td class="label">Responsibilities:</td>
			<td colspan="3">
				<spring:checkboxes items="${responsibilities}" path="responsibilities" />
			</td>
		</tr>
		<tr>
			<td class="label">Additional description:</td>
			<td colspan="3">
				<spring:textarea path="additionalDescription" cssClass="textarea" cols="70" rows="8" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<spring:checkbox path="omitProjectDescription" />
				Omit project description
			</td>
		</tr>
	</table>
	<input type="submit" />
</spring:form>