# Sesión 1: organización y planificación inicial

Revisión del 14 de octubre de 2026. Fuentes: presentación S1 (páginas 19-29 y
36-40), Proyecto2026 y avisos de la profesora facilitados por Jorge; repositorio,
issues y Project consultados con GitHub CLI. No sustituye decisiones del cliente.

> **Documento histórico:** describe la revisión del 14/10/2026, anterior a #25.
> Las menciones a ausencia de JPA/capas no describen el código actual. Para el
> registro integrado y sus pruebas, consultar [base técnica](technical/README.md),
> [AI-01](ai/AI-01-hito-funcional.md) y [modelo](diagrams/registro-usuarios.md).

## Empresa y acceso

Nombre confirmado: ForjaBiteCompany. El README recoge los cinco integrantes,
portavoz y responsabilidades iniciales. GitHub confirma acceso al repositorio de
JorgeMartinSanchez, albertojramosf06-source, alexxjgo, DIegolb46 y Raamoos.
Jorge confirma correo de constitución enviado con todos en copia y acceso de los
cinco al Project. Estas dos confirmaciones son del usuario, no comprobaciones de
correo o permisos realizadas por el agente.

## Comprensión del encargo

Los propietarios registrados y autenticados publican viviendas o habitaciones y
eligen reserva inmediata o confirmación de solicitudes. Se busca alojamiento sin
necesidad de iniciar sesión. Los inquilinos autenticados reservan y pagan; el
propietario decide sobre las solicitudes. Se notifican los resultados y se
devuelve el pago si la solicitud se rechaza. También se contemplan lista de
deseos, comodidades y política de cancelación como filtros.

El diseño del cliente es un punto de partida que se puede cuestionar con
justificación. No se ha acordado todavía el modelo definitivo de usuarios y roles.
La issue #15 distingue preguntas pendientes de aclaraciones del enunciado.
No se inventan respuestas sobre pagos simulados, campos obligatorios ni reglas
de cancelación. Las dudas abiertas no impiden organizar el trabajo de S1.

Calendario de la presentación S1: entrega intermedia 21/10/2026 y final
16/12/2026, sujeto a actualizaciones del profesorado.

## Primer incremento provisional

Jorge acepta el objetivo: registro de usuarios persistente y comprobable (#10).
Antes de implementar, verificar la base de #13, acordar los campos y revisar el
plan humano. Después: registro a través de las capas, validaciones, pruebas,
evidencia IA y revisión humana. Este orden es planificación, no implementación.

Se mantienen los responsables, prioridades y estados actuales. #13 está cerrada
y en Done, aunque main aún no contiene las capas ni JPA/H2 descritas en sus
criterios; debe contrastarse su cierre con el equipo antes de cambiar su estado.
La configuración JPA/H2 existe en feat/10-user-persistence, sin flujo funcional.
El siguiente trabajo del producto se priorizará por valor y dependencias: #11,
#12, #5 y #14; #4 necesita concretar disponibilidad y reservas.

No se obliga a desglosar todo el producto en S1. Reservas, pagos, notificaciones
y demás funciones se incorporarán progresivamente al backlog. La iteración y su
duración siguen pendientes de acuerdo del equipo; no se asignan fechas inventadas.

## Comprobación de cierre

- [x] Nombre, integrantes, portavoz y responsabilidades iniciales documentados.
- [x] Correo de constitución y acceso al Project confirmados por Jorge.
- [x] Repositorio y colaboradores comprobados en GitHub.
- [x] README, AGENTS y estructura de AI_USAGE existentes.
- [x] Historias iniciales con criterios, responsables y prioridades en el Project.
- [x] Objetivo provisional y dependencia del primer incremento acordados con Jorge.
- [x] Dudas recogidas sin presentarlas como requisitos resueltos.
- [ ] Revisar y aceptar esta documentación con el equipo.
- [ ] Resolver o mantener explícitas las preguntas pendientes con el cliente.
- [ ] Concretar iteración y fechas de trabajo con el equipo.

Las preguntas pendientes y la evolución posterior del backlog no exigen
implementar funcionalidades para cerrar S1. No se declara terminado el Hito 1.
La issue #17 se conserva intacta por indicación de Jorge. Las tareas #18 y #20 y
la PR #19 se mantienen como trabajo existente y no se duplican.
