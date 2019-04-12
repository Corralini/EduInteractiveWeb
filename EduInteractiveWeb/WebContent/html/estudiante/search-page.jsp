<%@ page import="java.util.List"%>
<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<%
	List<Profesor> profesores = (List<Profesor>) request.getAttribute(AttributeNames.RESULTADOS);
	List<NivelIngles> niveles = (List<NivelIngles>) request.getAttribute(AttributeNames.NIVELES);
	List<Dia> dias = (List<Dia>) request.getAttribute(AttributeNames.DIAS);
	Integer nivelIngles = (Integer) request.getAttribute(AttributeNames.NIVEL_INGLES_INPUT);
	String genero = (String) request.getAttribute(AttributeNames.GENERO);
	Double puntuacion = (Double) request.getAttribute(AttributeNames.PUNTUACION);
	Double precioMin = (Double) request.getAttribute(AttributeNames.PRECIO);
	Double precioMax = (Double) request.getAttribute(AttributeNames.PRECIO_MAX);
	Integer idDia = (Integer) request.getAttribute(AttributeNames.DIA);
%>
<div id="criteria">
	<form class="Criterios" action="<%=ControllerPaths.ESTUDIANTE%>">
		<input type="hidden" name="<%=ParameterNames.ACTION%>"
			value="<%=Actions.SEARCH%>"> <label
			for="<%=ParameterNames.NIVEL_INGLES%>"><b>Nivel</b></label> <select
			name="<%=ParameterNames.NIVEL_INGLES%>">
			<option value=" ">Cualquiera</option>
			<%
				for (NivelIngles i : niveles) {
					if(nivelIngles != null && i.getIdNivel().equals(nivelIngles)){
						%>
							<option value="<%=i.getIdNivel()%>" selected><%=i.getNivel()%></option>
						<%					
					}else{
						%>
							<option value="<%=i.getIdNivel()%>"><%=i.getNivel()%></option>
						<%
					}
				}
				%>
		</select> <label for="<%=ParameterNames.GENERO%>"><b>Genero</b></label> <select
			name="<%=ParameterNames.GENERO%>">
			<option value=" ">Cualquiera</option>
			<%
				if("H".equalsIgnoreCase(genero)){
			%>
			<option value="H" selected>Hombre</option>
			<%
				}else{
					%>
					<option value="H">Hombre</option>
					<%
				}
			%>
			<%
				if("M".equalsIgnoreCase(genero)){
			%>
					<option value="M" selected>Mujer</option>
			<%
				}else{
					%>
					<option value="M">Mujer</option>
					<%
				}
			%>
			<%
				if("O".equalsIgnoreCase(genero)){
			%>
					<option value="O" selected>Otro</option>
			<%
				}else{
					%>
					<option value="O">Otro</option>
					<%
				}
			%>
			
			
		</select>
		<label for="<%=ParameterNames.PUNTUACION%>"><b>Puntuación</b></label>
		<input type="text" min="0" max="10"
			name="<%=ParameterNames.PUNTUACION%>" value="<% if(puntuacion != null){%><%=puntuacion%><%}%>"> <label
			for="<%=ParameterNames.PRECIO%>"><b>Precio Mínimo</b></label> <input
			type="number" name="<%=ParameterNames.PRECIO%>" value="<% if(precioMin != null){%><%=precioMin%><%}%>"><br> <label
			for="<%=ParameterNames.PRECIO_MAX%>"><b>Precio Máximo</b></label> <input
			type="number" name="<%=ParameterNames.PRECIO_MAX%>" value="<% if(precioMax != null){%><%=precioMax%><%}%>"> <label
			for="<%=ParameterNames.DIA%>"><b>Dia</b></label> <select
			name="<%=ParameterNames.DIA%>">
			<option value=" ">Cualquiera</option>
			<%
				for (Dia d : dias) {
					if(idDia != null && d.getIdDia().equals(idDia)){
			%>
						<option value="<%=d.getIdDia()%>" selected><%=d.getDia()%></option>
			<%
					}else{
			%>
						<option value="<%=d.getIdDia()%>"><%=d.getDia()%></option>
			<%
					}
				}
			%>
		</select>
		<button id="submitCriteria" type="submit" class="aceptbtn">Buscar</button>
	</form>
	<hr id="separadorCriteria">
</div>

<!-- Total de resultados  -->
<p>
	<c:if test="${not empty total}">
		<fmt:message key="Encontrados" bundle="${messages}">
			<fmt:param value="${total}"></fmt:param>
		</fmt:message>
	</c:if>
</p>

<!--Mostrar resultado-->
<%
	if (profesores != null && !profesores.isEmpty()) {
%>
<div id="resultados">
	<%
		valores.clear();
			for (Profesor p : profesores) {

				valores.put(ParameterNames.ACTION, Actions.DETALLE_PROFESOR);
				valores.put(ParameterNames.ID_PROFESOR, p.getIdProfesor().toString());
	%>
	<div class="resultado">
		<a
			href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><%=ParameterUtils.makeName(p.getNombre(), p.getApellido1(), p.getApellido2())%></a>
		<p class="puntuacion"><%=p.getPuntuacion()%>/10
		</p>
		<p class="precio"><%=p.getPrecioSesion()%></p>
		<%
			valores.put(ParameterNames.ACTION, Actions.BUSCAR_HORARIOS);
		%>
		<a
			href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><button
				id="dispo" class="aceptbtn">Disponibilidad</button></a>
	</div>
	<%
		}
	%>
		<div id="paginacion">

		<c:url var="urlBase" value="/estudiante" scope="page">
			<c:param name="action" value="search" />
			<c:param name="search_box" value="${search_box}" />
			<c:param name="nivelIngles" value="${nivelIngles}" />
			<c:param name="genero" value="${genero}" />
			<c:param name="puntuacion" value="${puntuacion}" />
			<c:param name="precio" value="${precio}" />
			<c:param name="precio_max" value="${precio_max}" />
			<c:param name="dia" value="${dia}" />
		</c:url>

		<c:if test="${page > 1}">
			<a href="${urlBase}&page=1"> <fmt:message
					key="paginacion.primera" bundle="${messages}" />
			</a>
			&nbsp;&nbsp;
		</c:if>

		<c:if test="${page > 1}">
			<a href="${urlBase}&page=${page - 1}"> <fmt:message
					key="paginacion.anterior" bundle="${messages}" />
			</a>
		&nbsp;&nbsp;
		</c:if>

		<c:if test="${totalPages > 1}">

			<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
				<c:choose>
					<c:when test="${page != i}">
					&nbsp;<a href="${urlBase}&page=${i}"><b>${i}</b></a>&nbsp;
			  </c:when>
					<c:otherwise>
					&nbsp;<b>${i}</b>&nbsp;
			  </c:otherwise>
				</c:choose>
			</c:forEach>

		</c:if>

		<c:if test="${page < totalPages}">
		&nbsp;&nbsp;		
			<a href="${urlBase}&page=${page + 1}"> <fmt:message
					key="paginacion.siguiente" bundle="${messages}" />
			</a>
		</c:if>

		<c:if test="${page != totalPages}">
		&nbsp;&nbsp;<a href="${urlBase}&page=${totalPages}"><fmt:message
					key="paginacion.ultima" bundle="${messages}" /></a>
		</c:if>

	</div>
		<%
			} else {
	%>
	<p>No se encontraron resultados</p>
	<%
		}
	%>
</div>



<%@ include file="/html/estudiante/common/footer.jsp"%>