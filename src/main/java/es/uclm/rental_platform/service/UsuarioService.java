package es.uclm.rental_platform.service;

import es.uclm.rental_platform.domain.Usuario;
import es.uclm.rental_platform.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class UsuarioService {
    private static final Pattern EMAIL = Pattern.compile(
            "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?"
            + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?)+");
    private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrarUsuario(String email, String nombre, String apellidos,
                                   String tipoDocumento, String paisEmisor, String numeroDocumento) {
        if (estaVacio(email) || estaVacio(nombre) || estaVacio(apellidos)
                || estaVacio(tipoDocumento) || estaVacio(numeroDocumento)) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
        email = email.strip().toLowerCase(Locale.ROOT);
        nombre = nombre.strip();
        apellidos = apellidos.strip();
        numeroDocumento = numeroDocumento.strip().toUpperCase(Locale.ROOT);

        comprobarLongitud(email, 255, "El email");
        comprobarLongitud(nombre, 255, "El nombre");
        comprobarLongitud(apellidos, 255, "Los apellidos");
        comprobarLongitud(numeroDocumento, 32, "El documento");
        if (!EMAIL.matcher(email).matches() || email.indexOf('@') > 64) {
            throw new IllegalArgumentException("Introduce un email válido, por ejemplo nombre@dominio.com.");
        }

        if ("DNI".equals(tipoDocumento)) {
            paisEmisor = "ES";
            if (!numeroDocumento.matches("[0-9]{8}[A-Z]")
                    || LETRAS_DNI.charAt(Integer.parseInt(numeroDocumento.substring(0, 8)) % 23)
                    != numeroDocumento.charAt(8)) {
                throw new IllegalArgumentException("El DNI debe tener ocho cifras y una letra de control correcta.");
            }
        } else if ("PASAPORTE".equals(tipoDocumento)) {
            if (estaVacio(paisEmisor)) {
                throw new IllegalArgumentException("Selecciona el país emisor del pasaporte.");
            }
            paisEmisor = paisEmisor.strip().toUpperCase(Locale.ROOT);
            if (!Arrays.asList(Locale.getISOCountries()).contains(paisEmisor)) {
                throw new IllegalArgumentException("Selecciona un país emisor válido.");
            }
            // Regla básica del proyecto, no una validación oficial por país.
            if (!numeroDocumento.matches("[A-Z0-9]{1,32}")) {
                throw new IllegalArgumentException("El pasaporte debe contener entre 1 y 32 letras o cifras, sin espacios ni signos.");
            }
        } else {
            throw new IllegalArgumentException("Selecciona DNI español o pasaporte.");
        }

        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email.");
        }
        if (usuarioRepository.existsByTipoDocumentoAndPaisEmisorAndNumeroDocumento(
                tipoDocumento, paisEmisor, numeroDocumento)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese documento y país emisor.");
        }
        return usuarioRepository.saveAndFlush(
                new Usuario(email, nombre, apellidos, tipoDocumento, paisEmisor, numeroDocumento));
    }

    private boolean estaVacio(String valor) {
        return valor == null || valor.isBlank();
    }

    private void comprobarLongitud(String valor, int maximo, String campo) {
        if (valor.length() > maximo) {
            throw new IllegalArgumentException(campo + " no puede superar " + maximo + " caracteres.");
        }
    }
}
