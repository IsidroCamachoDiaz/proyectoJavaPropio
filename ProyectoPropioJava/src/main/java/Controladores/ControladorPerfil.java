package Controladores;

import java.io.IOException;
import java.io.InputStream;

import Dtos.UsuarioDTO;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.implementacionCRUD;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class ControladorPerfil extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
	     
		 	String id=request.getParameter("id");
		 	implementacionCRUD acciones = new implementacionCRUD ();
		 	String nombre=request.getParameter("nombre");
		 	String telefono=request.getParameter("telefono");
		 	boolean cambio=false;
	        UsuarioDTO usuarioCambio=acciones.SeleccionarUsuario("Select/"+id);
	        if(!usuarioCambio.getNombreUsuario().equals(usuarioCambio.getNombreUsuario())) {
	        	usuarioCambio.setNombreUsuario(nombre);
	        	cambio=true;
	        }
	        if(!usuarioCambio.getTlfUsuario().equals(telefono)) {
	        	usuarioCambio.setTlfUsuario(telefono);
	        	cambio=true;
	        }
	        
	        Part filePart;
			try {
				filePart = request.getPart("imagen");
				  if (filePart.getSize() > 0) {
				        InputStream fileContent = filePart.getInputStream();
				        // Convertir InputStream a byte[]
				        usuarioCambio.setFoto(fileContent.readAllBytes());
				        cambio=true;
				  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cambio) {
				acciones.ActualizarUsuario(usuarioCambio);
			}
			else {
				Alerta.Alerta(request, "No hizo ninguna modificacion", "warning");
			}	        	             	        	    
	    }
}
