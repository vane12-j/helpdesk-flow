package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.model.Incidencia;

import java.time.LocalDate;

public final class EstadoTransitionValidator {

    private EstadoTransitionValidator() {
    }

    public static void validarYCambiar(Incidencia incidencia, Estado nuevoEstado, String solucion) {
        Estado estadoActual = incidencia.getEstado();

        switch (estadoActual) {
            case REGISTRADA -> validarTransicion(estadoActual, nuevoEstado, Estado.LISTA);
            case LISTA -> validarTransicion(estadoActual, nuevoEstado, Estado.EN_DESARROLLO);
            case EN_DESARROLLO -> validarTransicion(estadoActual, nuevoEstado, Estado.EN_VALIDACION);
            case EN_VALIDACION -> {
                validarTransicion(estadoActual, nuevoEstado, Estado.FINALIZADA);
                validarSolucion(solucion);
                incidencia.setSolucion(solucion);
                incidencia.setFechaCierre(LocalDate.now());
            }
            case FINALIZADA -> throw new IllegalStateException("La incidencia ya está finalizada.");
        }

        incidencia.setEstado(nuevoEstado);
    }

    private static void validarTransicion(Estado actual, Estado nuevo, Estado esperado) {
        if (nuevo != esperado) {
            throw new IllegalStateException(
                    "Transición no permitida de " + actual + " a " + nuevo + ".");
        }
    }

    private static void validarSolucion(String solucion) {
        if (solucion == null || solucion.isBlank()) {
            throw new IllegalArgumentException("Debe indicar la solución.");
        }
    }
}
