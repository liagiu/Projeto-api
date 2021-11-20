package org.serratec.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.serratec.ecommerce.domain.Cliente;
import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.dto.ClienteLogadoDTO;
import org.serratec.ecommerce.exception.EmailException;
import org.serratec.ecommerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
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

    public ClienteDTO criar(ClienteDTO clienteDTO) throws EmailException {
    	Optional<Cliente> novoCliente = clienteRepository.findByEmail(clienteDTO.getEmail());
        
    	if (novoCliente.isPresent()) {
            throw new EmailException("E-mail já cadastrado.");
        }
        
        Cliente cliente = new Cliente();
            cliente.setNome(clienteDTO.getNome());
            cliente.setSobrenome(clienteDTO.getSobrenome());
            cliente.setDataNascimento(clienteDTO.getDataNascimento());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setSenha(passwordEncoder.encode(clienteDTO.getSenha()));
            cliente = clienteRepository.save(cliente);
        
        return new ClienteDTO(cliente);
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