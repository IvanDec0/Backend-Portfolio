package com.portfolio.security.service;

import com.portfolio.entity.Experience;
import com.portfolio.entity.Project;
import com.portfolio.security.repository.UsuarioRepository;
import com.portfolio.security.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> list(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public void delete(int id){
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> getOne(int id){
        return usuarioRepository.findById(id);
    }
    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsById(int id){
        return usuarioRepository.existsById(id);
    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }
}
