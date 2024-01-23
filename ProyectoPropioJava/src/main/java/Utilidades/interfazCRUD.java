package Utilidades;

import Dtos.AccesoDTO;
import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TipoTrabajoDTO;
import Dtos.TokenDTO;
import Dtos.TrabajoDTO;
import Dtos.UsuarioDTO;

public interface interfazCRUD {
	public UsuarioDTO SeleccionarUsuario(String url);
	
	public AccesoDTO SeleccionarAcceso(String url);
	
	public IncidenciaDTO SeleccionarIncidencia(String url);
	
	public SolicitudDTO SeleccionarSolicitud(String url);
	
	public TipoTrabajoDTO SeleccionarTipoDeTrabajo(String url);
	
	public TokenDTO SeleccionarToken(String url);
	
	public TrabajoDTO SeleccionarTrabajo(String url);
	
	public boolean InsertarUsuario(UsuarioDTO usuarioMeter);
	
	public boolean InsertarIncidencia(IncidenciaDTO incidenciaMeter);
	
	public boolean InsertarSolicitud(SolicitudDTO solicitudMeter);
	
	public boolean InsertarTipoDeTrabajo(TipoTrabajoDTO tipoTrabajoMeter);
	
	public boolean InsertarToken(TokenDTO tokenMeter);
	
	public boolean InsertarTrabajo(TrabajoDTO trabajoMeter);
	
	public boolean InsertarAcceso(AccesoDTO accesoMeter);
	
	public boolean EliminarAcceso(String url);
	
	public boolean EliminarIncidencia(String url);
	
	public boolean EliminarSolicitud(String url);
	
	public boolean EliminarTipoDeTrabajo(String url);
	
	public boolean EliminarToken(String url);
	
	public boolean EliminarTrabajo(String url);
	
	public boolean ActualizarAcceso(AccesoDTO nuevoAcceso);
	
	public boolean ActualizarIncidencia(IncidenciaDTO nuevaIncidencia);
	
	public boolean ActualizarSolicitud(SolicitudDTO nuevaSolicitud);
	
	public boolean ActualizarTipoDeTrabajo(TipoTrabajoDTO nuevoTipoTrabajo);
	
	public boolean ActualizarToken(TokenDTO nuevoToken);
	
	public boolean ActualizarTrabajo(TrabajoDTO nuevoTrabajo);
	
}
