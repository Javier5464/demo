package org.example.demo.modelo.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection jdbcConnection;
    private static String url ="jdbc:mysql://localhost:3306/cine";
    private static String user="root";
    private static String pwd="root";
    private static String driver= "com.mysql.cj.jdbc.Driver";

    private Conexion() {
        try {
            Class.forName(driver);
            jdbcConnection= DriverManager.getConnection(url,user,pwd);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getInstanceConexion() throws SQLException {
        if ((jdbcConnection==null)||(jdbcConnection.isClosed())){
            new Conexion();
        }
        return jdbcConnection;
    }
    public static void desconectar() throws SQLException {
        if ((jdbcConnection!=null)&&(!jdbcConnection.isClosed())){
            jdbcConnection.close();
        }
    }
}
