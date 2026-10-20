# AI-01 — Hito 1 · Implementación de una issue funcional

> Ingeniería del Software II · Laboratorio 2026-2027.
> Adaptación a la plantilla oficial facilitada por Jorge.
> Implementación: 15/10/2026. Integración: 17/10/2026. Actualización inicial: 19/10/2026. Estados contrastados: 20/10/2026.

## 1. Identificación

- **Equipo / empresa:** ForjaBiteCompany.
- **Integrantes:** Jorge Martín Sánchez, Alberto José Ramos Fernández, Alejandro Jiménez García-Oliva, Diego López Barriocanal y Carlos Ramos Cabañas.
- **Repositorio:** [ForjaBiteCompany](https://github.com/albertojramosf06-source/ForjaBiteCompany).
- **Issue relacionada:** [#10 — Registrar usuarios](https://github.com/albertojramosf06-source/ForjaBiteCompany/issues/10).
- **Historia de usuario:** como inquilino, quiero registrarme para poder reservar alojamientos.
- **Rama de trabajo:** `feat/10-user-persistence`.
- **Responsable principal del Hito 1:** Jorge Martín Sánchez.
- **Participantes y contribución:** Jorge solicitó el trabajo y aprobó los planes por pasos, pidió código sencillo y estilos y confirmó sus pruebas manuales. Carlos (`Raamoos`) aprobó #25 en GitHub. En #27 declara sus pruebas y el merge; se recoge como testimonio suyo, no como ejecución observada por el agente. Codex preparó código, documentación y pruebas. No se inventan otras contribuciones individuales.

## 2. Alcance de la issue

### Objetivo

Registrar un usuario desde un formulario Thymeleaf con email, nombre, apellidos y DNI.
Validar obligatorios y email repetido antes de guardar con JPA/H2, conservando los
campos al mostrar errores y confirmando el éxito. Mantener las capas acordadas.

### Criterios de aceptación relevantes

- [x] Introducir datos desde un formulario.
- [x] Rechazar obligatorios null, vacíos o con solo espacios.
- [x] Rechazar email repetido, mediante comparación exacta.
- [x] Guardar y recuperar en H2 durante la ejecución.
- [x] Pruebas de registro y errores, build correcto y README actualizado.

### Fuera de alcance

- Login, contraseñas, roles y relaciones con propietarios.
- Reservas, inmuebles y pagos.
- Normalización del email, letra/unicidad del DNI y conservación entre reinicios.

## 3. Plan humano previo

### Plan inicial del equipo

Se recoge el plan provisional que ya figuraba en #10; no se atribuye un nuevo
plan retrospectivo al equipo:

1. Email, nombre, apellidos y DNI.
2. Evitar campos vacíos y email repetido.
3. Guardar en BD, dependiendo de la base técnica de #13.
4. Crear el formulario.
5. Escribir pruebas de los criterios.

### Archivos o capas que se prevé modificar

Entidad, repositorio, servicio, controlador, vista, pruebas y documentación.
Los nombres concretos se acordaron en las propuestas posteriores del agente.

### Dependencias, supuestos o dudas

Java 17, Spring Boot 4.1.1 y Maven Wrapper existentes. JPA/H2 ya estaba en la rama
(commit `c417f5e`). Los campos fueron aceptados provisionalmente por Jorge;
no son respuestas del cliente. Roles y datos definitivos siguen pendientes en #15.

### Cómo se comprobará que funciona

Maven, pruebas de las capas, formulario válido/erróneo, consulta H2 y revisión del diff.

## 4. Contexto y propuesta del agente

### Contexto proporcionado

- [x] Repositorio abierto y rama de trabajo.
- [x] AGENTS.md y reglas de revisión aplicables.
- [x] Issue y criterios de aceptación.
- [x] Código relevante y estructura Maven/Spring Boot.
- [x] Materiales S1-S4, enunciado y avisos de la asignatura.

**Herramienta / agente:** Codex.
**Entorno:** aplicación Codex en Windows, PowerShell, Git y GitHub CLI.
**Modelo, si procede:** no se registró un identificador verificable en la implementación.

### Prompt principal

Resumen fiel de las peticiones sucesivas, no cita literal de un único prompt:

```text
Continuar S4 y #10 poco a poco, conservando el trabajo existente.
Revisar cada plan antes de modificar y mantener código sencillo de tercero de carrera.
Comprobar JPA/H2 y crear entidad, repositorio, servicio y formulario con estilos.
Documentar decisiones y ejecutar pruebas. Usar únicamente 8080 y preparar PR
para revisión humana, sin aprobar ni integrar.
```

### Plan propuesto por el agente

1. Comprobar JPA/H2 y documentar sus límites.
2. Crear Usuario y comprobar su tabla.
3. Crear repositorio y probar guardado/recuperación.
4. Crear servicio con obligatorios y email repetido y sus pruebas.
5. Añadir controlador, formulario, CSS y pruebas web.
6. Documentar y preparar PR para revisión humana.

**Archivos o componentes propuestos:** capas anteriores, README, AI_USAGE, AI-01,
decisiones, modelo e instrucción de revisión de persistencia en AGENTS.

## 5. Revisión humana del plan

| Propuesta del agente | Decisión | Justificación conocida |
| --- | --- | --- |
| Usuario con id generado y cuatro campos | Aceptada por Jorge | Sigue el plan provisional de #10. |
| Repositorio y recuperación desde H2 | Aceptada por Jorge | Comprobar persistencia antes de la web. |
| Servicio con obligatorios y duplicados | Aceptada por Jorge | Negocio fuera del controlador. |
| Formulario | Aceptada con estilos solicitados | Jorge pide una pantalla cuidada. |
| Revisión temporal en 8081 | Corregida durante la verificación | Jorge pide usar exclusivamente 8080; se detiene la otra instancia. |

### Cambios solicitados antes de implementar

Avanzar en pasos pequeños y revisables, con código explicable. Añadir estilos sin
bibliotecas de interfaz nuevas. El cambio de puerto ocurrió después, no antes.
Una implementación anterior se retiró por petición de Jorge; no se presenta como
producto entregado. Después se retomó el trabajo por sesiones.

### Plan final aprobado

Configuración → entidad → repositorio → servicio → controlador/formulario →
pruebas/documentación → revisión humana. Jorge aceptó cada paso en la conversación;
no se atribuye una aprobación colectiva no registrada.

## 6. Implementación y revisión del código

### Cambios principales

| Archivo / componente | Cambio realizado | Motivo |
| --- | --- | --- |
| `domain/Usuario.java` | Entidad, id generado, campos no nulos y email único | Estado persistente. |
| `repository/UsuarioRepository.java` | JpaRepository y existsByEmail | Acceso a datos. |
| `service/UsuarioService.java` | Validar obligatorios y duplicados y guardar | Lógica de registro. |
| `web/UsuarioController.java` | GET/POST, errores y redirección tras éxito | Coordinación web. |
| `registro.html` y `registro.css` | Formulario adaptable y mensajes | Flujo visible. |
| Pruebas de repositorio, servicio y web | H2 de prueba independiente | Verificación reproducible. |
| README, AGENTS y docs | Configuración, modelo, decisiones y evidencia | Trazabilidad y límites. |

### Revisión humana

Carlos aprobó el commit `ca2bca4` en #25 con «Todo bien». Jorge confirma la prueba
manual. La aprobación no contiene checklist detallada por criterio: no se inventan
esas comprobaciones ni la comprensión individual de todos los integrantes.
En #27 Carlos declara pruebas locales, H2 y revisión del diff. La nota inicial
la describía como abierta; [#27](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/27) se integró el 19/10/2026.
La integración no cambia el carácter de testimonio humano de esas pruebas.

### Problemas o carencias detectados

| Problema | Corrección / decisión |
| --- | --- |
| Propiedad de configuración duplicada y sangría | Corregidas sin cambiar el funcionamiento. |
| No nulo no impide cadenas vacías | Validación en el servicio. |
| Duplicado concurrente | Restricción única en BD y mensaje de error controlado en web. |
| Email sin normalizar; formato solo en navegador | Limitación visible, no validación de formato en servidor. |
| DNI sin validar letra ni unicidad | Fuera del alcance provisional, pendiente del cliente. |
| H2 en memoria | Se documenta pérdida de datos al detenerla. |
| Instancia de revisión ocupando 8080 | Detenida por el agente para liberar el puerto al usuario. |

## 7. Verificación

### Build

```powershell
.\mvnw.cmd clean verify
```

- [x] BUILD SUCCESS.
- **Observaciones:** 9 pruebas, 0 fallos, 0 errores, 0 omitidas. Ejecución del agente
  en main `f0f19b7` el 19/10/2026. Hubo también verificaciones durante la implementación.
  No se confunden estas ejecuciones con las pruebas humanas.

### Ejecución de la funcionalidad

```powershell
.\mvnw.cmd spring-boot:run
```

- [x] Arranque y formulario comprobados durante la implementación.
- [x] Jorge confirma el 19/10/2026 alta, duplicado y consulta H2 con resultado correcto.

URL: `http://localhost:8080/usuarios/registro`. El 19/10 la petición HTTP del agente
agotó el tiempo de espera: no se afirma que verificara un servidor en vivo ese día
ni se arrancó otra instancia. Se distingue del build y de la confirmación de Jorge.

### Comprobación funcional

**Caso válido**

- **Entrada / acción:** rellenar los cuatro campos y enviar el formulario.
- **Esperado:** confirmación y fila guardada.
- **Obtenido:** correcto según Jorge; pruebas web verifican redirección, mensaje
  y una fila. No se registraron los datos exactos usados por Jorge.

**Caso inválido o error controlado**

- **Acción:** repetir un email registrado.
- **Esperado:** error controlado sin crear otro usuario.
- **Obtenido:** Jorge confirma funcionamiento. Pruebas web/servicio verifican
  el mensaje y una única fila. Otras pruebas cubren cada obligatorio null,
  vacío o con espacios y conservación de campos ante error.

### Persistencia, si procede

**Qué se comprobó:** `SELECT * FROM USUARIOS;` en H2, confirmado por Jorge.
**Resultado:** fila consultable. La prueba de repositorio guarda con saveAndFlush,
limpia caché JPA y recupera por id comprobando todos los datos desde H2.
No se afirma conservación entre reinicios.

### Revisión final del cambio

Se revisaron status y diff y se comprobó el formato antes de publicar #25.
La PR no incluye PDF, capturas ni respaldos personales. La adaptación de la
ficha se revisó separadamente del código: Carlos aprobó [#28](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/28),
integrada el 20/10/2026. La presente actualización de estados necesita su propia revisión.

## 8. Evidencias

| Evidencia | Captura / enlace |
| --- | --- |
| Plan humano previo | [#10](https://github.com/albertojramosf06-source/ForjaBiteCompany/issues/10), plan provisional. |
| Plan del agente y decisiones humanas | Secciones 4-5 y [AI_USAGE](../../AI_USAGE.md), síntesis de la conversación de trabajo; sin publicar chats completos. |
| Revisión humana | [#25](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/25), aprobación de Raamoos. |
| Funcionalidad y persistencia | Confirmación de Jorge del 19/10, sin capturas aportadas. Testimonio adicional de Carlos en [#27](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/27), integrada el 19/10/2026. |
| Verificación reproducible | [Pruebas](../../src/test/java/es/uclm/rental_platform); informes locales target/surefire-reports no versionados. |
| Modelo y decisiones | [Modelo](../diagrams/registro-usuarios.md) y [decisiones](../decisions/registro-usuarios.md). |
| Pull Request | [#25](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/25). |

## 9. Pull Request y cierre

- **PR:** [#25](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/25).
- **Issue vinculada:** #10 cerrada; Project: Done.
- **Revisor/a:** Carlos Ramos Cabañas (`Raamoos`).
- **Estado:** integrada el 17/10/2026 tras aprobación de Carlos.
- **Commit revisado:** `ca2bca4575260d529b857d31fa2aaf63a2238cf5`.
- **Commit de integración:** `f0f19b7a755acf005d81be37f685bd9310aff6de`.

### Revisión de la PR

La propuesta incluye alcance, arquitectura, build, pruebas, documentación, IA y
limitaciones. Carlos aprobó el 17/10 a las 12:04:54 (Europe/Madrid); consta merge a
las 12:05:07. No se rellenó una checklist detallada en su review; no se inventa.

### Observaciones del revisor

- Comentario en GitHub: «Todo bien».
- Según su entrada de #27, Carlos ejecutó clean verify, probó alta, duplicado y
  obligatorios, consultó H2 y revisó status/diff. Declara que descartó añadir un
  listado sin acuerdo del autor: es una decisión de Carlos, no de Jorge.

## 10. Conclusión y defensa

### Conclusión

- **Qué aportó el agente:** código por capas, formulario con CSS, pruebas y documentación.
- **Qué revisó o corrigió el equipo:** Jorge aprobó pasos, pidió código explicable,
  estilos y uso exclusivo de 8080; Carlos revisó y aprobó #25.
- **Qué no se aceptó automáticamente:** Jorge pidió retirar la primera implementación
  y reiniciar por sesiones; también corrigió el uso temporal de 8081.
- **Decisión humana más importante:** mantener un flujo acotado, sin login ni roles
  todavía no acordados, y avanzar con revisión de cada paso.
- **Cómo se verificó:** pruebas de capas, revisión visual, prueba manual y H2
  confirmados por Jorge, y revisión de PR por Carlos.

### Estado final

- [x] Issue implementada e integrada mediante PR revisada.
- [x] Build correcto y funcionalidad/persistencia comprobadas según las fuentes anteriores.
- [x] Diff revisado en #25; modelo, decisiones y límites documentados.
- [x] Evidencias organizadas con fuentes explícitas conforme a la plantilla oficial.
- [x] Adaptación documental a la plantilla revisada por Carlos e integrada en [#28](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/28), el 20/10/2026.
- [ ] Revisión de la actualización de estados del 20/10/2026 y aceptación final de la entrega por el equipo.

No se declara terminada toda la entrega intermedia: quedan acciones de calidad,
documentación y planificación asignadas al resto del equipo.

### Resumen breve para defensa

**Issue:** #10, registro. **Agente:** construyó capas y pruebas. **Jorge:** delimitó
el alcance y el nivel, aprobó pasos y confirmó el flujo. **Carlos:** revisó #25.
**Conclusión:** registro en H2 verificable desde main, con límites conocidos;
el agente implementa y el equipo comprende, revisa y decide la integración.

### Regla final

> La IA puede proponer e implementar cambios, pero no puede aprobar su propio trabajo.
> El equipo responde por lo que revisa, verifica y justifica.
