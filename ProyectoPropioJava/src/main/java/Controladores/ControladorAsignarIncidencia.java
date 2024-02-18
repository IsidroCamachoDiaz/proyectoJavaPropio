package Controladores;

import java.io.IOException;
import java.util.Calendar;

import Dtos.IncidenciaDTO;
import Dtos.UsuarioDTO;
import Utilidades.Alerta;
import Utilidades.Escritura;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControladorAsignarIncidencia extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 * Metodo que se usa en el formulario para coger los valores
	 * y comprobarlos para Asignar la incidencia
	 * @param request
	 * @param response
	 * 
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		
			try {
					//Declaramos lo que necesitemos
					implementacionCRUD acciones = new implementacionCRUD();
					//Cogemos los valores del formulario
					String idUsuario= request.getParameter("id");
					String idUsuarioIncidencia= request.getParameter("idI");
					
					//Creamos los DTO de las cosas que necesitamos
					UsuarioDTO usuario = acciones.SeleccionarUsuario("Select/"+idUsuario);
					IncidenciaDTO incidenciaAsignar= acciones.SeleccionarIncidencia("Select/"+idUsuarioIncidencia);
					
					//COmprobamos que encuentre los DTO
					if(usuario==null||incidenciaAsignar==null) {
						Alerta.Alerta(request, "No se encontro al usuario o la incidencia", "error");
						response.sendRedirect("index.jsp");
						Escritura.EscribirFichero("No se encontro al usuario o a la incidencia en la base de datos");
					}
					
					//Asignamos al empleado y la fecha de inicio
					incidenciaAsignar.setEmpleado(usuario);					
					incidenciaAsignar.setFecha_inicio(Calendar.getInstance());
					
					
					//Comprobamos que se actualice bien la incidencia
					if(acciones.ActualizarIncidencia(incidenciaAsignar)) {
						Alerta.Alerta(request, "Se le asigno la incidencia corretamente", "success");
						response.sendRedirect("home.jsp");
						Escritura.EscribirFichero("Un empleado se asigno una incidencia");
					}
					//Si no se actualiza bien se avisa al usuario
					else {
						Alerta.Alerta(request, "No se le pudo asignar la incidencia intentelo mas tarde", "error");
						response.sendRedirect("home.jsp");
						Escritura.EscribirFichero("Un empleado intento asignarse una incidencia pero no se pudo actualizar");
					}
					
			} catch (IOException e) {
				e.printStackTrace();
				Escritura.EscribirFichero("Hubo un error en asignarse incidencia "+e.getLocalizedMessage());
			}
	 }
}
