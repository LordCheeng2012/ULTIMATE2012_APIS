package com.UL2012.API.Kardex.Service.Implementacion;

import com.UL2012.API.Kardex.Models.Dao.EmpleadosDao;
import com.UL2012.API.Kardex.Models.Entity.Empleados;
import com.UL2012.API.Kardex.Service.INT_Empleados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImplEmpleados implements INT_Empleados {

    @Autowired
    private EmpleadosDao Empleado;

    @Transactional
    @Override
    public Empleados SaveOrUpdateEmpleado(Empleados Emp) {
        return Empleado.save(Emp);
    }


    @Transactional(readOnly = true)
    @Override
    public Iterable<Empleados> EMPLEADOS() {
        Iterable<Empleados> EMP = Empleado.findAll();
        EMP.forEach(enp -> System.out.println(enp.toString()));
        return EMP;

    }

    @Transactional(readOnly = true)
    @Override
    public Empleados FindByIdEmpleado(String Emp) {
        System.out.println("Buscando empleado con código personal: " + Emp);
        Empleados empleado = Empleado.findById(Emp).orElse(null);
        if (empleado == null) {
            System.out.println("No se encontró empleado con el código personal: " + Emp);
        } else {
            System.out.println("Empleado encontrado: " + empleado.toString());
        }
        return empleado;
    }

    @Transactional
    @Override
    public Empleados RevParam(String parm) {
        System.out.println("En Implementacion");
        System.out.println("Parametro recibo es :" + parm);
        return Empleado.validateParameter(parm);
    }

    @Transactional
    @Override
    public void DeleteEmp(Empleados Emp) {
        Empleado.delete(Emp);
    }
}