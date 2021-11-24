package org.serratec.ecommerce.dto;

import org.serratec.ecommerce.domain.Produto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ItemPedidoDTO {
	private Integer quantidade;
	private Double precoVenda;
	private Double total;
	private Produto produto;
	
	@JsonIgnore
	private Long id_pedido;
	
	public ItemPedidoDTO() {
	
	}

	public ItemPedidoDTO(Integer quantidade, Double precoVenda, Double total, Produto produto, Long id_pedido) {
		this.quantidade = quantidade;
		this.precoVenda = precoVenda;
		this.total = total;
		this.produto = produto;
		this.id_pedido = id_pedido;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}
}
