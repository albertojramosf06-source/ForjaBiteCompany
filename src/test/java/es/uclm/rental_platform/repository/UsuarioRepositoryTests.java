package es.uclm.rental_platform.repository;

import es.uclm.rental_platform.domain.Usuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:usuarios_test;DB_CLOSE_ON_EXIT=FALSE")
@Transactional
class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void guardarYRecuperarUsuario() {
        Usuario usuario = new Usuario("ana@example.com", "Ana", "García López", "12345678Z");

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
        assertEquals("12345678Z", recuperado.getDni());
    }
}
