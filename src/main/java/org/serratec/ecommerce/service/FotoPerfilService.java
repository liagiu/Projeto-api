package org.serratec.ecommerce.service;

import java.io.IOException;
import java.util.Optional;

import org.serratec.ecommerce.domain.Cliente;
import org.serratec.ecommerce.domain.FotoPerfil;
import org.serratec.ecommerce.repository.FotoPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoPerfilService {
    @Autowired
    private FotoPerfilRepository fotoPerfilRepository;

    public FotoPerfil inserir(Cliente cliente, MultipartFile file) throws IOException {
    	FotoPerfil foto = new FotoPerfil();
        foto.setDados(file.getBytes());
        foto.setNome(file.getName());
        foto.setTipo(file.getContentType());
        foto.setCliente(cliente);
        return fotoPerfilRepository.save(foto);
    }

    public FotoPerfil buscar(Long id) {
        Optional<FotoPerfil> foto = fotoPerfilRepository.findById(id);
        if(foto.isPresent()) {
            return foto.get();
        }else {
            return null;
        }
    }
}
