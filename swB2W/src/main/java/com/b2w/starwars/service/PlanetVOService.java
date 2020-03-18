package com.b2w.starwars.service;

import java.util.List;

import com.b2w.starwars.model.vo.PlanetVO;

public interface PlanetVOService {
	
	public List<PlanetVO> getPlanetsFromAPIByName(String name);

}
