package com.UL2012.API.Kardex.Models.Entity;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "empleados_data")
public class Empleados {

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
    private String fechaNacimiento;
    @Column(name = "Carrera")
    private String carrera;
    @Column(name = "Foto", nullable = true)
    private String foto;
    @Column(name = "Turno", nullable = true)
    private String Turno;
    @Column(name = "Area",nullable = false)
    private String Area;
    @Column(name = "rol",nullable = true)
    private String rol;

    // Constructor with codigoPersonal parameter
    public Empleados(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }

    // Default constructor
    public Empleados() {
    }

    public String getTurno() {
        return Turno;
    }

    public String getRol() {
        return rol;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setTurno(String turno) {
        Turno = turno;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
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
    public List<Object> Get_Atributes(){
        return List.of(getCodigoPersonal(),getNombres(),getApellidos(),getEdad(),
                getFechaNacimiento(),getSexo(),getCarrera(),getRol(),getTurno(),getFoto());
    }
}
