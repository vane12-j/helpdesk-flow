package cr.utn.helpdesk.service;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Estado;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.Incidencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cr.utn.helpdesk.enums.Prioridad;
public class IncidenciaService {
    private final List<Incidencia> incidencias = new ArrayList<>();
    private int siguienteId = 1;

    public Incidencia registrarIncidencia(String titulo, String descripcion, Categoria categoria, Impacto impacto, Urgencia urgencia) {

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
        incidencia.setPrioridad(calcularPrioridad(impacto, urgencia));

        incidencias.add(incidencia);

        return incidencia;
    }

    public List<Incidencia> obtenerIncidencias() {
        return incidencias;
    }

    private Prioridad calcularPrioridad(Impacto impacto, Urgencia urgencia) {

        if (impacto == Impacto.ALTO && urgencia == Urgencia.ALTA) {
            return Prioridad.CRITICA;
        }

        if (impacto == Impacto.ALTO &&
                (urgencia == Urgencia.MEDIA || urgencia == Urgencia.BAJA)) {
            return Prioridad.ALTA;
        }

        if ((impacto == Impacto.MEDIO || impacto == Impacto.BAJO) &&
                urgencia == Urgencia.ALTA) {
            return Prioridad.ALTA;
        }

        return Prioridad.NORMAL;
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

        Estado estadoActual = incidencia.getEstado();

        switch (estadoActual) {

            case REGISTRADA:

                if (nuevoEstado != Estado.LISTA) {
                    throw new IllegalStateException("Transición no permitida.");
                }

                break;

            case LISTA:

                if (nuevoEstado != Estado.EN_DESARROLLO) {
                    throw new IllegalStateException("Transición no permitida.");
                }

                break;

            case EN_DESARROLLO:

                if (nuevoEstado != Estado.EN_VALIDACION) {
                    throw new IllegalStateException("Transición no permitida.");
                }

                break;

            case EN_VALIDACION:

                if (nuevoEstado != Estado.FINALIZADA) {
                    throw new IllegalStateException("Transición no permitida.");
                }

                if (solucion == null || solucion.isBlank()) {
                    throw new IllegalArgumentException("Debe indicar la solución.");
                }

                incidencia.setSolucion(solucion);
                incidencia.setFechaCierre(LocalDate.now());

                break;

            case FINALIZADA:

                throw new IllegalStateException("La incidencia ya está finalizada.");
        }

        incidencia.setEstado(nuevoEstado);

    }
}
