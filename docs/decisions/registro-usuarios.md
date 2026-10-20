# Decisiones del registro de usuarios (#10)

Fecha original: 15/10/2026. En esa fecha era una propuesta pendiente de revisión.

Actualización de estado — 20/10/2026: implementación revisada por Carlos e integrada
el 17/10 mediante [#25](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/25). Se conservan las decisiones y los límites originales;
esta actualización documental queda pendiente de su propia revisión.

## Alcance

Se sigue el plan humano de #10: registrar email, nombre, apellidos y DNI;
rechazar campos vacíos y email repetido; guardar y recuperar mediante JPA.
Jorge acepta esos campos como base provisional. No son una respuesta del cliente.
No se implementa login ni se decide la relación entre inquilino y propietario.

## Tecnología y capas

Se aprovecha la configuración de JPA/H2 ya existente en la rama. Spring Data JPA
proporciona el repositorio; H2 permite comprobar datos sin instalar otro gestor.
Se mantiene Thymeleaf con un controlador que coordina la web, un servicio que
valida y un repositorio que accede a datos. No se incorporan bibliotecas nuevas
para el formulario o CSS. Se utilizan clases y métodos explícitos para facilitar
su comprensión por el equipo.

## Datos y validaciones

Usuario tiene id Long generado por la BD y cuatro cadenas no nulas. El email
tiene una restricción única en BD. El servicio rechaza null, cadenas vacías y
espacios; después consulta existsByEmail y guarda. La restricción de BD protege
también inserciones concurrentes; el controlador muestra un error controlado
si una restricción impide guardar. No se garantiza un mensaje específico de
duplicado en ese caso concurrente.

El email se compara exactamente: no se han acordado reglas de normalización.
El navegador comprueba type=email; el servidor todavía no valida ese formato.
No se valida la letra de DNI ni se impone su unicidad. Los campos HTML tienen
maxlength=255 para coincidir con las columnas JPA; esa longitud no es un
requisito del cliente. Estas limitaciones se mantienen visibles para la revisión.

## Persistencia y revisión

H2 usa jdbc:h2:mem:rentaldb: guarda y permite recuperar datos durante la ejecución,
pero no los conserva al detener la aplicación. ddl-auto=update y la consola H2
son opciones del laboratorio; no se presentan como configuración de producción.
Las pruebas usan bases de datos de prueba independientes de la instancia en 8080.

El 15/10 estaban pendientes la prueba manual, la consulta H2 y la revisión del diff.
Jorge confirmó el 19/10 alta, email repetido y `SELECT * FROM USUARIOS` correctos.
Carlos aprobó #25 y dejó su testimonio de verificación en [#27](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/27).
No se atribuyen datos exactos ni nuevas pruebas humanas. Las decisiones sobre los
límites indicados siguen pendientes del cliente (#15). Ver [AI-01](../ai/AI-01-hito-funcional.md).
