package entidad;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transferencia {
	
	
	 private int idTransferencia;       
	    private int nroCuentaOrigen;       
	    private int nroCuentaDestino;      
	    private LocalDate fecha;          
	    private BigDecimal importe;        
	    private char estado;      
	    
	    public Transferencia() { }

	    public Transferencia(int idTransferencia,
	                         int nroCuentaOrigen,
	                         int nroCuentaDestino,
	                         LocalDate fecha,
	                         BigDecimal importe,
	                         char estado) {
	        this.idTransferencia = idTransferencia;
	        this.nroCuentaOrigen = nroCuentaOrigen;
	        this.nroCuentaDestino = nroCuentaDestino;
	        this.fecha = fecha;
	        this.importe = importe;
	        this.estado = estado;
	    }
//get set
	    public int getIdTransferencia()      { return idTransferencia; }
	    public void setIdTransferencia(int idTransferencia) {
	        this.idTransferencia = idTransferencia;
	    }

	    public int getNroCuentaOrigen()      { return nroCuentaOrigen; }
	    public void setNroCuentaOrigen(int nroCuentaOrigen) {
	        this.nroCuentaOrigen = nroCuentaOrigen;
	    }

	    public int getNroCuentaDestino()     { return nroCuentaDestino; }
	    public void setNroCuentaDestino(int nroCuentaDestino) {
	        this.nroCuentaDestino = nroCuentaDestino;
	    }

	    public LocalDate getFecha()          { return fecha; }
	    public void setFecha(LocalDate fecha) {
	        this.fecha = fecha;
	    }

	    public BigDecimal getImporte()       { return importe; }
	    public void setImporte(BigDecimal importe) {
	        this.importe = importe;
	    }

	    public char getEstado()              { return estado; }
	    public void setEstado(char estado)   { this.estado = estado; }

		@Override
		public String toString() {
			return "Transferencia [idTransferencia=" + idTransferencia + ", nroCuentaOrigen=" + nroCuentaOrigen
					+ ", nroCuentaDestino=" + nroCuentaDestino + ", estado=" + estado + "]";
		}


}
