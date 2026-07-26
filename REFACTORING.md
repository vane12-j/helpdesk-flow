# Refactorización

## Problema

`IncidenciaService` concentraba tres responsabilidades distintas:

1. Registro y consulta de incidencias
2. Cálculo de prioridad según impacto y urgencia
3. Validación de transiciones de estado y cierre con solución

Esto dificultaba las pruebas unitarias aisladas y violaba el principio de responsabilidad única (SRP).

## Mejora aplicada

Se extrajeron dos clases con responsabilidad única:

| Clase | Responsabilidad |
|-------|-----------------|
| `PrioridadCalculator` | Calcula la prioridad a partir de impacto y urgencia |
| `EstadoTransitionValidator` | Valida transiciones del flujo y exige solución al finalizar |

Adicionalmente se crearon servicios especializados:

| Clase | Responsabilidad |
|-------|-----------------|
| `ExpediteService` | Gestiona la restricción de una sola incidencia EXPEDITE activa |
| `KanbanService` | Organiza incidencias en columnas por estado, ordenadas por prioridad |

## Beneficios

- **Testabilidad**: `PrioridadCalculator` y `EstadoTransitionValidator` se prueban sin instanciar el servicio completo.
- **Mantenibilidad**: Cambios en reglas de prioridad o transiciones quedan localizados.
- **Extensibilidad**: Nuevas reglas (como EXPEDITE) se agregan sin modificar la lógica central de registro.

## Flujo de estados

```
REGISTRADA → LISTA → EN_DESARROLLO → EN_VALIDACION → FINALIZADA
```

Solo se permiten transiciones secuenciales. Al pasar a `FINALIZADA` es obligatorio indicar la solución.

## Restricción EXPEDITE

Solo puede existir **una** incidencia con prioridad `EXPEDITE` activa (no finalizada) a la vez. Al finalizar una incidencia EXPEDITE, el slot queda libre para otra.
