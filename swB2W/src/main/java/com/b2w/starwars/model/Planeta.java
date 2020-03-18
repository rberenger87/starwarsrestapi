package com.b2w.starwars.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class Planeta {

    @Transient
    public static final String SEQUENCE_NAME = "planeta_sequence";
	
	@Id
	private String _id;
	
	@NotEmpty(message = "Por favor informe um nome")
	private String nome;
	@NotEmpty(message = "Por favor informe um clima")
	private String clima;
	@NotEmpty(message = "Por favor informe um terreno")
	private String terreno;
	
	private int aparicoes;
	
	public Planeta(String _id, String nome, String clima, String terreno, int aparicoes) 
	{
		this._id = _id;
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
		this.aparicoes = aparicoes;
	}
	
	
}
