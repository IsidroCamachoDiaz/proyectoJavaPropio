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
<%@ page import="Servicios.ImplentacionIntereaccionUsuario" %>
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
	<!-- Lógica de JavaScript para mostrar la alerta -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
<script>
//Obtiene los atributos desde la sesión
var mensaje = '<%= session.getAttribute("mensajeAlerta") %>';
var tipo = '<%= session.getAttribute("tipoAlerta") %>';
    document.addEventListener('DOMContentLoaded', function () {
        // Lógica para mostrar la alerta con SweetAlert2
        if (mensaje !== null && tipo !== null && mensaje !== 'null' && tipo !== 'null') {
        	console.log('Mensaje:', mensaje, 'Tipo:', tipo);
            Swal.fire({
                icon: tipo,
                title: mensaje,
                confirmButtonText: 'OK'
            });
        }
    });
</script>
<%
//Declaramos lo que necesitemos
implementacionCRUD acciones = new implementacionCRUD();
//Cogemos de la url el token
String token = request.getParameter("tk");
//Buscamos el token
TokenDTO tokenEncontrado=acciones.SeleccionarToken(token);

// Obtener la fecha actual
Calendar ahora = Calendar.getInstance();

// Simular una fecha anterior (puedes ajustar según tus necesidades)
Calendar fechaAnterior = tokenEncontrado.getFch_limite();
fechaAnterior.add(Calendar.HOUR_OF_DAY, 1);


try{
	//Comprobamos si se encontro el token
if(tokenEncontrado.getToken().equals("")||tokenEncontrado.getToken()==null){
	Alerta.Alerta(request,"No se pudo encontrar en usuario intentelo mas tarde","error");
}
	//Comprobamos si se paso de la fecha
else if(ahora.after(fechaAnterior)){
	ImplentacionIntereaccionUsuario inter = new ImplentacionIntereaccionUsuario();
	//Buscamos al usuario y lo eliminamos
	UsuarioDTO usuario=acciones.SeleccionarUsuario("Select/"+String.valueOf(tokenEncontrado.getId_usuario().getIdUsuario()));
	inter.eliminarUsuario(usuario, request);
}
else{
	//Buscamos el usuario por el id del token
	UsuarioDTO usuario=acciones.SeleccionarUsuario("Select/"+String.valueOf(tokenEncontrado.getId_usuario().getIdUsuario()));
	//Comprobamos si lo encontri
	if(usuario.getEmailUsuario().equals("")||usuario.getEmailUsuario()==null){
		Alerta.Alerta(request,"No se pudo encontrar en usuario intentelo mas tarde","error");
	}
	//Su lo encuentra se le da el alta
	else{
		usuario.setAlta(true);
		//Comprobamos si se actualizo correctamente
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