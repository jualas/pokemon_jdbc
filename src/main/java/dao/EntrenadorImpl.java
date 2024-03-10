package dao;

import conexion.ConnectionUtil;
import entidades.Entrenador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorImpl implements EntrenadorDao {

    @Override
    // Método para obtener un Entrenador específico por su ID
    public Entrenador get(long id) {
        Entrenador entrenador = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Entrenador WHERE EntrenadorID = ?");
            // Establecemos el ID del Entrenador que queremos obtener
            statement.setLong(1, id);
            // Ejecutamos la consulta
            ResultSet resultSet = statement.executeQuery();
            // Si obtenemos un resultado, creamos un nuevo objeto Entrenador con los datos obtenidos
            if (resultSet.next()) {
                entrenador = new Entrenador(
                        resultSet.getInt("EntrenadorID"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("Ciudad")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devolvemos el Entrenador obtenido (o null si no se encontró ninguno)
        return entrenador;
    }

    @Override
    // Método para obtener todos los Entrenadores
    public List<Entrenador> getAll() {
        List<Entrenador> entrenadores = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Entrenador");
            // Ejecutamos la consulta
            ResultSet resultSet = statement.executeQuery();
            // Para cada resultado, creamos un nuevo objeto Entrenador y lo añadimos a la lista
            while (resultSet.next()) {
                Entrenador entrenador = new Entrenador(
                        resultSet.getInt("EntrenadorID"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("Ciudad")
                );
                entrenadores.add(entrenador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devolvemos la lista de Entrenadores
        return entrenadores;
    }

    @Override
    // Método para guardar un nuevo Entrenador en la base de datos
    public void save(Entrenador entrenador) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Entrenador (EntrenadorID, Nombre, Ciudad) VALUES (?, ?, ?)");
            // Establecemos los valores del nuevo Entrenador
            statement.setInt(1, entrenador.getEntrenadorID());
            statement.setString(2, entrenador.getNombre());
            statement.setString(3, entrenador.getCiudad());
            // Ejecutamos la consulta
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    // Método para actualizar los datos de un Entrenador existente
    public void update(Entrenador entrenador, String[] params) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("UPDATE Entrenador SET Nombre = ?, Ciudad = ? WHERE EntrenadorID = ?");
            // Establecemos los nuevos valores del Entrenador
            statement.setString(1, params[0]);
            statement.setString(2, params[1]);
            // Establecemos el ID del Entrenador que queremos actualizar
            statement.setInt(3, entrenador.getEntrenadorID());
            // Ejecutamos la consulta
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    // Método para eliminar un Entrenador de la base de datos
    public void delete(Entrenador entrenador) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Entrenador WHERE EntrenadorID = ?");
            // Establecemos el ID del Entrenador que queremos eliminar
            statement.setInt(1, entrenador.getEntrenadorID());
            // Ejecutamos la consulta
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
