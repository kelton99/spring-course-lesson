package com.kelton.forumapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelton.forumapi.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	List<Topico> findByCurso_Nome(String nomeCurso);

}
