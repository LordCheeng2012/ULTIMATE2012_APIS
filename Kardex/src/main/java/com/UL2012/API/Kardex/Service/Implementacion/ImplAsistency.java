package com.UL2012.API.Kardex.Service.Implementacion;
import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Dao.AsistencyDao;
import com.UL2012.API.Kardex.Models.Dao.EmpleadosDao;
import com.UL2012.API.Kardex.Models.Entity.Archivos;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Models.Entity.Empleados;
import com.UL2012.API.Kardex.Service.INT_Asitency;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImplAsistency implements INT_Asitency {

    @Autowired
    private AsistencyDao AsisDao;
    @Autowired
    private EmpleadosDao emp;
    @Override
    public List<Asitencia> BusquedaByQueryParams(AsistencyQueryDto dto) throws Exception {
        return AsisDao.BusquedaByQueryParams(dto.getCod_Per(),
                dto.getFecha(),
                dto.getArea(),
                dto.getTurno());
    }
    public boolean RegisterAsistency(String Code_emp){
        //hacer la consulta y completar sus datos faltantes
        boolean fl;
        Optional<Empleados> Empleado=emp.findById(Code_emp);
        if(Empleado.isPresent()){
        //insertar el codigo de empleado
        Empleados empleado = Empleado.get();
        //con el dto configurado para el empleado procedemos a insertar un registro
            Asitencia as = new Asitencia(Code_emp);
            as.setArea(empleado.getArea());
            as.setTurno(empleado.getTurno());
            as.setCarrera(empleado.getCarrera());
            as.setRol(empleado.getRol());
        //LLenar los datos de fecha de registro

            LocalTime time= LocalTime.now();
            Date date = new Date();
            as.setFecha(date);
            as.setHora_Ingreso(time);
            //insertar el registro
            try {
                AsisDao.save(as);
                fl=true;
            } catch (RuntimeException e) {
                fl=false;
                System.out.println(e.getMessage());
            }

        }else {
            fl= false;
        }
        return fl;
    }
    public void RegisterAsistencyByQRCode() throws NotFoundException, IOException, WriterException {
        String code = Archivos.readQRCode("MSOLISAL.png");
        this.RegisterAsistency(code);
    }
    @Override
    public void UpdateAsistency(String Code,String Peticicion) throws NotFoundException, IOException, WriterException {
        Optional<Asitencia> rowAsis= AsisDao.findById(Code);
        if(rowAsis.isPresent()){
            Asitencia as = rowAsis.get();
            switch (Peticicion) {
                case "Break" :
                    as.setHora_Break(LocalTime.now());
                    break;
                case "RetBreak":
                    as.setRetorno_Break(LocalTime.now());
                    break;
                case "Exit":
                    as.setHora_Salida(LocalTime.now());
                    break;
                default:
                    System.out.println("no se reconocio parametro,intentelo de nuevo cambiando el parametro");
                    break;
            }
            AsisDao.save(as);
            System.out.println("se ah actualizado el registro");
        }else{
            System.out.println("no se encontro el registro en la asistencia");
        }
    }
}
