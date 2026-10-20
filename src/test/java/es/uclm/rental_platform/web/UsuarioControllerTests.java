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
                .andExpect(content().string(containsString("name=\"numeroDocumento\"")));
        mockMvc.perform(get("/css/registro.css")).andExpect(status().isOk());
    }

    @Test
    void registrarDesdeFormularioYMostrarConfirmacion() throws Exception {
        var resultado = mockMvc.perform(post("/usuarios/registro")
                        .param("email", "ana@example.com").param("nombre", "Ana")
                        .param("apellidos", "García").param("tipoDocumento", "DNI").param("paisEmisor", "ES").param("numeroDocumento", "12345678Z"))
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
                        .param("nombre", "   ").param("apellidos", "García").param("tipoDocumento", "DNI").param("paisEmisor", "ES").param("numeroDocumento", "12345678Z"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "Todos los campos son obligatorios."))
                .andExpect(content().string(containsString("value=\"ana@example.com\"")));
        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void rechazarEmailRepetidoDesdeFormulario() throws Exception {
        usuarioService.registrarUsuario("ana@example.com", "Ana", "García", "DNI", "ES", "12345678Z");
        mockMvc.perform(post("/usuarios/registro").param("email", "ana@example.com")
                        .param("nombre", "Otra").param("apellidos", "Persona").param("tipoDocumento", "DNI").param("paisEmisor", "ES").param("numeroDocumento", "87654321X"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ya existe un usuario con ese email.")));
        assertEquals(1, usuarioRepository.count());
    }

    @Test
    void validarDniEnServidorYConservarDocumento() throws Exception {
        mockMvc.perform(post("/usuarios/registro").param("email", "ana@example.com")
                        .param("nombre", "Ana").param("apellidos", "García")
                        .param("tipoDocumento", "DNI").param("paisEmisor", "ES").param("numeroDocumento", "12345678A"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("numeroDocumento", "12345678A"))
                .andExpect(model().attribute("error", "El DNI debe tener ocho cifras y una letra de control correcta."));
        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void registrarPasaporteDesdeFormulario() throws Exception {
        mockMvc.perform(post("/usuarios/registro").param("email", "ANA@EXAMPLE.COM")
                        .param("nombre", "Ana").param("apellidos", "García")
                        .param("tipoDocumento", "PASAPORTE").param("paisEmisor", "FR").param("numeroDocumento", "ab12345"))
                .andExpect(status().is3xxRedirection());
        var usuario = usuarioRepository.findAll().get(0);
        assertEquals("ana@example.com", usuario.getEmail());
        assertEquals("PASAPORTE", usuario.getTipoDocumento());
        assertEquals("FR", usuario.getPaisEmisor());
        assertEquals("AB12345", usuario.getNumeroDocumento());
    }

    @Test
    void peticionDirectaRechazaEmailIncorrectoYLongitudExcesiva() throws Exception {
        for (String email : new String[]{"sin-arroba", "a".repeat(256) + "@example.com"}) {
            mockMvc.perform(post("/usuarios/registro").param("email", email)
                            .param("nombre", "Ana").param("apellidos", "García")
                            .param("tipoDocumento", "DNI").param("numeroDocumento", "12345678Z"))
                    .andExpect(status().isOk()).andExpect(model().attributeExists("error"));
        }
        assertEquals(0, usuarioRepository.count());
    }


    @Test
    void rechazarPeticionSinDocumento() throws Exception {
        mockMvc.perform(post("/usuarios/registro").param("email", "ana@example.com")
                        .param("nombre", "Ana").param("apellidos", "García"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "Todos los campos son obligatorios."));
        assertEquals(0, usuarioRepository.count());
    }

    @Test
    void dosRegistrosSimultaneosConMismoEmailCreanUnaSolaFila() throws Exception {
        var ejecutor = java.util.concurrent.Executors.newFixedThreadPool(2);
        var salida = new java.util.concurrent.CountDownLatch(1);
        try {
            var primera = ejecutor.submit(() -> {
                salida.await();
                return mockMvc.perform(post("/usuarios/registro").param("email", "ANA@EXAMPLE.COM")
                        .param("nombre", "Ana").param("apellidos", "García")
                        .param("tipoDocumento", "DNI").param("numeroDocumento", "12345678Z"))
                        .andReturn().getResponse().getStatus();
            });
            var segunda = ejecutor.submit(() -> {
                salida.await();
                return mockMvc.perform(post("/usuarios/registro").param("email", "ana@example.com")
                        .param("nombre", "Otra").param("apellidos", "Persona")
                        .param("tipoDocumento", "DNI").param("numeroDocumento", "87654321X"))
                        .andReturn().getResponse().getStatus();
            });
            salida.countDown();
            var estados = new java.util.ArrayList<>(java.util.List.of(
                    primera.get(20, java.util.concurrent.TimeUnit.SECONDS),
                    segunda.get(20, java.util.concurrent.TimeUnit.SECONDS)));
            java.util.Collections.sort(estados);
            assertEquals(java.util.List.of(200, 302), estados);
            assertEquals(1, usuarioRepository.count());
            assertEquals("ana@example.com", usuarioRepository.findAll().get(0).getEmail());
        } finally {
            ejecutor.shutdownNow();
        }
    }

}
