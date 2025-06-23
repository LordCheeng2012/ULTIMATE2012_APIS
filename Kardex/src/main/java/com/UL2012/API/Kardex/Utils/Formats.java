package com.UL2012.API.Kardex.Utils;
import com.UL2012.API.Kardex.Models.Entity.Empleados;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

//clase para validar formatos y validaciones de parametros
public class Formats {


    private static final Logger log = LoggerFactory.getLogger(Formats.class);
    private Message msg= new Message();
    public static boolean ValidateFormat(String expresion, String values){
        System.out.println("validando formato");
        if(values.matches(expresion)){
            System.out.println("formato valido para : "+values);
            return true;
        }else{
            System.out.println("Formato invalido : "+values);
            return false;
        }
    }
    public static boolean isValidDate(String d, String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        df.setLenient(false);
        try {
            df.parse(d);
        } catch (ParseException e) {
            return false;
        }
        return true;
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
    public Message ValidateObjectEmployes(Object esperado) {
        boolean flag=true;
        String mensaje ="";
       if(esperado instanceof Empleados ){
           System.out.println("Es un Objeto de tipo "+ Empleados.class);
            for (Object param : ((Empleados) esperado).Get_Atributes()){
                System.out.println("validando para : "+param.toString());
              if (param!= null || !param.toString().isEmpty()){
                  System.out.println("El parametro recibido es :"+param);
              }else {
                  mensaje = "Hay valores vacios en los datos enviados ,Exactamente:"+param;
                  System.out.println(mensaje);
                  return new Message().Get_Warning(mensaje,"Error de validación",flag);

              }

            }
           if(!ValidateFormat("^[A-Z]{3}[0-9]{3}$",((Empleados) esperado).getCodigoPersonal())){
               flag=false;
               mensaje="Formato invalido para el codigo de Empleado";
               System.out.println(mensaje);
               return new Message().Get_Warning(mensaje,"Error de validación",flag);
           }

           if(!isValidDate(((Empleados) esperado).getFechaNacimiento().toString(),"yyyy-MM-dd")){
               flag=false;
               mensaje = "La fecha de Nacimiento no coincide con el formato requerido";
               System.out.println(mensaje);
               return new Message().Get_Warning(mensaje,"Error de validación",flag);
           }
           if (!ValidateFormat("-?\\d+(\\\\.\\d+)?",((Empleados) esperado).getEdad().toString())){
               mensaje="La edad del Empleado debe ser un numero";
               System.out.println(mensaje);
               flag=false;
               return new Message().Get_Warning(mensaje,"Error de validación",flag);
           }
       }
        return new Message().Get_Success(mensaje,"Validacion Exitosa");
    }
    public static boolean validateHeaders(Map<String, Optional<Object>> headers) throws NullPointerException{
        AtomicBoolean flag = new AtomicBoolean(false);
        headers.forEach((k,v)->{
            if (v.isPresent()){
                Object obj = v.orElse("valor defecto");
                System.out.println("Header : "+k + " Presente ");
                System.out.println("valor de header "+ k + ": "+obj);
                flag.set(true);
            }else {
                String msg ="Header : "+k +" no esta presente";
                System.out.println(msg);
                throw  new NullPointerException(msg);
            }
        });
        return flag.get();
    }


}
