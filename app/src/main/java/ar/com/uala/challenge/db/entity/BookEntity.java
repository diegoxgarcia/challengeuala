package ar.com.uala.challenge.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_table")
public class BookEntity {

    @PrimaryKey
    private int id;
    private String nombre;
    private String autor;
    private boolean disponibilidad;
    private int popularidad;
    private String imagen;

    public BookEntity(int id, String nombre, String autor, boolean disponibilidad, int popularidad, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.disponibilidad = disponibilidad;
        this.popularidad = popularidad;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(int popularidad) {
        this.popularidad = popularidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
