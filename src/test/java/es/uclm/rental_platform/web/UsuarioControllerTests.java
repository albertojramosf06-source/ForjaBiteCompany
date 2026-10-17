package es.uclm.rental_platform.web;

import es.uclm.rental_platform.repository.UsuarioRepository;
import es.uclm.rental_platform.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:usuarios_web_test;DB_CLOSE_ON_EXIT=FALSE")
class UsuarioControllerTests {

    @Autowired private WebApplicationContext context;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private UsuarioService usuarioService;
    private MockMvc mockMvc;

    @BeforeEach
    void preparar() {
        usuarioRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void mostrarFormularioConEstilos() throws Exception {
        mockMvc.perform(get("/usuarios/registro"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("/css/registro.css")))
                .andExpect(content().string(containsString("name=\"dni\"")));
        mockMvc.perform(get("/css/registro.css")).andExpect(status().isOk());
    }

    @Test
    void registrarDesdeFormularioYMostrarConfirmacion() throws Exception {
        var resultado = mockMvc.perform(post("/usuarios/registro")
                        .param("email", "ana@example.com").param("nombre", "Ana")
                        .param("apellidos", "García").param("dni", "12345678Z"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios/registro"))
                .andExpect(flash().attribute("exito", "Tu usuario se ha registrado correctamente."))
                .andReturn();
        assertEquals(1, usuarioRepository.count());
        mockMvc.perform(get("/usuarios/registro").flashAttrs(resultado.getFlashMap()))
                .andExpect(content().string(containsString("Tu usuario se ha registrado correctamente.")));
    }

    @Test
    void rechazarCamposVaciosYConservarDatos() throws Exception {
        mockMvc.perform(post("/usuarios/registro").param("email", "ana@example.com")
                        .param("nombre", "   ").param("apellidos", "García").param("dni", "12345678Z"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "Todos los campos son obligatorios."))
                .andExpect(content().string(containsString("value=\"ana@example.com\"")));
        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void rechazarEmailRepetidoDesdeFormulario() throws Exception {
        usuarioService.registrarUsuario("ana@example.com", "Ana", "García", "12345678Z");
        mockMvc.perform(post("/usuarios/registro").param("email", "ana@example.com")
                        .param("nombre", "Otra").param("apellidos", "Persona").param("dni", "87654321X"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ya existe un usuario con ese email.")));
        assertEquals(1, usuarioRepository.count());
    }
}
