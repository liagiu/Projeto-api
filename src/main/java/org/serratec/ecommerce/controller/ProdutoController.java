package org.serratec.ecommerce.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.serratec.ecommerce.domain.FotoProduto;
import org.serratec.ecommerce.domain.Produto;
import org.serratec.ecommerce.dto.ProdutoDTO;
import org.serratec.ecommerce.service.FotoProdutoService;
import org.serratec.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/produto")
public class ProdutoController {
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FotoProdutoService fotoProdutoService;
	
	@GetMapping
	@ApiOperation(value = "Retorna todas os produtos", notes = "Todos os produtos")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Produtos obtidos com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<List<ProdutoDTO>> obterTodos() {
		return ResponseEntity.ok(produtoService.obterTodos());
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna um produto por ID", notes = "Produto por ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Produto obtido com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long id) {
		if (produtoService.buscar(id) == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new ProdutoDTO(produtoService.buscar(id)));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Criar um produto", notes = "Cria um ptoduto")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Produto criado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<?> criar(@RequestParam MultipartFile file, @Valid @RequestPart Produto produto) {
		ProdutoDTO novoProduto;
		
		try {
			novoProduto = produtoService.criar(file, produto);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoProduto.getId()).toUri();
		return ResponseEntity.created(uri).body(novoProduto);
	}
	
	@GetMapping("/{id}/foto")
	@ApiOperation(value = "Buscar foto do produto por id ", notes = "Foto do produto por ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Foto do produto obtida com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
    public ResponseEntity<byte[]> buscarFotoProduto(@PathVariable Long id){
        FotoProduto foto = fotoProdutoService.buscar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type",foto.getTipo());
        headers.add("content-length",String.valueOf(foto.getDados().length));
        return new ResponseEntity<>(foto.getDados(),headers,HttpStatus.OK);
    }

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar um produto", notes = "Atualiza um produto")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Produto atualizado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
		ProdutoDTO produtoAtualizado = produtoService.atualizar(id, produto);
		
		if (produtoAtualizado == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(produtoAtualizado);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar um produto", notes = "Deleta um produto")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Produto deletado com sucesso"),
			@ApiResponse(code = 401, message = "Erro de autenticação"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro no servidor"),
			@ApiResponse(code = 505, message = "Ocorreu uma exceção")
	})
	public ResponseEntity<Produto> deletar(@PathVariable Long id) {
		if (!produtoService.deletar(id)) {
			return ResponseEntity.notFound().build();
		}
		produtoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}