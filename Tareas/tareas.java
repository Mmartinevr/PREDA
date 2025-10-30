
/**
 * Write a description of class tareas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;
// Clase principal para manejar los argumentos y ejecutar el programa
public class tareas {
    public static void main(String[] args) {
        boolean trazar = false; // Activar/desactivar trazado
        String archivoEntrada = null; // Archivo de entrada
        String archivoSalida = null; // Archivo de salida

        // Parsear argumentos
        for (String arg : args) {
            switch (arg) {
                case "-t":
                    trazar = true; // Activar trazado
                    break;
                case "-h":
                    mostrarAyuda(); // Mostrar ayuda y salir
                    return;
                default:
                    if (archivoEntrada == null) {
                        archivoEntrada = arg; // Primer argumento como archivo de entrada
                    } else if (archivoSalida == null) {
                        archivoSalida = arg; // Segundo argumento como archivo de salida
                    } else {
                        System.err.println("Error: Demasiados argumentos.");
                        mostrarAyuda();
                        return;
                    }
                    break;
            }
        }

        // Iniciar programa si los argumentos son válidos
        try {
            // Leer los datos de entrada
            Tarea tarea = GestorArchivos.leerArchivo(archivoEntrada);

            // Resolver el problema de asignación
            Asignacion asignacion = new Asignacion(tarea);

            if (trazar) {
                System.out.println("Resolviendo con trazado activado...");
            }

            asignacion.resolver(trazar);

            // Escribir los resultados
            GestorArchivos.escribirArchivo(archivoSalida, asignacion);
        } catch (FileNotFoundException e) {
            System.err.println("Error: El archivo especificado no existe: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    // Método para mostrar ayuda sobre el uso del programa
    private static void mostrarAyuda() {
        System.out.println("SINTAXIS: java tareas [-t][-h] [fichero entrada] [fichero salida]");
        System.out.println("  -t           Traza cada paso del algoritmo.");
        System.out.println("  -h           Muestra esta ayuda.");
        System.out.println("  [fichero entrada] Nombre del fichero de entrada.");
        System.out.println("  [fichero salida]  Nombre del fichero de salida (opcional).");
    }
}




