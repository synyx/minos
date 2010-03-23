<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>
	<core:choose>
		<core:when test="${empty owner}">
			<spring:message code="skillz.project" />
		</core:when>
		<core:otherwise>
			<spring:message code="skillz.project.private" />
		</core:otherwise>
	</core:choose>
</h2>
<form:form modelAttribute="project">
	<table class="form">
		<tr>
			<td class="label"><spring:message code="name" />:</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="description" />:</td>
			<td><form:textarea path="description" cssClass="textarea" cols="70" rows="8" /></td>
			<td><form:errors path="description" /></td>
		</tr>
		<tfoot>
			<tr>
				<td>
					<core:if test="${not empty owner}">
						<input type="hidden" name="owner" id="owner" value="${owner.id}" />
					</core:if>
					<input type="submit" value="<spring:message code="ok" />" />
				</td>
			</tr>
		</tfoot>
	</table>
</form:form>