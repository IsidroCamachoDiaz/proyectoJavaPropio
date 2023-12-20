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

import Dtos.UsuarioDTO;






/**
 * Clase para implentar la interfez de register,login del usuario que implenta de la interfaz
 * 
 */

public class ImplentacionIntereaccionUsuario implements InterfaceIntereccionUsuario {
	
	public boolean IniciarSesion(UsuarioDTO user) {
		
		
		try {
			//Se le pasa la url
				URL url = new URL("http://localhost:8080/usuarioApi/usuarioSelect/usuarioDni/"+user.getDniUsuario());
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        
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
			
			
			
				ObjectMapper objectMapper = new ObjectMapper();

					String usuarioJson = objectMapper.writeValueAsString(usu);
		            URL url = new URL("http://localhost:8080/usuarioApi/usuarioInsertar");

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
			//Meter en un if si el usuario se encontro que haga el resto (tiene que llegar hasta ok=EnviarMensaje...)
			UUID uuid = UUID.randomUUID();
			String token = uuid.toString();
			//Aqui generas la fecha limite con un tiempo de 10 minutos
			//Aqui una vez encuentre el usuario insertas el token generado arriba y 
			//lo inserta con la fecha limite, id_Usuario
			 String mensaje=MenajeCorreo(token,seguridad.getProperty("direccion"));
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
			
			URL url = new URL("http://localhost:8080/usuarioApi/"+queDar);
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
	private String MenajeCorreo(String token,String direccion)
	{
		
		return " <p>Se a enviado una petición para restablecer la contraseña, si no has sido tu porfavor cambie la contraseña inmediatamente y si no pulsa aqui: </p>\r\n"
		 		+ "    <a href=\""+direccion+"?tk="+token+"\"><button class=\"button-64\" role=\"button\" value=\"Cambiar contraseña\" ><span class=\"text\">Restablecer contraseña</span></button></a>\r\n";
		 		
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
			addressFrom = new InternetAddress("'InfoWeb' <" + seguridad.getProperty("correo") + ">");
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
				body = " Restablecer contraseña: " + body;
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
	

}

