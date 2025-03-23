package com.UL2012.API.Kardex.Models.Dao;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Models.Entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface AsistencyDao extends CrudRepository<Asitencia,String> {
    //reto listar todos los registros segun filtros
    @Query(value ="call ListenAsistenncy(:p1,:p2,:p3,:p4)", nativeQuery = true)
    //si code_emp esta vacio o null , simplemente este parametro no se toma en cuenta
    List<Asitencia> BusquedaByQueryParams(String p1, String p2, String p3, String p4);


}
