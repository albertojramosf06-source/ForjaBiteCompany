# Registro de uso de IA

## Formulario y documentación del Hito 1 — 15 de octubre de 2026

Jorge autoriza continuar con controlador y formulario y solicita estilos.
Se añaden UsuarioController, registro.html, CSS propio y pruebas del flujo web.
La lógica de registro se conserva en el servicio. El controlador muestra errores
sin descartar los datos introducidos y redirige tras el éxito.
La ficha provisional está en [AI-01](docs/ai/AI-01-hito-funcional.md); registra plan,
decisiones conocidas, límites y comprobaciones pendientes. No se inventan reviews.
Verificación: `.\mvnw.cmd verify`, BUILD SUCCESS, 9 pruebas sin fallos ni errores;
revisión visual del formulario en navegador en tamaños estrecho y escritorio.
Instancia de revisión arrancada en 8081, sin modificar el puerto configurado.
Por petición de Jorge se detuvo esa instancia y se arrancó la aplicación en el
puerto original 8080. A partir de este punto se utiliza únicamente 8080.
Cambios locales sin commit ni PR; comprobación manual del equipo pendiente.

## Servicio de usuarios — 15 de octubre de 2026

Jorge aprueba crear UsuarioService, añadir existsByEmail al repositorio y probar
registro válido, campos null/vacíos/espacios y email repetido. El servicio recibe
los cuatro campos, valida antes de guardar y comunica los errores mediante
IllegalArgumentException con mensajes claros. No se incorpora lógica al controlador.
El email se compara tal como se recibe: no se han acordado reglas nuevas de
normalización o formato. La restricción única de BD sigue protegiendo duplicados
concurrentes; su tratamiento web se revisará al preparar el controlador.
Las pruebas usan H2 independiente y transacciones que se revierten al terminar.
No se añaden formularios en este paso. `.\mvnw.cmd verify`: BUILD SUCCESS,
5 pruebas sin fallos ni errores (arranque, repositorio y tres del servicio).
Diff comprobado sin errores de espacios. Revisión humana pendiente.

## Repositorio de usuarios — 15 de octubre de 2026

Jorge aprueba añadir UsuarioRepository y una prueba de guardado y recuperación.
La interfaz extiende JpaRepository<Usuario, Long>, sin consultas personalizadas.
La prueba usa una base H2 en memoria independiente (usuarios_test), guarda con
saveAndFlush y limpia la caché de JPA antes de recuperar por id y comprobar todos
los campos. La transacción del test se revierte al terminar. Los datos de ejemplo
no aparecen en la consola de la aplicación: son datos de prueba, no registros reales.
No se añaden servicio, validaciones de registro, controlador ni formulario.
Verificación: `.\mvnw.cmd verify`, BUILD SUCCESS, 2 pruebas sin fallos ni errores
(arranque y guardado/recuperación). Hibernate ejecutó INSERT y SELECT sobre H2.
Diff revisado sin errores de espacios. Revisión humana e integración pendientes.

## Primera entidad Usuario — 15 de octubre de 2026

Jorge revisó y aceptó crear únicamente la entidad Usuario en domain con id Long
generado y email, nombre, apellidos y DNI obligatorios; email único. Estos campos
proceden del plan humano provisional de #10, no de respuestas del cliente.
Codex utiliza anotaciones JPA, constructores y métodos de acceso explícitos,
acordes con el nivel y las técnicas de clase. No se añaden repositorios,
formularios ni relaciones entre usuario y propietario. Las columnas no nulas no
sustituyen la validación de cadenas vacías del futuro flujo de registro.
Hibernate genera la tabla usuarios con las cinco columnas y la restricción única
del email durante la prueba de arranque. `.\mvnw.cmd verify` termina en BUILD
SUCCESS: 1 prueba sin fallos ni errores. No se ha probado todavía guardar usuarios.

## Ajuste de configuración JPA/H2 — 15 de octubre de 2026

Jorge autorizó a Codex a quitar la propiedad spring.application.name duplicada,
corregir la sangría de una dependencia y documentar las dependencias JPA/H2,
la conexión de la consola y el carácter temporal de los datos en memoria.
Se mantiene la configuración previa, sin nuevas entidades, formularios ni reglas
de registro. Jorge pide código sencillo y acorde con las técnicas de clase.
La revisión humana sigue pendiente; este ajuste no completa el Hito 1.
Verificación del ajuste: `.\mvnw.cmd verify`, BUILD SUCCESS, 1 prueba sin fallos
ni errores; diff revisado sin errores de espacios (respetando CRLF).

Este documento recoge los usos relevantes de inteligencia artificial realizados durante el proyecto.

## Principios

- La IA puede proponer, pero el equipo decide.
- Todo cambio debe ser comprendido y revisado.
- Las propuestas deben verificarse mediante las herramientas correspondientes.
- No se integrará código generado por IA sin revisión humana.

## Hito 1 – Desarrollo funcional

Pendiente.

**Tarea delegada:** Organización y análisis de las sesiones S1-S3 a partir de los materiales de la asignatura.

**Contexto proporcionado:** Materiales oficiales S1, S2, S3, Proyecto2026 y diagramas del proyecto.

**Herramienta utilizada:** ChatGPT.

**Resultado final:** [PENDIENTE: completar tras revisar y aplicar la propuesta]

**Verificación realizada:** [PENDIENTE]

**Qué aceptó/modificó/rechazó el equipo:** [PENDIENTE]

### Uso de IA — [PENDIENTE: FECHA / IDENTIFICADOR]

**Tarea delegada:**  
[PENDIENTE]

**Contexto proporcionado:**  
[PENDIENTE]

**Plan propuesto:**  
[PENDIENTE]

**Herramienta utilizada:**  
[PENDIENTE]

**Participantes y responsabilidades:**  
[PENDIENTE]

**Qué aceptó el equipo:**  
[PENDIENTE]

**Qué modificó el equipo:**  
[PENDIENTE]

**Qué rechazó el equipo:**  
[PENDIENTE]

**Errores o carencias detectados:**  
[PENDIENTE]

**Motivo de las decisiones humanas:**  
[PENDIENTE]

**Verificación realizada:**  
[PENDIENTE]

**Resultado final:**  
[PENDIENTE]

**Valor real aportado:**  
[PENDIENTE]
