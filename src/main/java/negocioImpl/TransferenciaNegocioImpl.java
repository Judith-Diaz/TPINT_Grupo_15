package negocioImpl;

import java.sql.SQLException;
import java.time.LocalDate;

import DAO.PrestamoDAO;
import DAO.TransferenciaDAO;
import DAOimpl.PrestamoDAOimpl;
import DAOimpl.TransferenciaDAOimpl;
import Excepciones.CampoInvalidoException;
import entidad.Cuenta;
import entidad.Transferencia;
import negocio.TransferenciaNegocio;
import negocioImpl.TransferenciaNegocioImpl;

public class TransferenciaNegocioImpl implements TransferenciaNegocio {
	private TransferenciaDAO transfeDAO = new TransferenciaDAOimpl();

	// transferencia entre cuentas propias
	@Override
	public boolean RealizarTransferencia(Transferencia trans) {
		return transfeDAO.insertarTransferencia(trans);
	}

	// trasferencias para otras cuentas con cbu
	@Override
	public boolean ValidarTransferencia(Cuenta cuenta, Transferencia trans) {
		boolean exiteCBU = transfeDAO.CBUexiste(cuenta);
		// validad si el cbu existe
		if (exiteCBU) {
			Cuenta cuentaDestino = transfeDAO.obtenerPorCBU(cuenta);
			return transfeDAO.insertarTransferenciaCBU(cuentaDestino, trans);
		}
		return false;
	}
}
