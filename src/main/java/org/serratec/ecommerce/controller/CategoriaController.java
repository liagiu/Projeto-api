package org.serratec.ecommerce.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.serratec.ecommerce.domain.Categoria;
import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.exception.CategoriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.serratec.ecommerce.service.CategoriaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	@ApiOperation(value = "Retorna todas as categorias", notes = "Todas as categorias")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Categorias obtidas com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<List<CategoriaDTO>> obterTodos() {
		return ResponseEntity.ok(categoriaService.obterTodos());
	}
	
	@GetMapping("/{nome}")
	@ApiOperation(value = "Retorna uma categoria", notes = "Categoria por nome")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Categoria obtida com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<CategoriaDTO> buscar(@PathVariable String nome) {
		if (categoriaService.buscar(nome) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaService.buscar(nome));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Cria uma nova categoria", notes = "Criar categoria")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Categoria criada com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<?> criar(@Valid @RequestBody Categoria categoria) {
		try {
			CategoriaDTO categoriaDTO = categoriaService.criar(categoria);
			
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{nome}").buildAndExpand(categoriaDTO.getNome())
					.toUri();
			return ResponseEntity.created(uri).body(categoriaDTO);
		} catch (CategoriaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{nome}")
	@PreAuthorize("hasRole('admin')")
	@ApiOperation(value = "Atualiza os dados da categoria", notes = "Atualizar categoria")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Categoria atualizada com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<?> atualizar(@PathVariable String nome, @Valid @RequestBody Categoria categoria) {
		try {
			CategoriaDTO categoriaAtualizada = categoriaService.atualizar(nome, categoria);
			return ResponseEntity.ok(categoriaAtualizada);
		} catch (CategoriaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@Transactional
	@DeleteMapping("/{nome}")
	@PreAuthorize("hasRole('admin')")
	@ApiOperation(value = "Deleta uma categoria", notes = "Deletar categoria")
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Categoria deletada com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<CategoriaDTO> deletar(@PathVariable String nome) {
		if(!categoriaService.deletar(nome)) {
			return ResponseEntity.notFound().build();
		}
		categoriaService.deletar(nome);
		return ResponseEntity.noContent().build();	
	}

}