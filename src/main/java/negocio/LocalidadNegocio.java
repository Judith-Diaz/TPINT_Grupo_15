package negocio;

import entidad.Localidad;
import java.util.List;

public interface LocalidadNegocio {
	List<Localidad> listarPorProvincia(String provinciaId) throws Exception;
	List<Localidad> obtenerTodasLocalidades( ) throws Exception;
}
