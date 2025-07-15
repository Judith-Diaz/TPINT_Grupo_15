package DAO;

import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoCuenta;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CuentaDAO {

	// Operaciones basicas
//    boolean insertar(Cuenta cuenta);
//    boolean actualizar(Cuenta cuenta);
	boolean eliminar(int numeroCuenta) throws Exception;

//    Cuenta obtenerPorId(int numeroCuenta);
	boolean modificarCuenta(Cuenta cuenta) throws Exception;

	List<Cuenta> listarCuentas() throws Exception;/// celi

	boolean insertar(Cuenta cuenta) throws SQLException;/// judi

	List<Cuenta> CargarDDlCuentas(String dni) throws Exception;/// carga la ddl de Solicitud Prestamo -altaPrestamo
	// Operaciones de consultas

	// List<Cuenta> obtenerPorClienteDNI(String clienteDNI);
//    boolean existeNumeroCuenta(int numeroCuenta);
//    boolean existeCBU(String cbu);
//    List<Cuenta> obtenerPorTipoCuenta(int tipoCuenta);
	int contarCuentasPorCliente(String clienteDNI);/// judi

	boolean existeDNI(String dni) throws SQLException;/// judi

	List<TipoCuenta> obtenerTiposCuenta();

	BigDecimal obtenerSaldo(int nroCuenta) throws Exception;

	boolean debitarMonto(int nroCuenta, BigDecimal monto) throws Exception;

	int obtenerNumeroCuentaPorCbu(String cbu) throws Exception;

	List<Cuenta> obtenerCuentasPorDni(String dni) throws Exception;

	List<Cuenta> listarCuentasDadosBaja() throws Exception;
	
	List<Cuenta> listarPorRangoMonto(double min, double max) throws Exception;
}