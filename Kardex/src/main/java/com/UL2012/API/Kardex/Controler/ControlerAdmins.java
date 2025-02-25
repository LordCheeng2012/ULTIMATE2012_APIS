package com.UL2012.API.Kardex.Controler;

import com.UL2012.API.Kardex.Models.Entity.Admins;
import com.UL2012.API.Kardex.Service.INT_Admins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("UL2012/API/Kardex/V1/Admins")
public class ControlerAdmins {
    @Autowired(required = true)
    private INT_Admins Iadm;
    //METODOS-> ENDPOINTS
    @PostMapping("/Login")
    public ResponseEntity<?> Login(@RequestBody Admins admins){
        Map<String, Object> Response = new HashMap<>();
        HttpStatus status = null;
        if(admins.getUsername() == null || admins.getPassword() == null){
            Response.put("Cod", "ERR01");
            Response.put("Estado", "False");
            Response.put("User", admins.getUsername());
            Response.put("Password", admins.getPassword());
            Response.put("Details", "Usuario o Contraseña no pueden ser nulos O vacios");
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(Response, status);
        }
        try {
            System.out.println("En Controlador");

            List<String> Session = Iadm.Init_Session(admins.getUsername(), admins.getPassword());
            if(Session.get(0).equals("False")){
                Response.put("Cod", "ERR01");
                Response.put("Estado", Session.get(0));
                Response.put("Details", Session.get(1));
                status = HttpStatus.NOT_FOUND;

            }else {
                Response.put("Cod", "SUC01");
                Response.put("Estado", Session.get(0));
                Response.put("Details", Session.get(1));
                status = HttpStatus.OK;

            }

            return new ResponseEntity<>(Response, status);

        }catch (DataAccessException ex){
            Response.put("Cod","ERR01");
            Response.put("Message","Error en la llamada al Servicio");
            Response.put("Details",ex.getMessage());
            return  new ResponseEntity<>(Response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
