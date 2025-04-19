package com.UL2012.API.Kardex.Utils;
import java.time.LocalDate;

//clase para validar formatos y validaciones de parametros
public class Formats {
    public static boolean ValidateFormat(String expresion, String values){
        System.out.println("validando formato");
        if(values.matches(expresion)){
            System.out.println("formato valido");
            return true;
        }else{
            System.out.println("Formato invalido");
            return false;
        }
    }
    //metodo para obtener los datos de la fecha por separado  concatenar al id de una asistencica
    public static String CreateID_Asistency(String codeEmp){
        LocalDate localDate= LocalDate.now();
        int day =localDate.getDayOfMonth();
        String dayfor = (day<=9) ? "0"+day : String.valueOf(day);
        int month =localDate.getMonthValue();
        String monthfor = (month<=9) ? "0"+month : String.valueOf(month);
        String year =String.valueOf(localDate.getYear());
        String ID= codeEmp+dayfor+monthfor+year;
        String exp = "^([A-Z]{3}[0-9][0-9][0-9])(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[0-2])(2[0-9][2-5][1-9])$";
        Formats.ValidateFormat(exp,ID);
        System.out.println("ID GENERADO|"+ID+"|");
        return ID;
    }

}
