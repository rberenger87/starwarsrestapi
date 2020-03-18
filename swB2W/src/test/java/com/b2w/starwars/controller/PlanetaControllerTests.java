package com.b2w.starwars.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.b2w.starwars.model.Planeta;
import com.b2w.starwars.service.PlanetaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PlanetaController.class)
class PlanetaControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlanetaService planetaService;

	@Test
	public void findBy_id_quandoPassadoUmIDdePlaneta_RetornaTodosOsAtributosDoPlaneta() throws Exception {

		Planeta planeta = Planeta.builder()._id("1").nome("Tatooine").clima("clima").terreno("terreno").aparicoes(3).build();

		// When
		Mockito.when(
				planetaService.findBy_id(Mockito.anyString())).thenReturn(planeta);

		// Call
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/b2w/planetas/Tatooine").accept(
						MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{_id:\"1\",nome:Tatooine,clima:clima,terreno:terreno,aparicoes:3}";

		// Assert
		assertEquals(expected, result.getResponse().getContentAsString(), true);

	}

	@Test
	public void findByNome_quandoPassadoUmNomedePlaneta_RetornaTodosOsAtributosDoPlaneta() throws Exception {

		Planeta planeta = Planeta.builder()._id("1").nome("Tatooine").clima("clima").terreno("terreno").aparicoes(3).build();

		// When
		Mockito.when(
				planetaService.findByNome(Mockito.anyString())).thenReturn(planeta);

		// Call
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/b2w/planetas/search?nome=Tatooine").accept(
						MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{_id:\"1\",nome:Tatooine,clima:clima,terreno:terreno,aparicoes:3}";

		// Assert
		assertEquals(expected, result.getResponse().getContentAsString(), true);

	}

	@Test
	public void findAll_retornarTodosOsPlanetas() throws Exception {

		List<Planeta> planetas = new ArrayList<Planeta>();
		Planeta planeta = Planeta.builder()._id("1").nome("Tatooine").clima("clima").terreno("terreno").aparicoes(3).build();
		Planeta planeta2 = Planeta.builder()._id("2").nome("Teste").clima("clima2").terreno("terreno2").aparicoes(0).build();
		planetas.add(planeta);
		planetas.add(planeta2);

		// When
		Mockito.when(
				planetaService.findAll()).thenReturn(planetas);

		// Call
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/b2w/planetas").accept(
						MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[{_id:\"1\",nome:Tatooine,clima:clima,terreno:terreno,aparicoes:3},{_id:\"2\",nome:Teste,clima:clima2,terreno:terreno2,aparicoes:0}]";

		// Assert
		assertEquals(expected, result.getResponse().getContentAsString(), true);

	}

	@Test
	public void post_quandoEnviarUmPlaneta_RetornarCodigo201() throws Exception {

		Planeta planeta = Planeta.builder().nome("Tatooine").clima("clima").terreno("terreno").build();
		mockMvc.perform(post("/b2w/planetas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(planeta)))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void post_quandoEnviarUmPlanetaSemClima_RetornarCodigo400() throws Exception {

		Planeta planeta = Planeta.builder().nome("Tatooine").terreno("terreno").build();
		mockMvc.perform(post("/b2w/planetas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(planeta)))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void post_quandoEnviarUmPlanetaSemTerreno_RetornarCodigo400() throws Exception {

		Planeta planeta = Planeta.builder().nome("Tatooine").clima("clima").build();
		mockMvc.perform(post("/b2w/planetas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(planeta)))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void post_quandoEnviarUmPlanetaSemNome_RetornarCodigo400() throws Exception {

		Planeta planeta = Planeta.builder().clima("clima").terreno("terreno").build();
		mockMvc.perform(post("/b2w/planetas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(planeta)))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void delete_RemoverPlanetaPorID_RetornarCodigo200() throws Exception 
	{
		this.mockMvc.perform(MockMvcRequestBuilders
	            .delete("/b2w/planetas/{id}", "11")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}
	
	@Test
	public void delete_RemoverTodosOsPlanetas() throws Exception 
	{
		this.mockMvc.perform(MockMvcRequestBuilders
	            .delete("/b2w/planetas/")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
