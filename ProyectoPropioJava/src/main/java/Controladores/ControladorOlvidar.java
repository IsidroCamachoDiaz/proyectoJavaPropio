package Controladores;

import java.io.IOException;

import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.Encriptado;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Aqui va el controlador es para el formulario para restablecer enviar el correo
public class ControladorOlvidar extends HttpServlet{
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
     
        String correo=request.getParameter("correo");
        
        ImplentacionIntereaccionUsuario inter = new ImplentacionIntereaccionUsuario();
        
        if(inter.OlvidarClaveUsuario(request,correo)) {
        	Alerta.Alerta(request, "Se le mando un correo de restablecer contraseña", "success");
        }
        else {
        	Alerta.Alerta(request, "Hubo Un error intentelo de nuevo mas tarde", "error");
        }
        
		try {
			response.sendRedirect("contrasenia.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
        	    
    }

}
