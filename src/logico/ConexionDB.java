package logico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clase singleton para manejar la conexion a la base de datos
public class ConexionDB {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;" +
        "databaseName=EventosPUCMM;" +
        "integratedSecurity=true;" +
        "encrypt=true;" +
        "trustServerCertificate=true;";

    private static Connection connection = null;

    private ConexionDB() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL);
                System.out.println("Conexion a SQL Server establecida correctamente.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC no encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }
}
