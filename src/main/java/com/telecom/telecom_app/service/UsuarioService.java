package com.telecom.telecom_app.service;

import com.telecom.telecom_app.exception.ResourceInUseException;
import com.telecom.telecom_app.model.Usuario;
import com.telecom.telecom_app.repository.UsuarioRepository;
import com.telecom.telecom_app.repository.VendedorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final VendedorRepository vendedorRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          VendedorRepository vendedorRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.vendedorRepository = vendedorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow();
    }

    public Usuario crear(Usuario usuario) {
        usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        Usuario usuario = obtenerPorId(id);
        usuario.setUsername(usuarioActualizado.getUsername());
        if (usuarioActualizado.getPasswordHash() != null && !usuarioActualizado.getPasswordHash().isBlank()) {
            usuario.setPasswordHash(passwordEncoder.encode(usuarioActualizado.getPasswordHash()));
        }
        usuario.setRol(usuarioActualizado.getRol());
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new java.util.NoSuchElementException();
        }
        if (vendedorRepository.existsByUsuario_Id(id)) {
            throw new ResourceInUseException("El usuario esta asociado a un vendedor");
        }
        usuarioRepository.deleteById(id);
    }
}
