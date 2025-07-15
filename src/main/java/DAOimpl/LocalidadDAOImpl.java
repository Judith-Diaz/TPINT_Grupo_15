package DAOimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Conexion;
import DAO.LocalidadDAO;
import entidad.Localidad;

public class LocalidadDAOImpl implements LocalidadDAO {

	public List<Localidad> obtenerPorProvincia(String provinciaId) {
		List<Localidad> localidades = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = Conexion.getConexion();
			String provinciaIdFormateado = String.format("%02d", Integer.parseInt(provinciaId));
			System.out.println("Provincia ID recibido: " + provinciaId);
			System.out.println("Provincia ID formateado: " + provinciaIdFormateado);
			String sql = "SELECT IdLocalidad_Loc, IdProvincia_Loc, descripcion_Loc FROM Localidades WHERE IdProvincia_Loc = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, provinciaIdFormateado);
			System.out.println("Ejecutando consulta: " + sql + " con parámetro: " + provinciaIdFormateado);
			rs = ps.executeQuery();
			while (rs.next()) {
				Localidad loc = new Localidad();
				loc.setIdLocalidad_Loc(rs.getString("IdLocalidad_Loc"));
				loc.setIdProvincia_Loc(rs.getString("IdProvincia_Loc"));
				loc.setDescripcion_Loc(rs.getString("descripcion_Loc"));
				localidades.add(loc);
				System.out.println("Localidad encontrada: " + loc.getDescripcion_Loc());
			}

			System.out.println("Total localidades encontradas: " + localidades.size());

		} catch (SQLException e) {
			System.err.println("Error SQL en obtenerPorProvincia: " + e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.err.println("Error al formatear provincia ID: " + provinciaId);
			e.printStackTrace();
		} finally {
			// Cerrar rs, ps, conn
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return localidades;
	}
	
	public List<Localidad> obtenerTodasLocalidades() {
		List<Localidad> localidades = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Conexion.getConexion();
			String sql = "SELECT IdLocalidad_Loc, IdProvincia_Loc, descripcion_Loc FROM Localidades";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Localidad loc = new Localidad();
				loc.setIdLocalidad_Loc(rs.getString("IdLocalidad_Loc"));
				loc.setIdProvincia_Loc(rs.getString("IdProvincia_Loc"));
				loc.setDescripcion_Loc(rs.getString("descripcion_Loc"));
				localidades.add(loc);
			}
		} catch (SQLException e) {
			System.err.println("Error SQL en listarTodasLasLocalidades: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return localidades;
	}
}
