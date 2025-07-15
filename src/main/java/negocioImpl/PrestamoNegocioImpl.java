package negocioImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import DAO.PrestamoDAO;
import DAOimpl.PrestamoDAOimpl;
import Excepciones.SaldoInsuficienteException;
import DAO.CuotaDAO;
import DAO.CuentaDAO;
import DAO.MovimientoDAO;
import DAOimpl.CuentaDAOImpl;
import DAOimpl.CuotaDAOImpl;
import DAOimpl.MovimientoDAOimpl;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.Cuota;
import entidad.Prestamo;
import entidad.Movimiento;
import entidad.TipoMovimiento;
import negocio.MovimientoNegocio;
import negocio.PrestamoNegocio;
import negocio.CuentaNegocio;

public class PrestamoNegocioImpl implements PrestamoNegocio {

	private PrestamoDAO prestamoDAO = new PrestamoDAOimpl();
	private CuentaDAOImpl cuentaDAO = new CuentaDAOImpl();
	private CuotaDAOImpl cuotaDAO = new CuotaDAOImpl();
	private MovimientoDAO movimientoDAO = new MovimientoDAOimpl();

	//
	@Override
	public boolean SolicitarPrestamo(Prestamo prestamo) throws Exception {
		return prestamoDAO.CargarSolicitud(prestamo);
	}

	// prestamos listar los que tienene estado P
	@Override
	public List<Prestamo> listarPrestamos() throws Exception {
		return prestamoDAO.listarPrestamos();
	}

	// prestamos pendienetes de pago, con D
	@Override
	public List<Cuota> listarPagarPrestamos() throws Exception {
		return prestamoDAO.listarPagarPrestamos();
	}

	// cambi el estado del prestamo de P a A aprobado

	@Override
	public boolean cambiarEstadoP(int idPrestamo) throws Exception {
		return prestamoDAO.estadoP(idPrestamo);
	}

	// cambi el estado del prestamo de P a reprobado
	@Override
	public boolean cambiarEstadoR(int idPrestamo) throws Exception {
		return prestamoDAO.estadoR(idPrestamo);
	}

	public boolean pagarCuota(int idPrestamo, int nroCuota, int nroCuenta)
			throws Exception, SaldoInsuficienteException {
		BigDecimal saldo = cuentaDAO.obtenerSaldo(nroCuenta);
		List<Cuota> cuotas = listarPagarPrestamos();
		Cuota cuotaPagar = null;
		for (Cuota c : cuotas) {
			if (c.getPrestamo().getIdPrestamo() == idPrestamo && c.getNroCuota() == nroCuota) {
				cuotaPagar = c;
				break;
			}
		}
		if (cuotaPagar == null)
			return false;
		BigDecimal monto = cuotaPagar.getMontoCuota();
		if (saldo.compareTo(monto) < 0) {
			throw new SaldoInsuficienteException("ERROR AL PAGAR LA CUOTA, SALDO INSUFICIENTE");
		}
		boolean debitoOK = cuentaDAO.debitarMonto(nroCuenta, monto);
		boolean cuotaPagadaOK = cuotaDAO.marcarCuotaPagada(idPrestamo, nroCuota);
		if (debitoOK && cuotaPagadaOK) {
			Movimiento movimiento = new Movimiento();
			// Fecha: usa java.sql.Date para tu entidad
			movimiento.setFecha(new java.sql.Date(System.currentTimeMillis()));
			movimiento.setDetalle("Pago Cuota Prestamo");
			movimiento.setImporte(monto.negate());
			// Setear cuenta (origen) como objeto Cuenta
			Cuenta cuentaOrigen = new Cuenta();
			cuentaOrigen.setNumeroCuenta(nroCuenta);
			movimiento.setCuenta(cuentaOrigen);
			// TipoMovimiento: 3 es Pago de préstamo (según tu sistema)
			TipoMovimiento tipoMovimiento = new TipoMovimiento();
			tipoMovimiento.setIdTipoMovimiento(3);
			movimiento.setTipoMovimiento(tipoMovimiento);
			boolean movOk = movimientoDAO.registrarMovimiento(movimiento);
			if (!movOk) {
				throw new Exception("Error al registrar movimiento del pago.");
			}
			return true;
		}
		return false;
	}

	@Override
	public List<Cuota> listarPagarPrestamosPorDni(String dni) throws Exception {
		return prestamoDAO.listarPagarPrestamosPorDni(dni);
	}
}
