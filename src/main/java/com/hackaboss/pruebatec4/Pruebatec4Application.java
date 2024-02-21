package com.hackaboss.pruebatec4;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class Pruebatec4Application {

	public static void main(String[] args) {
		SpringApplication.run(Pruebatec4Application.class, args);


		}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("API Gestión de Reservas")
				.version("0.0.1")
				.description("A continuacion se presenta la documentacion de los distintos end-points que permite recibir la aplicacion para gestionar los diferentes tipos de paquetes de servicios que ofrece una agencia de turismo."));

	}

}


