package Controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TrabajoDTO;
import Dtos.UsuarioDTO;
import Servicios.ImplementacionInteraccionIncedencias;
import Utilidades.Alerta;
import Utilidades.Escritura;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControladorFinalizarIncidencia extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 * Metodo que se usa en el formulario para coger los valores
	 * y comprobarlos para Finalizar una incidencia
	 * @param request
	 * @param response
	 * 
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		
			try {
					//Declaramos lo que necesitemos
					implementacionCRUD acciones = new implementacionCRUD();
					ImplementacionInteraccionIncedencias inter= new ImplementacionInteraccionIncedencias();
					
					//Cogemos los valores del formulario
					String idUsuario= request.getParameter("id");
					String idUsuarioIncidencia= request.getParameter("idI");
					
					//Creamos el DTO con los paraemos pasados y usando el metodos de encriptar
					UsuarioDTO usuario = acciones.SeleccionarUsuario("Select/"+idUsuario);
					IncidenciaDTO incidenciaAsignar= acciones.SeleccionarIncidencia("Select/"+idUsuarioIncidencia);
					
					//Para asignar las incidencias con sus solicitudes
					List <SolicitudDTO> solicitudes = acciones.SeleccionarTodasSolicitudes();
										
					//Comprobamos si es null algo
					if(usuario==null||incidenciaAsignar==null) {
						Alerta.Alerta(request, "No se encontro al usuario o la incidencia", "error");
						response.sendRedirect("index.jsp");
						Escritura.EscribirFichero("Un usuario quiso finalizar una incidencia pero no se encontro el usuario o la incidencia");
					}
					
					//Cogemos todos los trabajos y los filtramos por los de la incidencia
					List <TrabajoDTO> trabajos = acciones.SeleccionarTodosTrabajos();
						incidenciaAsignar.setTrabajosConIncidencias(new ArrayList <TrabajoDTO>());
						for(TrabajoDTO tra:trabajos){
							if(tra.getIncidencia().getId_incidencia()==incidenciaAsignar.getId_incidencia()){
								incidenciaAsignar.getTrabajosConIncidencias().add(tra);
							}
						}
					
					
					//Comprobamos si tienes trabajos asignados
					if(incidenciaAsignar.getTrabajosConIncidencias()==null||incidenciaAsignar.getTrabajosConIncidencias().isEmpty()) {
						Alerta.Alerta(request, "La incidencia indicada no tiene ningun trabajo hecho", "error");
						response.sendRedirect("home.jsp");
						Escritura.EscribirFichero("Un usuario quiso finalizar una incidencia pero la incidencia no tiene ningun trabajo hecho");
						return;
					}
										
					//Comprobamos si lo finaliza bien
					if(inter.FinalizarIncidencia(incidenciaAsignar, request)) {
						Alerta.Alerta(request, "Se Finalizo la Incidencia Correctamente", "success");
						response.sendRedirect("home.jsp");
						Escritura.EscribirFichero("Un usuario finalizo un incidencia");
					}
					//Si no conseigue finalizarlo se avisa al usuario
					else {
						response.sendRedirect("home.jsp");
						Escritura.EscribirFichero("Un usuario quiso finalizar ua incdencia pero no se pudo finalizar");
					}
					
			} catch (Exception e) {
				Alerta.Alerta(request, "La incidencia indicada no tiene ningun trabajo hecho", "error");
				Escritura.EscribirFichero("Hubo un error en finalizar la incidencia "+e.getLocalizedMessage());
				try {
					response.sendRedirect("home.jsp");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
	 }
}
