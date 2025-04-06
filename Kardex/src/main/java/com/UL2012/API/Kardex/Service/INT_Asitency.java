package com.UL2012.API.Kardex.Service;


import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;

public interface INT_Asitency {
    List<Asitencia> BusquedaByQueryParams(AsistencyQueryDto dto) throws Exception;
    boolean RegisterAsistency(String dto);
    void RegisterAsistencyByQRCode() throws NotFoundException, IOException, WriterException;
    void UpdateAsistency(String Code ,String Peticion) throws NotFoundException, IOException, WriterException;
}
