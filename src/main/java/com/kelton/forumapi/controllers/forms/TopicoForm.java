package com.kelton.forumapi.controllers.forms;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import com.kelton.forumapi.modelo.Topico;
import com.kelton.forumapi.repositories.CursoRepository;
import com.sun.istack.NotNull;

public class TopicoForm {
	
	@NotNull @NotEmpty @Length(min = 5)
	private String titulo;
	
	@NotNull @NotEmpty @Length(min = 10)
	private String mensagem;
	
	@NotNull @NotEmpty
	private Long idCurso;
	
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
	public Long getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}
	public Topico converter(CursoRepository cursoRepo) {
		var curso = cursoRepo.findById(idCurso).get();
		return new Topico(titulo, mensagem, curso);
	}

	
	
}
