<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isErrorPage="true" import="org.apache.log4j.Logger"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@page import="java.io.Writer"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.PrintWriter"%>

<html>
<head>
	<title><spring:message code="core.ui.error" /></title>
</head>
<body>
	<h1><spring:message code="core.ui.error" /></h1>

	<p>
		<c:choose> 
			<c:when test="${not empty exception_id}">
				<spring:message code="core.ui.error.code" arguments="${exception_id}"/>
			</c:when>
			<c:otherwise>
				<spring:message code="core.ui.error.totallyunexpected" /> 
			</c:otherwise>
		</c:choose>
			<br /><br />
	</p>
	
	<security:authorize ifAllGranted="ROLE_ADMIN">
	    <div id="internal_server_error_details" style="background:#fee; border: 1px solid #900; padding:10px;">
        <h3>${exception}</h3>

        <p>
	        <pre style="font-size:9px;">
		        <%
		
		        Exception e = (Exception) pageContext.findAttribute("exception");
		
		        if ( e!= null) {
		
		        PrintWriter pw = new PrintWriter(out);
		        e.printStackTrace(pw);
		        pw.flush();
		
		        } else {
		            pageContext.getOut().write("nix exception");
		        }
		
		
		        %>
	        </pre>
        </p>
    	</div>
    </security:authorize>

</body>
</html>
