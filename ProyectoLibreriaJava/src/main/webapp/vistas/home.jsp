<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="Utilidades.Encriptado" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>

<!DOCTYPE html>
<html lang="en">
   <head>
      <!-- basic -->
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <!-- mobile metas -->
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="viewport" content="initial-scale=1, maximum-scale=1">
      <!-- site metas -->
      <title>Gestor Biblioteca</title>
      <meta name="keywords" content="">
      <meta name="description" content="">
      <meta name="author" content="">
      <!-- bootstrap css -->
      
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

      <!--<link rel="stylesheet" href="css/bootstrap.min.css">-->
      <!-- style css -->
      <link rel="stylesheet" href="common/css/home.css">
      <!-- Responsive-->
      <link rel="stylesheet" href="common/css/responsive.css">
      <!-- fevicon -->
      <link rel="icon" href="common/img/fevicon.png" type="image/gif" />
      <!-- Scrollbar Custom CSS -->
      <link rel="stylesheet" href="common/css/jquery.mCustomScrollbar.min.css">
      <!-- Tweaks for older IEs-->
      <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
      <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
   </head>
   <!-- body -->
   <%

   String nombreUser="";
   String codigoAcceso="";
   try{
	   Encriptado nc = new Encriptado();
	   UsuarioDTO user = new UsuarioDTO();	
	   String dni = request.getParameter("dni");	 
   URL url = new URL("http://localhost:8080/usuarioApi/usuarioSelect/usuarioDni/"+dni);
   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
   System.out.println(url);
   //Se le indica el metodo
   connection.setRequestMethod("GET");
   connection.setRequestProperty("Content-Type", "application/json");
   connection.setDoOutput(true);
   
   UsuarioDTO usuarioBD;
   
   //Comprobamos si esta correcto la url
	
		//Creamos el lector
       BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       String linea;
       
       // Crear un ObjectMapper (Jackson)
       ObjectMapper objectMapper = new ObjectMapper();
       
       //Pasamos el json
       linea = reader.readLine();
       reader.close();          
       
       	// Convertir el JSON a un objeto MiObjeto
       System.out.println("---------------------------------------JSON recibido: " + linea);
       //Lo convertimos a DTO
           usuarioBD=objectMapper.readValue(linea, UsuarioDTO.class);
       nombreUser=usuarioBD.getNombreUsuario()+" "+usuarioBD.getApellidosUsuario();
       codigoAcceso=usuarioBD.getAcceso().getCodigoAcceso();
	}catch(Exception e) {
System.out.println(e.getLocalizedMessage());
}
   %>
   <body class="main-layout">
      <!-- Pantalla de carga  -->
      <div class="loader_bg">
         <div class="loader"><img src="common/img/loading.gif" alt="#" /></div>
      </div>
      <!-- Pantalla de navegacion aqui se pone las acciones que puede
      hacer el usuario -->
      <div id="mySidepanel" class="sidepanel">
         <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
         <a href="home.jsp">Home </a>
         <a href="#about">Sobre Nosotros</a>
         <a href="#service">Administracion</a>
      </div>
      <!-- header -->
      <header>
         <div class="header">
            <div class="container-fluid">
               <div class="row">
                  <div class="col-md-4 col-sm-4">
                     <div class="logo">
                        <!--Aqui va el logo-->
                        <a href="home.jsp"><img src="common/img/logo.png" alt="#" /></a>
                     </div>
                  </div>
                  <div class="col-md-8 col-sm-8">
                     <div class="right_bottun">
                        <ul class="conat_info d_none ">
                           <li><a href="#"><i class="fa fa-user" aria-hidden="true"></i></a></li>
                        </ul>
                        <!--Foto de la pestaña del menu-->
                        <button class="openbtn" onclick="openNav()"><img src="common/img/menu_icon.png" alt="#"/> </button> 
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </header>
      <!-- end header inner -->
      <!-- end header -->
      <!-- banner -->
      <section class="banner_main">
         <div id="banner1" class="carousel slide banner_slide" data-ride="carousel">
            
            <div class="carousel-inner">
               <div class="carousel-item active">
                  <div class="container-fluid">
                     <div class="carousel-caption">
                        <div class="row">
                           <div class="col-md-7 col-lg-5">
                              <div class="text-bg">
                                 <!--Aqui va el parameto nombre usuario-->
                                 <h1>Bienvenido <%=nombreUser %></h1>
                                 <h2>Ha Iniciado Sesion Como: <%=codigoAcceso %></h2>
                                
                              </div>
                           </div>
                           <div class="col-md-12 col-lg-7">
                              <div class="row">
                                 <div class="col-md-6">
                                    <div class="ban_track">
                                       <!--Imagen para el fondo-->
                                       <figure><img src="common/img/personaLeyendo.png" alt="#"/></figure>
                                    </div>
                                 </div>
                                 <div class="col-md-6">
                                    <h1 id="h1-b">"Fueron los libros los que me hicieron sentir que tal vez no estaba completamente solo."</h1>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </section>
      <!-- Decoracion -->
      <div id="about" class="about ">
         <div class="container">
            <div class="row d_flex">
               <div class="col-md-6">
                  <div class="about_right">
                     <figure><img src="common/img/about.png" alt="#"/></figure>
                  </div>
               </div>
               <div class="col-md-6">
                  <div class="titlepage">
                     <h2>Sobre Nosotros</h2>
                     <p> Bienvenido a la mejor biblioteca virtual de toda España
                     </p>
                  </div>
               </div>
            </div>
         </div>
      </div>

      <div id="service" class="service">
         <div class="container">
            <div class="row">
               <div class="col-md-10 offset-md-1">
                  <div class="titlepage">
                     <h2>Nuestros Servicios</h2>
                     <p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, There </p>
                  </div>
               </div>
            </div>
            <div class="row">
               <div class="col-md-12">
                  <div class="service_main">
                     <div class="service_box blu_colo">
                        <i><img src="common/img/ser1.png" alt="#"/></i>
                        <h4>Prestamo de Libros</h4>
                     </div>
                     <div class="service_box yelldark_colo">
                        <i><img src="common/img/ser2.png" alt="#"/></i>
                        <h4>Relacion con los Autores</h4>
                     </div>
                     <div class="service_box yell_colo">
                        <i><img src="common/img/ser3.png" alt="#"/></i>
                        <h4>Todo tipo de Generos</h4>
                     </div>
                     <div class="service_box yelldark_colo">
                        <i><img src="common/img/ser4.png" alt="#"/></i>
                        <h4>Conectividad 100%</h4>
                     </div>
                     <div class="service_box yell_colo">
                        <i><img src="common/img/ser5.png" alt="#"/></i>
                        <h4>100% Seguro</h4>
                     </div>
                  </div>
               </div>

            </div>
         </div>
      </div>
     <!--Pie de pagina-->
      <footer>
         <div class="footer bottom_cross1">
            <div class="container">
               <div class="row">
                  <div class="col-md-4">
                     <ul class="location_icon">
                        <li><a href="#"><i class="fa fa-map-marker" aria-hidden="true"></i></a> Puedes Encontranos En : El, C. el Barbero de Sevilla, 1, 41006 Sevilla <br> 
                        </li>
                        <li><a href="#"><i class="fa fa-phone" aria-hidden="true"></i></a>Telefono :  954 64 58 00</li>
                        <li><a href="#"><i class="fa fa-envelope" aria-hidden="true"></i></a>Email : info@Altair.edu.es</li>
                     </ul>
                  </div>
                  <div class="col-md-8">
                     <div class="map">
                        <figure><img src="common/img/map.jpg" alt="#"/></figure>
                     </div>
                  </div>
               </div>
            </div>
            <div class="copyright">
               <div class="container">
                  <div class="row">
                     <div class="col-md-12">
                        <p>© 2023<a href="https://html.design/"> Biblioteca Virtual</a></p>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </footer>
      <!-- Javascript Scripts-->
      <script src="common/js/jquery.min.js"></script>
      <script src="common/js/popper.min.js"></script>
      <script src="common/js/bootstrap.bundle.min.js"></script>
      <script src="common/js/jquery-3.0.0.min.js"></script>
<!--       sidebar -->
      <script src="common/js/jquery.mCustomScrollbar.concat.min.js"></script>
      <script src="common/js/custom.js"></script>
      <script>
         function openNav() {
           document.getElementById("mySidepanel").style.width = "250px";
         }
         
         function closeNav() {
           document.getElementById("mySidepanel").style.width = "0";
         }
      </script>
   </body>
</html>