package org.serratec.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.serratec.ecommerce.domain.ItemPedido;
import org.serratec.ecommerce.domain.Pedido;
import org.serratec.ecommerce.domain.Produto;
import org.serratec.ecommerce.dto.ItemPedidoDTO;
import org.serratec.ecommerce.dto.ItemPedidoInserirDTO;
import org.serratec.ecommerce.exception.ItemPedidoException;
import org.serratec.ecommerce.exception.PedidoException;
import org.serratec.ecommerce.repository.ItemPedidoRepository;
import org.serratec.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemPedidoService {
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<ItemPedido> salvarLista (List<ItemPedidoInserirDTO> itemPedidoInserirDTOLista, Pedido pedido) throws ItemPedidoException, PedidoException {
		List<ItemPedido> itens = new ArrayList<>();
			for(ItemPedidoInserirDTO itemPedidoDTO : itemPedidoInserirDTOLista) {
				ItemPedido itemPedido = new ItemPedido();
				Produto produto = produtoService.buscar(itemPedidoDTO.getId_produto());
				
				if (itemPedidoDTO.getQuantidade() > produto.getQtdEstoque()) {
					throw new ItemPedidoException("Quantidade indisponível!");
				}
				
				itemPedido.setTotal(itemPedidoDTO.getQuantidade() * produto.getValor());
				produto.setQtdEstoque(produto.getQtdEstoque() - itemPedidoDTO.getQuantidade());
				itemPedido.setValor(produto.getValor());
				itemPedido.setPedido(pedido);
				itemPedido.setProduto(produto);
				itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
				itemPedidoRepository.save(itemPedido);
				itens.add(itemPedido);
			}
			return itens;
	}
	
	public List<ItemPedidoDTO> listar() {
		List<ItemPedidoDTO> itemPedidoDTOLista = new ArrayList<>();
		List<ItemPedido> ItemPedidoLista = itemPedidoRepository.findAll();
		ItemPedido itemPedido = new ItemPedido();
		
		for(int i = 0; i < ItemPedidoLista.size(); i++) {
			ItemPedidoDTO DTO = new ItemPedidoDTO();
			itemPedido = ItemPedidoLista.get(i);
			DTO.setId_pedido(itemPedido.getPedido().getId());
			DTO.setProduto(itemPedido.getProduto());
			DTO.setPrecoVenda(itemPedido.getValor());
			DTO.setQuantidade(itemPedido.getQuantidade());
			DTO.setTotal(itemPedido.getTotal());
			itemPedidoDTOLista.add(DTO);
		}
		return itemPedidoDTOLista;
	}
	
	public void reestocar(ItemPedido itemPedido) {
		Produto produto = itemPedido.getProduto();
		Integer quantidade = itemPedido.getQuantidade() + produto.getQtdEstoque();
		produto.setQtdEstoque(quantidade);
		produtoRepository.save(produto);
	}
	
	public Boolean deletar(Long id) {
		Pedido pedido = pedidoService.buscar(id);
		List<ItemPedido> lista  =  itemPedidoRepository.findAllByPedido(pedido);
		itemPedidoRepository.deleteAll(lista);
		return true;
	}
}
