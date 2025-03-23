package com.UL2012.API.Kardex.Controler;

import com.UL2012.API.Kardex.Models.Entity.Empleados;
import com.UL2012.API.Kardex.Service.INT_Empleados;
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
    public ResponseEntity<?> SaveOrUpdate(@RequestBody Empleados Emp){
        Map<String,Object> Response = new HashMap<>();
        HttpStatus status = null;
        if (Emp.getCodigoPersonal() == null || Emp.getNombres() == null) {
            Response.put("Cod", "ERR01");
            Response.put("Estado", "False");
            Response.put("User", Emp.getCodigoPersonal() );
            Response.put("Password", Emp.getNombres());
            Response.put("Details", "Usuario o Contraseña no pueden ser nulos O vacios");
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
    public Iterable<Empleados> Empleados(){
        return Iemp.EMPLEADOS();
    }

    @GetMapping("Empleados/{Emp}")
    public ResponseEntity<?> FindByID(@PathVariable("Emp") String  Emp){
        Map<String,Object> Response = new HashMap<>();
        try {
           Empleados EMP= Iemp.FindByIdEmpleado(Emp);
            return  new ResponseEntity<>(EMP,HttpStatus.OK);
        }catch (DataAccessException ex){
            Response.put("Cod","ERR01");
            Response.put("Message","No se ah encontrado Empleado");
            Response.put("Details",ex.getMessage());
            return  new ResponseEntity<>(Response,HttpStatus.NOT_FOUND);
        }

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/TestParam/{Param}")
    public void RevEmp(@PathVariable("Param") String Param){
        System.out.println("En Controlador");
        System.out.println("Valor del parametro : "+Param);
        Iemp.RevParam(Param);

    }

    @DeleteMapping("Empleados/Delete/{CodEmp}")
    public ResponseEntity<?> DeleteEmp(@PathVariable("CodEmp") String CodEmp){
        //mapa de errores
        Map<String,Object> Response = new HashMap<>();
        //Cuando se trata de manejar respuesta automaticas
        //debemos de retornar un objeto de tipo response entity , para  que
        // maneje segun la logica el tipo de respuesta ,como cabeceras ,parametros,token ,etc
        //todo dependiendo de su httpstatus
        //personalisar errores segun buenas practicas...
        try{
            Empleados Emp = Iemp.FindByIdEmpleado(CodEmp);
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
