[README.txt](https://github.com/user-attachments/files/22298877/README.txt)
# Programa Biblioteca — README

Sistema de gestión de biblioteca por consola en Java. Permite administrar libros, usuarios, préstamos, reservas y multas. 
El menú acepta comandos por nombre o por número.

---

## Contenido

- Descripción general
- Requisitos
- Estructura del proyecto
- Compilación y ejecución
- Uso del programa
- Modelo de dominio
- Extensión futura
- Licencia

---

## Descripción general

El **Programa Biblioteca** es una aplicación de consola desarrollada en Java cuyo objetivo es simular la gestión de una biblioteca. 
Entre sus principales funciones se encuentran:

- Registrar libros y usuarios.
- Realizar préstamos y devoluciones.
- Gestionar reservas de libros.
- Configurar días de préstamo y multas.
- Consultar libros por estado o por título.

El sistema está diseñado con un enfoque modular, separando la lógica en paquetes: `cli`, `dominio` y `servicio`.

---

## Requisitos

- **Java Development Kit (JDK) 17 o superior**  
- Sistema operativo: Windows, Linux o macOS

Para ejecutar directamente con doble clic en Windows, se incluye un script `.bat`.

---

## Estructura del proyecto

```
src/
 └─ biblioteca/
    ├─ Aplicacion.java
    ├─ cli/
    │  ├─ Comando.java
    │  ├─ ContextoComando.java
    │  └─ comandos/
    │     ├─ AgregarLibroComando.java
    │     ├─ BuscarPorTituloComando.java
    │     ├─ CancelarReservaComando.java
    │     ├─ ConfigurarDiasPrestamoComando.java
    │     ├─ ConfigurarMultaComando.java
    │     ├─ DevolverLibroComando.java
    │     ├─ ListarPorEstadoComando.java
    │     ├─ PrestarLibroComando.java
    │     ├─ RegistrarUsuarioComando.java
    │     └─ ReservarLibroComando.java
    ├─ dominio/
    │  ├─ EstadoLibro.java
    │  ├─ Libro.java
    │  └─ Usuario.java
    └─ servicio/
       └─ Biblioteca.java
```

---

## Compilación y ejecución

### Opción A: Usando el script `.bat` (Windows)

Crear un archivo `ejecutarBiblioteca.bat` en la carpeta raíz con el siguiente contenido:

```bat
@echo off
setlocal EnableExtensions EnableDelayedExpansion
set "SRC=src"
set "OUT=out"

if exist "%OUT%\" rmdir /s /q "%OUT%"
mkdir "%OUT%" >nul 2>&1

echo [INFO] Compilando...
for /r "%SRC%" %%F in (*.java) do (echo %%~fF) > "%OUT%\sources.txt"
javac -encoding UTF-8 -d "%OUT%" -sourcepath "%SRC%" @"%OUT%\sources.txt"

if errorlevel 1 (
    echo [ERROR] Fallo la compilación.
    pause
    exit /b 1
)

echo [OK] Compilación terminada.
echo [INFO] Ejecutando biblioteca.Aplicacion
java -cp "%OUT%" biblioteca.Aplicacion

echo.
pause
```

Al ejecutar este archivo con doble clic, se compila y corre automáticamente el programa.

### Opción B: Manual con terminal

En Windows (PowerShell):

```powershell
Remove-Item -Recurse -Force out -ErrorAction Ignore
New-Item -ItemType Directory -Path out | Out-Null
javac -encoding UTF-8 -d out -sourcepath src (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out biblioteca.Aplicacion
```

En Linux/macOS (bash):

```bash
rm -rf out && mkdir out
javac -encoding UTF-8 -d out -sourcepath src $(find src -name "*.java")
java -cp out biblioteca.Aplicacion
```

---

## Uso del programa

Al iniciar el sistema, se muestra un menú interactivo:

```
=== Programa Biblioteca ===
1 - agregar : Agregar libro
2 - registrar : Registrar usuario
3 - prestar : Prestar un libro a un usuario
4 - devolver : Devolver un libro prestado
5 - reservar : Reservar un libro disponible
6 - cancelar-reserva : Cancelar la reserva de un libro
7 - listar-por-estado : Listar libros por estado (disponible | prestado | reservado)
8 - buscar-titulo : Buscar libros por título (contiene)
9 - config-dias : Configurar días de préstamo por defecto
10 - config-multa : Configurar multa por día de atraso
0 - salir : Salir del programa
```

Se puede elegir escribiendo el número o el nombre del comando.

---

## Modelo de dominio

- **EstadoLibro**: Enum con estados `DISPONIBLE`, `PRESTADO`, `RESERVADO`.
- **Libro**: Representa un libro con atributos como título, autor, ISBN, estado y reservas.
- **Usuario**: Almacena datos del usuario y sus préstamos.
- **Biblioteca**: Servicio central encargado de manejar la lógica de negocio.

---

## Extensión futura

Este sistema puede extenderse con:

- Persistencia en archivos o base de datos.  
- Reportes de actividad y estadísticas.  
- Roles de usuario (bibliotecario, lector, administrador).  
- Interfaz gráfica o aplicación web.  

---

## Licencia

Uso académico y educativo.  
Autor: Tomas Romero Baylac
