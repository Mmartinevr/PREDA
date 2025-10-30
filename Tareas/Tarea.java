
/**
 * Write a description of class Tarea here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;
// Clase para manejar los datos de la tarea y costos
class Tarea {
    private int[][] costos; // Matriz de costos
    private int filas, columnas; // Dimensiones de la matriz

    // Constructor que valida las dimensiones y guarda la matriz
    public Tarea(int filas, int columnas, int[][] costos) {
        if (filas <= 0 || columnas <= 0) {
            throw new IllegalArgumentException("Las dimensiones deben ser mayores a cero.");
        }
        this.filas = filas;
        this.columnas = columnas;
        this.costos = costos;
    }

    // Métodos getter para acceder a los datos
    public int[][] getCostos() {
        return costos;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
}
