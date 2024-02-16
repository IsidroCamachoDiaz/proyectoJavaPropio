<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="Dtos.SolicitudDTO" %>
<%@ page import="Dtos.IncidenciaDTO" %>
<%@ page import="Utilidades.Alerta" %>
<%@ page import="Utilidades.implementacionCRUD" %>
<%@ page import="Utilidades.Escritura" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SystemRevive</title>
    <link rel="icon" href="img/logoPNG.png" type="image/jpg">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="fontawesome/css/all.min.css">
    <link rel="stylesheet" href="css/templatemo-style.css">
</head>
<body>
<%
 try{
String acceso=session.getAttribute("acceso").toString();

if(!acceso.equals("2")&&!acceso.equals("3")){
	Alerta.Alerta(request,"Solo los usuarios pueden modificar Solicitudes","error");
	response.sendRedirect("home.jsp");
	 Escritura.EscribirFichero("Una persona intento acceder a modificar solicitudes pero no es Usuario");

}
   }catch(Exception e){
	   Escritura.EscribirFichero("Una persona intento acceder sin haberse logueado");
	   Alerta.Alerta(request,"No ha iniciado Sesion en la web","error");
	   response.sendRedirect("index.jsp");
		
   }
 Escritura.EscribirFichero("Se accedio a modificar Solicitud");
 
 implementacionCRUD acciones=new implementacionCRUD();
//Si modifico el usuario que se actualice
UsuarioDTO user =acciones.SeleccionarUsuario("Select/"+session.getAttribute("usuario"));
//Convierto la imagen
request.setAttribute("base64Image", session.getAttribute("imagen"));

String idIncidencia = request.getParameter("idI");
IncidenciaDTO incidencia= acciones.SeleccionarIncidencia("Select/"+idIncidencia);

if(incidencia.getEmpleado().getIdUsuario()!=user.getIdUsuario()){
	Alerta.Alerta(request,"Esta Incidencia no es suya","error");
	response.sendRedirect("home.jsp");
}

if(incidencia.getDescripcion_tecnica()==null){
	incidencia.setDescripcion_tecnica("");
}

%>
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
            <%session.setAttribute("mensajeAlerta","null");
            session.setAttribute("tipoAlerta","null"); %>
        }
    });
</script>
    <!-- Page Loader -->
    <div id="loader-wrapper">
        <div id="loader"></div>

        <div class="loader-section section-left"></div>
        <div class="loader-section section-right"></div>

    </div>
    <nav class="navbar navbar-expand-lg">
        <div class="container-fluid">
            <a class="navbar-brand" href="home.jsp">
                <img class="logo" src="img/logo.png" alt="Imagen de Usuario">
                <span class="text-white">SystemRevive</span>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link nav-link-1" aria-current="page" href="home.jsp">Menu</a>
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
            <h2 class="text-white">Modificar Incidencia</h2>
        </form>
    </div>

    <div class="container-fluid tm-container-content tm-mt-60">
        <div class="row mb-4">
            <h2 class="col-6 tm-text-primary text-white">
                Modifique la Incidencia
            </h2>
        </div>
       <div class="row tm-mb-90">            
    <div class="col-xl-8 col-lg-7 col-md-6 col-sm-12 text-center">
        <img src="https://img.freepik.com/fotos-premium/gerente-hombre-europeo-milenario-guapo-ocupado-escribiendo-computadora-lugar-trabajo-interior-oficina-moderna_116547-43017.jpg" img-fluid rounded">
    </div>
    <div class="col-xl-4 col-lg-5 col-md-6 col-sm-12">
        <div class="tm-bg-gray tm-video-details">
        <form action="../ControladorModificarIncidencia" method="post" id="formulario">
            <div class="container mt-4">
                <label for="exampleTextarea" class="form-label">Describenos lo que te ocurre:</label>
                <textarea class="form-control"  rows="4" name="descripcion"><%=incidencia.getDescripcion_tecnica() %></textarea>
              </div>
             <div class="mb-4 text-center" style="margin-top:10px;">
                <button class="btn btn-primary tm-btn-big"  type="submit">Modificar Incidencia</button>
            </div>
            <input type="text" id="id" name="idI" value="<%=idIncidencia %>" style="display: none;" >
          </form>
           <a href="mostrarSolicitudes.jsp">
				<button  class="btn btn-primary" type="button">Volver</button>
			</a>	
        </div>
    </div>
</div>

    </div> <!-- container-fluid, tm-container-content -->

    
    <script src="js/plugins.js"></script>
    <script>
        $(window).on("load", function() {
            $('body').addClass('loaded');
        });
    </script>
</body>
</html>