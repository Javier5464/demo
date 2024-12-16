package org.example.demo.modelo.bd;

import org.example.demo.modelo.clases.Pelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean autenticarUsuario(String username, String password) {
        try (Connection con = Conexion.getInstanceConexion()) {
            String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registrarUsuario(String username, String password) {
        try (Connection con = Conexion.getInstanceConexion()) {
            String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean agregarPeliculaFavorita(int userid, int peliculaId) {
        try (Connection con = Conexion.getInstanceConexion()) {
            String sql = "INSERT INTO usuarios_peliculas_favoritas (usuario_id, pelicula_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setInt(2, peliculaId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Pelicula> obtenerPeliculasFavoritas(int userid) {
        List<Pelicula> peliculasFavoritas = new ArrayList<>();
        try (Connection con = Conexion.getInstanceConexion()) {
            String sql = "SELECT p.id_pelicula, p.titulo, p.genero FROM peliculas p " +
                    "JOIN usuarios_peliculas_favoritas upf ON p.id_pelicula = upf.pelicula_id " +
                    "WHERE upf.usuario_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pelicula pelicula = new Pelicula(rs.getInt("id_pelicula"), rs.getString("titulo"), rs.getString("genero"));
                peliculasFavoritas.add(pelicula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peliculasFavoritas;
    }

    public boolean isFavorita(int userid, int peliculaId) {
        try (Connection con = Conexion.getInstanceConexion()) {
            String sql = "SELECT * FROM usuarios_peliculas_favoritas WHERE usuario_id = ? AND pelicula_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setInt(2, peliculaId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Si hay un registro, la película es favorita
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}




