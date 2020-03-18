package com.b2w.starwars.service;

import com.b2w.starwars.exception.PlanetaNotFoundException;
import com.b2w.starwars.model.Planeta;

public interface PlanetaService {

	
	public Planeta findByNome(String nome);

	public Planeta save(Planeta planeta);

	public void delete(Planeta planeta);
	
	public void deleteAll();

	public Iterable<Planeta> findAll();

	public Planeta findBy_id(String id) throws PlanetaNotFoundException;

}
