package com.UL2012.API.Kardex.Models.Dao;

import com.UL2012.API.Kardex.Models.Entity.Empleados;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadosDao extends CrudRepository<Empleados, String> {
    // Método personalizado para validar el parámetro
    @Query("SELECT e FROM Empleados e WHERE e.codigoPersonal = :codigoPersonal")
    Empleados validateParameter(@Param("codigoPersonal") String codigoPersonal);




}
