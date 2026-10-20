# Plan de Gestión de Configuración

Propuesta del equipo para la issue #20, preparada el 15 de octubre de 2026.

Actualización de estado — 20/10/2026: el plan fue revisado por Alex y Alberto e
integrado el 15/10 mediante [#24](https://github.com/albertojramosf06-source/ForjaBiteCompany/pull/24). Las reglas siguientes corresponden
al plan integrado. La presente actualización documental necesita revisión propia.

## Rama principal y contrato Git

`main` representa la versión integrada y recuperable del proyecto.

- No se hace push directo a `main`.
- Todo cambio relevante tiene trabajo asociado en una issue o tarea identificada.
- Todo cambio que afecte a `main` llega mediante Pull Request.
- Toda PR relevante la revisa otra persona distinta de su autor.

## Ramas y commits

Crear cada rama desde `main` actualizada, con un propósito concreto y sin mezclar
tareas independientes. Convención: `<tipo>/<issue>-descripcion-corta`; tipos
habituales: `feat`, `fix`, `docs`, `refactor` y `chore`.
Ejemplo: `docs/20-plan-configuracion`. Las ramas creadas mediante Codex usan
`codex/<tipo>-<issue>-descripcion`, como `codex/docs-20-configuration-plan`.
No se renombran por este motivo las ramas existentes.

Los commits describen un cambio coherente: `docs: document configuration plan (#20)`.
Evitar mensajes como `cambios` o `prueba2`. Revisar `git diff` y después
`git diff --staged`; añadir solo los archivos relacionados con la tarea.

## Flujo trazable

1. Leer descripción, criterios, responsables, prioridad y dependencias de la issue.
2. Comprobar si otra persona ya ha iniciado una rama o PR del mismo trabajo.
3. Actualizar `main`, revisar `git status` y proteger cualquier trabajo pendiente.
4. Crear la rama y reflejar el trabajo real en el Project: In progress.
5. Realizar cambios acotados y revisar el diff. Si interviene un agente, revisar
   su plan antes de editar, conforme a AGENTS.md.
6. Ejecutar `.\mvnw.cmd verify`; probar el flujo manual cuando corresponda.
7. Actualizar documentación y registrar el uso de IA si ha influido en el trabajo.
8. Hacer commit y push de la rama; abrir PR vinculada a la issue.
9. Pasar a In review cuando el cambio esté preparado para revisión.
10. Atender observaciones, verificar de nuevo las correcciones y obtener revisión humana.
11. Integrar mediante merge solo si se cumplen criterios, verificación y revisión.
12. Cerrar la issue y pasar a Done al comprobar el cierre real del trabajo.

Los estados describen trabajo real; no se alteran para simular avances. El
responsable puede coordinar trabajo realizado en grupo. Este plan no reasigna
responsabilidades ni cambia estados automáticamente.

## Pull Requests y política de revisión

La PR debe explicar el problema y cambio, enlazar la issue, indicar comandos y
resultados de verificación y recoger decisiones, limitaciones y uso de IA.
Usar `Closes #20` solo si la propuesta cubre todos los criterios de esa issue.
Una PR en borrador permite preparar el cambio; pasar a Ready for review antes
de solicitar aprobación formal.

El revisor comprueba criterios, alcance, legibilidad, cambios accidentales,
documentación y evidencia del build. Puede comentar, pedir cambios o aprobar;
la decisión debe quedar registrada en GitHub y corresponder a una revisión real.
El autor responde y corrige cuando proceda. Un agente puede analizar y preparar
la propuesta, pero no aprobar ni integrar sus cambios.

## Protección de main

En la comprobación del 15/10/2026, GitHub devuelve `protected: false` para main.
Las reglas anteriores son el acuerdo de trabajo; no están impuestas por una
protección técnica en este momento. No se declara activada ninguna regla.

Configuración recomendada para revisar con el propietario: exigir PR y al menos
una aprobación de otra persona. Su disponibilidad depende del plan y permisos
del repositorio privado; si no está disponible, documentar la limitación y aplicar
la política mediante revisión humana. Los checks obligatorios se incorporarán
cuando haya automatización acordada. Esta tarea no cambia permisos ni reglas
del repositorio.

## Conflictos

Antes de resolver un conflicto, identificar los cambios de ambas ramas y hablar
con el integrante afectado. Acordar qué comportamiento y documentación deben
conservarse; no elegir automáticamente nuestra versión ni la remota.

Resolver las marcas de conflicto en una rama de trabajo, revisar el diff y
ejecutar verify y las pruebas pertinentes. Registrar la resolución y actualizar
la PR. Si hay dudas o riesgo de perder trabajo, parar y consultar. No utilizar
`reset --hard` ni push forzado como solución rutinaria.

## Archivos excluidos

Se versionan código, pruebas, configuración necesaria, Maven Wrapper y
documentación viva. El `.gitignore` existente excluye `target/`, clases, logs,
configuración personal de IDE, archivos del sistema, `.env` y
`application-local.properties`. Se conserva la excepción del JAR del Wrapper.

No subir credenciales, dependencias reconstruibles ni respaldos personales.
Antes de preparar archivos, revisar `git status`; los materiales de clase o
capturas locales no se añaden automáticamente. Revisar cualquier incorporación
con el equipo. `.gitignore` no elimina archivos ya versionados.

## Integración y recuperación local

La integración la decide el equipo después de la revisión. Tras el merge,
comprobar cierre de la issue y Project, y actualizar la copia local:

```powershell
git switch main
git pull --ff-only
git status
```

Si hay cambios locales, preservarlos y revisarlos antes de cambiar de rama. Si
`--ff-only` falla por divergencia, analizar los commits con el equipo; no borrar
trabajo para forzar la actualización. Las ramas ya integradas se pueden eliminar
cuando no contengan trabajo pendiente. Mantener historial y evidencias de revisión.

## Verificación histórica de la propuesta del 15/10/2026

Se han contrastado los criterios de #20, la presentación S3 y el flujo existente.
El resultado de Maven se registra en la PR; no se modifican código ni dependencias.
