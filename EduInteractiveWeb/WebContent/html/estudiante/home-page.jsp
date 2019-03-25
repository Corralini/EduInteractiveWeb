<%@ page import="java.util.List" %>
<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>

<%
	List<Profesor> profesores = (List<Profesor>) request.getAttribute(AttributeNames.PROFESOR);
	List<Sesion> sesiones = (List<Sesion>) request.getAttribute(AttributeNames.SESIONES);
%>

<div id="profesoresRecomendados">
        <%	
    	valores.clear();
    		for(Profesor p: profesores) { 
    			
        		valores.put(ParameterNames.ACTION, Actions.DETALLE_PROFESOR);
    	    	valores.put(ParameterNames.ID_PROFESOR, p.getIdProfesor().toString());
    	%>
         <div class="resultadoRecomendado">
            <a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><%=ParameterUtils.makeName(p.getNombre(), p.getApellido1(), p.getApellido2()) %></a>
            <p class="puntuacion"><%=p.getPuntuacion() %>/10</p>
            <p class="precio"><%=p.getPrecioSesion()%></p>
            <%
            	valores.put(ParameterNames.ACTION, Actions.BUSCAR_HORARIOS);
            %>
            <a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><button id="dispo" class="aceptbtn">Disponibilidad</button></a>
        </div>
        <%
        } 
        %>
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>