package com.UL2012.API.Kardex.Models.Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KardexSystemDb {
    private String  Estado ="Estableciendo la Conexion..";
    private Connection Conexion;

    public KardexSystemDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Registrar el controlador
            String db = "jdbc:mysql://localhost:3306/kardex_db";
            String user = "root";
            String password = "";
            this.Conexion = DriverManager.getConnection(db, user, password);
            this.Estado = "Conexion exitosa";
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontro el controlador JDBC.");
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public Connection getConexion() {
        return Conexion;
    }

    public void setConexion(Connection conexion) {
        Conexion = conexion;
    }
}
