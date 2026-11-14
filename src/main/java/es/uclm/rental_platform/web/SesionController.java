package es.uclm.rental_platform.web;

import es.uclm.rental_platform.domain.Usuario;
import es.uclm.rental_platform.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//Añadido nuevo para #12
@Controller
public class SesionController {

    private final UsuarioService usuarioService;

    public SesionController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/iniciar-sesion")
    public String mostrarInicioSesion() {
        return "iniciar-sesion";
    }

    @PostMapping("/iniciar-sesion")
    public String iniciarSesion(@RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String contrasena,
                                HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.iniciarSesion(email, contrasena);
            // La sesión guarda quién ha entrado y con qué rol.
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("rol", usuario.getRol());
            redirectAttributes.addFlashAttribute("exito", "Has iniciado sesión como " + usuario.getNombre() + ".");
            return "redirect:/";
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("email", email);
            return "iniciar-sesion";
        }
    }
}