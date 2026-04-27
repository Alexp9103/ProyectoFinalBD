package logico;

import java.util.ArrayList;
import logico.TrabajoCientifico;

public class Participante extends Persona {
	
	private ArrayList<TrabajoCientifico>trabajos;
	private String idparticipante;
	
	public Participante(String id, String nombre, String telefono, String cod) {
		super(id, nombre, telefono);
		trabajos = new ArrayList<>();
	    this.idparticipante = cod;
	}
	
	public ArrayList<TrabajoCientifico> getTrabajos() {
		return trabajos;
	}

	public void setTrabajos(ArrayList<TrabajoCientifico> trabajos) {
		this.trabajos = trabajos;
	}

	public String getCodparticipante() {
		return idparticipante;
	}

	public void setCodparticipante(String codparticipante) {
		this.idparticipante = codparticipante;
	}
	
	public void agregartrabajo(TrabajoCientifico trabajo) {
		trabajos.add(trabajo);
	}

	public void removertrabajo(TrabajoCientifico trabajo) {
		// TODO Auto-generated method stub
		
	}

}
