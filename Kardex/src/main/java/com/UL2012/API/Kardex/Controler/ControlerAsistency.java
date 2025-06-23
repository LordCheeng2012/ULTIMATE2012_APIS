package com.UL2012.API.Kardex.Controler;

import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Utils.Formats;
import com.UL2012.API.Kardex.Utils.Message;
import com.UL2012.API.Kardex.Service.INT_Asitency;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Asistencias", description = "Controlador para la gestión y consulta de asistencias de empleados. Permite registrar, actualizar y consultar asistencias mediante diferentes métodos, incluyendo código de empleado y QR. Proporciona respuestas detalladas sobre el estado de las operaciones y posibles errores.")
@RestController
@RequestMapping("UL2012/API/Kardex/V1/Asistency")
public class ControlerAsistency {
    @Autowired
    private INT_Asitency IAsis;
    private Message msg = new Message();

    @PostMapping("/Query")
    @Operation(
        summary = "Búsqueda de Asistencia",
        description = "Permite a un administrador buscar asistencias mediante parámetros de filtrado. Algunos filtros como fecha, carrera u horas de ingreso/salida pueden no estar disponibles y retornarán todos los registros existentes. Útil para reportes y auditorías.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto con los parámetros de búsqueda. Ejemplo: {\"codigoEmpleado\":\"ABC123\", \"fecha\":\"2024-06-22\"}",
            required = true,
            content = @Content(schema = @Schema(implementation = AsistencyQueryDto.class))
        )
    )
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
    public ResponseEntity<?> UpdateAsistency(@Parameter(description = "Codigo de la asistencia con formato : ^[A-Z]{3}[0-9]{11}$",allowEmptyValue = true) @RequestHeader("code") Optional<Object> code,
                                              @Parameter(description = "Maixmo : 8 , Minimo :8 , Tipo de marcado de asitencia [Break]:\n" +
                                                      "Indica el marcado de salida de almuerzo\n"+"[RetBreak]:"+"Indica el retorno de merienda \n"+
                                                      "[Exit] :Indica el marcado de salida") @RequestHeader("Peticion") Optional<Object> Pet) {
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        String mensaje="";
        try {
        if(Formats.validateHeaders(Map.of("CODE",code,"Peticion",Pet))) {
            System.out.println("headers presentes");
            String CODE = (String) code.get();
            String PETICION = (String) Pet.get();
            if (!(CODE.length() >= 10) || !(PETICION.length() >= 10)) {
                switch (PETICION) {
                    case "Break":
                        mensaje = "Time of the Break";
                        System.out.println(mensaje);
                        IAsis.UpdateAsistency(CODE, PETICION);
                        http = HttpStatus.OK;
                        break;
                    case "RetBreak":
                        mensaje = "Return Break";
                        System.out.println("Return Break");
                        IAsis.UpdateAsistency(CODE, PETICION);
                        http = HttpStatus.OK;
                        break;
                    case "Exit":
                        mensaje = "Time of the Exit";
                        System.out.println(mensaje);
                        IAsis.UpdateAsistency(CODE, PETICION);
                        http = HttpStatus.OK;
                        break;
                    default:
                        mensaje = "Fallo en el registro , no se detecto la petición";
                        http = HttpStatus.BAD_REQUEST;
                        break;
                }
            } else {
                mensaje = "Limite de caracteres exedido para codigo o peticion";
                http = HttpStatus.BAD_REQUEST;
            }
            msg.setCod_Msg("WAR02");
            msg.setTitle("Actualizar Asistencia");
            msg.setType("Warning");
            msg.setMessage(mensaje);
            msg.setData("Ocurrio un error");

        }else {
            System.out.println("Headers no presentes");
        }
        } catch (NullPointerException e) {
            return new ResponseEntity<>(msg.Get_Warning("Headers Requeridos",e.getMessage(),false),HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(msg.Get_Error("Error Inesperado","Ocurrio un error no controlado"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return msg.Get_Info("Actualizar Asistencia",mensaje,"El registro se ah actualizado correctamente",http);
    }



}
