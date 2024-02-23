package Utilidades;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Clase para el encriptado de la contraseña
 *@author Isidro Camacho Diaz
 */
public class Encriptado {
	
	/**
	 * Método para encriptar una contraseña utilizando el algoritmo SHA-256.
	 * 
	 * @param password La contraseña a encriptar.
	 * @return La contraseña encriptada como una cadena hexadecimal.
	 */
		public String EncriptarContra(String password) { 
			// Crea un StringBuffer para almacenar la cadena hexadecimal resultante
			StringBuffer hexString = new StringBuffer();
			try
			{
				// Obtiene una instancia de MessageDigest para el algoritmo SHA-256
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				// Calcula el hash de la contraseña
				byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				
				// Convierte el hash en una cadena hexadecimal
				for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(hash[i] & 0xff);
				if (hex.length() == 1) {
				hexString.append("0");
				}
				hexString.append(hex);
				}
				
			}catch(NoSuchAlgorithmException e)
			{
				System.out.println("Error");
			}
			// Devuelve la contraseña encriptada como una cadena hexadecimal
			return hexString.toString();						
		}
}
