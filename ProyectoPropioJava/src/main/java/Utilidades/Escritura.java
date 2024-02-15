package Utilidades;

import java.io.PrintWriter;

import Servicios.implementacionEscritura;

/**
 * La clase Escritura proporciona métodos estáticos para escribir texto en un archivo de texto.
 * Utiliza una implementación de la interfaz implementacionEscritura para realizar la escritura del archivo.
 *@author Isidro Camacho Diaz
 */
public class Escritura {
    
    /**
     * Escribe el texto proporcionado en un archivo de texto.
     * 
     * @param texto El texto que se escribirá en el archivo.
     */
    public static void EscribirFichero(String texto) {
        
        // Crea una instancia de implementacionEscritura para realizar la escritura
        implementacionEscritura es = new implementacionEscritura();
        
        // Abre el archivo para escritura, con la opción de agregar al final del archivo si ya existe
        //PrintWriter pr = es.abrirArchivo("C:\\Users\\isidr\\OneDrive\\Escritorio\\Ficheros\\log.txt", true);
        PrintWriter pr = es.abrirArchivo("C:\\Users\\Puesto3\\Desktop\\FicheroLog\\log.txt", true);
        
        // Escribe el texto en el archivo
        es.Escribir(pr, texto);
        
        // Cierra el archivo después de escribir
        es.Cerrar(pr);
    }
    
}