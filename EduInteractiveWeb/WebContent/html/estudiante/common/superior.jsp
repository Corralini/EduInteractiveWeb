<%@ page import="com.eduinteractive.web.utils.*, com.educorp.eduinteractive.ecommerce.model.*, com.eduinteractive.web.controller.*" %>
<%@ page import="java.util.HashMap, java.util.Map" %>
<%@ page import="java.util.List" %>
    <%
    	Estudiante e = (Estudiante) request.getSession().getAttribute(SessionAttributeNames.USUARIO);
        Map<String, String> valores = new HashMap<String, String>();
        valores.put(ParameterNames.ACTION, Actions.DETALLE_ESTUDIANTE);
    %>
<div class="navbar">
        <div class="dropdown">
            <button class="dropbtn"><a  href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><%=e.getNombre()%></a></button>
            <div class="dropdown-content">
                <a href="<%=request.getContextPath().concat(ViewPaths.PROPERTIES_ESTUDIANTE)%>" class="dropOption"><fmt:message key = "ajustes" bundle="${messages}"/></a>
                <a href="<%=request.getContextPath()%>/estudiante?action=logout" class="dropOption"><fmt:message key = "menu.cerrarSesion" bundle="${messages}"/></a>
            </div>
        </div>
        <a href="<%=ControllerPaths.SEARCH_PROFESOR_NAVBAR %>" id="profesores" onclick="getFocus('profesores')"><img src="<%=request.getContextPath()%>/img/images.png" alt="profesores"></img><br> <fmt:message key = "menu.profesores" bundle="${messages}"/></a>
        <a class="active" href="<%=request.getContextPath() + ViewPaths.PRE_HOME_ESTUDIANTE %>" id="home" onclick="getFocus('home')"><img src="<%=request.getContextPath()%>/img/home.png" alt="home"></img><br><fmt:message key = "menu.inicio" bundle="${messages}"/></a>
        <a href="<%=request.getContextPath() + ViewPaths.PRE_HOME_ESTUDIANTE %>" class="izquierda" id="logo"><img src="<%=request.getContextPath()%>/img/logo.png" alt="logo"></img></a>
        <form class="example" action="<%=ControllerPaths.ESTUDIANTE %>" style="margin:auto;max-width:300px">
        	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SEARCH%>">
            <input type="text" placeholder="<fmt:message key = "menu.search" bundle="${messages}"/>" name="<%=ParameterNames.SEARCH_BOX%>">
            <button id="searchButton" type="submit"><img src="<%=request.getContextPath()%>/img/searchIcon.png" alt="buscar"></img></button>
        </form>
    </div>