package com.b2w.starwars.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.b2w.starwars.exception.PlanetaNotFoundException;
import com.b2w.starwars.model.Planeta;
import com.b2w.starwars.service.PlanetaService;

@RestController
@RequestMapping("b2w/")
public class PlanetaController {

	@Autowired
	PlanetaService planetaService;

	@RequestMapping(value = "planetas", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Planeta>> getPlanetas() throws Throwable 
	{    	
		return new ResponseEntity<Iterable<Planeta>>(planetaService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "planetas/{id}", method = RequestMethod.GET)
	public ResponseEntity<Planeta> getPlanetaById(@PathVariable String id) throws PlanetaNotFoundException 
	{    	
		return new ResponseEntity<Planeta>(planetaService.findBy_id(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "planetas/search", method = RequestMethod.GET)
	public ResponseEntity<Planeta> getPlanetaByNome(@RequestParam(required = true) String nome) throws PlanetaNotFoundException  
	{
		return new ResponseEntity<Planeta>(planetaService.findByNome(nome), HttpStatus.OK);
	}

	@RequestMapping(value = "planetas", method = RequestMethod.POST)
	public ResponseEntity<Planeta> createPlaneta(@Valid @RequestBody Planeta planeta) 
	{
		return new ResponseEntity<Planeta>(planetaService.save(planeta),HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "planetas/{id}", method = RequestMethod.DELETE)
	public void deletePlaneta(@PathVariable String id) 
	{    	
		planetaService.delete(Planeta.builder()._id(id).build());
	}
	
	@RequestMapping(value = "planetas", method = RequestMethod.DELETE)
	public void deleteAll() 
	{    	
		planetaService.deleteAll();
	}

}
