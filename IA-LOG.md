# IA-LOG — Bitácora de uso de Inteligencia Artificial

Registro de interacciones con IA durante el desarrollo de **HelpDesk Flow**.

| Fecha | IA | Objetivo | Resultado | ¿Se usó? | Cambios |
|-------|-----|----------|-----------|----------|---------|
| 15/07/2026 | ChatGPT | Generar casos de prueba para cálculo de prioridad | Propuso 8 tests con `@ParameterizedTest` | Sí, modificado | Se adaptaron nombres al dominio del proyecto y se eliminaron casos duplicados con los de Vanessa |
| 18/07/2026 | Cursor (Claude) | Crear `EstadoTransitionValidator` con validación de transiciones | Generó la clase completa con switch por estado | Sí | Se integró tal cual, solo se ajustó el mensaje de error al español del proyecto |
| 20/07/2026 | ChatGPT | Diseñar interfaz Swing con tabla de incidencias | Propuso layout con `BorderLayout` y `JTable` sin integración con el servicio | No | **Rechazado**: la UI no respetaba el flujo de estados ni conectaba con `IncidenciaService`; se rehizo manualmente en `MainFrame` |
| 22/07/2026 | Cursor | Documentar refactorización en `REFACTORING.md` | Generó borrador con SRP y tabla de clases | Sí, modificado | Se añadieron secciones de EXPEDITE y flujo Kanban que la IA no incluyó |
| 24/07/2026 | ChatGPT | Implementar métricas (throughput y lead time) | Propuso usar `System.currentTimeMillis()` en lugar de `LocalDate` | Sí, modificado | Se reemplazó por `ChronoUnit.DAYS` entre `fechaCreacion` y `fechaCierre` para alinear con el modelo existente |

---

## Respuesta modificada (15/07/2026)

**Original de ChatGPT:** Tests con nombres en inglés (`testHighHighReturnsCritical`) y sin usar enums del proyecto.

**Versión final:** `PrioridadCalculatorTest` con nombres descriptivos en español (`impactoAltoUrgenciaAlta_retornaCritica`) y `@CsvSource` referenciando los enums `Impacto` y `Urgencia` reales.

**Motivo del cambio:** Mantener consistencia con las convenciones del equipo y evitar imports innecesarios.

---

## Respuesta rechazada (20/07/2026)

**Propuesta de ChatGPT:** Formulario Swing independiente que almacenaba incidencias en un `ArrayList` local, sin validaciones ni flujo de estados.

**Motivo del rechazo:**

1. Duplicaba la lógica de negocio fuera de `IncidenciaService`.
2. No validaba descripción mínima ni transiciones de estado.
3. No era compatible con las pruebas funcionales ya escritas por Isaac.

Se descartó por completo y Vanessa implementó `MainFrame` conectado al servicio existente.

---

## Lecciones aprendidas

- La IA acelera borradores de pruebas y documentación, pero hay que verificar que respete la arquitectura del proyecto.
- Para UI y reglas de negocio críticas, revisar siempre el código generado antes de integrarlo.
- Los prompts específicos ("usa `IncidenciaService`, no dupliques lógica") producen mejores resultados.
