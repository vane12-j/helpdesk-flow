package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.Incidencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EstadoTransitionValidatorTest {

    private Incidencia incidencia;

    @BeforeEach
    void setUp() {
        incidencia = new Incidencia(
                1, "Test", "Descripción larga de prueba",
                Categoria.SOFTWARE, Impacto.MEDIO, Urgencia.MEDIA,
                LocalDate.now()
        );
        incidencia.setEstado(Estado.REGISTRADA);
    }

    @Test
    void flujoCompleto_cambiaEstadosCorrectamente() {
        EstadoTransitionValidator.validarYCambiar(incidencia, Estado.LISTA, null);
        assertEquals(Estado.LISTA, incidencia.getEstado());

        EstadoTransitionValidator.validarYCambiar(incidencia, Estado.EN_DESARROLLO, null);
        assertEquals(Estado.EN_DESARROLLO, incidencia.getEstado());

        EstadoTransitionValidator.validarYCambiar(incidencia, Estado.EN_VALIDACION, null);
        assertEquals(Estado.EN_VALIDACION, incidencia.getEstado());

        EstadoTransitionValidator.validarYCambiar(incidencia, Estado.FINALIZADA, "Solución aplicada.");
        assertEquals(Estado.FINALIZADA, incidencia.getEstado());
        assertEquals("Solución aplicada.", incidencia.getSolucion());
    }

    @ParameterizedTest
    @EnumSource(value = Estado.class, names = {"EN_DESARROLLO", "EN_VALIDACION", "FINALIZADA"})
    void desdeRegistrada_transicionInvalida_lanzaExcepcion(Estado destino) {
        assertThrows(IllegalStateException.class,
                () -> EstadoTransitionValidator.validarYCambiar(incidencia, destino, null));
    }

    @Test
    void cierreSinSolucion_lanzaExcepcion() {
        incidencia.setEstado(Estado.EN_VALIDACION);

        assertThrows(IllegalArgumentException.class,
                () -> EstadoTransitionValidator.validarYCambiar(incidencia, Estado.FINALIZADA, null));

        assertThrows(IllegalArgumentException.class,
                () -> EstadoTransitionValidator.validarYCambiar(incidencia, Estado.FINALIZADA, "   "));
    }

    @Test
    void incidenciaFinalizada_noPermiteCambio() {
        incidencia.setEstado(Estado.FINALIZADA);

        assertThrows(IllegalStateException.class,
                () -> EstadoTransitionValidator.validarYCambiar(incidencia, Estado.LISTA, null));
    }
}
