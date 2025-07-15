package entidad;

import java.math.BigDecimal;
import java.sql.Date;

import Excepciones.ImporteInvalidoException;
import Excepciones.SaldoInvalidoException;

public class Movimiento {
	private int IDMovimiento;
	private Date Fecha;
	private String Detalle;
	private BigDecimal Importe;
	private Cuenta Cuenta;
	private TipoMovimiento tipoMovimiento;
	private BigDecimal SaldoAnterior;
	private BigDecimal SaldoPosterior;

	public Movimiento() {}

	public Movimiento(int IDMovimiento, Date Fecha, String Detalle, BigDecimal Importe, Cuenta cuenta,
			TipoMovimiento tipoMovimiento, BigDecimal SaldoAnterior, BigDecimal SaldoPosterior)
			throws ImporteInvalidoException, SaldoInvalidoException {

		if (Importe.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ImporteInvalidoException("El Importe debe ser mayor a 0 ");
		}
		if (SaldoAnterior.compareTo(BigDecimal.ZERO) <= 0) {
			throw new SaldoInvalidoException("El saldo debe ser mayor a 0 ");
		}
		if (SaldoPosterior.compareTo(BigDecimal.ZERO) <= 0) {
			throw new SaldoInvalidoException("El saldo debe ser mayor a 0 ");
		}

		this.IDMovimiento = IDMovimiento;
		this.Fecha = Fecha;
		this.Detalle = Detalle;
		this.Importe = Importe;
		this.tipoMovimiento = tipoMovimiento;
		this.SaldoAnterior = SaldoAnterior;
		this.SaldoPosterior = SaldoPosterior;
		this.Cuenta = cuenta;
	}

	// Getters y setters
	public int getIDMovimiento() {
		return IDMovimiento;
	}

	public void setIDMovimiento(int iDMovimiento) {
		IDMovimiento = iDMovimiento;
	}

	public Date getFecha() {
		return Fecha;
	}

	public void setFecha(Date fecha) {
		Fecha = fecha;
	}

	public String getDetalle() {
		return Detalle;
	}

	public void setDetalle(String detalle) {
		Detalle = detalle;
	}

	public BigDecimal getImporte() {
		return Importe;
	}

	public void setImporte(BigDecimal importe) {
		Importe = importe;
	}

	public Cuenta getCuenta() {
		return Cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		Cuenta = cuenta;
	}

	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public BigDecimal getSaldoAnterior() {
		return SaldoAnterior;
	}

	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		SaldoAnterior = saldoAnterior;
	}

	public BigDecimal getSaldoPosterior() {
		return SaldoPosterior;
	}

	public void setSaldoPosterior(BigDecimal saldoPosterior) {
		SaldoPosterior = saldoPosterior;
	}

	@Override
	public String toString() {
		return "Movimiento [IDMovimiento=" + IDMovimiento + ", Fecha=" + Fecha + ", Detalle=" + Detalle + ", Importe="
				+ Importe + ", Cuenta=" + Cuenta + ", tipoMovimiento=" + tipoMovimiento + ", SaldoAnterior="
				+ SaldoAnterior + ", SaldoPosterior=" + SaldoPosterior + "]";
	}


}
