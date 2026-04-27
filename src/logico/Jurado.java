package logico;

public class Jurado extends Persona{
	
	private String idjurado;
	private String areaespecializado;

	public Jurado(String cedula, String nombre, String telefono,String Codjurado,String areaespecializado) {
    	super(cedula, nombre, telefono);
		this.idjurado = Codjurado;
		this.areaespecializado = areaespecializado;
	}

	public String getCodjurado() {
		return idjurado;
	}

	public void setCodjurado(String Codjurado) {
		this.idjurado = Codjurado;
	}

	public String getAreaespecializado() {
		return areaespecializado;
	}

	public void setAreaespecializado(String areaespecializado) {
		this.areaespecializado = areaespecializado;
	}
	
	

}
