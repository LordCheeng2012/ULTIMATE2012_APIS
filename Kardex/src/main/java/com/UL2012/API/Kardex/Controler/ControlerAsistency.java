package com.UL2012.API.Kardex.Controler;
import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Models.Entity.Message;
import com.UL2012.API.Kardex.Service.INT_Asitency;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("UL2012/API/Kardex/V1/Asistency")
public class ControlerAsistency {
    @Autowired
    private INT_Asitency IAsis;
    private final Message msg= new Message();
    @PostMapping("/Query")
    public ResponseEntity<?> BusquedaByQueryParams(@RequestBody AsistencyQueryDto AsistenciaQuery) {
        System.out.println("Objeto es : "+AsistenciaQuery.toString());
        Message msg=new Message();
        //implementar la logica antes de mandar a query al dao
        try {
            List<Asitencia> asis=null;
            int index=0;
            //verificar si uno de los parametros son nulos
            for (String param : AsistenciaQuery.getParams()) {
                //se crea el get de obtener los parametros en el dto
                // y se hace la validacion
                if(param !=null){
                    //si no son nulos el contador se incrementa indicando que la posicion actual
                    //esta sin nul
                    index++;
                }else{
                    //de lo contrario
                    param="";
                    //asigna un valor vacio en el indice actual
                    AsistenciaQuery.getParams().set(index,param);
                }
            }
            //devuelve la lista de asistencias
             asis=IAsis.BusquedaByQueryParams(AsistenciaQuery);
            if(asis.isEmpty()){
                msg.setCod_Msg("ERR02");
                msg.setType("Warning");
                msg.setTitle("No se encontraron registros");
                msg.setMessage("No se encontraron registros con los parametros ingresados");
                return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);
            }
            return  new ResponseEntity<>(asis, HttpStatus.OK);

        } catch (Exception e) {
            msg.setCod_Msg("ERR01");
            msg.setType("Error");
            msg.setTitle("Error en la consulta");
            msg.setMessage(e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }



    }
    @GetMapping("/Register/{asis}")
    public ResponseEntity<?> RegisterAsistency( @PathVariable("asis") String asis){
        HttpStatus codeStatus = HttpStatus.CONFLICT;
        ResponseEntity<?> response;
        boolean flag=true;
            if(asis.isEmpty()){
                flag=false;
                msg.setCod_Msg("ERR01");
                msg.setTitle("Vacios");
                msg.setType("Warning");
                msg.setMessage("Error dato vacio");
                msg.setData("404");
                codeStatus=HttpStatus.BAD_REQUEST;
            } else if (asis.length()>=8) {
                System.out.println(asis);
                flag=false;
                msg.setCod_Msg("ERR02");
                msg.setTitle("Caracteres Superados");
                msg.setType("Warning");
                msg.setMessage("El codigo de Empleado debe ser de maximo 7 caracteres");
                msg.setData("404");
            }
        if(flag){
            //si es verdadero ejecutar la insercion
            boolean fs= IAsis.RegisterAsistency(asis);
            if(fs){
                msg.setCod_Msg("SUC01");
                msg.setTitle("Registrado");
                msg.setType("Exito");
                msg.setMessage("Se registro Empleado");
                msg.setData("201");
                codeStatus=HttpStatus.CREATED;
            }else{
                msg.setCod_Msg("ERR01");
                msg.setTitle("No Encontrado");
                msg.setType("ERROR");
                msg.setMessage("No se encontro ningun empleado con dicho codigo :"+asis);
                msg.setData("400");
                codeStatus= HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        response= new ResponseEntity<>(msg,codeStatus);
        return response;
    }
    @GetMapping("/Register/QRCode")
    public  ResponseEntity<?> RegisterByQRCode(String code) throws NotFoundException, IOException, WriterException {
        if(code.equals("true")){
            IAsis.RegisterAsistencyByQRCode();
        }else{
            return new ResponseEntity<>("error",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }
    @GetMapping("/UpdateAsistency")
    public ResponseEntity<?> UpdateAsistency( @RequestHeader("code") String code,
                                              @RequestHeader("Pet") String Pet) throws NotFoundException,
            IOException,
            WriterException {
        System.out.println("code :"+code);
        System.out.println("Pet :"+Pet);
        System.out.println("Analizando parametros..");
        HttpStatus http = null;
        String mensaje="";
        if(!code.isEmpty()|| !Pet.isEmpty()){
            if(!(code.length()>=10)|| !(Pet.length()>=10)){
                switch (Pet){
                    case "Break":
                        mensaje = "Time of the Break";
                        System.out.println(mensaje);
                        IAsis.UpdateAsistency(code,Pet);
                        http=HttpStatus.OK;
                        break;
                    case "RetBreak":
                        mensaje = "Return Break";
                        System.out.println("Return Break");
                        IAsis.UpdateAsistency(code,Pet);
                        http=HttpStatus.OK;
                        break;
                    case "Exit":
                        mensaje = "Time of the Exit";
                        System.out.println(mensaje);
                        IAsis.UpdateAsistency(code,Pet);
                        http=HttpStatus.OK;
                        break;
                    default:
                        mensaje = "Fallo en el registro , no se detecto la petición";
                        http=HttpStatus.BAD_REQUEST;
                        break;
                }
            }else{
                mensaje="Limite de caracteres exedido para codigo o peticion";
            }
            assert http != null;
            if(!http.is4xxClientError()){
                msg.setCod_Msg("SUC02");
                msg.setTitle("Actualizar Asistencia");
                msg.setType("Succes");
                msg.setMessage(mensaje);
                msg.setData("200");
            }else{
                msg.setCod_Msg("ERR02");
                msg.setTitle("Actualizar Asistencia");
                msg.setType("Warning");
                msg.setMessage(mensaje);
                msg.setData("400");
            }

        }else{
            mensaje="Algunos Headers Requeridos estan vacios";
            msg.setCod_Msg("ERR02");
            msg.setTitle("Actualizar Asistencia");
            msg.setType("Warning");
            msg.setMessage(mensaje);
            msg.setData("400");
            http=HttpStatus.BAD_REQUEST;
        }
        return  new ResponseEntity<>(msg,http);
    }

}
