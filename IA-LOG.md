# IA-LOG

## Información general

**Proyecto:** HelpDesk Flow

**Curso:** Metodologías Ágiles

**Integrantes:**

- Vanessa Amador Jiménez
- Isaac

---

# Objetivo del documento

Este documento registra el uso de herramientas de Inteligencia Artificial durante el desarrollo del proyecto HelpDesk Flow. Su propósito es evidenciar cómo la IA fue utilizada como apoyo para generar ideas, resolver dudas técnicas y mejorar la documentación, manteniendo siempre la revisión y validación humana de los resultados.

---

Registro de uso de IA

| Fecha | Herramienta | Objetivo | Resultado utilizado | Verificación | Cambios realizados por el equipo                                                                                                                                                                                                                                       |
|--------|-------------|----------|---------------------|--------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 24/07/2026 | ChatGPT | Diseñar la arquitectura del proyecto | Se obtuvo una propuesta inicial para organizar el proyecto en paquetes (`model`, `service`, `enums` y `ui`). | Se verificó compilando el proyecto en IntelliJ IDEA y comprobando que la estructura facilitara la organización del código. | Se reorganizaron algunos paquetes, se adaptó la estructura al proyecto y se crearon clases adicionales en los incidenciaService y el MainFrame                                                                                                                         |
| 24/07/2026 | ChatGPT | Diseñar el modelo del sistema | Se generó una propuesta para la clase `Incidencia` con sus atributos principales. | Se revisó que cumpliera con los requerimientos del proyecto y se realizaron pruebas de compilación. | Se modificaron atributos, constructores, métodos de acceso y se decidió utilizar un identificador incremental (`int`) en lugar de otra alternativa.                                                                                                                    |
| 25/07/2026 | ChatGPT | Implementar la lógica de negocio | Se obtuvo una versión inicial de `IncidenciaService` para registrar incidencias, calcular prioridades y gestionar estados. | Se realizaron pruebas funcionales registrando incidencias y verificando el flujo de estados dentro de la aplicación. | El código se reorganizado en métodos más claros, se añadieron validaciones y se ajustó el cálculo de prioridades.                                                                                                                                                      |
| 26/07/2026 | ChatGPT | Desarrollar la interfaz gráfica | Se generó una propuesta inicial del formulario utilizando Java Swing. | Se ejecutó la aplicación en IntelliJ IDEA verificando el funcionamiento del formulario y la tabla de incidencias. | Se reorganizó el código de la interfaz, se separó la lógica en diferentes métodos, se conectó con `IncidenciaService`, se añadió la actualización automática de la tabla y se realizaron mejoras visuales como impedir la edición de las filas y ajustar las columnas. |
| 27/07/2026 | ChatGPT | Elaborar la documentación del proyecto | Se generó una propuesta para el README, el tablero Kanban y la documentación del proyecto. | El contenido fue revisado manualmente y comparado con los requerimientos de la asignación antes de incorporarlo al repositorio. | Se reescribieron varias secciones, se amplió la documentación, se personalizó para el proyecto y se adaptó al formato que el profe dio en el enunciado.                                                                                                                |
---

## Respuesta de IA modificada por el equipo

Una de las respuestas generadas por la IA fue la implementación inicial de la clase `IncidenciaService` y de la interfaz gráfica desarrollada con Java Swing.

Antes de incorporarla al proyecto, el equipo realizó diversas modificaciones, entre ellas:

- Reorganización del código en métodos independientes para mejorar la legibilidad y el mantenimiento.
- Adaptación de la lógica para utilizar la clase `Incidencia` diseñada durante el proyecto.
- Implementación de validaciones adicionales para el registro de incidencias.
- Ajuste del cálculo de prioridades según los enums definidos por el equipo.
- Integración del formulario con la lógica existente del sistema.
- Actualización automática de la tabla al registrar nuevas incidencias.
- Mejoras visuales en la interfaz gráfica para ofrecer una mejor experiencia de uso.
---

# Sugerencia de IA rechazada

## Sugerencia rechazada

Durante el desarrollo, la IA sugirió implementar una base de datos para almacenar permanentemente las incidencias.

El equipo decidió no incorporar esta propuesta, ya que el alcance de la asignación no requería persistencia de datos y su implementación aumentaría significativamente la complejidad del proyecto.

---

## Razón técnica del rechazo

La implementación de una base de datos implicaba diseñar un modelo relacional, configurar un sistema gestor de bases de datos y desarrollar una capa de persistencia.

Como el objetivo principal del proyecto era aplicar principios de programación orientada a objetos, Kanban, TDD y buenas prácticas de desarrollo, se optó por utilizar una colección `ArrayList`, la cual permitió cumplir con todos los requerimientos funcionales solicitados por el curso.

---

# Validación de los resultados

Cada propuesta generada mediante Inteligencia Artificial fue revisada antes de incorporarse al proyecto.

LAntes de incorporar cualquier propuesta generada por la IA, el equipo realizó un proceso de validación que incluyó:

- Revisión manual del código generado.
- Adaptación del código a la arquitectura definida para el proyecto.
- Compilación del proyecto utilizando Maven.
- Ejecución de la aplicación en IntelliJ IDEA.
- Pruebas funcionales del formulario de registro de incidencias.
- Verificación del cálculo automático de prioridades.
- Comprobación del flujo de estados de las incidencias.
- Revisión del funcionamiento de la interfaz gráfica y de la actualización de la tabla.

Solo después de completar estas verificaciones el código fue integrado al repositorio mediante Git.

---

# Conclusiones

La IA se utilizado como una herramienta de apoyo para concluir el desarrollo del proyecto. Nos permitió acelerar la generación de ideas y resolver dudas técnicas. Sin embargo, todas las decisiones finales que se tomaron fue en equipo, realizando modificaciones, validaciones y pruebas antes de integrar cualquier propuesta al código.