package entidad;

public class TipoCuenta {
	private int IdTipoCuenta;
	private String NombreTipo;

	public TipoCuenta() {
	};

	public TipoCuenta(int IdTipoCuenta, String NombreTipo) {
		this.IdTipoCuenta = IdTipoCuenta;
		this.NombreTipo = NombreTipo;
	}

	public int getIdTipoCuenta() {
		return IdTipoCuenta;
	}

	public void setIdTipoCuenta(int idTipoCuenta) {
		IdTipoCuenta = idTipoCuenta;
	}

	public String getNombreTipo() {
		return NombreTipo;
	}

	public void setNombreTipo(String nombreTipo) {
		NombreTipo = nombreTipo;
	}

	@Override
	public String toString() {
		return "TipoCuenta [IdTipoCuenta=" + IdTipoCuenta + ", NombreTipo=" + NombreTipo + "]";
	}

}
