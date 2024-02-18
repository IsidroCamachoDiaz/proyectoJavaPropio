package Controladores;



import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TipoTrabajoDTO;
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
import Servicios.ImplementacionInteraccionTipos;
import Servicios.ImplentacionIntereaccionUsuario;

@MultipartConfig
public class ControladorCrearTipoTrabajo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	/**
	 *
	 * Metodo que se usa en el formulario para coger los valores
	 * y comprobarlos para Crear el tipo de trabajo
	 * @param request
	 * @param response
	 * 
	 * */
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
			
		 try {
			 implementacionCRUD acciones=new implementacionCRUD();
			 	
			 	//Cogemos los datos insertados por el usuario
			 	String descripcionTipo=request.getParameter("descripcion");
			 	Float precioServicio=Float.parseFloat(request.getParameter("precio"));
			 	
			 	//Comprobamos que no haya valores nulos
			 	if(descripcionTipo==null||descripcionTipo.equals("")||precioServicio==null||precioServicio==0) {
			 		Alerta.Alerta(request, "No puso todos los campos", "error");
			 		Escritura.EscribirFichero("Un usuario quiso crear un tipo de trabajo pero puso valores nulos");
			 		response.sendRedirect("vistas/home.jsp");
			 	}
			 	
			 	//Creamos el tipo de traabjo
			 	TipoTrabajoDTO tipoMeter=new TipoTrabajoDTO(descripcionTipo,precioServicio);
			 					
				//Declaramos la implementacion
				ImplementacionInteraccionTipos impl = new ImplementacionInteraccionTipos();

				try {
					//Comprobamos si se creo bien la incidencia
					if(impl.CrearTipo(tipoMeter, request)) {
						Escritura.EscribirFichero("Un usuario creo un tipo de trabajo");
						response.sendRedirect("vistas/home.jsp");
					}
					//Si no se creo bien avisamos al usuario
					else {
						Escritura.EscribirFichero("Un usuario quiso crear un tipo de trabajo pero no se pudo insertar");
						response.sendRedirect("vistas/home.jsp");
					}
				} catch (IOException e) {
					Alerta.Alerta(request, "Hubo un error intentelo mas tarde", "error");
					Escritura.EscribirFichero("Hubo un error en crear tipo de trabajo "+e.getLocalizedMessage());
					response.sendRedirect("vistas/home.jsp");
				}
				
		 }catch(Exception e) {
			 Escritura.EscribirFichero("Hubo un error en crear tipo de trabajo "+e.getLocalizedMessage());
			try {
				response.sendRedirect("vistas/home.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 }
		 	
		}
}
