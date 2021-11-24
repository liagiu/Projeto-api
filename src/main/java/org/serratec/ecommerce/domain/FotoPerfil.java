package org.serratec.ecommerce.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import io.swagger.annotations.ApiModelProperty;

public class FotoPerfil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_foto")
	@ApiModelProperty(value = "Identificador único da foto")
	private Long id;
	
	@Lob
	@ApiModelProperty(value = "Define os dados de uma foto ")
	private byte[] dados;
	
	@ApiModelProperty(value = "Define o nome de uma foto ")
	private String nome;
	
	@ApiModelProperty(value = "Define o tipo de uma foto ")
	private String tipo;
	
	@OneToOne
	@JoinColumn(name = "id_cliente")
	@ApiModelProperty(value = "ID referente ao cliente da foto")
	private Cliente cliente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getDados() {
		return dados;
	}

	public void setDados(byte[] dados) {
		this.dados = dados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FotoPerfil other = (FotoPerfil) obj;
		return Objects.equals(id, other.id);
	}
}
