package com.UL2012.API.Kardex.Controler;
import com.UL2012.API.Kardex.Models.DTO.LoginRequest;
import com.UL2012.API.Kardex.Models.Entity.Message;
import com.UL2012.API.Kardex.Service.INT_Admins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("UL2012/API/Kardex/V1/Admins")
public class ControlerAdmins {
    @Autowired
    private INT_Admins Iadm;
    //METODOS-> ENDPOINTS
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest admin){
        System.out.println("objeto es : "+admin.toString());
        HttpStatus status=null;
        List<Message> msg = null;
        if(admin.getUsername().isEmpty() || admin.getPassword().isEmpty()){
            status = HttpStatus.BAD_REQUEST;
            msg = List.of(new Message("ERR01",
                    "Error",
                    "Error de validacion",
                    "Los campos no pueden estar vacios",
                    ""));
        }else{
        //System.out.println("entro al for");
        List<Object[]> messages = Iadm.Init_Session(admin.getUsername(), admin.getPassword());
        msg= Message.ResponseMessage(messages);
        //VERIFICA LA RESPUESTAA
            //System.out.println("entro al else");
            for (Message m : msg) {

                if (m.getCod_Msg().equals("SUC02")) {

                    status = HttpStatus.ACCEPTED;
                } else if(m.getCod_Msg().equals("ERR23")) {
                    status = HttpStatus.UNAUTHORIZED;
                }else if(m.getCod_Msg().equals("ERR02")){
                    status = HttpStatus.UNAUTHORIZED;
                } else if (m.getCod_Msg().equals("ERR22")) {
                    status = HttpStatus.BAD_REQUEST;
                } else{
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }
    }
        return new ResponseEntity<>(msg, status);
    }

}
