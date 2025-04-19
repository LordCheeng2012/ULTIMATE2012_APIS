package com.UL2012.API.Kardex.Models.Entity;


import jakarta.persistence.*;
import java.time.*;
import java.util.Date;

@Entity
@Table(name = "Asistencia")
public class Asitencia {

    @Id
    @Column(name = "ID_Asistency")
    private String ID_Asistency;
    @Column(name = "Codigo_Personal")
    private  String Cod_Per;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date Fecha;
    @Column(name = "Area")
    private  String Area;
    @Column(name = "Carrera")
    private  String Carrera;
    @Column(name = "Turno")
    private  String Turno ;
    @Column(name = "Rol")
    private  String Rol;
    @Column(name = "Hora_Ing",nullable = true)
    @Temporal(TemporalType.TIME)
    private LocalTime Hora_Ingreso;
    @Column(name = "Sal_Break", nullable = true )
    @Temporal(TemporalType.TIME)
    private  LocalTime Hora_Break;
    @Column(name = "Ret_Brike" ,nullable = true)
    @Temporal(TemporalType.TIME)
    private  LocalTime Retorno_Break;
    @Column(name = "Hora_Sal",nullable = true )
    @Temporal(TemporalType.TIME)
    private  LocalTime Hora_Salida;

    public String getID_Asistency() {
        return ID_Asistency;
    }

    public void setID_Asistency(String ID_Asistency) {
        this.ID_Asistency = ID_Asistency;
    }

    @Override
    public String toString() {
        return "Asitencia{" +
                "'ID_Asistency'" + ID_Asistency +'\''+
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
    public Asitencia(String ID_As) {
        ID_Asistency=ID_As;
    }

    public Asitencia() {
    }

    public LocalTime getHora_Ingreso() {
        return Hora_Ingreso;
    }

    public void setHora_Ingreso(LocalTime hora_Ingreso) {
        Hora_Ingreso = hora_Ingreso;
    }

    public LocalTime getHora_Break() {
        return Hora_Break;
    }

    public void setHora_Break(LocalTime hora_Break) {
        Hora_Break = hora_Break;
    }

    public LocalTime getRetorno_Break() {
        return Retorno_Break;
    }

    public void setRetorno_Break(LocalTime retorno_Break) {
        Retorno_Break = retorno_Break;
    }

    public LocalTime getHora_Salida() {
        return Hora_Salida;
    }

    public void setHora_Salida(LocalTime hora_Salida) {
        Hora_Salida = hora_Salida;
    }

    public String getCod_Per() {
        return Cod_Per;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
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

    public void setRol(String rol) {
        Rol = rol;
    }

    public void setTurno(String turno) {
        Turno = turno;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public String getRol() {
        return Rol;
    }




}
