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
	private Endereco endereco;
	private List<Pedido> pedidos;

	public ClienteLogadoDTO() {
	}

	public ClienteLogadoDTO(String nome, String sobrenome, LocalDate dataNascimento, String email, Endereco endereco,
			List<Pedido> pedidos) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.endereco = endereco;
		this.pedidos = pedidos;
	}

	public ClienteLogadoDTO(Cliente cliente) {
		this.nome = cliente.getNome();
		this.sobrenome = cliente.getSobrenome();
		this.dataNascimento = cliente.getDataNascimento();
		this.email = cliente.getEmail();
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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