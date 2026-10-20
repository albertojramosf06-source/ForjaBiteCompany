package es.uclm.rental_platform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(
        name = "uk_usuario_documento", columnNames = {"tipo_documento", "pais_emisor", "numero_documento"}))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(name = "tipo_documento", nullable = false, length = 10)
    private String tipoDocumento;

    @Column(name = "pais_emisor", nullable = false, length = 2)
    private String paisEmisor;

    @Column(name = "numero_documento", nullable = false, length = 32)
    private String numeroDocumento;

    protected Usuario() {
        // JPA necesita un constructor sin argumentos para recuperar las entidades.
    }

    public Usuario(String email, String nombre, String apellidos, String tipoDocumento, String paisEmisor, String numeroDocumento) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.paisEmisor = paisEmisor;
        this.numeroDocumento = numeroDocumento;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoDocumento() { return tipoDocumento; }

    public String getPaisEmisor() { return paisEmisor; }

    public String getNumeroDocumento() { return numeroDocumento; }
}
