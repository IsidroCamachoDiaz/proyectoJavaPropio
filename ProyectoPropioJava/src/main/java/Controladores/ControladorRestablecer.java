package Controladores;

import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Encriptado;
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
        String token=request.getParameter("tk");
        String clave1=nc.EncriptarContra(request.getParameter("clave1"));
        String clave2=nc.EncriptarContra(request.getParameter("clave2"));
        ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
        
    	if(token != null && clave1.equals(clave2)) {
       		cosa.actualizarContrasena( token, clave1);
       	}
        	
        
       
        

           
        
    }
}
