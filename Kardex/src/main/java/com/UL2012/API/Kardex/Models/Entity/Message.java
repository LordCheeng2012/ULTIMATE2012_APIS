package com.UL2012.API.Kardex.Models.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message  {

    private String Cod_Msg;
    private String type;
    private String title;
    private String message;
    private String Data;


    public Message() {
    }

    public Message(String cod_Msg, String type, String title, String message,String data) {
        Cod_Msg = cod_Msg;
        this.type = type;
        this.title = title;
        this.message = message;
        this.Data = data;
    }
     public static List<Message>  ResponseMessage(List<Object[]> data){
        List<Message> messages = new ArrayList<>();
//importante : los streams , es una funcionalidad de java 8 en adelante que nos ayuda
// a recorrer colecciones de datos de una manera mas eficiente
// y map nos  ayuda a mapear los datos a traves de funciones para cada item de la coleccion
        if( data.equals(null)|| data.isEmpty()){
           messages.add(new Message("ERR01",
                   "Error",
                   "Error en la consulta",
                   "Error en la consulta",
                   "No data"));
        }else {
            messages=data.stream().map(item->
                    new Message(item[0].toString(),
                            item[1].toString(),
                            item[2].toString(),
                            item[3].toString(),
                            item[4].toString())
            ).toList(); // toList es una funcion que nos ayuda
            // a convertir el stream en una lista
        }
        return messages;
     }

    public String getCod_Msg() {
        return Cod_Msg;
    }

    public void setCod_Msg(String cod_Msg) {
        Cod_Msg = cod_Msg;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
