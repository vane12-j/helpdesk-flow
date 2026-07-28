# Retrospectiva — HelpDesk Flow

## ¿Qué aportó Kanban?

El tablero Kanban nos dio visibilidad clara del avance del proyecto. Al dividir el trabajo en columnas — Backlog, Preparado, En desarrollo, Validación y Hecho — pudimos identificar rápidamente qué historias estaban bloqueadas y cuáles listas para integrar. La implementación en código (`KanbanService`) reflejó esa misma estructura: cada incidencia vive en una columna según su estado y se ordena por prioridad, con EXPEDITE siempre al frente. Esto conectó la metodología visual con el software real y facilitó las demos durante el curso.

## ¿Qué problema dio el WIP?

Los límites de Work In Progress (WIP) fueron útiles pero incómodos al inicio. Con un límite de dos tarjetas en "En desarrollo", Vanessa tuvo que terminar el modelo antes de que Isaac pudiera avanzar con EXPEDITE, lo que generó cuellos de botella en la primera semana. Sin embargo, esa fricción obligó a priorizar: dejamos de multitasking y nos enfocamos en cerrar una historia antes de abrir otra. Aprendimos que WIP no es burocracia; es una herramienta para detectar sobrecarga temprano.

## ¿Qué errores detectó TDD?

Las pruebas expusieron varios bugs antes de llegar a la interfaz gráfica. El más notable fue el cierre sin solución: la primera versión permitía pasar a `FINALIZADA` con `null`, y el test `cierreSinSolucion_lanzaExcepcion` lo detectó de inmediato. También descubrimos que marcar dos incidencias como EXPEDITE no lanzaba error hasta escribir `ExpediteServiceTest`. Sin TDD, estos fallos habrían aparecido en la demo final. El enfoque Ping-Pong — Vanessa escribe prueba, Isaac implementa, y viceversa — nos mantuvo sincronizados.

## ¿Qué refactorizamos?

La refactorización principal fue extraer responsabilidades de `IncidenciaService`. Originalmente concentraba registro, cálculo de prioridad, validación de estados y consultas. Separamos `PrioridadCalculator`, `EstadoTransitionValidator`, `ExpediteService` y `KanbanService`. Esto mejoró la testabilidad: cada clase se prueba de forma aislada. Documentamos la decisión en `REFACTORING.md` para que el equipo entienda el porqué, no solo el qué.

## ¿Cómo afectó EXPEDITE?

EXPEDITE introdujo una regla de negocio que tocó varias capas. Solo puede existir una incidencia EXPEDITE activa, lo que obligó a crear un servicio dedicado y adaptar el ordenamiento del Kanban. En la práctica, simuló escenarios reales de soporte: cuando "Sin Internet" se marcó como EXPEDITE, automáticamente subió al tope de la cola. La restricción también simplificó decisiones del equipo: si algo es urgente de verdad, ocupa un slot exclusivo.

## ¿Cómo ayudó la IA?

La IA aceleró tareas repetitivas: generar esqueletos de pruebas con JUnit 5, redactar borradores de documentación y proponer estructuras de clases. Cursor fue especialmente útil para refactorizar y verificar que los tests siguieran pasando después de cada cambio. ChatGPT ayudó a explorar combinaciones de impacto/urgencia para la matriz de prioridad. En conjunto, redujo el tiempo en tareas mecánicas y nos dejó más tiempo para diseño y revisión.

## ¿Dónde falló la IA?

La IA falló cuando le pedimos código de UI sin contexto suficiente. ChatGPT generó un formulario Swing desconectado del servicio, con lógica duplicada y sin validaciones. También propuso usar timestamps en milisegundos para métricas, incompatible con nuestro modelo basado en `LocalDate`. En ambos casos, aceptar el código sin revisar habría roto la arquitectura. La lección: la IA es un asistente, no un reemplazo del criterio técnico.

## ¿Qué mejorarían?

Para un próximo sprint mejoraríamos tres aspectos. Primero, integrar métricas en la interfaz gráfica, no solo en el servicio. Segundo, definir historias de usuario más pequeñas para reducir conflictos de merge entre Vanessa e Isaac. Tercero, configurar el tablero Kanban en GitHub Projects desde el día uno, con WIP visibles para todo el equipo. También documentaríamos los prompts de IA que funcionaron mejor, para no repetir errores. En general, el proyecto demostró que XP — TDD, Kanban, refactorización continua y pair review — produce software más confiable que codificar sin pruebas ni límites de flujo.