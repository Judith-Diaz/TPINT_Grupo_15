package DAO;

import entidad.Cuenta;
import entidad.Transferencia;

public interface TransferenciaDAO {
	//trasfe entre mismas cuentas
	boolean insertarTransferencia( Transferencia trans) ;
		
		//trasnsfe entre cuentas diferentes cbu

	boolean insertarTransferenciaCBU (Cuenta cuenta, Transferencia tra);
		
		 Cuenta obtenerPorCBU(Cuenta cuenta);
		 boolean CBUexiste(Cuenta cuenta);
}
