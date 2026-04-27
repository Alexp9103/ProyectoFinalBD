package logico;

import logico.Participante;
import java.io.Serializable;

public class TrabajoCientifico implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private Participante propietario;
	private float calificacion;
	private String titulo;
	private boolean primeracalificaion;
	
	public TrabajoCientifico(String codigo, Participante propietario, String titulo) {
		super();
		this.codigo = codigo;
		this.propietario = propietario;
		calificacion = 0;
		this.titulo = titulo;
		primeracalificaion = false;
		
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Participante getPropietario() {
		return propietario;
	}

	public void setPropietario(Participante propietario) {
		this.propietario = propietario;
	}

	public float getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(float calificacion) {
		this.calificacion = calificacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isPrimeracalificaion() {
		return primeracalificaion;
	}

	public void setPrimeracalificaion(boolean primeracalificaion) {
		this.primeracalificaion = primeracalificaion;
		
	}
}

