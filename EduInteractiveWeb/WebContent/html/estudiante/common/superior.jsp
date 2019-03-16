<%@ page import="com.eduinteractive.web.utils.*, com.educorp.eduinteractive.ecommerce.model.*, com.eduinteractive.web.controller.*" %>
    <%
    Estudiante e = (Estudiante) request.getSession().getAttribute(SessionAttributeNames.ESTUDIANTE);
    %>
<div class="navbar">
        <div class="dropdown">
            <button class="dropbtn"><%=e.getNombre()%>
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="dropdown-content">
                <a href="#" class="dropOption">Ajustes</a>
                <a href="<%=request.getContextPath() %>/estudiante?action=logout" class="dropOption">Cerrar sesion</a>
            </div>
        </div>
        <a href="#" id="profesores" onclick="getFocus('profesores')"><img src="<%=request.getContextPath()%>/img/images.png" alt="profesores"></img><br> Profesores</a>
        <a class="active" href="#" id="home" onclick="getFocus('home')"><img src="<%=request.getContextPath()%>/img/home.png" alt="home"></img><br>inicio</a>
        <a href="#" class="izquierda" id="logo"><img src="<%=request.getContextPath()%>/img/logo.png" alt="logo"></img></a>
        <form class="example" action="#" style="margin:auto;max-width:300px">
            <input type="text" placeholder="buscar.." name="search2">
            <button id="searchButton" type="submit"><img src="<%=request.getContextPath()%>/img/searchIcon.png" alt="buscar"></img></button>
        </form>
    </div>