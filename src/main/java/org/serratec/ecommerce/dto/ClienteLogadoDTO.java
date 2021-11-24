package org.serratec.ecommerce.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.ecommerce.domain.Cliente;
import org.serratec.ecommerce.domain.Endereco;
import org.serratec.ecommerce.domain.Pedido;

public class ClienteLogadoDTO {
	private String nome;
	private String sobrenome;
	private LocalDate dataNascimento;
	private String email;
	private String nomeUsuario;
	private Endereco endereco;
	private String fotoPerfil;
	private List<Pedido> pedidos;

	public ClienteLogadoDTO() {
	}

	public ClienteLogadoDTO(String nome, String sobrenome, LocalDate dataNascimento, String email, String nomeUsuario, Endereco endereco, String fotoPerfil,
			List<Pedido> pedidos) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.nomeUsuario = nomeUsuario;
		this.endereco = endereco;
		this.fotoPerfil = fotoPerfil;
		this.pedidos = pedidos;
	}

	public ClienteLogadoDTO(Cliente cliente) {
		this.nome = cliente.getNome();
		this.sobrenome = cliente.getSobrenome();
		this.dataNascimento = cliente.getDataNascimento();
		this.email = cliente.getEmail();
		this.nomeUsuario = cliente.getNomeUsuario();
		this.endereco = cliente.getEndereco();
		this.pedidos = cliente.getPedidos();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	public List<ClienteLogadoDTO> convert (List<Cliente> clientes) {
		List<ClienteLogadoDTO> clienteLogDTO = new ArrayList<>();
		for (Cliente cliente : clientes) {
			ClienteLogadoDTO clienteLogadoDTO = new ClienteLogadoDTO(cliente);
			clienteLogDTO.add(clienteLogadoDTO);	
		}
		
		return clienteLogDTO;
	}
}