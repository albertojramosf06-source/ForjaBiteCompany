package es.uclm.rental_platform.service;

import es.uclm.rental_platform.domain.Usuario;
import es.uclm.rental_platform.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrarUsuario(String email, String nombre, String apellidos, String dni) {
        if (estaVacio(email) || estaVacio(nombre) || estaVacio(apellidos) || estaVacio(dni)) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email.");
        }

        Usuario usuario = new Usuario(email, nombre, apellidos, dni);
        return usuarioRepository.save(usuario);
    }

    private boolean estaVacio(String valor) {
        return valor == null || valor.isBlank();
    }
}
