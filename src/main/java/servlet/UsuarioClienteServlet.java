package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import entidad.Cuenta;
import entidad.Cuota;
import entidad.Movimiento;
import entidad.Prestamo;
import entidad.Provincia;
import entidad.Transferencia;
import negocio.CuentaNegocio;
import negocio.PrestamoNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.PrestamoNegocioImpl;
import negocio.PrestamoNegocio;
import negocio.ClienteNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocio.MovimientoNegocio;
import negocioImpl.MovimientoNegocioImpl;
import negocioImpl.TransferenciaNegocioImpl;
import negocio.TransferenciaNegocio;

@WebServlet("/UsuarioClienteServlet")
public class UsuarioClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
	private PrestamoNegocio prestamoNegocio = new PrestamoNegocioImpl();
	private MovimientoNegocio movimientoNegocio = new MovimientoNegocioImpl();

	public UsuarioClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String dni = (session != null) ? (String) session.getAttribute("dniUsuario") : null;
		if (dni == null) {
			response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
			return;
		}
		try {
			List<Cuenta> cuentas = cuentaNegocio.CargarDDlCuentas(dni);
			session.setAttribute("listaCuentas", cuentas);
			request.getRequestDispatcher("/vistas/AltaPrestamo.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error cargando cuentas", e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		// SEGUN LA ACCION, REDIRIGIMOS AL METODO CORRESPONDIENTE
		switch (accion) {
		// Vemos como estaria conformado el prestamo
		case "SolicitarPrestamo":
			solicitarPrestamo(request, response);
			break;
		// pedir prestamo
		case "VerPrestamo":
			verPrestamo(request, response);
			break;
		case "listarPagarPrestamo":
			ListarPagarPrestamo(request, response);
			break;
		// info personal
		case "verInformacionPersonal":
			verInformacionPersonal(request, response);
			break;
		case "verMovimientos":
			verMovimientos(request, response);
			break;
		case "transferencias":
			transferencias(request, response);
			break;
		case "listarTodo":
			listarTodo(request, response);
			break;
		// meva a cargar las DDl del CLiente
		case "autotransferencias":
			autotransferencias(request, response);
			break;
		// boton transferiri a cuentas propias
		case "transferirAcuentaPropia":
			transferirAcuentaPropia(request, response);
			break;
		// REDIRIGIR A 'HomeCliente.jsp'
		default:
			response.sendRedirect("vistas/HomeCliente.jsp");
			break;
		}
	}

	private void ListarPagarPrestamo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String dni = (session != null) ? (String) session.getAttribute("dniUsuario") : null;
		if (dni == null) {
			response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
			return;
		}
		try {
			List<Cuenta> cuentas = cuentaNegocio.CargarDDlCuentas(dni);
			session.setAttribute("listaCuentas", cuentas);
			List<Cuota> listaPagarPrestamos = prestamoNegocio.listarPagarPrestamosPorDni(dni);
			request.setAttribute("listaPagarPrestamos", listaPagarPrestamos);
			request.getRequestDispatcher("/vistas/PagarPrestamo.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error al listar prestamos", e);
		}
	}

	private void verPrestamo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String importeStr = request.getParameter("importe");
		String cuotasStr = request.getParameter("cuotas");
		String cuentaDestino = request.getParameter("cuentaDestino");
		int cuotas = Integer.parseInt(cuotasStr);
		float importe = Float.parseFloat(importeStr);
		float interes;
		switch (cuotas) {
		case 3:
			interes = 0.20f;
			break;
		case 6:
			interes = 0.50f;
			break;
		case 12:
			interes = 0.75f;
			break;
		case 24:
			interes = 1.00f;
			break;
		default:
			interes = 0.0f;
		}
		float total = importe * interes + importe;
		float cuotaMens = total / cuotas;
		request.setAttribute("cuentaDestino", cuentaDestino);
		request.setAttribute("importe", importe);
		request.setAttribute("cuotas", cuotas);
		request.setAttribute("cuotaMensual", cuotaMens);
		request.setAttribute("total", total);
		request.getRequestDispatcher("/vistas/AltaPrestamo.jsp").forward(request, response);
	}

	private void solicitarPrestamo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String dni = (session != null) ? (String) session.getAttribute("dniUsuario") : null;
		String cuentaDestinoStr = request.getParameter("cuentaDestino-VerPrestamo");
		String importeStr = request.getParameter("importe-VerPrestamo");
		String cuotas = request.getParameter("cuotas-VerPrestamo");
		String totalStr = request.getParameter("total-VerPrestamo");
		String cuotaMensualStr = request.getParameter("cuotaMensual-VerPrestamo");
		if (cuentaDestinoStr == null || cuentaDestinoStr.isEmpty() || importeStr == null || importeStr.isEmpty()
				|| cuotas == null || cuotas.isEmpty() || totalStr == null || totalStr.isEmpty()) {
			request.setAttribute("error",
					"Faltan datos para procesar la solicitud. Asegúrese de haber hecho primero 'Ver Préstamo'.");
			request.getRequestDispatcher("/vistas/AltaPrestamo.jsp").forward(request, response);
			return;
		}
		int numeroCuenta = Integer.parseInt(cuentaDestinoStr);
		BigDecimal importe = new BigDecimal(importeStr).setScale(2, RoundingMode.HALF_UP);
		BigDecimal total = new BigDecimal(totalStr).setScale(2, RoundingMode.HALF_UP);
		BigDecimal cuotaMensual = new BigDecimal(cuotaMensualStr).setScale(2, RoundingMode.HALF_UP);
		Cliente cliente = new Cliente();
		cliente.setDni(dni);
		Cuenta cuenta = new Cuenta();
		cuenta.setNumeroCuenta(numeroCuenta);
		Prestamo prestamo = new Prestamo();
		prestamo.setCuenta(cuenta);
		prestamo.setCliente(cliente);
		prestamo.setImporteSolicitado(importe);
		prestamo.setCuotas(cuotas);
		prestamo.setEstado("P");
		prestamo.setFecha(LocalDate.now());
		prestamo.setImporteTotal(total);
		prestamo.setPlazoMeses(Integer.parseInt(cuotas));
		prestamo.setMontoMensual(cuotaMensual);
		try {
			PrestamoNegocio negocio = new PrestamoNegocioImpl();
			boolean solicitud = negocio.SolicitarPrestamo(prestamo);
			if (solicitud) {
				request.setAttribute("error", "¡Tu préstamo se solicitó con éxito! En breve recibirás la aprobación.");
			} else {
				request.setAttribute("error", "No se pudo guardar el préstamo.");
			}
		} catch (Exception e) {
			request.setAttribute("error", "Error al solicitar préstamo: " + e.getMessage());
		}
		request.getRequestDispatcher("/vistas/AltaPrestamo.jsp").forward(request, response);
	}

	private void verInformacionPersonal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String dni = (session != null) ? (String) session.getAttribute("dniUsuario") : null;
		if (dni == null) {
			response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
			return;
		}
		try {
			ClienteNegocio clienteNeg = new ClienteNegocioImpl();
			Cliente cliente = clienteNeg.obtenerClientePorDni(dni);
			request.setAttribute("cliente", cliente);
			request.getRequestDispatcher("/vistas/InformacionPersonal.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void verMovimientos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String dni = (session != null) ? (String) session.getAttribute("dniUsuario") : null;
		if (dni == null) {
			response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
			return;
		}
		String cuentaStr = request.getParameter("cuentaDestino");
		String fechaDesde = request.getParameter("fecha_desde");
		String fechaHasta = request.getParameter("fecha_hasta");
		if (cuentaStr == null || cuentaStr.isEmpty()) {
			request.setAttribute("error", "No se seleccionó ninguna cuenta.");
			request.getRequestDispatcher("/vistas/HomeCliente.jsp").forward(request, response);
			return;
		}
		try {
			int numeroCuenta = Integer.parseInt(cuentaStr);
			List<Movimiento> movimientos;
			if (fechaDesde != null && !fechaDesde.isEmpty() && fechaHasta != null && !fechaHasta.isEmpty()) {
				movimientos = movimientoNegocio.ListarMovimientosPorRango(numeroCuenta, fechaDesde, fechaHasta);
			} else {
				movimientos = movimientoNegocio.obtenerMovimientosPorCuenta(numeroCuenta);
			}
			request.setAttribute("listaMovimientos", movimientos);
			request.setAttribute("cuentaSeleccionada", cuentaStr);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error al obtener movimientos.");
		}
		request.getRequestDispatcher("/vistas/HomeCliente.jsp").forward(request, response);
	}

	private void listarTodo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String dni = (session != null) ? (String) session.getAttribute("dniUsuario") : null;
		if (dni == null) {
			response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
			return;
		}
		String cuentaStr = request.getParameter("cuentaDestino");
		String fechaDesde = request.getParameter("fecha_desde");
		String fechaHasta = request.getParameter("fecha_hasta");
		if (cuentaStr == null || cuentaStr.isEmpty()) {
			request.setAttribute("error", "No se seleccionó ninguna cuenta.");
			request.getRequestDispatcher("/vistas/HomeCliente.jsp").forward(request, response);
			return;
		}
		try {
			int numeroCuenta = Integer.parseInt(cuentaStr);
			List<Movimiento> movimientos;
			if (fechaDesde != null && !fechaDesde.isEmpty() && fechaHasta != null && !fechaHasta.isEmpty()) {
				movimientos = movimientoNegocio.obtenerMovimientosPorCuenta(numeroCuenta);
			} else {
				movimientos = movimientoNegocio.obtenerMovimientosPorCuenta(numeroCuenta);
			}
			request.setAttribute("listaMovimientos", movimientos);
			request.setAttribute("cuentaSeleccionada", cuentaStr);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error al obtener movimientos.");
		}
		request.getRequestDispatcher("/vistas/HomeCliente.jsp").forward(request, response);
	}

	
	
	private void autotransferencias(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String dni = (session != null) ? (String) session.getAttribute("dniUsuario") : null;
		if (dni == null) {
			response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
			return;
		}
		try {
			List<Cuenta> cuentas = cuentaNegocio.CargarDDlCuentas(dni);
			session.setAttribute("listaCuentas", cuentas);
			request.getRequestDispatcher("/vistas/Autotransferencia.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error al listar", e);
		}
	}

	private void transferirAcuentaPropia(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String montoSTR = request.getParameter("monto");
		BigDecimal monto = new BigDecimal(montoSTR);
		String cuentaOrigenSTR = request.getParameter("cuentaDebitar");
		String[] partes1 = cuentaOrigenSTR.split("-");
		String cuentaDestinoSTR = request.getParameter("cuentaDestino");
		String[] partes2 = cuentaDestinoSTR.split("-");
		int cuentaOrigen = Integer.parseInt(partes1[0]);
		BigDecimal saldoOrigen = new BigDecimal(partes1[1]);
		int cuentaDestino = Integer.parseInt(partes2[0]);
		BigDecimal saldoDestino = new BigDecimal(partes2[1]);
		if (cuentaOrigen == cuentaDestino) {
			request.setAttribute("error", "La cuenta origen y destino no pueden ser iguales.");
			request.getRequestDispatcher("/vistas/Autotransferencia.jsp").forward(request, response);
			return;
		}
		if (saldoOrigen.compareTo(monto) < 0) {
			request.setAttribute("error", "El monto a trasnferiri debe ser menor.");
			request.getRequestDispatcher("/vistas/Autotransferencia.jsp").forward(request, response);
			return;
		}
		Transferencia transferenciaOrigen = new Transferencia();
		transferenciaOrigen.setNroCuentaOrigen(cuentaOrigen);
		transferenciaOrigen.setNroCuentaDestino(cuentaDestino);
		transferenciaOrigen.setImporte(monto);
		transferenciaOrigen.setFecha(LocalDate.now());
		transferenciaOrigen.setEstado('1');
		try {
			TransferenciaNegocio transfeNeg = new TransferenciaNegocioImpl();
			boolean TransferenciaExitosa = transfeNeg.RealizarTransferencia(transferenciaOrigen);
			if (TransferenciaExitosa) {
				request.setAttribute("error", "¡Transferncia realizada");
				request.getRequestDispatcher("/vistas/Autotransferencia.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "No se pudo  realizar tranferencia.");
			}
		} catch (Exception e) {
			request.setAttribute("error", "Error al transferiri: " + e.getMessage());
		}
	}

	private void transferencias(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String dni = (session != null) ? (String) session.getAttribute("dniUsuario") : null;
		if (dni == null) {
			response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
			return;
		}
		try {
			List<Cuenta> cuentas = cuentaNegocio.CargarDDlCuentas(dni);
			session.setAttribute("listaCuentas", cuentas);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error al listar", e);
		}
		String montoSTR = request.getParameter("monto");
		if (montoSTR == null) {
			request.getRequestDispatcher("/vistas/Transferencias.jsp").forward(request, response);
			return;
		}
		BigDecimal monto = new BigDecimal(montoSTR);
		String cbuDestino = request.getParameter("cbu");
		String cuentaOrigenSTR = request.getParameter("cuentaDebitar");
		String mensaje;
		try {
			String[] partes1 = cuentaOrigenSTR.split("-");
			int cuentaOrigen = Integer.parseInt(partes1[0]);// parseo
			BigDecimal saldoOrigen = new BigDecimal(partes1[1]);
			if (saldoOrigen.compareTo(monto) < 0) {
				mensaje = "El monto debe ser menor al saldo disponible.";
			} else {
				Transferencia transferenciaOrigen = new Transferencia();
				Cuenta cuenta = new Cuenta();
				transferenciaOrigen.setNroCuentaOrigen(cuentaOrigen);
				transferenciaOrigen.setImporte(monto);
				transferenciaOrigen.setFecha(LocalDate.now());
				transferenciaOrigen.setEstado('1');
				cuenta.setCbu(cbuDestino);
				TransferenciaNegocio transfeNeg = new TransferenciaNegocioImpl();
				boolean TransferenciaExitosa = transfeNeg.ValidarTransferencia(cuenta, transferenciaOrigen);
				mensaje = TransferenciaExitosa ? "TRANSFERENCIA REALIZADA CON EXITO" : "ERROR. CUENTA DADA DE BAJA O CUENTA INEXISTENTE";
			}
		} catch (NumberFormatException nfe) {
			mensaje = "Formato de número inválido.";
		} catch (Exception ex) {
			ex.printStackTrace();
			mensaje = "Error al transferir: " + ex.getMessage();
		}
		request.setAttribute("estadoTransferencia", mensaje);
		request.getRequestDispatcher("/vistas/Transferencias.jsp").forward(request, response);
	}
}