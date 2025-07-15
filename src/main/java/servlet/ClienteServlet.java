package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ClienteDAO;
import DAO.ProvinciaDAO;
import DAO.LocalidadDAO;
import DAOimpl.ClienteDAOimpl;
import DAOimpl.LocalidadDAOImpl;
import DAOimpl.ProvinciaDAOImpl;
import Excepciones.ContraseñaNoCoincideException;
import entidad.Cliente;
import entidad.Prestamo;
import entidad.Provincia;
import entidad.Localidad;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocio.UsuarioNegocio;
import negocio.LocalidadNegocio;
import negocio.ProvinciaNegocio;
import negocioImpl.LocalidadNegocioImpl;
import negocioImpl.ProvinciaNegocioImpl;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;
import Excepciones.CampoInvalidoException;
import Excepciones.ClienteExistenteException;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// INTANCIAMOS LAS CAPAS
	private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
	private UsuarioNegocio usuarioNegocio = new UsuarioNegocioImpl();
	private ProvinciaNegocio provinciaNegocio = new ProvinciaNegocioImpl();
	private LocalidadNegocio localidadNegocio = new LocalidadNegocioImpl();

	// CONSTRUCTOR VACIO
	public ClienteServlet() {
		super();
	}

	// PETICIONES GET:
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// OBTENEMOS EL PARAMETRO DEL JSP
		String action = request.getParameter("accion");
		// POR DEFECTO, HACEMOS QUE LISTE LOS CLIENTES SIN FILTROS
		if (action == null) {
			action = "listar";
		}
		// SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
		switch (action) {
		// MOSTRAR LISTADO DE CLIENTES
		case "listar":
			listarClientes(request, response);
			break;
		// MOSTRAR LISTADO DE CLIENTES DADOS DE BAJA
        case "listarInactivos":
            listarClientesDadosBaja(request, response);
            break;
		// EDITAR UN CLIENTE MEDIANTE SU DNI
		case "editar":
			request.setAttribute("editarDni", request.getParameter("dni"));
			listarClientes(request, response);
			break;
		default:
			response.sendRedirect("vistas/AltaCliente.jsp");
			break;
		}
	}

	// PETICIONES POST:
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// OBTENEMOS EL PARAMETRO DEL JSP
		String action = request.getParameter("accion");
		// SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
		switch (action) {
		// DAR DE ALTA UN CLIENTE
		case "alta":
			altaCliente(request, response);
			break;
		// ELIMINAR UN CLIENTE
		case "eliminar":
			eliminarCliente(request, response);
			break;
		// MODIFICAR UN CLIENTE
		case "modificar":
			modificarCliente(request, response);
			break;
		// POR DEFECTO, LISTAMOS TODOS LOS CLIENTES
		default:
			response.sendRedirect("ClienteServlet?accion=listar");
			break;
		}
	}

	// METODO PARA LISTAR CLIENTES
	private void listarClientes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// LISTAMOS LOS CLIENTES DE LA BD
			List<Cliente> listaClientes = clienteNegocio.listarClientes();
			request.setAttribute("listaClientes", listaClientes);
			// CARGAMOS LAS PROVINCIAS Y LOCALIDADES DE LA BD
			cargarProvinciasYLocalidades(request);
			request.getRequestDispatcher("/vistas/ListarClientes.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error al listar clientes", e);
		}
	}

	// METODO PARA DAR DE ALTA UN CLIENTE
	private void altaCliente(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// OBTENEMOS LOS DATOS DEL CLIENTE DEL JSP PARA VALIDAR VALORES
			Cliente cliente = obtenerClienteDesdeJSP(request);
			// VALIDAMOS LOS DATOS DEL CLIENTE Y LA CONTRASEÑA
			clienteNegocio.validarCliente(cliente);
			System.out.println("Provincia ID: " + cliente.getProvincia().getId());
			System.out.println("Localidad ID: " + cliente.getLocalidad().getIdLocalidad_Loc());
			clienteNegocio.validarContraseniasIguales(request.getParameter("password"),
					request.getParameter("confirmPassword"));
			// CREAMOS UN NUEVO USUARIO Y LE SETIAMOS LOS VALORES CORRESPONDIENTES
			Usuario usuario = new Usuario();
			usuario.setDniUsu(cliente.getDni());
			usuario.setNombreUsu(request.getParameter("usuario"));
			usuario.setContraseniaUsu(request.getParameter("password"));
			usuario.setRolUsu('C');
			// AGREGAMOS EL CLIENTE Y EL USUARIO
			boolean exitoCliente = clienteNegocio.agregarCliente(cliente);
			boolean exitoUsuario = exitoCliente && usuarioNegocio.agregarUsuario(usuario);
			if (exitoCliente && exitoUsuario) {
				request.setAttribute("mensajeExito", "CLIENTE AGREGADO CON EXITO");
			} else {
				request.setAttribute("errorMensaje", "ERROR AL AGREGAR AL CLIENTE");
			}
	        cargarProvinciasYLocalidades(request);
	        request.getRequestDispatcher("vistas/AltaCliente.jsp").forward(request, response);
		// EXCEPCIONES
		} catch (ClienteExistenteException | ContraseñaNoCoincideException | CampoInvalidoException e) {
	        request.setAttribute("mensajeError", e.getMessage());
	        cargarProvinciasYLocalidades(request);
	        request.getRequestDispatcher("vistas/AltaCliente.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMensaje", "ERROR AL AGREGAR AL CLIENTE");
	        cargarProvinciasYLocalidades(request);
	        request.getRequestDispatcher("vistas/AltaCliente.jsp").forward(request, response);
		}
	}

	// METODO PARA HACER BAJA LOGICA DE UN CLIENTE
	private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// OBTENEMOS EL DNI DESDE EL JSP
			String dni = request.getParameter("dni");
			// ELIMINAMOS EL CLIENTE
			boolean eliminado = clienteNegocio.eliminarCliente(dni);
	        if (eliminado) {
	            request.setAttribute("mensajeExito", "EL CLIENTE FUE ELIMINADO CON EXITO");
	        } else {
	            request.setAttribute("errorMensaje", "ERROR AL ELIMINAR AL CLIENTE");
	        }
	        listarClientes(request, response);
		// EXCEPCIONES
		} catch (Exception e) {
	        e.printStackTrace();
	        listarClientes(request, response);
		}
	}

	// METODO PARA MODIFICAR LOS DATOS DEL CLIENTE
	private void modificarCliente(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// OBTENEMOS LOS DATOS DEL CLIENTE DESDE EL JSP PARA VALIDAR VALORES
			Cliente cliente = obtenerClienteDesdeJSP(request);
			// VALIDAMOS LOS VALORES DEL CLIENTE
			clienteNegocio.validarCliente(cliente);
			// MODIFICAMOS EL CLIENTE
			boolean exitoCliente = clienteNegocio.modificarCliente(cliente);
			// OBTENEMOS LA NUEVA CONTRASEÑA Y LA CAMBIAMOS
			String nuevaPassword = request.getParameter("password");
			boolean exitoUsuario = true;
			if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
				exitoUsuario = usuarioNegocio.actualizarPassword(cliente.getDni(), nuevaPassword);
			}
            if (exitoCliente && exitoUsuario) {
            	request.setAttribute("mensajeExito", "EL CLIENTE FUE MODIFICADO CON EXITO");
            } else {
                request.setAttribute("errorMensaje", "ERROR AL MODIFICAR AL CLIENTE");
            }
            listarClientes(request, response);
		// EXCEPCIONES
		} catch (CampoInvalidoException e) {
			request.setAttribute("errorMensaje", e.getMessage());
			request.setAttribute("editarDni", request.getParameter("dni"));
			listarClientes(request, response);
		} catch (Exception e) {
		    e.printStackTrace();
		    request.setAttribute("errorMensaje", e.getMessage());
		    request.setAttribute("editarDni", request.getParameter("dni"));
		    listarClientes(request, response);
		}
	}

	// METODO PARA OBTENER LOS DATOS DEL CLIENTE DEL JSP
	private Cliente obtenerClienteDesdeJSP(HttpServletRequest request) {
		// OBTENEMOS LOS DATOS DEL CLIENTE DESDE EL JSP
		Cliente cliente = new Cliente();
		cliente.setDni(request.getParameter("dni"));
		cliente.setCuil(request.getParameter("cuil"));
		cliente.setNombre(request.getParameter("nombre"));
		cliente.setApellido(request.getParameter("apellido"));
		cliente.setSexo(request.getParameter("sexo"));
		cliente.setNacionalidad(request.getParameter("nacionalidad"));
		cliente.setFechaNacimiento(LocalDate.parse(request.getParameter("fechaNacimiento")));
		cliente.setDireccion(request.getParameter("direccion"));
		Provincia provincia = new Provincia();
		provincia.setId(request.getParameter("provincia"));
		cliente.setProvincia(provincia);
		Localidad localidad = new Localidad();
		localidad.setIdLocalidad_Loc(request.getParameter("localidad"));
		cliente.setLocalidad(localidad);
		cliente.setCorreoElectronico(request.getParameter("email"));
		cliente.setTelefonos(request.getParameter("telefonos"));
		cliente.setEstado(true);
	    Usuario usuario = new Usuario();
	    usuario.setDniUsu(request.getParameter("dni"));
	    usuario.setNombreUsu(request.getParameter("usuario"));
	    usuario.setContraseniaUsu(request.getParameter("password"));
	    usuario.setRolUsu('C'); // o el rol que corresponda
	    cliente.setUsuario(usuario);
		return cliente;
	}

	// METODO PARA CARGAR LAS PROVINCIAS Y LOCALIDADES DE LA BD
	private void cargarProvinciasYLocalidades(HttpServletRequest request) {
		try {
			// CREAMOS Y CARGAMOS LAS LISTAS PARA LAS PROVINCIAS Y LOCALIDADES
			List<Provincia> listaProvincias = provinciaNegocio.listarProvincias();
			List<Localidad> listaLocalidades = localidadNegocio.obtenerTodasLocalidades();
			request.setAttribute("provincias", listaProvincias);
			request.setAttribute("localidades", listaLocalidades);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// METODO PARA LISTAR CLENTES DADOS DE BAJA
	private void listarClientesDadosBaja(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    try {
	        // LISTAMOS LOS CLIENTES DE LA BD CON ESTADO = FALSE
	        List<Cliente> listaClientes = clienteNegocio.listarClientesDadosBaja();
	        request.setAttribute("listaClientes", listaClientes);
	        request.setAttribute("clientesInactivos", true);
	        // CARGAMOS LAS PROVINCIAS Y LOCALIDADES DE LA BD
	        cargarProvinciasYLocalidades(request);
	        request.getRequestDispatcher("/vistas/ListarClientes.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new ServletException("Error al listar clientes dados de baja", e);
	    }
	}
}
