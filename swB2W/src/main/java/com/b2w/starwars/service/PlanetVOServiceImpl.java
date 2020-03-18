package com.b2w.starwars.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.b2w.starwars.model.vo.PlanetVO;
import com.b2w.starwars.model.vo.ResultVO;

public class PlanetVOServiceImpl implements PlanetVOService {
	
	public List<PlanetVO> getPlanetsFromAPIByName(String name)
	{
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        
        ResponseEntity<ResultVO> response = restTemplate.exchange("https://swapi.co/api/planets/?search={name}", HttpMethod.GET,entity,ResultVO.class,params);
        
        ResultVO result = (ResultVO)response.getBody();
        
        return result.getResults();
	}
	
}
