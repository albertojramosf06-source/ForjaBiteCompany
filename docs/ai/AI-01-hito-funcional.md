# AI-01: registro persistente de usuarios (#10)

Evidencia provisional del trabajo local en feat/10-user-persistence.
Fecha: 15 de octubre de 2026. Herramienta: Codex. Solicitante: Jorge Martín Sánchez.

## Plan humano y contexto

La issue #10 contiene el plan provisional: email, nombre, apellidos y DNI;
campos obligatorios y email único; base de datos, formulario y pruebas.
El contexto incluye Java 17, Spring Boot, Maven Wrapper, las capas del proyecto,
los materiales de la asignatura y la configuración JPA/H2 previa de esta rama.

## Plan del agente y decisiones humanas

El agente propone pasos pequeños: comprobar JPA/H2, crear entidad, repositorio,
servicio y finalmente controlador y formulario. Jorge aprueba cada paso en la
conversación y pide código de nivel de tercero de carrera, sencillo y explicable.
Para el formulario solicita estilos; se usa CSS propio adaptable al móvil.
No se atribuyen aceptación o revisión a los demás integrantes ni al cliente.
La implementación anterior retirada no se presenta como producto vigente.

## Cambios actuales

- Usuario: entidad JPA con id generado y los cuatro campos acordados.
- UsuarioRepository: guardar, consultar y comprobar existencia de email.
- UsuarioService: comprobar campos obligatorios y duplicados antes de guardar.
- UsuarioController: mostrar formulario, llamar al servicio y gestionar respuesta.
- registro.html y registro.css: formulario con errores, confirmación y estilos.
- Pruebas del repositorio, servicio y flujo web con H2 independiente.

## Límites y decisiones pendientes

El modelo de roles, login y relación con propietarios está fuera de este cambio.
Los campos proceden del plan provisional, pendientes de contraste con el cliente.
No se valida letra/formato de DNI ni su unicidad. El email se compara exactamente;
no se ha acordado normalización. El navegador valida type=email; no se afirma
validación del formato de email en el servidor. El servicio valida obligatorios.
Los campos web limitan a 255 caracteres, coincidiendo con las columnas JPA.
La restricción única de BD protege inserciones concurrentes; el controlador
devuelve un mensaje controlado si falla una restricción al guardar.
H2 es en memoria: se pierde el contenido al detener la aplicación.

## Verificación y revisión

`.\mvnw.cmd verify`: BUILD SUCCESS, 9 pruebas sin fallos ni errores (arranque,
repositorio, servicio y cuatro pruebas web). Incluye formulario, CSS, guardado,
confirmación, obligatorios y duplicados. Se revisó visualmente el formulario en
el navegador en tamaño estrecho y escritorio. Se arrancó una instancia de revisión
en 8081 al no poder arrancar otra en 8080; no se cambió el puerto configurado.
Después, por petición de Jorge, se detuvo 8081 y se arrancó en 8080. La instancia
de revisión se detuvo después para liberar 8080 a la ejecución del usuario.
En la última comprobación no había aplicación escuchando; no se arrancó otra.
Se utiliza únicamente el puerto original 8080.
Pendientes: prueba manual de Jorge, consulta de los datos en consola H2, revisión
del equipo y decisión de integración. El trabajo se guardó en un commit con la
identidad GitHub de Jorge y se actualizó la rama con main sin modificar main.
Se prepara una PR en borrador; no se declara aprobada ni integrada.

Se añaden la instrucción de revisión de persistencia de S4 en AGENTS.md,
docs/decisions/registro-usuarios.md y docs/diagrams/registro-usuarios.md.

## Valor aportado

El agente prepara un flujo pequeño atravesando las capas y pruebas reproducibles.
El equipo debe contrastar resultados, entender el código y decidir su aceptación.
