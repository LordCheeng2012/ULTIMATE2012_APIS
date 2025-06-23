package com.UL2012.API.Kardex.Service.Implementacion;
import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Dao.AsistencyDao;
import com.UL2012.API.Kardex.Models.Dao.EmpleadosDao;
import com.UL2012.API.Kardex.Models.Entity.Archivos;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Models.Entity.Empleados;
import com.UL2012.API.Kardex.Utils.Message;
import com.UL2012.API.Kardex.Service.INT_Asitency;
import com.UL2012.API.Kardex.Utils.Formats;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
    Message msg = new Message();
    @Override
    public List<Asitencia> BusquedaByQueryParams(AsistencyQueryDto dto) throws Exception {
        return AsisDao.BusquedaByQueryParams(dto.getCod_Per(),
                dto.getFecha(),
                dto.getArea(),
                dto.getTurno());
    }
    public Message RegisterAsistency(String Code_emp){
        //hacer la consulta y completar sus datos faltantes
        Optional<Empleados> Empleado=emp.findById(Code_emp);
        if(Empleado.isPresent()){
        //insertar el codigo de empleado
        Empleados empleado = Empleado.get();
        // con la asistencia registrada procedemos a insertar reporte diario
                String id =Formats.CreateID_Asistency(Code_emp);
                 //verificar duplicados en assitencia
                Asitencia as = new Asitencia(id);
                as.setCod_Per(empleado.getCodigoPersonal());
                as.setArea(empleado.getArea());
                as.setTurno(empleado.getTurno());
                as.setCarrera(empleado.getCarrera());
                as.setRol(empleado.getRol());
                //LLenar los datos de fecha de registro
                LocalTime time = LocalTime.now();
                Date date = new Date();
                as.setFecha(date);
                as.setHora_Ingreso(time);
                //insertar el registro
                try {
                    //VERIFICIAR EXISTENCIA DE ASISTENCIA EN LOS REGISTROS
                    Optional <Asitencia> a = AsisDao.findById(id);
                    if(a.isPresent()){
                        System.out.println("Existe Asistencia");
                        msg=msg.Get_Warning("Asistencia Creada",
                                "EL Empleado ya marco su asistencia",false);
                        msg.setCodeStatus(HttpStatus.CONFLICT);
                    }else {
                        //Insertar el registro para el reporte
                        AsisDao.save(as);
                        Message resRepor = Register_Report_Asistency(LocalDate.now(), Code_emp);
                        resRepor.Show_Message();
                        msg=resRepor;
                        msg.setCodeStatus(HttpStatus.CREATED);
                        msg.setMessage("Se ah registrado la asistencia para el empleado");
                    }
                } catch (RuntimeException e) {

                    System.out.println(e.getMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

        }else {
            msg=msg.Get_Warning("No existe Empleado","Verifique el codigo ingresado",false);
            msg.setCodeStatus(HttpStatus.NOT_FOUND);
        }
        return msg ;
    }
    public void RegisterAsistencyByQRCode() throws NotFoundException, IOException, WriterException {
        String code = Archivos.readQRCode("MSOLISAL.png");
        this.RegisterAsistency(code);
    }
    @Override
    public Optional<Message> UpdateAsistency(String Code,String Peticicion) throws NotFoundException, IOException, WriterException {
        //verficar si existe el registro de la asistencia
        //si existe el registro de asistencia
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
            msg = msg.Get_Success("Se ah actualizado el registro",
                    "El registro se ah actualizado correctamente");
        }else{
            System.out.println("no se encontro el registro en la asistencia");
            return Optional.of(msg.Get_Warning("No se encontro el registro",
                    "Verifique el codigo ingresado",false));
        }
        return Optional.of(msg);
    }
    public Message Register_Report_Asistency(LocalDate date,String Code)throws SQLException {

        try {
            Optional<Empleados> as = emp.findById(Code);
            if (as.isEmpty()) {
                msg = msg.Get_Warning("No se encontro el empleado"
                        , "Verifique el codigo ingresado",false);
            }else {
                List<Object[]> RESP = AsisDao.Asistency_Report(date, Code);
                List<Message> ParseResp = Message.ResponseMessage(RESP);
                for (Message mes : ParseResp) {
                    msg = mes;
                }
            }
            }catch(Exception exception){
                System.out.println("[Warning]: !NO SE REGISTRO REPORTE DIARIO DE REGISTRO¡ ");
                System.out.println("Causa del error :" + exception.getCause());
                System.out.println("Razón :" + exception.getMessage());
                throw new SQLException(exception.getMessage(), exception.getCause());
            }

        return msg;

    }
}
