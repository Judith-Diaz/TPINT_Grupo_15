package DAOimpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.Conexion;
import DAO.PrestamoDAO;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.Cuota;
import entidad.Prestamo;

public class PrestamoDAOimpl implements PrestamoDAO {
	private Connection getConnection() throws SQLException {// helpers para abriri conexion
		return Conexion.getConexion();
	}

	@Override
	public boolean CargarSolicitud(Prestamo prestamo) throws SQLException {
		String consulta = "insert into prestamos(NumeroCuenta_Pr,DNICliente_Pr,ImporteTotal_Pr, Fecha_Pr, ImporteSolicitado_Pr, Cuotas_Pr,  Estado_Pr, PlazoPagoMeses_Pr,MontoMensual_Pr) VALUES (?,?,?, ?, ?, ?, ?, ?, ?)";
		try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement(consulta)) {
	        ps.setInt(1, prestamo.getCuenta().getNumeroCuenta());
	        ps.setString(2, prestamo.getCliente().getDni());
			ps.setBigDecimal(3, prestamo.getImporteTotal()); // DECIMAL / BigDecimal
			ps.setDate(4, java.sql.Date.valueOf(prestamo.getFecha())); // LocalDate / java.sql.Date
			ps.setBigDecimal(5, prestamo.getImporteSolicitado()); // DECIMAL / BigDecimal
			ps.setString(6, prestamo.getCuotas());
			ps.setString(7, prestamo.getEstado());
			ps.setInt(8, prestamo.getPlazoMeses());
			ps.setBigDecimal(9, prestamo.getMontoMensual());
			return ps.executeUpdate() == 1;
		}
	}

	public List<Prestamo> listarPrestamos() throws Exception {
		List<Prestamo> listaP = new ArrayList<>();
		String sql = "select p.IdPrestamo_Pr, p.NumeroCuenta_Pr,p.DNICliente_Pr, p.Fecha_Pr, p.ImporteSolicitado_Pr, p.Cuotas_Pr, p.MontoMensual_Pr from Prestamos p where p.Estado_Pr ='P'";
	    try (Connection conn = Conexion.getConexion();
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ResultSet rs = ps.executeQuery()) {
	           while (rs.next()) {
	               Prestamo prestamo = new Prestamo();
	               prestamo.setIdPrestamo(rs.getInt("IdPrestamo_Pr"));
	               // Cliente
	               Cliente cliente = new Cliente();
	               cliente.setDni(rs.getString("DNICliente_Pr"));
	               prestamo.setCliente(cliente);
	               // Cuenta
	               Cuenta cuenta = new Cuenta();
	               cuenta.setNumeroCuenta(rs.getInt("NumeroCuenta_Pr"));
	               prestamo.setCuenta(cuenta);
	               prestamo.setFecha(rs.getDate("Fecha_Pr").toLocalDate());
	               prestamo.setImporteSolicitado(rs.getBigDecimal("ImporteSolicitado_Pr"));
	               prestamo.setCuotas(rs.getString("Cuotas_Pr"));
	               prestamo.setMontoMensual(rs.getBigDecimal("MontoMensual_Pr"));
	               listaP.add(prestamo);
	           }
	       }
	       return listaP;
	}

	// me carga los prestamos en clientes para que paguen
	public List<Cuota> listarPagarPrestamos() throws Exception {
		List<Cuota> listaC = new ArrayList<>();
		String sql = "select  FechaVencimiento_Cuo ,IDPrestamo_Cuo, NroCuenta_Cuo, NroCuota_Cuo, MontoCuota_Cuo from Cuotas   where Estado_Cuo ='D'";
	    try (Connection conn = Conexion.getConexion();
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ResultSet rs = ps.executeQuery()) {
	           while (rs.next()) {
	               Cuota cuota = new Cuota();
	               cuota.setFechaVencimiento(rs.getDate("FechaVencimiento_Cuo"));
	               // Prestamo
	               Prestamo prestamo = new Prestamo();
	               prestamo.setIdPrestamo(rs.getInt("IDPrestamo_Cuo"));
	               cuota.setPrestamo(prestamo);
	               // Cuenta
	               Cuenta cuenta = new Cuenta();
	               cuenta.setNumeroCuenta(rs.getInt("NroCuenta_Cuo"));
	               cuota.setCuenta(cuenta);
	               cuota.setNroCuota(rs.getInt("NroCuota_Cuo"));
	               cuota.setMontoCuota(rs.getBigDecimal("MontoCuota_Cuo"));

	               listaC.add(cuota);
	           }
	       }
	       return listaC;
	}

	// le cambio el estado de P a A aprobado en la tabla de prestamo
	public boolean estadoP(int idPrestamo) throws SQLException {
		String consulta = "update Prestamos set Estado_Pr='A' where idPrestamo_Pr= ? and Estado_Pr='P'";
		try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement(consulta)) {
			ps.setInt(1, idPrestamo);
			int filasAfectadas = ps.executeUpdate();
			System.out.println("filasAfectadas------------------------------ = " + filasAfectadas);
			if (filasAfectadas == 1) {
				return true;// aca hago que si o si se me valla para el lado true
			} else {
				return filasAfectadas > 0;
			} // devuelve true si filasAfectadas es 1, false si es 0
		}
	}

	// le cambio el estado de P a R rechazado en la tabla de prestamo
	public boolean estadoR(int idPrestamo) throws SQLException {

		String consulta = "update Prestamos set Estado_Pr='R' where idPrestamo_Pr= ? and Estado_Pr='P'";
		try (Connection cn = getConnection(); PreparedStatement ps = cn.prepareStatement(consulta)) {
			ps.setInt(1, idPrestamo);

			int filasAfectadas = ps.executeUpdate();
			System.out.println("filasAfectadas------------------------------ = " + filasAfectadas);
			if (filasAfectadas == 1) {

				return true;// aca hago que si o si se me valla para el lado true
			} else {
				return filasAfectadas > 0;
			} // devuelve true si filasAfectadas es 1, false si es 0

		}
	}
	
	public List<Cuota> listarPagarPrestamosPorDni(String dni) throws Exception {
	    List<Cuota> lista = new ArrayList<>();
	    String sql = "SELECT cu.*, p.DNICliente_Pr FROM Cuotas cu " +
	                 "INNER JOIN Prestamos p ON cu.IDPrestamo_Cuo = p.IdPrestamo_Pr " +
	                 "WHERE p.DNICliente_Pr = ? AND cu.Estado_Cuo = 'D'";
	    try (Connection conn = Conexion.getConexion();
	         PreparedStatement ps = conn.prepareStatement(sql)) {      
	        ps.setString(1, dni);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Cuota cuota = new Cuota();
	                cuota.setFechaVencimiento(rs.getDate("FechaVencimiento_Cuo"));

	                Prestamo prestamo = new Prestamo();
	                prestamo.setIdPrestamo(rs.getInt("IDPrestamo_Cuo"));

	                Cliente cliente = new Cliente();
	                cliente.setDni(rs.getString("DNICliente_Pr")); // ← NECESARIO
	                prestamo.setCliente(cliente);

	                cuota.setPrestamo(prestamo);

	                Cuenta cuenta = new Cuenta();
	                cuenta.setNumeroCuenta(rs.getInt("NroCuenta_Cuo"));
	                cuota.setCuenta(cuenta);

	                cuota.setNroCuota(rs.getInt("NroCuota_Cuo"));
	                cuota.setMontoCuota(rs.getBigDecimal("MontoCuota_Cuo"));
	                lista.add(cuota);
	            }
	        }
	    }
	    return lista;
	}
}
