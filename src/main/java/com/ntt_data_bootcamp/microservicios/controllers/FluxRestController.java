package com.ntt_data_bootcamp.microservicios.controllers;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntt_data_bootcamp.microservicios.PersonaService;
import com.ntt_data_bootcamp.microservicios.Models.Persona;

import reactor.core.publisher.Flux;

@RestController
public class FluxRestController {
	
	@Autowired
	PersonaService servicio;	
	
	@GetMapping(value="/listaAsync",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<List<Persona>> buscarTodas() {
		return Flux.just(servicio.getAll()).delaySequence(Duration.ofSeconds(3)).repeat();
	}
		
}
