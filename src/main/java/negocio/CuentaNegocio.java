package negocio;

import entidad.Cuenta;
import entidad.TipoCuenta;

import java.sql.SQLException;
import java.util.List;

import Excepciones.CampoInvalidoException;

public interface CuentaNegocio {

	// Operaciones ABML con validaciones de negocio
	boolean crearCuenta(Cuenta cuenta) throws SQLException, CampoInvalidoException;/// usio judi
	boolean modificarCuenta(Cuenta cuenta) throws Exception;
	boolean eliminarCuenta(int numeroCuenta) throws Exception;
	List<Cuenta> listarCuentas() throws Exception;/// uso celi
	List<Cuenta> listarCuentasDadosBaja() throws Exception;
	List<Cuenta> listarPorRangoMonto(double min, double max) throws Exception;
	List<Cuenta>CargarDDlCuentas(String dni) throws Exception;//solicitar prestamo -altaPrestamo
	List<TipoCuenta> listarTiposCuenta();
	int obtenerNumeroCuentaPorCbu(String cbu) throws Exception;
	List<Cuenta> obtenerCuentasPorDni(String dni) throws Exception;
}