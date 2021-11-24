package org.serratec.ecommerce.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.serratec.ecommerce.domain.Produto;
import org.serratec.ecommerce.dto.ProdutoDTO;
import org.serratec.ecommerce.exception.ProdutoException;
import org.serratec.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoProdutoService fotoProdutoService;
	
	public List<ProdutoDTO> obterTodos() {
		List<ProdutoDTO> produtosDTO = new ArrayList<ProdutoDTO>();
        List<Produto> produtos = produtoRepository.findAll();
        
        for(Produto produto : produtos) {
        	ProdutoDTO produtoDTO = new ProdutoDTO(produto);
            produtosDTO.add(produtoDTO);
        }
        return produtosDTO;
	}

	public Produto buscar(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent()) {
			return produto.get();
		}
		return null;
	}
	
	public ProdutoDTO adicionarFotoUrl(Produto produto) throws ProdutoException {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/produto/{id}/foto")
				.buildAndExpand(produto.getId()).toUri();

		System.out.println("URI" + uri);
		
		Optional<Produto> produtoCriado = produtoRepository.findByNome(produto.getNome());
		if(produtoCriado.isPresent()) {
			throw new ProdutoException("Produto " + produto.getNome() + " já cadastrado.");
		}
		
		ProdutoDTO produtoDTO = new ProdutoDTO();
		produtoDTO.setNome(produto.getNome());
		produtoDTO.setDescricao(produto.getDescricao());
		produtoDTO.setQtdEstoque(produto.getQtdEstoque());
		produtoDTO.setValor(produto.getValor());
		produtoDTO.setFotoProduto(uri.toString());
		return produtoDTO;
	}

	public ProdutoDTO criar(MultipartFile file, Produto produto) throws IOException {
		fotoProdutoService.inserir(produtoRepository.save(produto), file);
		try {
			return adicionarFotoUrl(produto);
		} catch (ProdutoException e) {
			e.getMessage();
			return null;
		}
	}

	public ProdutoDTO atualizar(Long id, @Valid Produto produto) {
		if (!produtoRepository.existsById(id)) {
            return null;
        }
        produto.setId(id);
        produto = produtoRepository.save(produto);
        return new ProdutoDTO(produto);
	}

	public boolean deletar(Long id) {
		if(produtoRepository.existsById(id)) {
			produtoRepository.deleteById(id);
			return true;			
		}
		return false;
	}
}
