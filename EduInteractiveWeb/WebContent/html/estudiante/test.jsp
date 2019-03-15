<%@ page import="java.util.List" %>
<%@ page import="com.educorp.eduinteractive.ecommerce.model.*"%>
<%@ page import="com.eduinteractive.web.controller.*" %>
<%@ page import="com.eduinteractive.web.utils.*" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>EduInteractive</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="<%=request.getContextPath()%>/img/logo.ico">
    <script src="<%=request.getContextPath()%>/js/main.js"></script>
</head>
<body>
    <form class="modal-content" action="<%=ControllerPaths.ESTUDIANTE%>" method="post">
    	<input type="hidden" name = "<%=ParameterNames.ACTION%>" value = "<%=Actions.SIGNIN%>">
        <label class="test" for="<%=ParameterNames.PREGUNTA_1%>">1.  ..... Caviar in the fridge.</label> <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_1%>" value="<%=ParameterNames.OPCION_A%>">A. There isn't no <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_1%>" value="<%=ParameterNames.OPCION_B%>">B. There is any <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_1%>" value="<%=ParameterNames.OPCION_C%>">C. There isn't any  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_1%>" value="<%=ParameterNames.OPCION_D%>">D. There aren't no  <br>
        <hr>
        <label for="<%=ParameterNames.PREGUNTA_2%>">2. </label>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_2%>" value="<%=ParameterNames.OPCION_A%>">A. What like his brother?   <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_2%>" value="<%=ParameterNames.OPCION_B%>">B. How his brother is?  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_2%>" value="<%=ParameterNames.OPCION_C%>">C. How's his brother?   <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_2%>" value="<%=ParameterNames.OPCION_D%>">D. What's his brother like? <br>
        <hr>
        <label for="<%=ParameterNames.PREGUNTA_3%>">3. He goes to work ... </label> <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_3%>" value="<%=ParameterNames.OPCION_A%>">A. by taxi. <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_3%>" value="<%=ParameterNames.OPCION_B%>">B. on taxi. <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_3%>" value="<%=ParameterNames.OPCION_C%>">C. with taxi.   <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_3%>" value="<%=ParameterNames.OPCION_D%>">D. in taxi. <br>
        <hr>
        <label for="<%=ParameterNames.PREGUNTA_4%>">4. There ..... hooligans at the match, for a change.</label> <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_4%>" value="<%=ParameterNames.OPCION_A%>">A. were no  <br>    
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_4%>" value="<%=ParameterNames.OPCION_B%>">B. weren't no   <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_4%>" value="<%=ParameterNames.OPCION_C%>">C. were any <br>   
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_4%>" value="<%=ParameterNames.OPCION_D%>">D. were not <br>
        <hr>
        <label for="<%=ParameterNames.PREGUNTA_5%>">5. Have you phoned the restaurant about the booking? Yes, I've ..... done that.</label> <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_5%>" value="<%=ParameterNames.OPCION_A%>">A. still <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_5%>" value="<%=ParameterNames.OPCION_B%>">B. already  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_5%>" value="<%=ParameterNames.OPCION_C%>">C. yet  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_5%>" value="<%=ParameterNames.OPCION_D%>">D. now  <br>
        <hr>
        <label for="<%=ParameterNames.PREGUNTA_6%>">6. Peter is ..... Jane to do it at this very moment.</label> <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_6%>" value="<%=ParameterNames.OPCION_A%>">A. telling  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_6%>" value="<%=ParameterNames.OPCION_B%>">B. saying   <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_6%>" value="<%=ParameterNames.OPCION_C%>">C. saying to    <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_6%>" value="<%=ParameterNames.OPCION_D%>">D. telling to   <br>
        <hr>
        <label for="<%=ParameterNames.PREGUNTA_7%>">7. That's the woman ..... I saw stealing the handbag!</label> <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_7%>" value="<%=ParameterNames.OPCION_A%>">A. whom <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_7%>" value="<%=ParameterNames.OPCION_B%>">B.  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_7%>" value="<%=ParameterNames.OPCION_C%>">C. what <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_7%>" value="<%=ParameterNames.OPCION_D%>">D. whose    <br>
        <hr>
        <label for="<%=ParameterNames.PREGUNTA_8%>">8. 'Those cases look heavy' "..... carry one for you?" "That's very nice of you"
        </label> <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_8%>" value="<%=ParameterNames.OPCION_A%>">A. Will I   <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_8%>" value="<%=ParameterNames.OPCION_B%>">B. Do I have    <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_8%>" value="<%=ParameterNames.OPCION_C%>">C. Shall I  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_8%>" value="<%=ParameterNames.OPCION_D%>">D. Do I <br>
        <hr>
        <label for="<%=ParameterNames.PREGUNTA_9%>">9. </label> <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_9%>" value="<%=ParameterNames.OPCION_A%>">A. The brown fox quick jumped over the dog lazy.    <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_9%>" value="<%=ParameterNames.OPCION_B%>">B. The brown quick fox jumped over the lazy dog.    <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_9%>" value="<%=ParameterNames.OPCION_C%>">C. The fox quick and brown jumped over the lazy dog.    <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_9%>" value="<%=ParameterNames.OPCION_D%>">D. The quick brown fox jumped over the lazy dog.    <br>
        <hr>
        <label for="<%=ParameterNames.PREGUNTA_10%>">10. He wrote the programme ...... , he didn't need anybody's help.</label> <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_10%>" value="<%=ParameterNames.OPCION_A%>">A. by his own  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_10%>" value="<%=ParameterNames.OPCION_B%>">B. on his own  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_10%>" value="<%=ParameterNames.OPCION_C%>">C. on himself  <br>
        <input class="test" type="radio" name="<%=ParameterNames.PREGUNTA_10%>" value="<%=ParameterNames.OPCION_D%>">D. by his ownership    <br>
        <button type="submit">Registrarse</button>
    </form>
</body>
</html>