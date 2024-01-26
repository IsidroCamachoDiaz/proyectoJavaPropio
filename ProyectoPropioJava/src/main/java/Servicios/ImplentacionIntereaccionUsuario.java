package Servicios;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Dtos.TokenDTO;
import Dtos.UsuarioDTO;
import Utilidades.Alerta;
import Utilidades.Correo;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;






/**
 * Clase para implentar la interfez de register,login del usuario que implenta de la interfaz
 * 
 */

public class ImplentacionIntereaccionUsuario implements InterfaceIntereccionUsuario {
	
	public boolean IniciarSesion(UsuarioDTO user,HttpServletRequest request) {
		
		
		try {
			//Se le pasa la url
				
				UsuarioDTO usuarioBD;
		        HttpSession session = request.getSession();
		        implementacionCRUD acciones = new implementacionCRUD();
		        
		        //Cogemos el usuario de la base de datos
		        try {
				usuarioBD =acciones.SeleccionarUsuario("SelectCorreo/"+user.getEmailUsuario());
				if(usuarioBD==null) {
					throw new RuntimeException();
				}
		        }catch(RuntimeException e) {
		        	Alerta.Alerta(request,"El Correo y/o contraseña es incorrecto", "error");
		        	return false;
		        }
				//Si no es igual lo manda al login con aviso
	            if(user.getClaveUsuario().equals(usuarioBD.getClaveUsuario())) {
	            	//Comprobamos is verifico la cuenta
	            	if(usuarioBD.getAcceso().getCodigoAcceso().equals("Pendiente")) {
	            		Alerta.Alerta(request,"El usuario no esta dado de alta en la web", "error");
	            		return false;
	            	}else {
	            		//Asignamos el usuario y el control de acceso de vada usuario
	                	session.setAttribute("usuario",usuarioBD);
	            		if(usuarioBD.getAcceso().getCodigoAcceso().equals("Usuario")) {
	            			session.setAttribute("acceso","1");
	            		}
	            		else if(usuarioBD.getAcceso().getCodigoAcceso().equals("Empleado")) {
	            			session.setAttribute("acceso","2");
	            		}
	            		else {
	            			session.setAttribute("acceso","3");
	            		}
	            		return true;
	            	}
	            }
	            else {
					Alerta.Alerta(request,"El DNI y/o Clave son incorrectos","error");
	            	return false;
	            }
		}catch(Exception e) {
			Alerta.Alerta(request,"Hubo un error intentelo mas tarde","error");
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}
	
	@Override
	public boolean RegistrarUsuario(UsuarioDTO usu,HttpServletRequest request) {
		
		try{
			implementacionCRUD acciones = new implementacionCRUD();
			UsuarioDTO usuarioSiHay =acciones.SeleccionarUsuario("SelectCorreo/"+usu.getEmailUsuario());
			if(usuarioSiHay!=null) {
				Alerta.Alerta(request, "Ya existe una cuenta con ese correo", "error");
				return false;
			}
			else {
				acciones.InsertarUsuario(usu);
	            
	            UsuarioDTO usuId=acciones.SeleccionarUsuario("SelectCorreo/"+usu.getEmailUsuario());
	            Correo correo=new Correo();
	            if(correo.EnviarCorreoToken(usuId)) {
	            	return true;
	            }
	            else {
	            	return false;
	            }
			}	            
			}catch (IllegalStateException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Ya esta uso el metodo para insertar el usuario. |"+e);
			}catch (NullPointerException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Hay un componente nulo. |"+e);
			}catch (SecurityException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] El gerente de seguridad para indicar una violación de seguridad. |"+e);
			}catch (IndexOutOfBoundsException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Algun tipo (como una matriz, una cadena o un vector) está fuera de rango. |"+e);
			}
			
	return false;
	}
	@Override
	public boolean OlvidarClaveUsuario(String correo) {
		boolean ok=false;
		try {
			Properties seguridad = new Properties();
			seguridad.load(ImplentacionIntereaccionUsuario.class.getResourceAsStream("/Utilidades/parametros.properties"));
			//Aqui tiene que ir el buscar el usuario por el correo
			UsuarioDTO usuarioCorreo=HacerGets("SelectCorreo/"+correo);
			if(usuarioCorreo.getNombreUsuario()==null||usuarioCorreo.getNombreUsuario().equals("")) {
				return false;
			}
			//Meter en un if si el usuario se encontro que haga el resto (tiene que llegar hasta ok=EnviarMensaje...)
			UUID uuid = UUID.randomUUID();
			String token = uuid.toString();
			//Aqui generas la fecha limite con un tiempo de 10 minutos
			TokenDTO tokenMandarBD= new TokenDTO();
			tokenMandarBD.setFch_limite(new GregorianCalendar());
			tokenMandarBD.getFch_limite().add(Calendar.MINUTE, 10);
			tokenMandarBD.setToken(token);
			//Aqui una vez encuentre el usuario insertas el token generado arriba y 
			//lo inserta con la fecha limite, id_Usuario
			Correo correo2= new Correo();
			 String mensaje=correo2.MensajeCorreo(token,seguridad.getProperty("direccion"));
			 ok=correo2.EnviarMensaje(mensaje,correo,true,"Recuperar Contraseña",seguridad.getProperty("correo"),true);
		}catch(IOException e)
		{
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] No se pudo leer el .properties. |"+e);
			return false;
		}
		catch(NullPointerException e)
		{
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] El .properties es nulo. |"+e);
			return false;
		}
		return ok;
	}
	
	/**
	 * Metodo para hacer consultas
	 * @param queDar
	 * @return UsuarioDTO devuelve un objeto usuario
	 */
	private UsuarioDTO HacerGets(String queDar)
	{
		try{
			
			URL url = new URL("http://localhost:8080/usuario/"+queDar);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");

	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        ObjectMapper objectMapper = new ObjectMapper();
	        String line;
	        UsuarioDTO usuarios = null;
	        while ((line = reader.readLine()) != null) {
	        	usuarios = objectMapper.readValue(line, new TypeReference<UsuarioDTO>() {});
	        }
	        
	        return usuarios;
		} catch (JsonProcessingException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-HacerGets] El objeto UsuarioDto no se pudo convertir a json. |"+e);
		} catch (IOException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-HacerGets] Se produjo un error al crear el flujo de salida. |"+e);
		}
		
		return null;
	}
	/**
	 * Metodo para hacer update a usuario
	 * @param usuario
	 * @return
	 */
	    private boolean hacerUpdate(UsuarioDTO usuario) {
	        try {
	            // Construir la URL para la solicitud PUT
	            URL url = new URL("http://localhost:8080/usuarioApi/" + usuario.getEmailUsuario());
	            
	            // Abrir la conexión HTTP
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("PUT");
	            connection.setRequestProperty("Content-Type", "application/json");
	            connection.setDoOutput(true);

	            // Convertir el objeto UsuarioDTO a JSON
	            ObjectMapper objectMapper = new ObjectMapper();
	            String jsonInputString = objectMapper.writeValueAsString(usuario);

	            // Escribir el JSON en la solicitud
	            try (OutputStream outputStream = connection.getOutputStream()) {
	                byte[] input = jsonInputString.getBytes("utf-8");
	                outputStream.write(input, 0, input.length);
	            }

	            // Verificar el código de respuesta
	            int responseCode = connection.getResponseCode();
	            return responseCode == HttpURLConnection.HTTP_OK;

	        } catch (IOException e) {
	            // Manejar cualquier excepción de IO
	            e.printStackTrace();
	            return false;
	        }
	    }
	@Override
	public boolean actualizarContrasena(String token, String clave1) {
		 try {
       	  
	            // Obtener el usuario actual
	            UsuarioDTO usuarioActual = HacerGets(String.valueOf(token));

	            // Verificar si se obtuvo el usuario
	            if (usuarioActual != null) {
	                // Actualizar la contraseña en el objeto UsuarioDTO
	                usuarioActual.setClaveUsuario(clave1);

	                // Realizar la actualización en el sistema externo
	                return hacerUpdate(usuarioActual);
	            } else {
	                System.out.println("No se pudo obtener el usuario para actualizar la contraseña.");
	                return false;
	            }

	        } catch (Exception e) {
	            // Manejar cualquier excepción
	            e.printStackTrace();
	            return false;
	        }
	}


}

