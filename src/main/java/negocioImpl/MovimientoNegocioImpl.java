package negocioImpl;

import java.util.List;
import entidad.Movimiento;
import DAO.MovimientoDAO;
import DAOimpl.MovimientoDAOimpl;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio{
	private MovimientoDAO movimientoDAO = new MovimientoDAOimpl();

    @Override
    public List<Movimiento> obtenerMovimientosPorCuenta(int numeroCuenta) throws Exception {
        return movimientoDAO.obtenerMovimientosPorCuenta(numeroCuenta);
    }
    
    
    @Override
    public List<Movimiento> ListarMovimientosPorRango(int numeroCuenta,String fechaDesde,String fechaHasta) throws Exception {
    	return movimientoDAO.obtenerMovimientosPorCuentaYRango(numeroCuenta, fechaDesde, fechaHasta);
    }
    
    @Override
    public boolean registrarMovimiento(Movimiento movimiento) throws Exception {
        return movimientoDAO.registrarMovimiento(movimiento);
    }
}
