package com.UL2012.API.Kardex.Controler;
import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Utils.Message;
import com.UL2012.API.Kardex.Service.INT_Asitency;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("UL2012/API/Kardex/V1/Asistency")
public class ControlerAsistency {
    @Autowired
    private INT_Asitency IAsis;
    private  Message msg= new Message();
    @PostMapping("/Query")
    @Operation(summary = "Busqueda de Asistenia", description = "Permite a un administrador buscar a una Asistencia mediante parametros de filtrado, algunos filtros como fecha,Carrera u horas de ingreso o salida no estan disponibles" +
            " y simplemente retornaran todos los registros existentes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de Asistencia según el filtro proporcionado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Asitencia.class))),
            @ApiResponse(responseCode = "400", description = "No se encontro la Asistencia.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    })
    public ResponseEntity<?> BusquedaByQueryParams(@RequestBody AsistencyQueryDto AsistenciaQuery) {

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
            if(AsistenciaQuery.getFecha().isEmpty()){
                AsistenciaQuery.setFecha(null);
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
            msg.setTitle("Error en la peticion");
            //msg.setMessage(e.getMessage());
            msg.setMessage("Ocurrio un error en la petición , consulte con el administrador para mas detalles");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }



    }
    @GetMapping("/Register/{asis}")
    @Operation(summary = "Registro de Asistencia", description = "Permite registrar la asistencia de un empleado mediante su codigo de empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asistencia Registrada Exitosa.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "404", description = "No se encontro el codigo de Empleado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    })
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
            Message fs= IAsis.RegisterAsistency(asis);
            msg=fs;
            codeStatus=msg.getCodeStatus();
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
    @Operation(summary = "Actualizar Asistencia", description = "Permite actualizar la asistencia de un empleado mediante su codigo de empleado y el tipo de asistencia " +
            "como Break, retorno de break o salida")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asistencia Registrada Exitosa.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "No se encontro el codigo de Empleado o tipo de asistencia.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    })
    public ResponseEntity<?> UpdateAsistency( @RequestHeader("code") String code,
                                              @RequestHeader("Pet") String Pet) throws NotFoundException,
            IOException,
            WriterException {
        System.out.println("code :"+code);
        System.out.println("Pet :"+Pet);
        System.out.println("Analizando parametros..");
        HttpStatus http = null;
        Optional<Message> response = Optional.empty();
        String mensaje="";
        if(!code.isEmpty()|| !Pet.isEmpty()){
            if(!(code.length()>=10)|| !(Pet.length()>=10)){
                switch (Pet){
                    case "Break":
                        mensaje = "Time of the Break";
                        System.out.println(mensaje);
                        response=IAsis.UpdateAsistency(code,Pet);
                        http=HttpStatus.OK;
                        break;
                    case "RetBreak":
                        mensaje = "Return Break";
                        System.out.println("Return Break");
                        response=IAsis.UpdateAsistency(code,Pet);
                        http=HttpStatus.OK;
                        break;
                    case "Exit":
                        mensaje = "Time of the Exit";
                        System.out.println(mensaje);
                        response= IAsis.UpdateAsistency(code,Pet);
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
            if(response.isPresent()){
                msg=response.get();
            }else {
                msg.setCod_Msg("WAR02");
                msg.setTitle("Actualizar Asistencia");
                msg.setType("Warning");
                msg.setMessage(mensaje);
                msg.setData("Ocurrio un error");
            }
        }else{
            mensaje="Algunos Headers Requeridos estan vacios";
            msg.setCod_Msg("ERR02");
            msg.setTitle("Actualizar Asistencia");
            msg.setType("Warning");
            msg.setMessage(mensaje);
            msg.setData("400");
            //msg.setCodeStatus(http.BAD_REQUEST);
            http=HttpStatus.BAD_REQUEST;
        }
        assert http != null;
        return  new ResponseEntity<>(msg,http);
    }



}
