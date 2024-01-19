package Utilidades;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpServletRequest;

public class Alerta {
	/**
	 * Metodo para encriptar la contraseña 
	 * @param password
	 * @return String 
	 * @author ASMP-17/11/23
	 */
		public static void Alerta(HttpServletRequest request,String mensaje,String tipo) { 
			// Configura atributos en la sesión para que estén disponibles después de la redirección
	        request.getSession().setAttribute("mensajeAlerta", mensaje);
	        request.getSession().setAttribute("tipoAlerta", tipo);	
		}
}
