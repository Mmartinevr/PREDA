
/**
 * Write a description of class hanoi here.
 * 
 * @author (Miguel Martínez Redondo) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.Scanner;

public class hanoi {
    public static void main(String[] args) {
        // Inicialización de variables para las opciones del programa y archivos
        boolean traza = false; // Indica si se debe activar el modo "traza" (detallar el estado de los postes)
        boolean ayuda = false; // Indica si el usuario solicitó ayuda
        String ficheroEntrada = null; // Archivo desde el cual leer parámetros (opcional)
        String ficheroSalida = null; // Archivo donde escribir el resultado (opcional)

        // Verificar número de argumentos no reconocidos
        int numArgsNoOpciones = 0;
        
        // Parsear los argumentos proporcionados al programa
        for (String arg : args) {
            if (arg.equals("-t")) {
                traza = true;
            } else if (arg.equals("-h")) {
                ayuda = true;
            } else {
                numArgsNoOpciones++; // Contar argumentos no reconocidos
                if (ficheroEntrada == null) {
                    ficheroEntrada = arg;
                } else if (ficheroSalida == null) {
                    ficheroSalida = arg;
                } else {
                    System.err.println("Error: Demasiados argumentos. Se esperaban como máximo dos archivos.");
                    System.out.println("La sintaxis a utilizar es la siguiente:");
                    mostrarAyuda();
                    return; // Finalizar el programa
                }
            }
        }

        // Validar número total de argumentos no reconocidos
        if (numArgsNoOpciones > 2) {
            System.err.println("Error: Demasiados argumentos. El formato esperado es:");
            mostrarAyuda();
            return; // Finalizar el programa
        }

        // Si se solicita ayuda, mostrar la información y salir 
        if (ayuda) {
            mostrarAyuda();
            return;
        }

        // Declaración de variables para los parámetros del problema
        int origen = 1, destino = 3, numDiscos = 0;
        boolean entradaValida = false; // Bandera para verificar la validez de los parámetros
        
        // Intentar leer los parámetros desde un archivo de entrada (si se proporcionó)
        if (ficheroEntrada != null) {
            // Intentar leer desde archivo
            try {
                File archivoEntrada = new File(ficheroEntrada); // Abrir archivo de entrada
                if (!archivoEntrada.exists()) { // Validar existencia
                    throw new FileNotFoundException("El fichero de entrada no existe.");
                }

                Scanner scanner = new Scanner(archivoEntrada); // Usar un scanner para leer el archivo
                origen = scanner.nextInt(); // Leer el poste origen
                destino = scanner.nextInt(); // Leer el poste destino
                numDiscos = scanner.nextInt(); // Leer el número de discos
                scanner.close();

                // Validar los parámetros
                if (origen < 1 || origen > 3 || destino < 1 || destino > 3 || numDiscos < 0) {
                    throw new IllegalArgumentException("Los valores deben ser: origen (1-3), destino (1-3), numDiscos (>=0).");
                }

                entradaValida = true; // Si todo es correcto, la entrada es válida
            } catch (FileNotFoundException e) {
                // Archivo no encontrado, notificar al usuario y cambiar a entrada manual
                System.err.println("Error: " + e.getMessage());
                System.out.println("Cambiando a entrada estándar...");
            } catch (Exception e) {
                // Error al leer parámetros del archivo
                System.err.println("Error al leer el fichero: " + e.getMessage());
                System.out.println("Cambiando a entrada estándar...");
            }
        }

        // Si no se pudo leer desde un archivo, usar entrada estándar, solicitar los datos manualmente
        while (!entradaValida) {
            try {
                System.out.println("Introduce los valores: origen destino numDiscos (separados por espacios):");
                Scanner scanner = new Scanner(System.in); // Leer entrada desde consola
                String linea = scanner.nextLine().trim(); // Eliminar espacios innecesarios
                // Separar los valores introducidos
                String[] partes = linea.split("\\s+"); // Divide por uno o más espacios
                
                if (partes.length != 3) {// Validar que hay tres valores
                    throw new IllegalArgumentException("Debes introducir exactamente tres números separados por espacios.");
                }

                // Intentar convertir las partes a números enteros
                origen = Integer.parseInt(partes[0]); // Leer origen
                destino = Integer.parseInt(partes[1]); // Leer destino
                numDiscos = Integer.parseInt(partes[2]); // Leer número de discos

                // Validar los valores
                if (origen < 1 || origen > 3 || destino < 1 || destino > 3 || numDiscos < 0) {
                    throw new IllegalArgumentException("Los valores deben ser: origen (1-3), destino (1-3), numDiscos (>=0).");
                }

                // Validar que origen y destino no sean iguales
                if (origen == destino) {
                    throw new IllegalArgumentException("El origen y el destino no pueden ser iguales.");
                }
                
                entradaValida = true; // La entrada es válida, salir del bucle
            } catch (NumberFormatException e) {
                // Error al convertir texto a número
                System.err.println("Error: Formato inválido. Asegúrate de que todos los valores son números enteros.");
            } catch (IllegalArgumentException e) {
                // Error de validación específica
                System.err.println("Error de validación: " + e.getMessage());
            } catch (Exception e) {
                // Cualquier otro error
                System.err.println("Error inesperado: " + e.getMessage());
            }
            // Si la entrada no es válida, solicitar de nuevo
            if (!entradaValida) {
                System.out.println("Por favor, introduce los valores de nuevo.");
            }
        }
        
        // Crear instancia del problema con los parámetros proporcionados
        hanoiParam hanoiParam = new hanoiParam(origen, destino, numDiscos);

        // Ejecutar el algoritmo y escribir la salida
        try {
            BufferedWriter writer;
            // Configurar el escritor según la salida
            if (ficheroSalida != null) {
                // Escribir en el fichero proporcionado
                writer = new BufferedWriter(new FileWriter(ficheroSalida));
            } else {
                // Crear archivo por defecto "salida.txt" y también escribir en consola
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter("salida.txt"));
                BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(System.out));
                writer = new MultiWriter(fileWriter, stdoutWriter); // Envolver en una clase que maneja ambas salidas y escribir en estas
            }
            // Resolver el problema y escribir la salida
            hanoiParam.ejecutar(writer, traza);
            // Cerrar el escritor
            writer.close();
        } catch (IOException e) {
            // Error al escribir en la salida
            System.err.println("Error al escribir los datos de salida: " + e.getMessage());
        }
    }
    // Método que muestra cómo usar el programa
    private static void mostrarAyuda() {
        System.out.println("SINTAXIS: java hanoi [-t][-h] [fichero entrada] [fichero salida]");
        System.out.println("-t Traza el algoritmo");
        System.out.println("-h Muestra esta ayuda");
        System.out.println("[fichero entrada] Nombre del fichero de entrada");
        System.out.println("[fichero salida] Nombre del fichero de salida");
    }
}







