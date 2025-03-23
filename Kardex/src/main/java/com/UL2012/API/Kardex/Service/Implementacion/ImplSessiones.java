package com.UL2012.API.Kardex.Service.Implementacion;

import com.UL2012.API.Kardex.Models.Dao.SessionsDao;
import com.UL2012.API.Kardex.Models.Entity.Admins;
import com.UL2012.API.Kardex.Models.Entity.Sessiones;
import com.UL2012.API.Kardex.Service.INT_Sessiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplSessiones implements INT_Sessiones {
    @Autowired
    private SessionsDao Sesdao;


    @Override
    public void deleteSession(String Codigo_Personal) {

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
