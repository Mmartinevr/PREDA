
/**
 * Write a description of class GestorArchivos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;
// Clase para manejar la entrada y salida de archivos
class GestorArchivos {

    // Método para leer el archivo de entrada o aceptar datos manualmente
    public static Tarea leerArchivo(String nombreArchivo) throws IOException {
        BufferedReader br;
        
        // Si se proporciona un nombre de archivo, validar su existencia
        if (nombreArchivo != null) {
            File archivo = new File(nombreArchivo);
            if (!archivo.exists()) {
                throw new FileNotFoundException("El archivo de entrada no existe: " + nombreArchivo);
            }
            br = new BufferedReader(new FileReader(nombreArchivo));
        } else {
            // Entrada manual si no se proporciona archivo
            System.out.println("Introduzca los datos manualmente (formato: dimensiones seguido de la matriz):");
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        // Leer dimensiones de la matriz
        String linea = br.readLine().trim();
        validarFormato(linea, true); // Validar que las dimensiones estén en el formato correcto
        String[] dimensiones = linea.split(" ");
        int filas = Integer.parseInt(dimensiones[0]);
        int columnas = Integer.parseInt(dimensiones[1]);

        // Verificar que las dimensiones sean válidas
        if (filas <= 0 || columnas <= 0) {
            throw new IllegalArgumentException("Las dimensiones deben ser mayores a cero.");
        }

        // Leer la matriz de costos
        int[][] costos = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            linea = br.readLine();
            if (linea == null) {
                throw new IllegalArgumentException("El archivo de entrada tiene menos filas de las indicadas.");
            }
            linea = linea.trim();
            validarFormato(linea, false); // Validar que la fila tenga el formato correcto
            String[] valores = linea.split(" ");
            if (valores.length != columnas) {
                throw new IllegalArgumentException("El número de valores en la fila " + (i + 1) + " no coincide con el número de columnas.");
            }
            for (int j = 0; j < columnas; j++) {
                costos[i][j] = Integer.parseInt(valores[j]);
            }
        }

        // Devolver un objeto Tarea con los datos leídos
        return new Tarea(filas, columnas, costos);
    }

    // Método para escribir la asignación óptima en un archivo
    public static void escribirArchivo(String nombreArchivo, Asignacion asignacion) throws IOException {
        PrintStream out;
        
        // Usar "salida.txt" si no se especifica un archivo
        if (nombreArchivo == null) {
            nombreArchivo = "salida.txt";
        }
        out = new PrintStream(new FileOutputStream(nombreArchivo));
        
        // Imprimir el resultado en el archivo y en la consola
        asignacion.imprimirResultado(out);
        out.close();
        asignacion.imprimirResultado(System.out);
    }

    // Validar el formato de una línea de entrada
    private static void validarFormato(String linea, boolean esDimensiones) {
        if (esDimensiones) {
            if (!linea.matches("\\d+ \\d+")) {
                throw new IllegalArgumentException("Las dimensiones deben estar en formato: <filas> <columnas> (ejemplo: 4 4).");
            }
        } else {
            if (!linea.matches("(\\d+\\s+)*\\d+")) {
                throw new IllegalArgumentException("Cada fila de la matriz debe contener solo números separados por espacios.");
            }
        }
    }
}
