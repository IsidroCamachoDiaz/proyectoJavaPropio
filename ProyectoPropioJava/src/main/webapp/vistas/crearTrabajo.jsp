<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="Dtos.TipoTrabajoDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
 //Control de Sesion
 String accesoSesion = "0";
 try {
     accesoSesion = session.getAttribute("acceso").toString();
     if (accesoSesion.equals("1")) {
     	Alerta.Alerta(request, "No puede acceder a este lugar de la web", "warning");
     	Escritura.EscribirFichero("Un usuario intento entrar a crear trabajos");
     	response.sendRedirect("home.jsp");
     	return;
     }

 } catch (Exception e) {
     Alerta.Alerta(request, "No ha iniciado Sesión en la web", "error");
     response.sendRedirect("../index.jsp");
     return;
 }
 Escritura.EscribirFichero("Se accedio a crear Trabajo");
%>
<%
//Declaramos lo que necesitemos
implementacionCRUD acciones=new implementacionCRUD();

//Cogemos el usuario
UsuarioDTO usuario =acciones.SeleccionarUsuario("Select/"+session.getAttribute("usuario"));

//Insertamos la imagen
request.setAttribute("base64Image", session.getAttribute("imagen"));

//Cogemos la incidencia
String idIncidencia = request.getParameter("idI");

//Cogemos todas los tipos y los filtramos por los disponibles
List <TipoTrabajoDTO> tipos=acciones.SeleccionarTodosTiposDeTrabajo();
List <TipoTrabajoDTO> tiposActivos=new ArrayList <TipoTrabajoDTO>();

	for(TipoTrabajoDTO t:tipos){
		if(t.getFecha_fin()==null){
			tiposActivos.add(t);
		}
	}
	
//Si no hay ninguno se avisa al usuario y se manda al home
if(tiposActivos.isEmpty()){
	Alerta.Alerta(request, "No hay ningun tipo de trabajo disponible", "warning");
	response.sendRedirect("home.jsp");
	return;
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
            <h2 class="text-white">Crear Trabajo</h2>
        </form>
    </div>

    <div class="container-fluid tm-container-content tm-mt-60">
        <div class="row mb-4">
            <h2 class="col-6 tm-text-primary text-white">
                Ingrese los datos para crear el trabajo
            </h2>
        </div>
       <div class="row tm-mb-90">            
    <div class="col-xl-8 col-lg-7 col-md-6 col-sm-12 text-center">
        <img src="https://img.freepik.com/foto-gratis/joven-que-suelda-circuito-computadora-sobre-escritorio-madera_23-2147922188.jpg" alt="Image" class="img-fluid rounded">
    </div>
    <div class="col-xl-4 col-lg-5 col-md-6 col-sm-12">
        <div class="tm-bg-gray tm-video-details">
        <form action="../ControladorCrearTrabajo" method="post" enctype="multipart/form-data" id="formulario">
            <!-- Campo de Descripcion -->
            <div class="form-group">
                <label for="exampleTextarea" class="form-label">Describe lo que haras:</label>
                <textarea class="form-control"  rows="4" name="descripcion" required></textarea>
            </div>

            <!-- Campo de Número de Horas -->
            <div class="form-group">
                <label for="telefono">Horas Del Trabajo:</label>
                <input type="tel" class="form-control" id="horas" name="horas" pattern="[0-9]" required >
            </div>
            
            <!-- Campo de Tipo de Trabajo -->
            <div class="form-group">
                <label for="campo_select" class="form-label">Selecciona el acceso del usuario:</label>
		    <select class="form-select" id="tipo" name="tipo" required>
			<%	
			for(int i=0;i<tiposActivos.size();i++){
					%><option value="<%=tiposActivos.get(i).getId_tipo() %>"><%=tiposActivos.get(i).getDescripcion_tipo() %></option><%
		    }
		    %>
		    </select>
            </div>
            
			
            <input type="text" id="id" name="idI" value="<%=idIncidencia %>" style="display: none;" >
             <div class="mb-4 text-center">
                <button class="btn btn-primary tm-btn-big"  type="submit">Crear Trabajo</button>
            </div>
          </form>
           <a href="mostrarTrabajos.jsp">
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