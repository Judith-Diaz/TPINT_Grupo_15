package Excepciones;

public class SaldoInvalidoException extends Exception {
   
	private static final long serialVersionUID = 1L;

	public SaldoInvalidoException(String mensaje) {
        super(mensaje);
    }
}