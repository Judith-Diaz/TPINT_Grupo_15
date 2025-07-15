package negocio;

import entidad.Cliente;
import java.util.List;

import Excepciones.CampoInvalidoException;
import Excepciones.ContraseñaNoCoincideException;
import Excepciones.EdadInvalidaException;


public interface ClienteNegocio {
	  boolean agregarCliente(Cliente cliente) throws Exception;
	  boolean eliminarCliente(String dni) throws Exception;
	  boolean modificarCliente(Cliente cliente) throws Exception;
	  Cliente obtenerClientePorDni(String dni) throws Exception;
	  List<Cliente> listarClientes() throws Exception;
	  List<Cliente> listarClientesDadosBaja() throws Exception;
	  boolean existeClientePorDniOCuil(String dni, String cuil) throws Exception;
	  public boolean existeClientePorDni(String dni)throws Exception;
	  boolean existeNombreApellidoEnOtroCliente(String nombre, String apellido, String dniActual) throws Exception;
	  public boolean validarContraseniasIguales(String contrasenia1, String contrasenia2)  throws ContraseñaNoCoincideException;
	  public void validarCliente (Cliente cliente) throws CampoInvalidoException, EdadInvalidaException;
}
