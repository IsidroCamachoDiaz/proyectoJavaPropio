package Controladores;

import java.io.IOException;

import Dtos.UsuarioDTO;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControladorEliminarUsuario extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		
			try {
					implementacionCRUD acciones = new implementacionCRUD();
					String idUsuarioEliminar= request.getParameter("id");
					//Crearmos el DTO con los paraemos pasados y usando el metodos de encriptar
					UsuarioDTO usuario = acciones.SeleccionarUsuario("Select/"+idUsuarioEliminar);
					if(usuario==null) {
						Alerta.Alerta(request, "No se encontro al usuario", "error");
					}
					
					//USamos la implementacion
					ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
					
					//Comprobamos si esta bien el usuario
					if(cosa.eliminarUsuario(usuario, request)) {
						response.sendRedirect("gestionUsuarios.jsp");							
					}
					else {
						Alerta.Alerta(request, "No se pudo borrar al usuario intentelo mas tarde", "error");
						response.sendRedirect("home.jsp");
					}
					
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
}
