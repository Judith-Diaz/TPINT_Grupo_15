package DAO;

public interface CuotaDAO {
	boolean marcarCuotaPagada(int idPrestamo, int nroCuota) throws Exception;
}
