package org.example.demo.modelo.clases;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private int idActor;
    private String actorName;
    List<Pelicula> peliculasList;

    public Actor() {
        peliculasList=new ArrayList<>();
    }

    public Actor(int idActor, String actorName, List<Pelicula> peliculasList) {
        this.idActor = idActor;
        this.actorName = actorName;
        this.peliculasList = peliculasList;
    }

    public int getidActor() {
        return idActor;
    }

    public void setidActor(int idActor) {
        this.idActor = idActor;
    }

    public String getactorName() {
        return actorName;
    }

    public void setctorName(String actorName) {
        this.actorName = actorName;
    }

    public void addPelicula (Pelicula pelicula){
        this.peliculasList.add(pelicula);
    }
    public List<Pelicula> getpeliculasList() {
        return peliculasList;
    }

    public void setpeliculasList(List<Pelicula> peliculasList) {
        this.peliculasList = peliculasList;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "idActor=" + idActor +
                ", actorName='" + actorName + '\'' +
                ", peliculasList=" + peliculasList +
                '}';
    }
}
