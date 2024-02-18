package Controladores;

import java.io.IOException;

import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.Encriptado;
import Utilidades.Escritura;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Aqui va el controlador es para el formulario para restablecer enviar el correo
public class ControladorOlvidar extends HttpServlet{
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		try {
    	//Cogemos el correo
        String correo=request.getParameter("correo");
        
        //Declaramos la implementacion
        ImplentacionIntereaccionUsuario inter = new ImplentacionIntereaccionUsuario();
        
        //Compribamos si se hizo bin la peticion
        if(inter.OlvidarClaveUsuario(request,correo)) {
        	Alerta.Alerta(request, "Se le mando un correo de restablecer contraseña", "success");
        	Escritura.EscribirFichero("Un usuario pidio una peticion de restablecimiento de contraseña");
        }
        //Si no se hizo bien se avisa al usuario
        else {
        	Escritura.EscribirFichero("Un usuario pedir una peticion de retablecimiento de contraseña pero no puedo hacer la peticion");
        	Alerta.Alerta(request, "Hubo Un error intentelo de nuevo mas tarde", "error");
        }
        
        //Se vuelve a mandar a la ventana
		response.sendRedirect("contrasenia.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			Escritura.EscribirFichero("Hubo un error en olvidar contraseña "+e.getLocalizedMessage());
			try {
				response.sendRedirect("contrasenia.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
        	    
    }

}
