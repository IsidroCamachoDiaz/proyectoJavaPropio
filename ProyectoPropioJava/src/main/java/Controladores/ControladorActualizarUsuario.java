package Controladores;

import java.io.IOException;
import java.io.InputStream;

import Dtos.UsuarioDTO;
import Servicios.ImplementacionAdministracion;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.ComprobacionImagen;
import Utilidades.implementacionCRUD;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

//Aqui va el controlador es para el formulario para restablecer enviar el correo
@MultipartConfig
public class ControladorActualizarUsuario extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
    	try {
    		
    	boolean cambio=false;
    	
        String idUsuario=request.getParameter("id");
	 	String nombre=request.getParameter("nombre");
	 	String telefono=request.getParameter("telefono");
	 	String acceso=request.getParameter("acceso");
	 	
	    ImplementacionAdministracion inter = new ImplementacionAdministracion();
	 	
	 	implementacionCRUD acciones = new implementacionCRUD();
	 	UsuarioDTO usuarioCambiar = acciones.SeleccionarUsuario("Select/"+idUsuario);
	 	
	 	if(usuarioCambiar==null) {
	 		Alerta.Alerta(request, "No se encontro al usuario", "error");
				response.sendRedirect("vistas/home.jsp");
	 	}
	 	
	 	if(!nombre.equals(usuarioCambiar.getNombreUsuario())||!telefono.equals(usuarioCambiar.getTlfUsuario())
	 			||!acceso.equals(String.valueOf(usuarioCambiar.getAcceso().getIdAcceso()))) {
	 		
	 		if(!acceso.equals(String.valueOf(usuarioCambiar.getAcceso().getIdAcceso()))) {
	 			if(inter.CambiarAcceso(usuarioCambiar, request, acceso)) {
	 			}
	 			else {
	 				response.sendRedirect("vistas/home.jsp");
	 			}
	 		}
	 		usuarioCambiar.setNombreUsuario(nombre);
	 		usuarioCambiar.setTlfUsuario(telefono);
	 		cambio=true;
	 		
	 	}
	 	//Cojo la imagen
        Part filePart;
			filePart = request.getPart("imagen");
			
			ComprobacionImagen com=new ComprobacionImagen();
			
			 //Comprobamos si ha metido una foto
			  if (filePart.getSize() > 0) {
				  
				  if(com.esArchivoImagen(filePart)) {
					  InputStream fileContent = filePart.getInputStream();
				        // Convertir InputStream a byte[]
				        usuarioCambiar.setFoto(fileContent.readAllBytes());
				        cambio=true;
				  }
				  //Si no metio algo que sea una foto se pone una por defecto
				  else {
					  try (InputStream defaultImageStream = getClass().getResourceAsStream("/user.png")) {
					        if (defaultImageStream != null) {
					            // Convertir InputStream a byte[]
					            usuarioCambiar.setFoto(defaultImageStream.readAllBytes());
					            cambio=true;
					        } 
					    } catch (IOException e) {
					        // Manejar la excepción de entrada/salida si es necesario
					        e.printStackTrace();
					    }
				  }
			        
			  }
			
			
			if(cambio) {
				acciones.ActualizarUsuario(usuarioCambiar);
				Alerta.Alerta(request, "Se modifico correctamente el usuario "+usuarioCambiar.getNombreUsuario(), "success");
				response.sendRedirect("vistas/gestionUsuarios.jsp");
			}
			//Si no ha cambiado se le avisa
			else {
				Alerta.Alerta(request, "No hizo ninguna modificacion", "warning");
				response.sendRedirect("vistas/gestionUsuarios.jsp");
			}	
        
    	}catch (IOException e) {
			e.printStackTrace();
		}catch (ServletException e) {
			e.printStackTrace();
		}
    	catch(Exception e) {	
    	}
    }

}
