package negocio;

import java.sql.SQLException;
import entidad.Usuario;

public interface UsuarioNegocio {
    Usuario validarUsuarioLogin(String usuario, String contrasenia);
    boolean agregarUsuario(Usuario usuario) throws SQLException;
    boolean actualizarPassword(String dni, String nuevaPassword) throws Exception; 
    //
    boolean validarCredenciales(String nombreUsuario, String contrasenia) throws Exception;
}
