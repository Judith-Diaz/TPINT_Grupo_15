package entidad;

public class Localidad {
	private String idLocalidad_Loc;
	private String idProvincia_Loc;
	private String descripcion_Loc;

	// Constructor vacío
	public Localidad() {
	}

	public Localidad(String idLocalidad_Loc, String idProvincia_Loc, String descripcion_Loc) {
		this.idLocalidad_Loc = idLocalidad_Loc;
		this.idProvincia_Loc = idProvincia_Loc;
		this.descripcion_Loc = descripcion_Loc;
	}

	// Getters y setters
	public String getIdLocalidad_Loc() {
		return idLocalidad_Loc;
	}

	public void setIdLocalidad_Loc(String idLocalidad_Loc) {
		this.idLocalidad_Loc = idLocalidad_Loc;
	}

	public String getIdProvincia_Loc() {
		return idProvincia_Loc;
	}

	public void setIdProvincia_Loc(String idProvincia_Loc) {
		this.idProvincia_Loc = idProvincia_Loc;
	}

	public String getDescripcion_Loc() {
		return descripcion_Loc;
	}

	public void setDescripcion_Loc(String descripcion_Loc) {
		this.descripcion_Loc = descripcion_Loc;
	}
}
