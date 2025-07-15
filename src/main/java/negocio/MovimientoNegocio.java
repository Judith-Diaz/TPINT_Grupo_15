package negocio;

import java.util.List;
import entidad.Movimiento;

public interface MovimientoNegocio {
	List<Movimiento> obtenerMovimientosPorCuenta(int numeroCuenta) throws Exception;
	List<Movimiento>ListarMovimientosPorRango(int numeroCuenta,String fechaDesde,String fechaHasta) throws Exception;
	boolean registrarMovimiento(Movimiento movimiento) throws Exception;

}
