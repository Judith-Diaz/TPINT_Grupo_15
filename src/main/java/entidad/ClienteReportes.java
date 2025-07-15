package entidad;

public class ClienteReportes {

    private String nombre;
    private int cantidadCuentas;

    public ClienteReportes(String nombre, int cantidadCuentas) {
        this.nombre = nombre;
        this.cantidadCuentas = cantidadCuentas;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidadCuentas() {
		return cantidadCuentas;
	}

	public void setCantidadCuentas(int cantidadCuentas) {
		this.cantidadCuentas = cantidadCuentas;
	}

	@Override
	public String toString() {
		return "ClienteReportes [nombre=" + nombre + ", cantidadCuentas=" + cantidadCuentas + "]";
	}



}
