package cr.utn.helpdesk.model;

import cr.utn.helpdesk.enums.Estado;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class KanbanBoard {

    private final Map<Estado, List<Incidencia>> columnas;

    public KanbanBoard() {
        columnas = new EnumMap<>(Estado.class);
        for (Estado estado : Estado.values()) {
            columnas.put(estado, List.of());
        }
    }

    public KanbanBoard(Map<Estado, List<Incidencia>> columnas) {
        this.columnas = new EnumMap<>(Estado.class);
        for (Estado estado : Estado.values()) {
            this.columnas.put(estado, columnas.getOrDefault(estado, List.of()));
        }
    }

    public List<Incidencia> getColumna(Estado estado) {
        return Collections.unmodifiableList(columnas.get(estado));
    }

    public Map<Estado, List<Incidencia>> getColumnas() {
        Map<Estado, List<Incidencia>> copia = new EnumMap<>(Estado.class);
        columnas.forEach((estado, lista) -> copia.put(estado, Collections.unmodifiableList(lista)));
        return Collections.unmodifiableMap(copia);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TABLERO KANBAN ===\n");
        for (Estado estado : Estado.values()) {
            sb.append("\n[").append(estado).append("]\n");
            List<Incidencia> incidencias = columnas.get(estado);
            if (incidencias.isEmpty()) {
                sb.append("  (vacío)\n");
            } else {
                for (Incidencia inc : incidencias) {
                    sb.append("  #").append(inc.getId())
                            .append(" - ").append(inc.getTitulo())
                            .append(" [").append(inc.getPrioridad()).append("]\n");
                }
            }
        }
        return sb.toString();
    }
}
