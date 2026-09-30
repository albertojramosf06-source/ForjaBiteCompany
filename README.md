# Plataforma de alquiler de viviendas

Proyecto desarrollado para el laboratorio de Ingeniería del Software II (curso 2026-2027).

## Equipo

Empresa: ForjaBiteCompany

Integrantes:
- Jorge Martín Sánchez
- Alberto José Ramos Fernández
- Alejandro Jiménez García-Oliva
- Diego López Barriocanal
- Carlos Ramos Cabañas

Portavoz:
- Jorge Martín Sánchez

## Objetivo

Desarrollar una plataforma para la gestión de alquiler de viviendas siguiendo un proceso iterativo e incremental de ingeniería del software.

## Estado

Sprint 0 – Preparación y planificación.

## Construcción y ejecución

### Requisitos

- Java 17

### Clonar el repositorio

Desde la terminal en orden uno por uno:

```powershell
git clone https://github.com/albertojramosf06-source/ForjaBiteCompany.git

cd ForjaBiteCompany
code .

git status
git remote -v
git branch

```

### Construcción, verificación y ejecución

Desde la raíz del repositorio:

```powershell
.\mvnw.cmd clean
.\mvnw.cmd test
.\mvnw.cmd package
.\mvnw.cmd verify

Ejecución: .\mvnw.cmd spring-boot:run
```
### Ejecución

 ```powershell
.\mvnw.cmd spring-boot:run
```
### Ver 
http://localhost:8080/

### Parar
Ctrl + C

### Dependencias Principales
- spring-boot-starter-webmvc → aplicación web con Spring MVC: controladores y peticiones HTTP.
- spring-boot-starter-thymeleaf → vistas HTML mediante plantillas Thymeleaf.
- spring-boot-starter-webmvc-test → soporte para pruebas de Spring MVC.
- spring-boot-starter-thymeleaf-test → soporte para pruebas de Thymeleaf.


### Git

 ```powershell
git config --global user.name "[PENDIENTE: NOMBRE Y APELLIDOS]"
git config --global user.email "[PENDIENTE: EMAIL]"
git status
git add .
git commit -m "Describe los cambios"
git push
git pull
```
### Sincronización del repositorio local con GitHub

Antes de comenzar una nueva tarea, la rama `main` local debe estar actualizada respecto a `origin/main` y no debe contener cambios pendientes.

#### Flujo habitual

Para actualizar la copia local:

```powershell
git checkout main
git pull
git status
```

El resultado esperado es:

```text
On branch main
Your branch is up to date with 'origin/main'.

nothing to commit, working tree clean
```

A partir de este punto se puede crear una nueva rama asociada a la tarea o issue correspondiente.

#### Recuperación ante una rama `main` divergente

Si Git indica que `main` y `origin/main` han divergido, primero debe comprobarse qué commits existen únicamente en local:

```powershell
git log --oneline origin/main..main
```

Si existe algún commit local que se quiera conservar, se crea primero una rama de seguridad:

```powershell
git branch backup/local-main-before-sync
```

Después se actualiza la información del repositorio remoto:

```powershell
git fetch origin
```

Finalmente, si se ha comprobado que el trabajo local está protegido y se desea que `main` vuelva a coincidir exactamente con GitHub:

```powershell
git reset --hard origin/main
```

Se verifica el resultado con:

```powershell
git status
```

**Importante:** `git reset --hard origin/main` no forma parte del flujo normal de trabajo. Se utiliza únicamente para recuperar una rama local cuando se ha comprobado previamente que no se perderá trabajo.

#### Regla de trabajo

No se debe desarrollar directamente sobre `main`.

El flujo de trabajo será:

1. Actualizar `main`.
2. Seleccionar una issue o tarea.
3. Crear una rama desde `main` actualizada.
4. Realizar cambios acotados.
5. Revisar los cambios.
6. Hacer commit.
7. Hacer push de la rama.
8. Crear una Pull Request.
9. Realizar la revisión.
10. Integrar mediante merge.
11. Actualizar nuevamente `main` en local.

#### Actualizar el repositorio local después de un cambio en GitHub

Si el cambio realizado en GitHub solo afecta al Project, por ejemplo:

- Mover una issue a `In progress`.
- Cambiar la prioridad.
- Asignar un responsable.
- Crear o editar una issue.

No es necesario hacer nada en local, porque no se han modificado archivos del repositorio.

Si se han modificado archivos en GitHub o se ha hecho merge de una Pull Request a `main`, se debe actualizar la copia local con:

```powershell
git checkout main
git pull
git status
```

Después de esto, los archivos modificados en GitHub estarán también actualizados en la carpeta local.

#### Flujo recomendado antes de empezar una nueva tarea

Antes de crear una nueva rama:

```powershell
git checkout main
git pull
git status
```

Cuando `main` esté actualizada y el árbol de trabajo esté limpio, se crea la rama correspondiente a la tarea:

```powershell
git checkout -b tipo/numero-descripcion
```

Ejemplos:

```text
feat/12-search-availability
fix/21-invalid-date-validation
docs/13-update-maven-readme
refactor/30-reservation-service
```

Formato recomendado:

```text
<tipo>/<issue>-descripcion-corta
```

#### Flujo después de hacer merge de una Pull Request

Cuando una Pull Request haya sido revisada y fusionada en `main`:

```powershell
git checkout main
git pull
```

Si la rama local ya no se necesita:

```powershell
git branch -d nombre-de-la-rama
```

El objetivo es que `main` local vuelva a coincidir con `origin/main` antes de empezar una nueva tarea.
