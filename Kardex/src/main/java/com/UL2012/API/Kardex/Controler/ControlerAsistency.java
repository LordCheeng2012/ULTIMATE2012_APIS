package com.UL2012.API.Kardex.Controler;
import com.UL2012.API.Kardex.Models.DTO.AsistencyQueryDto;
import com.UL2012.API.Kardex.Models.Entity.Asitencia;
import com.UL2012.API.Kardex.Models.Entity.Message;
import com.UL2012.API.Kardex.Service.INT_Asitency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("UL2012/API/Kardex/V1/Asistency")
public class ControlerAsistency {

    @Autowired
    private INT_Asitency IAsis;
    @PostMapping("/Query")
    public ResponseEntity<?> BusquedaByQueryParams(@RequestBody AsistencyQueryDto AsistenciaQuery) {
        System.out.println("Objeto es : "+AsistenciaQuery.toString());
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
            msg.setTitle("Error en la consulta");
            msg.setMessage(e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

    }




}
