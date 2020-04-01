package com.kelton.forumapi.controllers.forms;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.kelton.forumapi.modelo.Topico;
import com.kelton.forumapi.repositories.TopicoRepository;
import com.sun.istack.NotNull;

public class AtualizarTopicoForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String titulo;
	
	@NotNull @NotEmpty @Length(min = 10)
	private String mensagem;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Topico atualizar(Long id, TopicoRepository topicoRepo) {
		var topico = topicoRepo.findById(id).get();
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		return topico;
	}
	
	
	
	
}
