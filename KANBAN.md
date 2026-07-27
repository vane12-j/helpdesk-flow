# Tablero Kanban — GitHub Projects

Guía para crear y mantener el tablero del proyecto **HelpDesk Flow**.

## Columnas requeridas

| Columna | Propósito | Límite WIP sugerido |
|---------|-----------|---------------------|
| **Backlog** | Historias pendientes por priorizar | Sin límite |
| **Preparado** | Historias listas para desarrollo | 5 |
| **En desarrollo** | Trabajo activo | 2 |
| **Validación** | En revisión o pruebas | 3 |
| **Hecho** | Completado e integrado | Sin límite |

## Pasos para crear el tablero

1. Ir a https://github.com/vane12-j/helpdesk-flow
2. Pestaña **Projects** → **New project**
3. Elegir plantilla **Board**
4. Renombrar columnas según la tabla anterior
5. Configurar límites WIP en **En desarrollo** (2) y **Validación** (3)
6. Agregar tarjetas por historia de usuario

## Historias de usuario sugeridas

- Como técnico, quiero registrar una incidencia con título, descripción, categoría, impacto y urgencia.
- Como técnico, quiero que el sistema calcule la prioridad automáticamente.
- Como supervisor, quiero marcar una incidencia como EXPEDITE (solo una activa).
- Como técnico, quiero mover incidencias por el flujo de estados secuencial.
- Como supervisor, quiero consultar métricas (total, abiertas, cerradas, throughput, lead time).
- Como usuario, quiero ver incidencias en un tablero Kanban ordenado por prioridad.

## Criterios de aceptación (ejemplo: EXPEDITE)

- [ ] Solo una incidencia EXPEDITE activa a la vez
- [ ] No se puede marcar EXPEDITE una incidencia finalizada
- [ ] Al finalizar una EXPEDITE, el slot queda libre
- [ ] Pruebas unitarias cubren los casos anteriores

## Mantenimiento

- Mover tarjetas al cambiar de estado en cada PR
- Respetar WIP: no iniciar nueva tarjeta si "En desarrollo" está lleno
- Revisar el tablero en cada daily/sync del equipo
