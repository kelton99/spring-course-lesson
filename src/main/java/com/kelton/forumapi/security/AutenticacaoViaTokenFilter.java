package com.kelton.forumapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kelton.forumapi.modelo.Usuario;
import com.kelton.forumapi.repositories.UsuarioRepository;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	

	private TokenService tokenService;
	private UsuarioRepository repo;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repo) {
		this.tokenService = tokenService;
		this.repo = repo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken (request);
		boolean valido = tokenService.isTokenValido(token);
		
		if(valido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticarCliente(String token) {
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = repo.findById(idUsuario).get();
		var authentication = new UsernamePasswordAuthenticationToken(usuario,  null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);		
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) 
			return null;
		
		return token.split(" ")[1];
	}

	
}
