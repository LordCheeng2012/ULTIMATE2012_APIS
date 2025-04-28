package com.UL2012.API.Kardex;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kardex API")
                        .description("API para la gestión de empleados, asistencia y administración para Circuito Magico del Agua.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Soporte UL2012")
                                .email("soporte@ul2012.com")
                                .url("https://ul2012.com"))
                        .license(new License()
                                .name("Licencia Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}