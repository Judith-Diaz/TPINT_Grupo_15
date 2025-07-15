package entidad;

import java.time.LocalDate;

public class Cliente {
	private String dni;
    private String cuil;
    private String nombre;
    private String apellido;
    private String sexo;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private String direccion;
    private Provincia provincia;
	private Localidad localidad;
    private String correoElectronico;
    private String telefonos;
    private Usuario usuario;
    private boolean estado;
    
    // CONSTRUCTOR VACIO
    public Cliente() {
        this.estado = true;
        this.fechaNacimiento = LocalDate.now();
    }

    // CONSTRUCTOR CON PARAMETROS
    public Cliente(String dni, String cuil, String nombre, String apellido, String sexo,
                   String nacionalidad, LocalDate fechaNacimiento, String direccion,
                   Provincia provincia, Localidad localidad, String correoElectronico,
                   String telefonos, boolean estado) {
        this.dni = dni;
        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.provincia = provincia;
        this.localidad = localidad;
        this.correoElectronico = correoElectronico;
        this.telefonos = telefonos;
        this.estado = estado;
    }

    // GETTERS Y SETTERS
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCuil() { return cuil; }
    public void setCuil(String cuil) { this.cuil = cuil; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
    
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefonos() { return telefonos; }
    public void setTelefonos(String telefonos) { this.telefonos = telefonos; }


    public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean getEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", cuil=" + cuil + ", nombre=" + nombre + ", apellido=" + apellido + ", sexo="
				+ sexo + ", nacionalidad=" + nacionalidad + ", fechaNacimiento=" + fechaNacimiento + ", direccion="
				+ direccion + ", provincia=" + provincia + ", localidad=" + localidad + ", correoElectronico="
				+ correoElectronico + ", telefonos=" + telefonos + ", usuario=" + usuario + ", estado=" + estado + "]";
	}





    

}
