package Utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Dtos.AccesoDTO;
import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TipoTrabajoDTO;
import Dtos.TokenDTO;
import Dtos.TrabajoDTO;
import Dtos.UsuarioDTO;

public class implementacionCRUD implements interfazCRUD {

	private static final String BASE_URL = "http://localhost:8080/";
	@Override
    public UsuarioDTO SeleccionarUsuario(String queDar) {
        return hacerGet("usuario/" + queDar, UsuarioDTO.class);
    }
	
	@Override
    public AccesoDTO SeleccionarAcceso(String queDar) {
        return hacerGet("acceso/" + queDar, AccesoDTO.class);
    }
	
	@Override
    public IncidenciaDTO SeleccionarIncidencia(String queDar) {
        return hacerGet("incidencia/" + queDar, IncidenciaDTO.class);
    }
	
	@Override
    public SolicitudDTO SeleccionarSolicitud(String queDar) {
        return hacerGet("solicitud/" + queDar, SolicitudDTO.class);
    }
	
	@Override
    public TipoTrabajoDTO SeleccionarTipoDeTrabajo(String queDar) {
        return hacerGet("tipo_incidencia/" + queDar, TipoTrabajoDTO.class);
    }
	
	@Override
    public TokenDTO SeleccionarToken(String queDar) {
        return hacerGet("tokenSelect/" + queDar, TokenDTO.class);
    }
	
	@Override
    public TrabajoDTO SeleccionarTrabajo(String queDar) {
        return hacerGet("trabajo/" + queDar, TrabajoDTO.class);
    }
	
    private <T> T hacerGet(String endpoint, Class<T> responseType) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            ObjectMapper objectMapper = new ObjectMapper();
            String line;
            T entidad = null;
            while ((line = reader.readLine()) != null) {
                entidad = objectMapper.readValue(line, responseType);
            }

            return entidad;
        } catch (JsonProcessingException e) {
            System.err.println("[ERROR-InteraccionEntidad-hacerGet] Error al convertir el objeto a JSON. | " + e);
        } catch (IOException e) {
            System.err.println("[ERROR-InteraccionEntidad-hacerGet] Se produjo un error al realizar la solicitud GET. | " + e);
        }

        return null;
    }

		@Override
		public boolean InsertarUsuario(UsuarioDTO usuarioMeter) {
	        return hacerPost("usuario/Insertar", usuarioMeter);
	
		}
		
		@Override
		public boolean InsertarAcceso(AccesoDTO nuevoAcceso) {
	        return hacerPost("acceso/Insertar", nuevoAcceso);
	    }
		
		@Override
	    public boolean InsertarIncidencia(IncidenciaDTO nuevaIncidencia) {
	        return hacerPost("incidencia/Insertar", nuevaIncidencia);
	    }
		
		@Override
	    public boolean InsertarSolicitud(SolicitudDTO nuevaSolicitud) {
	        return hacerPost("solicitud/Insertar", nuevaSolicitud);
	    }
		
		@Override
	    public boolean InsertarTipoDeTrabajo(TipoTrabajoDTO nuevoTipoTrabajo) {
	        return hacerPost("tipo_incidencia/Insertar", nuevoTipoTrabajo);
	    }
		
		@Override
	    public boolean InsertarToken(TokenDTO nuevoToken) {
	        return hacerPost("token/Insertar", nuevoToken);
	    }
		
		@Override
	    public boolean InsertarTrabajo(TrabajoDTO nuevoTrabajo) {
	        return hacerPost("trabajo/Insertar", nuevoTrabajo);
	    }

	    private boolean hacerPost(String endpoint, Object entidad) {
	        try {
	            URL url = new URL(BASE_URL + endpoint);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("POST");
	            connection.setDoOutput(true);
	            connection.setRequestProperty("Content-Type", "application/json");

	            ObjectMapper objectMapper = new ObjectMapper();
	            String jsonEntidad = objectMapper.writeValueAsString(entidad);

	            try (OutputStream os = connection.getOutputStream()) {
	                byte[] input = jsonEntidad.getBytes("utf-8");
	                os.write(input, 0, input.length);
	            }

	            int responseCode = connection.getResponseCode();
	            return responseCode == HttpURLConnection.HTTP_OK;
	        } catch (JsonProcessingException e) {
	            System.err.println("[ERROR-InteraccionEntidad-hacerPost] Error al convertir el objeto a JSON. | " + e);
	        } catch (IOException e) {
	            System.err.println("[ERROR-InteraccionEntidad-hacerPost] Se produjo un error al realizar la solicitud POST. | " + e);
	        }

	        return false;
	    }

	@Override
    public boolean EliminarAcceso(String idAcceso) {
        return hacerDelete("acceso/Eliminar/" + idAcceso);
    }
	@Override
    public boolean EliminarIncidencia(String idIncidencia) {
        return hacerDelete("incidencia/Eliminar/" + idIncidencia);
    }
	@Override
    public boolean EliminarSolicitud(String idSolicitud) {
        return hacerDelete("solicitud/Eliminar/" + idSolicitud);
    }
	@Override
    public boolean EliminarTipoDeTrabajo(String idTipoTrabajo) {
        return hacerDelete("tipoTrabajo/Eliminar/" + idTipoTrabajo);
    }
	@Override
    public boolean EliminarToken(String idToken) {
        return hacerDelete("token/Eliminar/" + idToken);
    }
	@Override
    public boolean EliminarTrabajo(String idTrabajo) {
        return hacerDelete("trabajo/Eliminar/" + idTrabajo);
    }
	@Override
    public boolean EliminarUsuario(String idUsuario) {
        return hacerDelete("usuario/Eliminar/" + idUsuario);
    }

    private boolean hacerDelete(String endpoint) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            System.err.println("[ERROR-InteraccionEntidad-hacerDelete] Se produjo un error al realizar la solicitud DELETE. | " + e);
        }

        return false;
    }

	@Override
    public boolean ActualizarIncidencia(IncidenciaDTO nuevaIncidencia) {
        return hacerPut("incidencia/Actualizar/"+nuevaIncidencia , nuevaIncidencia);
    }
	@Override
    public boolean ActualizarSolicitud(SolicitudDTO nuevaSolicitud) {
        return hacerPut("solicitud/Actualizar/", nuevaSolicitud);
    }
	@Override
    public boolean ActualizarTipoDeTrabajo(TipoTrabajoDTO nuevoTipoTrabajo) {
        return hacerPut("tipoTrabajo/Actualizar/", nuevoTipoTrabajo);
    }

	@Override
    public boolean ActualizarTrabajo(TrabajoDTO nuevoTrabajo) {
        return hacerPut("trabajo/Actualizar/" , nuevoTrabajo);
    }
	
	@Override
    public boolean ActualizarUsuario(UsuarioDTO nuevoUsuario) {
        return hacerPut("usuario/Actualizar/"+nuevoUsuario.getIdUsuario() , nuevoUsuario);
    }
	

    private boolean hacerPut(String endpoint, Object entidad) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonEntidad = objectMapper.writeValueAsString(entidad);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonEntidad.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (JsonProcessingException e) {
            System.err.println("[ERROR-InteraccionEntidad-hacerPut] Error al convertir el objeto a JSON. | " + e);
        } catch (IOException e) {
            System.err.println("[ERROR-InteraccionEntidad-hacerPut] Se produjo un error al realizar la solicitud PUT. | " + e);
        }

        return false;
    }
}
