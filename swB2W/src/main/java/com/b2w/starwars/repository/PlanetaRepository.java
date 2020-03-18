package com.b2w.starwars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.b2w.starwars.model.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	public Planeta findByNome(String nome);

	public void deleteByNome(String nome);

	public Planeta findBy_id(String _id);
	
	  
}
