package entidades;

import java.util.Objects;

public class Entrenador {
    private int EntrenadorID;
    private String Nombre;
    private String Ciudad;

    // Getters y setters para los atributos
    public int getEntrenadorID() {
        return EntrenadorID;
    }

    public void setEntrenadorID(int EntrenadorID) {
        this.EntrenadorID = EntrenadorID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String Ciudad) {
        this.Ciudad = Ciudad;
    }

    // Constructor
    public Entrenador(int EntrenadorID, String Nombre, String Ciudad) {
        this.EntrenadorID = EntrenadorID;
        this.Nombre = Nombre;
        this.Ciudad = Ciudad;
    }

    // Método equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrenador that = (Entrenador) o;
        return EntrenadorID == that.EntrenadorID &&
                Nombre.equals(that.Nombre) &&
                Ciudad.equals(that.Ciudad);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return Objects.hash(EntrenadorID, Nombre, Ciudad);
    }

    // Método toString
    @Override
    public String toString() {
        return "Entrenador{" +
                "EntrenadorID=" + EntrenadorID +
                ", Nombre='" + Nombre + '\'' +
                ", Ciudad='" + Ciudad + '\'' +
                '}';
    }
}