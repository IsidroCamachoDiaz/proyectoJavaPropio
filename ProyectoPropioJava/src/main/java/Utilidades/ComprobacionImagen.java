package Utilidades;

import org.apache.commons.io.FilenameUtils;

import jakarta.servlet.http.Part;

/**
 * La clase ComprobacionImagen proporciona métodos para verificar si un archivo es una imagen.
 *@author Isidro Camacho Diaz
 */
public class ComprobacionImagen {
	
	
	/**
	 * Método para Comprobar si el archivo es un tipo de imagen
	 * 
	 * @param filePart La parte de archivo.
	 * @return true si es una imagen false si no lo es
	 */
	public boolean esArchivoImagen(Part filePart) {
        // Verificar por extensión del archivo
        String nombreArchivo = obtenerNombreArchivo(filePart);
        String extension = FilenameUtils.getExtension(nombreArchivo);

        // También puedes verificar por tipo MIME si es necesario
        String tipoMIME = filePart.getContentType();

        return (esExtensionImagen(extension) || esTipoMIMEImagen(tipoMIME));
    }

	/**
	 * Método para obtener el nombre de archivo de una parte de archivo.
	 * 
	 * @param filePart La parte de archivo de la que se desea obtener el nombre.
	 * @return El nombre del archivo si se pudo extraer, o null si no se pudo obtener.
	 */
	private String obtenerNombreArchivo(Part filePart) {
	    // Iterar a través de las partes de la cabecera "content-disposition" para encontrar el nombre del archivo
	    for (String content : filePart.getHeader("content-disposition").split(";")) {
	        // Verificar si la parte comienza con "filename", lo que indica el nombre del archivo
	        if (content.trim().startsWith("filename")) {
	            // Extraer y devolver el nombre del archivo
	            return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    // Si no se encuentra el nombre del archivo, devolver null
	    return null;
	}

	/**
	 * Método para verificar si una extensión de archivo corresponde a una imagen.
	 * 
	 * @param extension La extensión del archivo a verificar.
	 * @return True si la extensión corresponde a una imagen (jpg, jpeg, png), False de lo contrario.
	 */
	private boolean esExtensionImagen(String extension) {
	    // Verificar si la extensión no es nula y corresponde a una imagen (jpg, jpeg, png)
	    return extension != null && (extension.equalsIgnoreCase("jpg") ||
	                                 extension.equalsIgnoreCase("jpeg") ||
	                                 extension.equalsIgnoreCase("png"));
	}

	/**
	 * Método para verificar si un tipo MIME corresponde a una imagen.
	 * 
	 * (Para Entender el metodo)
	 * MIME es una etiqueta que identifica el formato o tipo de archivo de una imagen cuando se transfiere a 
	 * través de la web
	 * 
	 * @param tipoMIME El tipo MIME del archivo a verificar.
	 * @return True si el tipo MIME corresponde a una imagen, False de lo contrario.
	 */
	private boolean esTipoMIMEImagen(String tipoMIME) {
	    // Verificar si el tipo MIME no es nulo y comienza con "image"
	    return tipoMIME != null && tipoMIME.startsWith("image");
	}
}
