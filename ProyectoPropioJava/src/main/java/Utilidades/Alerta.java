package Utilidades;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * La clase Alerta proporciona métodos estáticos para configurar alertas en la sesión HTTP.
 * Las alertas consisten en un mensaje y un tipo de alerta que pueden ser utilizados para notificar al usuario sobre eventos importantes.
 *@author Isidro Camacho Diaz
 */
public class Alerta {
		/**
		 * Método estático para configurar una alerta en la sesión HTTP.
		 * La alerta consiste en un mensaje y un tipo de alerta, que pueden ser utilizados para notificar al usuario sobre eventos importantes.
		 * 
		 * @param request La solicitud HTTP donde se configurará la alerta.
		 * @param mensaje El mensaje de la alerta que se mostrará al usuario.
		 * @param tipo El tipo de la alerta, puede ser utilizado para determinar el estilo o la gravedad de la alerta (por ejemplo: "success", "warning", "error").
		 */
		public static void Alerta(HttpServletRequest request,String mensaje,String tipo) { 
			// Configura atributos en la sesión para que estén disponibles después de la redirección
	        request.getSession().setAttribute("mensajeAlerta", mensaje);
	        request.getSession().setAttribute("tipoAlerta", tipo);	
		}
}
