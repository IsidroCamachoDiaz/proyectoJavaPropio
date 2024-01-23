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
String token = request.getParameter("tk");
TokenDTO tokenEncontrado;



try {
	//Se le pasa la url
		URL url = new URL("http://localhost:8080/usuarioApi/token/"+token);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        
        //Se le indica el metodo
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        
        //Comprobamos si esta correcto la url
		
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			//Creamos el lectpr
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String linea;
            StringBuilder response2 = new StringBuilder();
            
            // Crear un ObjectMapper (Jackson)
            ObjectMapper objectMapper = new ObjectMapper();
            
            //Pasamos el json
            linea = reader.readLine();
            reader.close();          
            if(linea.isEmpty())
            	Alerta.Alerta(request,"No se pudo verificar","error");
            
            	// Convertir el JSON a un objeto MiObjeto
            System.out.println("JSON recibido: " + linea);
            //Lo convertimos a DTO
            
                tokenEncontrado=objectMapper.readValue(linea, TokenDTO.class);

            
        } else {
            System.out.println("La solicitud GET no fue exitosa. Código de respuesta: " + connection);
        }
}catch(Exception e) {
	System.out.println(e.getLocalizedMessage());
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