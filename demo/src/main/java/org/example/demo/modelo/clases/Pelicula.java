package org.example.demo.modelo.clases;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pelicula {
    private final int idPelicula;
    private final StringProperty titulo;
    private final StringProperty genero;
    private final BooleanProperty favorita;


    public Pelicula(int idPelicula, String titulo, String genero) {
        this.idPelicula = idPelicula;
        this.titulo = new SimpleStringProperty(titulo);
        this.genero = new SimpleStringProperty(genero);
        this.favorita = new SimpleBooleanProperty(false);
    }


    public int getIdPelicula() {
        return idPelicula;
    }

    public String getitulo() {
        return titulo.get();
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public String getgenero() {
        return genero.get();
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public boolean isFavorita() {
        return favorita.get();
    }

    public BooleanProperty favoritaProperty() {
        return favorita;
    }

    public void setFavorita(boolean favorita) {
        this.favorita.set(favorita);
    }



    @Override
    public String toString() {
        return "Pelicula{" +
                "idPelicula=" + idPelicula +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                '}';
    }
}

