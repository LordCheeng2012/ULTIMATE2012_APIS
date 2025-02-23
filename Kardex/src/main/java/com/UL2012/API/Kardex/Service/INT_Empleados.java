package com.UL2012.API.Kardex.Service;

import com.UL2012.API.Kardex.Models.Entity.Empleados;

public interface INT_Empleados {

   Empleados SaveOrUpdateEmpleado(Empleados Emp);
    Empleados FindByIdEmpleado(String Emp);
    Iterable<Empleados> EMPLEADOS();
    Empleados RevParam(String param);
    void DeleteEmp(Empleados Emp);

}
