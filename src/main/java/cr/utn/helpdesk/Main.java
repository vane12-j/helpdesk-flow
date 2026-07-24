package cr.utn.helpdesk;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.Incidencia;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Incidencia incidencia = new Incidencia(
                1,
                "No hay conexión a Internet",
                "El equipo no puede acceder a la red desde esta mañana.",
                Categoria.RED,
                Impacto.ALTO,
                Urgencia.ALTA,
                LocalDate.now()
        );

        System.out.println(incidencia);

    }
}