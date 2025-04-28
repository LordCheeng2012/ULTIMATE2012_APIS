package com.UL2012.API.Kardex.Service.Implementacion;
import com.UL2012.API.Kardex.Models.Dao.SessionsDao;
import com.UL2012.API.Kardex.Models.Entity.Sessiones;
import com.UL2012.API.Kardex.Service.INT_Sessiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ImplSessiones implements INT_Sessiones {
    @Autowired
    private SessionsDao Sesdao;
    @Override
    public boolean CloseSession(String id_asistency) {
         //verificar session
        Sessiones ses= Sesdao.findById(id_asistency).orElse(null);
        if(ses!=null){
            //verificar cantidad de sessiones
            if(ses.getOff_Session()==null){
                ses.setOff_Session(String.valueOf(LocalDateTime.now()));
                ses= Sesdao.save(ses);
                System.out.println("Se cerro la session");
                return true;
            }
            System.out.println("Ya existe una sesion cerrada");
            return false;

        }else{
            System.out.println("No se encontro la session");
            return false;
        }
    }

    @Override
    public Sessiones allSessions() {
        return null;
    }

    @Override
    public Sessiones allSessionByCodEmp() {
        return null;
    }
}
