package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.enums.Urgencia;

public final class PrioridadCalculator {

    private PrioridadCalculator() {
    }

    public static Prioridad calcular(Impacto impacto, Urgencia urgencia) {
        if (impacto == Impacto.ALTO && urgencia == Urgencia.ALTA) {
            return Prioridad.CRITICA;
        }

        if (impacto == Impacto.ALTO
                && (urgencia == Urgencia.MEDIA || urgencia == Urgencia.BAJA)) {
            return Prioridad.ALTA;
        }

        if ((impacto == Impacto.MEDIO || impacto == Impacto.BAJO)
                && urgencia == Urgencia.ALTA) {
            return Prioridad.ALTA;
        }

        return Prioridad.NORMAL;
    }
}
