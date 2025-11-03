package com.clinicaOdontologica.up_microservicios;

import com.clinicaOdontologica.up_microservicios.dao.BD;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UpMicroserviciosApplication {

	public static void main(String[] args) {

        BD.crearTablas();
        SpringApplication.run(UpMicroserviciosApplication.class, args);
	}

}
