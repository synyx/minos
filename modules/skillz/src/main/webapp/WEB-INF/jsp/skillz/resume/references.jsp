<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://www.joda.org/joda/time/tags" %>

<%@ taglib prefix="c" tagdir="/WEB-INF/tags/core" %>

<script type="text/javascript">
<!--
$(function() {
	$(".projectDescription > p.skillzProjectName").click(function() {
		$(this).siblings("p.skillzProjectDescription").toggle("fast");
	});
});
//-->
</script>

<table class="form" id="reference">
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
				<p style="display: none;" class="skillzProjectDescription">${reference.project.description}</p>
			</core:if> <core:if test="${not empty reference.additionalDescription}">
				<p style="display: none;" class="skillzProjectDescription">${reference.additionalDescription}</p>
			</core:if> <core:if test="${not empty reference.responsibilities}">
				<p style="display: none;" class="skillzProjectName"><spring:message
					code="skillz.responsibilities" />:</p>
				<ul style="display: none;">
					<core:forEach items="${reference.responsibilities}"
						var="responsibility">
						<li>${responsibility.name}</li>
					</core:forEach>
				</ul>
			</core:if>
			</td>
			<td class="actions">
				<c:imageLink href="resume/references/${reference.id}" imageUrl="/images/skillz/reference_edit.png" altKey="skillz.reference.edit" />
				<c:deleteLink href="resume/references/${reference.id}" imageUrl="/images/skillz/reference_delete.png" />
			</td>
		</tr>
	</core:forEach>
	<tfoot>
		<tr>
			<td colspan="3">
			<c:imageLink href="resume/references/form" imageUrl="/images/skillz/reference_add.png" altKey="skillz.reference.new" />
		</tr>
	</tfoot>
</table>