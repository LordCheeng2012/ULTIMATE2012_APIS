package com.UL2012.API.Kardex;
import com.UL2012.API.Kardex.Models.Entity.Archivos;
import com.google.zxing.WriterException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class KardexApplication {

	public static void main(String[] args) throws Exception, WriterException, IOException,
			FileNotFoundException {
		SpringApplication.run(KardexApplication.class, args);
		Archivos d = new Archivos();
		//preparamos e mapeo de correciones y sugerencias si encuentra errores en el qr
		//Archivos.CreateQRCode("Caleb","Roxana calamardo","png");
		//System.out.println(Archivos.readQRCode("Caleb.png"));
	}

}
