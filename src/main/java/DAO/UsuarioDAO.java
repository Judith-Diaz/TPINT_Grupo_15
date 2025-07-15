package DAO;

import java.sql.SQLException;

import entidad.Usuario;

public interface UsuarioDAO {
	    Usuario validarUsuarioLogin(String usuario, String contrasenia);
	    Boolean agregarUsuario(Usuario objCargado) throws SQLException;; 
	    boolean actualizarPassword(String dni, String nuevaPassword) throws Exception;
	     boolean validarCredenciales(String nombreUsuario, String contrasenia) throws Exception;
		boolean existePasswordEnOtroUsuario(String nuevaPassword, String dniActual) throws SQLException;
	}

