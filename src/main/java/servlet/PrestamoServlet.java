package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Excepciones.SaldoInsuficienteException;
import entidad.Cuenta;
import entidad.Cuota;
import entidad.Prestamo;
import negocio.PrestamoNegocio;
import negocioImpl.PrestamoNegocioImpl;
import negocio.CuentaNegocio;
import negocioImpl.CuentaNegocioImpl;

@WebServlet("/PrestamoServlet")
public class PrestamoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PrestamoNegocio prestamoNegocio = new PrestamoNegocioImpl();
	private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();

	public PrestamoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("accion");

		// SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
		switch (action) {
		// Muestra listado los prestamos que estan con estado pendientes
		case "listarPrestamoP":
			request.setAttribute("editarPrestamos", request.getParameter("numeroCuenta"));
			listarPrestamos(request, response);
			break;
		case "verCuotasYPagos":
		    verCuotasYPagos(request, response);
		    break;
		// REDIRIGIR A 'ListarPrestamo.jsp'
		default:
			response.sendRedirect("vistas/ListarPrestamo.jsp");
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// OBTENEMOS EL PARAMETRO DEL JSP
		String action = request.getParameter("accion");
		// SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
		switch (action) {
		case "autorizar":
			autorizarPrestamo(request, response);
			break;
		case "rechazar":
			rechazaPrestamo(request, response);
			break;
		case "pagarCuota":
		    pagarCuota(request, response);
		    break;
		// POR DEFECTO, LISTAMOS TODOS LOS CLIENTES
		default:
			response.sendRedirect("PrestamoServlet?accion=listarPrestamoP");
			break;
		}
	}

	private void listarPrestamos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Prestamo> listaPrestamos = prestamoNegocio.listarPrestamos();
			request.setAttribute("listaPrestamos", listaPrestamos);
			RequestDispatcher rd = request.getRequestDispatcher("/vistas/ListarPrestamos.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error al listar prestamos", e);
		}
	}
	
	private void verCuotasYPagos(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String dniCliente = (String) request.getSession().getAttribute("dniUsuario");
	    listarCuotasYCuentasPorCliente(request, response, dniCliente);
	}

	private void autorizarPrestamo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));

		try {

			boolean cambioEstadoOK = prestamoNegocio.cambiarEstadoP(idPrestamo);

			if (cambioEstadoOK) {
				List<Prestamo> lista = prestamoNegocio.listarPrestamos();
				request.setAttribute("listaPrestamos", lista);
				request.setAttribute("mensajeExito", "EL PRESTAMO SE AUTORIZO CON EXITO");
			} else {
				List<Prestamo> lista = prestamoNegocio.listarPrestamos();
				request.setAttribute("listaPrestamos", lista);
				request.setAttribute("error", "No se pudo aprobar el préstamo");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error interno: " + e.getMessage());
		}
		request.getRequestDispatcher("/vistas/ListarPrestamos.jsp").forward(request, response);

	}

	private void rechazaPrestamo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));

		try {

			boolean cambioEstadoOK = prestamoNegocio.cambiarEstadoR(idPrestamo);

			if (cambioEstadoOK) {
				List<Prestamo> lista = prestamoNegocio.listarPrestamos();
				request.setAttribute("listaPrestamos", lista);
				request.setAttribute("mensajeExito", "EL PRESTAMO SE RECHAZO CON EXITO");
			} else {
				List<Prestamo> lista = prestamoNegocio.listarPrestamos();
				request.setAttribute("listaPrestamos", lista);
				request.setAttribute("error", "devuelve falso la consulta");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error interno: " + e.getMessage());
		}
		request.getRequestDispatcher("/vistas/ListarPrestamos.jsp").forward(request, response);

	}
	
	private void pagarCuota(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
	        int nroCuota = Integer.parseInt(request.getParameter("nroCuota"));
	        int nroCuenta = Integer.parseInt(request.getParameter("cuentaDebitar"));
	        String dniCliente = (String) request.getSession().getAttribute("dniUsuario");

	        boolean exito = prestamoNegocio.pagarCuota(idPrestamo, nroCuota, nroCuenta);

	        if (exito) {
	            request.setAttribute("mensajeExito", "LA CUOTA FUE PAGADA CON EXITO");
	        } else {
	            request.setAttribute("errorMensaje", "NO SE PUDO PAGAR LA CUOTA");
	        }

	    } catch (SaldoInsuficienteException e) {
	        request.setAttribute("errorMensaje", e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("errorMensaje", "NO SE PUDO PAGAR LA CUOTA" + e.getMessage());
	    }

	    // Ahora recargo solo cuotas y cuentas del cliente
	    String dniCliente = request.getParameter("dniCliente");
	    listarCuotasYCuentasPorCliente(request, response, dniCliente);
	}
	
	private void listarCuotasYCuentasPorCliente(HttpServletRequest request, HttpServletResponse response, String dniCliente)
	        throws ServletException, IOException {
	    try {
	        // Lista solo las cuotas pendientes para ese cliente
	        List<Cuota> cuotasDelCliente = prestamoNegocio.listarPagarPrestamosPorDni(dniCliente);
	        request.setAttribute("listaPagarPrestamos", cuotasDelCliente);

	        // Lista las cuentas del cliente (asegurate de tener este método en cuentaNegocio)
	        List<Cuenta> listaCuentasDelCliente = cuentaNegocio.obtenerCuentasPorDni(dniCliente); // o como sea tu método
	        request.setAttribute("cuentas", listaCuentasDelCliente);
	        
	        // Para que el JSP sepa qué cliente está visualizando
	        request.setAttribute("dniBuscado", dniCliente);

	        // Forward al JSP para mostrar los datos
	        request.getRequestDispatcher("/vistas/PagarPrestamo.jsp").forward(request, response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Error al cargar datos del cliente: " + e.getMessage());
	        request.getRequestDispatcher("/vistas/PagarPrestamo.jsp").forward(request, response);
	    }
	}
	
}
