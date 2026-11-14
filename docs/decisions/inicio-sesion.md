# Decisiones del inicio de sesión (#12)

Fecha: 01/11/2026. Decisión provisional, pendiente de revisión del equipo en la PR.

## Alcance

Se sigue #12: un usuario o propietario registrado inicia sesión con sus
credenciales; si son correctas se le deja entrar y si son incorrectas, o el
usuario no existe, se rechaza con un mensaje claro. No se implementa cerrar
sesión ni se protegen rutas: eso llegará cuando haya funciones que lo necesiten,
como registrar una propiedad (#5).

## Credenciales

El cliente todavía no ha dicho qué credenciales se usan (#15). De forma
provisional se usan el email, que ya es único, y una contraseña nueva que se pide
en los dos formularios de registro. Se exige una contraseña de 8 a 72 caracteres.

## Cómo se guarda la contraseña

Se valoraron tres opciones:

- A) Spring Security completo. Protege todas las rutas por defecto y obliga a
  configurar el login, la consola H2 y las pruebas existentes. Es más de lo que
  pide #12.
- B) Solo el módulo `spring-security-crypto`, para cifrar con BCrypt.
- C) Cifrar a mano con las clases de Java. Más código propio y más fácil
  equivocarse.

Se elige la B. Es la dependencia más pequeña que resuelve el riesgo de
"almacenamiento inseguro de contraseñas" de #12 y no cambia el resto de la
aplicación. La base de datos guarda el hash de BCrypt, nunca la contraseña.
BCrypt solo admite 72 bytes; la ñ y las letras con tilde ocupan 2, así que una
contraseña con muchas puede no llegar a 72 caracteres.

## Inicio de sesión

`SesionController` atiende `/iniciar-sesion`. La comprobación está en
`UsuarioService.iniciarSesion`: busca el email sin distinguir mayúsculas y compara
la contraseña con el hash guardado. Si el email no existe o la contraseña no
coincide, el mensaje es el mismo ("El email o la contraseña no son correctos."),
para no revelar qué emails están registrados.

Si las credenciales son correctas se guardan en la sesión `usuarioId` y `rol`,
y se vuelve al inicio con un mensaje de bienvenida. Las siguientes issues pueden
usar el `rol` de la sesión para saber si el usuario es propietario (#5).

## Límites conocidos

- No hay botón de cerrar sesión: la sesión dura hasta cerrar el navegador.
- Sin protección CSRF ni renovación del identificador de sesión, que daría
  Spring Security completo.
- Se modifica código de #10 y #11: los registros piden ahora contraseña y las
  pruebas existentes se adaptan para enviarla.

## Pendiente

- Respuesta del cliente sobre las credenciales (#15).
- Valorar Spring Security completo cuando haya rutas que proteger.