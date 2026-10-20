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
                "ana@example.com", "Ana", "García López", "DNI", "ES", "12345678Z");

        assertNotNull(usuario.getId());
        Usuario recuperado = usuarioRepository.findById(usuario.getId()).orElseThrow();
        assertEquals("ana@example.com", recuperado.getEmail());
        assertEquals("Ana", recuperado.getNombre());
        assertEquals("García López", recuperado.getApellidos());
        assertEquals("12345678Z", recuperado.getNumeroDocumento());
    }

    @Test
    void rechazarCadaCampoObligatorioVacio() {
        String[] valoresVacios = {null, "", "   "};

        for (String valorVacio : valoresVacios) {
            assertThrows(IllegalArgumentException.class, () ->
                    usuarioService.registrarUsuario(valorVacio, "Ana", "García", "DNI", "ES", "12345678Z"));
            assertThrows(IllegalArgumentException.class, () ->
                    usuarioService.registrarUsuario("ana@example.com", valorVacio, "García", "DNI", "ES", "12345678Z"));
            assertThrows(IllegalArgumentException.class, () ->
                    usuarioService.registrarUsuario("ana@example.com", "Ana", valorVacio, "DNI", "ES", "12345678Z"));
            assertThrows(IllegalArgumentException.class, () ->
                    usuarioService.registrarUsuario("ana@example.com", "Ana", "García", "DNI", "ES", valorVacio));
        }

        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void rechazarEmailDuplicado() {
        usuarioService.registrarUsuario("ana@example.com", "Ana", "García", "DNI", "ES", "12345678Z");

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.registrarUsuario("ana@example.com", "Otra", "Persona", "DNI", "ES", "87654321X"));

        assertEquals("Ya existe un usuario con ese email.", error.getMessage());
        assertEquals(1, usuarioRepository.count());
    }

    @Test
    void normalizarEmailNombreYDocumento() {
        Usuario usuario = usuarioService.registrarUsuario(
                "  ANA@Example.COM  ", "  Ana  ", " O'Neill-García ", "DNI", "", " 12345678z ");
        assertEquals("ana@example.com", usuario.getEmail());
        assertEquals("Ana", usuario.getNombre());
        assertEquals("O'Neill-García", usuario.getApellidos());
        assertEquals("12345678Z", usuario.getNumeroDocumento());
        assertEquals("ES", usuario.getPaisEmisor());
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                "ANA@EXAMPLE.COM", "Otra", "Persona", "DNI", "ES", "87654321X"));
        assertEquals(1, usuarioRepository.count());
    }

    @Test
    void rechazarEmailsMalFormados() {
        for (String email : new String[]{"ana", "ana@", "@example.com", "ana@@example.com",
                "ana a@example.com", "ana..a@example.com", "ana@-dominio.com", "ana@example.com."}) {
            assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                    email, "Ana", "García", "DNI", "ES", "12345678Z"));
        }
        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void respetarLimitesDeNombreYApellidos() {
        Usuario usuario = usuarioService.registrarUsuario("ana@example.com", "a".repeat(255),
                "b".repeat(255), "DNI", "ES", "12345678Z");
        assertEquals(255, usuario.getNombre().length());
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                "otra@example.com", "a".repeat(256), "García", "DNI", "ES", "87654321X"));
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                "otra@example.com", "Ana", "b".repeat(256), "DNI", "ES", "87654321X"));
    }

    @Test
    void rechazarEmailDemasiadoLargo() {
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                "a".repeat(256) + "@example.com", "Ana", "García", "DNI", "ES", "12345678Z"));
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                "a".repeat(65) + "@example.com", "Ana", "García", "DNI", "ES", "12345678Z"));
        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void rechazarDniIncorrectoYDuplicado() {
        for (String dni : new String[]{"12345678A", "1234567Z", "123456789Z", "X1234567L", "1234 678Z"}) {
            assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                    "ana@example.com", "Ana", "García", "DNI", "ES", dni));
        }
        usuarioService.registrarUsuario("ana@example.com", "Ana", "García", "DNI", "ES", "12345678Z");
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                "otra@example.com", "Otra", "Persona", "DNI", "FR", "12345678z"));
        assertEquals(1, usuarioRepository.count());
    }

    @Test
    void pasaporteNormalizadoYUnicoPorPais() {
        Usuario usuario = usuarioService.registrarUsuario("ana@example.com", "Ana", "García",
                "PASAPORTE", "fr", " ab12345 ");
        assertEquals("AB12345", usuario.getNumeroDocumento());
        assertEquals("FR", usuario.getPaisEmisor());
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                "otra@example.com", "Otra", "Persona", "PASAPORTE", "FR", "ab12345"));
        usuarioService.registrarUsuario("otra@example.com", "Otra", "Persona", "PASAPORTE", "DE", "AB12345");
        assertEquals(2, usuarioRepository.count());
    }

    @Test
    void rechazarPasaporteSinPaisOConCaracteresInvalidos() {
        for (String pais : new String[]{null, "", "   ", "XX", "Francia"}) {
            assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                    "ana@example.com", "Ana", "García", "PASAPORTE", pais, "AB12345"));
        }
        for (String numero : new String[]{"AB 123", "AB-123", "Á123", "A".repeat(33)}) {
            assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                    "ana@example.com", "Ana", "García", "PASAPORTE", "FR", numero));
        }
        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void aceptarLimitesDePasaporteYRechazarTipoDesconocido() {
        usuarioService.registrarUsuario("a@example.com", "Ana", "García", "PASAPORTE", "FR", "A");
        usuarioService.registrarUsuario("b@example.com", "Ana", "García", "PASAPORTE", "FR", "B".repeat(32));
        assertThrows(IllegalArgumentException.class, () -> usuarioService.registrarUsuario(
                "c@example.com", "Ana", "García", "NIE", "ES", "X1234567L"));
        assertEquals(2, usuarioRepository.count());
    }

}
