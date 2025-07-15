package DAO;


import java.util.List;
import entidad.Localidad;

public interface LocalidadDAO {

	List<Localidad> obtenerPorProvincia(String provinciaId) throws Exception;
	List<Localidad> obtenerTodasLocalidades() throws Exception;

}
