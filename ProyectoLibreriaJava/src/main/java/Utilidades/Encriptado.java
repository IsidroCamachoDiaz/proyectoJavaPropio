package Utilidades;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Clase para el encriptado de la contraseña
 * @author ASMP-17/11/23
 */
public class Encriptado {
	
	/**
	 * Metodo para encriptar la contraseña 
	 * @param password
	 * @return String 
	 * @author ASMP-17/11/23
	 */
		public String EncriptarContra(String password) { 
			StringBuffer hexString = new StringBuffer();
			try
			{
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				
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
			return hexString.toString();
			
			
		}
}
