package logico;

public class Usuario implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String roll;
	private String user;
	private String contrasena;
	
	public Usuario(String tipo, String user, String contrasena) {
		super();
		this.roll = tipo;
		this.user = user;
		this.contrasena = contrasena;
	}

	public String getTipo() {
		return roll;
	}

	public void setTipo(String tipo) {
		this.roll = tipo;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	

}
