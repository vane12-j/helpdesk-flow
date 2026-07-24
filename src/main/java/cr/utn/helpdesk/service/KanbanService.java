package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.model.Incidencia;
import cr.utn.helpdesk.model.KanbanBoard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class KanbanService {

    private final List<Incidencia> incidencias;

    public KanbanService(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public KanbanBoard obtenerTablero() {
        Map<Estado, List<Incidencia>> columnas = new EnumMap<>(Estado.class);

        for (Estado estado : Estado.values()) {
            columnas.put(estado, new ArrayList<>());
        }

        for (Incidencia incidencia : incidencias) {
            columnas.get(incidencia.getEstado()).add(incidencia);
        }

        for (Estado estado : Estado.values()) {
            columnas.get(estado).sort(comparadorPrioridad());
        }

        return new KanbanBoard(columnas);
    }

    private Comparator<Incidencia> comparadorPrioridad() {
        return Comparator.comparingInt((Incidencia i) -> ordenPrioridad(i.getPrioridad()))
                .thenComparingInt(Incidencia::getId);
    }

    private int ordenPrioridad(Prioridad prioridad) {
        return switch (prioridad) {
            case EXPEDITE -> 0;
            case CRITICA -> 1;
            case ALTA -> 2;
            case NORMAL -> 3;
        };
    }
}
