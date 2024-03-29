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
import java.util.List;
import java.util.ArrayList;

/**
 * Implementación de la interfaz CRUD que proporciona operaciones de consulta, inserción, actualización y eliminación
 * para entidades como Usuario, Acceso, Incidencia, Solicitud, TipoTrabajo, Token y Trabajo a través de una API REST.
 *@author Isidro Camacho Diaz
 */
public class implementacionCRUD implements interfazCRUD {

	
	// URL base de la API REST
    private static final String BASE_URL = "http://localhost:8080/";


    @Override
    public List<UsuarioDTO> SeleccionarTodosUsuarios() {
        return hacerGetLista("usuario/Select", UsuarioDTO.class);
    }
    
    @Override
    public List<AccesoDTO> SeleccionarTodosAccesos() {
        return hacerGetLista("acceso/Select", AccesoDTO.class);
    }

    @Override
    public List<IncidenciaDTO> SeleccionarTodasIncidencias() {
        return hacerGetLista("incidencia/Select", IncidenciaDTO.class);
    }

    @Override
    public List<SolicitudDTO> SeleccionarTodasSolicitudes() {
        return hacerGetLista("solicitud/Select", SolicitudDTO.class);
    }

    @Override
    public List<TipoTrabajoDTO> SeleccionarTodosTiposDeTrabajo() {
        return hacerGetLista("tipo_incidencia/Select", TipoTrabajoDTO.class);
    }


    @Override
    public List<TrabajoDTO> SeleccionarTodosTrabajos() {
        return hacerGetLista("trabajo/Select", TrabajoDTO.class);
    }
    
    @Override
    public List<TokenDTO> SeleccionarTodosTokens() {
        return hacerGetLista("token/Select", TokenDTO.class);
    }

    /**
     * Método privado para realizar solicitudes GET genéricas a la API REST y obtener una lista.
     *
     * @param endpoint     Ruta específica del recurso a consultar.
     * @param responseType Clase del objeto que se espera recibir como respuesta.
     * @param <T>          Tipo genérico que representa el tipo de objeto de respuesta.
     * @return Lista de objetos de tipo T obtenida como respuesta de la solicitud GET.
     */
    private <T> List<T> hacerGetLista(String endpoint, Class<T> responseType) {
        try {
        	// Construye la URL utilizando el endpoint proporcionado y la URL base
            URL url = new URL(BASE_URL + endpoint);
            // Abre una conexión HttpURLConnection con la URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Establece el método de solicitud como GET
            connection.setRequestMethod("GET");
            
            // Lee la respuesta del servidor
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // Crea un ObjectMapper para convertir el JSON en objetos Java
            ObjectMapper objectMapper = new ObjectMapper();
            
            // Utiliza TypeFactory para construir el TypeReference
            List<T> listaEntidades = objectMapper.readValue(reader, 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, responseType));
            
            // Devuelve la lista de entidades obtenida
            return listaEntidades;
        } catch (JsonProcessingException e) {
        	Escritura.EscribirFichero("[ERROR-InteraccionEntidad-hacerGetLista] Error al convertir el objeto a JSON. | " + e);
            System.err.println("[ERROR-InteraccionEntidad-hacerGetLista] Error al convertir el objeto a JSON. | " + e);
        } catch (IOException e) {
        	Escritura.EscribirFichero("[ERROR-InteraccionEntidad-hacerGetLista] Se produjo un error al realizar la solicitud GET. | " + e);
            System.err.println("[ERROR-InteraccionEntidad-hacerGetLista] Se produjo un error al realizar la solicitud GET. | " + e);
        }

        return new ArrayList<>(); // Devolver una lista vacía en caso de error.
    }


    
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
        return hacerGet("token/Select/" + queDar, TokenDTO.class);
    }

    @Override
    public TrabajoDTO SeleccionarTrabajo(String queDar) {
        return hacerGet("trabajo/" + queDar, TrabajoDTO.class);
    }

    /**
     * Método privado para realizar solicitudes GET genéricas a la API REST.
     *
     * @param endpoint     Ruta específica del recurso a consultar.
     * @param responseType Clase del objeto que se espera recibir como respuesta.
     * @param <T>          Tipo genérico que representa el tipo de objeto de respuesta.
     * @return Objeto de tipo T obtenido como respuesta de la solicitud GET.
     */
    private <T> T hacerGet(String endpoint, Class<T> responseType) {
        try {
        	 // Construye la URL utilizando el endpoint proporcionado y la URL base
            URL url = new URL(BASE_URL + endpoint);
            // Abre una conexión HttpURLConnection con la URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Establece el método de solicitud como GET
            connection.setRequestMethod("GET");
            
            // Lee la respuesta del servidor
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // Crea un ObjectMapper para convertir el JSON en objetos Java
            ObjectMapper objectMapper = new ObjectMapper();
            
            // Lee cada línea de la respuesta y convierte el JSON en un objeto del tipo especificado
            String line;
            T entidad = null;
            while ((line = reader.readLine()) != null) {
                entidad = objectMapper.readValue(line, responseType);
            }
            
            // Devuelve el objeto obtenido
            return entidad;
        } catch (JsonProcessingException e) {
        	Escritura.EscribirFichero("[ERROR-InteraccionEntidad-hacerGet] Error al convertir el objeto a JSON. | " + e);
            System.err.println("[ERROR-InteraccionEntidad-hacerGet] Error al convertir el objeto a JSON. | " + e);
        } catch (IOException e) {
        	Escritura.EscribirFichero("[ERROR-InteraccionEntidad-hacerGet] Se produjo un error al realizar la solicitud GET. | " + e);
            System.err.println("[ERROR-InteraccionEntidad-hacerGet] Se produjo un error al realizar la solicitud GET. | " + e);
        }

        return null;
    }

    @Override
    public boolean InsertarUsuario(UsuarioDTO usuarioMeter) {
        return hacerPost("usuario/Insertar", usuarioMeter);
    }
    
    @Override
    public boolean InsertarUsuarioAdministrador(UsuarioDTO usuarioMeter) {
        return hacerPost("usuario/InsertarAdministrador", usuarioMeter);
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

    /**
     * Método privado para realizar solicitudes POST genéricas a la API REST.
     *
     * @param endpoint Ruta específica del recurso donde se realizará la inserción.
     * @param entidad  Objeto que se desea insertar en la base de datos.
     * @return true si la operación de inserción fue exitosa, false de lo contrario.
     */
    private boolean hacerPost(String endpoint, Object entidad) {
        try {
        	// Construye la URL utilizando el endpoint proporcionado y la URL base
            URL url = new URL(BASE_URL + endpoint);
            // Abre una conexión HttpURLConnection con la URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Establece el método de solicitud como POST
            connection.setRequestMethod("POST");
            // Habilita la escritura de datos en la conexión
            connection.setDoOutput(true);
            // Establece el tipo de contenido del cuerpo de la solicitud como JSON
            connection.setRequestProperty("Content-Type", "application/json");

            // Crea un ObjectMapper para convertir el objeto Java en JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Convierte el objeto en formato JSON
            String jsonEntidad = objectMapper.writeValueAsString(entidad);

            // Envía los datos JSON al servidor
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonEntidad.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obtiene el código de respuesta HTTP
            int responseCode = connection.getResponseCode();
            // Devuelve true si la solicitud fue exitosa (código 200 OK)
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (JsonProcessingException e) {
        	Escritura.EscribirFichero("[ERROR-InteraccionEntidad-hacerPost] Error al convertir el objeto a JSON. | " + e);
            System.err.println("[ERROR-InteraccionEntidad-hacerPost] Error al convertir el objeto a JSON. | " + e);
        } catch (IOException e) {
        	Escritura.EscribirFichero("[ERROR-InteraccionEntidad-hacerPost] Se produjo un error al realizar la solicitud POST. | " + e);
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

    /**
     * Método privado para realizar solicitudes DELETE genéricas a la API REST.
     *
     * @param endpoint Ruta específica del recurso donde se realizará la eliminación.
     * @return true si la operación de eliminación fue exitosa, false de lo contrario.
     */
    private boolean hacerDelete(String endpoint) {
        try {
        	// Construye la URL utilizando el endpoint proporcionado y la URL base
            URL url = new URL(BASE_URL + endpoint);
            // Abre una conexión HttpURLConnection con la URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Establece el método de solicitud como DELETE
            connection.setRequestMethod("DELETE");

            // Obtiene el código de respuesta HTTP
            int responseCode = connection.getResponseCode();
            // Devuelve true si la solicitud fue exitosa (código 200 OK)
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
        	Escritura.EscribirFichero("[ERROR-InteraccionEntidad-hacerDelete] Se produjo un error al realizar la solicitud DELETE. | " + e);
            System.err.println("[ERROR-InteraccionEntidad-hacerDelete] Se produjo un error al realizar la solicitud DELETE. | " + e);
        }

        return false;
    }

    @Override
    public boolean ActualizarIncidencia(IncidenciaDTO nuevaIncidencia) {
        return hacerPut("incidencia/Actualizar/" + nuevaIncidencia.getId_incidencia(), nuevaIncidencia);
    }

    @Override
    public boolean ActualizarSolicitud(SolicitudDTO nuevaSolicitud) {
        return hacerPut("solicitud/Actualizar/" + nuevaSolicitud.getIdSolicitud(), nuevaSolicitud);
    }

    @Override
    public boolean ActualizarTipoDeTrabajo(TipoTrabajoDTO nuevoTipoTrabajo) {
        return hacerPut("tipo_incidencia/Actualizar/" + nuevoTipoTrabajo.getId_tipo(), nuevoTipoTrabajo);
    }

    @Override
    public boolean ActualizarTrabajo(TrabajoDTO nuevoTrabajo) {
        return hacerPut("trabajo/Actualizar/" + nuevoTrabajo.getId_trabajo(), nuevoTrabajo);
    }

    @Override
    public boolean ActualizarUsuario(UsuarioDTO nuevoUsuario) {
        return hacerPut("usuario/Actualizar/" + nuevoUsuario.getIdUsuario(), nuevoUsuario);
    }

    /**
     * Método privado para realizar solicitudes PUT genéricas a la API REST.
     *
     * @param endpoint Ruta específica del recurso donde se realizará la actualización.
     * @param entidad Objeto que contiene los nuevos datos para la actualización.
     * @return true si la operación de actualización fue exitosa, false de lo contrario.
     */
    private boolean hacerPut(String endpoint, Object entidad) {
        try {
        	// Construye la URL utilizando el endpoint proporcionado y la URL base
            URL url = new URL(BASE_URL + endpoint);
            // Abre una conexión HttpURLConnection con la URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Establece el método de solicitud como PUT
            connection.setRequestMethod("PUT");
            // Permite la escritura de datos en la conexión
            connection.setDoOutput(true);
            // Establece el tipo de contenido de la solicitud como JSON
            connection.setRequestProperty("Content-Type", "application/json");

            // Convierte el objeto entidad a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonEntidad = objectMapper.writeValueAsString(entidad);

            // Envía los datos JSON a través de la conexión
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonEntidad.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            // Obtiene el código de respuesta HTTP
            int responseCode = connection.getResponseCode();
            // Devuelve true si la solicitud fue exitosa (código 200 OK)
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (JsonProcessingException e) {
        	Escritura.EscribirFichero("[ERROR-InteraccionEntidad-hacerPut] Error al convertir el objeto a JSON. | " + e);
            System.err.println("[ERROR-InteraccionEntidad-hacerPut] Error al convertir el objeto a JSON. | " + e);
        } catch (IOException e) {
        	Escritura.EscribirFichero("[ERROR-InteraccionEntidad-hacerPut] Se produjo un error al realizar la solicitud PUT. | " + e);
            System.err.println("[ERROR-InteraccionEntidad-hacerPut] Se produjo un error al realizar la solicitud PUT. | " + e);
        }

        return false;
    }
}
