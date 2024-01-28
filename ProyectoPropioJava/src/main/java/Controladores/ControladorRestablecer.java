package Controladores;

import java.io.IOException;

import Dtos.TokenDTO;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.Encriptado;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Aqui va el controlador es para el formulario para restablecer la contra
public class ControladorRestablecer extends HttpServlet{


    private static final long serialVersionUID = 1L;
	

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
		 try
		 {
			 response.getWriter().append("Served at: ").append(request.getContextPath());
		 }catch(Exception e) {

				System.out.println("[ERROR-ControladorRegistro-doGet] Se produjo un error en el metodo get");
			}
		
		}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
     
        
    	Encriptado nc = new Encriptado();
        ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
        implementacionCRUD acciones= new implementacionCRUD();
        
    	//Cogemos los valores
        String token=request.getParameter("tk");
        TokenDTO tokenBD= acciones.SeleccionarToken(token);
        String clave1=nc.EncriptarContra(request.getParameter("clave1"));
        String clave2=nc.EncriptarContra(request.getParameter("clave2"));
        
        //Se comprueba que encuentre el token y las clvaes sean igual
    	if(tokenBD != null && clave1.equals(clave2)) {
    		if(cosa.actualizarContrasena(request,tokenBD ,clave1)) {
    			Alerta.Alerta(request, "Se modifco la clave correctamente", "success");
    		}
    		else {
    			Alerta.Alerta(request, "Hubo un error intentelo mas tarde", "error");
    		}
       	}
		//Si no lo es se avisa al usuario
    	else {
    		Alerta.Alerta(request, "No se encontro a su usuario vuelva a pedir cambio de contraseña", "error");
    	}       
		try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
