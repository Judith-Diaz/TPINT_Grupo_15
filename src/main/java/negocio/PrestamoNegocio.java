package negocio;

import java.math.BigDecimal;
import java.util.List;

import Excepciones.SaldoInsuficienteException;
import entidad.Cliente;
import entidad.Cuota;
import entidad.Prestamo;

public interface PrestamoNegocio {
	 boolean SolicitarPrestamo(Prestamo prestamo) throws Exception;//me carga la tabla de prestamos 
	 List<Prestamo> listarPrestamos() throws Exception;//me lista los pretsamos ocn estado P
	 List<Cuota> listarPagarPrestamos() throws Exception;//me lista los pretsamos ocn estado D de debe pagar
	 List<Cuota> listarPagarPrestamosPorDni(String dni) throws Exception;
	 boolean  cambiarEstadoR(int idPrestamo) throws Exception;
	 boolean cambiarEstadoP(int idPrestamo) throws Exception;
	 boolean pagarCuota(int idPrestamo, int nroCuota, int nroCuenta) throws Exception, SaldoInsuficienteException;
}
