package entidad;

public class Usuario {

	// Atributos 
    private String dniUsu;
    private String nombreUsu;
    private String contraseniaUsu;
    private char rolUsu;

    // Constructor
    public Usuario() {
        //vavcio
    } 
    
    public Usuario(String dniUsu, String nombreUsu, String contraseniaUsu, char rolUsu) {
        this.setDniUsu(dniUsu);
        this.setNombreUsu(nombreUsu);
        this.setContraseniaUsu(contraseniaUsu);
        this.setRolUsu(rolUsu);
    }


	
	// Get Set
    public String getDniUsu() {
        return dniUsu;
    }
    
    public void setDniUsu(String dniUsu) {
        this.dniUsu = dniUsu;
       }
    
    public String getNombreUsu() {
        return nombreUsu;
    }
    
    public void setNombreUsu(String nombreUsu) {
    	 this.nombreUsu = nombreUsu;
        }
    
    public String getContraseniaUsu() {
        return contraseniaUsu;
    }
    public void setContraseniaUsu(String contraseniaUsu) {
    	 this.contraseniaUsu = contraseniaUsu;
    }
    public char getRolUsu() {
        return rolUsu;
    }
    public void setRolUsu(char rolUsu) {
    	this.rolUsu = rolUsu;
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "dniUsu='" + dniUsu + '\'' +
                ", nombreUsu='" + nombreUsu + '\'' +
                ", rolUsu=" + rolUsu +
                '}';
    }
}
