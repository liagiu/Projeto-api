package org.serratec.ecommerce.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.serratec.ecommerce.domain.Cliente;
import org.serratec.ecommerce.domain.FotoPerfil;
import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.dto.ClienteLogadoDTO;
import org.serratec.ecommerce.service.ClienteService;
import org.serratec.ecommerce.service.FotoPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private FotoPerfilService fotoPerfilService;
	
	@GetMapping
	@PreAuthorize("hasRole('admin')")
	@ApiOperation(value = "Retorna todas os clientes", notes = "Todos os clientes")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Clientes obtidos com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<List<ClienteLogadoDTO>> obterTodos() {
		return ResponseEntity.ok(clienteService.obterTodos());
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna um cliente por ID", notes = "Cliente por ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Cliente obtido com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<ClienteLogadoDTO> buscar(@PathVariable Long id) {
		if (clienteService.buscar(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteService.buscar(id));
	}
	
	@GetMapping("/{id}/foto")
	@ApiOperation(value = "Buscar foto do perfil por id ", notes = "Foto do perfil por ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Foto do perfil obtida com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
    public ResponseEntity<byte[]> buscarFotoPerfil(@PathVariable Long id){
        FotoPerfil foto = fotoPerfilService.buscar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type",foto.getTipo());
        headers.add("content-length",String.valueOf(foto.getDados().length));
        return new ResponseEntity<>(foto.getDados(),headers,HttpStatus.OK);
    }
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Criar um cliente", notes = "Cria um cliente")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Cliente criado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<?> criar(@RequestParam MultipartFile file, @Valid @RequestPart Cliente cliente) {
		ClienteDTO novoCliente;
		
		try {
			novoCliente = clienteService.criar(file, cliente);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoCliente.getId()).toUri();
		return ResponseEntity.created(uri).body(novoCliente);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar um cliente", notes = "Atualiza um cliente")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Cliente atualizado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
		ClienteDTO clienteAtualizado = clienteService.atualizar(id, cliente);
		
		if (clienteAtualizado == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(clienteAtualizado);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar um cliente", notes = "Deleta um cliente")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Cliente deletado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<ClienteLogadoDTO> deletar(@PathVariable Long id) {
		if (!clienteService.deletar(id)) {
			return ResponseEntity.notFound().build();
		}
		clienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}