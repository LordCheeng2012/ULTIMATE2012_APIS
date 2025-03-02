package com.UL2012.API.Kardex.Service;

import com.UL2012.API.Kardex.Models.Entity.Admins;
import com.UL2012.API.Kardex.Models.Entity.Message;

import java.util.List;
import java.util.Optional;

public interface INT_Admins {

    List<Object[]> Init_Session(String User, String Password);
    Admins allAdmins();
    Admins AdminByCodEmp(String CodEmp);
    void DeleteAdminByID(String CodEmp);
}
