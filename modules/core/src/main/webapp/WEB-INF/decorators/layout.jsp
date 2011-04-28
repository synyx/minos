<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="minos" uri="http://www.synyx.org/minos/tags" %>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script type="text/javascript" src="<c:url value="/js/minos-core.js" />"> </script>
	<title><decorator:title default="Minos 2" /></title>
	<minos:styles />
	<decorator:head />
</head>
<body>
	<div id="header">
		<h1><decorator:title default="Minos 2" /></h1>
		<minos:menu menuId="MAIN" />
	</div>
	<div id="middle">
		<minos:system-message />
		<decorator:body />
	</div>
	<div id="footer"></div>
</body>
</html>
