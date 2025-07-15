package entidad;

public class TipoMovimiento {
private int IdTipoMovimiento;
private String NombreTipo;
private String Descripcion;

public TipoMovimiento() {};

public TipoMovimiento(int IdTipoMovimiento,String NombreTipo,String Descripcion) {
this.IdTipoMovimiento=IdTipoMovimiento;
this.NombreTipo=NombreTipo;
this.Descripcion=Descripcion;
	
}
public int getIdTipoMovimiento() {
	return IdTipoMovimiento;
}
public void setIdTipoMovimiento(int idTipoMovimiento) {
	IdTipoMovimiento = idTipoMovimiento;
}
public String getNombreTipo() {
	return NombreTipo;
}
public void setNombreTipo(String nombreTipo) {
	NombreTipo = nombreTipo;
}
public String getDescripcion() {
	return Descripcion;
}
public void setDescripcion(String descripcion) {
	Descripcion = descripcion;
}
@Override
public String toString() {
	return "TipoMovimiento [IdTipoMovimiento=" + IdTipoMovimiento + ", NombreTipo=" + NombreTipo + ", Descripcion="
			+ Descripcion + "]";
}

}
