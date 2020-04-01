package com.kelton.forumapi.controllers.dto;

import java.time.LocalDateTime;

import com.kelton.forumapi.modelo.Resposta;

public class RespostaDTO {

	private Long id;
	private String mensagem;
	private LocalDateTime dataCriacao;
	private String nomeAutor;
	
	public RespostaDTO(Resposta resposta) {
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.dataCriacao = resposta.getDataCriacao();
		this.nomeAutor = resposta.getAutor().getNome();
	}
	
}
