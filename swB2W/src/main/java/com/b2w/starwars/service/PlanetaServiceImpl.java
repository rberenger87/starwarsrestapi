package com.b2w.starwars.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2w.starwars.exception.PlanetaNotFoundException;
import com.b2w.starwars.model.Planeta;
import com.b2w.starwars.model.vo.PlanetVO;
import com.b2w.starwars.repository.PlanetaRepository;

@Service
public class PlanetaServiceImpl implements PlanetaService {

	@Autowired
	PlanetaRepository planetaRepository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	PlanetVOService planetVOService;
	
	public Planeta findByNome(String nome) 
	{
		Planeta planeta = planetaRepository.findByNome(nome);
		if(planeta != null) 
		{
			planeta.setAparicoes(getAparicoesFromPlanet(planeta));
			return planeta;
		}
		else
			throw new PlanetaNotFoundException();
	}

	public Planeta save(Planeta planeta) 
	{
		planeta.set_id(String.valueOf(sequenceGeneratorService.generateSequence(Planeta.SEQUENCE_NAME)));
		return planetaRepository.save(planeta);
	}

	public void delete(Planeta planeta) 
	{
		planetaRepository.delete(planeta);
	}

	public void deleteAll() 
	{
		planetaRepository.deleteAll();	
	}

	public Iterable<Planeta> findAll() 
	{
		List<Planeta> planetas = planetaRepository.findAll();
		planetas.forEach(planet->planet.setAparicoes(getAparicoesFromPlanet(planet)));
		return planetas;
	}

	public Planeta findBy_id(String id) throws PlanetaNotFoundException 
	{
		
		Planeta planeta = planetaRepository.findBy_id(id);
		if(planeta != null) 
		{
			planeta.setAparicoes(getAparicoesFromPlanet(planeta));
			return planeta;
		}
		else
			throw new PlanetaNotFoundException();
	}
	
	protected int getAparicoesFromPlanet(Planeta planet) 
	{
		List<PlanetVO> planetVO = planetVOService.getPlanetsFromAPIByName(planet.getNome());
		if (planetVO.size() > 0 && planetVO.get(0).getFilms() != null)
			return planetVO.get(0).getFilms().size();
		else
			return 0;
	}

}
