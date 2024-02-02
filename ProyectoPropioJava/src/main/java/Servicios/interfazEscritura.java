package Servicios;

import java.io.PrintWriter;

public interface interfazEscritura {
	/**
	 * Recibe un string que es la Ruta y devuelve el fichero ya abierto
	 * */
	public PrintWriter abrirArchivo(String ruta,boolean sobrescribir);
	/**
	 * Recibe un string para escribir el fichero
	 * */
	public PrintWriter Escribir(PrintWriter pw,String texto);
	/**
	 * Cierra el fichero
	 * */
	public void Cerrar(PrintWriter fichero);
}
