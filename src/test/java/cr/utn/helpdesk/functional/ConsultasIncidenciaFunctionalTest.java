package cr.utn.helpdesk.functional;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.service.IncidenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsultasIncidenciaFunctionalTest {

    private IncidenciaService service;

    @BeforeEach
    void setUp() {
        service = new IncidenciaService();
    }

    @Test
    void obtenerAbiertas_excluyeFinalizadas() {
        service.registrarIncidencia(
                "Abierta", "Descripción larga de incidencia abierta",
                Categoria.SOFTWARE, Impacto.MEDIO, Urgencia.MEDIA
        );
        service.registrarIncidencia(
                "Cerrada", "Descripción larga de incidencia cerrada",
                Categoria.HARDWARE, Impacto.BAJO, Urgencia.BAJA
        );

        cerrarIncidencia(2);

        assertEquals(1, service.obtenerAbiertas().size());
        assertEquals(1, service.obtenerFinalizadas().size());
        assertTrue(service.obtenerAbiertas().stream()
                .noneMatch(i -> i.getEstado() == Estado.FINALIZADA));
    }

    @Test
    void buscarPorEstadoYPrioridad_filtraCorrectamente() {
        service.registrarIncidencia(
                "Critica", "Descripción larga de incidencia crítica",
                Categoria.RED, Impacto.ALTO, Urgencia.ALTA
        );
        service.registrarIncidencia(
                "Normal", "Descripción larga de incidencia normal",
                Categoria.OTRO, Impacto.BAJO, Urgencia.BAJA
        );

        assertEquals(2, service.buscarPorEstado(Estado.REGISTRADA).size());
        assertEquals(1, service.buscarPorPrioridad(
                service.buscarPorId(1).getPrioridad()).size());
    }

    private void cerrarIncidencia(int id) {
        service.cambiarEstado(id, Estado.LISTA, null);
        service.cambiarEstado(id, Estado.EN_DESARROLLO, null);
        service.cambiarEstado(id, Estado.EN_VALIDACION, null);
        service.cambiarEstado(id, Estado.FINALIZADA, "Problema resuelto correctamente.");
    }
}
