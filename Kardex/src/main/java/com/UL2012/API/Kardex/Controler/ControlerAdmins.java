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
        List<Object[]> messages = Iadm.Init_Session(admin.getUsername(), admin.getPassword());
        List<Message> msg = Message.ResponseMessage(messages);
        //VERIFICA LA RESPUESTA
        HttpStatus status=null;
        if(admin.getUsername().equals("") || admin.getPassword().equals("")   ){

            msg.add(new Message("ERR01",
                    "Warning",
                    "Campos vacios",
                    "Campos vacios",
                    "No data"));
            status = HttpStatus.BAD_REQUEST;

        }else {
            System.out.println("entro al else");
            for (Message m : msg) {
                System.out.println("entro al for");
                if (m.getCod_Msg().equals("SUC02")) {
                    status = HttpStatus.OK;
                } else {
                    status = HttpStatus.UNAUTHORIZED;
                }
            }
        }
return new ResponseEntity<>(msg, status);
    }

}
