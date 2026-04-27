package logico;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;

import logico.Comision;
import logico.Recurso;

public class Evento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String codigo;
	private String ubicacion;
	private String fechainicio;
	private String fechafinal;
	private int cupo;
	private ArrayList<Recurso>recursos;
	private ArrayList<Comision>comisiones;
	
	public Evento(String nombre, String codigo,String ubicacion, String fechainicio,String fechafinal,int cupo) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
		this.ubicacion=ubicacion;
		this.fechainicio = fechainicio;
		this.fechafinal=fechafinal;
		this.cupo=cupo;
		this.comisiones = new ArrayList<>();
		this.recursos = new ArrayList<>();

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}

	public String getFechafinal() {
		return fechafinal;
	}

	public void setFechafinal(String fechafinal) {
		this.fechafinal = fechafinal;
	}

	public int getCupo() {
		return cupo;
	}

	public void setCupo(int cupo) {
		this.cupo = cupo;
	}

	public ArrayList<Recurso> getRecursos() {
		return recursos;
	}

	public ArrayList<Comision> getComisiones() {
		return comisiones;
	}

	public void setComisiones(ArrayList<Comision> comisiones) {
		for (Comision comi : comisiones) {
			agregarcomision(comi);
		}
	}
	
	public void agregarcomision(Comision c) {
		comisiones.add(c);
	}
	
	public void agregarrecurso(Recurso cosa) {
		recursos.add(cosa);
		cosa.setUbicacion(ubicacion);
		cosa.setdisponible(false);
	}
	
	public void setRecursos(ArrayList<Recurso> recursoss) {
		for (Recurso recurso : recursoss) {
			agregarrecurso(recurso);
		}
	}
	
	
	
}