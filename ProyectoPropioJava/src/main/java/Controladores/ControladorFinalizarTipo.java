package Controladores;

import java.io.IOException;
import java.util.Calendar;

import Dtos.TipoTrabajoDTO;
import Dtos.UsuarioDTO;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControladorFinalizarTipo extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		
			try {
					implementacionCRUD acciones = new implementacionCRUD();
					String idTipoFinalizar= request.getParameter("id");
					
					//Crearmos el DTO con los paraemos pasados y usando el metodos de encriptar
					TipoTrabajoDTO trabajo = acciones.SeleccionarTipoDeTrabajo("Select/"+idTipoFinalizar);
					if(trabajo==null) {
						Alerta.Alerta(request, "No se encontro el tipo", "error");
					}
					trabajo.setFecha_fin(Calendar.getInstance());
					
					
					
					//Comprobamos si esta bien el usuario
					if(acciones.ActualizarTipoDeTrabajo(trabajo)) {
						Alerta.Alerta(request, "Se Finalizo correctamente la solicitud", "success");
						response.sendRedirect("vistas/home.jsp");							
					}
					else {
						Alerta.Alerta(request, "No se pudo finalizar el tipo de trabajo intentelo mas tarde", "error");
						response.sendRedirect("vistas/home.jsp");	
					}
					
			} catch (IOException e) {
				Alerta.Alerta(request, "Hubo un error intentelo mas tarde", "error");
				try {
					response.sendRedirect("vistas/home.jsp");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
	 }
}
