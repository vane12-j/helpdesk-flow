package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.model.Incidencia;
import cr.utn.helpdesk.model.Metricas;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MetricasService {

    private final List<Incidencia> incidencias;

    public MetricasService(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public Metricas calcular() {
        int total = incidencias.size();
        int cerradas = 0;
        int abiertas = 0;
        long sumaLeadTime = 0;
        int incidenciasConLeadTime = 0;
        LocalDate primeraCreacion = null;
        LocalDate ultimoCierre = null;

        Map<Prioridad, Integer> cantidadPorPrioridad = new EnumMap<>(Prioridad.class);
        for (Prioridad prioridad : Prioridad.values()) {
            cantidadPorPrioridad.put(prioridad, 0);
        }

        for (Incidencia incidencia : incidencias) {
            cantidadPorPrioridad.merge(incidencia.getPrioridad(), 1, Integer::sum);

            if (incidencia.getEstado() == Estado.FINALIZADA) {
                cerradas++;
                if (incidencia.getFechaCierre() != null && incidencia.getFechaCreacion() != null) {
                    sumaLeadTime += ChronoUnit.DAYS.between(
                            incidencia.getFechaCreacion(), incidencia.getFechaCierre());
                    incidenciasConLeadTime++;

                    if (ultimoCierre == null || incidencia.getFechaCierre().isAfter(ultimoCierre)) {
                        ultimoCierre = incidencia.getFechaCierre();
                    }
                }
            } else {
                abiertas++;
            }

            if (primeraCreacion == null || incidencia.getFechaCreacion().isBefore(primeraCreacion)) {
                primeraCreacion = incidencia.getFechaCreacion();
            }
        }

        double leadTimePromedio = incidenciasConLeadTime == 0
                ? 0.0
                : (double) sumaLeadTime / incidenciasConLeadTime;

        double throughput = calcularThroughput(cerradas, primeraCreacion, ultimoCierre);

        return new Metricas(total, abiertas, cerradas, throughput, leadTimePromedio, cantidadPorPrioridad);
    }

    private double calcularThroughput(int cerradas, LocalDate primeraCreacion, LocalDate ultimoCierre) {
        if (cerradas == 0 || primeraCreacion == null || ultimoCierre == null) {
            return 0.0;
        }

        long dias = ChronoUnit.DAYS.between(primeraCreacion, ultimoCierre);
        if (dias == 0) {
            return cerradas;
        }

        return (double) cerradas / dias;
    }
}
