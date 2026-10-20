# Plan de Calidad — Versión inicial

> **Objetivo:** definir de forma preliminar qué entendemos por calidad en el proyecto, qué problemas, limitaciones y deuda técnica conocemos y qué acciones se irán realizando durante su evolución.

Esta versión inicial se prepara antes de la entrega intermedia con carácter formativo. **El Plan de Calidad se evaluará íntegramente en la entrega final**, una vez incorporadas las evidencias de Sonar y la evolución del proyecto.

> **Actualización de estados — 20/10/2026:** se conservan responsables y fechas
> del plan inicial. La actualización anterior fue aprobada por Alex e integrada
> mediante [#31](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/31). La reconciliación posterior fue
> aprobada por Alex e integrada en [#37](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/37) el 20/10/2026.
> Esta nota registra hechos comprobados, no la aceptación final de toda la entrega.

> **Ampliación integrada del 20/10:** validación de email y documento,
> normalización y límites revisados por Alex e integrados en [#36](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/36).
> Se actualizan las tablas con las mejoras resueltas y los límites aún vigentes.
> Reglas actuales y límites: [decisiones](../decisions/registro-usuarios.md).

## 1. Identificación

- **Equipo / empresa:** ForjaBiteCompany
- **Fecha:** 17/10/2026
- **Versión original:** 0.1 (17/10/2026).
- **Revisión:** 0.2 (20/10/2026), integrada por #37 tras revisión de Alex.
- **Nota de mantenimiento:** las verificaciones posteriores se registran en la base técnica y AI_USAGE; esta tabla conserva las fechas originales.
- **Responsable de mantener el plan:** Carlos Ramos Cabañas
- **Iteración citada en el plan inicial:** Iteration 3, Oct 14 - Oct 26.
  El Project configura inicio 14/10 y duración 14 días; la discrepancia de fechas
  queda pendiente de acuerdo en #34. No se cambia el calendario del equipo.

---

## 2. Alcance del Plan de Calidad

Este plan se aplica al estado actual del proyecto y se irá actualizando a medida que se incorporen nuevas técnicas y herramientas.

En este momento se centra en:

- fiabilidad básica de los flujos funcionales;
- mantenibilidad de la arquitectura;
- coherencia entre modelo, código y documentación;
- gestión correcta de configuración e integración;
- visibilidad y seguimiento de deuda técnica, limitaciones y problemas conocidos;
- protección de credenciales y datos sensibles;
- preparación para las sesiones posteriores de pruebas, verificación automatizada, cobertura, Sonar y mantenimiento de la calidad.

> En esta versión inicial todavía no se establecen objetivos de cobertura ni resultados de Sonar, ya que estas técnicas se introducirán en sesiones posteriores.
>
> Esta versión se revisa en la entrega intermedia con finalidad formativa; la dimensión de **Calidad se evalúa al 100 % en la entrega final**.

---

## 3. Objetivos de calidad

### 3.1. Fiabilidad

Objetivo:

> Los flujos funcionales implementados deben poder ejecutarse de forma repetible y sin errores no controlados.

Criterios actuales:

- la aplicación arranca correctamente desde `main`;
- el build desde limpio finaliza correctamente;
- los datos persistentes se guardan y recuperan correctamente;
- las entradas inválidas reciben un tratamiento controlado;
- los errores conocidos no se ocultan.

---

### 3.2. Mantenibilidad

Objetivo:

> El código debe mantener una estructura comprensible y coherente con la arquitectura definida.

Criterios actuales:

- separación clara entre Controller, Service, Repository y entidades;
- la lógica de negocio no se concentra en los controladores;
- las clases y métodos tienen responsabilidades acotadas;
- las issues se mantienen con un alcance razonable;
- los cambios incluidos en una PR son comprensibles y están relacionados con su issue;
- las decisiones técnicas relevantes quedan documentadas.

---

### 3.3. Trazabilidad y configuración

Objetivo:

> Todo cambio relevante debe poder seguirse desde su requisito o issue hasta su integración.

Criterios actuales:

- cada funcionalidad relevante tiene issue;
- las ramas siguen la convención del proyecto;
- existe una Pull Request para integrar cambios;
- otra persona revisa la PR;
- `main` no recibe cambios directos injustificados;
- GitHub Project refleja el estado real del trabajo;
- la versión entregada puede identificarse y recuperarse.

---

### 3.4. Seguridad básica

Objetivo:

> El repositorio no debe contener información sensible ni configuraciones inseguras evidentes.

Criterios actuales:

- no se versionan contraseñas;
- no se versionan tokens;
- no se incluyen códigos temporales de autenticación;
- no se incluyen datos personales innecesarios;
- los archivos locales o sensibles se excluyen mediante `.gitignore` cuando corresponda.

---

### 3.5. Usabilidad mínima

Objetivo:

> La interfaz puede ser sencilla, pero debe permitir demostrar correctamente los casos de uso implementados.

Criterios actuales:

- los flujos principales son accesibles;
- los formularios muestran los campos necesarios;
- los mensajes de error son comprensibles;
- el resultado de una operación puede comprobarse;
- la demo puede repetirse desde una versión recuperada de `main`.

---
## 4. Problemas, limitaciones y aspectos pendientes

| Aspecto pendiente | Tipo | Acción prevista | Responsable | Estado |
|---|---|---|---|---|
| Modelo de usuario provisional: el diagrama del cliente incluye login, contraseña y dirección; el registro actual guarda email, nombre, apellidos y DNI español o pasaporte con país emisor | Diseño / Dependencia | Trasladar al cliente las dudas de #15 y actualizar el modelo y `docs/diagrams` según su respuesta | Jorge (portavoz) | Pendiente |
| Se solicita y almacena un documento de identidad, cuya necesidad no ha confirmado el cliente | Seguridad básica | Confirmar con el cliente (#15) si es necesario el documento; la validación pedida por Jorge no sustituye esa respuesta | Equipo | Pendiente |
| Adaptación de AI-01 a la plantilla oficial (carencia detectada el 17/10) | Documentación | Completar `docs/ai/AI-01-hito-funcional.md` con la plantilla | Jorge (Carlos aporta Verificación y Revisión) | Resuelto: [#28](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/28), integrada el 20/10 |
| README con estado "Sprint 0" (carencia detectada el 17/10) | Documentación | Actualizar el estado y revisar las instrucciones antes de la entrega | Diego | Estado corregido en [#30](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/30), integrada el 20/10; estilos y validaciones integrados en #36; reconciliación documental integrada en #37 |
| `main` sin protección técnica (`protected: false`); las normas se aplican mediante revisión humana | Configuración | Activar la protección si el plan de GitHub lo permite o documentar la limitación | Alberto | Pendiente |
| Construcción de `main` tras cada integración | Integración | Ejecutar `.\mvnw.cmd clean verify` antes y después de cada merge | Todo el equipo | Agente: clean verify en main `1258bb7`, 20/10, 22 pruebas correctas; se conserva la comprobación humana del 17/10 |
| Información sensible en el repositorio | Configuración | Revisar `.gitignore` y el diff antes de cada commit; `application.properties` solo contiene la configuración H2 de laboratorio | Todo el equipo | Revisado 17/10 |

---

## 5. Deuda técnica conocida

Registrar únicamente deuda que el equipo conoce y ha decidido posponer de forma consciente.

| Deuda técnica | Motivo por el que se pospone | Prioridad | Acción futura |
|---|---|---|---|
| Validaciones de email y DNI ausentes en #25 (deuda histórica resuelta en #36) | Se pospusieron al acotar el primer incremento; Jorge autorizó después ampliarlas | Media (histórica) | Formato y normalización de email, límites y letra/unicidad de DNI implementados y probados; no implica reglas definitivas del cliente |
| Pasaportes con regla básica de 1 a 32 letras/cifras y país ISO; sin autenticidad ni formato oficial por país | Alcance básico aceptado por Jorge para pasaportes extranjeros | Media | Concretar con el cliente reglas por país si se necesitan |
| Sin verificación de buzón ni identificación de la misma persona con documentos distintos | No se implementa una comprobación de identidad | Media | Acordar el nivel de verificación antes de ampliar el alcance |
| Formulario documental dependiente de JavaScript | El selector muestra solo los campos del documento elegido | Baja | Valorar alternativa sin JavaScript si se requiere |
| Sin migración para una BD antigua persistente | H2 actual es temporal y el nuevo esquema se crea al arrancar | Baja | Preparar migración antes de utilizar datos duraderos de un esquema anterior |
| Base de datos H2 en memoria: los datos se pierden al parar la aplicación | Es la base de datos de laboratorio de S4 y basta para demostrar la persistencia | Baja | Valorar una base de datos persistente cuando el proyecto lo necesite |
| `ddl-auto=update`, `show-sql=true` y consola H2 activados | Facilitan el desarrollo; no son configuración de producción (S4 diap. 15) | Baja | Mantenerlos solo para desarrollo y revisarlos antes de la entrega final |
| Sin inicio de sesión ni roles (inquilino / propietario) | Fuera del alcance del Hito 1; depende de #11, #12 y de las respuestas de #15 | Media | Abordarlo en las issues #11 y #12 |

> La deuda técnica no es un error oculto. Debe ser visible, justificada y planificada.

---

## 6. Criterios de revisión actuales

Antes de considerar una issue lista para integrarse, se comprobará:

- [ ] tiene criterios de aceptación;
- [ ] el cambio es trazable y está correctamente aislado cuando corresponde;
- [ ] el código compila;
- [ ] el proyecto ejecuta el build acordado;
- [ ] existe una Pull Request cuando el cambio vaya a integrarse en `main`;
- [ ] otra persona ha revisado el cambio;
- [ ] el equipo comprende los cambios realizados;
- [ ] la documentación afectada está actualizada;
- [ ] el modelo está actualizado cuando corresponde;
- [ ] el uso relevante de IA está declarado;
- [ ] no se han incluido credenciales o datos sensibles.

---

## 7. Acciones iniciales antes de la entrega intermedia

| Acción | Responsable | Fecha límite | Estado |
|---|---|---|---|
| Comprobar `main` desde limpio | Carlos | 17/10/2026 | Hecho |
| Cerrar e integrar el Hito 1 | Jorge (revisión: Carlos) | 20/10/2026 | Implementación integrada en [#25](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/25), 17/10; ficha integrada en [#28](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/28), 20/10. No equivale al cierre de toda la entrega |
| Completar `AI-01-hito-funcional.md` | Jorge (Carlos: Verificación y Revisión) | 20/10/2026 | Hecho: [#28](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/28), revisión de Carlos e integración el 20/10 |
| Añadir la skill S5 "Review change before integration" a `AGENTS.md` | Alex | 20/10/2026 | Hecho: [#29](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/29), integrada el 20/10 |
| Revisar README y documentación | Diego | 20/10/2026 | README y AI_USAGE integrados en [#30](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/30), 20/10; ampliación #36 integrada; reconciliación integrada en #37 |
| Actualizar modelo/diagramas | Jorge | 15/10/2026 | Hecho (`docs/diagrams/registro-usuarios.md`) |
| Revisar problemas pendientes y deuda técnica | Carlos | 17/10/2026 | En curso (este plan) |
| Poner el Project al día y replanificar el resto de issues | Alberto | 20/10/2026 | #16 cerrada y Done; #32–#33 descartadas; #34 Ready para replanificar. Acuerdos de responsables/iteraciones pendientes |
| Identificar versión intermedia | Jorge | 21/10/2026 | Código integrado identificado: `1258bb7289c49d898336e8163368434afc24afd4`. #33 cerrada como no prevista; no se declara creada una release ni un tag |
| Preparar demo | Todo el equipo | 20/10/2026 | #32 descartada por Jorge como tarea adicional; la demostración se abordará en la defensa. Jorge confirma funcionamiento de aplicación y H2; no se afirma ensayo colectivo |

---

## 8. Preparación para el siguiente bloque de trabajo

Antes de la Sesión 6, el equipo debería identificar:

- **Flujo más crítico:** registro de usuarios (`/usuarios/registro` → `UsuarioController` → `UsuarioService.registrarUsuario`).
- **Comportamientos más propensos a fallo:** email repetido (también en registros simultáneos, controlado por la restricción única de la base de datos) y campos vacíos o con solo espacios.
- **Entradas válidas:** email válido no registrado, nombre y apellidos no vacíos y documento válido no repetido. Para pasaporte, país obligatorio.
- **Entradas inválidas:** obligatorios `null`/vacíos/espacios, email incorrecto o duplicado, longitudes excesivas, DNI incorrecto/repetido y pasaporte sin país válido o con número inválido/repetido.
- **Casos límite:** normalización y duplicados simultáneos; 255/256 caracteres en nombre y apellidos, 64/65 en parte local del email, 32/33 en pasaporte; mismo pasaporte con países distintos. La revisión de pruebas incluye límites y dos peticiones concurrentes con el mismo email, además de error de integridad del controlador. No se declara cobertura exhaustiva ni una prueba de carga.
- **Zonas que necesitarán mayor verificación:** las validaciones de `UsuarioService` y la gestión de errores de `UsuarioController`.

Estos elementos servirán como punto de partida para:

- **S6:** diseño e implementación de pruebas con JUnit;
- **S7:** verificación automatizada con Maven, Surefire y JaCoCo;
- **S8:** análisis de calidad con Sonar;
- **Hito 2:** pruebas y verificación con IA agéntica.

El equipo deberá incorporar estas actividades a sus propios sprints o iteraciones, de acuerdo con su planificación y estado real del proyecto.

---

## 9. Revisión del plan

Este documento debe actualizarse cuando:

- cambie el alcance del proyecto;
- aparezcan nuevos problemas, limitaciones o dependencias relevantes;
- se detecte nueva deuda técnica;
- se incorporen pruebas;
- se obtenga la línea base de Sonar;
- se introduzcan métricas o criterios nuevos;
- se cierre una acción de calidad relevante.

### Próxima revisión prevista

- **Fecha / sesión:** Sesión 6 (inicio del bloque de pruebas)
- **Responsable:** Carlos Ramos Cabañas

---

## 10. Resumen

Ahora mismo los objetivos de calidad del equipo son que el registro de usuarios funcione de forma fiable y repetible desde `main`, mantener la separación por capas y la trazabilidad issue → PR → revisión. La limitación más importante es que el modelo de usuario es provisional y depende de las respuestas del cliente (#15), lo que afecta a las validaciones y al uso del DNI. El 17/10 se identificó como prioridad completar AI-01; la ficha ya está integrada mediante #28. La ampliación #36 está integrada y el código se identifica por `1258bb7`, con 22 pruebas correctas. #16 está cerrada; las tareas adicionales #32 y #33 fueron descartadas, no completadas. Quedan acuerdos de planificación en #34 y aclarar el acta de seguimiento; #35 recoge el seguimiento posterior. Son decisiones o evidencias pendientes, no fallos de código que deban rellenarse por suposición. La nueva petición de preparar un PDF es material de estudio, no evidencia de un ensayo colectivo. Se conserva el historial y no se declara cerrada toda la entrega.