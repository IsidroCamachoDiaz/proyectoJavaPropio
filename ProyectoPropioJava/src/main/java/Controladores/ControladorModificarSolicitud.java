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
public class ControladorModificarSolicitud extends HttpServlet {
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
			 //Creamos un boleano para comprobar
			 boolean cambio=false;
			 implementacionCRUD acciones=new implementacionCRUD();
			 	
			 	//Cogemos los datos insertados por el usuario
			 	String idUsuario=request.getParameter("id");
			 	String idSolicitud=request.getParameter("idS");
			 	String descripcionNueva=request.getParameter("descripcion");
			 	
			 	//Cogemos el usuario
			 	UsuarioDTO user =acciones.SeleccionarUsuario("Select/"+idUsuario);
			 	
			 	//Cogemos la solicitud
				SolicitudDTO solicitud = acciones.SeleccionarSolicitud("Select/"+idSolicitud);
				
				//Comprobamos si esta vacio
				if(solicitud.getDescripcion().equals(null)||solicitud.getDescripcion().equals("")) {
					Escritura.EscribirFichero("Un usuario quizo cambiar una solicitud pero no puso nada");
					Alerta.Alerta(request,"No introdujo ningun descripcion del problema","info");
					response.sendRedirect("vistas/crearSolicitud.jsp");
				}
				
				//Comprobamos si es distinta a la antigua
				if(!solicitud.getDescripcion().equals(descripcionNueva)) {
					solicitud.setDescripcion(descripcionNueva);
					solicitud.getIncidenciaSolicitud().setDescripcion_usuario(descripcionNueva);
					cambio=true;
				}
				
				
				//Declaramos la implementacion
				ImplementacionInteraccionIncedencias impl = new ImplementacionInteraccionIncedencias();

				try {
					//Comprobamos si se cambio la descripcion
					if(cambio) {
						//Comprobamos sis e actualizo bien
						if(acciones.ActualizarSolicitud(solicitud)) {
							acciones.ActualizarIncidencia(solicitud.getIncidenciaSolicitud());
							Escritura.EscribirFichero("Un usuario cambio la descripcion de una solicitud");
						Alerta.Alerta(request,"Se envio la Solicitud Correctamente","success");
						response.sendRedirect("vistas/home.jsp");
						}
						//Si no se pudo actualizar se avisa al usuario
						else {
							Escritura.EscribirFichero("Un usuario quizo cambiar una solicitud pero no se pudo actualizar");
							Alerta.Alerta(request,"Hubo un error paar actualizar su solicitud intentelo mas tarde","error");
							response.sendRedirect("vistas/mostrarSolicitudes.jsp");	
						}
					}
					//Si no cambio nada se le avisa
					else {
						Escritura.EscribirFichero("Un usuario quizo cambiar una solicitud pero no cambio nada");
						Alerta.Alerta(request,"No cambio la descripcion de la solicitud","warning");
						response.sendRedirect("vistas/home.jsp");
					}
				} catch (IOException e) {
					Escritura.EscribirFichero("Hubo un error "+e.getLocalizedMessage());
					e.printStackTrace();
					response.sendRedirect("vistas/home.jsp");
				}
				
		 }catch(Exception e) {
			 Escritura.EscribirFichero("Hubo un error "+e.getLocalizedMessage());
			 System.out.println("[ERROR-ControladorRegistro-doPost] Se produjo un error en el metodo post al insertar al usuario. | "+e);
			}
		 	
		}
}
