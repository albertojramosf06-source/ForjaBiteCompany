package es.uclm.rental_platform.repository;

import es.uclm.rental_platform.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByTipoDocumentoAndPaisEmisorAndNumeroDocumento(
            String tipoDocumento, String paisEmisor, String numeroDocumento);
}
