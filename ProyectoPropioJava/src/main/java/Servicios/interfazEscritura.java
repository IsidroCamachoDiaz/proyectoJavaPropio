package Servicios;

import java.io.PrintWriter;

/**
 * La interfaz interfazEscritura proporciona métodos para manipular archivos de texto.
 * Implementa métodos para abrir, escribir y cerrar un archivo.
 * @author Isidro Camacho Diaz
 */
public interface interfazEscritura {
    
    /**
     * Abre un archivo en la ruta especificada.
     * 
     * @param ruta La ruta del archivo que se abrirá.
     * @param sobrescribir Indica si se sobrescribirá el archivo si ya existe.
     * @return Un objeto PrintWriter que representa el archivo abierto para escritura.
     */
    public PrintWriter abrirArchivo(String ruta, boolean sobrescribir);
    
    /**
     * Escribe texto en un archivo.
     * 
     * @param pw El objeto PrintWriter que representa el archivo donde se escribirá el texto.
     * @param texto El texto que se escribirá en el archivo.
     * @return El objeto PrintWriter actualizado después de escribir el texto.
     */
    public PrintWriter Escribir(PrintWriter pw, String texto);
    
    /**
     * Cierra el archivo abierto.
     * 
     * @param fichero El objeto PrintWriter que representa el archivo que se cerrará.
     */
    public void Cerrar(PrintWriter fichero);
}
