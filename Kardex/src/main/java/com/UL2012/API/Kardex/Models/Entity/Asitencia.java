package com.UL2012.API.Kardex.Models.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "Asistencia")
public class Asitencia {

    @Column(name = "Codigo_Personal")
    private  String Cod_Per;
    @Column(name = "Fecha")
    private  String Fecha;

    @Override
    public String toString() {
        return "Asitencia{" +
                "Cod_Per='" + Cod_Per + '\'' +
                ", Fecha='" + Fecha + '\'' +
                ", Area='" + Area + '\'' +
                ", Carrera='" + Carrera + '\'' +
                ", Turno='" + Turno + '\'' +
                ", Rol='" + Rol + '\'' +
                ", Hora_Ingreso='" + Hora_Ingreso + '\'' +
                ", Hora_Break='" + Hora_Break + '\'' +
                ", Retorno_Break='" + Retorno_Break + '\'' +
                ", Hora_Salida='" + Hora_Salida + '\'' +
                '}';
    }

    @Column(name = "Area")
    private  String Area;
    @Column(name = "Carrera")
    private  String Carrera;
    @Column(name = "Turno")
    private  String Turno ;
    @Column(name = "Rol")
    private  String Rol;
    @Column(name = "Hora_Ing")
    private  String Hora_Ingreso;
    @Column(name = "Sal_Break")
    private  String Hora_Break;
    @Column(name = "Ret_Brike")
    private  String Retorno_Break;
    @Column(name = "Hora_Sal")
    private  String Hora_Salida;

    public Asitencia(String cod_Per) {
        Cod_Per = cod_Per;
    }

    public Asitencia() {
    }

    public String getCod_Per() {
        return Cod_Per;
    }

    public String getHora_Salida() {
        return Hora_Salida;
    }

    public String getRetorno_Break() {
        return Retorno_Break;
    }

    public String getHora_Ingreso() {
        return Hora_Ingreso;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getArea() {
        return Area;
    }

    public String getCarrera() {
        return Carrera;
    }

    public String getTurno() {
        return Turno;
    }

    public void setCod_Per(String cod_Per) {
        Cod_Per = cod_Per;
    }

    public void setHora_Salida(String hora_Salida) {
        Hora_Salida = hora_Salida;
    }

    public void setRetorno_Break(String retorno_Break) {
        Retorno_Break = retorno_Break;
    }

    public void setHora_Break(String hora_Break) {
        Hora_Break = hora_Break;
    }

    public void setHora_Ingreso(String hora_Ingreso) {
        Hora_Ingreso = hora_Ingreso;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public void setTurno(String turno) {
        Turno = turno;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getRol() {
        return Rol;
    }

    public String getHora_Break() {
        return Hora_Break;
    }
}
