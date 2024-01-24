<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="Dtos.TokenDTO" %>
<%@ page import="Utilidades.Alerta" %>
<%@ page import="Utilidades.implementacionCRUD" %>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="Dtos.AccesoDTO" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html>
<!-- Coding by CodingNepal | www.codingnepalweb.com-->
<html lang="en" dir="ltr">
  <head>
    <meta charset="UTF-8">
    <title> Login and Registration Form in HTML & CSS | CodingLab </title>
    <link rel="stylesheet" href="style.css">
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   </head>
<body>
<%
implementacionCRUD acciones = new implementacionCRUD();
String token = request.getParameter("tk");
TokenDTO tokenEncontrado=acciones.SeleccionarToken(token);

// Obtener la fecha actual
Calendar ahora = Calendar.getInstance();

// Simular una fecha anterior (puedes ajustar según tus necesidades)
Calendar fechaAnterior = tokenEncontrado.getFch_limite();

long diferenciaEnMillis = ahora.getTimeInMillis() - fechaAnterior.getTimeInMillis();
long diferenciaEnMinutos = diferenciaEnMillis / (60 * 1000); // Convertir a minutos

try{
if(tokenEncontrado.getToken().equals("")||tokenEncontrado.getToken()==null){
	Alerta.Alerta(request,"No se pudo encontrar en usuario intentelo mas tarde","error");
}
else if(diferenciaEnMinutos > 10){
	Alerta.Alerta(request,"Paso el Tiempo de verificacion","error");
}
else{
	UsuarioDTO usuario=acciones.SeleccionarUsuario(String.valueOf(tokenEncontrado.getId_usuario()));

	if(usuario.getEmailUsuario().equals("")||usuario.getEmailUsuario()==null){
		Alerta.Alerta(request,"No se pudo encontrar en usuario intentelo mas tarde","error");
	}
	else{

		AccesoDTO accesoDar=acciones.SeleccionarAcceso("3");
		usuario.setAcceso(accesoDar);
		if(acciones.ActualizarUsuario(usuario)){
			Alerta.Alerta(request,"Felicidades Has dado de alta la cuenta","success");
		}
		else{
			Alerta.Alerta(request,"Hubo Un error intentelo mas tarde","error");
		}
		}
	}
}catch(Exception e){
	Alerta.Alerta(request,"Hubo Un error intentelo mas tarde","error");
}



%>
  <div class="container">
    <input type="checkbox" id="flip">
    <div class="cover">
      <div class="front">
        <img src="personaContenta.jpg" alt="">
        <div class="text">
          <span class="text-1">Felicidades</span>
          <span class="text-2">Has dado de alta tu cuenta</span>
        </div>
      </div>
      <div class="back">
        <!--<img class="backImg" src="images/backImg.jpg" alt="">-->
        <div class="text">
          <span class="text-1">Complete miles of journey <br> with one step</span>
          <span class="text-2">Let's get started</span>
        </div>
      </div>
    </div>
    <div class="forms">
        <div class="form-content">
          <div class="login-form">
            <div class="title">Alta Confirmada</div>
          <form action="#">
            <div class="input-boxes">
              <p>Has dado de alta tu cuenta con el correo acceda a la web</p>
              <div class="text sign-up-text">Inicie Sesion en la Cuenta <label><a href="index.jsp">Iniciar Sesion</a></label></div>
            </div>
        </form> 
      </div>
    </div>
    </div>
  </div>
</body>
</html>