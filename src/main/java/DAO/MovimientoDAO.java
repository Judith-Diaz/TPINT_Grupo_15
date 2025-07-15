package DAO;

import java.sql.SQLException;
import java.util.List;
import entidad.Movimiento;

public interface MovimientoDAO {
	List<Movimiento> obtenerMovimientosPorCuenta(int numeroCuenta) throws Exception;
	List<Movimiento>obtenerMovimientosPorCuentaYRango(int numeroCuenta,String fechaDesde,String fechaHasta) throws SQLException;
	boolean registrarMovimiento(Movimiento movimiento) throws Exception;

}
