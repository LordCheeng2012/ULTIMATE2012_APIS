package com.UL2012.API.Kardex.Models.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.*;
import javax.imageio.ImageIO;
public  class Archivos {
    //propiedades
    private String PathUrl;
    private File file;
    private String TypeFile;
    //Metodos de la clase
    public String  GenerateFile(String fileName,String extensionFile) {
        try {
            setTypeFile(extensionFile);
            File pathfile = new File(getPathUrl() + fileName + getTypeFile());
            System.out.println("path del archivo generado : " + getPathUrl() + fileName + getTypeFile());
            PrintWriter salida = new PrintWriter(pathfile);
            setFile(pathfile);
            salida.close();
            System.out.println("Archivo creado ");
            return "[SUCCESS] : Archivo Generado correctamente";
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.out.println("[ERROR] : "+ex.getMessage());
            return null;
        }
    }
    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public String getTypeFile() {
        return TypeFile;
    }
    public void setTypeFile(String typeFile) {
        TypeFile = "."+typeFile;
    }
    public void EditFile(String file , String content){
        try{
            File f = new File(this.PathUrl+file);
            PrintWriter salida = new PrintWriter(new FileWriter(f,true));
            salida.println(content);
            salida.close();
            System.out.println("Archivo Editado ");
        }

        catch(FileNotFoundException ex)
        {
            ex.printStackTrace(System.out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void LeerArchivo(String file){

        try{
           BufferedReader leer = new BufferedReader(new FileReader(this.PathUrl+file));
           String info = leer.readLine();
           while (info !=null){
               System.out.println(info);
               info = leer.readLine();
           }
           leer.close();

            System.out.println("Se leyo el archivo ");

        }

        catch(FileNotFoundException ex)
        {
            ex.printStackTrace(System.out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Archivos() {
        setPathUrl("Kardex/lib/QR/");
    }
    public Archivos(String pathUrl) {
        PathUrl = pathUrl;
    }
    public String getPathUrl() {
        return PathUrl;
    }
    public void setPathUrl(String pathUrl) {
        PathUrl = pathUrl;
    }
    public File SearhFile(String Filename) throws FileNotFoundException{
        Archivos ar = new Archivos();
        File fl = new File(ar.getPathUrl()+Filename);
        if(fl.exists()){
            System.out.println("[SUCCESS]: El archivo existe");
            return fl;
        }else{
            System.out.println("[WARNING]: El archivo no existe");
            return  null;
        }

    }
    //Estaticos
    public static void CreateQRCode(String NameFile,String Valor,String extensionfile){
        List<String> p = new ArrayList<>();
        p.add(NameFile);
        p.add(Valor);
        p.add(extensionfile);
        for (String dat:p){
            if(dat==null || dat.isEmpty()|| dat.equals("")){
                System.out.println("[ERROR]: No se puede crear el qr, faltan datos");
                return;
            }
        }


        try {
            //generar el archivo
            Archivos Dir= new Archivos();
            String res= Dir.GenerateFile(NameFile,extensionfile);
            System.out.println("[INFO]: "+res);
            //Generar qr
            QRCodeWriter qr= new QRCodeWriter();
            BitMatrix matrix = qr.encode(Valor, BarcodeFormat.QR_CODE,
                    100, 100);
            int MatrixWidth = matrix.getWidth();
            BufferedImage img = new BufferedImage(MatrixWidth,
                    MatrixWidth,
                    BufferedImage.TYPE_INT_RGB);
            img.createGraphics();
            Graphics2D gd = (Graphics2D) img.createGraphics();
            gd.setColor(Color.WHITE);
            gd.fillRect(0,0,100,100);
            gd.setColor(Color.BLACK);
            //generar la imagen
            //debemos recorrer la matrix
            for (int i = 0; i <MatrixWidth ; i++) {
                for (int j = 0; j <MatrixWidth ; j++) {
                    if(matrix.get(i,j)){
                        gd.fillRect(i,j,1,1);
                    }
                }
            }
            if(Dir.getFile()!=null){
                //guardar la informacion -> bytes en la ruta
                FileOutputStream qrCode= new FileOutputStream(Dir.getFile());
                boolean flag = ImageIO.write(img,extensionfile,qrCode);
                if(flag){
                    System.out.println("Codigo generado correctamente," +
                            " verifique la ruta de :"+Dir.getFile());
                }else{
                    System.out.println("[Error] :  no se lleno la informacion a la imagen");
                    System.out.println("datos : "+img.toString()+" ," +
                            " "+Dir.getTypeFile()+", "+qrCode.toString());

                }

            }

        }catch (WriterException exception){
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE,null,exception);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void CreateFile(String PathFilename){
        try{
            File file = new File(PathFilename);
            PrintWriter salida = new PrintWriter(file);
            salida.close();
            System.out.println("Archivo creado ");
        }

        catch(Exception ex)
        {
            ex.printStackTrace(System.out);
        }
    }
    public static String readQRCode(String file)
            throws FileNotFoundException, IOException, NotFoundException,WriterException {
            //Obtener la imagen qr
        if (file.isEmpty() || file==null){
            return "[ERROR]: Warning this param : "+file+ " is null or empty";
        }
        Archivos arc= new Archivos();
        File fl = arc.SearhFile(file);

        if(fl!=null){
            Map <DecodeHintType, String> hintMap = new HashMap < DecodeHintType, String>();
            hintMap.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            // Si no es null,entonces debemos de hacer la lectura nesesitamos un BufferedImage
            FileInputStream fileout = new FileInputStream(fl);
            //leemos el archivo , suponiendo que es una imegen
            BufferedImage img = ImageIO.read(fileout);
            //lleemos la imagen
            BufferedImageLuminanceSource imBuffered= new BufferedImageLuminanceSource(img);
            //convertirmos en bufferedImageLuminacesource fuente de lumicencia de la imagen
            HybridBinarizer binarizer = new HybridBinarizer(imBuffered);
            BinaryBitmap bitmap = new BinaryBitmap(binarizer);
            Result res = new MultiFormatReader().decode(bitmap,hintMap);
            String resultado = res.getText();
            System.out.println("[SUCCESS] : Información Extraida");
            return resultado;
        }else{
           return "[ERROR]: No se encontro el archivo";
        }
    }
}
