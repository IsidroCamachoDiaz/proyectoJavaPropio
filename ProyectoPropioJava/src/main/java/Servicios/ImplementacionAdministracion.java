package Servicios;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import Dtos.AccesoDTO;
import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.UsuarioDTO;
import Utilidades.Alerta;
import Utilidades.Correo;
import Utilidades.Escritura;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

/**
* Clase para implentar la interfaz de la administracion
* 
*/
public class ImplementacionAdministracion implements InterfaceAdministracion {
	
     public boolean CambiarAcceso(UsuarioDTO usuario,HttpServletRequest request,String idAcceso) {
    	 //Declaramos lo que necesitemos
    	 implementacionCRUD acciones = new implementacionCRUD();
    	 
    	 //Buscamos el acceszo que se le quiere dar
    	 AccesoDTO accesoDar=acciones.SeleccionarAcceso("Select/"+idAcceso);
    	 //Cogemso todas las solicitudes
    	 List <SolicitudDTO>  solicitudes = acciones.SeleccionarTodasSolicitudes();
    	 
    	 //Las filtramos por la del usuario
		 solicitudes = solicitudes.stream()
	                .filter(solicitud -> solicitud.getUsuarioSolicitud().getIdUsuario() == usuario.getIdUsuario())
	                .collect(Collectors.toList());
    	 
		 //Comprobamos que usuario se le va a dar
    	 if(accesoDar.getCodigoAcceso().equals("Administrador")) {
    		 //Si es el empleado se le da porque solo se le añade la gestion de usaurios
    		 if(usuario.getAcceso().getCodigoAcceso().equals("Empleado")) {
    			 usuario.setAcceso(accesoDar);
    			 Escritura.EscribirFichero("Se le cambio el acceso a un usuario "+usuario.getNombreUsuario());
     			return true;
    		 }
    		 //Si es un usuario se comprueba si tiene solicitudes activas
    		 else {
    			 //Si no tiene se le da
	    		if(solicitudes.isEmpty()) {  		
	    			usuario.setAcceso(accesoDar);
	    			Escritura.EscribirFichero("Se le cambio el acceso a un usuario "+usuario.getNombreUsuario());
	    			return true;
	    		}
	    		//Si tiene solicitudes no se le da
	    		Alerta.Alerta(request, "No se puede cambiar un usuario que tienen solicitudes de atencion al cliente", "error");
	    		Escritura.EscribirFichero("No se puede cambiar un usuario que tienen solicitudes de atencion al cliente");
	    		return false;
    		 }
    	 }
    	 //Si se quiere dar empleado
    	 else if(accesoDar.getCodigoAcceso().equals("Empleado")) {
    		 //Si es administrador se le da directamente porque el adminsitracion tiene el plus de gestion de ussuarios
    		 if(usuario.getAcceso().getCodigoAcceso().equals("Administrador")) {
    			 usuario.setAcceso(accesoDar);
    			 Escritura.EscribirFichero("Se le cambio el acceso a un usuario "+usuario.getNombreUsuario());
     			return true;
    		 }
    		 //Si es un usuario se comprueba si tiene solicitudes
    		 else {
			 //Si no tiene solicitudes se le da el rol
	    		if(solicitudes.isEmpty()) {  		
	    			usuario.setAcceso(accesoDar);
	    			Escritura.EscribirFichero("Se le cambio el acceso a un usuario "+usuario.getNombreUsuario());
	    			return true;
	    		}
	    		//Si tiene solicitudes activas no se le da
	    		Alerta.Alerta(request, "No se puede cambiar un usuario que tienen solicitudes de atencion al cliente", "error");
	    		 Escritura.EscribirFichero("No se puede cambiar un usuario que tienen solicitudes de atencion al cliente "+usuario.getNombreUsuario());
	    		return false;
    		 }
    		 
    	 }
    	 //Si quiere ser usuario
    	 else {
    		 //Cogemos las incidencias y se filtran por las del el usuario
    		 List <IncidenciaDTO> incidencias = acciones.SeleccionarTodasIncidencias();
    		 
    		 incidencias = incidencias.stream()
 	                .filter(incidencia -> incidencia.getEmpleado().getIdUsuario() == usuario.getIdUsuario())
 	                .collect(Collectors.toList());
    		 //Si no tiene ninguna incidencia se le da el rol de usuario
    		 if(incidencias.isEmpty()) {
    			 usuario.setAcceso(accesoDar);
    			 Escritura.EscribirFichero("Se le cambio el acceso a un usuario "+usuario.getNombreUsuario());
	    		return true; 
    		 }
    		 //Si tiene incidencia no se le da
    		 Alerta.Alerta(request, "No se puede cambiar un usuario que tienen solicitudes de atencion al cliente", "error");
    		 Escritura.EscribirFichero("No se puede cambiar un usuario que tienen solicitudes de atencion al cliente "+usuario.getNombreUsuario());
    		 return false;
    			
    	 }
     }
     
     @Override
 	public boolean CrearUsuario(UsuarioDTO usu,HttpServletRequest request) {
 		
 		try{
 			Properties seguridad = new Properties();
				seguridad.load(ImplementacionAdministracion.class.getResourceAsStream("/Utilidades/parametros.properties"));
 			implementacionCRUD acciones = new implementacionCRUD();
 			UsuarioDTO usuarioSiHay =acciones.SeleccionarUsuario("SelectCorreo/"+usu.getEmailUsuario());
 			if(usuarioSiHay!=null) {
 				Alerta.Alerta(request, "Ya existe una cuenta con ese correo", "error");
 				Escritura.EscribirFichero("Una persona intento registarse pero ya hay una cuenta con ese correo");
 				return false;
 			}
 			else {
 				acciones.InsertarUsuario(usu);
 	            
 	            UsuarioDTO usuId=acciones.SeleccionarUsuario("SelectCorreo/"+usu.getEmailUsuario());
 	            Correo correo=new Correo();
 	            
 	           String mensaje=correo.MensajeCorreo(usu.getNombreUsuario());
 	           
 				boolean ok=correo.EnviarMensaje(mensaje,usu.getEmailUsuario(),true,"Bienvenido",seguridad.getProperty("correo"),true);
 	            if(ok) {
 	            	return true;
 	            }
 	            else {
 	            	Alerta.Alerta(request, "No se pudo mandar el correo", "error");
 	            	return false;
 	            }
 			}	            
 			}catch (IllegalStateException e) {
 				Escritura.EscribirFichero("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Ya esta uso el metodo para insertar el usuario. |"+e);
 				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Ya esta uso el metodo para insertar el usuario. |"+e);
 			}catch (NullPointerException e) {
 				Escritura.EscribirFichero("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Hay un componente nulo. |"+e);
 				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Hay un componente nulo. |"+e);
 			}catch (SecurityException e) {
 				Escritura.EscribirFichero("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] El gerente de seguridad para indicar una violación de seguridad. |"+e);
 				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] El gerente de seguridad para indicar una violación de seguridad. |"+e);
 			}catch (IndexOutOfBoundsException e) {
 				Escritura.EscribirFichero("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Algun tipo (como una matriz, una cadena o un vector) está fuera de rango. |"+e);
 				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Algun tipo (como una matriz, una cadena o un vector) está fuera de rango. |"+e);
 			}catch(IOException e) {
 				Escritura.EscribirFichero("[ERROR-ImplentacionAdministracion-Crear]Hubo error en la implemnetacion de properties"+e);
 				System.err.println("[ERROR-ImplentacionAdministracion-Crear]Hubo error en la implemnetacion de properties"+e);
 			}
 	return false;
 	}
}