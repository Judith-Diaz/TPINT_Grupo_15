package DAOimpl;

import DAO.Conexion;
import DAO.CuentaDAO;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.Provincia;
import entidad.TipoCuenta;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAOImpl implements CuentaDAO {

	private PreparedStatement ps;

	private Connection getConnection() throws SQLException {// helpers para abriri conexion
		return Conexion.getConexion();
	}

	@Override
	public boolean existeDNI(String dni) throws SQLException {
		String consulta = "select  Nombre_Cl from clientes where DNI_Cl=?";
		try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(consulta)) {
			ps.setString(1, dni);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next(); // true si encontró almenos 1 fila
			}
		}
	}

	@Override
	public int contarCuentasPorCliente(String clienteDNI) { // cuentas que tiene cada cliente hasta 3 puede terner y
															// ademas este con estado 1
		String consulta = "SELECT COUNT(*) FROM Cuentas WHERE ClienteDNI_Cu = ? and Estado_Cu=1";
		try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(consulta)) {
			ps.setString(1, clienteDNI); // Ponemos el parámetro
			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1); // Devuelve la cantidad (número de filas)
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean insertar(Cuenta cuenta) throws SQLException {
		
		String consulta = "INSERT INTO Cuentas ( ClienteDNI_Cu, FechaCreacion_Cu, TipoCuenta_Cu, CBU_Cu, SALDO_Cu) VALUES (?, ?, ?, ?, ?)";
		try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(consulta)) {
			ps.setString(1, cuenta.getCliente().getDni());
			ps.setDate(2, java.sql.Date.valueOf(cuenta.getFechaCreacion()));
			
			ps.setInt(3, cuenta.getTipoCuenta().getIdTipoCuenta());
			ps.setString(4, cuenta.getCbu());
			ps.setDouble(5, cuenta.getSaldo());
			return ps.executeUpdate() == 1;
			
		}
	}

	@Override
	// ----------------------celii
	public List<Cuenta> listarCuentas() throws Exception {
		List<Cuenta> listaC = new ArrayList<>();
		String sql = "SELECT c.NumeroCuenta_Cu, c.ClienteDNI_Cu, c.FechaCreacion_Cu, c.TipoCuenta_Cu, c.CBU_Cu, c.SALDO_Cu, t.IdTipoCuenta_Tc, t.NombreTipo_Tc AS TipoCuenta FROM Cuentas c "
				+ "INNER JOIN TiposCuentas t ON c.TipoCuenta_Cu = t.IdTipoCuenta_Tc " + "WHERE c.Estado_Cu = TRUE";
		try (Connection conn = Conexion.getConexion();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Cuenta cuenta = new Cuenta();
				cuenta.setNumeroCuenta(rs.getInt("NumeroCuenta_Cu"));

				Cliente cliente = new Cliente();
				cliente.setDni(rs.getString("ClienteDNI_Cu"));
				cuenta.setCliente(cliente);

				cuenta.setFechaCreacion(rs.getDate("FechaCreacion_Cu").toLocalDate());

				TipoCuenta tipoCuenta = new TipoCuenta();
				tipoCuenta.setIdTipoCuenta(rs.getInt("IdTipoCuenta_Tc"));
				tipoCuenta.setNombreTipo(rs.getString("TipoCuenta"));
				cuenta.setTipoCuenta(tipoCuenta);

				cuenta.setCbu(rs.getString("CBU_Cu"));
				cuenta.setSaldo(rs.getDouble("SALDO_Cu"));
				listaC.add(cuenta);
			}
		}
		return listaC;
	}
	
	public List<Cuenta> listarCuentasDadosBaja() throws Exception {
		List<Cuenta> listaC = new ArrayList<>();
		String sql = "SELECT c.NumeroCuenta_Cu, c.ClienteDNI_Cu, c.FechaCreacion_Cu, c.TipoCuenta_Cu, c.CBU_Cu, c.SALDO_Cu, "
				+ "t.IdTipoCuenta_Tc, t.NombreTipo_Tc AS TipoCuenta "
				+ "FROM Cuentas c "
				+ "INNER JOIN TiposCuentas t ON c.TipoCuenta_Cu = t.IdTipoCuenta_Tc "
				+ "WHERE c.Estado_Cu = FALSE";
		try (Connection conn = Conexion.getConexion();
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Cuenta cuenta = new Cuenta();
				cuenta.setNumeroCuenta(rs.getInt("NumeroCuenta_Cu"));

				Cliente cliente = new Cliente();
				cliente.setDni(rs.getString("ClienteDNI_Cu"));
				cuenta.setCliente(cliente);

				cuenta.setFechaCreacion(rs.getDate("FechaCreacion_Cu").toLocalDate());

				TipoCuenta tipoCuenta = new TipoCuenta();
				tipoCuenta.setIdTipoCuenta(rs.getInt("IdTipoCuenta_Tc"));
				tipoCuenta.setNombreTipo(rs.getString("TipoCuenta"));
				cuenta.setTipoCuenta(tipoCuenta);

				cuenta.setCbu(rs.getString("CBU_Cu"));
				cuenta.setSaldo(rs.getDouble("SALDO_Cu"));

				listaC.add(cuenta);
			}
		}
		return listaC;
	}
	
	public List<Cuenta> listarPorRangoMonto(double min, double max) throws Exception {
		List<Cuenta> listaC = new ArrayList<>();
		String sql = "SELECT c.NumeroCuenta_Cu, c.ClienteDNI_Cu, c.FechaCreacion_Cu, c.TipoCuenta_Cu, c.CBU_Cu, c.SALDO_Cu, "
				+ "t.IdTipoCuenta_Tc, t.NombreTipo_Tc AS TipoCuenta "
				+ "FROM Cuentas c "
				+ "INNER JOIN TiposCuentas t ON c.TipoCuenta_Cu = t.IdTipoCuenta_Tc "
				+ "WHERE c.Estado_Cu = TRUE AND c.SALDO_Cu BETWEEN ? AND ?";
		try (Connection conn = Conexion.getConexion();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setDouble(1, min);
			ps.setDouble(2, max);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Cuenta cuenta = new Cuenta();
					cuenta.setNumeroCuenta(rs.getInt("NumeroCuenta_Cu"));

					Cliente cliente = new Cliente();
					cliente.setDni(rs.getString("ClienteDNI_Cu"));
					cuenta.setCliente(cliente);

					cuenta.setFechaCreacion(rs.getDate("FechaCreacion_Cu").toLocalDate());

					TipoCuenta tipoCuenta = new TipoCuenta();
					tipoCuenta.setIdTipoCuenta(rs.getInt("IdTipoCuenta_Tc"));
					tipoCuenta.setNombreTipo(rs.getString("TipoCuenta"));
					cuenta.setTipoCuenta(tipoCuenta);

					cuenta.setCbu(rs.getString("CBU_Cu"));
					cuenta.setSaldo(rs.getDouble("SALDO_Cu"));

					listaC.add(cuenta);
				}
			}
		}
		return listaC;
	}

	@Override
	public boolean modificarCuenta(Cuenta cuenta) throws SQLException {
		String consulta = "UPDATE Cuentas SET FechaCreacion_Cu=?, TipoCuenta_Cu=? WHERE NumeroCuenta_Cu=?";
		try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(consulta)) {
			ps.setDate(1, Date.valueOf(cuenta.getFechaCreacion()));
			ps.setInt(2, cuenta.getTipoCuenta().getIdTipoCuenta());
			ps.setInt(3, cuenta.getNumeroCuenta());
			int filas = ps.executeUpdate();
			System.out.println("Filas modificadas: " + filas);
			return filas > 0;
		}
	}

	@Override
	public boolean eliminar(int numeroCuenta) throws Exception {
		String sql = "UPDATE Cuentas SET Estado_Cu = FALSE WHERE NumeroCuenta_Cu = ?";
		try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, numeroCuenta);
			return ps.executeUpdate() > 0;
		}
	}

	public List<Cuenta> CargarDDlCuentas(String dni) {
		List<Cuenta> lista = new ArrayList<>();
		try (Connection conn = Conexion.getConexion()) {
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT cuentas.SALDO_Cu, cuentas.NumeroCuenta_Cu, cuentas.ClienteDNI_Cu, cuentas.TipoCuenta_Cu, cuentas.CBU_Cu, t.IdTipoCuenta_Tc, t.NombreTipo_Tc " +
				"FROM cuentas INNER JOIN TiposCuentas t ON cuentas.TipoCuenta_Cu = t.IdTipoCuenta_Tc " +
				"WHERE cuentas.Estado_Cu = 1 AND cuentas.ClienteDNI_Cu = ?");
			stmt.setString(1, dni);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Cuenta c = new Cuenta();
				c.setNumeroCuenta(rs.getInt("NumeroCuenta_Cu"));
				c.setCbu(rs.getString("CBU_Cu"));
				c.setSaldo(rs.getBigDecimal("SALDO_Cu").doubleValue());

				// 🛠 Crear el objeto TipoCuenta y setearlo en Cuenta
				TipoCuenta tipoCuenta = new TipoCuenta();
				tipoCuenta.setIdTipoCuenta(rs.getInt("IdTipoCuenta_Tc"));
				tipoCuenta.setNombreTipo(rs.getString("NombreTipo_Tc"));
				c.setTipoCuenta(tipoCuenta);

				lista.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<TipoCuenta> obtenerTiposCuenta() {
		List<TipoCuenta> tipos = new ArrayList<>();
		try (Connection conn = Conexion.getConexion();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT IdTipoCuenta_Tc, NombreTipo_Tc FROM TiposCuentas")) {

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				TipoCuenta tipo = new TipoCuenta();
				tipo.setIdTipoCuenta(rs.getInt("IdTipoCuenta_Tc"));
				tipo.setNombreTipo(rs.getString("NombreTipo_Tc"));
				tipos.add(tipo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tipos;
	}

	@Override
	public BigDecimal obtenerSaldo(int nroCuenta) throws Exception {
		BigDecimal saldo = BigDecimal.ZERO;
		String query = "SELECT Saldo_Cu FROM Cuentas WHERE NumeroCuenta_Cu = ?";

		try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, nroCuenta);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				saldo = rs.getBigDecimal("Saldo_Cu");
			}
		}
		return saldo;
	}

	@Override
	public boolean debitarMonto(int nroCuenta, BigDecimal monto) throws Exception {
		String query = "UPDATE Cuentas SET Saldo_Cu = Saldo_Cu - ? WHERE NumeroCuenta_Cu = ?";

		try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setBigDecimal(1, monto);
			stmt.setInt(2, nroCuenta);

			return stmt.executeUpdate() == 1;
		}
	}
	
	public int obtenerNumeroCuentaPorCbu(String cbu) throws Exception {
	    int numeroCuenta = -1;
	    String sql = "SELECT NumeroCuenta_Cu FROM Cuentas WHERE CBU_Cu = ?";
	    try (Connection con = Conexion.getConexion();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, cbu);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                numeroCuenta = rs.getInt("NumeroCuenta_Cu");
	            }
	        }
	    }
	    return numeroCuenta;
	}
	
	@Override
	public List<Cuenta> obtenerCuentasPorDni(String dni) throws Exception {
	    List<Cuenta> lista = new ArrayList<>();
	    String sql = "SELECT * FROM Cuentas WHERE ClienteDNI_Cu = ? AND Estado_Cu = 1"; // adapta el nombre del campo
	    try (Connection conn = Conexion.getConexion();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, dni);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Cuenta c = new Cuenta();
	                c.setNumeroCuenta(rs.getInt("NumeroCuenta_Cu"));
	                c.setSaldo(rs.getDouble("Saldo_Cu")); // o como tengas el saldo
	                // Completar otros campos si necesitás
	                lista.add(c);
	            }
	        }
	    }
	    return lista;
	}
}
