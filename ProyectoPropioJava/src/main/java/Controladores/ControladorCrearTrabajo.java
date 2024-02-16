package Controladores;



import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TipoTrabajoDTO;
import Dtos.TrabajoDTO;
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
public class ControladorCrearTrabajo extends HttpServlet {
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
			 	String idIncidencia=request.getParameter("idI");
			 	String idTipo=request.getParameter("tipo");
			 	String descripcion=request.getParameter("descripcion");
			 	int horas=Integer.parseInt(request.getParameter("horas"));
			 	
			 	
			 	IncidenciaDTO incidenciaBD=acciones.SeleccionarIncidencia("Select/"+idIncidencia);
			 	TipoTrabajoDTO tipoBD=acciones.SeleccionarTipoDeTrabajo("Select/"+idTipo);
			 	
			 	if(descripcion==null||incidenciaBD==null||tipoBD==null||horas==0) {
			 		Alerta.Alerta(request,"no introdujo bien los valores","info");
					response.sendRedirect("vistas/crearTrabajo.jsp");
			 	}
			 	
			 	TrabajoDTO trabajoMeter=new TrabajoDTO(descripcion,false,horas,incidenciaBD,tipoBD);
			 	
			 	//Creamos la solicitud y la incidencia
				SolicitudDTO solicitud = new SolicitudDTO(request.getParameter("descripcion"),false,Calendar.getInstance(),user);
				
				if(solicitud.getDescripcion().equals(null)||solicitud.getDescripcion().equals("")) {
					Alerta.Alerta(request,"No introdujo ningun descripcion del problema","info");
					response.sendRedirect("vistas/crearSolicitud.jsp");
				}
				
				//Declaramos la implementacion
				ImplementacionInteraccionIncedencias impl = new ImplementacionInteraccionIncedencias();

				try {
					//Comprobamos si se creo bien la incidencia
					if(impl.CrearIncidencia(solicitud, request)) {
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
