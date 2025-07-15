package entidad;

import java.math.BigDecimal;
import java.time.LocalDate;

import Excepciones.ImporteInvalidoException;
import Excepciones.MontoCuotaInvalido;

public class Prestamo {
	private int idPrestamo;
	private Cliente cliente;
	private Cuenta cuenta;
	private LocalDate fecha;
	private BigDecimal importeTotal;
	private BigDecimal importeSolicitado;
	private int plazoMeses;
	private BigDecimal montoMensual;
	private String cuotas;
	private String estado;

	public Prestamo() {
	}

	public Prestamo(int idPrestamo, Cliente cliente, Cuenta cuenta, LocalDate fecha, BigDecimal importeTotal,
			BigDecimal importeSolicitado, int plazoMeses, BigDecimal montoMensual, String cuotas, String estado)
			throws ImporteInvalidoException, MontoCuotaInvalido {

		//
		if (importeTotal.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ImporteInvalidoException("El importe debe ser mayor a 0 ");
		}
		if (importeSolicitado.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ImporteInvalidoException("El importe debe ser mayor a 0 ");
		}
		if (montoMensual.compareTo(BigDecimal.ZERO) <= 0) {
			throw new MontoCuotaInvalido("El monto debe ser mayor a 0 ");
		}

		this.idPrestamo = idPrestamo;
		this.cliente = cliente;
		this.cuenta = cuenta;
		this.fecha = fecha;
		this.importeTotal = importeTotal;
		this.importeSolicitado = importeSolicitado;
		this.plazoMeses = plazoMeses;
		this.montoMensual = montoMensual;
		this.cuotas = cuotas;
		this.estado = estado;
	}

	// set get
	public int getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(BigDecimal it) {
		this.importeTotal = it;
	}

	public BigDecimal getImporteSolicitado() {
		return importeSolicitado;
	}

	public void setImporteSolicitado(BigDecimal is) {
		this.importeSolicitado = is;
	}

	public int getPlazoMeses() {
		return plazoMeses;
	}

	public void setPlazoMeses(int plazoMeses) {
		this.plazoMeses = plazoMeses;
	}

	public BigDecimal getMontoMensual() {
		return montoMensual;
	}

	public void setMontoMensual(BigDecimal mm) {
		this.montoMensual = mm;
	}

	public String getCuotas() {
		return cuotas;
	}

	public void setCuotas(String cuotas) {
		this.cuotas = cuotas;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Prestamo [idPrestamo=" + idPrestamo + ", cliente=" + cliente + ", cuenta=" + cuenta + ", fecha=" + fecha
				+ ", importeTotal=" + importeTotal + ", importeSolicitado=" + importeSolicitado + ", plazoMeses="
				+ plazoMeses + ", montoMensual=" + montoMensual + ", cuotas=" + cuotas + ", estado=" + estado + "]";
	}



}
