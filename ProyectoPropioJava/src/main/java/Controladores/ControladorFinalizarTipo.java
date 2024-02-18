package Controladores;

import java.io.IOException;
import java.util.Calendar;

import Dtos.TipoTrabajoDTO;
import Dtos.UsuarioDTO;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.Escritura;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControladorFinalizarTipo extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		
			try {
				//Declaramos loq eu necesitemos
					implementacionCRUD acciones = new implementacionCRUD();
					String idTipoFinalizar= request.getParameter("id");
					
					//Buscamos el tipo de trabajo que se quiere finalizar
					TipoTrabajoDTO trabajo = acciones.SeleccionarTipoDeTrabajo("Select/"+idTipoFinalizar);
					if(trabajo==null) {
						Alerta.Alerta(request, "No se encontro el tipo", "error");
						Escritura.EscribirFichero("Un usuario quiso finalizar un tipo de trabajo pero no se encontro el tipo de trabajo");
					}
					
					//Le asigamos al fecha fin
					trabajo.setFecha_fin(Calendar.getInstance());					
					
					//Comprobamos si e finalizo bien el tipo de incidencia
					if(acciones.ActualizarTipoDeTrabajo(trabajo)) {
						Alerta.Alerta(request, "Se Finalizo correctamente la solicitud", "success");
						response.sendRedirect("vistas/home.jsp");
						Escritura.EscribirFichero("Un usuario finalizo una incdenia correctamente");
					}
					//Si no se finaliza se avisa al usuario
					else {
						Alerta.Alerta(request, "No se pudo finalizar el tipo de trabajo intentelo mas tarde", "error");
						response.sendRedirect("vistas/home.jsp");	
						Escritura.EscribirFichero("Un usuario quiso finalizar un tipo de trabajo pero no se pudo finalizar");
					}
					
			} catch (Exception e) {
				Alerta.Alerta(request, "Hubo un error intentelo mas tarde", "error");
				Escritura.EscribirFichero("Hubo un erroe en finalizar Tipo de trabajo "+e.getLocalizedMessage());
				try {
					response.sendRedirect("vistas/home.jsp");
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
			}
	 }
}
