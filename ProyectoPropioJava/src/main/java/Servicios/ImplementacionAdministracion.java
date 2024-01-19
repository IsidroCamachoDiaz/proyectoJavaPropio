package Servicios;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import jakarta.servlet.http.Part;

//Convierte el array de bytes a una imagen BufferedImage 
//ESTO VA EN EL METODO PARA CONVERTIR UN ARRAY A IMAGEN PARA PONERLO EN EL HTML
//BufferedImage image = convertByteArrayToImage(imageBytes);
public class ImplementacionAdministracion implements InterfaceAdministracion {

 private BufferedImage pasarDeByteAImagen(byte[] byteArray) throws IOException {
     // Crea un flujo de entrada desde el array de bytes
     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);

     // Lee la imagen desde el flujo de entrada
     BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

     // Cierra el flujo de entrada
     byteArrayInputStream.close();

     return bufferedImage;
 }
 private byte[] pasarDeImagenAByte(Part part) throws IOException {
     InputStream imagen = part.getInputStream();
     ByteArrayOutputStream byteImagen = new ByteArrayOutputStream();
     byte[] buffer = new byte[1024];
     int numeroArrayByte;

     while ((numeroArrayByte = imagen.read(buffer)) != -1) {
         byteImagen.write(buffer, 0, numeroArrayByte);
     }

     return byteImagen.toByteArray();
 }
}