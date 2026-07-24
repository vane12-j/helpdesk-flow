package cr.utn.helpdesk;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.service.IncidenciaService;

public class Main {

    public static void main(String[] args) {

        IncidenciaService service = new IncidenciaService();

        service.registrarIncidencia(
                "Sin Internet",
                "La computadora no tiene acceso a Internet.",
                Categoria.RED,
                Impacto.ALTO,
                Urgencia.ALTA
        );

        service.registrarIncidencia(
                "Mouse dañado",
                "El mouse dejó de funcionar correctamente.",
                Categoria.HARDWARE,
                Impacto.BAJO,
                Urgencia.MEDIA
        );

        System.out.println("===== TABLERO KANBAN INICIAL =====");
        System.out.println(service.getKanbanService().obtenerTablero());

        service.getExpediteService().marcarExpedite(1);
        System.out.println("\n===== EXPEDITE ASIGNADA A ID 1 =====");
        System.out.println(service.getKanbanService().obtenerTablero());

        service.cambiarEstado(1, Estado.LISTA, null);
        service.cambiarEstado(1, Estado.EN_DESARROLLO, null);
        service.cambiarEstado(1, Estado.EN_VALIDACION, null);
        service.cambiarEstado(1, Estado.FINALIZADA, "Se reemplazó el cable de red.");

        System.out.println("\n===== TABLERO KANBAN FINAL =====");
        System.out.println(service.getKanbanService().obtenerTablero());
    }
}
