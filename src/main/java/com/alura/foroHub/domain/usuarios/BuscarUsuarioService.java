package com.alura.foroHub.domain.usuarios;


import com.alura.foroHub.domain.topicos.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    public UserDetails buscarUsuarioPorTopico(Long idTopico) {
        var topico = topicoRepository.getReferenceById(idTopico);
        return usuarioRepository.findByCorreoElectronico(topico.getAutor().getUsername());
    }
}
