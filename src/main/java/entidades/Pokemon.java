package entidades;

import java.util.Objects;

public class Pokemon {
    private int PokemonID;
    private String Nombre;
    private String Tipo;
    private Integer Generacion;
    private Integer EntrenadorID;

    // Getters y setters para los atributos
    public int getPokemonID() {
        return PokemonID;
    }

    public void setPokemonID(int PokemonID) {
        this.PokemonID = PokemonID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public Integer getGeneracion() {
        return Generacion;
    }

    public void setGeneracion(Integer Generacion) {
        this.Generacion = Generacion;
    }

    public Integer getEntrenadorID() {
        return EntrenadorID;
    }

    public void setEntrenadorID(Integer EntrenadorID) {
        this.EntrenadorID = EntrenadorID;
    }

    // Constructor
    public Pokemon(int PokemonID, String Nombre, String Tipo, Integer Generacion, Integer EntrenadorID) {
        this.PokemonID = PokemonID;
        this.Nombre = Nombre;
        this.Tipo = Tipo;
        this.Generacion = Generacion;
        this.EntrenadorID = EntrenadorID;
    }

    // Método equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return PokemonID == pokemon.PokemonID &&
                Nombre.equals(pokemon.Nombre) &&
                Tipo.equals(pokemon.Tipo) &&
                Generacion.equals(pokemon.Generacion) &&
                EntrenadorID.equals(pokemon.EntrenadorID);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return Objects.hash(PokemonID, Nombre, Tipo, Generacion, EntrenadorID);
    }

    // Método toString
    @Override
    public String toString() {
        return
                "PokemonID=" + PokemonID +
                ", Nombre='" + Nombre + '\'' +
                ", Tipo='" + Tipo + '\'' +
                ", Generacion=" + Generacion +
                ", EntrenadorID=" + EntrenadorID ;
    }
}
