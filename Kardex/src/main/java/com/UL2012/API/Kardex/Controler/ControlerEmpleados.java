package com.UL2012.API.Kardex.Controler;

import com.UL2012.API.Kardex.Models.Entity.Empleados;
import com.UL2012.API.Kardex.Service.INT_Empleados;
import com.UL2012.API.Kardex.Utils.Formats;
import com.UL2012.API.Kardex.Utils.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("UL2012/API/Kardex/V1")
public class ControlerEmpleados {
    @Autowired
    private INT_Empleados Iemp;
    private final Formats formats= new Formats();
    private final Message Response = new Message() ;
    HttpStatus  status = null;
    //METODOS-> ENDPOINTS
    @PostMapping("Empleados/Save")
    @Operation(summary = "Actualizar Empleado", description = "Permite actualizar a un empleado existente o crear uno nuevo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empeado  Registrado Exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "No se encontro el codigo de Empleado o parametros invalidos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    })
    public ResponseEntity<?> SaveOrUpdate(@RequestBody Empleados Emp) {
        //mapa de errores
            try {
                Message result = formats.ValidateObjectEmployes(Emp);
                if(result.isFlag()){
                    System.out.println("flag result es :"+result.isFlag());
                    Iemp.SaveOrUpdateEmpleado(Emp);
                    return new ResponseEntity<>(Response.Get_Success("Nuevo Empleado Registrado",Emp.getCodigoPersonal()), HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>(Response.Get_Error(result.getMessage(),"Verifique los datos ingresados"),HttpStatus.BAD_REQUEST);
                }

            } catch (NullPointerException e) {
                System.out.println("Error de validación , Uno de los datos recibidos es nulo");
                System.out.println("Los datos enviados no ccumplen con el esquema requerido");
                return new ResponseEntity<>(Response.Get_Error("Excepcion no Controlada",
                        "Hubo un problema al procesar la solicitud,Exactamente : Los datos enviados no ccumplen con el esquema requerido"), HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(Response.Get_Error("Excepcion no Controlada",
                        "Hubo un problema al procesar la solicitud,Exactamente : Hubo un error en el servicio"), HttpStatus.INTERNAL_SERVER_ERROR);
            }


    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/Empleados")
    @Operation(summary = "Lista Empleados", description = "Permite Listar a todos los empleados existentes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista todos los empleados existentes.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Empleados.class))),
    })
    public Iterable<Empleados> Empleados(){
        return Iemp.EMPLEADOS();
    }
    @GetMapping("Empleados/{Emp}")
    @Operation(summary = "Consulta Empleado", description = "Buscar a un Empleado por su codigo personal.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Se encontro Empleado Exitosa.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Empleados.class))),
            @ApiResponse(responseCode = "204", description = "No se encontro recurso solicitado."),
            @ApiResponse(responseCode = "404", description = "No se encontro el codigo de Empleado o tipo de asistencia.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    })
    public ResponseEntity<?> FindByID(@Parameter(description = "ID del Empleado con formato :^([A-Z]{3})[0-9]{3}$ ") @PathVariable("Emp") String  Emp){
        Map<String,Object> Response = new HashMap<>();
        try {
            if(!Formats.ValidateFormat("^([A-Z]{3})[0-9]{3}$",Emp)){
                Response.put("Cod","ERR01");
                Response.put("Message","Formato Incorrecto o no se encontro el recurso");
                Response.put("Details","El formato del codigo personal no es correcto");
                return new ResponseEntity<>(Response,HttpStatus.NOT_FOUND);

            }
           Empleados EMP= Iemp.FindByIdEmpleado(Emp);
            if (EMP == null) {
                Response.put("Cod", "ERR01");
                Response.put("Message", "No se ah encontrado Empleado");
                Response.put("Details", "No se ah encontrado el empleado con el codigo personal: " + Emp);
                //return new ResponseEntity<>(Response, HttpStatus.NOT_FOUND);
                return new ResponseEntity<>(Response, HttpStatus.NO_CONTENT);
            }
            return  new ResponseEntity<>(EMP,HttpStatus.OK);

        }catch (DataAccessException ex){
            Response.put("Cod","ERR01");
            Response.put("Message","No se ah encontrado Empleado");
            Response.put("Details",ex.getMessage());
            return  new ResponseEntity<>(Response,HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("Empleados/Delete/{CodEmp}")
    @Operation(summary = "Eliminar Empleado", description = "Permite Eliminar a un empleado mediante su codigo de empleado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Se elimino al Empleado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "404", description = "No se encontro el codigo de Empleado o tipo de asistencia."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    })
    public ResponseEntity<?> DeleteEmp(@Parameter(description = "ID del Empleado con formato :^([A-Z]{3})[0-9]{3}$ ")@PathVariable("CodEmp") String CodEmp){
        //mapa de errores
        Map<String,Object> Response = new HashMap<>();
        //Cuando se trata de manejar respuesta automaticas
        //debemos de retornar un objeto de tipo response entity , para  que
        // maneje segun la logica el tipo de respuesta ,como cabeceras ,parametros,token ,etc
        //todo dependiendo de su httpstatus
        //personalisar errores segun buenas practicas...
        if(!Formats.ValidateFormat("^([A-Z]{3})[0-9]{3}$",CodEmp)){
            Response.put("Cod","ERR01");
            Response.put("Message","Formato Incorrecto");
            Response.put("Details","El formato del codigo personal no es correcto");
            return new ResponseEntity<>(Response,HttpStatus.NOT_FOUND);
        }else {
        try{
            Empleados Emp = Iemp.FindByIdEmpleado(CodEmp);
            if (Emp == null) {
                Response.put("Cod", "ERR01");
                Response.put("Message", "No se ah encontrado Empleado");
                Response.put("Details", "No se ah encontrado el empleado con el codigo personal: " + CodEmp);
                return new ResponseEntity<>(Response, HttpStatus.CONFLICT);
            }else{
                Iemp.DeleteEmp(Emp);
                Response.put("Cod","KAR001");
                Response.put("Message","Empleado Eliminado");
                return  new ResponseEntity<>(Response,HttpStatus.OK);
            }

            }catch(DataAccessException exec){
            Response.put("Cod","ERR03");
            Response.put("Message","No se encontro el Empleado");
            Response.put("Detalle",exec.getMessage());
            return new ResponseEntity<>(Response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    }

}
