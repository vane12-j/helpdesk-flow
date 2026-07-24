package cr.utn.helpdesk.functional;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.Incidencia;
import cr.utn.helpdesk.model.KanbanBoard;
import cr.utn.helpdesk.service.IncidenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IncidenciaServiceFunctionalTest {

    private IncidenciaService service;

    @BeforeEach
    void setUp() {
        service = new IncidenciaService();
    }

    @Test
    void flujoCompletoDesdeRegistroHastaCierre() {
        Incidencia inc = service.registrarIncidencia(
                "Sin acceso VPN",
                "El usuario no puede conectarse a la VPN corporativa.",
                Categoria.RED,
                Impacto.ALTO,
                Urgencia.ALTA
        );

        assertEquals(Estado.REGISTRADA, inc.getEstado());
        assertEquals(Prioridad.CRITICA, inc.getPrioridad());

        service.cambiarEstado(inc.getId(), Estado.LISTA, null);
        service.cambiarEstado(inc.getId(), Estado.EN_DESARROLLO, null);
        service.cambiarEstado(inc.getId(), Estado.EN_VALIDACION, null);
        service.cambiarEstado(inc.getId(), Estado.FINALIZADA, "Se reinició el túnel VPN.");

        Incidencia finalizada = service.buscarPorId(inc.getId());
        assertEquals(Estado.FINALIZADA, finalizada.getEstado());
        assertNotNull(finalizada.getFechaCierre());
        assertEquals("Se reinició el túnel VPN.", finalizada.getSolucion());
    }

    @Test
    void transicionInvalidaDesdeRegistrada_rechazada() {
        service.registrarIncidencia(
                "Impresora atascada",
                "La impresora no imprime documentos correctamente.",
                Categoria.HARDWARE,
                Impacto.BAJO,
                Urgencia.MEDIA
        );

        assertThrows(IllegalStateException.class,
                () -> service.cambiarEstado(1, Estado.EN_DESARROLLO, null));
    }

    @Test
    void cierreSinSolucion_rechazado() {
        service.registrarIncidencia(
                "App lenta",
                "La aplicación tarda demasiado en cargar los datos.",
                Categoria.SOFTWARE,
                Impacto.MEDIO,
                Urgencia.MEDIA
        );

        service.cambiarEstado(1, Estado.LISTA, null);
        service.cambiarEstado(1, Estado.EN_DESARROLLO, null);
        service.cambiarEstado(1, Estado.EN_VALIDACION, null);

        assertThrows(IllegalArgumentException.class,
                () -> service.cambiarEstado(1, Estado.FINALIZADA, null));
    }

    @Test
    void expediteIntegradoConServicio() {
        service.registrarIncidencia(
                "Servidor caído",
                "El servidor principal no responde a peticiones.",
                Categoria.RED,
                Impacto.ALTO,
                Urgencia.ALTA
        );
        service.registrarIncidencia(
                "Teclado roto",
                "Varias teclas del teclado no funcionan bien.",
                Categoria.HARDWARE,
                Impacto.BAJO,
                Urgencia.BAJA
        );

        service.getExpediteService().marcarExpedite(1);
        assertEquals(Prioridad.EXPEDITE, service.buscarPorId(1).getPrioridad());

        assertThrows(IllegalStateException.class,
                () -> service.getExpediteService().marcarExpedite(2));
    }
}
