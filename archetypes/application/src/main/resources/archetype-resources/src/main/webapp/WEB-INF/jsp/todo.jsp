#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<spring:url value="/styles" var="stylesheets" />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><spring:message code="todos.item" /> by ${symbol_dollar}{todo.createdBy.username}</title>
    <link type="text/css" href="${symbol_dollar}{stylesheets}/style.css" rel="stylesheet" title="default" />
</head>
<body>
    <h1><spring:message code="todos.item" /> by ${symbol_dollar}{todo.createdBy.username}</h1>
    <p>${symbol_dollar}{todo.description}</p>
    <p>Written on: ${symbol_dollar}{todo.createdDate}</p>
</body>
</html>
