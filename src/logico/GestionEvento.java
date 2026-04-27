package logico;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logico.Usuario;

import java.io.Serializable;

import logico.Jurado;
import logico.Participante;
import logico.TrabajoCientifico;
import logico.Comision;
import logico.Evento;
import logico.GestionEvento;
import logico.Persona;
import logico.Recurso;
import logico.TrabajoCientifico;


public class GestionEvento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Persona>personas;
	private ArrayList<TrabajoCientifico>trabajos;
	private ArrayList<Evento>eventos;
	private ArrayList<Recurso>recursos;
	private ArrayList<Comision>comisiones;
	private ArrayList<Comision>comisionesaux;
	private static GestionEvento event = null;
	
	private ArrayList<Usuario> usuarios;
	private Usuario nowuser;
	
	private int codjurado;
	private int codparticipante;
	private int codtrabajo;
	private int codrecurso;
	private int codevento;
	private int codcomision;
	
	
	public GestionEvento() {
		super();
		this.personas= new ArrayList<>();
		this.trabajos = new ArrayList<>();
		this.recursos = new ArrayList<>();
		this.comisiones=new ArrayList<>();
		this.eventos = new ArrayList<>();
		this.comisionesaux = new ArrayList<>();
		this.usuarios = new ArrayList<>();
		codjurado = 1;
		codparticipante = 1;
		codtrabajo = 1;
		codrecurso = 1;
		codevento = 1;
		codcomision = 1;
	}

	public static GestionEvento getInstance(){
		   if(event == null){
			 event = new GestionEvento();  
		   } 	   
		   return event;
	}
	
	public Usuario getUser() {
		return nowuser;
	}

	public void setUser(Usuario user) {
		this.nowuser = user;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	
	
	public ArrayList<Persona> getPersonas() {
		return personas;
	}


	public void setPersonas(ArrayList<Persona> personas) {
		this.personas = personas;
	}


	public ArrayList<TrabajoCientifico> getTrabajos() {
		return trabajos;
	}


	public void setTrabajos(ArrayList<TrabajoCientifico> trabajos) {
		this.trabajos = trabajos;
	}


	public ArrayList<Evento> getEventos() {
		return eventos;
	}


	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
	}


	public ArrayList<Recurso> getRecursos() {
		return recursos;
	}


	public void setRecursos(ArrayList<Recurso> recursos) {
		this.recursos = recursos;
	}


	public ArrayList<Comision> getComisiones() {
		return comisiones;
	}


	public void setComisiones(ArrayList<Comision> comisiones) {
		this.comisiones = comisiones;
	}
	
	public void agregarcomisionesaux(Comision com) {
		comisionesaux.add(com);
		codcomision++;
		sqlInsertarComision(com); // INSERT comision + jurados miembros
		// Agregar tambien a la lista principal para que aparezca en MostrarComision
		if (indcomision(com.getIdcomision()) == -1) {
			comisiones.add(com);
		}
	}
	
	public ArrayList<Comision> getcomisionesaux() {
		return comisionesaux;
	}


	public static GestionEvento getEvent() {
		return event;
	}


	public static void setEvent(GestionEvento event) {
		GestionEvento.event = event;
	}


	public int getCodjurado() {
		return codjurado;
	}


	public void setCodjurado(int codjurado) {
		this.codjurado = codjurado;
	}


	public int getCodparticipante() {
		return codparticipante;
	}


	public void setCodparticipante(int codparticipante) {
		this.codparticipante = codparticipante;
	}


	public int getCodtrabajo() {
		return codtrabajo;
	}


	public void setCodtrabajo(int codtrabajo) {
		this.codtrabajo = codtrabajo;
	}


	public int getCodrecurso() {
		return codrecurso;
	}


	public void setCodrecurso(int codrecurso) {
		this.codrecurso = codrecurso;
	}


	public int getCodevento() {
		return codevento;
	}


	public void setCodevento(int codevento) {
		this.codevento = codevento;
	}


	public int getCodcomision() {
		return codcomision;
	}


	public void setCodcomision(int codcomision) {
		this.codcomision = codcomision;
	}
	
	
	//Funciones para agregar
	
	public void agregarpersonas(Persona persona) {
		personas.add(persona);

		if(persona instanceof Participante) {
			codparticipante++;
			sqlInsertarParticipante((Participante) persona);
		}
		if(persona instanceof Jurado) {
			codjurado++;
			sqlInsertarJurado((Jurado) persona);
		}
	}
	
	
	public void agregartrabajo(TrabajoCientifico trabajo) {
		trabajos.add(trabajo);
		codtrabajo++;
		// Buscar en qué comisión fue asignado (ya fue seteado por el llamador)
		String idComi = null;
		for (Comision c : comisiones) {
			for (TrabajoCientifico t : c.getTrabajos()) {
				if (t.getCodigo().equals(trabajo.getCodigo())) {
					idComi = c.getIdcomision();
					break;
				}
			}
			if (idComi != null) break;
		}
		sqlInsertarTrabajo(trabajo, idComi);
	}
	
	public void agregartrabajo(String cod,String titulo, String codparticipante, Comision comi){
		Participante parti = buscaparticipante(codparticipante);
		
		TrabajoCientifico trabajo = new TrabajoCientifico(cod, parti, titulo);
		
		agregartrabajo(trabajo);
		comi.agregartrabajos(trabajo);
		parti.agregartrabajo(trabajo);
	}
	
	public void agregarevento(Evento evento) {
		eventos.add(evento);
		codevento++;
		sqlInsertarEvento(evento); // INSERT evento + Evento_Comision + Evento_Recurso + UPDATE recursos
	}


	public void agregarrecurso(Recurso recurso) {
		recursos.add(recurso);
		codrecurso++;
		sqlInsertarRecurso(recurso);
	}


	public void agregarcomisiones(Comision comicion) {
		// Solo agregar si no esta ya en la lista (evitar duplicados con agregarcomisionesaux)
		if (indcomision(comicion.getIdcomision()) == -1) {
			comisiones.add(comicion);
		}
		// Sin SQL: la comision ya fue insertada en agregarcomisionesaux()
	}


	public void reguser(Usuario aux) {
		usuarios.add(aux);
		sqlInsertarUsuario(aux);
	}
	
	//Funciones para buscar
	
	public Comision buscacomision(String codigo) {
		Comision comi = null;
		boolean encontrado = false;
		int i = 0;
		
		while(i < comisiones.size() && encontrado == false) {
			if(comisiones.get(i).getIdcomision().equals(codigo))
			{
				encontrado = true;
				comi = comisiones.get(i);
			}
			
			i++;
		}
		
		return comi;
	}
	
	public Evento buscarEvento(String codigo) {
		Evento event = null;
		boolean encontrado = false;
		int i = 0;
		
		while(i < eventos.size() && encontrado == false) {
			if(eventos.get(i).getCodigo().equals(codigo))
			{
				encontrado = true;
				event = eventos.get(i);
			}
			
			i++;
		}
		
		return event;
	}
	
	public TrabajoCientifico buscatrabajo(String codigo) {
		TrabajoCientifico trab = null;
		boolean encontrado = false;
		int i = 0;
		
		while(i < trabajos.size() && encontrado == false) {
			if(trabajos.get(i).getCodigo().equals(codigo))
			{
				encontrado = true;
				trab = trabajos.get(i);
			}
			
			i++;
		}
		
		return trab;
	}
	
	public Participante buscaparticipante(String codigo) {
		
		Participante parti = null;
		boolean encontrado = false;
		int i = 0;
		
		while(i < personas.size() && encontrado == false) {
			if(personas.get(i) instanceof Participante)
			{
				if(((Participante)personas.get(i)).getCodparticipante().equals(codigo))
				{
					encontrado = true;
					parti = (Participante)personas.get(i);
				}
			}
			
			i++;
		}
		
		return parti;
	}
	
	public Participante buscaparticipantebycedula(String cedula) {
		Participante parti = null;
		boolean encontrado = false;
		int i = 0;
		
		while(i < personas.size() && encontrado == false) {
			if(personas.get(i) instanceof Participante)
			{
				if(((Participante)personas.get(i)).getCedula().equals(cedula))
				{
					encontrado = true;
					parti = (Participante)personas.get(i);
				}
			}
			
			i++;
		}
		
		return parti;
	}
	
	public Jurado buscarJurado(String codigo) {
		Jurado parti = null;
		boolean encontrado = false;
		int i = 0;
		
		while(i < personas.size() && encontrado == false) {
			if(personas.get(i) instanceof Jurado)
			{
				if(((Jurado)personas.get(i)).getCodjurado().equals(codigo))
				{
					encontrado = true;
					parti = (Jurado)personas.get(i);
				}
			}
			
			i++;
		}
		
		return parti;
	}
	
	public Recurso buscarrecurso(String codigo) {
		Recurso recu = null;
		boolean encontrado = false;
		int i = 0;
		
		while(i < recursos.size() && encontrado == false) {
			if(recursos.get(i).getCodigo().equals(codigo))
			{
				encontrado = true;
				recu = recursos.get(i);
			}
			
			i++;
		}
		
		return recu;
	}

        // funciones para eliminar
	
	public void eliminarRecurso(String codigo) {
		int ind = indRecurso(codigo);
		if(ind != -1) {
			recursos.remove(ind);
			sqlEliminarRecurso(codigo);
		}
	}
	
	public int indRecurso(String codigo) {
		int posi = -1;
		int i = 0;
		boolean seguir = true;
		
		while(i < recursos.size() && seguir == true)
		{
			if(recursos.get(i).getCodigo().equals(codigo))
			{
				posi = i;
				seguir = false;
			}	
			i++;
		}
		
		return posi;
	}
	
	public void eliminarTrabajo(TrabajoCientifico trabajo) {
		int ind = indTrabajo(trabajo.getCodigo());
		if(ind != -1) {
			trabajos.remove(ind);
			sqlEliminarTrabajo(trabajo.getCodigo());
		}
	}
	
	public int indTrabajo(String codigo) {
		int posi = -1;
		int i = 0;
		boolean seguir = true;
		
		while(i < trabajos.size() && seguir == true)
		{
			if(trabajos.get(i).getCodigo().equals(codigo))
			{
				posi = i;
				seguir = false;
			}	
			i++;
		}
		
		return posi;
	}

	
	public void eliminarEvento(String codigo) {
		int ind = indevento(codigo);
		if (ind != -1) {
			eventos.remove(ind);
			sqlEliminarEvento(codigo);
		}
	}

	public void eliminarparticipante(String codigo) {
		int ind = indparticipante(codigo);
		if(ind != -1) {
			personas.remove(ind);
			sqlEliminarParticipante(codigo);
		}
	}


	public void eliminarjurado(String codigo) {
		int ind = indjurado(codigo);
		if(ind != -1) {
			personas.remove(ind);
			sqlEliminarJurado(codigo);
		}
	}
	
	public int indcomision(String codigo) {
		int posi = -1;
		int i = 0;
		boolean seguir = true;
		
		while(i < comisiones.size() && seguir == true)
		{
			if(comisiones.get(i).getIdcomision().equalsIgnoreCase(codigo))
			{
				posi = i;
				seguir = false;
			}	
			i++;
		}
		
		return posi;
	}
	
	public void modicomision(Comision comision) {
		int ind = indcomision(comision.getIdcomision());
		if(ind != -1) {
			comisiones.set(ind, comision);
			sqlActualizarComision(comision);
		}
	}


	public void modifevento(Evento evento) {
		int ind = indevento(evento.getCodigo());
		if(ind != -1) {
			eventos.set(ind, evento);
			sqlActualizarEvento(evento);
		}
	}
	
	public int indevento(String codigo) {
		int posi = -1;
		int i = 0;
		boolean seguir = true;
		
		while(i < eventos.size() && seguir == true)
		{
			if(eventos.get(i).getCodigo().equalsIgnoreCase(codigo))
			{
				posi = i;
				seguir = false;
			}	
			i++;
		}
		
		return posi;
	}
	
	//Indices
	
	public int indparticipante(String codigo) {
		int posi = -1;
		int i = 0;
		boolean seguir = true;
		
		while(i < personas.size() && seguir == true)
		{
			if(personas.get(i) instanceof Participante && ((Participante)personas.get(i)).getCodparticipante().equals(codigo))
			{
				posi = i;
				seguir = false;
			}	
			i++;
		}
		
		return posi;
	}
	
	
	public int indjurado(String codigo) {
		int posi = -1;
		int i = 0;
		boolean seguir = true;
		
		while(i < personas.size() && seguir == true)
		{
			if(personas.get(i) instanceof Jurado && ((Jurado)personas.get(i)).getCodjurado().equals(codigo))
			{
				posi = i;
				seguir = false;
			}	
			i++;
		}
		
		return posi;
	}
	

	// Guarda los cambios en la BD al cerrar la aplicacion
	public void guardarDatos(String archivo) {
		for (Persona p : personas) {
			if (p instanceof Participante) sqlActualizarParticipante((Participante) p);
			else if (p instanceof Jurado)  sqlActualizarJurado((Jurado) p);
		}
		System.out.println("Datos sincronizados.");
	}

	// Carga todos los datos desde la BD al iniciar la aplicacion
	public static void cargarDatos(String archivo) {
		event = new GestionEvento();
		Connection conn = ConexionDB.getConnection();
		if (conn == null) {
			System.err.println("No hay conexion a la base de datos. Iniciando con datos vacios.");
			return;
		}

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;

			// Cargar usuarios
			ArrayList<Usuario> usuarios = new ArrayList<>();
			rs = stmt.executeQuery("SELECT roll, nombre_usuario, contrasena FROM Usuario");
			while (rs.next()) {
				usuarios.add(new Usuario(
					rs.getString("roll"),
					rs.getString("nombre_usuario"),
					rs.getString("contrasena")
				));
			}
			rs.close();
			event.setUsuarios(usuarios);

			// Cargar participantes
			ArrayList<Persona> personas = new ArrayList<>();
			rs = stmt.executeQuery("SELECT codparticipante, cedula, nombre, telefono FROM Participante");
			while (rs.next()) {
				personas.add(new Participante(
					rs.getString("cedula"),
					rs.getString("nombre"),
					rs.getString("telefono"),
					rs.getString("codparticipante")
				));
			}
			rs.close();

			// Cargar jurados
			rs = stmt.executeQuery("SELECT codjurado, cedula, nombre, telefono, areaespecializado FROM Jurado");
			while (rs.next()) {
				personas.add(new Jurado(
					rs.getString("cedula"),
					rs.getString("nombre"),
					rs.getString("telefono"),
					rs.getString("codjurado"),
					rs.getString("areaespecializado")
				));
			}
			rs.close();
			event.setPersonas(personas);

			// Ajustar contadores de codigos segun lo cargado
			int nParticipantes = 0, nJurados = 0;
			for (Persona p : personas) {
				if (p instanceof Participante) nParticipantes++;
				else if (p instanceof Jurado)  nJurados++;
			}
			event.setCodparticipante(nParticipantes + 1);
			event.setCodjurado(nJurados + 1);

			// Cargar recursos
			ArrayList<Recurso> recursos = new ArrayList<>();
			rs = stmt.executeQuery("SELECT codigo, disponible, ubicacion, tipo, descripcion FROM Recurso");
			while (rs.next()) {
				recursos.add(new Recurso(
					rs.getString("codigo"),
					rs.getBoolean("disponible"),
					rs.getString("ubicacion"),
					rs.getString("tipo"),
					rs.getString("descripcion")
				));
			}
			rs.close();
			event.setRecursos(recursos);
			event.setCodrecurso(recursos.size() + 1);

			// Cargar comisiones
			ArrayList<Comision> comisiones = new ArrayList<>();
			rs = stmt.executeQuery("SELECT idcomision, area, codjurado_presidente FROM Comision");
			while (rs.next()) {
				String codPres = rs.getString("codjurado_presidente");
				Jurado pres = null;
				if (codPres != null) {
					for (Persona p : personas) {
						if (p instanceof Jurado && ((Jurado) p).getCodjurado().equals(codPres)) {
							pres = (Jurado) p;
							break;
						}
					}
				}
				comisiones.add(new Comision(rs.getString("idcomision"), rs.getString("area"), pres));
			}
			rs.close();
			event.setComisiones(comisiones);
			event.setCodcomision(comisiones.size() + 1);

			// Cargar miembros de comisiones
			rs = stmt.executeQuery("SELECT idcomision, codjurado FROM Comision_Jurado");
			while (rs.next()) {
				String idComi = rs.getString("idcomision");
				String codJ   = rs.getString("codjurado");
				Comision comi = null;
				Jurado   jur  = null;
				for (Comision c : comisiones) {
					if (c.getIdcomision().equals(idComi)) { comi = c; break; }
				}
				for (Persona p : personas) {
					if (p instanceof Jurado && ((Jurado) p).getCodjurado().equals(codJ)) {
						jur = (Jurado) p; break;
					}
				}
				if (comi != null && jur != null) comi.agregarjurados(jur);
			}
			rs.close();

			// Cargar eventos
			ArrayList<Evento> eventos = new ArrayList<>();
			rs = stmt.executeQuery("SELECT codigo, nombre, ubicacion, fechainicio, fechafinal, cupo FROM Evento");
			while (rs.next()) {
				eventos.add(new Evento(
					rs.getString("nombre"),
					rs.getString("codigo"),
					rs.getString("ubicacion"),
					rs.getString("fechainicio"),
					rs.getString("fechafinal"),
					rs.getInt("cupo")
				));
			}
			rs.close();
			event.setEventos(eventos);
			event.setCodevento(eventos.size() + 1);

			// Cargar relacion Evento-Comision
			rs = stmt.executeQuery("SELECT codevento, idcomision FROM Evento_Comision");
			while (rs.next()) {
				String codEv  = rs.getString("codevento");
				String idComi = rs.getString("idcomision");
				Evento  ev = null;
				Comision c = null;
				for (Evento e : eventos) {
					if (e.getCodigo().equals(codEv)) { ev = e; break; }
				}
				for (Comision com : comisiones) {
					if (com.getIdcomision().equals(idComi)) { c = com; break; }
				}
				if (ev != null && c != null) ev.agregarcomision(c);
			}
			rs.close();

			// Cargar relacion Evento-Recurso
			rs = stmt.executeQuery("SELECT codevento, codrecurso FROM Evento_Recurso");
			while (rs.next()) {
				String codEv  = rs.getString("codevento");
				String codRec = rs.getString("codrecurso");
				Evento  ev  = null;
				Recurso rec = null;
				for (Evento e : eventos) {
					if (e.getCodigo().equals(codEv)) { ev = e; break; }
				}
				for (Recurso r : recursos) {
					if (r.getCodigo().equals(codRec)) { rec = r; break; }
				}
				if (ev != null && rec != null) ev.getRecursos().add(rec);
			}
			rs.close();

			// Cargar trabajos cientificos
			ArrayList<TrabajoCientifico> trabajos = new ArrayList<>();
			rs = stmt.executeQuery(
				"SELECT codigo, titulo, codparticipante, calificacion, primeracalificacion, idcomision " +
				"FROM TrabajoCientifico");
			while (rs.next()) {
				String codPart = rs.getString("codparticipante");
				Participante part = null;
				if (codPart != null) {
					for (Persona p : personas) {
						if (p instanceof Participante &&
							((Participante) p).getCodparticipante().equals(codPart)) {
							part = (Participante) p;
							break;
						}
					}
				}
				TrabajoCientifico t = new TrabajoCientifico(
					rs.getString("codigo"), part, rs.getString("titulo"));
				t.setCalificacion(rs.getFloat("calificacion"));
				t.setPrimeracalificaion(rs.getBoolean("primeracalificacion"));
				trabajos.add(t);

				// Vincular trabajo a su comision
				String idComi = rs.getString("idcomision");
				if (idComi != null) {
					for (Comision c : comisiones) {
						if (c.getIdcomision().equals(idComi)) {
							c.agregartrabajos(t);
							break;
						}
					}
				}
				// Vincular trabajo a su participante
				if (part != null) part.agregartrabajo(t);
			}
			rs.close();
			stmt.close();
			event.setTrabajos(trabajos);
			event.setCodtrabajo(trabajos.size() + 1);

			System.out.println("Datos cargados.");

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al cargar datos: " + e.getMessage());
		}
	}

	// Metodos privados para operaciones SQL

	private void sqlInsertarParticipante(Participante p) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Participante (codparticipante, cedula, nombre, telefono) VALUES (?, ?, ?, ?)");
			ps.setString(1, p.getCodparticipante());
			ps.setString(2, p.getCedula());
			ps.setString(3, p.getNombre());
			ps.setString(4, p.getTelefono());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL INSERT Participante: " + e.getMessage());
		}
	}

	private void sqlInsertarJurado(Jurado j) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Jurado (codjurado, cedula, nombre, telefono, areaespecializado) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, j.getCodjurado());
			ps.setString(2, j.getCedula());
			ps.setString(3, j.getNombre());
			ps.setString(4, j.getTelefono());
			ps.setString(5, j.getAreaespecializado());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL INSERT Jurado: " + e.getMessage());
		}
	}

	private void sqlInsertarRecurso(Recurso r) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Recurso (codigo, disponible, ubicacion, tipo, descripcion) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, r.getCodigo());
			ps.setBoolean(2, r.getdisponible());
			ps.setString(3, r.getUbicacion());
			ps.setString(4, r.getTipo());
			ps.setString(5, r.getDescripcion());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL INSERT Recurso: " + e.getMessage());
		}
	}

	private void sqlInsertarComision(Comision c) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Comision (idcomision, area, codjurado_presidente) VALUES (?, ?, ?)");
			ps.setString(1, c.getIdcomision());
			ps.setString(2, c.getArea());
			if (c.getPresidente() != null) ps.setString(3, c.getPresidente().getCodjurado());
			else ps.setNull(3, java.sql.Types.VARCHAR);
			ps.executeUpdate();
			ps.close();
			if (c.getJurados() != null && !c.getJurados().isEmpty()) {
				PreparedStatement psCJ = conn.prepareStatement(
					"INSERT INTO Comision_Jurado (idcomision, codjurado) VALUES (?, ?)");
				for (Jurado j : c.getJurados()) {
					psCJ.setString(1, c.getIdcomision());
					psCJ.setString(2, j.getCodjurado());
					psCJ.executeUpdate();
				}
				psCJ.close();
			}
		} catch (SQLException e) {
			System.err.println("SQL INSERT Comision: " + e.getMessage());
		}
	}

	private void sqlInsertarEvento(Evento ev) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Evento (codigo, nombre, ubicacion, fechainicio, fechafinal, cupo) VALUES (?, ?, ?, ?, ?, ?)");
			ps.setString(1, ev.getCodigo());
			ps.setString(2, ev.getNombre());
			ps.setString(3, ev.getUbicacion());
			ps.setString(4, ev.getFechainicio());
			ps.setString(5, ev.getFechafinal());
			ps.setInt(6, ev.getCupo());
			ps.executeUpdate();
			ps.close();
			if (ev.getComisiones() != null) {
				PreparedStatement psEC = conn.prepareStatement(
					"INSERT INTO Evento_Comision (codevento, idcomision) VALUES (?, ?)");
				for (Comision c : ev.getComisiones()) {
					psEC.setString(1, ev.getCodigo());
					psEC.setString(2, c.getIdcomision());
					psEC.executeUpdate();
				}
				psEC.close();
			}
			if (ev.getRecursos() != null) {
				PreparedStatement psER = conn.prepareStatement(
					"INSERT INTO Evento_Recurso (codevento, codrecurso) VALUES (?, ?)");
				for (Recurso r : ev.getRecursos()) {
					psER.setString(1, ev.getCodigo());
					psER.setString(2, r.getCodigo());
					psER.executeUpdate();
				}
				psER.close();
				// Actualizar recursos asignados (disponible=false, ubicacion del evento)
				for (Recurso r : ev.getRecursos()) {
					sqlActualizarRecurso(r);
				}
			}
		} catch (SQLException e) {
			System.err.println("SQL INSERT Evento: " + e.getMessage());
		}
	}

	private void sqlInsertarTrabajo(TrabajoCientifico t, String idComision) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO TrabajoCientifico (codigo, titulo, codparticipante, calificacion, primeracalificacion, idcomision) VALUES (?, ?, ?, ?, ?, ?)");
			ps.setString(1, t.getCodigo());
			ps.setString(2, t.getTitulo());
			if (t.getPropietario() != null) ps.setString(3, t.getPropietario().getCodparticipante());
			else ps.setNull(3, java.sql.Types.VARCHAR);
			ps.setFloat(4, t.getCalificacion());
			ps.setBoolean(5, t.isPrimeracalificaion());
			if (idComision != null) ps.setString(6, idComision);
			else ps.setNull(6, java.sql.Types.VARCHAR);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL INSERT TrabajoCientifico: " + e.getMessage());
		}
	}

	private void sqlInsertarUsuario(Usuario u) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO Usuario (roll, nombre_usuario, contrasena) VALUES (?, ?, ?)");
			ps.setString(1, u.getTipo());
			ps.setString(2, u.getUser());
			ps.setString(3, u.getContrasena());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL INSERT Usuario: " + e.getMessage());
		}
	}

	private void sqlActualizarParticipante(Participante p) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE Participante SET cedula=?, nombre=?, telefono=? WHERE codparticipante=?");
			ps.setString(1, p.getCedula());
			ps.setString(2, p.getNombre());
			ps.setString(3, p.getTelefono());
			ps.setString(4, p.getCodparticipante());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL UPDATE Participante: " + e.getMessage());
		}
	}

	private void sqlActualizarJurado(Jurado j) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE Jurado SET cedula=?, nombre=?, telefono=?, areaespecializado=? WHERE codjurado=?");
			ps.setString(1, j.getCedula());
			ps.setString(2, j.getNombre());
			ps.setString(3, j.getTelefono());
			ps.setString(4, j.getAreaespecializado());
			ps.setString(5, j.getCodjurado());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL UPDATE Jurado: " + e.getMessage());
		}
	}

	private void sqlActualizarComision(Comision c) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE Comision SET area=?, codjurado_presidente=? WHERE idcomision=?");
			ps.setString(1, c.getArea());
			if (c.getPresidente() != null) ps.setString(2, c.getPresidente().getCodjurado());
			else ps.setNull(2, java.sql.Types.VARCHAR);
			ps.setString(3, c.getIdcomision());
			ps.executeUpdate();
			ps.close();
			// Re-sincronizar miembros
			PreparedStatement psDel = conn.prepareStatement(
				"DELETE FROM Comision_Jurado WHERE idcomision=?");
			psDel.setString(1, c.getIdcomision());
			psDel.executeUpdate();
			psDel.close();
			if (c.getJurados() != null && !c.getJurados().isEmpty()) {
				PreparedStatement psIns = conn.prepareStatement(
					"INSERT INTO Comision_Jurado (idcomision, codjurado) VALUES (?, ?)");
				for (Jurado j : c.getJurados()) {
					psIns.setString(1, c.getIdcomision());
					psIns.setString(2, j.getCodjurado());
					psIns.executeUpdate();
				}
				psIns.close();
			}
		} catch (SQLException e) {
			System.err.println("SQL UPDATE Comision: " + e.getMessage());
		}
	}

	private void sqlActualizarEvento(Evento ev) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE Evento SET nombre=?, ubicacion=?, fechainicio=?, fechafinal=?, cupo=? WHERE codigo=?");
			ps.setString(1, ev.getNombre());
			ps.setString(2, ev.getUbicacion());
			ps.setString(3, ev.getFechainicio());
			ps.setString(4, ev.getFechafinal());
			ps.setInt(5, ev.getCupo());
			ps.setString(6, ev.getCodigo());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e2) {
			System.err.println("SQL UPDATE Evento: " + e2.getMessage());
		}
	}

	private void sqlActualizarRecurso(Recurso r) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE Recurso SET disponible=?, ubicacion=?, tipo=?, descripcion=? WHERE codigo=?");
			ps.setBoolean(1, r.getdisponible());
			ps.setString(2, r.getUbicacion());
			ps.setString(3, r.getTipo());
			ps.setString(4, r.getDescripcion());
			ps.setString(5, r.getCodigo());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL UPDATE Recurso: " + e.getMessage());
		}
	}

	private void sqlEliminarParticipante(String codigo) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement psN = conn.prepareStatement(
				"UPDATE TrabajoCientifico SET codparticipante=NULL WHERE codparticipante=?");
			psN.setString(1, codigo);
			psN.executeUpdate();
			psN.close();
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM Participante WHERE codparticipante=?");
			ps.setString(1, codigo);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL DELETE Participante: " + e.getMessage());
		}
	}

	private void sqlEliminarJurado(String codigo) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			// Quitar como presidente de comisiones
			PreparedStatement psP = conn.prepareStatement(
				"UPDATE Comision SET codjurado_presidente=NULL WHERE codjurado_presidente=?");
			psP.setString(1, codigo);
			psP.executeUpdate();
			psP.close();
			// Quitar de membresias de comisiones
			PreparedStatement psCJ = conn.prepareStatement(
				"DELETE FROM Comision_Jurado WHERE codjurado=?");
			psCJ.setString(1, codigo);
			psCJ.executeUpdate();
			psCJ.close();
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM Jurado WHERE codjurado=?");
			ps.setString(1, codigo);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL DELETE Jurado: " + e.getMessage());
		}
	}

	private void sqlEliminarRecurso(String codigo) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement psER = conn.prepareStatement(
				"DELETE FROM Evento_Recurso WHERE codrecurso=?");
			psER.setString(1, codigo);
			psER.executeUpdate();
			psER.close();
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM Recurso WHERE codigo=?");
			ps.setString(1, codigo);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL DELETE Recurso: " + e.getMessage());
		}
	}

	private void sqlActualizarTrabajo(TrabajoCientifico t) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE TrabajoCientifico SET calificacion=?, primeracalificacion=? WHERE codigo=?");
			ps.setFloat(1, t.getCalificacion());
			ps.setBoolean(2, t.isPrimeracalificaion());
			ps.setString(3, t.getCodigo());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL UPDATE TrabajoCientifico: " + e.getMessage());
		}
	}

	public void calificarTrabajo(String codigoTrabajo, float nota) {
		TrabajoCientifico t = buscatrabajo(codigoTrabajo);
		if (t != null) {
			t.setCalificacion(nota);
			t.setPrimeracalificaion(true);
			sqlActualizarTrabajo(t);
		}
	}

	private void sqlEliminarEvento(String codigo) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement psEC = conn.prepareStatement(
				"DELETE FROM Evento_Comision WHERE codevento=?");
			psEC.setString(1, codigo);
			psEC.executeUpdate();
			psEC.close();
			PreparedStatement psER = conn.prepareStatement(
				"DELETE FROM Evento_Recurso WHERE codevento=?");
			psER.setString(1, codigo);
			psER.executeUpdate();
			psER.close();
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM Evento WHERE codigo=?");
			ps.setString(1, codigo);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL DELETE Evento: " + e.getMessage());
		}
	}

	private void sqlEliminarTrabajo(String codigo) {
		Connection conn = ConexionDB.getConnection();
		if (conn == null) return;
		try {
			PreparedStatement ps = conn.prepareStatement(
				"DELETE FROM TrabajoCientifico WHERE codigo=?");
			ps.setString(1, codigo);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQL DELETE TrabajoCientifico: " + e.getMessage());
		}
	}

	public boolean confirmLogin(String usuar, String contra) {
		boolean login = false;
		//System.out.println(usuarios.size());
		for (Usuario user : usuarios) {
			if(user.getUser().equals(usuar) && user.getContrasena().equals(contra)){
				nowuser = user;
				login = true;
			}
		}
		return login;
	}


	
}