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
```
