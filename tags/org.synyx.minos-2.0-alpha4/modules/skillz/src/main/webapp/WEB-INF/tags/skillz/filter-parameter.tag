<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@tag import="java.util.Arrays"%>
<%@tag import="org.synyx.hades.domain.Persistable"%>
<%@tag import="org.synyx.minos.skillz.domain.resume.SingleChoiceParameter"%>
<%@tag import="org.synyx.minos.skillz.domain.resume.MultipleChoiceParameter"%>

<%@ attribute name="filterParam" type="org.synyx.minos.skillz.domain.resume.ResumeFilterParameter" %>
<%@ attribute name="index" type="java.lang.Integer" %>

<%!

/**
 * Helper method for checking if the request parameter array contains a
 * filter parameter from the forEach loop.
 */
@SuppressWarnings("unchecked")
private boolean containsParameter(HttpServletRequest request, JspContext jspContext) {
    String[] filter = request.getParameterValues(filterParam.getName()) ;
    if (filter == null) {
        return false;
    }
    String item = String.valueOf(((Persistable<Long>) jspContext.getAttribute("item")).getId());
    return Arrays.asList(filter).contains(item);
}
 
%>

<spring:message code="${filterParam.messageKey}" />:
<core:choose>
	<core:when test="<%= filterParam instanceof SingleChoiceParameter %>">
		<select name="${filterParam.name}">
			<core:forEach items="${filterParam.referenceData}" var="item">
				<option value="${item.id}"<core:if test="${param[filterParam.name] eq item.id}"> selected="selected"</core:if>>
					${item}
				</option>
			</core:forEach>
		</select>
	</core:when>
	<core:when test="<%= filterParam instanceof MultipleChoiceParameter %>">
		<core:forEach items="${filterParam.referenceData}" var="item">
			<input type="checkbox" name="${filterParam.name}" value="${item.id}" <core:if test="<%= containsParameter(request, jspContext) %>">checked="checked" </core:if>/>
			${item}
		</core:forEach>
	</core:when>
	<core:otherwise>
		<input type="text" name="${filterParam.name}" value="${param[filterParam.name]}" />
	</core:otherwise>
</core:choose>
<br />