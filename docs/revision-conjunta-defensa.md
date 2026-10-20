# Revisión conjunta previa al material de defensa

Fecha: 20/10/2026 (Europe/Madrid). Revisión solicitada por Jorge, preparada con
asistencia de Codex. Este documento es una instantánea de la revisión, no una
aprobación automática de la entrega ni una instrucción para cambiar el alcance.

## Referencia y alcance

- Main integrado consultado: `614bae73db51b60a6b6c717806fa76dfbb93ea89` (#37).
- #37 aprobada por Alex e integrada; #36 ya contiene estilos y validaciones.
- Esta revisión conserva la lógica de producción y amplía pruebas y documentación
  en la rama `codex/revision-final-defensa`. Tras completar la revisión, Jorge
  autoriza su publicación en una PR; aprobación e integración corresponden al equipo.
- Archivos inspeccionados: clases Java, vistas, CSS, JavaScript, configuración H2,
  pom, Wrapper, pruebas, README, AGENTS, AI_USAGE y documentos de docs.
- Evidencias remotas contrastadas: PR #37, criterios de #10, Project, #34 y #35.
  Material docente consultado previamente: S1-S5 y avisos aportados por Jorge.

## Correcciones aplicadas en un solo bloque

| Hallazgo | Resolución |
| --- | --- |
| README proponía una recuperación con reset y rama de respaldo insuficiente para cambios sin commit | Flujo de diagnóstico sin comandos destructivos; explica qué conserva una rama y cómo preservar trabajo antes de decidir |
| Notas de preparación de #37 podían confundirse con su estado ya integrado | Se registra su aprobación e integración como hecho fechado; AI_USAGE mantiene el historial |
| Texto histórico de S1 podía interpretarse como código actual sin JPA | Encabezado histórico con referencias a base técnica, AI-01 y modelo vigentes |
| Límites exactos de email no comprobados | Prueba de 255 caracteres admitidos y 256 rechazados, con parte local de 64; se conserva el rechazo previo de 65 |
| Ceros iniciales y tipo de documento ausente sin caso dedicado | Nuevas pruebas del servicio |
| DNI único solo probado en servicio | Nueva prueba de restricción real de BD |
| Registros simultáneos no ejecutados en pruebas | Dos peticiones con variantes de mayúsculas: una fila y respuestas 200/302 |
| Rama de error de integridad del controlador no probada directamente | Prueba con servicio simulado que lanza la excepción; comprueba vista, error, seis datos conservados y ausencia de éxito |
| Petición directa sin documento sin prueba dedicada | Error controlado y ninguna fila creada |

## Verificación y conclusión técnica

Comando: `.\mvnw.cmd clean verify`. Resultado: **BUILD SUCCESS; 29 pruebas,
0 fallos, 0 errores y 0 omitidas**. Informes reproducibles en
`target/surefire-reports/`, no versionados.

| Grupo | Pruebas |
| --- | --- |
| Arranque | 1 |
| Repositorio y restricciones H2 | 4 |
| Servicio y reglas de registro | 14 |
| Web de integración con MockMvc | 9 |
| Controlador ante error de integridad simulado | 1 |

Los errores de unicidad que aparecen en la salida SQL de las pruebas negativas
son provocados y esperados; su resultado lo determinan las aserciones, no la
mera presencia de la palabra error en el log.

Se conservan identificador generado, columnas no nulas, email único y restricción
compuesta de documento. El servicio normaliza y valida; el controlador coordina.
No se añaden relaciones JPA, dependencias, autenticación ni funcionalidades.
No se ha encontrado un fallo funcional nuevo en el alcance acordado del registro.
La revisión y las pruebas no constituyen una garantía de ausencia absoluta de defectos.

## Límites acordados que permanecen visibles

- H2 en memoria pierde registros al detenerse. Los tests usan otras bases.
- Pasaportes: regla básica alfanumérica y país, sin autenticidad ni formato oficial
  de cada país. No se detecta una persona que usa documentos diferentes.
- No hay comprobación de existencia del buzón, login, roles, inmuebles o reservas.
- El selector depende de JavaScript. El navegador no sustituye la validación del servidor.
- No se incluye migración para datos duraderos de un esquema antiguo.
- La consulta previa de duplicados no elimina una carrera; la restricción de BD
  protege el guardado y el controlador comunica un error controlado.
- Main sin protección técnica y sin evidencia de ejecuciones GitHub Actions en la
  consulta: normas de revisión aplicadas por el equipo, no impuestas por checks.

## Pendientes que requieren una decisión o evidencia humana

No se rellenan por suposición ni se presentan como errores de implementación:

1. #34: acordar responsables, fechas e iteraciones. Conviene incluir el siguiente
   bloque de pruebas, verificación y calidad de S6-S8, además de dependencias de
   las funcionalidades futuras. La discrepancia de Iteration 3 está documentada.
2. Acta de seguimiento: localizar el documento existente o aclarar con el
   profesorado qué seguimiento y momento corresponde. #35 trata el posterior.
3. #15: respuestas reales del cliente sobre roles, datos, pagos y otras reglas.

El Project consultado mantiene siete elementos Done, #34 Ready y siete Backlog.
#32 y #33 fueron descartadas por Jorge, no terminadas. No se reabren por crear
material de estudio. El usuario confirma que aplicación y H2 funcionan; no se
inventa un ensayo colectivo ni su reparto de intervenciones.

## Preparación del PDF

La parte técnica y los documentos permiten redactar el material de estudio.
Debe separar el hito original de #25 (nueve pruebas), la ampliación #36 (22) y
esta verificación local (29). Las modificaciones locales de esta revisión no
deben presentarse como integradas hasta que el equipo las revise e integre.
El PDF explicará decisiones y pendientes con fuentes; no sustituye la demo ni
prueba que cada integrante haya comprendido el código. Esa comprensión se practica.
