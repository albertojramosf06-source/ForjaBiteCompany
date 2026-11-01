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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Añadido nuevo para #12
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:sesion_web_test;DB_CLOSE_ON_EXIT=FALSE")
class SesionControllerTests {

    private static final String CONTRASENA = "secreta123";

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
    void mostrarFormularioDeInicioSesion() throws Exception {
        mockMvc.perform(get("/iniciar-sesion"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("action=\"/iniciar-sesion\"")))
                .andExpect(content().string(containsString("name=\"contrasena\"")));
    }

    @Test
    void inicioEnlazaAlInicioDeSesion() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("href=\"/iniciar-sesion\"")));
    }

    @Test
    void iniciarSesionCorrectaGuardaUsuarioYRolEnLaSesion() throws Exception {
        usuarioService.registrarPropietario("pedro@example.com", "Pedro", "Sánchez", "DNI", "ES", "12345678Z", CONTRASENA);

        var resultado = mockMvc.perform(post("/iniciar-sesion")
                        .param("email", "pedro@example.com").param("contrasena", CONTRASENA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("exito", "Has iniciado sesión como Pedro."))
                .andExpect(request().sessionAttribute("rol", "PROPIETARIO"))
                .andReturn();
        mockMvc.perform(get("/").flashAttrs(resultado.getFlashMap()))
                .andExpect(content().string(containsString("Has iniciado sesión como Pedro.")));
    }

    @Test
    void rechazarContrasenaIncorrectaYConservarEmail() throws Exception {
        usuarioService.registrarUsuario("ana@example.com", "Ana", "García", "DNI", "ES", "12345678Z", CONTRASENA);

        mockMvc.perform(post("/iniciar-sesion").param("email", "ana@example.com").param("contrasena", "otraContrasena"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "El email o la contraseña no son correctos."))
                .andExpect(request().sessionAttributeDoesNotExist("usuarioId"))
                .andExpect(content().string(containsString("value=\"ana@example.com\"")));
    }

    @Test
    void rechazarUsuarioNoRegistrado() throws Exception {
        mockMvc.perform(post("/iniciar-sesion").param("email", "nadie@example.com").param("contrasena", CONTRASENA))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "El email o la contraseña no son correctos."))
                .andExpect(request().sessionAttributeDoesNotExist("usuarioId"));
    }
}