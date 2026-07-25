# HelpDesk Flow

## Descripción

HelpDesk Flow es una aplicación desarrollada en **Java** para gestionar incidencias de soporte técnico. El sistema permite registrar incidencias, asignar automáticamente una prioridad según el impacto y la urgencia, administrar el estado de cada incidencia y consultar la información registrada.

El proyecto fue desarrollado como parte del curso de **Metodologías Ágiles**, aplicando principios de desarrollo incremental, control de versiones con Git y buenas prácticas de programación orientada a objetos.

---

# Integrantes

- Vanessa Amador Jiménez
- Isaac (Apellido)

---

# Características principales

Actualmente el sistema permite:

- Registrar incidencias.
- Generar automáticamente el identificador de cada incidencia.
- Validar los datos ingresados.
- Calcular automáticamente la prioridad.
- Asignar el estado inicial de una incidencia.
- Consultar incidencias registradas.
- Buscar incidencias por:
    - ID
    - Categoría
    - Estado
    - Prioridad
- Cambiar el estado de una incidencia respetando el flujo definido.
- Visualizar las incidencias mediante una interfaz gráfica desarrollada con Java Swing.

---

# Tecnologías utilizadas

- Java 21
- Maven
- Swing (Interfaz gráfica)
- IntelliJ IDEA
- Git
- GitHub

---

# Dependencias

El proyecto utiliza únicamente dependencias estándar de Java y Maven.

Actualmente no requiere:

- Base de datos
- Frameworks externos
- Librerías adicionales

La interfaz gráfica está desarrollada utilizando **Java Swing**, incluido en el JDK.

---

# Requisitos

Antes de ejecutar el proyecto se debe tener instalado:

- Java JDK 21 o superior
- Apache Maven 3.9 o superior
- IntelliJ IDEA (recomendado)

Para verificar las versiones instaladas:

```bash
java -version
```

```bash
mvn -version
```

---

# Estructura del proyecto

```
helpdesk-flow
│
├── src
│   ├── main
│   │   └── java
│   │       └── cr
│   │           └── utn
│   │               └── helpdesk
│   │                   ├── enums
│   │                   ├── model
│   │                   ├── service
│   │                   ├── ui
│   │                   └── Main.java
│   │
│   └── test
│
├── pom.xml
├── README.md
└── .gitignore
```

---

# Arquitectura

El proyecto se encuentra organizado por responsabilidades.

## model

Contiene las entidades del sistema.

Actualmente:

- Incidencia

---

## enums

Contiene todas las enumeraciones utilizadas por el sistema.

- Categoria
- Estado
- Impacto
- Prioridad
- Urgencia

---

## service

Contiene toda la lógica de negocio.

Actualmente:

- Registro de incidencias.
- Validaciones.
- Búsquedas.
- Cambio de estados.
- Cálculo automático de prioridad.

---

## ui

Contiene la interfaz gráfica desarrollada con Swing.

Desde esta interfaz el usuario puede:

- Registrar incidencias.
- Visualizar las incidencias registradas en una tabla.

---

# Funcionamiento del sistema

El usuario únicamente debe ingresar:

- Título
- Descripción
- Categoría
- Impacto
- Urgencia

Al registrar una incidencia, el sistema genera automáticamente:

- Identificador
- Estado inicial
- Prioridad
- Fecha de creación

Posteriormente la incidencia puede avanzar por el flujo definido de estados.

```
REGISTRADA
      ↓
LISTA
      ↓
EN_DESARROLLO
      ↓
EN_VALIDACION
      ↓
FINALIZADA
```

---

# Cómo ejecutar el proyecto

## Opción 1 (Recomendada)

Abrir el proyecto con IntelliJ IDEA.

Ejecutar la clase:

```
Main.java
```

Ubicación:

```
src/main/java/cr/utn/helpdesk/Main.java
```

---

## Opción 2 (Maven)

Compilar el proyecto:

```bash
mvn clean compile
```

Ejecutar:

```bash
mvn exec:java
```

---

# Cómo utilizar la aplicación

1. Abrir la aplicación.
2. Completar el formulario.
3. Presionar **Registrar incidencia**.
4. La incidencia aparecerá automáticamente en la tabla.
5. El sistema asignará el ID, estado y prioridad automáticamente.

---

# Validaciones implementadas

El sistema valida que:

- El título no esté vacío.
- La descripción tenga al menos 10 caracteres.
- No se permitan transiciones inválidas entre estados.
- Una incidencia no puede finalizarse sin una solución.

---

# Estado actual del proyecto

Implementado:

- Modelo del sistema.
- Registro de incidencias.
- Interfaz gráfica.
- Validaciones.
- Búsquedas.
- Cálculo automático de prioridad.
- Gestión del flujo de estados.

Pendiente:

- Pruebas unitarias.
- GitHub Actions.
- Documentación complementaria (IA-LOG y Retrospectiva).

---

