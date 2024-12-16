package org.example.demo.modelo.clases;

public class Productora {
    private int idProductora;
    private String productoraName;
    private Pelicula pelicula;

    public Productora() {
    }

    public Productora(int idProductora, String productoraName, Pelicula pelicula) {
        this.idProductora = idProductora;
        this.productoraName = productoraName;
        this.pelicula = pelicula;
    }

    public int getIdProductora() {
        return idProductora;
    }

    public void setIdProductora(int idProductora) {
        this.idProductora = idProductora;
    }

    public String getProductoraName() {
        return productoraName;
    }

    public void setProductoraName(String productoraName) {
        this.productoraName = productoraName;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    @Override
    public String toString() {
        return "Productora{" +
                "idProductor=" + idProductora +
                ", productorName='" + productoraName + '\'' +
                ", pelicula=" + pelicula +
                '}';
    }
}

