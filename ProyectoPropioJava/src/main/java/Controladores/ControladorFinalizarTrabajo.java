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

public class ControladorFinalizarTrabajo extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 * Metodo que se usa en el formulario para coger los valores
	 * y comprobarlos para Finalizar un trabajo
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
					String idTrabajo= request.getParameter("idT");
					
					//Creamos el DTO con los paraemos pasados y usando el metodos de encriptar
					TrabajoDTO trabajoFinalizar= acciones.SeleccionarTrabajo("Select/"+idTrabajo);
					
										
					//Comprobamos si es null algo
					if(trabajoFinalizar==null) {
						Alerta.Alerta(request, "No se encontro el trabajo que se quiere eliminar", "error");
						response.sendRedirect("vistas/mostrarTrabajos.jsp");
						Escritura.EscribirFichero("Un usuario quiso finalizar un trabajo pero no se encontro el trabajo en la base de datos");
					}
																				
					//Comprobamos si lo finaliza bien
					if(inter.FinalizarTrabajo(trabajoFinalizar, request)) {
						Alerta.Alerta(request, "Se Finalizo el Trabajo Correctamente", "success");
						response.sendRedirect("vistas/mostrarTrabajos.jsp");
						Escritura.EscribirFichero("Un usuario finalizo un trabajo");
					}
					//Si no conseigue finalizarlo se avisa al usuario
					else {
						response.sendRedirect("vistas/mostrarTrabajos.jsp");
						Escritura.EscribirFichero("Un usuario quiso finalizar un trabajo pero no se pudo finalizar");
					}
					
			} catch (Exception e) {
				Alerta.Alerta(request, "No se pudo finalizar el trabajo intentelo mas tarde", "error");
				Escritura.EscribirFichero("Hubo un error en finalizar el trabajo "+e.getLocalizedMessage());
				try {
					response.sendRedirect("vistas/mostrarTrabajos.jsp");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
	 }
}
