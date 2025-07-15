package negocioImpl;

import DAO.ProvinciaDAO;
import DAOimpl.ProvinciaDAOImpl;
import entidad.Provincia;
import negocio.ProvinciaNegocio;

import java.util.List;

public class ProvinciaNegocioImpl  implements ProvinciaNegocio {
    private ProvinciaDAO provinciaDAO = new ProvinciaDAOImpl();

    public List<Provincia> listarProvincias() throws Exception {
        return provinciaDAO.obtenerTodas();
    }
}
