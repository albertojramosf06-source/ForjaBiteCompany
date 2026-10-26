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

## Persistencia del incremento #10

- `spring-boot-starter-data-jpa`: entidades y repositorios para guardar y recuperar usuarios.
- `h2` (runtime): base de datos en memoria del laboratorio.
- `spring-boot-h2console` (runtime): consola web para consultar esa base.

Formulario: http://localhost:8080/usuarios/registro. Consola:
http://localhost:8080/h2-console/; JDBC `jdbc:h2:mem:rentaldb`, usuario `sa`,
contraseña vacía. Los datos se pierden al detener la aplicación.
La página inicial conserva su ruta `/` y enlaza al formulario.

## Registro de propietarios (#11)

Formulario: http://localhost:8080/propietarios/registro. Usa la misma vista
`registro.html` y el mismo servicio que el registro de usuarios; solo cambian los
textos, la ruta del formulario y el rol guardado (`PROPIETARIO`). El registro de
usuarios guarda `INQUILINO`. El rol se comprueba con `SELECT * FROM USUARIOS;`.
Decisión: [registro de propietarios](../decisions/registro-propietarios.md).

Verificación del 26/10/2026: `.\mvnw.cmd clean verify` → BUILD SUCCESS, 39 pruebas,
0 fallos y 0 errores (repositorio 5, servicio 19, web de integración 13,
controlador de errores 1 y arranque 1).

## Ejecución

Requisito: Java 17.

Desde la raíz del repositorio:

```powershell
.\mvnw.cmd clean verify
.\mvnw.cmd spring-boot:run
```

Con la aplicación arrancada, http://localhost:8080/ muestra el inicio
"El comienzo de un nuevo hogar." (`HomeController` → `home.html`), con acceso al
registro. El logo permite volver al inicio. Ambas vistas comparten `registro.css`.
`registro.js` muestra los campos del documento elegido y requiere JavaScript.

## Verificación local conjunta del 20/10/2026

La funcionalidad integrada no se modifica. Sobre main de referencia
`614bae73db51b60a6b6c717806fa76dfbb93ea89` se amplían las pruebas locales:
`.\mvnw.cmd clean verify` termina con BUILD SUCCESS, **29 pruebas**, 0 fallos,
0 errores y 0 omitidas. Desglose: arranque 1, repositorio 4, servicio 14,
web de integración 9 y controlador de errores 1.

Se comprueban también email de 255/256 caracteres, DNI con ceros iniciales,
tipo ausente, DNI duplicado en BD, petición sin documento, dos peticiones
simultáneas con el mismo email y conservación de datos ante un error de integridad.
El caso concurrente comprueba una sola fila, una respuesta de éxito y otra
controlada; no es una prueba de carga ni garantiza todos los entrelazados posibles.

Estado completo y límites: [revisión conjunta](../revision-conjunta-defensa.md).
Los resultados siguientes de 22 pruebas son la evidencia anterior de #36 y no se
sustituyen retroactivamente por los 29 de esta revisión local.

## Verificación histórica de la versión integrada por #36

Versión integrada por #36: `1258bb7289c49d898336e8163368434afc24afd4`.
El 20/10/2026, `.\mvnw.cmd clean verify` terminó correctamente: 22 pruebas
(1 de arranque, 3 de repositorio, 11 de servicio y 7 web), sin fallos ni errores.
Los informes se generan en `target/surefire-reports/`. Los tests usan H2 de prueba
y sus usuarios no aparecen en la consola de la aplicación.

Jorge confirma en la conversación del 20/10 que probó la aplicación actual y H2
con resultado correcto. No se inventan datos exactos ni capturas de esa prueba.
Para consultar los registros, mantener la aplicación arrancada y ejecutar
`SELECT * FROM USUARIOS;` en la consola H2. Reglas y límites:
[decisiones del registro](../decisions/registro-usuarios.md).

Para parar la aplicación: `Ctrl + C` en la terminal.

La carpeta `target/` la genera Maven al compilar y no se versiona (está en `.gitignore`).
