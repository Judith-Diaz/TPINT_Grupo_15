package DAO;

import java.sql.SQLException;
import java.util.List;

import entidad.Cliente;
import entidad.Cuenta;
import entidad.Cuota;
import entidad.Prestamo;

public interface PrestamoDAO {
	 boolean CargarSolicitud(Prestamo prestamo) throws Exception;
	 List<Prestamo> listarPrestamos() throws Exception;
	 List<Cuota> listarPagarPrestamos() throws Exception;
	 List<Cuota> listarPagarPrestamosPorDni(String dni) throws Exception;
	 boolean estadoP(int idPrestamo) throws Exception;
	 boolean estadoR(int idPrestamo) throws Exception;
}
