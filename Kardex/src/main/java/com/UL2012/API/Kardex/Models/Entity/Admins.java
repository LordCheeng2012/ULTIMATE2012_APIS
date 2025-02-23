package com.UL2012.API.Kardex.Models.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admins {
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
