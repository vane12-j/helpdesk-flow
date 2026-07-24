package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.Incidencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpediteServiceTest {

    private List<Incidencia> incidencias;
    private ExpediteService expediteService;

    @BeforeEach
    void setUp() {
        incidencias = new ArrayList<>();
        expediteService = new ExpediteService(incidencias);

        Incidencia inc1 = crearIncidencia(1, Impacto.ALTO, Urgencia.ALTA);
        Incidencia inc2 = crearIncidencia(2, Impacto.BAJO, Urgencia.MEDIA);
        incidencias.add(inc1);
        incidencias.add(inc2);
    }

    @Test
    void marcarExpedite_cambiaPrioridad() {
        expediteService.marcarExpedite(1);
        assertEquals(Prioridad.EXPEDITE, incidencias.get(0).getPrioridad());
    }

    @Test
    void soloUnaExpediteActiva_rechazaSegunda() {
        expediteService.marcarExpedite(1);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> expediteService.marcarExpedite(2));
        assertEquals("Ya existe una incidencia EXPEDITE activa (ID 1).", ex.getMessage());
    }

    @Test
    void expediteFinalizada_liberaSlot() {
        expediteService.marcarExpedite(1);
        incidencias.get(0).setEstado(Estado.FINALIZADA);

        expediteService.marcarExpedite(2);
        assertEquals(Prioridad.EXPEDITE, incidencias.get(1).getPrioridad());
    }

    @Test
    void quitarExpedite_restauraPrioridadCalculada() {
        expediteService.marcarExpedite(1);
        expediteService.quitarExpedite(1);
        assertEquals(Prioridad.CRITICA, incidencias.get(0).getPrioridad());
    }

    @Test
    void marcarExpediteIncidenciaFinalizada_lanzaExcepcion() {
        incidencias.get(0).setEstado(Estado.FINALIZADA);

        assertThrows(IllegalStateException.class,
                () -> expediteService.marcarExpedite(1));
    }

    @Test
    void marcarExpediteIdInexistente_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> expediteService.marcarExpedite(99));
    }

    @Test
    void obtenerExpediteActiva_retornaNullSiNoHay() {
        assertNull(expediteService.obtenerExpediteActiva());
    }

    private Incidencia crearIncidencia(int id, Impacto impacto, Urgencia urgencia) {
        Incidencia inc = new Incidencia(
                id, "Inc " + id, "Descripción de prueba larga",
                Categoria.RED, impacto, urgencia, LocalDate.now()
        );
        inc.setEstado(Estado.REGISTRADA);
        inc.setPrioridad(PrioridadCalculator.calcular(impacto, urgencia));
        return inc;
    }
}
