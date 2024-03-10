package dao;

import conexion.ConnectionUtil;
import entidades.Pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonImpl implements PokemonDao {

    @Override
    // Método para obtener un Pokemon específico por su ID
    public Pokemon get(long id) {
        Pokemon pokemon = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Pokemon WHERE PokemonID = ?");
            // Establecemos el ID del Pokemon que queremos obtener
            statement.setLong(1, id);
            // Ejecutamos la consulta
            ResultSet resultSet = statement.executeQuery();
            // Si obtenemos un resultado, creamos un nuevo objeto Pokemon con los datos obtenidos
            if (resultSet.next()) {
                pokemon = new Pokemon(
                        resultSet.getInt("PokemonID"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("Tipo"),
                        resultSet.getInt("Generacion"),
                        resultSet.getInt("EntrenadorID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devolvemos el Pokemon obtenido (o null si no se encontró ninguno)
        return pokemon;
    }

    @Override
    // Método para obtener todos los Pokemon
    public List<Pokemon> getAll() {
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Pokemon");
            // Ejecutamos la consulta
            ResultSet resultSet = statement.executeQuery();
            // Para cada resultado, creamos un nuevo objeto Pokemon y lo añadimos a la lista
            while (resultSet.next()) {
                Pokemon pokemon = new Pokemon(
                        resultSet.getInt("PokemonID"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("Tipo"),
                        resultSet.getInt("Generacion"),
                        resultSet.getInt("EntrenadorID")
                );
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devolvemos la lista de Pokemon
        return pokemons;
    }

    @Override
    // Método para guardar un nuevo Pokemon en la base de datos
    public void save(Pokemon pokemon) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Pokemon (PokemonID, Nombre, Tipo, Generacion, EntrenadorID) VALUES (?, ?, ?, ?, ?)");
            // Establecemos los valores del nuevo Pokemon
            statement.setInt(1, pokemon.getPokemonID());
            statement.setString(2, pokemon.getNombre());
            statement.setString(3, pokemon.getTipo());
            statement.setInt(4, pokemon.getGeneracion());
            statement.setInt(5, pokemon.getEntrenadorID());
            // Ejecutamos la consulta
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    // Método para actualizar los datos de un Pokemon existente
    public void update(Pokemon pokemon, String[] params) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("UPDATE Pokemon SET Nombre = ?, Tipo = ?, Generacion = ?, EntrenadorID = ? WHERE PokemonID = ?");
            // Establecemos los nuevos valores del Pokemon
            statement.setString(1, params[0]);
            statement.setString(2, params[1]);
            statement.setInt(3, Integer.parseInt(params[2]));
            statement.setInt(4, Integer.parseInt(params[3]));
            // Establecemos el ID del Pokemon que queremos actualizar
            statement.setInt(5, pokemon.getPokemonID());
            // Ejecutamos la consulta
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    // Método para eliminar un Pokemon de la base de datos
    public void delete(Pokemon pokemon) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Pokemon WHERE PokemonID = ?");
            // Establecemos el ID del Pokemon que queremos eliminar
            statement.setInt(1, pokemon.getPokemonID());
            // Ejecutamos la consulta
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todos los tipos de Pokemon únicos
    public List<String> getUniqueTypes() throws SQLException {
        List<String> types = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT Tipo FROM Pokemon");
            // Ejecutamos la consulta
            ResultSet resultSet = statement.executeQuery();
            // Para cada resultado, añadimos el tipo de Pokemon a la lista
            while (resultSet.next()) {
                types.add(resultSet.getString("Tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devolvemos la lista de tipos de Pokemon
        return types;
    }

    // Método para obtener todos los Pokemon de un tipo específico
    public List<Pokemon> getByType(String type) throws SQLException {
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            // Preparamos la consulta SQL
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Pokemon WHERE Tipo = ?");
            // Establecemos el tipo de Pokemon que queremos obtener
            statement.setString(1, type);
            // Ejecutamos la consulta
            ResultSet resultSet = statement.executeQuery();
            // Para cada resultado, creamos un nuevo objeto Pokemon y lo añadimos a la lista
            while (resultSet.next()) {
                Pokemon pokemon = new Pokemon(
                        resultSet.getInt("PokemonID"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("Tipo"),
                        resultSet.getInt("Generacion"),
                        resultSet.getInt("EntrenadorID")
                );
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devolvemos la lista de Pokemon
        return pokemons;
    }

    // Método para imprimir todos los tipos de Pokemon
    public static void printPokemonTypes() {
        try {
            // Creamos un objeto PokemonImpl para interactuar con la base de datos
            PokemonImpl pokemonImpl = new PokemonImpl();
            // Obtenemos una lista de todos los tipos únicos de Pokemon en la base de datos
            List<String> types = pokemonImpl.getUniqueTypes();
            // Imprimimos los tipos de Pokemon
            System.out.println("Tipos de Pokemon:");
            for (int i = 0; i < types.size(); i++) {
                System.out.println((i + 1) + ". " + types.get(i));
            }
        } catch (SQLException e) {
            // Si ocurre un error al interactuar con la base de datos, imprimimos el mensaje de error
            System.out.println("Error al interactuar con la base de datos: " + e.getMessage());
        }
    }


}