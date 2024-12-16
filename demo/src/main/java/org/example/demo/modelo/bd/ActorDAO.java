package org.example.demo.modelo.bd;

import org.example.demo.modelo.clases.Actor;
import org.example.demo.modelo.clases.Pelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorDAO {

    public static List<Actor> getBDActores() {
        ResultSet rs, rs2;
        String sql2;
        List<Actor> actorList = new ArrayList<Actor>();
        try {
            Connection con = Conexion.getInstanceConexion();

            String sql = "select * from actores";
            PreparedStatement preparedStatement= con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Actor actor = new Actor();
                actor.setidActor(rs.getInt(1));
                actor.setctorName(rs.getString(2));
                sql2 = "pe.id_pelicula, pe.titulo, pe.genero from peliculas pe, actores_peliculas ap " +
                        " where ap.pelicula_id= pe.id_pelicula and ap.actor_id = ?"  ;
                PreparedStatement ps2= con.prepareStatement(sql2);
                ps2.setInt(1,actor.getidActor());
                rs2 = ps2.executeQuery();
                List<Pelicula> peliculaList = new ArrayList<>();
                while (rs2.next()){
                    peliculaList.add(new Pelicula(
                            rs2.getInt(1), rs2.getString(2),rs2.getString(3)
                    ));
                }
                actor.setpeliculasList(peliculaList);
                actorList.add(actor);
            }

            Conexion.desconectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actorList;
    }

    public static List<Actor> getBDActoresOpcion2() {
        ResultSet rs;

        List<Actor> actorList = new ArrayList<Actor>();
        Map<Integer, Actor> actorMap= new HashMap<Integer, Actor>();
        try {
            Connection con = Conexion.getInstanceConexion();

            String sql = "select a.id_actor, a.nombre, pe.id_pelicula, pe.titulo, pe.genero from actores a, peliculas pe , actores_peliculas ap  " +
                    " where ap.pelicula_id= pe.id_pelicula and ap.actor_id = a.id_actor";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int indice = rs.getInt(1);
                if (actorMap.get(indice) == null) {
                    actorMap.put(indice, new Actor());
                    actorMap.get(indice).setidActor(indice);
                    actorMap.get(indice).setctorName(rs.getString(2));
                }
                actorMap.get(indice).addPelicula(new Pelicula(rs.getInt(3), rs.getString(4), rs.getString(5)));

            }
            for (Actor tm : actorMap.values())
                actorList.add(tm);


            Conexion.desconectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actorList;
    }

    public boolean insertarActor(Actor actor) {
        boolean insertado = false;

        try {
            Connection con = Conexion.getInstanceConexion();

            String sql = "insert into actores values (?,?) ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, actor.getidActor());
            preparedStatement.setString(2, actor.getactorName());
            insertado = preparedStatement.executeUpdate() > 0;

            Conexion.desconectar();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insertado;
    }

    public Actor getActorByName(String name) {
        ResultSet rs;
        Actor Actor = new Actor();
        try {
            Connection con = Conexion.getInstanceConexion();

            String sql = "select * from actores where nombre like ?";
            PreparedStatement preparedStatement= con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Actor.setidActor(rs.getInt(1));
                Actor.setctorName(rs.getString(2));
            }

            Conexion.desconectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Actor;
    }

    public void asociarPeliculas(Actor actor, List<Pelicula> peliculaList) {
        ResultSet rs;
        int idPelicula = 0;

        try {
            actor=this.getActorByName(actor.getactorName());
            Connection con = Conexion.getInstanceConexion();
            String sql = "insert into actores_peliculas values (?,?)";
            PreparedStatement preparedStatement= con.prepareStatement(sql);
            for (Pelicula c: peliculaList){
                preparedStatement.setInt(1,actor.getidActor());
                preparedStatement.setInt(2,c.getIdPelicula());
                preparedStatement.executeUpdate();
            }

            Conexion.desconectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}


