<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope['user-locale']}"/>
<fmt:setBundle basename = "resources.Messages" var = "messages" scope="session"/> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error 400</title>
</head>
<body>
<h1><fmt:message key="error400" bundle="${messages}" /></h1>
</body>
</html>