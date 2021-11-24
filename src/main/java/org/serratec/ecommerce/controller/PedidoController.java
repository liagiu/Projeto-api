package org.serratec.ecommerce.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.serratec.ecommerce.domain.Pedido;
import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping
	@ApiOperation(value = "Retorna todos os pedidos", notes = "Todos os pedidos")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Pedidos obtidos com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<List<PedidoDTO>> obterTodos() {
		return ResponseEntity.ok(pedidoService.obterTodos());
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna um pedido por ID", notes = "Pedido por ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Pedido obtido com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<PedidoDTO> buscar(@PathVariable Long id) {
		if (pedidoService.buscar(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new PedidoDTO(pedidoService.buscar(id)));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Criar um pedido", notes = "Cria um pedido")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Pedido criado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<?> criar(@RequestBody @Valid Pedido pedido) {
		PedidoDTO pedidoDTO = pedidoService.criar(pedido);
		
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}").buildAndExpand(pedidoDTO.getId())
					.toUri();
		return ResponseEntity.created(uri).body(pedidoDTO);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar um pedido", notes = "Atualiza um pedido")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Pedido atualizado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Pedido pedido) {
		PedidoDTO pedidoAtualizado = pedidoService.atualizar(id, pedido);
		
		if (pedidoAtualizado == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(pedidoAtualizado);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar um pedido", notes = "Deleta um pedido")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Pedido deletado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<Pedido> deletar(@PathVariable Long id) {
		if (!pedidoService.deletar(id)) {
			return ResponseEntity.notFound().build();
		}
		pedidoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}