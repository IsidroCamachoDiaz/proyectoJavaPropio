<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.util.Base64" %>
<%@ page import="Utilidades.implementacionCRUD" %>
<%@ page import="Utilidades.Alerta" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="icon" href="img/logoPNG.png" type="image/jpg">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="fontawesome/css/all.min.css">
    <link rel="stylesheet" href="css/templatemo-style.css">

</head>
<body>
<%
String accesoSesion = "0";
try {
    accesoSesion = session.getAttribute("acceso").toString();
    if (!accesoSesion.equals("1") && !accesoSesion.equals("2") && !accesoSesion.equals("3")) {
        throw new IllegalStateException("Acceso inválido");
    }

} catch (Exception e) {
    Alerta.Alerta(request, "No ha iniciado Sesión en la web", "error");
    response.sendRedirect("../index.jsp");
    return;
}
System.out.println(accesoSesion);
implementacionCRUD acciones=new implementacionCRUD();
//Si modifico el usuario que se actualice
UsuarioDTO user =(UsuarioDTO) session.getAttribute("usuario");
//Selecciono el nuevi
user = acciones.SeleccionarUsuario("Select/"+user.getIdUsuario());
//Convierto la imagen
String base64Image = Base64.getEncoder().encodeToString(user.getFoto());

//Coloca la cadena Base64 en el alcance de la solicitud
request.setAttribute("base64Image", base64Image);
session.setAttribute("imagen", base64Image);

%>

    <!-- Page Loader -->
    <div id="loader-wrapper">
        <div id="loader"></div>

        <div class="loader-section section-left"></div>
        <div class="loader-section section-right"></div>

    </div>
    <nav class="navbar navbar-expand-lg">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img class="logo" src="img/logo.png" alt="Imagen de Usuario">
                <span class="text-white">Menu</span>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link nav-link-1 active" aria-current="page" href="home.jsp">Menu</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-2" href="modificarPerfil.jsp">
                            <div class="user-info-container d-flex align-items-center">
                                <img class="rounded-circle user-avatar" src="data:image/jpeg;base64,${base64Image}" alt="Imagen de Usuario">
                                <span class="ml-2 text-white">Perfil</span>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="tm-hero d-flex justify-content-center align-items-center" data-parallax="scroll" data-image-src="img/hero.jpg">
        <form class="d-flex tm-search-form">
            <div class="user-info-container2 d-flex align-items-center">
                <img class="rounded-circle user-avatar2" src="data:image/jpeg;base64,${base64Image}" alt="Imagen de Usuario">
                <h2 class="text-white">Bienvenido <%=user.getNombreUsuario() %></h2>
            </div>
        </form>
    </div>
	<%
	String acceso=user.getAcceso().getCodigoAcceso();
	%>
    <div class="container-fluid tm-container-content tm-mt-60">
        <div class="row mb-4">
            <h2 class="col-6 tm-text-primary text-white">
                Seleccione Una Opcion
            </h2>
            <div class="col-6 d-flex justify-content-end align-items-center">
                
            </div>
        </div>
        
        <div class="row tm-mb-90 tm-gallery">
        <%if(acceso.equals("Usuario")){ %>
        	<div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
                <figure class="effect-ming tm-video-item">
                    <img src="img/img-03.jpg" alt="Image" class="img-fluid">
                    <figcaption class="d-flex align-items-center justify-content-center">
                        <h2>Solicitudes</h2>
                        <a href="photo-detail.html"></a>
                    </figcaption>                    
                </figure>
            </div>
            <%}
        	else{ %>
            <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
                <figure class="effect-ming tm-video-item">
                    <img src="img/img-04.jpg" alt="Image" class="img-fluid">
                    <figcaption class="d-flex align-items-center justify-content-center">
                        <h2>Incidencias</h2>
                        <a href="photo-detail.html"></a>
                    </figcaption>                    
                </figure>
            </div>

            <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
                <figure class="effect-ming tm-video-item">
                    <img src="img/img-05.jpg" alt="Image" class="img-fluid">
                    <figcaption class="d-flex align-items-center justify-content-center">
                        <h2>Trabajos</h2>
                        <a href="photo-detail.html"></a>
                    </figcaption>                    
                </figure>
            </div>
            <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
                <figure class="effect-ming tm-video-item">
                    <img src="img/img-06.jpg" alt="Image" class="img-fluid">
                    <figcaption class="d-flex align-items-center justify-content-center">
                        <h2>Tipos De Incidencias</h2>
                        <a href="photo-detail.html"></a>
                    </figcaption>                    
                </figure>
            </div>
            <%
            if(acceso.equals("Administrador")){%>
            <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
                <figure class="effect-ming tm-video-item">
                    <img src="img/img-01.jpg" alt="Image" class="img-fluid">
                    <figcaption class="d-flex align-items-center justify-content-center">
                        <h2>Gestion de Usuarios</h2>
                        <a href="photo-detail.html"></a>
                    </figcaption>                    
                </figure>
            </div>
            <%} 
            }
            %>
                    
        </div> <!-- row -->
        
    </div> <!-- container-fluid, tm-container-content -->
    
    <script src="js/plugins.js"></script>
    <script>
        $(window).on("load", function() {
            $('body').addClass('loaded');
        });
    </script>
</body>
</html>