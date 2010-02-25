<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:form modelAttribute="matrix">
	<table class="form">
		<tr>
			<th></th>
			<c:forEach items="${levels}" var="level">
				<th class="skillzSkillLevel">${level.name}</th>
			</c:forEach>
		</tr>
		<c:forEach items="${map}" var="entry">
			<c:set var="category" value="${entry.key}" />
			<c:set var="skillEntries" value="${entry.value}" />
			<tr>
				<th colspan="${fn:length(levels) + 1}" class="skill.category">${category.name}</th>
			</tr>
			<c:forEach items="${skillEntries}" var="entry">
				<spring:nestedPath path="entries[${entry.index}]">
					<tr>
						<td>${entry.name}</td>
						<c:forEach items="${levels}" var="level">
							<td class="skillzSkillLevel<c:if test="${!entry.acknowledged}"> attention</c:if>">
								<form:radiobutton path="level" id="${level.id}" value="${level}" />
							</td>
						</c:forEach>
					</tr>
				</spring:nestedPath>
			</c:forEach>
		</c:forEach>
		<tr>
			<td colspan="${fn:length(levels) + 1}"><input type="submit" /></td>
		</tr>
	</table>
</form:form>