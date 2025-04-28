package com.UL2012.API.Kardex.Controler;

import com.UL2012.API.Kardex.Models.Entity.Empleados;
import com.UL2012.API.Kardex.Service.INT_Empleados;
import com.UL2012.API.Kardex.Utils.Formats;
import com.UL2012.API.Kardex.Utils.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("UL2012/API/Kardex/V1")
public class ControlerEmpleados {
    @Autowired
    private INT_Empleados Iemp;
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
    public ResponseEntity<?> SaveOrUpdate(@RequestBody Empleados Emp){
        Map<String,Object> Response = new HashMap<>();
        HttpStatus status = null;
        if(!Formats.ValidateFormat("^[A-Z]{3}[0-9]{3}$",Emp.getCodigoPersonal())){
            Response.put("Cod","ERR01");
            Response.put("Estado","False");
            Response.put("User",Emp.getCodigoPersonal());
            Response.put("Password",Emp.getNombres());
            Response.put("Details","Formato de codigo personal no es correcto");
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(Response,status);

        }
        if (Emp.getCodigoPersonal() == null || Emp.getNombres() == null) {
            Response.put("Cod", "ERR01");
            Response.put("Estado", "False");
            Response.put("User", Emp.getCodigoPersonal());
            Response.put("Password", Emp.getNombres());
            Response.put("Details", "Datos del nuevo Empleado no pueden ser nulos O vacios");
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(Response, status);
        }
        try{
            Response.put("Cod","KAR01");
            Response.put("Message","Se ah creado el Empleado");
            Response.put("Details",Emp.getCodigoPersonal());
             Iemp.SaveOrUpdateEmpleado(Emp);
             return  new ResponseEntity<>(Response,HttpStatus.CREATED);
        } catch (Exception e  ) {
            Response.put("Cod","ERR02");
            Response.put("Message","Ocurrio un error en la Peticion");
            Response.put("Details",e.getMessage());
            return  new ResponseEntity<>(Response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/Empleados")
    @Operation(summary = "Actualizar Asistencia", description = "Permite actualizar la asistencia de un empleado mediante su codigo de empleado y el tipo de asistencia " +
            "como Break, retorno de break o salida")
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
            @ApiResponse(responseCode = "404", description = "No se encontro el codigo de Empleado o tipo de asistencia."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    })
    public ResponseEntity<?> FindByID(@PathVariable("Emp") String  Emp){
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
    public ResponseEntity<?> DeleteEmp(@PathVariable("CodEmp") String CodEmp){
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
        }
        try{
            Empleados Emp = Iemp.FindByIdEmpleado(CodEmp);
            if (Emp == null) {
                Response.put("Cod", "ERR01");
                Response.put("Message", "No se ah encontrado Empleado");
                Response.put("Details", "No se ah encontrado el empleado con el codigo personal: " + CodEmp);
                return new ResponseEntity<>(Response, HttpStatus.CONFLICT);
            }
            Iemp.DeleteEmp(Emp);
            Response.put("Cod","KAR001");
            Response.put("Message","Empleado Eliminado");
            return  new ResponseEntity<>(Response,HttpStatus.OK);
        }catch(DataAccessException exec){

            Response.put("Cod","ERR03");
            Response.put("Message","No se encontro el Empleado");
            Response.put("Detalle",exec.getMessage());
            return new ResponseEntity<>(Response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
