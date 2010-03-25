<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>

<%@ taglib prefix="c" tagdir="/WEB-INF/tags/core" %>

<h2><spring:message code="skillz.category" /></h2>

<c:form modelAttribute="category" action="/web/skillz/categories">
	<table class="form">
		<tr>
			<td class="label"><spring:message code="skillz.category.name" />:</td>
			<td><form:input path="name" /></td>
			<td><form:errors path="name" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="skillz.category.description" />:</td>
			<td><form:textarea path="description" /></td>
			<td><form:errors path="description" /></td>
		</tr>
		<tr>
			<td colspan="3">
				<form:hidden path="id" />
				<input type="submit" value="<spring:message code="core.ui.save" />" />
			</td>
		</tr>
	</table>
</c:form>

<spring:url value="/web/skillz/skill" var="skillurl" />
<core:if test="${!category.new}">

	<h2><spring:message code="skillz.skills" /></h2>
	<table id="skills" class="form">
		<core:forEach items="${category.skillz}" var="skill">
			<tr>
				<form:form action="${skillurl}" method="post">
					<td>
						<input type="hidden" name="skill" value="${skill.id}" />
						<input type="text" name="name" value="${skill.name}" />
					</td> 
					<td>
						<select name="category">
							<core:forEach items="${categories}" var="row">
								<core:if test="${row != category}">
									<option value="${row.id}">${row.name}</option>
								</core:if>
							</core:forEach>
						</select>
						<input type="submit" name="moveSkill" value="<spring:message code="skillz.skill.move" />" />
					</td>
				</form:form>
					<td>
						<c:deleteLink href="../skill/${skill.id}" imageUrl="/images/skillz/reference_delete.png" />
					</td>
			</tr>
		</core:forEach>
		<tr>
			<td colspan="3">
				<form:form action="${skillurl}" method="post">
					<input type="hidden" name="category" value="${category.id}" />
					<input type="text" name="name" />
					<input type="submit" name="addSkill" value="<spring:message code="core.ui.save" />" />
				</form:form>
			</td>
		</tr>
	</table>

</core:if>