package entidad;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import Excepciones.SaldoInvalidoException;

public class Cuenta {
	private int numeroCuenta;
	private Cliente cliente;
	private LocalDate fechaCreacion;
	private TipoCuenta tipoCuenta;
	private String cbu;
	private double saldo;
	private boolean estado;

	// Constructor vacío
	public Cuenta() {
		this.saldo = 0.0;
		this.fechaCreacion = LocalDate.now();
	}

	// Constructor con parametros
	public Cuenta(int numeroCuenta, Cliente cliente, LocalDate fechaCreacion, TipoCuenta tipoCuenta, String cbu,
			double saldo, boolean estado) {
		this.numeroCuenta = numeroCuenta;
		this.cliente = cliente;
		this.fechaCreacion = fechaCreacion;
		this.tipoCuenta = tipoCuenta;
		this.cbu = cbu;
		this.saldo = saldo;
		this.estado = estado;
	}

	// Getters y Setters
	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getCbu() {
		return cbu;
	}

	public void setCbu(String cbu) {
		this.cbu = cbu;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}


	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	@Override
	public String toString() {
		return "Cuenta [numeroCuenta=" + numeroCuenta + ", cliente=" + cliente + ", fechaCreacion=" + fechaCreacion
				+ ", tipoCuenta=" + tipoCuenta + ", cbu=" + cbu + ", saldo=" + saldo + ", estado=" + estado + "]";
	}







	
}