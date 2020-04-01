package com.kelton.forumapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelton.forumapi.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
