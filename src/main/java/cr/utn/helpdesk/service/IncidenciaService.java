package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Prioridad;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.Incidencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaService {

    private final List<Incidencia> incidencias = new ArrayList<>();
    private int siguienteId = 1;
    private final ExpediteService expediteService;

    public IncidenciaService() {
        this.expediteService = new ExpediteService(incidencias);
    }

    public Incidencia registrarIncidencia(String titulo,
                                          String descripcion,
                                          Categoria categoria,
                                          Impacto impacto,
                                          Urgencia urgencia) {

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }

        if (descripcion == null || descripcion.length() < 10) {
            throw new IllegalArgumentException("La descripción debe tener al menos 10 caracteres.");
        }

        Incidencia incidencia = new Incidencia(
                siguienteId++,
                titulo,
                descripcion,
                categoria,
                impacto,
                urgencia,
                LocalDate.now()
        );

        incidencia.setEstado(Estado.REGISTRADA);
        incidencia.setPrioridad(PrioridadCalculator.calcular(impacto, urgencia));

        incidencias.add(incidencia);

        return incidencia;
    }

    public List<Incidencia> obtenerIncidencias() {
        return List.copyOf(incidencias);
    }

    public Incidencia buscarPorId(int id) {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getId() == id) {
                return incidencia;
            }
        }
        return null;
    }

    public List<Incidencia> buscarPorEstado(Estado estado) {
        List<Incidencia> resultado = new ArrayList<>();

        for (Incidencia incidencia : incidencias) {
            if (incidencia.getEstado() == estado) {
                resultado.add(incidencia);
            }
        }

        return resultado;
    }

    public List<Incidencia> buscarPorPrioridad(Prioridad prioridad) {
        List<Incidencia> resultado = new ArrayList<>();

        for (Incidencia incidencia : incidencias) {
            if (incidencia.getPrioridad() == prioridad) {
                resultado.add(incidencia);
            }
        }

        return resultado;
    }

    public List<Incidencia> buscarPorCategoria(Categoria categoria) {
        List<Incidencia> resultado = new ArrayList<>();

        for (Incidencia incidencia : incidencias) {
            if (incidencia.getCategoria() == categoria) {
                resultado.add(incidencia);
            }
        }

        return resultado;
    }

    public void cambiarEstado(int id, Estado nuevoEstado, String solucion) {
        Incidencia incidencia = buscarPorId(id);

        if (incidencia == null) {
            throw new IllegalArgumentException("Incidencia no encontrada.");
        }

        EstadoTransitionValidator.validarYCambiar(incidencia, nuevoEstado, solucion);
    }

    public ExpediteService getExpediteService() {
        return expediteService;
    }

    public KanbanService getKanbanService() {
        return new KanbanService(incidencias);
    }
}
