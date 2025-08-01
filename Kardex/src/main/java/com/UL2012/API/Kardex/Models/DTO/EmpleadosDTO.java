package com.UL2012.API.Kardex.Models.DTO;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "empleados_data")
public class EmpleadosDTO {

    @Id
    @Column(name = "Codigo_Personal")
    private String codigoPersonal;

    @Column(name = "Nombres")
    private String nombres;

    @Column(name = "Apellidos")
    private String apellidos;

    @Column(name = "Edad")
    private Integer edad;

    @Column(name = "Sexo")
    private String sexo;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "Carrera")
    private String carrera;

    @Column(name = "Foto", nullable = true)
    private String foto;

    // Constructor with codigoPersonal parameter
    public EmpleadosDTO(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }

    // Default constructor
    public EmpleadosDTO() {
    }

    // Getters and Setters
    public String getCodigoPersonal() {
        return codigoPersonal;
    }

    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
