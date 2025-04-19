package com.UL2012.API.Kardex.Service;
import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Utils.Message;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface INT_Asitency {
    List<Asitencia> BusquedaByQueryParams(AsistencyQueryDto dto) throws Exception;
    Message RegisterAsistency(String dto);
    void RegisterAsistencyByQRCode() throws NotFoundException, IOException, WriterException;
    Optional<Message> UpdateAsistency(String Code , String Peticion) throws NotFoundException, IOException, WriterException;
    public Message Register_Report_Asistency(LocalDate date, String Code)throws SQLException;
}
