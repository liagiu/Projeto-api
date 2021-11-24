package org.serratec.ecommerce.dto;

import java.time.LocalDate;

import org.serratec.ecommerce.domain.Cliente;
import org.serratec.ecommerce.domain.Endereco;

public class ClienteDTO {
	private Long id;
	private String nome;
	private String sobrenome;
	private LocalDate dataNascimento;
	private String email;
	private String cpf;
	private String nomeUsuario;
	private String senha;
	private String fotoPerfil;
	private Endereco endereco;
	
	public ClienteDTO() {
		
	}

	public ClienteDTO(Long id, String nome, String sobrenome, LocalDate dataNascimento, String email, String cpf,
			String nomeUsuario, String senha, String fotoPerfil, Endereco endereco) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.cpf = cpf;
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
		this.fotoPerfil = fotoPerfil;
		this.endereco = endereco;
	}



	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.sobrenome = cliente.getSobrenome();
		this.dataNascimento = cliente.getDataNascimento();
		this.email = cliente.getEmail();
		this.cpf = cliente.getCpf();
		this.senha = cliente.getSenha();  
		this.endereco = cliente.getEndereco();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
