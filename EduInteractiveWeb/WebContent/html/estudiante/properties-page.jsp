<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<div class="tab">
            <button class="tablinks" onclick="changePage(event, 'Preferences')" onload="changePage(event, 'Preferences')" id="defaultOpen"><fmt:message key = "preferences" bundle="${messages}"/></button>
            <button class="tablinks" onclick="changePage(event, 'Privacy')"><fmt:message key = "privacy" bundle="${messages}"/></button>
          </div>
          
          <div id="Preferences" class="tabcontent">
            <h3><fmt:message key = "preferences" bundle="${messages}"/></h3>
            <p><fmt:message key = "preferences.changeLocale" bundle="${messages}"/></p>
            <form action="<%=ControllerPaths.ESTUDIANTE%>" method="post"> 
            	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.CHANGE_LOCALE%>">
                <select name="<%=ParameterNames.LOCALE%>">
                    <option value="en"><fmt:message key = "locale.ingles" bundle="${messages}"/></option>
                    <option value="es"><fmt:message key = "locale.spanish" bundle="${messages}"/></option>
                </select>
                <button type="submit" class="aceptbtn" style="width: 100px"><fmt:message key = "confirmar" bundle="${messages}"/></button>
            </form>
          </div>
          
          <div id="Privacy" class="tabcontent">
            <h3><fmt:message key = "privacy" bundle="${messages}"/></h3>
            <form action="<%=ControllerPaths.ESTUDIANTE%>" method="post">
            	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.CHANGE_PSSWD%>">
                <label for="<%=ParameterNames.PASSWORD%>"><fmt:message key = "privacy.currentPassword" bundle="${messages}"/></label>
                <input type="password" name="<%=ParameterNames.PASSWORD%>"><br><br>
                <label for="<%=ParameterNames.NEW_PASSWD%>"><fmt:message key = "privacy.newPassword" bundle="${messages}"/></label>
                <input type="password" name="<%=ParameterNames.NEW_PASSWD%>"><br><br>
                <label for="<%=ParameterNames.PSSWD_REPEAT%>"><fmt:message key = "privacy.repeatNewPassword" bundle="${messages}"/></label>
                <input type="password" name="<%=ParameterNames.PSSWD_REPEAT%>">
                <button type="submit" class="aceptbtn" style="width: 100px"><fmt:message key = "privacy.cambiar" bundle="${messages}"/></button>
            </form>
            
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>