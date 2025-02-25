package com.UL2012.API.Kardex.Models.Entity;

import jakarta.persistence.*;

@Entity
@NamedStoredProcedureQuery(
        name = "Admins.Login_Service",
        procedureName = "Login_Service",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "USERNAME", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "U_Password", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "isVerific", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "msg", type = String.class)
        }
)
@Table(name = "admins")

public class Admins {
    @Id
    @Column(name = "Codigo_Personal")
    private  String Cod_Per;
    @Column(name = "User_Name")
    private String  Username;
    @Column(name = "User_Paswword")
    private String Password;

    public Admins() {
    }

    public Admins(String cod_Per) {
        Cod_Per = cod_Per;
    }

    public String getCod_Per() {
        return Cod_Per;
    }

    public void setCod_Per(String cod_Per) {
        Cod_Per = cod_Per;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
