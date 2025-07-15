package DAOimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.Conexion;
import DAO.TransferenciaDAO;
import entidad.Cuenta;
import entidad.Transferencia;

public class TransferenciaDAOimpl implements TransferenciaDAO {
	private Connection getConnection() throws SQLException {// helpers para abriri conexion
		return Conexion.getConexion();
	}

	@Override
	public boolean insertarTransferencia(Transferencia tra) {
		String sql = "INSERT INTO Transferencias "
				+ "(NroCuentaOrigen_Tra, NroCuentaDestino_Tra, Fechao_Tra, Importe_Tra, Estado_Tra) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, tra.getNroCuentaOrigen());
			ps.setInt(2, tra.getNroCuentaDestino());
			ps.setDate(3, Date.valueOf(tra.getFecha()));
			ps.setBigDecimal(4, tra.getImporte());
			ps.setString(5, String.valueOf(tra.getEstado()));
			int filas = ps.executeUpdate();
			return filas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertarTransferenciaCBU(Cuenta cuenta, Transferencia tra) {
		String sql = "INSERT INTO Transferencias "
				+ "(NroCuentaOrigen_Tra, NroCuentaDestino_Tra, Fechao_Tra, Importe_Tra, Estado_Tra) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, tra.getNroCuentaOrigen());
			ps.setInt(2, cuenta.getNumeroCuenta());
			ps.setDate(3, Date.valueOf(tra.getFecha()));
			ps.setBigDecimal(4, tra.getImporte());
			ps.setString(5, String.valueOf(tra.getEstado()));
			int filas = ps.executeUpdate();
			return filas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean CBUexiste(Cuenta cbu) {
		String sql = "SELECT * FROM Cuentas WHERE CBU_Cu = ? AND Estado_Cu = 1";
		try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, cbu.getCbu());

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Cuenta obtenerPorCBU(Cuenta cuenta) {
		String sql = "SELECT NumeroCuenta_Cu, CBU_Cu FROM Cuentas WHERE CBU_Cu = ? AND Estado_Cu = 1";
		try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, cuenta.getCbu());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Cuenta c = new Cuenta();
					c.setNumeroCuenta(rs.getInt("NumeroCuenta_Cu"));
					c.setCbu(rs.getString("CBU_Cu"));
					return c;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
