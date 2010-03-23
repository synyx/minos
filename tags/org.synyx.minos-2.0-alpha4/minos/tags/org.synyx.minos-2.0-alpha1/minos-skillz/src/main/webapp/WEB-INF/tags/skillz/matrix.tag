<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="matrix" type="org.synyx.minos.skillz.domain.SkillMatrix" %>
<%@ attribute name="levels" type="java.util.Collection" %>


<table class="form">
	<tr>
		<th></th>
		<core:forEach items="${levels}" var="level">
			<th class="skillzSkillLevel">${level.name}</th>
		</core:forEach>
	</tr>
	<core:forEach items="${matrix.map}" var="entry">
	
		<core:set var="category" value="${entry.key}" />
		<core:set var="entries" value="${entry.value}" />
	
		<tr>
			<th colspan="${fn:length(levels) + 1}" class="skillzCategory">${category.name}</th>
		</tr>
		
		<core:forEach items="${entries}" var="entry">
			<tr>
				<td class="skillzSkill">${entry.name}</td>
				<core:forEach items="${levels}" var="level">
					<td class="skillzSkillLevel"><core:if test="${level eq entry.level}">X</core:if></td>
				</core:forEach>
			</tr>
		</core:forEach>
	</core:forEach>	
	<tfoot>
		<tr>
			<td></td>
			<td colspan="${fn:length(levels)}">
				<a href="matrix/form"><spring:message code="skillz.skillMatrix.edit" /></a>
			</td>
		</tr>
	</tfoot>
</table>