package es.uclm.rental_platform.web;

import es.uclm.rental_platform.service.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ModelAttribute("paises")
    public Map<String, String> paises() {
        Map<String, String> paises = new TreeMap<>();
        Locale idioma = Locale.forLanguageTag("es");
        for (String codigo : Locale.getISOCountries()) {
            paises.put(codigo, new Locale("", codigo).getDisplayCountry(idioma));
        }
        return paises;
    }

    @GetMapping("/usuarios/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/usuarios/registro")
    public String registrarUsuario(@RequestParam(defaultValue = "") String email,
                                   @RequestParam(defaultValue = "") String nombre,
                                   @RequestParam(defaultValue = "") String apellidos,
                                   @RequestParam(defaultValue = "") String tipoDocumento,
                                   @RequestParam(defaultValue = "") String paisEmisor,
                                   @RequestParam(defaultValue = "") String numeroDocumento,
                                   Model model, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.registrarUsuario(email, nombre, apellidos, tipoDocumento, paisEmisor, numeroDocumento);
            redirectAttributes.addFlashAttribute("exito", "Tu usuario se ha registrado correctamente.");
            return "redirect:/usuarios/registro";
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
        } catch (DataIntegrityViolationException exception) {
            model.addAttribute("error", "No se ha podido guardar el usuario. Revisa los datos y comprueba que el email o el documento no estén registrados.");
        }

        model.addAttribute("email", email);
        model.addAttribute("nombre", nombre);
        model.addAttribute("apellidos", apellidos);
        model.addAttribute("tipoDocumento", tipoDocumento);
        model.addAttribute("paisEmisor", paisEmisor);
        model.addAttribute("numeroDocumento", numeroDocumento);
        return "registro";
    }
}
