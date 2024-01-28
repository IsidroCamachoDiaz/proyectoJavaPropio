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

/**
 * Implementación de la interfaz CRUD que proporciona operaciones de consulta, inserción, actualización y eliminación
 * para entidades como Usuario, Acceso, Incidencia, Solicitud, TipoTrabajo, Token y Trabajo a través de una API REST.
 */
public class implementacionCRUD implements interfazCRUD {

	
	// URL base de la API REST
    private static final String BASE_URL = "http://localhost:8080/";

    /**
     * Realiza una solicitud GET para seleccionar un usuario por su identificador.
     *
     * @param queDar Identificador del usuario a seleccionar.
     * @return Objeto UsuarioDTO correspondiente al identificador proporcionado.
     */
    @Override
    public UsuarioDTO SeleccionarUsuario(String queDar) {
        return hacerGet("usuario/" + queDar, UsuarioDTO.class);
    }

    /**
     * Realiza una solicitud GET para seleccionar un acceso por su identificador.
     *
     * @param queDar Identificador del acceso a seleccionar.
     * @return Objeto AccesoDTO correspondiente al identificador proporcionado.
     */
    @Override
    public AccesoDTO SeleccionarAcceso(String queDar) {
        return hacerGet("acceso/" + queDar, AccesoDTO.class);
    }

    /**
     * Realiza una solicitud GET para seleccionar una incidencia por su identificador.
     *
     * @param queDar Identificador de la incidencia a seleccionar.
     * @return Objeto IncidenciaDTO correspondiente al identificador proporcionado.
     */
    @Override
    public IncidenciaDTO SeleccionarIncidencia(String queDar) {
        return hacerGet("incidencia/" + queDar, IncidenciaDTO.class);
    }

    /**
     * Realiza una solicitud GET para seleccionar una solicitud por su identificador.
     *
     * @param queDar Identificador de la solicitud a seleccionar.
     * @return Objeto SolicitudDTO correspondiente al identificador proporcionado.
     */
    @Override
    public SolicitudDTO SeleccionarSolicitud(String queDar) {
        return hacerGet("solicitud/" + queDar, SolicitudDTO.class);
    }

    /**
     * Realiza una solicitud GET para seleccionar un tipo de trabajo por su identificador.
     *
     * @param queDar Identificador del tipo de trabajo a seleccionar.
     * @return Objeto TipoTrabajoDTO correspondiente al identificador proporcionado.
     */
    @Override
    public TipoTrabajoDTO SeleccionarTipoDeTrabajo(String queDar) {
        return hacerGet("tipo_incidencia/" + queDar, TipoTrabajoDTO.class);
    }

    /**
     * Realiza una solicitud GET para seleccionar un token por su identificador.
     *
     * @param queDar Identificador del token a seleccionar.
     * @return Objeto TokenDTO correspondiente al identificador proporcionado.
     */
    @Override
    public TokenDTO SeleccionarToken(String queDar) {
        return hacerGet("token/Select/" + queDar, TokenDTO.class);
    }

    /**
     * Realiza una solicitud GET para seleccionar un trabajo por su identificador.
     *
     * @param queDar Identificador del trabajo a seleccionar.
     * @return Objeto TrabajoDTO correspondiente al identificador proporcionado.
     */
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

    /**
     * Realiza una solicitud POST para insertar un nuevo usuario en la base de datos.
     *
     * @param usuarioMeter Objeto UsuarioDTO que se desea insertar.
     * @return true si la operación de inserción fue exitosa, false de lo contrario.
     */
    @Override
    public boolean InsertarUsuario(UsuarioDTO usuarioMeter) {
        return hacerPost("usuario/Insertar", usuarioMeter);
    }

    /**
     * Realiza una solicitud POST para insertar un nuevo acceso en la base de datos.
     *
     * @param nuevoAcceso Objeto AccesoDTO que se desea insertar.
     * @return true si la operación de inserción fue exitosa, false de lo contrario.
     */
    @Override
    public boolean InsertarAcceso(AccesoDTO nuevoAcceso) {
        return hacerPost("acceso/Insertar", nuevoAcceso);
    }

    /**
     * Realiza una solicitud POST para insertar una nueva incidencia en la base de datos.
     *
     * @param nuevaIncidencia Objeto IncidenciaDTO que se desea insertar.
     * @return true si la operación de inserción fue exitosa, false de lo contrario.
     */
    @Override
    public boolean InsertarIncidencia(IncidenciaDTO nuevaIncidencia) {
        return hacerPost("incidencia/Insertar", nuevaIncidencia);
    }

    /**
     * Realiza una solicitud POST para insertar una nueva solicitud en la base de datos.
     *
     * @param nuevaSolicitud Objeto SolicitudDTO que se desea insertar.
     * @return true si la operación de inserción fue exitosa, false de lo contrario.
     */
    @Override
    public boolean InsertarSolicitud(SolicitudDTO nuevaSolicitud) {
        return hacerPost("solicitud/Insertar", nuevaSolicitud);
    }

    /**
     * Realiza una solicitud POST para insertar un nuevo tipo de trabajo en la base de datos.
     *
     * @param nuevoTipoTrabajo Objeto TipoTrabajoDTO que se desea insertar.
     * @return true si la operación de inserción fue exitosa, false de lo contrario.
     */
    @Override
    public boolean InsertarTipoDeTrabajo(TipoTrabajoDTO nuevoTipoTrabajo) {
        return hacerPost("tipo_incidencia/Insertar", nuevoTipoTrabajo);
    }

    /**
     * Realiza una solicitud POST para insertar un nuevo token en la base de datos.
     *
     * @param nuevoToken Objeto TokenDTO que se desea insertar.
     * @return true si la operación de inserción fue exitosa, false de lo contrario.
     */
    @Override
    public boolean InsertarToken(TokenDTO nuevoToken) {
        return hacerPost("token/Insertar", nuevoToken);
    }

    /**
     * Realiza una solicitud POST para insertar un nuevo trabajo en la base de datos.
     *
     * @param nuevoTrabajo Objeto TrabajoDTO que se desea insertar.
     * @return true si la operación de inserción fue exitosa, false de lo contrario.
     */
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

    /**
     * Realiza una solicitud DELETE para eliminar un acceso de la base de datos.
     *
     * @param idAcceso Identificador del acceso que se desea eliminar.
     * @return true si la operación de eliminación fue exitosa, false de lo contrario.
     */
    @Override
    public boolean EliminarAcceso(String idAcceso) {
        return hacerDelete("acceso/Eliminar/" + idAcceso);
    }

    /**
     * Realiza una solicitud DELETE para eliminar una incidencia de la base de datos.
     *
     * @param idIncidencia Identificador de la incidencia que se desea eliminar.
     * @return true si la operación de eliminación fue exitosa, false de lo contrario.
     */
    @Override
    public boolean EliminarIncidencia(String idIncidencia) {
        return hacerDelete("incidencia/Eliminar/" + idIncidencia);
    }

    /**
     * Realiza una solicitud DELETE para eliminar una solicitud de la base de datos.
     *
     * @param idSolicitud Identificador de la solicitud que se desea eliminar.
     * @return true si la operación de eliminación fue exitosa, false de lo contrario.
     */
    @Override
    public boolean EliminarSolicitud(String idSolicitud) {
        return hacerDelete("solicitud/Eliminar/" + idSolicitud);
    }

    /**
     * Realiza una solicitud DELETE para eliminar un tipo de trabajo de la base de datos.
     *
     * @param idTipoTrabajo Identificador del tipo de trabajo que se desea eliminar.
     * @return true si la operación de eliminación fue exitosa, false de lo contrario.
     */
    @Override
    public boolean EliminarTipoDeTrabajo(String idTipoTrabajo) {
        return hacerDelete("tipoTrabajo/Eliminar/" + idTipoTrabajo);
    }

    /**
     * Realiza una solicitud DELETE para eliminar un token de la base de datos.
     *
     * @param idToken Identificador del token que se desea eliminar.
     * @return true si la operación de eliminación fue exitosa, false de lo contrario.
     */
    @Override
    public boolean EliminarToken(String idToken) {
        return hacerDelete("token/Eliminar/" + idToken);
    }

    /**
     * Realiza una solicitud DELETE para eliminar un trabajo de la base de datos.
     *
     * @param idTrabajo Identificador del trabajo que se desea eliminar.
     * @return true si la operación de eliminación fue exitosa, false de lo contrario.
     */
    @Override
    public boolean EliminarTrabajo(String idTrabajo) {
        return hacerDelete("trabajo/Eliminar/" + idTrabajo);
    }

    /**
     * Realiza una solicitud DELETE para eliminar un usuario de la base de datos.
     *
     * @param idUsuario Identificador del usuario que se desea eliminar.
     * @return true si la operación de eliminación fue exitosa, false de lo contrario.
     */
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

    /**
     * Realiza una solicitud PUT para actualizar una incidencia en la base de datos.
     *
     * @param nuevaIncidencia Objeto IncidenciaDTO con los nuevos datos.
     * @return true si la operación de actualización fue exitosa, false de lo contrario.
     */
    @Override
    public boolean ActualizarIncidencia(IncidenciaDTO nuevaIncidencia) {
        return hacerPut("incidencia/Actualizar/" + nuevaIncidencia, nuevaIncidencia);
    }

    /**
     * Realiza una solicitud PUT para actualizar una solicitud en la base de datos.
     *
     * @param nuevaSolicitud Objeto SolicitudDTO con los nuevos datos.
     * @return true si la operación de actualización fue exitosa, false de lo contrario.
     */
    @Override
    public boolean ActualizarSolicitud(SolicitudDTO nuevaSolicitud) {
        return hacerPut("solicitud/Actualizar/" + nuevaSolicitud, nuevaSolicitud);
    }

    /**
     * Realiza una solicitud PUT para actualizar un tipo de trabajo en la base de datos.
     *
     * @param nuevoTipoTrabajo Objeto TipoTrabajoDTO con los nuevos datos.
     * @return true si la operación de actualización fue exitosa, false de lo contrario.
     */
    @Override
    public boolean ActualizarTipoDeTrabajo(TipoTrabajoDTO nuevoTipoTrabajo) {
        return hacerPut("tipoTrabajo/Actualizar/" + nuevoTipoTrabajo, nuevoTipoTrabajo);
    }

    /**
     * Realiza una solicitud PUT para actualizar un trabajo en la base de datos.
     *
     * @param nuevoTrabajo Objeto TrabajoDTO con los nuevos datos.
     * @return true si la operación de actualización fue exitosa, false de lo contrario.
     */
    @Override
    public boolean ActualizarTrabajo(TrabajoDTO nuevoTrabajo) {
        return hacerPut("trabajo/Actualizar/" + nuevoTrabajo, nuevoTrabajo);
    }

    /**
     * Realiza una solicitud PUT para actualizar un usuario en la base de datos.
     *
     * @param nuevoUsuario Objeto UsuarioDTO con los nuevos datos.
     * @return true si la operación de actualización fue exitosa, false de lo contrario.
     */
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
