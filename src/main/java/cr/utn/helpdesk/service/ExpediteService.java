package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.model.Incidencia;

import java.util.List;

public class ExpediteService {

    private final List<Incidencia> incidencias;

    public ExpediteService(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public void marcarExpedite(int id) {
        Incidencia incidencia = buscarPorId(id);

        if (incidencia == null) {
            throw new IllegalArgumentException("Incidencia no encontrada.");
        }

        if (incidencia.getEstado() == Estado.FINALIZADA) {
            throw new IllegalStateException("No se puede marcar como EXPEDITE una incidencia finalizada.");
        }

        Incidencia actualExpedite = obtenerExpediteActiva();
        if (actualExpedite != null && actualExpedite.getId() != id) {
            throw new IllegalStateException(
                    "Ya existe una incidencia EXPEDITE activa (ID " + actualExpedite.getId() + ").");
        }

        incidencia.setPrioridad(Prioridad.EXPEDITE);
    }

    public void quitarExpedite(int id) {
        Incidencia incidencia = buscarPorId(id);

        if (incidencia == null) {
            throw new IllegalArgumentException("Incidencia no encontrada.");
        }

        if (incidencia.getPrioridad() != Prioridad.EXPEDITE) {
            throw new IllegalStateException("La incidencia no está marcada como EXPEDITE.");
        }

        incidencia.setPrioridad(
                PrioridadCalculator.calcular(incidencia.getImpacto(), incidencia.getUrgencia()));
    }

    public Incidencia obtenerExpediteActiva() {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getPrioridad() == Prioridad.EXPEDITE
                    && incidencia.getEstado() != Estado.FINALIZADA) {
                return incidencia;
            }
        }
        return null;
    }

    private Incidencia buscarPorId(int id) {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getId() == id) {
                return incidencia;
            }
        }
        return null;
    }
}
