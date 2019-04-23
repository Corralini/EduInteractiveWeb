<%@ include file="/html/profesor/common/head.jsp"%>
<%
    	Profesor p = (Profesor) request.getSession().getAttribute(SessionAttributeNames.USUARIO);
    %>
<div class="navbar">
        <div class="dropdown">
            <button class="dropbtn"><%=p.getNombre()%></button>
            <div class="dropdown-content">
                <a href="<%=request.getContextPath()%>/profesor?action=logout" class="dropOption">Cerrar sesión</a>
            </div>
        </div>
        <a href="#" class="izquierda" id="logo"><img src="<%=request.getContextPath()%>/img/logo.png" alt="logo"></img></a>
</div>

<p>Su cuenta aún no ha sido activada</p>