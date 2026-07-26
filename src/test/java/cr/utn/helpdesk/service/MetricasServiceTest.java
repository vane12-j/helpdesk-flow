package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.Incidencia;
import cr.utn.helpdesk.model.Metricas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetricasServiceTest {

    private List<Incidencia> incidencias;
    private MetricasService metricasService;

    @BeforeEach
    void setUp() {
        incidencias = new ArrayList<>();
        metricasService = new MetricasService(incidencias);
    }

    @Test
    void sinIncidencias_retornaCeros() {
        Metricas metricas = metricasService.calcular();

        assertEquals(0, metricas.getTotal());
        assertEquals(0, metricas.getAbiertas());
        assertEquals(0, metricas.getCerradas());
        assertEquals(0.0, metricas.getThroughput());
        assertEquals(0.0, metricas.getLeadTimePromedioDias());
    }

    @Test
    void incidenciasAbiertasYcerradas_calculaTotales() {
        Incidencia abierta = crearIncidencia(1, Estado.REGISTRADA, Prioridad.NORMAL);
        Incidencia cerrada = crearIncidencia(2, Estado.FINALIZADA, Prioridad.ALTA);
        cerrada.setFechaCreacion(LocalDate.of(2026, 7, 1));
        cerrada.setFechaCierre(LocalDate.of(2026, 7, 6));

        incidencias.add(abierta);
        incidencias.add(cerrada);

        Metricas metricas = metricasService.calcular();

        assertEquals(2, metricas.getTotal());
        assertEquals(1, metricas.getAbiertas());
        assertEquals(1, metricas.getCerradas());
        assertEquals(5.0, metricas.getLeadTimePromedioDias());
        assertEquals(1, metricas.getCantidadPorPrioridad().get(Prioridad.NORMAL));
        assertEquals(1, metricas.getCantidadPorPrioridad().get(Prioridad.ALTA));
    }

    @Test
    void throughput_calculaIncidenciasPorDia() {
        Incidencia inc1 = crearIncidencia(1, Estado.FINALIZADA, Prioridad.CRITICA);
        inc1.setFechaCreacion(LocalDate.of(2026, 7, 1));
        inc1.setFechaCierre(LocalDate.of(2026, 7, 5));

        Incidencia inc2 = crearIncidencia(2, Estado.FINALIZADA, Prioridad.NORMAL);
        inc2.setFechaCreacion(LocalDate.of(2026, 7, 2));
        inc2.setFechaCierre(LocalDate.of(2026, 7, 10));

        incidencias.add(inc1);
        incidencias.add(inc2);

        Metricas metricas = metricasService.calcular();

        assertEquals(2, metricas.getCerradas());
        assertEquals(0.22, metricas.getThroughput(), 0.01);
    }

    private Incidencia crearIncidencia(int id, Estado estado, Prioridad prioridad) {
        Incidencia inc = new Incidencia(
                id, "Inc " + id, "Descripción de prueba larga",
                Categoria.SOFTWARE, Impacto.MEDIO, Urgencia.MEDIA,
                LocalDate.of(2026, 7, 1)
        );
        inc.setEstado(estado);
        inc.setPrioridad(prioridad);
        return inc;
    }
}
