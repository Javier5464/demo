package org.example.demo.modelo.bd;

import org.example.demo.modelo.clases.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {
    public PeliculaDAO() {
    }
    public boolean insertarPelicula(Pelicula pelicula) {
        boolean insertado = false;

        try {
            Connection con = Conexion.getInstanceConexion();
            Statement statement = con.createStatement();

            String sqlMaxId = "SELECT MAX(id_pelicula) FROM peliculas";
            ResultSet rs = statement.executeQuery(sqlMaxId);
            int nuevoId = 1;
            if (rs.next()) {
                nuevoId = rs.getInt(1) + 1;
            }

            String sql = "INSERT INTO peliculas (id_pelicula, titulo, genero) VALUES (" + nuevoId + ",'" + pelicula.getitulo() + "','" + pelicula.getgenero() + "')";
            insertado = statement.executeUpdate(sql) > 0;

            Conexion.desconectar();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insertado;
    }


    public List<Pelicula> getBDPeliculas() {
        ResultSet rs;
        List<Pelicula> peliculaList = new ArrayList<>();
        try {
            Connection con = Conexion.getInstanceConexion();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM peliculas";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                peliculaList.add(new Pelicula(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            Conexion.desconectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return peliculaList;
    }



    public List<Pelicula> getBDPeliculasByName(String name) {
        List<Pelicula> peliculaList = new ArrayList<>();
        ResultSet rs;
        try {
            Connection con = Conexion.getInstanceConexion();
            String sql = "SELECT * FROM peliculas WHERE titulo LIKE ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                peliculaList.add(new Pelicula(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            Conexion.desconectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return peliculaList;
    }



    public int getIdPelicula() {
        ResultSet rs;
        int idPelicula = 0;

        try {
            Connection con= Conexion.getInstanceConexion();
            Statement statement= con.createStatement();
            String sql = "select MAX(id_pelicula) from peliculas";

            rs = statement.executeQuery(sql);
            if (rs.next()){
                idPelicula= rs.getInt(1);
            }

            Conexion.desconectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idPelicula;
    }
}



