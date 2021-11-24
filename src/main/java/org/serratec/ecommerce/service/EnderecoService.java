package org.serratec.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.serratec.ecommerce.domain.Endereco;
import org.serratec.ecommerce.dto.EnderecoDTO;
import org.serratec.ecommerce.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<EnderecoDTO> obterTodos() {
		List<EnderecoDTO> enderecosDTO = new ArrayList<EnderecoDTO>();
		List<Endereco> enderecos = enderecoRepository.findAll();
		
		for (Endereco endereco : enderecos) {
			EnderecoDTO dto = new EnderecoDTO(endereco); 
			enderecosDTO.add(dto); 
		}
		
		return enderecosDTO;
	}

    public EnderecoDTO buscar(Long id) {
    	Optional<Endereco> endereco = enderecoRepository.findById(id);
		if (endereco.isPresent()) {
			return new EnderecoDTO(endereco.get());
		}
		return null;
    }

    public EnderecoDTO criar(@Valid Endereco endereco) {
    	endereco = enderecoRepository.save(endereco);
		return new EnderecoDTO(endereco);
    }

    public EnderecoDTO atualizar(Long id, @Valid Endereco endereco) {
    	if (!enderecoRepository.existsById(id)) {
            return null;
        }
    	endereco.setId(id);
    	endereco = enderecoRepository.save(endereco);
        return new EnderecoDTO(endereco);
    }

    public Boolean deletar(Long id) {
        if(enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);;
            return true;
        }
        return false;
    }
}
