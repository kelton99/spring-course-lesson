package com.kelton.forumapi.controllers;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.kelton.forumapi.controllers.dto.TopicoDTO;
import com.kelton.forumapi.controllers.dto.TopicoDetalhadoDTO;
import com.kelton.forumapi.controllers.forms.AtualizarTopicoForm;
import com.kelton.forumapi.controllers.forms.TopicoForm;
import com.kelton.forumapi.modelo.Topico;
import com.kelton.forumapi.repositories.CursoRepository;
import com.kelton.forumapi.repositories.TopicoRepository;

/*
 * This entity features cache for educational motives  
 * Entity that are changing constantly should feature cache
 * 
 */

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepo;

	@Autowired
	private CursoRepository cursoRepo;

	@GetMapping
	@Cacheable(value = "listaDeTopicos")
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso, Pageable paginacao) {
		
		Page<Topico> topicos;
		topicos = (nomeCurso != null) ? topicoRepo.findByCurso_Nome(nomeCurso, paginacao) : topicoRepo.findAll(paginacao);
		return TopicoDTO.converter(topicos);
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		var topico = form.converter(cursoRepo);
		topicoRepo.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicoDetalhadoDTO> detalhar(@PathVariable Long id) {
		var topico = topicoRepo.findById(id);

		if (topico.isPresent())
			return ResponseEntity.ok(new TopicoDetalhadoDTO(topico.get()));

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarTopicoForm form) {
		var optional = topicoRepo.findById(id);

		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepo);
			return ResponseEntity.ok(new TopicoDTO(topico));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		var topico = topicoRepo.findById(id);

		if (topico.isPresent()) {
			topicoRepo.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
