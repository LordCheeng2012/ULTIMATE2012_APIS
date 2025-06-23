package com.UL2012.API.Kardex.Controler;
import com.UL2012.API.Kardex.Models.DTO.LoginRequest;
import com.UL2012.API.Kardex.Service.INT_Sessiones;
import com.UL2012.API.Kardex.Utils.Formats;
import com.UL2012.API.Kardex.Utils.Message;
import com.UL2012.API.Kardex.Service.INT_Admins;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Autowired
    private INT_Sessiones Ises;
    //METODOS-> ENDPOINTS
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a un administrador iniciar sesión en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Sesión iniciada correctamente.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los campos.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest admin){
        HttpStatus status=null;
        List<Message> msg = null;
        if((admin.getUsername() == null ||admin.getUsername().isEmpty())
                || (admin.getPassword().isEmpty())||(admin.getPassword() == null)){
            status = HttpStatus.BAD_REQUEST;
            msg = List.of(new Message("ERR01",
                    "Error",
                    "Error de validacion",
                    "Los campos no pueden estar vacios",
                    "",false));
        }else{
        //System.out.println("entro al for");
            if(!Formats.ValidateFormat("^([a-zA-Z]{10,20})@UL.com.pe$", admin.getUsername())){
                status = HttpStatus.BAD_REQUEST;
                msg = List.of(new Message("ERR01",
                        "Error",
                        "Error de validacion",
                        "El formato del usuario no es correcto",
                        "usuario no es el esperado",false));
                status=HttpStatus.BAD_REQUEST;
            }else {
                List<Object[]> messages = Iadm.Init_Session(admin.getUsername(), admin.getPassword());
                msg = Message.ResponseMessage(messages);
                //VERIFICA LA RESPUESTAA
                //System.out.println("entro al else");
                for (Message m : msg) {
                    if (m.getCod_Msg().equals("SUC02")) {
                        status = HttpStatus.ACCEPTED;
                        //SI ES ACEPTADO COMUNICARSE CON EL SERVICIO DE SESIONES PARA INFORMAR QUE HAY UNA SESSION ACTIVA
                        System.out.println("Se acepto la session");
                    } else if (m.getCod_Msg().equals("ERR23")) {
                        status = HttpStatus.UNAUTHORIZED;
                    } else if (m.getCod_Msg().equals("ERR02")) {
                        status = HttpStatus.UNAUTHORIZED;
                    } else if (m.getCod_Msg().equals("ERR22")) {
                        status = HttpStatus.UNAUTHORIZED;
                    } else {
                        status = HttpStatus.INTERNAL_SERVER_ERROR;
                    }
                }
            }
    }
        assert status != null;
        return new ResponseEntity<>(msg, status);
    }

    @GetMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Permite a un administrador cerrar su  sesión existente en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sesión finalizada correctamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los campos.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    })
    public ResponseEntity<?> CloseSession(@RequestParam String id_asistency){
        System.out.println("Cerrando Sessión..");
        HttpStatus status = null;
        List<Message> msg = null;
        status = HttpStatus.BAD_REQUEST;
        msg = null;
        if(id_asistency.isEmpty()){
            msg=List.of(new Message("ERR01",
                    "Error",
                    "Error de validacion",
                    "Los campos no pueden estar vacios",
                    "",false));
        }else{
            String message = "";
            if(Formats.ValidateFormat("^([A-Z]{3}[0-9]{3})([0-9]*)",id_asistency)){

                if(Ises.CloseSession(id_asistency)){
                    System.out.println("Se cerro la sesión,Correctamente");
                    message = "Se cerro la session,Correctamente";
                    status=HttpStatus.OK;
                }else{
                    System.out.println("No se cerro la sesión");
                    message = "La session no se cerro porque no existe o tienes otra sesión abierta";
                }

            }else{
                System.out.println("El formato del usuario no es correcto");
                message = "El formato del usuario no es correcto";
            }
            msg=List.of(new Message("STD01",
                    "STATUS",
                    "Cierre de session",
                    message,
                    "",true));
        }

        return new ResponseEntity<>(msg, status);

    }

}
