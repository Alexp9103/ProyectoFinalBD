package logico;

import java.util.ArrayList;
import java.io.Serializable;

import logico.Comision;
import logico.TrabajoCientifico;
import logico.Jurado;
import logico.TrabajoCientifico;

public class Comision implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String idcomision;
	private String area;
	private Jurado presidente;
	private ArrayList<Jurado>jurados;
	private ArrayList<TrabajoCientifico>trabajos;
	
	public Comision(String idcomision, String area, Jurado presidente) {
		super();
		this.idcomision = idcomision;
		this.area = area;
		this.presidente = presidente;
		this.jurados = new ArrayList<>();
		this.trabajos = new ArrayList<>();
	}
	
	public void agregartrabajos(TrabajoCientifico trabajo) {
		trabajos.add(trabajo);
	}
	
	public String getIdcomision() {
		return idcomision;
	}

	public void setIdcomision(String idcomision) {
		this.idcomision = idcomision;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public Jurado getPresidente() {
		return presidente;
	}

	public void setPresidente(Jurado presidente) {
		this.presidente = presidente;
	}

	public ArrayList<Jurado> getJurados() {
		return jurados;
	}

	public void setJurados(ArrayList<Jurado> jurados) {
		this.jurados = jurados;
	}
	
	public void agregarjurados(Jurado j) {
		jurados.add(j);
	}

	public ArrayList<TrabajoCientifico> getTrabajos() {
		return trabajos;
	}
	

	public void setTrabajos(ArrayList<TrabajoCientifico> trabajos) {
		this.trabajos = trabajos;
	}
	
	public void removertrabajo(TrabajoCientifico trabajo) {
		int i = 0;
		boolean encontrado = false;
		while (i < trabajos.size() && encontrado != true) {
			if(trabajos.get(i).getCodigo().equalsIgnoreCase(trabajo.getCodigo())) {
				trabajos.remove(i);
				encontrado = true;
			}
			i++;
		}
		
	}

	

}