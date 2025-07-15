package DAO;

import java.sql.SQLException;
import java.util.List;
import entidad.Cliente;

public interface ClienteDAO {
	 boolean agregarCliente(Cliente cliente) throws Exception;
	 boolean eliminarCliente(String dni) throws SQLException;
	 boolean modificarCliente(Cliente cliente) throws Exception;
	 Cliente obtenerClientePorDni(String dni) throws SQLException;
	 List<Cliente> listarClientes() throws SQLException;
	 boolean existeClientePorDniOCuil(String dni, String cuil) throws SQLException;
	 boolean existeClientePorDni(String dni) throws SQLException ;
	 boolean existeNombreApellidoEnOtroCliente(String nombre, String apellido, String dniActual) throws SQLException;
	 List<Cliente> listarClientesDadosBaja() throws SQLException;
}
