package servlet;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.ReportesDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

	
import DAOimpl.ReportesDAOimpl;
import negocio.ReportesNegocio;
import negocioImpl.ReportesNegocioImpl;
import entidad.ReportePrestamos;
import java.sql.Date;

/**
 * Servlet implementation class ReportesServlet
 */
@WebServlet("/ReportesServlet")
public class ReportesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");

	    if (accion != null) {
	    	switch (accion) {
	        case "generarPorcentaje":
	            try {
	                mostrarReporteCuentas(request, response);
	            } catch (Exception e) {
	                e.printStackTrace();
	                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al mostrar el reporte");
	            }
	        
	            break;
	            
	        case "generarCantPrestamos":
                try {
                    mostrarReportePrestamos(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al mostrar el reporte de préstamos");
                }
                break;
	        case "generarCantClientes":
	            try {
	                generarReporteClientesConPrestamos(request, response);
	            } catch (Exception e) {
	                e.printStackTrace();
	                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al mostrar el reporte de clientes con préstamos");
	            }
	            break;
	        
	        // otros casos...
	    }
	        }
	    } 
	
	// MÉTODO PARA LLAMAR LOS PORCENTAJES DE LOS TIPOS DE CUENTA
	private void mostrarReporteCuentas(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		
	    ReportesDao dao = new ReportesDAOimpl();
	    Map<String, Double> porcentajes = dao.porcentajeCuentas_sp();
	    System.out.println("Porcentajes: " + porcentajes);
	    
	    request.setAttribute("porcentajeAhorro", porcentajes.get("porcentajeAhorro"));
	    request.setAttribute("porcentajeCorriente", porcentajes.get("porcentajeCorriente"));

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Reportes.jsp");
		dispatcher.forward(request, response);
	}
	
	// MÉTODO PARA LLAMAR EL REPORTE COMPLETO DE PRÉSTAMOS
    private void mostrarReportePrestamos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        ReportesNegocio negocio = new ReportesNegocioImpl();
        ReportePrestamos reporte = negocio.reporteEstadoPrestamos_sp();
        System.out.println("Reporte Préstamos: " + reporte);

        request.setAttribute("totalPrestamosSolicitados", reporte.getTotalPrestamosSolicitados());
        request.setAttribute("prestamosPendientes", reporte.getPrestamosPendientes());
        request.setAttribute("prestamosAprobados", reporte.getPrestamosAprobados());
        request.setAttribute("prestamosRechazados", reporte.getPrestamosRechazados());
        request.setAttribute("porcentajePendientes", reporte.getPorcentajePendientes());
        request.setAttribute("porcentajeAprobados", reporte.getPorcentajeAprobados());
        request.setAttribute("porcentajeRechazados", reporte.getPorcentajeRechazados());
        request.setAttribute("totalPorcentaje", reporte.getTotalPorcentaje());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Reportes.jsp");
        dispatcher.forward(request, response);
    }
    
    //METODO PARA MOSTRAR LA CANTIDAD DE CLIENTES CON PRESTAMO POR FECHA
    
    private void generarReporteClientesConPrestamos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String fechaInicioStr = request.getParameter("fechaInicio");
        String fechaFinStr = request.getParameter("fechaFin");

        if (fechaInicioStr == null || fechaFinStr == null) {
            request.setAttribute("error", "Debe seleccionar ambas fechas.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Reportes.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // ✅ Conversión de String a java.sql.Date
        Date fechaInicio = Date.valueOf(fechaInicioStr);
        Date fechaFin = Date.valueOf(fechaFinStr);

       
        String fechaInicioFormateada = fechaInicio.toString(); 
        String fechaFinFormateada = fechaFin.toString();

        
        ReportesDao dao = new ReportesDAOimpl();
        int totalClientes = dao.reporteClientesConPrestamo_sp(fechaInicio, fechaFin);

        
        request.setAttribute("cantidadClientesConPrestamo", totalClientes);
        request.setAttribute("fechaDesde", fechaInicioFormateada); 
        request.setAttribute("fechaHasta", fechaFinFormateada);     

        RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Reportes.jsp");
        dispatcher.forward(request, response);
    }
	
    
}

