package negocioImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DAO.ReportesDao;
import DAOimpl.ReportesDAOimpl;

import entidad.ClienteReportes;
import negocio.ReportesNegocio;

import entidad.ReportePrestamos;
import java.sql.Date;


public class ReportesNegocioImpl implements ReportesNegocio {
    
    
    private ReportesDao reportesDAO;
    
    
    public ReportesNegocioImpl() {
        this.reportesDAO = new ReportesDAOimpl();
    }


	@Override
	public Map<String, Double> porcentajeCuentas_sp() throws SQLException {
		return reportesDAO.porcentajeCuentas_sp();
	}
	
	@Override
    public ReportePrestamos reporteEstadoPrestamos_sp() throws SQLException {
        return reportesDAO.reporteEstadoPrestamos_sp();
    }
	
	@Override
	public int reporteClientesConPrestamo_sp(Date fechaInicio, Date fechaFin) {
	    return reportesDAO.reporteClientesConPrestamo_sp(fechaInicio, fechaFin);
	}
    
    
}
