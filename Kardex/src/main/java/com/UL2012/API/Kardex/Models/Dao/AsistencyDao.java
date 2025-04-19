package com.UL2012.API.Kardex.Models.Dao;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
public interface AsistencyDao extends CrudRepository<Asitencia,String> {
    //reto listar todos los registros segun filtros
    @Query(value ="call ListenAsistenncy(:p1,:p2,:p3,:p4)", nativeQuery = true)
    //si code_emp esta vacio o null , simplemente este parametro no se toma en cuenta
    List<Asitencia> BusquedaByQueryParams(String p1, String p2, String p3, String p4);
    @Query(value = "CALL Reportes(:Fecha, :Code)", nativeQuery = true)
    List<Object[]> Asistency_Report(@Param("Fecha") LocalDate Fecha,
                                    @Param("Code") String Code);

}
