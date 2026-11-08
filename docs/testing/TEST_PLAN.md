# TEST_PLAN — Plan de Pruebas del Proyecto

> **Ingeniería del Software II · Laboratorio 2026-2027**  
> Este documento recoge la estrategia global de pruebas del proyecto y se actualiza progresivamente durante las sesiones S6–S8 y el resto del desarrollo.
>
> **Es un único documento para todo el proyecto.** No debe duplicarse por cada issue, historia de usuario o prueba. Los casos y pruebas relevantes se registran de forma resumida en las tablas y solo se documentan con mayor detalle cuando exista una decisión, carencia, defecto o evidencia especialmente significativa.
>
> El objetivo no es maximizar el número de tests ni alcanzar un porcentaje aislado de cobertura, sino comprobar comportamientos relevantes mediante pruebas comprensibles, reproducibles y capaces de detectar defectos.

---

## 1. Identificación

- **Equipo / empresa:** ForjaBiteCompany  
- **Repositorio:** [albertojramosf06-source/ForjaBiteCompany](https://github.com/albertojramosf06-source/ForjaBiteCompany)  
- **Versión del documento:** 0.1  
- **Responsable de mantener el plan:** Carlos Ramos Cabañas  
- **Sprint / iteración actual:** Iteración 4 (propuesta en #34, pendiente de acuerdo del equipo)  
- **Última actualización:** 08/11/2026  

---

## 2. Estrategia general de pruebas

### Objetivo

> Indicar brevemente qué pretende conseguir el equipo con las pruebas automatizadas y qué comportamientos considera prioritario proteger.

Proteger el registro de usuarios (#10), único flujo funcional integrado en `main`: que un alta correcta se guarda y se recupera, y que los datos vacíos, duplicados o incorrectos se rechazan con un error controlado y sin guardar nada.

### Prioridades actuales

- Flujos funcionales críticos.
- Reglas de negocio relevantes.
- Validaciones y errores controlados.
- Persistencia y consultas significativas.
- Comportamientos propensos a fallo.
- Funcionalidades modificadas o susceptibles de regresión.

### Tipos de prueba utilizados

| Tipo | Cuándo se utiliza | Ejemplos en el proyecto |
|---|---|---|
| Unitaria | Para comprobar una clase, método o regla de negocio de forma aislada | `UsuarioControllerErrorTests` (controlador con el servicio simulado con Mockito, sin Spring) |
| Integración | Para comprobar la colaboración entre varios componentes o con persistencia | `UsuarioServiceTests` y `UsuarioRepositoryTests` (Spring + base H2 de prueba) |
| Web / controlador | Para comprobar rutas, formularios o respuestas cuando aporte valor | `UsuarioControllerTests` (formulario `/usuarios/registro` con MockMvc) |

> El equipo no tiene que utilizar todos los tipos de prueba en todas las funcionalidades. Debe seleccionar el nivel de prueba adecuado al comportamiento que quiere proteger.

---

## 3. Funcionalidades y comportamientos prioritarios

> Registrar aquí los principales flujos o comportamientos que el equipo considera relevantes para probar. No es necesario incluir todas las issues del proyecto.

| ID | Funcionalidad / comportamiento | Issue o requisito relacionado | Motivo de prioridad | Estado de pruebas |
|---|---|---|---|---|
| FP-01 | Registro de usuarios | #10 y ampliación de validaciones #36 | Único flujo integrado en `main`; reglas de negocio, datos propensos a fallo (vacíos, duplicados, límites) y persistencia | Cubierto |
| FP-02 | Registro de propietarios | #11 (pruebas en la PR #39, sin integrar) | Reutiliza el registro y añade el rol | Pendiente |
| FP-03 | Inicio de sesión | #12 (pruebas en la PR #40, sin integrar) | Credenciales y contraseña cifrada | Pendiente |

---

## 4. Catálogo de casos de prueba

> Los casos se diseñan primero en lenguaje natural. Después, cuando proceda, se automatizan con JUnit.

| ID | Funcionalidad | Criterio protegido | Tipo de caso | Entrada / situación | Resultado esperado | Prioridad |
|---|---|---|---|---|---|---|
| CP-01 | Registro de usuarios | #10: el usuario queda guardado y se puede recuperar | Válido | BD vacía; email, nombre, apellidos y DNI válidos (`ana@example.com`, `12345678Z`) | Se guarda con id y se recupera con los mismos datos | Alta |
| CP-02 | Registro de usuarios | #10: el usuario introduce sus datos de registro | Válido | BD vacía; mismos datos enviados desde el formulario `/usuarios/registro` | Redirige, muestra "Tu usuario se ha registrado correctamente." y hay 1 usuario | Alta |
| CP-03 | Registro de usuarios | #36: pasaporte con país emisor | Válido | BD vacía; pasaporte `ab12345` con país `fr` | Se guarda como `AB12345` / `FR` | Media |
| CP-04 | Registro de usuarios | #36: datos normalizados | Válido | BD vacía; `"  ANA@Example.COM  "` y DNI `12345678z` | Se guarda `ana@example.com` y `12345678Z` | Media |
| CP-05 | Registro de usuarios | #36: DNI conservado como texto | Válido | BD vacía; DNI `00000000t` | Se guarda `00000000T` sin perder los ceros | Media |
| CP-06 | Registro de usuarios | #10: no se permiten datos obligatorios vacíos | Inválido | BD vacía; cada obligatorio `null`, vacío o solo espacios | Error "Todos los campos son obligatorios." y no se guarda nada | Alta |
| CP-07 | Registro de usuarios | #10: no se permiten dos usuarios con el mismo email | Inválido | Ya existe `ana@example.com`; otro usuario con ese email (también en mayúsculas) | Error "Ya existe un usuario con ese email." y sigue habiendo 1 usuario | Alta |
| CP-08 | Registro de usuarios | #10: no se permiten dos usuarios con el mismo email | Inválido | BD vacía; dos altas simultáneas con el mismo email | Solo se guarda 1 usuario; la otra petición recibe un error controlado | Alta |
| CP-09 | Registro de usuarios | #36: email con formato válido | Inválido | BD vacía; emails mal formados (`ana`, `ana@`, `@example.com`…) | Error de email y no se guarda nada | Media |
| CP-10 | Registro de usuarios | #36: DNI con letra de control correcta | Inválido | BD vacía; `12345678A`, `1234567Z`, `X1234567L`… | Error "El DNI debe tener ocho cifras y una letra de control correcta." y no se guarda nada | Media |
| CP-11 | Registro de usuarios | #36: documento único | Inválido | Ya existe el DNI `12345678Z`; otro usuario con el mismo DNI | Error y sigue habiendo 1 usuario | Media |
| CP-12 | Registro de usuarios | #36: pasaporte con país y formato válidos | Inválido | BD vacía; pasaporte sin país, con país `XX` o con espacios o signos | Error y no se guarda nada | Media |
| CP-13 | Registro de usuarios | #36: pasaporte único por país | Inválido | Ya existe `AB12345` / `FR`; otro usuario con `AB12345` / `FR` | Error (con `DE` sí se admite) | Media |
| CP-14 | Registro de usuarios | #36: tipo de documento DNI o pasaporte | Inválido | BD vacía; tipo de documento vacío o `NIE` | Error y no se guarda nada | Media |
| CP-15 | Registro de usuarios | #36: nombre y apellidos hasta 255 caracteres | Límite | Nombre y apellidos de 255 y de 256 caracteres | 255 se acepta; 256 se rechaza | Media |
| CP-16 | Registro de usuarios | #36: email hasta 255 caracteres | Límite | Email de 255 y de 256 caracteres | 255 se acepta; 256 se rechaza | Media |
| CP-17 | Registro de usuarios | #36: parte local del email hasta 64 caracteres | Límite | Parte local de 64 y de 65 caracteres | 64 se acepta; 65 se rechaza | Media |
| CP-18 | Registro de usuarios | #36: pasaporte de 1 a 32 caracteres | Límite | Pasaporte de 1, 32 y 33 caracteres | 1 y 32 se aceptan; 33 se rechaza | Media |

### Convención

- **Válido:** comprueba comportamiento esperado con datos correctos.
- **Inválido:** comprueba que una entrada o situación incorrecta se trata de forma controlada.
- **Límite:** comprueba valores frontera o casos especialmente sensibles.
- **Regresión:** protege un comportamiento ante cambios futuros.

---

## 5. Particiones de equivalencia y valores límite

> Utilizar esta sección únicamente en los comportamientos donde estas técnicas aporten valor. No es necesario rellenarla para cada prueba.

### Particiones de equivalencia

| Dato / condición | Partición válida | Partición inválida | Casos asociados |
|---|---|---|---|
| Campos obligatorios | Con texto | `null`, vacío o solo espacios | CP-01, CP-06 |
| Email | Formato válido y no registrado | Mal formado; ya registrado | CP-01, CP-07, CP-09 |
| DNI | Ocho cifras y letra correcta, no registrado | Letra incorrecta o longitud mala; ya registrado | CP-01, CP-10, CP-11 |
| Pasaporte | País válido y de 1 a 32 letras o cifras | Sin país; país inexistente; espacios o signos; repetido en el mismo país | CP-03, CP-12, CP-13 |
| Tipo de documento | `DNI` o `PASAPORTE` | Vacío u otro tipo (`NIE`) | CP-01, CP-14 |

### Valores límite

| Dato / condición | Valores límite relevantes | Comportamiento esperado | Casos asociados |
|---|---|---|---|
| Nombre y apellidos | 255 / 256 caracteres | 255 se acepta; 256 se rechaza | CP-15 |
| Email completo | 255 / 256 caracteres | 255 se acepta; 256 se rechaza | CP-16 |
| Parte local del email | 64 / 65 caracteres | 64 se acepta; 65 se rechaza | CP-17 |
| Número de pasaporte | 1 y 32 / 33 caracteres | 1 y 32 se aceptan; 33 se rechaza | CP-18 |

---

## 6. Pruebas automatizadas implementadas

> Relacionar los casos diseñados con las pruebas JUnit existentes. Una prueba puede cubrir uno o varios casos relacionados si está justificado.

| ID caso | Clase / método JUnit | Tipo | Comportamiento protegido | Estado |
|---|---|---|---|---|
| CP-01 | `UsuarioServiceTests.registrarUsuarioConDatosCorrectos`, `UsuarioRepositoryTests.guardarYRecuperarUsuario` | Integración | Alta correcta guardada y recuperada | Implementada |
| CP-02 | `UsuarioControllerTests.registrarDesdeFormularioYMostrarConfirmacion` | Web | Alta desde el formulario con confirmación | Implementada |
| CP-03 | `UsuarioServiceTests.pasaporteNormalizadoYUnicoPorPais`, `UsuarioControllerTests.registrarPasaporteDesdeFormulario` | Integración y Web | Alta con pasaporte | Implementada |
| CP-04 | `UsuarioServiceTests.normalizarEmailNombreYDocumento` | Integración | Normalización de email, nombre y documento | Implementada |
| CP-05 | `UsuarioServiceTests.conservarCerosInicialesDelDni` | Integración | DNI sin perder ceros | Implementada |
| CP-06 | `UsuarioServiceTests.rechazarCadaCampoObligatorioVacio`, `UsuarioControllerTests.rechazarCamposVaciosYConservarDatos`, `UsuarioControllerTests.rechazarPeticionSinDocumento` | Integración y Web | Obligatorios vacíos rechazados sin guardar | Implementada |
| CP-07 | `UsuarioServiceTests.rechazarEmailDuplicado`, `UsuarioServiceTests.normalizarEmailNombreYDocumento`, `UsuarioControllerTests.rechazarEmailRepetidoDesdeFormulario`, `UsuarioRepositoryTests.baseDeDatosImpideEmailRepetido` | Integración y Web | Email duplicado rechazado | Implementada |
| CP-08 | `UsuarioControllerTests.dosRegistrosSimultaneosConMismoEmailCreanUnaSolaFila`, `UsuarioControllerErrorTests.errorDeIntegridadConservaLosDatosYSoloMuestraError` | Web y Unitaria | Duplicado simultáneo con error controlado | Implementada |
| CP-09 | `UsuarioServiceTests.rechazarEmailsMalFormados`, `UsuarioControllerTests.peticionDirectaRechazaEmailIncorrectoYLongitudExcesiva` | Integración y Web | Email mal formado rechazado | Implementada |
| CP-10 | `UsuarioServiceTests.rechazarDniIncorrectoYDuplicado`, `UsuarioControllerTests.validarDniEnServidorYConservarDocumento` | Integración y Web | DNI incorrecto rechazado | Implementada |
| CP-11 | `UsuarioServiceTests.rechazarDniIncorrectoYDuplicado`, `UsuarioRepositoryTests.baseDeDatosImpideDniRepetido` | Integración | DNI duplicado rechazado | Implementada |
| CP-12 | `UsuarioServiceTests.rechazarPasaporteSinPaisOConCaracteresInvalidos` | Integración | Pasaporte incompleto o mal formado rechazado | Implementada |
| CP-13 | `UsuarioServiceTests.pasaporteNormalizadoYUnicoPorPais`, `UsuarioRepositoryTests.baseDeDatosImpideDocumentoRepetido` | Integración | Pasaporte duplicado en el mismo país rechazado | Implementada |
| CP-14 | `UsuarioServiceTests.rechazarTipoDeDocumentoAusente`, `UsuarioServiceTests.aceptarLimitesDePasaporteYRechazarTipoDesconocido` | Integración | Tipo de documento obligatorio y conocido | Implementada |
| CP-15 | `UsuarioServiceTests.respetarLimitesDeNombreYApellidos` | Integración | Límite de nombre y apellidos | Implementada |
| CP-16 | `UsuarioServiceTests.aceptarEmailEnLimiteYRechazarElSiguienteCaracter` | Integración | Límite del email | Implementada |
| CP-17 | `UsuarioServiceTests.aceptarEmailEnLimiteYRechazarElSiguienteCaracter`, `UsuarioServiceTests.rechazarEmailDemasiadoLargo` | Integración | Límite de la parte local del email | Implementada |
| CP-18 | `UsuarioServiceTests.aceptarLimitesDePasaporteYRechazarTipoDesconocido`, `UsuarioServiceTests.rechazarPasaporteSinPaisOConCaracteresInvalidos` | Integración | Límite del pasaporte | Implementada |

### Criterios de diseño

Las pruebas implementadas deberían, cuando resulte aplicable:

- seguir una estructura comprensible **Arrange – Act – Assert**;
- tener nombres descriptivos;
- utilizar datos de prueba claros;
- contener aserciones significativas;
- comprobar un objetivo entendible;
- fallar cuando se rompe el comportamiento protegido;
- evitar dependencias innecesarias de detalles internos;
- ser repetibles desde Maven.

---

## 7. Revisión de calidad de las pruebas

> Esta sección sirve para revisar el conjunto de pruebas, no para crear una ficha por cada test.

### Checklist general

- [x] Las pruebas están relacionadas con requisitos, criterios o comportamientos relevantes.
- [x] Existen casos válidos, inválidos y límite cuando corresponda.
- [ ] Las aserciones comprueban resultados significativos.
- [ ] Las pruebas pueden fallar ante defectos reales relacionados con el comportamiento protegido.
- [ ] No existen pruebas vacías o que siempre pasen.
- [ ] No se mezclan demasiados objetivos no relacionados en una misma prueba.
- [ ] Se han detectado y corregido pruebas frágiles cuando ha sido necesario.
- [ ] El equipo comprende qué protege cada prueba relevante.
- [x] Las pruebas pueden ejecutarse de forma reproducible con Maven.

### Pruebas frágiles, débiles o poco significativas detectadas

| Prueba | Problema detectado | Decisión | Estado |
|---|---|---|---|
| `UsuarioServiceTests.rechazarCadaCampoObligatorioVacio` | Solo comprueba el tipo de excepción. Con email o documento vacíos, el error puede venir de la validación de formato y no de la de obligatorios, y la prueba no lo distingue | Modificar | Pendiente |
| `UsuarioServiceTests.rechazarDniIncorrectoYDuplicado` | Mezcla dos objetivos: DNI incorrecto y DNI duplicado | Dividir | Pendiente |
| `UsuarioServiceTests.aceptarLimitesDePasaporteYRechazarTipoDesconocido` | Mezcla dos objetivos: límites del pasaporte y tipo de documento desconocido | Dividir | Pendiente |
| `UsuarioServiceTests.respetarLimitesDeNombreYApellidos` | No comprueba que no se guarde nada después de los rechazos | Modificar | Pendiente |
| `RentalPlatformApplicationTests.contextLoads` | No tiene aserciones: solo falla si la aplicación no arranca | Mantener justificada | Pendiente |

---

## 8. Defecto deliberado — Evidencia S6

> Durante S6 el equipo debe introducir de forma controlada al menos un defecto para comprobar si una prueba es capaz de detectarlo.

- **Prueba seleccionada:**  
- **Comportamiento protegido:**  
- **Defecto introducido:**  
- **Resultado esperado:**  
- **¿La prueba falló?:** Sí / No  
- **Corrección realizada:**  
- **Resultado después de corregir:**  

### Evidencia de ejecución

```powershell
.\mvnw.cmd test
.\mvnw.cmd verify
```

- **Resultado antes del defecto:**  
- **Resultado con el defecto:**  
- **Resultado tras la corrección:**  

> La finalidad no es romper el proyecto sin motivo, sino demostrar que la prueba puede detectar el defecto que afirma proteger.

---

## 9. Ejecución reproducible del conjunto de pruebas

### Comandos de referencia

```powershell
.\mvnw.cmd test
.\mvnw.cmd verify
```

### Estado actual

- [x] El conjunto de pruebas puede ejecutarse desde Maven.
- [x] El build completo finaliza correctamente.
- [x] Los fallos existentes están identificados y documentados.
- [x] No existen pruebas deshabilitadas o eliminadas únicamente para obtener un build verde.

### Última ejecución

- **Fecha:**  8/11/2026
- **Resultado:** BUILD SUCCESS  Tests run: 29, Failures: 0, Errors: 0, Skipped: 0
- **Incidencias:**  Ninguna

---

## 10. Verificación y relación con cobertura y calidad

> Esta sección se completa progresivamente durante S7 y S8.
>
> **Importante:** la cobertura y Sonar aportan evidencias complementarias, pero no sustituyen el diseño de buenos casos de prueba. El **Hito 2** se cierra en **S7** con pruebas y verificación automatizada; Sonar pertenece a la dimensión de **Calidad** y prepara información para el mantenimiento posterior.

### 10.1 Verificación — S7 · Maven y Surefire

> Registrar aquí la evidencia mínima de que el conjunto de pruebas puede ejecutarse de forma reproducible fuera del IDE.

- **Fecha de verificación:**  
- **Rama / commit verificado:**  
- **Comandos ejecutados:**

```powershell
.\mvnw.cmd test
.\mvnw.cmd verify
```

- **Resultado de `test`:**  
- **Resultado de `verify`:**  
- **Número de tests ejecutados:**  
- **Fallos o errores relevantes:**  
- **Informe Surefire revisado:** `target/surefire-reports/`  
- **Evidencia / enlace:**  

### 10.2 Cobertura — S7 · JaCoCo

- **Informe generado:** `target/site/jacoco/index.html`  
- **Cobertura observada de forma global:**  
- **Clase / paquete / comportamiento revisado:**  
- **Zonas importantes con cobertura insuficiente:**
  - 
  - 

- **Ramas o casos relevantes no ejercitados:**
  - 
  - 

- **Justificación de zonas no cubiertas cuando proceda:**
  - 
  - 

> No se exige perseguir un porcentaje concreto por sí mismo. La cobertura debe interpretarse en relación con comportamientos, requisitos, casos de prueba y riesgos reales.

### 10.3 Interpretación y decisión — S7

> La cobertura debe provocar una decisión técnica explícita, aunque la decisión sea no añadir ninguna prueba.

- **Carencia o riesgo detectado:**  
- **Caso(s) del plan afectados:**  
- **Decisión:** Añadir prueba / Mejorar prueba / Mantener sin cambios / Justificar no cobertura  
- **Cambio realizado:**  
- **Motivo:**  
- **Resultado tras repetir `test` / `verify`:**  
- **Relación con AI-02, si procede:**  
- **Evidencia / enlace:**  

### 10.4 Sonar — S8 · Contexto para nuevas necesidades de prueba

- Problemas o incidencias que sugieren añadir o revisar pruebas:
  - 
  - 

- Pruebas añadidas o modificadas como consecuencia del análisis de calidad:
  - 
  - 

> **La cobertura no se interpreta como un objetivo aislado.** Debe relacionarse con requisitos, casos de prueba, defectos que se pretende detectar y zonas relevantes del código.

---

## 11. Pendientes y evolución del plan

| Pendiente / mejora | Motivo | Prioridad | Responsable | Estado |
|---|---|---|---|---|
| Defecto deliberado (§8) | Exigido en S6 para demostrar que una prueba detecta el defecto que protege | Alta | Carlos | Pendiente |
| Corregir las pruebas débiles de §7 | Deben fallar por la razón correcta | Media | Carlos | Pendiente |
| Añadir casos y pruebas del registro de propietarios (FP-02) | Sus pruebas están en la PR #39, sin integrar en `main` | Alta | Carlos | Pendiente |
| Añadir casos y pruebas del inicio de sesión (FP-03) | Sus pruebas están en la PR #40, sin integrar en `main` | Alta | Carlos | Pendiente |
| Selector de documento con JavaScript | Las pruebas web usan MockMvc, sin navegador | Baja | Equipo | Pendiente |
| Revisar los casos si el cliente cambia los datos obligatorios o el documento | Modelo de usuario provisional, pendiente de #15 | Media | Equipo | Pendiente |

El plan debe actualizarse cuando:

- se incorporen nuevas funcionalidades relevantes;
- cambien criterios o reglas de negocio;
- se detecten defectos significativos;
- se añadan o eliminen pruebas importantes;
- se detecten pruebas frágiles;
- JaCoCo muestre zonas relevantes no ejercitadas;
- la verificación S7 produzca una decisión sobre añadir, mejorar o justificar pruebas;
- Sonar revele nuevas necesidades de verificación;
- cambie la estrategia de pruebas del equipo.

---

## 12. Resumen del estado de pruebas

> Completar en 3–5 líneas y mantenerlo actualizado.

- **Principales comportamientos protegidos:** registro de usuarios (#10 y #36): obligatorios, email y documento únicos, formato y límites de los datos, y persistencia.  
- **Pruebas automatizadas relevantes:** 29 pruebas en `UsuarioServiceTests`, `UsuarioRepositoryTests`, `UsuarioControllerTests`, `UsuarioControllerErrorTests` y `RentalPlatformApplicationTests`.  
- **Carencias actuales:** pruebas que mezclan objetivos o no comprueban el mensaje (§7); defecto deliberado sin hacer; registro de propietarios e inicio de sesión sin integrar.  
- **Próxima prioridad de pruebas:** defecto deliberado (§8) y corrección de las pruebas débiles de §7.  
- **Estado general:** En progreso