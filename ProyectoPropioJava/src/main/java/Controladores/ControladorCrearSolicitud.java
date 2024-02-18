package Controladores;



import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.UsuarioDTO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import Utilidades.Alerta;
import Utilidades.ComprobacionImagen;
import Utilidades.Encriptado;
import Utilidades.Escritura;
import Utilidades.implementacionCRUD;
import Servicios.ImplementacionAdministracion;
import Servicios.ImplementacionInteraccionIncedencias;
import Servicios.ImplentacionIntereaccionUsuario;

@MultipartConfig
public class ControladorCrearSolicitud extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	 protected void doGet(HttpServletRequest request, HttpServletResponse response){
		 try
		 {
			 response.getWriter().append("Served at: ").append(request.getContextPath());
		 }catch(Exception e) {

				System.out.println("[ERROR-ControladorRegistro-doGet] Se produjo un error en el metodo get");
			}
		
		}
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
			
		 try {
			 implementacionCRUD acciones=new implementacionCRUD();
			 	
			 	//Cogemos los datos insertados por el usuario
			 	String idUsuario=request.getParameter("id");
			 		 	
			 	UsuarioDTO user =acciones.SeleccionarUsuario("Select/"+idUsuario);
			 	//Creamos la solicitud y la incidencia
				SolicitudDTO solicitud = new SolicitudDTO(request.getParameter("descripcion"),false,Calendar.getInstance(),user);
				
				//Comprobamos si pone valores nulos
				if(solicitud.getDescripcion().equals(null)||solicitud.getDescripcion().equals("")) {
					Alerta.Alerta(request,"No introdujo ningun descripcion del problema","info");
					response.sendRedirect("vistas/crearSolicitud.jsp");
					Escritura.EscribirFichero("Un usuario intento crear una solicitud pero puso valores nulos");
				}
				
				//Declaramos la implementacion
				ImplementacionInteraccionIncedencias impl = new ImplementacionInteraccionIncedencias();

				try {
					//Comprobamos si se creo bien solicitud
					if(impl.CrearIncidencia(solicitud, request)) {
						Alerta.Alerta(request,"Se envio la Solicitud Correctamente","success");
						Escritura.EscribirFichero("Un usuario quiso creo una solicitud");
						response.sendRedirect("vistas/home.jsp");
					}
					//Si no se creo bien se avisa al usuario
					else {
						Alerta.Alerta(request,"Hubo un fallo al crear la solicitud","error");
						Escritura.EscribirFichero("Un usuario quiso crear una solicitud pero no se pudo insertar");
						response.sendRedirect("vistas/home.jsp");
					}
				} catch (IOException e) {
					e.printStackTrace();
					Escritura.EscribirFichero("Hubo un error en crear solicitud "+e.getLocalizedMessage());
					response.sendRedirect("vistas/home.jsp");
				}
				
		 }catch(Exception e) {
			 Escritura.EscribirFichero("Hubo un error en crear solicitud "+e.getLocalizedMessage());
				try {
					response.sendRedirect("vistas/home.jsp");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		 	
		}
}
