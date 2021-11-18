package com.ntt_data_bootcamp.microservicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.ntt_data_bootcamp.microservicios.Models.PersonaBuilder;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
public class BootcampNttDataRetoServerApplication implements CommandLineRunner{
	
	@Autowired
	PersonaBuilder builder;
	
	@Autowired
	PersonaService servicio;

	public static void main(String[] args) {
		SpringApplication.run(BootcampNttDataRetoServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		servicio.dbPersonas.add(builder.id(1L).dni("12341234A").nombre("Pablo").apellidos("González Lozano").build());
		servicio.dbPersonas.add(builder.id(2L).dni("12341234A").nombre("Laura").apellidos("García López").build());
		servicio.dbPersonas.add(builder.id(3L).dni("12341234A").nombre("Martín").apellidos("Ramos Pérez").build());
		servicio.dbPersonas.add(builder.id(4L).dni("12341234A").nombre("Ana").apellidos("Álvarez García").build());
		servicio.dbPersonas.add(builder.id(5L).dni("12341234A").nombre("Roberto").apellidos("Martín Martín").build());
		
	}

}
