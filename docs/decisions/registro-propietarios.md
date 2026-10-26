# Decisiones del registro de propietarios (#11)

Fecha: 26/10/2026. Decisión provisional, pendiente de revisión del equipo en la PR.

## Alcance

Se sigue #11: registrar propietarios con los mismos datos que los usuarios de #10
(email, nombre, apellidos y DNI español o pasaporte), rechazar campos vacíos y
email repetido, y guardar y recuperar mediante JPA. No se implementa el inicio de
sesión (#12) ni la publicación de inmuebles (#5).

## Modelo elegido

Se valoraron dos opciones:

- A) Una entidad `Propietario` con su propia tabla.
- B) Un campo `rol` en `Usuario` con los valores `INQUILINO` o `PROPIETARIO`.

Se elige la B. #12 pide acceder según el rol, y con una sola tabla el login busca
en un único sitio. Además se reutilizan las validaciones de #10 sin duplicar código.

## Doble rol

El cliente todavía no ha respondido si una persona puede ser inquilino y
propietario a la vez (#15). El email sigue siendo único en toda la tabla, así que
un email registrado como inquilino no puede registrarse como propietario. Con la
opción A se podría registrar la misma persona en las dos tablas, lo que decidiría
el doble rol sin confirmarlo. Si el cliente lo permite, el rol pasará a ser una
lista de roles en la misma tabla.

## Cambios en el registro

`rol` es una columna no nula guardada como texto, igual que `tipo_documento`.
`/usuarios/registro` guarda `INQUILINO` y `/propietarios/registro` guarda
`PROPIETARIO`. Los dos formularios usan `registro.html` y `UsuarioService`; solo
cambian los textos, la ruta y el rol. La lógica sigue en el servicio.

Se modifica código integrado de #10: las pruebas de repositorio se adaptan al
nuevo constructor y el resto se conservan sin cambios.

## Pendiente

- Contrastar la decisión con el diagrama de clases del cliente.
- Respuesta del cliente sobre el doble rol (#15).
- Inicio de sesión en #12.