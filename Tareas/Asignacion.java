
/**
 * Write a description of class Asignacion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;
// Clase para realizar la asignación mediante el algoritmo de Ramificación y Poda
class Asignacion {
    private Tarea tarea; // Objeto que contiene la matriz de costos
    private int[] asignacionOptima; // Arreglo que almacena la asignación óptima
    private int costoMinimo; // Costo mínimo encontrado

    // Constructor de la clase
    public Asignacion(Tarea tarea) {
        this.tarea = tarea;
        this.asignacionOptima = new int[tarea.getFilas()];
        Arrays.fill(asignacionOptima, -1); // Inicializar la asignación con -1
        this.costoMinimo = Integer.MAX_VALUE; // Inicializar el costo mínimo con un valor alto
    }

    // Método para resolver el problema de asignación
    public void resolver(boolean trazar) {
        int[][] costos = tarea.getCostos();
        boolean[] tareasAsignadas = new boolean[tarea.getColumnas()]; // Seguimiento de tareas ya asignadas
        backtrack(0, 0, tareasAsignadas, new int[tarea.getFilas()], costos, trazar);
    }

    // Método recursivo para explorar todas las posibles asignaciones
    private void backtrack(int agente, int costoActual, boolean[] tareasAsignadas, int[] asignacionActual, int[][] costos, boolean trazar) {
        // Caso base: todos los agentes están asignados
        if (agente == tarea.getFilas()) {
            if (costoActual < costoMinimo) {
                costoMinimo = costoActual; // Actualizar el costo mínimo
                System.arraycopy(asignacionActual, 0, asignacionOptima, 0, asignacionActual.length); // Guardar la asignación óptima
            }
            return;
        }

        // Explorar todas las tareas disponibles para el agente actual
        for (int tarea = 0; tarea < costos[0].length; tarea++) {
            if (!tareasAsignadas[tarea]) { // Continuar solo si la tarea no está asignada
                asignacionActual[agente] = tarea; // Asignar la tarea al agente
                tareasAsignadas[tarea] = true; // Marcar la tarea como asignada

                if (trazar) {
                    System.out.println("Agente " + (agente + 1) + " asignado a tarea " + (tarea + 1) + " con costo acumulado: " + (costoActual + costos[agente][tarea]));
                }

                // Llamada recursiva para el siguiente agente
                backtrack(agente + 1, costoActual + costos[agente][tarea], tareasAsignadas, asignacionActual, costos, trazar);

                // Deshacer la asignación para explorar otras opciones
                tareasAsignadas[tarea] = false;
            }
        }
    }

    // Imprimir la asignación óptima y su costo
    public void imprimirResultado(PrintStream out) {
        out.println("La salida tiene la asignación con coste mínimo siendo el primer valor el del agente y el segundo el de la tarea asignada.");
        for (int i = 0; i < asignacionOptima.length; i++) {
            out.println((i + 1) + " " + (asignacionOptima[i] + 1));
        }
        out.println("Costo mínimo: " + costoMinimo);
    }
}



