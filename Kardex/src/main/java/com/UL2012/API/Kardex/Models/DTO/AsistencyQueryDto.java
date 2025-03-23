package com.UL2012.API.Kardex.Models.DTO;

import java.util.ArrayList;
import java.util.List;

public class AsistencyQueryDto {
    private String cod_Per;
    private String fecha;
    private String area;
    private String Carrera;
    private String turno;
    private String rol;
    private String retorno_Break;
    private String hora_Salida;


    public AsistencyQueryDto() {
    }

    public AsistencyQueryDto(String cod_Per, String fecha, String area,String Carr, String turno, String rol, String retorno_Break, String hora_Salida) {
        this.cod_Per = cod_Per;
        this.fecha = fecha;
        this.Carrera = Carr;
        this.area = area;
        this.turno = turno;
        this.rol = rol;
        this.retorno_Break = retorno_Break;
        this.hora_Salida = hora_Salida;
    }

    public String getCod_Per() {
        return cod_Per;
    }

    public void setCod_Per(String cod_Per) {
        this.cod_Per = cod_Per;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRetorno_Break() {
        return retorno_Break;
    }

    public void setRetorno_Break(String retorno_Break) {
        this.retorno_Break = retorno_Break;
    }

    public String getHora_Salida() {
        return hora_Salida;
    }

    public void setHora_Salida(String hora_Salida) {
        this.hora_Salida = hora_Salida;
    }
}