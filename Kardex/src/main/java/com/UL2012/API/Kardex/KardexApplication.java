package com.UL2012.API.Kardex;
import com.google.zxing.WriterException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
@SpringBootApplication
public class KardexApplication {

	public static void main(String[] args) throws Exception, WriterException, IOException {
		SpringApplication.run(KardexApplication.class, args);
		//preparamos e mapeo de correciones y sugerencias si encuentra errores en el qr
		//Prueba de integracion entre estos componentes
		//System.out.println("creando qr");
		//Archivos.CreateQRCode("Olenka","OPF716","png");
		//System.out.println("leyendo qr..");
		//String code = Archivos.readQRCode("MSOLISAL.png");
		//System.out.println(code);

	}

}
