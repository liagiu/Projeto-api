package org.serratec.ecommerce.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.serratec.ecommerce.domain.Cliente;
import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.dto.ClienteLogadoDTO;
import org.serratec.ecommerce.exception.ClienteException;
import org.serratec.ecommerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
	private FotoPerfilService fotoPerfilService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<ClienteLogadoDTO> obterTodos(){
        List<ClienteLogadoDTO> clientesDTO = new ArrayList<ClienteLogadoDTO>();
        List<Cliente> clientes = clienteRepository.findAll();
        
        for(Cliente cliente : clientes) {
            ClienteLogadoDTO clienteDTO = new ClienteLogadoDTO(cliente);
            clientesDTO.add(clienteDTO);
        }
        return clientesDTO;
    } 

    public ClienteLogadoDTO buscar(Long id) {
    	Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			return new ClienteLogadoDTO(cliente.get());
		}
		return null;
    }
    
    public ClienteDTO adicionarFotoUrl(Cliente cliente) throws ClienteException {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/cliente/{id}/foto")
				.buildAndExpand(cliente.getId()).toUri();

		System.out.println("URI" + uri);
		
		Optional<Cliente> clienteEmail  = clienteRepository.findByEmail(cliente.getEmail());
    	Optional<Cliente> clienteCpf  = clienteRepository.findByCpf(cliente.getCpf());
    	Optional<Cliente> clienteNomeUsuario  = clienteRepository.findByNomeUsuario(cliente.getNomeUsuario());
    	
    	if (clienteEmail.isPresent()) {
            throw new ClienteException("E-mail já cadastrado.");
        } else if (clienteCpf.isPresent()) {
            throw new ClienteException("CPF já cadastrado.");
        } else if (clienteNomeUsuario.isPresent()) {
            throw new ClienteException("Nome de usuário já cadastrado.");
        }
		
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setNome(cliente.getNome());
		clienteDTO.setSobrenome(cliente.getSobrenome());
		clienteDTO.setDataNascimento(cliente.getDataNascimento());
		clienteDTO.setEmail(cliente.getEmail());
		clienteDTO.setCpf(cliente.getCpf());
		clienteDTO.setEndereco(cliente.getEndereco());
		clienteDTO.setNomeUsuario(cliente.getNomeUsuario());
		clienteDTO.setSenha(passwordEncoder.encode(cliente.getSenha()));
		clienteDTO.setFotoPerfil(uri.toString());
		
		return clienteDTO;
	}

    public ClienteDTO criar(MultipartFile file, Cliente cliente) throws IOException {
    	fotoPerfilService.inserir(clienteRepository.save(cliente), file);
		try {
			return adicionarFotoUrl(cliente);
		} catch (ClienteException e) {
			e.getMessage();
			return null;
		}
    }

    public ClienteDTO atualizar(Long id, @Valid Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            return null;
        }
        cliente.setId(id);
        cliente = clienteRepository.save(cliente);
        return new ClienteDTO(cliente);
    }

    public boolean deletar(Long id) {
        if(clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;            
        }
        return false;
    }
}