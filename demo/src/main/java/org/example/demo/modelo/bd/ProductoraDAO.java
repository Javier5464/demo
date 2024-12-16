package org.example.demo.modelo.bd;

import org.example.demo.modelo.clases.Pelicula;
import org.example.demo.modelo.clases.Productora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoraDAO {
    public ProductoraDAO() {
    }
    public boolean insertarProductora (Productora productora){
        boolean insertado=false;

        try {

            Connection con= Conexion.getInstanceConexion();
            Statement statement= con.createStatement();
            String sql = "insert into productoras values (" + productora.getIdProductora() + ",'" + productora.getProductoraName() + "'," +productora.getPelicula().getIdPelicula()  + ")";

            insertado=  statement.executeUpdate(sql) >0;

            Conexion.desconectar();

        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }
        return insertado;
    }

    public List<Productora> getBDProductoras() {
        List<Productora> productoraList= new ArrayList<>();

        ResultSet rs;
        try {
            Connection con= Conexion.getInstanceConexion();
            Statement statement= con.createStatement();

            String sql = "select p.id_productora, p.productura_name, pe.id_pelicula, pe.titulo, pe.genero from productoras p, peliculas pe where p.pelicula_id= pe.id_pelicula ";
            rs = statement.executeQuery(sql);
            while (rs.next()){
                productoraList.add (new Productora(rs.getInt(1), rs.getString(2),
                        new Pelicula(rs.getInt(3), rs.getString(4), rs.getString(5))));

            }

            Conexion.desconectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productoraList;
    }

    public List<Productora> getProductorasByName(String nameRepresentante){
        List<Productora> productoraList= new ArrayList<>();
        ResultSet rs;
        try {
            Connection con= Conexion.getInstanceConexion();
            String sql= "select * from productoras where productora_name like ? ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,nameRepresentante);
            rs= preparedStatement.executeQuery();
            while(rs.next()){
                productoraList.add (new Productora(rs.getInt(1), rs.getString(2), null));
            }
            Conexion.desconectar();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productoraList;
    }


}