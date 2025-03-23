package com.UL2012.API.Kardex.Service;
import com.UL2012.API.Kardex.Models.Entity.Admins;
import com.UL2012.API.Kardex.Models.Entity.Sessiones;

public interface INT_Sessiones {

    void deleteSession(String Codigo_Personal);
    Sessiones allSessions();
    Sessiones allSessionByCodEmp();

}
