package Utilidades;

import java.io.PrintWriter;

import Servicios.implementacionEscritura;

public class Escritura {
	
	public static void EscribirFichero(String texto) {
		
		implementacionEscritura es = new implementacionEscritura();
		
		//PrintWriter pr =es.abrirArchivo("C:\\Users\\isidr\\OneDrive\\Escritorio\\Ficheros\\log.txt", true);
		PrintWriter pr =es.abrirArchivo("C:\\Users\\Puesto3\\Desktop\\FicheroLog\\log.txt", true);
		es.Escribir(pr, texto);
		es.Cerrar(pr);
	}
	
}
