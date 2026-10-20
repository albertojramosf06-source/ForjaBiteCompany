package es.uclm.rental_platform.web;

import es.uclm.rental_platform.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsuarioControllerErrorTests {
    @Test
    void errorDeIntegridadConservaLosDatosYSoloMuestraError() {
        UsuarioService servicio = mock(UsuarioService.class);
        when(servicio.registrarUsuario("ana@example.com", "Ana", "García", "PASAPORTE", "FR", "AB12345"))
                .thenThrow(new DataIntegrityViolationException("Restricción de prueba"));
        var controlador = new UsuarioController(servicio);
        var modelo = new ExtendedModelMap();
        var redireccion = new RedirectAttributesModelMap();
        String vista = controlador.registrarUsuario("ana@example.com", "Ana", "García",
                "PASAPORTE", "FR", "AB12345", modelo, redireccion);
        assertEquals("registro", vista);
        assertEquals("No se ha podido guardar el usuario. Revisa los datos y comprueba que el email o el documento no estén registrados.",
                modelo.get("error"));
        assertEquals("ana@example.com", modelo.get("email"));
        assertEquals("Ana", modelo.get("nombre"));
        assertEquals("García", modelo.get("apellidos"));
        assertEquals("PASAPORTE", modelo.get("tipoDocumento"));
        assertEquals("FR", modelo.get("paisEmisor"));
        assertEquals("AB12345", modelo.get("numeroDocumento"));
        assertFalse(redireccion.getFlashAttributes().containsKey("exito"));
    }
}
