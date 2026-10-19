# Registro de uso de IA

## Actualización documental de AI-01 — 19 de octubre de 2026

Jorge autoriza adaptar la ficha a la plantilla oficial que aporta en la raíz del
repositorio. Codex actualiza solo docs/ai/AI-01-hito-funcional.md y esta referencia,
conservando el resto de entradas y el trabajo pendiente de los compañeros.
Se incorporan las evidencias de #10 y #25, la aprobación de Carlos y la integración
del 17/10, diferenciándolas de su declaración de pruebas en la PR #27 aún abierta.
Jorge confirma el 19/10 que probó alta, email repetido y consulta H2 con éxito;
no se inventan datos de prueba exactos, capturas ni decisiones individuales.
La ficha distingue pruebas del agente, confirmaciones humanas y límites conocidos.
No se modifica código ni se aprueba o integra esta documentación automáticamente.
Verificación: `.\mvnw.cmd verify`, BUILD SUCCESS, 9 pruebas sin fallos ni errores.
Se comprobaron las diez secciones de la plantilla, los enlaces locales y el diff.
La actualización se presenta en PR en borrador para revisión humana; no cierra
por sí sola #16 ni las tareas de otros integrantes.

## Preparación de la PR del Hito 1 — 15 de octubre de 2026

Jorge autoriza guardar el trabajo, actualizar la rama con main, completar la
instrucción de revisión JPA, decisiones y modelo, y preparar PR en borrador.
Se conserva la documentación previa de S1-S3. Los commits se atribuyen a
JorgeMartinSanchez mediante su correo noreply de GitHub. No se cambia main ni se
aprueba la propuesta. Las notas anteriores describen el estado local en cada
paso; desde este punto el trabajo está guardado en commits para preparar su push.
La prueba manual y la consulta de datos del usuario siguen pendientes de confirmar.
Verificación tras actualizar la rama: `.\mvnw.cmd verify`, BUILD SUCCESS,
9 pruebas sin fallos ni errores. Diff revisado, sin errores de espacios.

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

## Uso de IA para organización y análisis de materiales

Esta actividad de organización no constituye por sí sola el Hito 1 funcional.

**Tarea delegada:** Organización y análisis de las sesiones S1-S3 a partir de los materiales de la asignatura.

**Contexto proporcionado:** Materiales oficiales S1, S2, S3, Proyecto2026 y diagramas del proyecto.

**Herramienta utilizada:** ChatGPT.

**Resultado final:** [PENDIENTE: completar tras revisar y aplicar la propuesta]

**Verificación realizada:** [PENDIENTE]

**Qué aceptó/modificó/rechazó el equipo:** [PENDIENTE]

## Revisión de S1 — 14 de octubre de 2026

**Herramienta:** Codex, mediante la cuenta GitHub CLI JorgeMartinSanchez.
**Solicitante:** Jorge Martín Sánchez; no se atribuye revisión a otros integrantes.
**Contexto:** S1, enunciado, capturas de avisos, README, AGENTS, AI_USAGE, issues,
Project y conversación. Se consultaron las fuentes y el estado remoto antes de editar.
**Plan:** conservar lo realizado; resumir encargo;
separar dudas y aclaraciones; concretar criterios de #16; documentar objetivo
provisional y evidencia real. No modificar código ni integrar cambios.
**Decisiones humanas:** Jorge autoriza los ajustes, confirma empresa, correo y
accesos, acepta registro persistente como objetivo provisional e indica no tocar #17.
Durante la ejecución pide mantener el nombre original del Project. Se conserva
ForjaByte Company-Planificacion; el cambio de título inicial se revierte.
**Resultado:** documentación preparada en README y docs/session1-review.md;
aclaraciones añadidas a #15 y criterios añadidos a #16, comprobados en GitHub.
El nombre del Project se restauró y comprobó; #17, estados y responsables se conservaron. Revisión del equipo
y aceptación final pendientes. No se presenta este análisis como hito funcional.
**Verificación:** comparación de documentos y GitHub; `.\mvnw.cmd verify`:
BUILD SUCCESS, 1 prueba sin fallos ni errores. Diff comprobado respetando los
finales de línea existentes. Solo se modifican documentos; no se añade código.
**Valor aportado:** distinguir lo completado, lo provisional y lo pendiente sin
duplicar trabajo ni inventar requisitos o decisiones del cliente.

## Implementación retirada

El 14 de octubre de 2026 Jorge aceptó un plan de registro persistente. Codex lo
implementó localmente y ejecutó verify: 8 pruebas sin fallos. Después Jorge pidió
retirar el trabajo; se eliminaron los archivos nuevos, se restauraron los existentes
y se volvió a main. No hubo commit, push ni PR de esa implementación.
Este registro describe un uso real de IA, no una funcionalidad entregada.

## Plan de Gestión de Configuración — 15 de octubre de 2026

Jorge autoriza a Codex a preparar #20 después de revisar el plan propuesto.
Se comprueban issues, ramas y PR abiertas para no duplicar trabajo visible.
Se crea docs/plan-gestion-configuracion.md y se enlaza desde el README.
Se documenta main sin protección técnica (protected: false); no se cambian
permisos, responsables ni reglas remotas. El contenido se prepara como propuesta
para revisión humana, sin atribuir aceptación a Carlos ni a otros integrantes.
Verificación: `.\mvnw.cmd verify`, BUILD SUCCESS, 1 prueba sin fallos ni errores;
diff comprobado sin errores. Esta actividad documental no
constituye el Hito 1 funcional y el agente no aprueba ni integra la propuesta.

## Hito 1 – Desarrollo funcional

Pendiente: debe existir una funcionalidad vigente con revisión y evidencias.

### Plantilla de uso de IA — [PENDIENTE: FECHA / IDENTIFICADOR]

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
