package org.serratec.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.serratec.ecommerce.domain.Pedido;
import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

        @Autowired
        private PedidoRepository pedidoRepository;

        public List<PedidoDTO> obterTodos() {
    		List<PedidoDTO> pedidosDTO = new ArrayList<PedidoDTO>();
    		List<Pedido> pedidos = pedidoRepository.findAll();
    		
    		for (Pedido pedido : pedidos) {
    			PedidoDTO dto = new PedidoDTO(pedido); 
    			pedidosDTO.add(dto); 
    		}
    		
    		return pedidosDTO;
    	}

        public Pedido buscar(Long id) {
        	Optional<Pedido> pedido = pedidoRepository.findById(id);
    		if (pedido.isPresent()) {
    			return pedido.get();
    		}
    		return null;
        }

        public PedidoDTO criar(@Valid Pedido pedido) {
        	pedido = pedidoRepository.save(pedido);
			return new PedidoDTO(pedido);
        }

        public PedidoDTO atualizar(Long id, @Valid Pedido pedido) {
        	if (!pedidoRepository.existsById(id)) {
                return null;
            }
        	pedido.setId(id);
        	pedido = pedidoRepository.save(pedido);
            return new PedidoDTO(pedido);
        }

        public Boolean deletar(Long id) {
            if(pedidoRepository.existsById(id)) {
                pedidoRepository.deleteById(id);;
                return true;
            }
            return false;
        }
}
