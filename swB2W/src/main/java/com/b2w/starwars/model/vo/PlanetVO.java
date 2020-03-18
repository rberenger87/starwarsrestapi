package com.b2w.starwars.model.vo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanetVO {

	private String name;
	private List<String> films;
	
}
