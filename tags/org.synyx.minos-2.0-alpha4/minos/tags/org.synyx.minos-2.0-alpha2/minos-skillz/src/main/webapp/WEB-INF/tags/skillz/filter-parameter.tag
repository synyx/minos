<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="filterParam" type="org.synyx.minos.skillz.domain.resume.ResumeFilterParameter" %>

<spring:message code="${filterParam.messageKey}" />: <input type="text" name="${filterParam.name}" value="${param[filterParam.name]}" /><br />