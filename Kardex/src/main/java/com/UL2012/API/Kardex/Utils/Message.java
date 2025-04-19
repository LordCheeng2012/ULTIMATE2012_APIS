package com.UL2012.API.Kardex.Utils;

import org.springframework.http.HttpStatus;

import java.util.List;
public class Message  {

    private String Cod_Msg;
    private String type;
    private String title;
    private String message;
    private String Data;
    private HttpStatus codeStatus;
    public Message() {
    }
    public Message(String cod_Msg, String type, String title, String message,String data) {
        Cod_Msg = cod_Msg;
        this.type = type;
        this.title = title;
        this.message = message;
        this.Data = data;
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
    @Override
    public String toString() {
        return "Message{" +
                "Cod_Msg='" + Cod_Msg + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", Data='" + Data + '\'' +
                '}';
    }

    public HttpStatus getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(HttpStatus codeStatus) {
        this.codeStatus = codeStatus;
    }

    public  Message Get_Warning(String message, String details){
        String Code_Msg="WAR01";
        String Title ="Inesperado";
        String Type="Warning";
        String Msg =message;
        String data;
        HttpStatus CodeStatus=HttpStatus.BAD_REQUEST;
        data = details;
        return new Message(Code_Msg,Title,Type,Msg,data);
    }
    public  Message Get_Success(String message, String details){
        String Code_Msg="SUC01";
        String Title ="Exito";
        String Type="Success";
        String Msg =message;
        String data=details;
        HttpStatus CodeStatus=HttpStatus.OK;
        return new Message(Code_Msg,Title,Type,Msg,data);
    }
    public static List<Message>  ResponseMessage(List<Object[]> data){
        List<Message> messages;
        System.out.println("Recibiendo respuesta del servidor");
//importante : los streams , es una funcionalidad de java 8 en adelante que nos ayuda
// a recorrer colecciones de datos de una manera mas eficiente
// y map nos  ayuda a mapear los datos a traves de funciones para cada item de la coleccion
        //ystem.out.println("entro al else");
        for(Object[] obj :data){
            System.out.println("escaneando datos del objeto:");
            System.out.println("0:"+obj[0]);
            System.out.println("1:"+obj[1]);
            System.out.println("2:"+obj[2]);
            System.out.println("3:"+obj[3]);
            System.out.println("4:"+obj[4]);
        }
        messages=data.stream().map(item->

                new Message(item[0].toString(),
                        item[1].toString(),
                        item[2].toString(),
                        item[3].toString(),
                        item[4].toString())
        ).toList(); // toList es una funcion que nos ayuda
        // a convertir el stream en una lista

        return messages;
    }
    public  void Show_Message(){
        System.out.println("[INFO] :"+this.title);
        System.out.println("[Code] :"+this.Cod_Msg);
        System.out.println("[Description] :"+this.message);
        System.out.println("[Details] :"+this.Data);
    }
}
//get response message default



