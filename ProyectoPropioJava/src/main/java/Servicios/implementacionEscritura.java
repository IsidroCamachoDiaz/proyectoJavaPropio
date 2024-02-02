package Servicios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;

public class implementacionEscritura implements interfazEscritura {

	 public PrintWriter abrirArchivo(String ruta, boolean sobreEscribir) {
	        FileWriter fichero = null;
	        PrintWriter pw = null;

	        try {
	            // Verificar si el archivo existe
	            File archivo = new File(ruta);

	            if (!archivo.exists()) {
	                // Si el archivo no existe, intentar crearlo
	                if (archivo.createNewFile()) {
	                    System.out.println("Archivo creado: " + archivo.getAbsolutePath());
	                } else {
	                    System.out.println("No se pudo crear el archivo: " + archivo.getAbsolutePath());
	                    return null;  // No se puede abrir el archivo, retornar null
	                }
	            }

	            // Abrir el archivo
	            fichero = new FileWriter(archivo, sobreEscribir);
	            pw = new PrintWriter(fichero);

	            // Cabecera (si es necesario)

	        } catch (IOException ioe) {
	            // Si ocurre un error al abrir o crear el archivo, manejar la excepción
	            System.out.print("[ERROR] - FICHERO NO ENCONTRADO: " + ruta + "\n" + ioe);
	        }

	        return pw;
	    }
	 
	public PrintWriter Escribir(PrintWriter pw,String texto) {
		//Se pide el texto para escribir y se escribe
		LocalDateTime ahora = LocalDateTime.now();
		pw.println("["+ ahora.toString()+"] "+texto);
		return pw;
	}
	
	public void Cerrar(PrintWriter fichero) {
		//Cierra el fichero
		fichero.close();
	}
}
