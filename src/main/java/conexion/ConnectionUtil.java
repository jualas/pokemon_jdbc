package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    // URL de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/pokemon?useSSL=false";
    // Nombre de usuario para la base de datos
    private static final String USER = "root";

    /**
     * Este método se utiliza para obtener una conexión a la base de datos.
     * @return una conexión a la base de datos
     * @throws SQLException sí hay un problema al establecer la conexión
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Intenta obtener una conexión a la base de datos
            return DriverManager.getConnection(URL, USER, "");
        } catch (SQLException e) {
            // Imprime el mensaje de error si no se puede establecer la conexión
            System.out.println("No se pudo establecer la conexión a la base de datos: " + e.getMessage());
            throw e;
        }
    }
}