<%@ page import="com.eduinteractive.web.utils.*, com.educorp.eduinteractive.ecommerce.model.*, com.eduinteractive.web.controller.*" %>
<%@ page import="java.util.HashMap, java.util.Map" %>
<%@ page import="java.util.List" %>
    <%
    	Profesor p = (Profesor) request.getSession().getAttribute(SessionAttributeNames.USUARIO);
        Map<String, String> valores = new HashMap<String, String>();
        valores.put(ParameterNames.ACTION, Actions.DETALLE_PROFESOR);
    %>
<div class="navbar">
        <div class="dropdown">
            <button class="dropbtn"><a  href="<%=ParameterUtils.URLBuilder(ControllerPaths.PROFESOR, valores)%>"><%=p.getNombre()%></a></button>
            <div class="dropdown-content">
                <a href="<%=request.getContextPath().concat(ViewPaths.SETTINGS_PROFESOR)%>" class="dropOption"><fmt:message key = "ajustes" bundle="${messages}"/></a>
                <a href="<%=request.getContextPath()%>/profesor?action=logout" class="dropOption"><fmt:message key="menu.cerrarSesion" bundle="${messages}"/></a>
            </div>
        </div>
        <a class="active" href="<%=request.getContextPath() + ViewPaths.PRE_HOME_PROFESOR %>" id="home"><img src="<%=request.getContextPath()%>/img/home.png" alt="home"></img><br><fmt:message key="menu.inicio" bundle="${messages}"/></a>
        <a href="<%=request.getContextPath() + ViewPaths.PRE_HOME_PROFESOR %>" class="izquierda" id="logo"><img src="<%=request.getContextPath()%>/img/logo.png" alt="logo"></img></a>
    </div>