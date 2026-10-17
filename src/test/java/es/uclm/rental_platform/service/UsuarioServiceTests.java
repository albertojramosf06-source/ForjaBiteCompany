package es.uclm.rental_platform.service;

import es.uclm.rental_platform.domain.Usuario;
import es.uclm.rental_platform.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:usuarios_service_test;DB_CLOSE_ON_EXIT=FALSE")
@Transactional
class UsuarioServiceTests {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void registrarUsuarioConDatosCorrectos() {
        Usuario usuario = usuarioService.registrarUsuario(
                "ana@example.com", "Ana", "García López", "12345678Z");

        assertNotNull(usuario.getId());
        Usuario recuperado = usuarioRepository.findById(usuario.getId()).orElseThrow();
        assertEquals("ana@example.com", recuperado.getEmail());
        assertEquals("Ana", recuperado.getNombre());
        assertEquals("García López", recuperado.getApellidos());
        assertEquals("12345678Z", recuperado.getDni());
    }

    @Test
    void rechazarCadaCampoObligatorioVacio() {
        String[] valoresVacios = {null, "", "   "};

        for (String valorVacio : valoresVacios) {
            assertThrows(IllegalArgumentException.class, () ->
                    usuarioService.registrarUsuario(valorVacio, "Ana", "García", "12345678Z"));
            assertThrows(IllegalArgumentException.class, () ->
                    usuarioService.registrarUsuario("ana@example.com", valorVacio, "García", "12345678Z"));
            assertThrows(IllegalArgumentException.class, () ->
                    usuarioService.registrarUsuario("ana@example.com", "Ana", valorVacio, "12345678Z"));
            assertThrows(IllegalArgumentException.class, () ->
                    usuarioService.registrarUsuario("ana@example.com", "Ana", "García", valorVacio));
        }

        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void rechazarEmailDuplicado() {
        usuarioService.registrarUsuario("ana@example.com", "Ana", "García", "12345678Z");

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.registrarUsuario("ana@example.com", "Otra", "Persona", "87654321X"));

        assertEquals("Ya existe un usuario con ese email.", error.getMessage());
        assertEquals(1, usuarioRepository.count());
    }
}
