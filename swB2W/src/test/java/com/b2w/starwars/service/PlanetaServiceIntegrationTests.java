package com.b2w.starwars.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.b2w.starwars.model.Planeta;
import com.b2w.starwars.model.vo.PlanetVO;
import com.b2w.starwars.model.vo.ResultVO;
import com.b2w.starwars.repository.PlanetaRepository;

@RunWith(SpringRunner.class)
public class PlanetaServiceIntegrationTests {

	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {

		@Bean
		public PlanetaService planetaService() {
			return new PlanetaServiceImpl();
		}
	}

	@MockBean
	private PlanetaRepository planetaRepository;

	@MockBean
	SequenceGeneratorService sequenceGeneratorService;

	@MockBean
	PlanetVOServiceImpl planetVOServiceImpl;

	@Autowired
	private PlanetaServiceImpl planetaService;
	
	@Mock
	private RestTemplate restTemplate;

	@Test
	public void quandoPassadoUmPlanetaExistente_RetornaQuantidadeDeFilmes() 
	{
		
		ArrayList<String> films = new ArrayList<String>();
		films.add("1");
		films.add("2");

		List<PlanetVO> listPlanetVO = new ArrayList<PlanetVO>();

		PlanetVO planetVO = PlanetVO.builder().name("Tatooine").films(films).build();

		listPlanetVO.add(planetVO);

		Mockito.when(planetVOServiceImpl.getPlanetsFromAPIByName("Tatooine"))
		.thenReturn(listPlanetVO);
		
		Planeta planeta = Planeta.builder().nome("Tatooine").clima("clima").terreno("terreno").build();

		int count = planetaService.getAparicoesFromPlanet(planeta);

		assertThat(count)
		.isEqualTo(2);
	}

	@Test
	public void quandoPassadoUmPlanetaInexistente_EntaoRetornaNenhumFilme() 
	{

		int count = planetaService.getAparicoesFromPlanet(new Planeta());
		
		Mockito
        .when(restTemplate
        		.exchange(ArgumentMatchers.anyString(),
                          ArgumentMatchers.any(HttpMethod.class),
                          ArgumentMatchers.any(),
                          ArgumentMatchers.<Class<ResultVO>>any()
        				  )
        	  )
        .thenReturn(new ResponseEntity<ResultVO>(new ResultVO(), HttpStatus.NOT_FOUND));

		assertThat(count)
		.isEqualTo(0);
	}

}
