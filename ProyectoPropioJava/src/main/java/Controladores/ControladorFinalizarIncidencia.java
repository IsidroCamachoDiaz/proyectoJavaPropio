package Controladores;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.UsuarioDTO;
import Servicios.ImplementacionInteraccionIncedencias;
import Utilidades.Alerta;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControladorFinalizarIncidencia extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		
			try {
					implementacionCRUD acciones = new implementacionCRUD();
					ImplementacionInteraccionIncedencias inter= new ImplementacionInteraccionIncedencias();
					
					String idUsuario= request.getParameter("id");
					String idUsuarioIncidencia= request.getParameter("idI");
					
					//Creamos el DTO con los paraemos pasados y usando el metodos de encriptar
					UsuarioDTO usuario = acciones.SeleccionarUsuario("Select/"+idUsuario);
					IncidenciaDTO incidenciaAsignar= acciones.SeleccionarIncidencia("Select/"+idUsuarioIncidencia);
					
					//Para asignar las incidencias con sus solicitudes
					List <SolicitudDTO> solicitudes = acciones.SeleccionarTodasSolicitudes();
					
					for(SolicitudDTO s:solicitudes) {
						if(s.getIncidenciaSolicitud().getId_incidencia()==incidenciaAsignar.getId_incidencia()) {
							incidenciaAsignar.setSolicitud(s);
							break;
						}
					}
					
					
					if(usuario==null||incidenciaAsignar==null) {
						Alerta.Alerta(request, "No se encontro al usuario o la incidencia", "error");
						response.sendRedirect("index.jsp");
					}
										
					//Comprobamos si esta bien el usuario
					if(inter.FinalizarIncidencia(incidenciaAsignar, request)) {
						Alerta.Alerta(request, "Se Finalizo la Incidencia Correctamente", "success");
						response.sendRedirect("home.jsp");							
					}
					else {				
						response.sendRedirect("home.jsp");
					}
					
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
}
