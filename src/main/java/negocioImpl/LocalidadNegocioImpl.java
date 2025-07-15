package negocioImpl;

import DAO.LocalidadDAO;
import DAOimpl.LocalidadDAOImpl;
import entidad.Localidad;
import negocio.LocalidadNegocio;

import java.util.List;

public class LocalidadNegocioImpl implements LocalidadNegocio{
	
	private LocalidadDAO localidadDAO = new LocalidadDAOImpl();

    public List<Localidad> listarPorProvincia(String provinciaId) throws Exception {
        return localidadDAO.obtenerPorProvincia(provinciaId);
    }
    
    public List<Localidad> obtenerTodasLocalidades() throws Exception{
    	return localidadDAO.obtenerTodasLocalidades();
    }
	
}
