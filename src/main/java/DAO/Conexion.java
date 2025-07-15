package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
	  public static Connection getConexion() {
		  Connection conn = null;
	        try {
	            // Driver correcto para 5.1.47
	            Class.forName("com.mysql.jdbc.Driver");
	            
	            String url = "jdbc:mysql://localhost:3306/TP_INT?useSSL=false";
	            String user = "root";
	            String password = "root";
	            
	            System.out.println("Intentando conectar a: " + url);
	            conn = DriverManager.getConnection(url, user, password);
	            
	            if (conn != null) {
	                System.out.println("Conexión exitosa!");
	            } else {
	                System.out.println("Conexión falló - conn es null");
	            }
	            
	        } catch (ClassNotFoundException e) {
	            System.out.println("Driver no encontrado: " + e.getMessage());
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.out.println("Error SQL: " + e.getMessage());
	            e.printStackTrace();
	        }
	        
	        return conn;
	    }

	
}