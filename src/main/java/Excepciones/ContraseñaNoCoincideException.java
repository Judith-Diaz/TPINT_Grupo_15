package Excepciones;

public class ContraseñaNoCoincideException extends Exception{
private static final long serialVersionUID = 1L;
    
     
    public ContraseñaNoCoincideException(String mensaje) {
        super(mensaje);
    }
  
}
