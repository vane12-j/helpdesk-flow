package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.enums.Urgencia;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrioridadCalculatorTest {

    @Test
    void impactoAltoUrgenciaAlta_retornaCritica() {
        assertEquals(Prioridad.CRITICA,
                PrioridadCalculator.calcular(Impacto.ALTO, Urgencia.ALTA));
    }

    @ParameterizedTest
    @CsvSource({
            "ALTO, MEDIA, ALTA",
            "ALTO, BAJA, ALTA",
            "MEDIO, ALTA, ALTA",
            "BAJO, ALTA, ALTA"
    })
    void combinacionesAlta_retornaAlta(String impacto, String urgencia, String esperada) {
        assertEquals(Prioridad.valueOf(esperada),
                PrioridadCalculator.calcular(
                        Impacto.valueOf(impacto),
                        Urgencia.valueOf(urgencia)));
    }

    @ParameterizedTest
    @CsvSource({
            "MEDIO, MEDIA, NORMAL",
            "MEDIO, BAJA, NORMAL",
            "BAJO, MEDIA, NORMAL",
            "BAJO, BAJA, NORMAL"
    })
    void combinacionesNormales_retornaNormal(String impacto, String urgencia, String esperada) {
        assertEquals(Prioridad.valueOf(esperada),
                PrioridadCalculator.calcular(
                        Impacto.valueOf(impacto),
                        Urgencia.valueOf(urgencia)));
    }
}
