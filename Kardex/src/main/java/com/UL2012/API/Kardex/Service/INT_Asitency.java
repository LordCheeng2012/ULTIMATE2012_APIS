package com.UL2012.API.Kardex.Service;


import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Models.Entity.Message;
import java.util.List;

public interface INT_Asitency {

    List<Asitencia> BusquedaByQueryParams(AsistencyQueryDto dto) throws Exception;
    Message RegisterAsistency(AsistencyQueryDto dto);
}
