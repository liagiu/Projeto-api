package org.serratec.ecommerce.service;

import java.io.IOException;
import java.util.Optional;

import org.serratec.ecommerce.domain.FotoProduto;
import org.serratec.ecommerce.domain.Produto;
import org.serratec.ecommerce.repository.FotoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoProdutoService {
    @Autowired
    private FotoProdutoRepository fotoProdutoRepository;

    public FotoProduto inserir(Produto produto, MultipartFile file) throws IOException {
    	FotoProduto foto = new FotoProduto();
        foto.setDados(file.getBytes());
        foto.setNome(file.getName());
        foto.setTipo(file.getContentType());
        foto.setProduto(produto);
        return fotoProdutoRepository.save(foto);
    }

    public FotoProduto buscar(Long id) {
        Optional<FotoProduto> foto = fotoProdutoRepository.findById(id);
        if(foto.isPresent()) {
            return foto.get();
        }else {
            return null;
        }
    }
}
