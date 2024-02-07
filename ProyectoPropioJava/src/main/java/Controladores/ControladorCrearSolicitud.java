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
				
				if(solicitud.getDescripcion().equals(null)||solicitud.getDescripcion().equals("")) {
					Alerta.Alerta(request,"No introdujo ningun descripcion del problema","info");
					response.sendRedirect("vistas/crearSolicitud.jsp");
				}
				IncidenciaDTO incidencia =new IncidenciaDTO(request.getParameter("descripcion"),false,solicitud);
				solicitud.setIncidenciaSolicitud(incidencia);
				
				//Declaramos la implementacion
				ImplementacionInteraccionIncedencias impl = new ImplementacionInteraccionIncedencias();

				try {
					//Comprobamos si se creo bien la incidencia
					if(impl.CrearIncidencia(solicitud, incidencia, request)) {
						Alerta.Alerta(request,"Se envio la Solicitud Correctamente","success");
						response.sendRedirect("vistas/home.jsp");
					}
					else {
						Alerta.Alerta(request,"Hubo un fallo al crear la solicitud","error");
						response.sendRedirect("vistas/home.jsp");
					}
				} catch (IOException e) {
					e.printStackTrace();
					response.sendRedirect("vistas/crearUsuario.jsp");
				}
				
		 }catch(Exception e) {
			 System.out.println("[ERROR-ControladorRegistro-doPost] Se produjo un error en el metodo post al insertar al usuario. | "+e);
			}
		 	
		}
}
