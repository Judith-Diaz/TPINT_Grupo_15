package DAOimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.Conexion;
import DAO.UsuarioDAO;
import entidad.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {
	@Override
	public Usuario validarUsuarioLogin(String usuario, String contrasenia) {
		
		try {
			validarCredenciales( usuario,  contrasenia);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		System.out.println("=== INICIO validarUsuarioLogin ===");
		System.out.println("Usuario recibido: " + usuario);
		System.out.println("Contraseña recibida: " + contrasenia);

		String consulta = "SELECT * FROM usuarios   where Estado_Usu=1 and Nombre_Usu = ? AND Contrasenia_Usu = ?";
		try (Connection conn = Conexion.getConexion(); PreparedStatement st = conn.prepareStatement(consulta)) {

			System.out.println("Conexión obtenida: " + (conn != null ? "OK" : "NULL"));
			System.out.println("PreparedStatement creado: " + (st != null ? "OK" : "NULL"));

			st.setString(1, usuario);
			st.setString(2, contrasenia);

			try (ResultSet rs = st.executeQuery()) {
				System.out.println("ResultSet obtenido: " + (rs != null ? "OK" : "NULL"));
				if (rs.next()) {
					System.out.println("Usuario encontrado en BD!");
					Usuario user = new Usuario();
					user.setDniUsu(rs.getString("Dni_Usu"));
					user.setNombreUsu(rs.getString("Nombre_Usu"));
					user.setContraseniaUsu(rs.getString("Contrasenia_Usu"));
					user.setRolUsu(rs.getString("Rol_Usu").charAt(0));
					return user;
				} else {
					System.out.println("No se encontró usuario en BD");
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al validar usuario: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("=== FIN validarUsuarioLogin - Retornando NULL ===");
		return null;
	}
	
	
	//validacion 
	 public  boolean validarCredenciales(String nombreUsuario, String contrasenia) throws Exception {
	        if (nombreUsuario == null || contrasenia == null) {
	            throw new Exception("Usuario y contraseña son requeridos");
	        }
	        
	        if (nombreUsuario.trim().isEmpty() || contrasenia.trim().isEmpty()) {
	            throw new Exception("Usuario y contraseña no pueden estar vacíos");
	        }
	        
	        String sql = "SELECT COUNT(*) FROM Usuarios WHERE UPPER(Nombre_Usu) = UPPER(?) AND Contrasenia_Usu = ?";
	        
	        try (Connection conn = Conexion.getConexion(); 
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setString(1, nombreUsuario.trim());
	            ps.setString(2, contrasenia);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next() && rs.getInt(1) > 0) {
	                    return true;
	                }
	            }
	        }
	        
	        throw new Exception("Usuario o contraseña incorrectos");
	    }

	/// AGREGO UN USUARIO NUEVO ; PERO VIENE JUNTO CON EL ALTA DEL CLIENTE
	@Override
	public Boolean agregarUsuario(Usuario usuario) throws SQLException {
		String sql = "INSERT INTO Usuarios (DNI_Usu, Nombre_Usu, Contrasenia_Usu, Rol_Usu) VALUES (?, ?, ?, ?)";

		try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, usuario.getDniUsu());
			ps.setString(2, usuario.getNombreUsu());
			ps.setString(3, usuario.getContraseniaUsu());
			ps.setString(4, String.valueOf(usuario.getRolUsu())); // Asumiendo que rolUsu es char y C'

			int filas = ps.executeUpdate();
			return filas > 0; // true si insertó
		}
	}

	@Override
	public boolean actualizarPassword(String dni, String nuevaPassword) throws Exception {
	    if (existePasswordEnOtroUsuario(nuevaPassword, dni)) {
	        throw new Exception("LA CONTRASEÑA YA ESTÁ SIENDO UTILIZADA POR OTRO USUARIO");
	    }

	    String sql = "UPDATE Usuarios SET Contrasenia_Usu = ? WHERE Dni_Usu = ?";
	    try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, nuevaPassword);
	        ps.setString(2, dni);
	        return ps.executeUpdate() > 0;
	    }
	}
	
	@Override
	public boolean existePasswordEnOtroUsuario(String nuevaPassword, String dniActual) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM Usuarios WHERE Contrasenia_Usu = ? AND Dni_Usu <> ?";
	    try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, nuevaPassword);
	        ps.setString(2, dniActual);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    }
	    return false;
	}
}
	

	
