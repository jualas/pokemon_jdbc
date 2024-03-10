package dao;

import entidades.Pokemon;
import java.util.List;

public interface PokemonDao {
    Pokemon get(long id);
    List<Pokemon> getAll();
    void save(Pokemon pokemon);
    void update(Pokemon pokemon, String[] params);
    void delete(Pokemon pokemon);
}
