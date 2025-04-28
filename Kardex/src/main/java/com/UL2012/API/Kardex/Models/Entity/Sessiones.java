package com.UL2012.API.Kardex.Models.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "login_adms")
public class Sessiones {
    @Id
    @Column(name = "ID_Asistency")
    private String ID_Asistency;
    @Column(name = "Codigo_Personal")
    private  String Codigo_Personal;
    @Column(name = "User_Name")
    private String User_Name;
    @Column(name = "User_Paswword")
    private  String User_Paswword;
    @Column(name = "On_Session")
    private String On_Session;
    @Column(name = "Off_Session")
    private String Off_Session;

    public Sessiones() {
    }

    public Sessiones(String codigo_Personal) {
        Codigo_Personal = codigo_Personal;
    }

    public String getCodigo_Personal() {
        return Codigo_Personal;
    }

    public String getID_Asistency() {
        return ID_Asistency;
    }

    public void setID_Asistency(String ID_Asistency) {
        this.ID_Asistency = ID_Asistency;
    }

    public void setCodigo_Personal(String codigo_Personal) {
        Codigo_Personal = codigo_Personal;
    }

    public String getOff_Session() {
        return Off_Session;
    }

    public void setOff_Session(String off_Session) {
        Off_Session = off_Session;
    }

    public String getOn_Session() {
        return On_Session;
    }

    public void setOn_Session(String on_Session) {
        On_Session = on_Session;
    }

    public String getUser_Paswword() {
        return User_Paswword;
    }

    public void setUser_Paswword(String user_Paswword) {
        User_Paswword = user_Paswword;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }
}
