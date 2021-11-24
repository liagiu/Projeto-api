package org.serratec.ecommerce.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    @ApiModelProperty(value = "Identificador único do produto")
    private Long id;
    
    @NotBlank(message = "Nome não pode ser vazio" )
    @ApiModelProperty(value = "Nome do produto", required = true)
    private String nome;
    
    @ApiModelProperty(value = "Descrição do produto", required = true)
    private String descricao;
    
	@PositiveOrZero(message = "Insira um valor válido")
    @NotBlank(message = "Valor não pode ser vazio" )
    @Min(value = 0, message = "Valor não pode ser negativo")
    @ApiModelProperty(value = "Valor unitário do produto", required = true)
    private Double valor;
    
	@NotBlank(message = "Quantidade em estoque não pode ser vazio")
	@Column(name = "qtd_estoque")
	@ApiModelProperty(value = "Quantidade de estoque do produto")
	private Integer qtdEstoque;
	
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    
    public Produto() {    
    }
    
    public Produto(Long id, String nome, String descricao, Double valor, Integer qtdEstoque, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.qtdEstoque = qtdEstoque;
        this.categoria = categoria;
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

	public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
        Produto other = (Produto) obj;
        return Objects.equals(id, other.id);
    }
}
