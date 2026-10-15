# Base técnica

En esta carpeta se documentan las decisiones técnicas del proyecto sobre Maven, Spring Boot, dependencias y ejecución local.

## Stack

- Java 17
- Spring Boot 4.1.1
- Maven 3.9.16 mediante Maven Wrapper (`mvnw` / `mvnw.cmd`), para no depender de que Maven esté instalado en cada equipo.
- Paquete base: `es.uclm.rental_platform`
- Punto de entrada: `RentalPlatformApplication`

## Dependencias

- `spring-boot-starter-webmvc` → aplicación web con Spring MVC: controladores y peticiones HTTP.
- `spring-boot-starter-thymeleaf` → vistas HTML mediante plantillas Thymeleaf.
- `spring-boot-starter-webmvc-test` (test) → soporte para pruebas de Spring MVC.
- `spring-boot-starter-thymeleaf-test` (test) → soporte para pruebas de Thymeleaf.

Cualquier dependencia nueva debe añadirse aquí con su justificación.

## Ejecución

Requisito: Java 17.

Desde la raíz del repositorio:

```powershell
.\mvnw.cmd clean
.\mvnw.cmd test
.\mvnw.cmd package
.\mvnw.cmd verify
.\mvnw.cmd spring-boot:run
```

Con la aplicación arrancada, en http://localhost:8080/ debe aparecer la página "Plataforma de alquiler de viviendas" (`HomeController` → `home.html`).

Para parar la aplicación: `Ctrl + C` en la terminal.

La carpeta `target/` la genera Maven al compilar y no se versiona (está en `.gitignore`).