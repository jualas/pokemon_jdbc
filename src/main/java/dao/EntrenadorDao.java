package dao;

import entidades.Entrenador;
import java.util.List;

public interface EntrenadorDao {
    Entrenador get(long id);
    List<Entrenador> getAll();
    void save(Entrenador entrenador);
    void update(Entrenador entrenador, String[] params);
    void delete(Entrenador entrenador);
}

