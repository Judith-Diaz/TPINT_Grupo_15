package negocio;

import entidad.Cuenta;
import entidad.Transferencia;

public interface TransferenciaNegocio {
	// trasnfe entre mismas cuentas
	boolean RealizarTransferencia(Transferencia transferenciaOrigen) throws Exception;

//trasnsfe entre cuentas diferentes

	boolean ValidarTransferencia(Cuenta cuenta, Transferencia trans);

}