package modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Constantes de configuración (¡Mejor si luego las pasas a un archivo .properties!)
    private static final String URL = "jdbc:mysql://localhost:3306/Gestor_Medico";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456";

    // Método único para obtener la conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
    
    // Método de utilidad para cerrar recursos y evitar fugas de memoria
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}