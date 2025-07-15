package negocioImpl;

import DAO.ClienteDAO;
import DAOimpl.ClienteDAOimpl;
import Excepciones.ContraseñaNoCoincideException;
import Excepciones.EdadInvalidaException;
import entidad.Cliente;
import negocio.ClienteNegocio;
import Excepciones.CampoInvalidoException;
import Excepciones.ClienteExistenteException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class ClienteNegocioImpl implements ClienteNegocio {

	private ClienteDAO clienteDao;

	public ClienteNegocioImpl() {
		this.clienteDao = new ClienteDAOimpl();
	}

	@Override
	public boolean agregarCliente(Cliente cliente) throws Exception {
		return clienteDao.agregarCliente(cliente);
	}

	@Override
	public boolean eliminarCliente(String dni) throws Exception {
		return clienteDao.eliminarCliente(dni);
	}

	@Override
	public boolean modificarCliente(Cliente cliente) throws Exception {
		return clienteDao.modificarCliente(cliente);
	}

	@Override
	public Cliente obtenerClientePorDni(String dni) throws Exception {
		return clienteDao.obtenerClientePorDni(dni);
	}

	@Override
	public List<Cliente> listarClientes() throws Exception {
		return clienteDao.listarClientes();
	}
	
	@Override
	public List<Cliente> listarClientesDadosBaja() throws Exception{
		return clienteDao.listarClientesDadosBaja();
	}
	
	//
	@Override
	public boolean existeClientePorDniOCuil(String dni, String cuil) throws Exception {
		return clienteDao.existeClientePorDniOCuil(dni, cuil);
	}

	@Override
	public boolean existeClientePorDni(String dni) throws Exception {
		return clienteDao.existeClientePorDni(dni);
	}

	@Override
	public boolean existeNombreApellidoEnOtroCliente(String nombre, String apellido, String dniActual)
			throws Exception {
		return clienteDao.existeNombreApellidoEnOtroCliente(nombre, apellido, dniActual);
	}

	@Override
	public boolean validarContraseniasIguales(String contrasenia1, String contrasenia2)
			throws ContraseñaNoCoincideException {
		if (contrasenia1 == null || contrasenia2 == null) {
			throw new ContraseñaNoCoincideException("Las contraseñas no pueden ser nulas");
		}
		if (!contrasenia1.equals(contrasenia2)) {
			throw new ContraseñaNoCoincideException("Las contraseñas no coinciden");
		}
		return true;
	}

	public void validarCliente(Cliente cliente) throws CampoInvalidoException, EdadInvalidaException {
		if (cliente.getCuil() == null || cliente.getCuil().trim().isEmpty())
			throw new CampoInvalidoException("El campo CUIL está vacío.");
		if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty())
			throw new CampoInvalidoException("El campo Nombre está vacío.");
		if (cliente.getApellido() == null || cliente.getApellido().trim().isEmpty())
			throw new CampoInvalidoException("El campo Apellido está vacío.");
		if (cliente.getNacionalidad() == null || cliente.getNacionalidad().trim().isEmpty())
			throw new CampoInvalidoException("El campo Nacionalidad está vacío.");
		if (cliente.getDireccion() == null || cliente.getDireccion().trim().isEmpty())
			throw new CampoInvalidoException("El campo Dirección está vacío.");
		if (cliente.getCorreoElectronico() == null || cliente.getCorreoElectronico().trim().isEmpty())
			throw new CampoInvalidoException("El campo Correo Electrónico está vacío.");
		if (cliente.getTelefonos() == null || cliente.getTelefonos().trim().isEmpty())
			throw new CampoInvalidoException("El campo Teléfonos está vacío.");
	    if (cliente.getUsuario() == null || cliente.getUsuario().getContraseniaUsu() == null || cliente.getUsuario().getContraseniaUsu().trim().isEmpty())
	        throw new CampoInvalidoException("El campo Contraseña está vacío.");
		LocalDate hoy = LocalDate.now();
		int edad = Period.between(cliente.getFechaNacimiento(), hoy).getYears();
		if (edad < 18) {
			throw new EdadInvalidaException("El cliente debe ser mayor de 18 años.");
		}
	}
	

}
