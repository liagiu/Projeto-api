package org.serratec.ecommerce.dto;

import java.util.List;

import org.serratec.ecommerce.domain.Categoria;
import org.serratec.ecommerce.domain.Produto;

public class CategoriaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private List<Produto> produtos;

    public CategoriaDTO() {
    }
    
    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
        this.produtos = categoria.getProdutos();
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

	public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
