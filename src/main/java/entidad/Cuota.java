package entidad;

import java.math.BigDecimal;
import java.sql.Date;

import Excepciones.MontoCuotaInvalido;

public class Cuota {
	private int IdCuota;
	private Prestamo prestamo;
	private Cuenta cuenta;
	private int NroCuota;
	private Date FechaVencimiento;

	private BigDecimal MontoCuota;
	private String Estado;

	public Cuota() {
	};

	public Cuota(int IdCuota, Prestamo prestamo, Cuenta cuenta, int NroCuota, Date FechaVencimiento, BigDecimal MontoCuota,
			String Estado) throws MontoCuotaInvalido {
		if (MontoCuota.compareTo(BigDecimal.ZERO) <= 0) {
			throw new MontoCuotaInvalido("El monto de la cuota debe ser mayor a 0 ");
		}

		this.IdCuota = IdCuota;
		this.prestamo = prestamo;
		this.cuenta = cuenta;
		this.NroCuota = NroCuota;
		this.FechaVencimiento = FechaVencimiento;

		this.MontoCuota = MontoCuota;
		this.Estado = Estado;
	}

	public int getIdCuota() {
		return IdCuota;
	}

	public void setIdCuota(int idCuota) {
		IdCuota = idCuota;
	}
	

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public int getNroCuota() {
		return NroCuota;
	}

	public void setNroCuota(int nroCuota) {
		NroCuota = nroCuota;
	}

	public Date getFechaVencimiento() {
		return FechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		FechaVencimiento = fechaVencimiento;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	public BigDecimal getMontoCuota() {
		return MontoCuota;
	}

	public void setMontoCuota(BigDecimal montoCuota) {
		MontoCuota = montoCuota;
	}

	@Override
	public String toString() {
		return "Cuota [IdCuota=" + IdCuota + ", prestamo=" + prestamo + ", cuenta=" + cuenta + ", NroCuota=" + NroCuota
				+ ", FechaVencimiento=" + FechaVencimiento + ", MontoCuota=" + MontoCuota + ", Estado=" + Estado + "]";
	}



}
