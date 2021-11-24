package org.serratec.ecommerce.dto;

import org.serratec.ecommerce.domain.Produto;

public class ProdutoDTO {
	
	private Long id;
	private String nome;
	private String descricao;
	private Double valor;
	private Integer qtdEstoque;
	private String fotoProduto;
	private CategoriaDTO categoriaDTO;
	
	public ProdutoDTO() {
		
	}
	
	public ProdutoDTO(Long id, String nome, String descricao, Double valor, Integer qtdEstoque, String fotoProduto,
			CategoriaDTO categoriaDTO) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.valor = valor;
		this.qtdEstoque = qtdEstoque;
		this.fotoProduto = fotoProduto;
		this.categoriaDTO = categoriaDTO;
	}

	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.valor = produto.getValor();
		this.qtdEstoque = produto.getQtdEstoque();
		this.categoriaDTO = new CategoriaDTO(produto.getCategoria());
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public String getFotoProduto() {
		return fotoProduto;
	}

	public void setFotoProduto(String fotoProduto) {
		this.fotoProduto = fotoProduto;
	}

	public CategoriaDTO getCategoriaDTO() {
		return categoriaDTO;
	}

	public void setCategoriaDTO(CategoriaDTO categoriaDTO) {
		this.categoriaDTO = categoriaDTO;
	}
}
