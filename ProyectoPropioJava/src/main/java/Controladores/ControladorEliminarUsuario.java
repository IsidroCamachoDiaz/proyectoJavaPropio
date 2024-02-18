package Controladores;

import java.io.IOException;

import Dtos.UsuarioDTO;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.Escritura;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControladorEliminarUsuario extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 * Metodo que se usa en el formulario para coger los valores
	 * y comprobarlos para eliminar un Usuario en la gestion de adminsitradores
	 * @param request
	 * @param response
	 * 
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {		
		
			try {
					//Declaramos lo que necesitemos
					implementacionCRUD acciones = new implementacionCRUD();
					String idUsuarioEliminar= request.getParameter("id");
					
					//Cogemos el usuario de la base de datos
					UsuarioDTO usuario = acciones.SeleccionarUsuario("Select/"+idUsuarioEliminar);
					
					//Comprobamos si se encontro
					if(usuario==null) {
						Alerta.Alerta(request, "No se encontro al usuario", "error");
						Escritura.EscribirFichero("Un adminsitardor quiso eliminar un usuario pero no se encontro el usuario");
					}
					
					//USamos la implementacion
					ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
					
					//Comprobamos si esta bien el usuario
					if(cosa.eliminarUsuario(usuario, request)) {
						response.sendRedirect("gestionUsuarios.jsp");
						Escritura.EscribirFichero("Un administrador elimino un usuario "+usuario.getNombreUsuario());
					}
					//Si no se pudo elimiinar se avisa al usuario
					else {
						Alerta.Alerta(request, "No se pudo borrar al usuario intentelo mas tarde", "error");
						response.sendRedirect("home.jsp");
						Escritura.EscribirFichero("Unadministrador quiso eliminar usuario pero no se pudo eliminar");
					}
					
			} catch (Exception e) {
				e.printStackTrace();
				Escritura.EscribirFichero("Hubo un error en eliminar usuario "+e.getLocalizedMessage());
			}
	 }
}
