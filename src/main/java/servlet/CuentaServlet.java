package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CuentaDAO;
import DAOimpl.CuentaDAOImpl;
import Excepciones.CampoInvalidoException;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoCuenta;
import negocio.CuentaNegocio;
import negocioImpl.CuentaNegocioImpl;

@WebServlet("/CuentaServlet")
public class CuentaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// INTANCIAMOS LAS CAPAS
	private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();

	// CONSTRUCTOR VACIO
	public CuentaServlet() {
		super();
		this.cuentaNegocio = new CuentaNegocioImpl();
	}

	// PETICIONES GET:
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// OBTENEMOS EL PARAMETRO DEL JSP
		String action = request.getParameter("accion");
		// POR DEFECTO, HACEMOS QUE LISTE LAS CUENTAS SIN FILTROS
		if (action == null) {
			action = "listar";
		}
		// SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
		switch (action) {
		// MOSTRAR LISTADO DE CUENTAS
		case "listar":
			listarCuentas(request, response);
			break;	
		// MOSTRAR LISTADO DE CUENTAS DADAS DE BAJA
		case "listarInactivas":
			listarCuentasInactivas(request, response);
			break;
		// MOSTRAR LISTADO CUENTAS RANGO MONTO
		case "filtrarMonto":
			listarPorRangoMonto(request, response);
			break;
			// EDITAR UN CLIENTE MEDIANTE SU DNI
		case "editar":
			request.setAttribute("editarCuenta", request.getParameter("numeroCuenta"));
			listarCuentas(request, response);
			break;
		// REDIRIGIR A 'AltaCuenta.jsp'
		default:
			response.sendRedirect("vistas/AltaCuenta.jsp");
			break;
		}
	}

	// PETICIONES POST:
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// OBTENEMOS EL PARAMETRO DEL JSP
		String action = request.getParameter("accion");
		// SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
		switch (action) {
		// DAR DE ALTA UNA CUENTA
		case "alta":
			altaCuenta(request, response);
			break;
		// ELIMINAR UNA CUENTA
		case "eliminar":
			eliminarCuenta(request, response);
			break;
		// MODIFICAR UN CLIENTE
		case "modificar":
			modificarCuenta(request, response);
			break;
		// POR DEFECTO, LISTAMOS TODAS LAS CUENTAS
		default:
			response.sendRedirect("CuentaServlet?accion=listar");
			break;
		}
	}

	// METODO PARA LISTAR CUENTAS
	private void listarCuentas(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    try {
	        List<Cuenta> listaCuentas = cuentaNegocio.listarCuentas();
	        request.setAttribute("listaCuentas", listaCuentas);
	        cargarTiposCuenta(request);
	        request.getRequestDispatcher("/vistas/ListarCuentas.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new ServletException("Error al listar cuentas", e);
	    }
	}
	
	private void listarCuentasInactivas(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Cuenta> listaCuentas = cuentaNegocio.listarCuentasDadosBaja();
			request.setAttribute("listaCuentas", listaCuentas);
			request.setAttribute("cuentasInactivas", true);
			request.getRequestDispatcher("/vistas/ListarCuentas.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMensaje", "Error al listar cuentas inactivas.");
			request.getRequestDispatcher("/vistas/ListarCuentas.jsp").forward(request, response);
		}
	}
	
	private void listarPorRangoMonto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			double min = Double.parseDouble(request.getParameter("min"));
			double max = Double.parseDouble(request.getParameter("max"));

			List<Cuenta> cuentasFiltradas = cuentaNegocio.listarPorRangoMonto(min, max);
			request.setAttribute("listaCuentas", cuentasFiltradas);
			request.getRequestDispatcher("/vistas/ListarCuentas.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMensaje", "Error al filtrar cuentas por monto.");
			request.getRequestDispatcher("/vistas/ListarCuentas.jsp").forward(request, response);
		}
	}
	
	// METODO PARA DAR DE ALTA UNA CUENTA
	private void altaCuenta(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
		 	System.out.println("------------------entro alta cuenta");
	    try {
	        // OBTENEMOS LOS DATOS DE LA CUENTA DESDE EL JSP PARA VALIDAD DATOS
	        Cuenta cuenta = obtenerCuentaDesdeJSP(request);
	        // SETEAMOS EL SALDO EN 10000.00
	        cuenta.setSaldo(10000.00);
	        cuenta.setFechaCreacion(LocalDate.now());
	    	System.out.println(cuenta.getFechaCreacion()+"+------------------------------------------------------------------");
	   
	    	
	    	// CREAMOS LA CUENTA
	        boolean exitoCuenta = cuentaNegocio.crearCuenta(cuenta);
			if (exitoCuenta) {
				request.setAttribute("mensaje", "CUENTA CREADA  CON EXITO.");
				System.out.println("entro aca 1");
				request.getRequestDispatcher("/vistas/AltaCuenta.jsp").forward(request, response);
			} else {
				request.setAttribute("mensaje", "EL DNI NO EXISTE COMO CLIENTE O EL CLIENTE YA CUENTA CON 3 CUENTAS.");
				System.out.println("entro aca 2");
				request.getRequestDispatcher("/vistas/AltaCuenta.jsp").forward(request, response);
			}
	    // EXCEPCIONES
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("entro aca 3");
	        response.sendRedirect("vistas/AltaCuenta.jsp?exito=false");
	    }
	}

	// METODO PARA MODIFICAR UNA CUENTA
	private void modificarCuenta(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        // OBTENEMOS LOS DATOS DE LA CUENTA DESDE EL JSP PARA VALIDAD DATOS
	        Cuenta cuenta = obtenerCuentaDesdeJSP(request);
	        // MODIFICAMOS LA CUENTA
	        boolean exitoCuenta = cuentaNegocio.modificarCuenta(cuenta);
	        if (exitoCuenta) {
	            request.setAttribute("mensajeExito", "LA CUENTA FUE MODIFICADA EXITOSAMENTE");
	        } else {
	            request.setAttribute("errorMensaje", "NO SE PUDO MODIFICAR LA CUENTA");
	        }
	        listarCuentas(request, response);
		// EXCEPCIONES
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("CuentaServlet?accion=listar&modificado=false");
	    }
	}

	// METODO PARA ELIMINAR UNA CUENTA
	private void eliminarCuenta(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	    	// OBTENEMOS EL NUMERO DE CUENTA
	        int numeroCuenta = Integer.parseInt(request.getParameter("numeroCuenta"));
	        // ELIMINAMOS LA CUENTA
	        boolean exito = cuentaNegocio.eliminarCuenta(numeroCuenta);
	        if (exito) {
	        	request.setAttribute("mensajeExito", "LA CUENTA FUE ELIMINADA CON EXITO");
	        } else {
	        	request.setAttribute("errorMensaje", "ERROR AL ELIMINAR LA CUENTA");
	        }
	        listarCuentas(request, response);
	    // EXCEPCIONES
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendRedirect("CuentaServlet?accion=listar&error=true");
	        listarCuentas(request, response);
	    }
	}

	// METODO PARA OBTENER LOS DATOS DE LAS CUENTAS DEL JSP
	private Cuenta obtenerCuentaDesdeJSP(HttpServletRequest request) {
		// CREAMOS UN OBJETO CUENTA
		Cuenta cuenta = new Cuenta();
		// OBNETEMOS EL NUMERO DE CUENTA
		if (request.getParameter("numeroCuenta") != null) {
			cuenta.setNumeroCuenta(Integer.parseInt(request.getParameter("numeroCuenta")));
		}
		// CREAMOS Y CARGAMOS EL DNI DEL CLIENTE SELECCIONADO
		Cliente cliente = new Cliente();
		cliente.setDni(request.getParameter("cliente"));
		cuenta.setCliente(cliente);
		// CREAMOS Y CARGAMOS EL TIPO DE CUENTA
		TipoCuenta tipo = new TipoCuenta();
		tipo.setIdTipoCuenta(Integer.parseInt(request.getParameter("tipoCuenta")));
		cuenta.setTipoCuenta(tipo);
//		// OBTENEMOS LA FECHA DE CREACION
//		cuenta.setFechaCreacion(LocalDate.parse(request.getParameter("fechaCreacion")));
		return cuenta;
	}
	
	// METODO PARA CARGAR LOS TIPOS DE CUENTA DESDE LA BD
	private void cargarTiposCuenta(HttpServletRequest request) {
	    try {
	        List<TipoCuenta> listaTiposCuenta = cuentaNegocio.listarTiposCuenta();
	        request.setAttribute("listaTiposCuenta", listaTiposCuenta);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
