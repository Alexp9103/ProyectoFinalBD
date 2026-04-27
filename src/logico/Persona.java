package logico;

import java.io.Serializable;


public abstract class Persona implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	protected String cedula;
	protected String nombre;
	protected String telefono;
	
	public Persona(String cedula, String nombre, String telefono) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.telefono = telefono;
	}


	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
}