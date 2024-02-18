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
import Utilidades.Escritura;
import Utilidades.implementacionCRUD;
import Servicios.ImplementacionAdministracion;
import Servicios.ImplementacionInteraccionIncedencias;
import Servicios.ImplentacionIntereaccionUsuario;

@MultipartConfig
public class ControladorCrearTrabajo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
			
		 try {
			 implementacionCRUD acciones=new implementacionCRUD();
			 	
			 	//Cogemos los datos insertados por el usuario
			 	String idIncidencia=request.getParameter("idI");
			 	String idTipo=request.getParameter("tipo");
			 	String descripcion=request.getParameter("descripcion");
			 	int horas=Integer.parseInt(request.getParameter("horas"));
			 	
			 	
			 	//Cogemos la incidencia y el tipo de trabajo de eligio el usuario
			 	IncidenciaDTO incidenciaBD=acciones.SeleccionarIncidencia("Select/"+idIncidencia);
			 	TipoTrabajoDTO tipoBD=acciones.SeleccionarTipoDeTrabajo("Select/"+idTipo);
			 	
			 	//Comprobamos si esta todo correcto los valores
			 	if(descripcion==null||incidenciaBD==null||tipoBD==null||horas==0||descripcion.equals("")) {
			 		Alerta.Alerta(request,"no introdujo bien los valores","info");
					response.sendRedirect("vistas/crearTrabajo.jsp");
					Escritura.EscribirFichero("Un usuario quiso crear un trabajo pero puso valores nulos");
					return;
			 	}
			 	
			 	//Creamos el trabajo
			 	TrabajoDTO trabajoMeter=new TrabajoDTO(descripcion,false,horas,incidenciaBD,tipoBD);				
				
				//Declaramos la implementacion
				ImplementacionInteraccionIncedencias impl = new ImplementacionInteraccionIncedencias();

				try {
					//Comprobamos si se creo bien el trabajo
					if(impl.CrearTrabajo(trabajoMeter, request)) {
						Alerta.Alerta(request,"Se creo el trabajo Correctamente","success");
						response.sendRedirect("vistas/home.jsp");
						Escritura.EscribirFichero("Un usuario creo un trabajo par una incidencia");
					}
					//Si no se pudo crear se avisa al usuario
					else {
						Alerta.Alerta(request,"Hubo un fallo al crear el trabajo","error");
						response.sendRedirect("vistas/home.jsp");
						Escritura.EscribirFichero("Un usuario quiso crear un trabajo pero no se pudo insertar");
					}
				} catch (IOException e) {
					e.printStackTrace();
					response.sendRedirect("vistas/crearUsuario.jsp");
					Escritura.EscribirFichero("Hubo un error en crear Trabajo "+e.getLocalizedMessage());
				}
				
		 }catch(Exception e) {
			 try {
				response.sendRedirect("vistas/home.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			 Escritura.EscribirFichero("Hubo un error en crear Trabajo "+e.getLocalizedMessage());
		 }
		 	
		}
}
