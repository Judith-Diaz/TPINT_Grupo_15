package negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import entidad.ReportePrestamos;
import java.sql.Date;


public interface ReportesNegocio {
	Map<String, Double> porcentajeCuentas_sp() throws SQLException;
	
	ReportePrestamos reporteEstadoPrestamos_sp() throws SQLException;
	
	public int reporteClientesConPrestamo_sp(Date fechaInicio, Date fechaFin);
}
