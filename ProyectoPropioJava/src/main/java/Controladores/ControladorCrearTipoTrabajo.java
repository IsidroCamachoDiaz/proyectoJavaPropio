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
import Utilidades.implementacionCRUD;
import Servicios.ImplementacionAdministracion;
import Servicios.ImplementacionInteraccionIncedencias;
import Servicios.ImplementacionInteraccionTipos;
import Servicios.ImplentacionIntereaccionUsuario;

@MultipartConfig
public class ControladorCrearTipoTrabajo extends HttpServlet {
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
			 	String descripcionTipo=request.getParameter("descripcion");
			 	Float precioServicio=Float.parseFloat(request.getParameter("precio"));
			 	
			 	if(descripcionTipo==null||descripcionTipo.equals("")||precioServicio==null||precioServicio==0) {
			 		Alerta.Alerta(request, "No puso todos los campos", "error");
			 		response.sendRedirect("vistas/home.jsp");
			 	}
			 	
			 	TipoTrabajoDTO tipoMeter=new TipoTrabajoDTO(descripcionTipo,precioServicio);
			 					
				//Declaramos la implementacion
				ImplementacionInteraccionTipos impl = new ImplementacionInteraccionTipos();

				try {
					//Comprobamos si se creo bien la incidencia
					if(impl.CrearTipo(tipoMeter, request)) {
						response.sendRedirect("vistas/home.jsp");
					}
					else {
						response.sendRedirect("vistas/home.jsp");
					}
				} catch (IOException e) {
					Alerta.Alerta(request, "Hubo un error intentelo mas tarde", "error");
					response.sendRedirect("vistas/home.jsp");
				}
				
		 }catch(Exception e) {
			 System.out.println("[ERROR-ControladorRegistro-doPost] Se produjo un error en el metodo post al insertar al usuario. | "+e);
			}
		 	
		}
}
