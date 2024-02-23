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
public class ControladorModificarIncidencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	/**
	 *
	 * Metodo que se usa en el formulario para coger los valores
	 * y comprobarlos para Modificar una Incidencia
	 * @param request
	 * @param response
	 * 
	 * */
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
			
		 try {
			 //Creamos un boleano para comprobar
			 boolean cambio=false;
			 implementacionCRUD acciones=new implementacionCRUD();
			 	
			 	//Cogemos los datos insertados por el usuario
			 	String idIncidencia=request.getParameter("idI");
			 	String descripcionNueva=request.getParameter("descripcion");
			 	
			 	
			 	//Cogemos la solicitud
				IncidenciaDTO incidencia = acciones.SeleccionarIncidencia("Select/"+idIncidencia);
				
				//Comprobamos si esta vacio
				if(descripcionNueva==null||descripcionNueva.equals("")) {
					Escritura.EscribirFichero("Un empleado quizo cambiar la descripcion pero no puso nada");
					Alerta.Alerta(request,"No introdujo ningun descripcion del problema","info");
					response.sendRedirect("vistas/home.jsp");
				}
				
				//Comprobamos si es nulo
				if(incidencia.getDescripcion_tecnica()==null) {
					incidencia.setDescripcion_tecnica(descripcionNueva);
					cambio=true;
				}
				//Comprobamos si es distinta a la antigua
				else{
					if(!incidencia.getDescripcion_tecnica().equals(descripcionNueva)) {
						incidencia.setDescripcion_tecnica(descripcionNueva);
						cambio=true;
					}
				}
				
				
				//Declaramos la implementacion
				ImplementacionInteraccionIncedencias impl = new ImplementacionInteraccionIncedencias();

				try {
					//Comprobamos si se cambio la descripcion
					if(cambio) {
						//Comprobamos sis e actualizo bien
						if(acciones.ActualizarIncidencia(incidencia)) {
							Escritura.EscribirFichero("Un empleado cambio la descripcion de una incidencia");
						Alerta.Alerta(request,"Se Actualizo la incidencia correctamente","success");
						response.sendRedirect("vistas/home.jsp");
						}
						//Si no se pudo actualizar se avisa al usuario
						else {
							Escritura.EscribirFichero("Un empelado quiso cambiar una incidencia pero no se pudo actualizar");
							Alerta.Alerta(request,"Hubo un error paar actualizar su solicitud intentelo mas tarde","error");
							response.sendRedirect("vistas/mostrarIncidencias.jsp");	
						}
					}
					//Si no cambio nada se le avisa
					else {
						Escritura.EscribirFichero("Un empleado quizo cambiar un descripcion de una incidencia pero no cambio nada");
						Alerta.Alerta(request,"No cambio la descripcion de la incidencia","warning");
						response.sendRedirect("vistas/home.jsp");
					}
				} catch (IOException e) {
					Escritura.EscribirFichero("Hubo un error "+e.getLocalizedMessage());
					e.printStackTrace();
					response.sendRedirect("vistas/home.jsp");
				}
				
		 }catch(Exception e) {
			 Escritura.EscribirFichero("Hubo un error "+e.getLocalizedMessage());
			}
		 	
		}
}
