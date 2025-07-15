package negocioImpl;

import negocio.CuentaNegocio;
import DAO.CuentaDAO;
import DAOimpl.CuentaDAOImpl;
import Excepciones.CampoInvalidoException;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoCuenta;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CuentaNegocioImpl implements CuentaNegocio {

	private CuentaDAO cuentaDAO;

	public CuentaNegocioImpl() {
		this.cuentaDAO = new CuentaDAOImpl();
	}

	@Override
	public boolean crearCuenta(Cuenta cuenta) throws SQLException, CampoInvalidoException {
		// try {
		// Validaciones de negocio, existe el dni en la bd como cliente?
		if (!cuentaDAO.existeDNI(cuenta.getCliente().getDni())) {
			return false;
		}
		// Validar que el cliente no tenga más de 3 cuentas
		if (cuentaDAO.contarCuentasPorCliente(cuenta.getCliente().getDni()) >= 3) {
			return false;
		}
//	    if (cuenta.getFechaCreacion().isAfter(LocalDate.now())) {
//	        throw new CampoInvalidoException("La fecha de creación no puede ser posterior a la fecha actual.");
//	    }
		cuenta.setCbu(generarCBU());
		return cuentaDAO.insertar(cuenta);
	}

	/// crear cbu cualquiera
	///
	private String generarCBU() {
		StringBuilder sb = new StringBuilder();
		ThreadLocalRandom rnd = ThreadLocalRandom.current();
		for (int i = 0; i < 22; i++)
			sb.append(rnd.nextInt(10));
		return sb.toString();
	}

	// c/celiii
	public List<Cuenta> listarCuentas() throws Exception {
		return cuentaDAO.listarCuentas();
	}
	
	public List<Cuenta> listarCuentasDadosBaja() throws Exception {
		return cuentaDAO.listarCuentasDadosBaja();
	}
	
	public List<Cuenta> listarPorRangoMonto(double min, double max) throws Exception {
		return cuentaDAO.listarPorRangoMonto(min, max);
	}
	
	@Override
	public boolean modificarCuenta(Cuenta cuenta) throws Exception {
	    if (cuenta.getFechaCreacion().isAfter(LocalDate.now())) {
	        throw new CampoInvalidoException("La fecha de creación no puede ser posterior a la fecha actual.");
	    }
		return cuentaDAO.modificarCuenta(cuenta);
	}

	@Override
	public boolean eliminarCuenta(int numeroCuenta) throws Exception {
		return cuentaDAO.eliminar(numeroCuenta);
	}

	// solicitud de prestamo cargar la ddl cuentas
	@Override
	public List<Cuenta> CargarDDlCuentas(String dni) throws Exception {
		return cuentaDAO.CargarDDlCuentas(dni);
	}

	public List<TipoCuenta> listarTiposCuenta() {
		CuentaDAO dao = new CuentaDAOImpl();
		return dao.obtenerTiposCuenta();
	}
	
	@Override
    public int obtenerNumeroCuentaPorCbu(String cbu) throws Exception {
        return cuentaDAO.obtenerNumeroCuentaPorCbu(cbu);
    }

	@Override
	public List<Cuenta> obtenerCuentasPorDni(String dni) throws Exception {
	    return cuentaDAO.obtenerCuentasPorDni(dni);
	}
	
}
