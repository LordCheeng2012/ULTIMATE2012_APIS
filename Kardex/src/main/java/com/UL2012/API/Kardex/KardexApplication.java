package com.UL2012.API.Kardex;
import com.UL2012.API.Kardex.Utils.Formats;
import com.UL2012.API.Kardex.Utils.Message;
import com.google.zxing.WriterException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class KardexApplication {

	public static void main(String[] args) throws Exception, WriterException, IOException {
		SpringApplication.run(KardexApplication.class, args);
		//preparamos e mapeo de correciones y sugerencias si encuentra errores en el qr



	}

}
