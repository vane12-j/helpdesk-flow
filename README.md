# HelpDesk Flow

## Descripción del sistema

HelpDesk Flow es una aplicación desarrollada en Java que permite gestionar incidencias de soporte técnico mediante un flujo de trabajo basado en estados. El sistema permite registrar incidencias, calcular automáticamente su prioridad según el impacto y la urgencia, consultar incidencias mediante diferentes criterios y administrar su ciclo de vida.

## Integrantes

- Vanessa Amador Jiménez
- Isaac (Apellido)

## Tecnologías utilizadas

- Java 21
- IntelliJ IDEA
- Maven
- Git y GitHub
- JUnit 5 (pendiente)
- GitHub Actions (pendiente)

## Requisitos

- Java JDK 21 o superior
- Maven 3.9 o superior
- IntelliJ IDEA (recomendado)

## Estructura del proyecto

```
src/
├── main/
│   └── java/
│       └── cr/
│           └── utn/
│               └── helpdesk/
│                   ├── enums/
│                   ├── model/
│                   ├── service/
│                   └── Main.java
└── test/
```

## Funcionalidades implementadas

- Registro de incidencias.
- Generación automática de identificadores.
- Validación de datos.
- Cálculo automático de prioridad.
- Consulta de incidencias.
- Búsqueda por ID.
- Búsqueda por categoría.
- Búsqueda por estado.
- Búsqueda por prioridad.

## Funcionalidades pendientes

- Flujo completo de estados.
- Métricas del sistema.
- Cambio de requerimiento EXPEDITE.
- Pruebas automatizadas.
- Integración continua.

## Compilación

```bash
mvn clean compile
```

## Ejecución

```bash
mvn exec:java
```

O bien ejecutar la clase `Main.java` desde IntelliJ IDEA.

## Ejecución de pruebas

```bash
mvn test
```

> Actualmente las pruebas se encuentran en desarrollo.

## Tablero Kanban

Pendiente de agregar el enlace.

## Decisiones de diseño

- Se utilizó una arquitectura por capas sencilla (`model`, `service` y `enums`).
- Se implementaron enumeraciones para representar categorías, estados, impacto, urgencia y prioridad.
- La lógica de negocio se concentra en la clase `IncidenciaService`.
- La clase `Incidencia` representa el modelo principal del dominio.

## Estado de la integración continua

Pendiente de configurar GitHub Actions.