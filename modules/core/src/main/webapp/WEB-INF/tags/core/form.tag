<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="id" required="false" description="The id to be set for the form. If not set the name of the model attribute will be used." %>
<%@ attribute name="action" required="true" description="The URL of the collection resource to POST to for creation. Default resource URL is then \$\{collectionUrl}/{id}." %>
<%@ attribute name="resourceAction" required="false" description="A custom URL template to update existing entities with PUT" %>
<%@ attribute name="modelAttribute" required="true" description="The model attribute to create the form for. Has to have a 'new' property as well as an 'id'." %>

<%--

Tag file to automatically create correct HTML form according to the model attribute state.

* New form objects will be POSTed to the collection URL determined through 'action' attribute
* Existing form objects will be PUT to either assuming a default of just the 'id' attribute of 
  the model object appended or explicitly defined through setting 'resourceAction'. 

--%>

<c:set var="modelObject" value="${requestScope[modelAttribute]}" />
<c:set var="method" value="${modelObject['new'] ? 'POST' : 'PUT'}" />
<c:set var="formId" value="${empty id ? modelAttribute : id}" />

<%-- Determine URL template to use to find the URL to point to --%>
<c:choose>
	<c:when test="${empty resourceAction}">
		<c:set var="resourceUrlTemplate">${action}/{id}</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="resourceUrlTemplate">${resourceAction}</c:set>
	</c:otherwise>
</c:choose>

<%-- Determine URL to point to --%>
<c:choose>
	<c:when test="${modelObject['new']}">
		<c:set var="formAction"><spring:url value="${action}" /></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="formAction">
			<spring:url value="${resourceUrlTemplate}">
                <c:if test="${fn:contains(resourceUrlTemplate, '{id}')}">
                    <spring:param name="id" value="${modelObject.id}" />
                </c:if>
			</spring:url>
		</c:set>
	</c:otherwise>
</c:choose>

<form:form id="${formId}" method="${method}" action="${formAction}" modelAttribute="${modelAttribute}">
	<c:if test="${not empty _conversationId}">
		<input type="hidden" name="_conversationId" value="${_conversationId}" />
	</c:if>
	<jsp:doBody />	
</form:form>
