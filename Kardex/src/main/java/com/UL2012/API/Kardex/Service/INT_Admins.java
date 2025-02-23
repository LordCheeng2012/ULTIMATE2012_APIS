package com.UL2012.API.Kardex.Service;

import com.UL2012.API.Kardex.Models.Entity.Admins;
public interface INT_Admins {

    Admins allAdmins();
    Admins AdminByCodEmp(String CodEmp);
    void DeleteAdminByID(String CodEmp);
}
