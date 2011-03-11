#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<spring:url value="/styles" var="stylesheets" />
<c:set var="singularMessage"><spring:message code="items.singular" /></c:set>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${symbol_dollar}{singularMessage} by ${symbol_dollar}{item.createdBy.username}</title>
    <link type="text/css" href="${symbol_dollar}{stylesheets}/style.css" rel="stylesheet" title="default" />
</head>
<body>
    <h1>${symbol_dollar}{singularMessage} by ${symbol_dollar}{item.createdBy.username}</h1>
    <p>${symbol_dollar}{item.description}</p>
    <p>Written on: ${symbol_dollar}{item.createdDate}</p>
</body>
</html>
