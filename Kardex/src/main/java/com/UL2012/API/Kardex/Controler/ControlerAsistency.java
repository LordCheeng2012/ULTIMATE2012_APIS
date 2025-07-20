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
        description = "Permite a un administrador buscar asistencias mediante parámetros de filtrado. " +
                "Los filtros pueden incluir código de empleado, fecha, carrera, horas de ingreso/salida, entre otros. " +
                "Si algún filtro no se proporciona, se retornarán todos los registros existentes para ese campo. " +
                "Ideal para reportes y auditorías. " +
                "Reglas de validación: al menos un parámetro debe estar presente. Si 'fecha' está vacía, se ignora en el filtro."
    )
    @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Lista de asistencias encontrada según los filtros proporcionados. " +
                        "Se retorna una lista vacía si no hay coincidencias.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Asitencia.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No se encontraron registros con los parámetros ingresados. " +
                        "Esto ocurre cuando la búsqueda no arroja resultados.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Solicitud mal formada o error en los parámetros de entrada. " +
                        "Por ejemplo, si los parámetros requeridos están vacíos o nulos.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor. " +
                        "Ocurre cuando hay una excepción inesperada durante el procesamiento.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            )
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
    @Operation(
        summary = "Registro de Asistencia",
        description = "Permite registrar la asistencia de un empleado mediante su código de empleado. " +
                "Reglas de validación: el código de empleado no debe estar vacío y debe tener un máximo de 7 caracteres. " +
                "Si el código es inválido, se retorna un error."
    )
    @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Asistencia registrada exitosamente. " +
                        "Se retorna un mensaje de confirmación.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "El código de empleado está vacío o no cumple con las reglas de validación (máximo 7 caracteres).",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No se encontró el código de empleado en el sistema.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Conflicto al registrar la asistencia. Puede deberse a un registro duplicado.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            )
    })
    public ResponseEntity<?> RegisterAsistency(@PathVariable("asis") String asis){
        HttpStatus codeStatus = HttpStatus.CONFLICT;
        ResponseEntity<?> response;
        if(Formats.ValidateFormat("^[A-Z]{3}[0-9]{3}$",asis)){
            //si es verdadero ejecutar la insercion
            Message fs= IAsis.RegisterAsistency(asis);
            msg=fs;
            codeStatus=msg.getCodeStatus();
            response= new ResponseEntity<>(msg,codeStatus);
            return response;
        }
        codeStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(msg.Get_Warning("Formato invalido para el codigo de registro",
                "Parametro recibido es invalido",false),codeStatus);

    }
    @GetMapping("/Register/QRCode")
    @Operation(
        summary = "Registro de Asistencia por QR",
        description = "Permite registrar la asistencia de un empleado mediante un código QR. " +
                "El parámetro 'code' debe ser igual a 'true' para ejecutar el registro. " +
                "Si el parámetro es distinto, se retorna un error."
    )
    @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Asistencia registrada exitosamente mediante QR.",
                content = @Content(mediaType = "application/json", schema = @Schema(example = "\"ok\""))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "El parámetro 'code' es inválido o no se proporcionó correctamente.",
                content = @Content(mediaType = "application/json", schema = @Schema(example = "\"error\""))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            )
    })
    public  ResponseEntity<?> RegisterByQRCode(String code) throws NotFoundException, IOException, WriterException {
        if(code.equals("true")){
            IAsis.RegisterAsistencyByQRCode();
        }else{
            return new ResponseEntity<>("error",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }
    @GetMapping("/UpdateAsistency")
    @Operation(
        summary = "Actualizar Asistencia",
        description = "Permite actualizar la asistencia de un empleado mediante su código de empleado y el tipo de asistencia. " +
                "Reglas de validación: " +
                "- El header 'code' debe tener formato ^[A-Z]{3}[0-9]{11}$ y no estar vacío. " +
                "- El header 'Peticion' debe ser uno de los siguientes valores: [Break, RetBreak, Exit]. " +
                "Si los headers no cumplen con las reglas, se retorna un error."
    )
    @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "Asistencia actualizada exitosamente. " +
                        "Se retorna un mensaje de confirmación.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "El código de empleado o el tipo de asistencia no cumplen con las reglas de validación, " +
                        "o los headers requeridos no están presentes.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))
            )
    })
    public ResponseEntity<?> UpdateAsistency(
            @Parameter(
                description = "Código de la asistencia. Formato requerido: ^[A-Z]{3}[0-9]{11}$. No debe estar vacío.",
                example = "ABC12345678901",
                required = true
            ) @RequestHeader("code") Optional<Object> code,
            @Parameter(
                description = "Tipo de marcado de asistencia. Valores permitidos: [Break] (salida de almuerzo), [RetBreak] (retorno de merienda), [Exit] (salida).",
                example = "Break",
                required = true
            ) @RequestHeader("Peticion") Optional<Object> Pet) {
        HttpStatus http = HttpStatus.INTERNAL_SERVER_ERROR;
        Optional<Message> response = Optional.empty();
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
                        response= IAsis.UpdateAsistency(CODE, PETICION);
                        http = response.get().isFlag()? HttpStatus.OK:HttpStatus.BAD_REQUEST;
                        break;
                    case "RetBreak":
                        mensaje = "Return Break";
                        System.out.println("Return Break");
                        response= IAsis.UpdateAsistency(CODE, PETICION);
                        http = response.get().isFlag()? HttpStatus.OK:HttpStatus.BAD_REQUEST;
                        break;
                    case "Exit":
                        mensaje = "Time of the Exit";
                        System.out.println(mensaje);
                        response= IAsis.UpdateAsistency(CODE, PETICION);
                        http = response.get().isFlag()? HttpStatus.OK:HttpStatus.BAD_REQUEST;
                        break;
                    default:
                        mensaje = "Fallo en el registro , no se detecto la petición";
                        http = HttpStatus.BAD_REQUEST;
                        response = Optional.of(msg.Get_Warning(mensaje,"no se detecto la peticion requerida",false)) ;
                        break;
                }
            } else {
                mensaje = "Limite de caracteres exedido para codigo o peticion";
                http = HttpStatus.BAD_REQUEST;
                response = Optional.of(msg.Get_Error(mensaje,"Parametros [CODIGO Y PETICION] deben cumplir con la validacion de - maximo 10 minimo 10"));
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
        return new ResponseEntity<>(response.get(),http);
    }

}
