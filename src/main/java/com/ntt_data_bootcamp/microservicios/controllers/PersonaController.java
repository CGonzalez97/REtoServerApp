package com.ntt_data_bootcamp.microservicios.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ntt_data_bootcamp.microservicios.PersonaService;
import com.ntt_data_bootcamp.microservicios.Models.Persona;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class PersonaController {
	
	private final static Logger logger = LoggerFactory.getLogger(PersonaController.class);
	
	@Autowired
	PersonaService servicio;
	
	private Counter counterGetAll;
	private Counter counterGetById;
	private Counter counterUpdate;
	private Counter counterPost;
	private Counter counterDelete;
	

	public PersonaController(MeterRegistry registry) {
		this.counterGetAll = Counter.builder("peticiones.getall").register(registry);
		this.counterGetById = Counter.builder("peticiones.getbyid").register(registry);
		this.counterUpdate = Counter.builder("peticiones.update").register(registry);
		this.counterPost = Counter.builder("peticiones.post").register(registry);
		this.counterDelete = Counter.builder("peticiones.delete").register(registry);
	}
	
	@GetMapping()
	public ResponseEntity<List<Persona>> getAll(){
		counterGetAll.increment();
		return ResponseEntity.status(HttpStatus.OK).body(servicio.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Persona> getById(@PathVariable("id") Long id){
		counterGetById.increment();
		Optional<Persona> resul = servicio.getById(id);
		if(resul.isEmpty()) {
			logger.info("Searched person not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(resul.get());
		}
	}
	
	@PutMapping()
	public ResponseEntity<Void> updatePersona(@RequestBody Persona personaUpdated){
		counterUpdate.increment();
		servicio.updatePersona(personaUpdated);
		logger.info("Person updated.");
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@PostMapping()
	public ResponseEntity<Void> postPersona(@RequestBody Persona personaN){
		counterPost.increment();
		servicio.addPersona(personaN);
		logger.info("Person created.");
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePersona(@PathVariable("id") Long id){
		counterDelete.increment();
		servicio.deletePersonaById(id);
		logger.info("Person deleted.");
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
