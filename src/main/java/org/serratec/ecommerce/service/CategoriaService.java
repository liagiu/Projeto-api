package org.serratec.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.serratec.ecommerce.domain.Categoria;
import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.exception.CategoriaException;
import org.serratec.ecommerce.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<CategoriaDTO> obterTodos() {
		List<CategoriaDTO> categoriasDTO = new ArrayList<CategoriaDTO>();
		List<Categoria> categorias = categoriaRepository.findAll();
		
		for (Categoria cat : categorias) {
			CategoriaDTO dto = new CategoriaDTO(cat); 
			categoriasDTO.add(dto); 
		}
		
		return categoriasDTO;
	}

	public CategoriaDTO buscar(String nome) {
		Optional<Categoria> categoria = categoriaRepository.findByNome(nome);
		if (categoria.isPresent()) {
			return new CategoriaDTO(categoria.get());
		}
		return null;
	}

	public CategoriaDTO criar(@Valid Categoria categoria) throws CategoriaException {
		Optional<Categoria> categoriaCriada = categoriaRepository.findByNome(categoria.getNome());
		if(!categoriaCriada.isPresent()) {
			categoria = categoriaRepository.save(categoria);
			return new CategoriaDTO(categoria);
		}
		throw new CategoriaException("Categoria " + categoria.getNome() + " já cadastrada.");	
	}

	public CategoriaDTO atualizar(String nome, @Valid Categoria categoria) throws CategoriaException {
		if(!categoriaRepository.existsByNome(nome)) {
			throw new CategoriaException("A categoria " + nome + " não existe.");		
		}		
		Long id = categoriaRepository.findByNome(nome).get().getId();
		categoria.setId(id);
		return new CategoriaDTO(categoriaRepository.save(categoria));
	}
	
	public Boolean deletar(String nome) {
		if(categoriaRepository.existsByNome(nome)) {
			categoriaRepository.deleteByNome(nome);
			return true;			
		}
		return false;
	}
}