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

// Esta clase se encarga de administrar todas las incidencias.
// Aquí se encuentra la lógica de negocio como registrar, buscar y cambiar estados.
public class IncidenciaService {

    // Lista donde se almacenan todas las incidencias registradas.
    private final List<Incidencia> incidencias = new ArrayList<>();

    // Variable utilizada para asignar un ID consecutivo a cada nueva incidencia.
    private int siguienteId = 1;

    // Servicio encargado de manejar las incidencias tipo Expedite.
    private final ExpediteService expediteService;

    // Constructor del servicio.
    public IncidenciaService() {
        this.expediteService = new ExpediteService(incidencias);
    }

    // Registra una nueva incidencia después de validar la información ingresada.
    public Incidencia registrarIncidencia(String titulo, String descripcion, Categoria categoria, Impacto impacto, Urgencia urgencia) {

        // Valida que el título no esté vacío.
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }

        // Valida que la descripción tenga una longitud mínima.
        if (descripcion == null || descripcion.length() < 10) {
            throw new IllegalArgumentException("La descripción debe tener al menos 10 caracteres.");
        }

        // Crea la nueva incidencia asignándole un ID y la fecha actual.
        Incidencia incidencia = new Incidencia(
                siguienteId++,
                titulo,
                descripcion,
                categoria,
                impacto,
                urgencia,
                LocalDate.now()
        );

        // Toda incidencia inicia en estado REGISTRADA.
        incidencia.setEstado(Estado.REGISTRADA);
        // Calcula automáticamente la prioridad según el impacto y la urgencia.
        incidencia.setPrioridad(PrioridadCalculator.calcular(impacto, urgencia));

        // Guarda la incidencia en la lista.
        incidencias.add(incidencia);
        return incidencia;
    }

    // Devuelve una copia de todas las incidencias registradas.
    public List<Incidencia> obtenerIncidencias() {
        return List.copyOf(incidencias);
    }

    // Busca una incidencia utilizando su ID.
    public Incidencia buscarPorId(int id) {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getId() == id) {
                return incidencia;
            }
        }
        return null;
    }

    // Devuelve todas las incidencias que tengan un estado específico.
    public List<Incidencia> buscarPorEstado(Estado estado) {
        List<Incidencia> resultado = new ArrayList<>();

        for (Incidencia incidencia : incidencias) {
            if (incidencia.getEstado() == estado) {
                resultado.add(incidencia);
            }
        }

        return resultado;
    }

    // Devuelve todas las incidencias con una prioridad determinada.
    public List<Incidencia> buscarPorPrioridad(Prioridad prioridad) {
        List<Incidencia> resultado = new ArrayList<>();

        for (Incidencia incidencia : incidencias) {
            if (incidencia.getPrioridad() == prioridad) {
                resultado.add(incidencia);
            }
        }

        return resultado;
    }

    // Devuelve todas las incidencias pertenecientes a una categoría.
    public List<Incidencia> buscarPorCategoria(Categoria categoria) {
        List<Incidencia> resultado = new ArrayList<>();

        for (Incidencia incidencia : incidencias) {
            if (incidencia.getCategoria() == categoria) {
                resultado.add(incidencia);
            }
        }

        return resultado;
    }

    // Cambia el estado de una incidencia validando que la transición sea permitida.
    public void cambiarEstado(int id, Estado nuevoEstado, String solucion) {
        // Busca la incidencia por su ID.
        Incidencia incidencia = buscarPorId(id);
        // Si no existe, genera una excepción.
        if (incidencia == null) {
            throw new IllegalArgumentException("Incidencia no encontrada.");
        }
        // Delega la validación y el cambio de estado al validador de transiciones.
        EstadoTransitionValidator.validarYCambiar(incidencia, nuevoEstado, solucion);
    }
    // Devuelve la instancia del servicio Expedite.
    public ExpediteService getExpediteService() {
        return expediteService;
    }
    // Devuelve una instancia del servicio Kanban para organizar las incidencias por estados.
    public KanbanService getKanbanService() {
        return new KanbanService(incidencias);
    }
}