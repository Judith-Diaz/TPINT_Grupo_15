package Excepciones;

public class EdadInvalidaException extends Exception {
    private static final long serialVersionUID = 1L;

    public EdadInvalidaException(String mensaje) {
        super(mensaje);
    }
}