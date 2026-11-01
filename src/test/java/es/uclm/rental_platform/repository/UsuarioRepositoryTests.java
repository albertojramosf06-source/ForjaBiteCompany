package es.uclm.rental_platform.repository;

import es.uclm.rental_platform.domain.Usuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue; //Añadido nuevo para #12

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:usuarios_test;DB_CLOSE_ON_EXIT=FALSE")
@Transactional
class UsuarioRepositoryTests {

    //Añadido nuevo para #12
    private static final String CONTRASENA = "contrasenaCifrada";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void guardarYRecuperarUsuario() {
        //Añadido para #11: ''inquilino
        Usuario usuario = new Usuario("ana@example.com", "Ana", "García López", "DNI", "ES", "12345678Z", "INQUILINO", CONTRASENA);

        Usuario guardado = usuarioRepository.saveAndFlush(usuario);
        Long id = guardado.getId();
        assertNotNull(id);

        // Vaciar la caché de JPA obliga a recuperar los datos desde H2.
        entityManager.clear();
        Usuario recuperado = usuarioRepository.findById(id).orElseThrow();

        assertEquals(id, recuperado.getId());
        assertEquals("ana@example.com", recuperado.getEmail());
        assertEquals("Ana", recuperado.getNombre());
        assertEquals("García López", recuperado.getApellidos());
        assertEquals("12345678Z", recuperado.getNumeroDocumento());
    }

    @Test
    void baseDeDatosImpideEmailRepetido() {
        //Añadido para #11: ''inquilino
        usuarioRepository.saveAndFlush(new Usuario("ana@example.com", "Ana", "García", "DNI", "ES", "12345678Z", "INQUILINO", CONTRASENA));
        org.junit.jupiter.api.Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class,
                () -> usuarioRepository.saveAndFlush(new Usuario("ana@example.com", "Otra", "Persona", "DNI", "ES", "87654321X", "INQUILINO", CONTRASENA)));
    }

    @Test
    void baseDeDatosImpideDocumentoRepetido() {
        //Añadido para #11: ''inquilino
        usuarioRepository.saveAndFlush(new Usuario("ana@example.com", "Ana", "García", "PASAPORTE", "FR", "AB12345", "INQUILINO", CONTRASENA));
        org.junit.jupiter.api.Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class,
                () -> usuarioRepository.saveAndFlush(new Usuario("otra@example.com", "Otra", "Persona", "PASAPORTE", "FR", "AB12345", "INQUILINO", CONTRASENA)));
    }


    @Test
    void baseDeDatosImpideDniRepetido() {
        //Añadido para #11: ''inquilino
        usuarioRepository.saveAndFlush(new Usuario("ana@example.com", "Ana", "García", "DNI", "ES", "12345678Z", "INQUILINO", CONTRASENA));
        org.junit.jupiter.api.Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class,
                () -> usuarioRepository.saveAndFlush(new Usuario("otra@example.com", "Otra", "Persona", "DNI", "ES", "12345678Z", "INQUILINO", CONTRASENA)));
    }

    @Test
    //Añadido para #11
    void guardarYRecuperarPropietarioConSuRol() {
        Usuario guardado = usuarioRepository.saveAndFlush(
                new Usuario("pedro@example.com", "Pedro", "Sánchez", "DNI", "ES", "12345678Z", "PROPIETARIO", CONTRASENA));

        entityManager.clear();
        Usuario recuperado = usuarioRepository.findById(guardado.getId()).orElseThrow();

        assertEquals("pedro@example.com", recuperado.getEmail());
        assertEquals("PROPIETARIO", recuperado.getRol());
    }

    //Añadido nuevo para #12
    @Test
    void buscarUsuarioPorEmailSinDistinguirMayusculas() {
        usuarioRepository.saveAndFlush(new Usuario("ana@example.com", "Ana", "García", "DNI", "ES", "12345678Z", "INQUILINO", CONTRASENA));

        Usuario encontrado = usuarioRepository.findByEmailIgnoreCase("ANA@example.com").orElseThrow();

        assertEquals("ana@example.com", encontrado.getEmail());
        assertEquals(CONTRASENA, encontrado.getContrasena());
        assertTrue(usuarioRepository.findByEmailIgnoreCase("otra@example.com").isEmpty());
    }

}