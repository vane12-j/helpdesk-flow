package cr.utn.helpdesk.functional;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.KanbanBoard;
import cr.utn.helpdesk.service.IncidenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KanbanServiceFunctionalTest {

    private IncidenciaService service;

    @BeforeEach
    void setUp() {
        service = new IncidenciaService();
    }

    @Test
    void tableroAgrupaIncidenciasPorEstado() {
        service.registrarIncidencia(
                "Inc A", "Descripción larga de incidencia A",
                Categoria.SOFTWARE, Impacto.MEDIO, Urgencia.MEDIA
        );
        service.registrarIncidencia(
                "Inc B", "Descripción larga de incidencia B",
                Categoria.HARDWARE, Impacto.BAJO, Urgencia.BAJA
        );

        service.cambiarEstado(1, Estado.LISTA, null);

        KanbanBoard tablero = service.getKanbanService().obtenerTablero();

        assertEquals(1, tablero.getColumna(Estado.REGISTRADA).size());
        assertEquals(1, tablero.getColumna(Estado.LISTA).size());
        assertEquals(0, tablero.getColumna(Estado.EN_DESARROLLO).size());
    }

    @Test
    void tableroOrdenaExpeditePrimero() {
        service.registrarIncidencia(
                "Normal", "Descripción larga de incidencia normal",
                Categoria.OTRO, Impacto.BAJO, Urgencia.BAJA
        );
        service.registrarIncidencia(
                "Crítica", "Descripción larga de incidencia crítica",
                Categoria.RED, Impacto.ALTO, Urgencia.ALTA
        );

        service.getExpediteService().marcarExpedite(1);

        KanbanBoard tablero = service.getKanbanService().obtenerTablero();
        var registradas = tablero.getColumna(Estado.REGISTRADA);

        assertEquals(Prioridad.EXPEDITE, registradas.get(0).getPrioridad());
        assertEquals(Prioridad.CRITICA, registradas.get(1).getPrioridad());
    }
}
