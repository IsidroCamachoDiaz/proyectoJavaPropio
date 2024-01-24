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
				URL url = new URL("http://localhost:8080/usuarioApi/usuarioSelect/usuarioDni/"+user.getEmailUsuario());
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        
		        HttpSession session = request.getSession();
		        
		        //Se le indica el metodo
		        connection.setRequestMethod("GET");
		        connection.setRequestProperty("Content-Type", "application/json");
		        connection.setDoOutput(true);
		        
		        UsuarioDTO usuarioBD;
		        
		        //Comprobamos si esta correcto la url
				
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					//Creamos el lectpr
		            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		            String linea;
		            StringBuilder response = new StringBuilder();
		            
		            // Crear un ObjectMapper (Jackson)
		            ObjectMapper objectMapper = new ObjectMapper();
		            
		            //Pasamos el json
		            linea = reader.readLine();
		            reader.close();          
		            if(linea.isEmpty())
		            	return false;
		            
		            	// Convertir el JSON a un objeto MiObjeto
		            System.out.println("JSON recibido: " + linea);
		            //Lo convertimos a DTO
		            
		                usuarioBD=objectMapper.readValue(linea, UsuarioDTO.class);

		            if(user.getClaveUsuario().equals(usuarioBD.getClaveUsuario())) {
	                	session.setAttribute("usuario",usuarioBD);
		                if(usuarioBD.getAcceso().getCodigoAcceso().equals("Administrador")) {

							session.setAttribute("acceso","2");
						}
						else {
							session.setAttribute("acceso","1");
						}
		            	return true;
		            }
		            else
		            	return false;
		        } else {
		            System.out.println("La solicitud GET no fue exitosa. Código de respuesta: " + connection);
		        }
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			return false;
		}
		return false;
			}
	
	@Override
	public boolean RegistrarUsuario(UsuarioDTO usu) {
		
		try{
			implementacionCRUD acciones = new implementacionCRUD();
			boolean ok=false;
				
				ObjectMapper objectMapper = new ObjectMapper();

					String usuarioJson = objectMapper.writeValueAsString(usu);
		            URL url = new URL("http://localhost:8080/usuario/Insertar");

		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		            
		            connection.setRequestMethod("POST");
		            connection.setRequestProperty("Content-Type", "application/json");
		            connection.setDoOutput(true);

		            try (OutputStream os = connection.getOutputStream()) {
		    		    byte[] input = usuarioJson.getBytes("utf-8");
		    		    os.write(input, 0, input.length);
		    		}
		            if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
		                // Si la respuesta es HTTP_CREATED (201), se ha creado correctamente
		                System.out.println("Usuario creado exitosamente");
		            } else {
		                // Si no es HTTP_CREATED, imprime la respuesta para depurar
		                System.out.println("Respuesta del servidor: " + connection.getResponseCode() + " " + connection.getResponseMessage());
		            }
		            
		            UsuarioDTO usuId=acciones.SeleccionarUsuario("SelectCorreo/"+usu.getEmailUsuario());
		            try {
						Properties seguridad = new Properties();
						seguridad.load(ImplentacionIntereaccionUsuario.class.getResourceAsStream("/Utilidades/parametros.properties"));
						//Aqui tiene que ir el buscar el usuario por el correo
						//Meter en un if si el usuario se encontro que haga el resto (tiene que llegar hasta ok=EnviarMensaje...)
						
						//Aqui generas la fecha limite con un tiempo de 10 minutos
						//Aqui una vez encuentre el usuario insertas el token generado arriba y 
						//lo inserta con la fecha limite, id_Usuario
						
						UUID uuid = UUID.randomUUID();
						String token = uuid.toString();
						//Fecha Limite
						Calendar fechaLimite=Calendar.getInstance();
						fechaLimite.add(Calendar.MINUTE, 10);
						
						//Creo token
						TokenDTO tk= new TokenDTO(token,fechaLimite,usuId.getIdUsuario());
						if(acciones.InsertarToken(tk)) {
							String mensaje=MensajeCorreoAlta(token);
							 ok=EnviarMensaje(mensaje,usu.getEmailUsuario(),true,"Solicitud De Alta",seguridad.getProperty("correo"),true);
						}
						else {
						//Aqui Falla
						}
						 
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
		            
			 return true;
			} catch (JsonProcessingException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] El objeto UsuarioDto no se pudo convertir a json. |"+e);
			} catch (IOException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Se produjo un error al crear el flujo de salida. |"+e);
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
			 String mensaje=MensajeCorreo(token,seguridad.getProperty("direccion"));
			 ok=EnviarMensaje(mensaje,correo,true,"Recuperar Contraseña",seguridad.getProperty("correo"),true);
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
	 * Metodo donde crea el cuerpo del correo
	 * @param token
	 * @param direccion
	 * @return String
	 */
	private String MensajeCorreo(String token, String direccion) {
	    return "<div style=\"text-align: center; background-color: #7d2ae8; padding: 20px;\">\r\n"
	            + "    <p style=\"color: white;\">Se ha enviado una solicitud para restablecer la contraseña. Si no has solicitado esto, cambia tu contraseña de inmediato.</p>\r\n"
	            + "    <p style=\"color: white;\">Si has solicitado el restablecimiento de contraseña, por favor haz clic en el siguiente enlace:</p>\r\n"
	            + "    <a href=\"" + direccion + "?tk=" + token + "\"><button style=\"background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px;\">Restablecer contraseña</button></a>\r\n"
	            + "    <p style=\"color: white;\">Gracias por confiar en nosotros.</p>\r\n"
	            + "</div>";
	}
	
	private String MensajeCorreoAlta(String direccion) {
	    return "<div style=\"text-align: center; background-color: #7d2ae8; padding: 20px;\">\r\n"
	            + "    <p style=\"color: white;\">Bienvenido a nuestra plataforma. Tu cuenta ha sido creada con éxito.</p>\r\n"
	            + "    <p style=\"color: white;\">Para comenzar a utilizar nuestros servicios, por favor, haz clic en el siguiente enlace:</p>\r\n"
	            + "    <a href=\"http://localhost:8081/ProyectoGetPa/altaHecha.jsp?tk=" + direccion + "\"><button style=\"background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px;\">Activar cuenta</button></a>\r\n"
	            + "    <p style=\"color: white;\">Gracias por unirte a nosotros.</p>\r\n"
	            + "</div>";
	}
	/**
	 * Metodo para enviar correo al usuario introducido
	 * @param body
	 * @param to
	 * @param html
	 * @param subject
	 * @param frommail
	 * @param cco
	 * @return boolean
	 */
	private boolean EnviarMensaje(String body, String to, boolean html, String subject,String frommail, boolean cco)  {
		boolean resultado = true;
		Transport tp = null;
		try {
			Properties seguridad = new Properties();
			seguridad.load(
					ImplentacionIntereaccionUsuario.class.getResourceAsStream("/Utilidades/parametros.properties"));

			// Parametros de coneccion con un correo de ionos
			String host = "smtp.ionos.es";
			String miLogin = seguridad.getProperty("usuarioCorreo");
			String miPassword = seguridad.getProperty("contraCorreo");
			InternetAddress addressFrom;
			InternetAddress[] addressReply = new InternetAddress[1];

			// Obtener propiedades del sistema
			Properties properties = System.getProperties();

			// Configurar servidor de correo
			properties.setProperty("mail.smtp.host", host);
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.port", "587");
			properties.setProperty("mail.smtp.user", miLogin);
			properties.setProperty("mail.smtp.auth", "true");

			// Obtenga el objeto de sesión predeterminado.
			Session session2 = Session.getInstance(properties);
			
			//Configura los parametros para conectarse
			Message msg = new MimeMessage(session2);
			
			//La direccion de quien lo envia
			addressFrom = new InternetAddress("'SystemRevive' <" + seguridad.getProperty("correo") + ">");
			msg.setFrom(addressFrom);
			
			//A quien envia el correo
			addressReply[0] = new InternetAddress(frommail);
			msg.setReplyTo(addressReply);
			InternetAddress addressTo = new InternetAddress();
			addressTo = new InternetAddress(to);

			InternetAddress[] addressToOK = new InternetAddress[1];
			addressToOK[0] = addressTo;
			//Establece la dirreccion a quien lo envia
			msg.setRecipients(RecipientType.TO, addressToOK);
			if (cco)
				msg.addRecipients(RecipientType.BCC, addressReply);
			//Establece el asunto de este mensaje.
			msg.setSubject(subject);
			//Construye el body
			if (html) {
				body = body;
				msg.setContent(body, "text/html; charset=ISO-8859-1");
			} else {
				msg.setContent(body, "text/plain; charset=ISO-8859-1");
			}
			//Envia el mensaje
			tp = session2.getTransport("smtp");
			tp.connect((String) properties.get("mail.smtp.user"), miPassword);
			tp.sendMessage(msg, msg.getAllRecipients());

		} catch (AddressException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-EnviarMensaje]  Falló en el análisis. |" + e);
			resultado = false;
		} catch (SecurityException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-EnviarMensaje] No se permite el acceso a la propiedad. |"+ e);

		} catch (NullPointerException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-EnviarMensaje] El .properties es nulo. |" + e);
			resultado = false;
		} catch (SendFailedException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] La direccion es no valida|" + e);
			resultado = false;
		} catch (IllegalWriteException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] No se admite la modificación de los valores existentes en la implementación subyacente. |"+ e);
			resultado = false;
		} catch (IllegalStateException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] El mensaje se intenta optener de una carpeta READ_ONLY. |"+ e);
			resultado = false;
		} catch (NoSuchProviderException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] No se encuentra el proveedor para el protocolo dado. |"+ e);
			resultado = false;
		} catch (IOException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] No se pudo leer el .properties. |"+ e);
			resultado = false;
		}catch (MessagingException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] Se produjo un fallo. |" + e);
			resultado = false;
		} finally {
			try {
				tp.close();
			} catch (MessagingException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] Error al cerrar. |" + e);
				resultado = false;
			}
		}
		return resultado;
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

