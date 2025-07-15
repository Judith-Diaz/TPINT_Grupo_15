package DAOimpl;

import java.sql.*;
import java.util.*;
import DAO.ReportesDao;
import entidad.ReportePrestamos;
import DAO.Conexion;
import java.sql.Date;

public class ReportesDAOimpl implements ReportesDao{
	
	public Map<String, Double> porcentajeCuentas_sp() {
	    Map<String, Double> porcentajes = new HashMap<>();

	    try (
	        Connection con = Conexion.getConexion();
	        CallableStatement cst = con.prepareCall("{CALL porcentaje_cuentas()}");
	        ResultSet rs = cst.executeQuery();
	    ) {
	        if (rs.next()) {
	            porcentajes.put("porcentajeAhorro", rs.getDouble("porcentajeAhorro"));
	            porcentajes.put("porcentajeCorriente", rs.getDouble("porcentajeCorriente"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    

	    return porcentajes;
	}
	
	@Override
    public ReportePrestamos reporteEstadoPrestamos_sp() throws SQLException {
        ReportePrestamos reporte = new ReportePrestamos();

        try (
            Connection con = Conexion.getConexion();
            CallableStatement cst = con.prepareCall("{CALL reporte_estado_prestamos()}");
            ResultSet rs = cst.executeQuery();
        ) {
            if (rs.next()) {
                reporte.setTotalPrestamosSolicitados(rs.getInt("TotalPrestamosSolicitados"));
                reporte.setPrestamosPendientes(rs.getInt("PrestamosPendientes"));
                reporte.setPrestamosAprobados(rs.getInt("PrestamosAprobados"));
                reporte.setPrestamosRechazados(rs.getInt("PrestamosRechazados"));
                reporte.setPorcentajePendientes(rs.getDouble("PorcentajePendientes"));
                reporte.setPorcentajeAprobados(rs.getDouble("PorcentajeAprobados"));
                reporte.setPorcentajeRechazados(rs.getDouble("PorcentajeRechazados"));
                reporte.setTotalPorcentaje(rs.getDouble("TotalPorcentaje"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reporte;
    }
	
	@Override
	public int reporteClientesConPrestamo_sp(Date fechaInicio, Date fechaFin) {
	    int cantidadClientes = 0;

	    try (
	        Connection conn = Conexion.getConexion();
	        CallableStatement stmt = conn.prepareCall("{CALL reporte_clientes_con_prestamos(?, ?)}");
	    ) {
	        stmt.setDate(1, fechaInicio);
	        stmt.setDate(2, fechaFin);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                cantidadClientes = rs.getInt("CantidadClientes");
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return cantidadClientes;
	}
}
