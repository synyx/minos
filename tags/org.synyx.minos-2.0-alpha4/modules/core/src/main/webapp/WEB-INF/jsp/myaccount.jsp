<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="umt" tagdir="/WEB-INF/tags/umt" %>

<h2><spring:message code="myaccount.title" /></h2>

<umt:userForm myaccount="true" />