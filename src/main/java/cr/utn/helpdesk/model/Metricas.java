package cr.utn.helpdesk.model;

import cr.utn.helpdesk.enums.Prioridad;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class Metricas {

    private final int total;
    private final int abiertas;
    private final int cerradas;
    private final double throughput;
    private final double leadTimePromedioDias;
    private final Map<Prioridad, Integer> cantidadPorPrioridad;

    public Metricas(int total, int abiertas, int cerradas, double throughput,
                    double leadTimePromedioDias, Map<Prioridad, Integer> cantidadPorPrioridad) {
        this.total = total;
        this.abiertas = abiertas;
        this.cerradas = cerradas;
        this.throughput = throughput;
        this.leadTimePromedioDias = leadTimePromedioDias;
        this.cantidadPorPrioridad = new EnumMap<>(cantidadPorPrioridad);
    }

    public int getTotal() {
        return total;
    }

    public int getAbiertas() {
        return abiertas;
    }

    public int getCerradas() {
        return cerradas;
    }

    public double getThroughput() {
        return throughput;
    }

    public double getLeadTimePromedioDias() {
        return leadTimePromedioDias;
    }

    public Map<Prioridad, Integer> getCantidadPorPrioridad() {
        return Collections.unmodifiableMap(cantidadPorPrioridad);
    }

    @Override
    public String toString() {
        return "Metricas{" +
                "total=" + total +
                ", abiertas=" + abiertas +
                ", cerradas=" + cerradas +
                ", throughput=" + String.format("%.2f", throughput) +
                ", leadTimePromedioDias=" + String.format("%.2f", leadTimePromedioDias) +
                ", cantidadPorPrioridad=" + cantidadPorPrioridad +
                '}';
    }
}
