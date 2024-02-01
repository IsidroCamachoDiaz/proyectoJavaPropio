package Utilidades;

import org.apache.commons.io.FilenameUtils;

import jakarta.servlet.http.Part;

public class ComprobacionImagen {
	
	public boolean esArchivoImagen(Part filePart) {
        // Verificar por extensión del archivo
        String nombreArchivo = obtenerNombreArchivo(filePart);
        String extension = FilenameUtils.getExtension(nombreArchivo);

        // También puedes verificar por tipo MIME si es necesario
        String tipoMIME = filePart.getContentType();

        return (esExtensionImagen(extension) || esTipoMIMEImagen(tipoMIME));
    }

    private  String obtenerNombreArchivo(Part filePart) {
        for (String content : filePart.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private boolean esExtensionImagen(String extension) {
        return extension != null && (extension.equalsIgnoreCase("jpg") ||
                                     extension.equalsIgnoreCase("jpeg") ||
                                     extension.equalsIgnoreCase("png") 
        );
    }

    private  boolean esTipoMIMEImagen(String tipoMIME) {
        return tipoMIME != null && tipoMIME.startsWith("image");
    }
}
