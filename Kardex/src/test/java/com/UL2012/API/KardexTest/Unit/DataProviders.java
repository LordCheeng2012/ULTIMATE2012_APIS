package com.UL2012.API.KardexTest.Unit;


import java.util.ArrayList;
import java.util.List;

public class DataProviders {
    public static List<Object[]> ResponseLogin(String Estatus){
        Object[] data ={"","","","",""};
        List<Object[]> res = new ArrayList<>();
       switch (Estatus){
           case "success":
           data[0]="SUC02";
           data[1]="Succes";
           data[2]="Componente Succes";
           data[3]="La prueba unitaria paso";
           data[4]="SUCC01";
           res.add(data);
           break;
           case "Error":
               data[0]="SUC02";
               data[1]="Componente Succes";
               data[2]="La prueba unitaria paso";
               data[3]="no data";
               data[4]="ERR01";
               res.add(data);
               break;
           case "Warning":
               data[0]="Warning";
               data[1]="Datos invalidos";
               data[2]="La prueba unitaria paso";
               data[3]="no data";
               data[4]="WRN01";
               res.add(data);
               break;
           case "Know":
               data[0]="Know";
               data[1]="credenciales inexistentes";
               data[2]="La prueba unitaria paso";
               data[3]="no data";
               data[4]="?";
               res.add(data);
               break;
           default:
               System.out.println("no se reconocio el parametro retornara null");
               res.add(null);

       }

        res.add(data);
       return res;
    }

}
