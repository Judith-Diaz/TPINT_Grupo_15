package DAOimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import DAO.Conexion;
import DAO.MovimientoDAO;
import entidad.Cuenta;
import entidad.Movimiento;
import entidad.TipoMovimiento;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovimientoDAOimpl implements MovimientoDAO {
	@Override
	public List<Movimiento> obtenerMovimientosPorCuenta(int numeroCuenta) throws Exception {
		List<Movimiento> lista = new ArrayList<>();

		String sql = "SELECT m.*, tm.IdTipoMovimiento_Tm, tm.Descripcion_Tm " + "FROM Movimientos m "
				+ "JOIN TiposMovimientos tm ON m.IdTipoMovimiento_Mv = tm.IdTipoMovimiento_Tm "
				+ "WHERE m.NumeroCuenta_Mv = ?";
		try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, numeroCuenta);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Movimiento mov = new Movimiento();
					mov.setIDMovimiento(rs.getInt("IDMovimiento_Mv"));
					mov.setFecha(rs.getDate("Fecha_Mv"));
					mov.setDetalle(rs.getString("Detalle_Mv"));
					mov.setImporte(rs.getBigDecimal("Importe_Mv"));
					Cuenta cuentaOrigen = new Cuenta();
					cuentaOrigen.setNumeroCuenta(rs.getInt("NumeroCuenta_Mv"));
					mov.setCuenta(cuentaOrigen);
					TipoMovimiento tipo = new TipoMovimiento();
					tipo.setIdTipoMovimiento(rs.getInt("IdTipoMovimiento_Tm"));
					tipo.setDescripcion(rs.getString("Descripcion_Tm"));
					mov.setTipoMovimiento(tipo);
					lista.add(mov);
				}
			}
		}
		return lista;
	}

	@Override
	public List<Movimiento> obtenerMovimientosPorCuentaYRango(int numeroCuenta, String fechaDesde, String fechaHasta)
			throws SQLException {
		List<Movimiento> lista = new ArrayList<>();
		String sql = "SELECT m.*, tm.IdTipoMovimiento_Tm, tm.Descripcion_Tm " + "FROM Movimientos m "
				+ "JOIN TiposMovimientos tm ON m.IdTipoMovimiento_Mv = tm.IdTipoMovimiento_Tm "
				+ "WHERE m.NumeroCuenta_Mv = ? AND m.Fecha_Mv BETWEEN ? AND ?";
		try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, numeroCuenta);
			ps.setString(2, fechaDesde);
			ps.setString(3, fechaHasta);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Movimiento mov = new Movimiento();
					mov.setIDMovimiento(rs.getInt("IDMovimiento_Mv"));
					mov.setFecha(rs.getDate("Fecha_Mv"));
					mov.setDetalle(rs.getString("Detalle_Mv"));
					mov.setImporte(rs.getBigDecimal("Importe_Mv"));
					Cuenta cuentaOrigen = new Cuenta();
					cuentaOrigen.setNumeroCuenta(rs.getInt("NumeroCuenta_Mv"));
					mov.setCuenta(cuentaOrigen);
					TipoMovimiento tipo = new TipoMovimiento();
					tipo.setIdTipoMovimiento(rs.getInt("IdTipoMovimiento_Tm"));
					tipo.setDescripcion(rs.getString("Descripcion_Tm"));
					mov.setTipoMovimiento(tipo);
					lista.add(mov);
				}
			}
		}
		return lista;
	}

	public boolean registrarMovimiento(Movimiento movimiento) throws Exception {
		String sql = "INSERT INTO Movimientos (Fecha_Mv, Detalle_Mv, Importe_Mv, NumeroCuenta_Mv, IdTipoMovimiento_Mv) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setDate(1, new java.sql.Date(movimiento.getFecha().getTime()));
			ps.setString(2, movimiento.getDetalle());
			ps.setBigDecimal(3, movimiento.getImporte());
			ps.setInt(4, movimiento.getCuenta().getNumeroCuenta());
			ps.setInt(5, movimiento.getTipoMovimiento().getIdTipoMovimiento());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Error al registrar movimiento DAO", e);
		}
	}
}
