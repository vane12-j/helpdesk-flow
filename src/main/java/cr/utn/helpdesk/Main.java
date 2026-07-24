package cr.utn.helpdesk;

import cr.utn.helpdesk.enums.Categoria;
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

        System.out.println("===== TODAS =====");

        for (var incidencia : service.obtenerIncidencias()) {
            System.out.println(incidencia);
        }

        System.out.println("\n===== BUSCAR ID 1 =====");
        System.out.println(service.buscarPorId(1));

        System.out.println("\n===== CATEGORIA RED =====");

        for (var incidencia : service.buscarPorCategoria(Categoria.RED)) {
            System.out.println(incidencia);
        }

    }
}