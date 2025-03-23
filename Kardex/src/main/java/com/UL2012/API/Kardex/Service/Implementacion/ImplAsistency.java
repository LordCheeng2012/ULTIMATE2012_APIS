package com.UL2012.API.Kardex.Service.Implementacion;
import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Dao.AsistencyDao;
import com.UL2012.API.Kardex.Models.Dao.EmpleadosDao;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Models.Entity.Empleados;
import com.UL2012.API.Kardex.Models.Entity.Message;
import com.UL2012.API.Kardex.Service.INT_Asitency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ImplAsistency implements INT_Asitency {

    @Autowired
    private AsistencyDao AsisDao;
    @Autowired
    private EmpleadosDao emp;
    @Autowired

    @Override
    public List<Asitencia> BusquedaByQueryParams(AsistencyQueryDto dto) throws Exception {
        return AsisDao.BusquedaByQueryParams(dto.getCod_Per(),
                dto.getFecha(),
                dto.getArea(),
                dto.getTurno());
    }
    public Message RegisterAsistency(AsistencyQueryDto dto){
        //hacer la consulta y completar sus datos faltantes
        Message msg = new Message();
        Optional<Empleados> Empleado=emp.findById(dto.getCod_Per());
        if(Empleado.isPresent()){
        //insertar el codigo de empleado
        Empleados empleado = Empleado.get();
        //rellenemos los datos del empleado a partir de su codigo
            dto.setArea(empleado.getArea());
            dto.setRol(empleado.getRol());
            dto.setTurno(empleado.getTurno());
            dto.setCarrera(empleado.getCarrera());
        //con el dto configurado para el empleado procedemos a insertar un registro


            msg.setCod_Msg("SUC01");
            msg.setTitle("Registrado");
            msg.setType("Exito");
            msg.setMessage("Se registro Empleado");
            msg.setData("200");
        }else{
            msg.setCod_Msg("ERR01");
            msg.setTitle("No Encontrado");
            msg.setType("ERROR");
            msg.setMessage("No se encontro el Empleado,no se registro");
            msg.setData("404");
        }
        return msg;
    }
}
