
/**
 * Write a description of class hanoiParam here.
 * 
 * @author (Miguel Martínez Redondo) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class hanoiParam {
    private int origen; // Poste inicial desde donde comienzan los discos
    private int destino; // Poste al que deben trasladarse los discos
    private int numDiscos; // Cantidad total de discos en el problema
    private List<Integer>[] postes; // Representación de los tres postes como listas de discos

    @SuppressWarnings("unchecked") // Supresión de advertencia para la creación de arrays genéricos
    public hanoiParam(int origen, int destino, int numDiscos) {
        this.origen = origen;
        this.destino = destino;
        this.numDiscos = numDiscos;
        this.postes = new List[3]; // Array de listas, una para cada poste

        // Inicialización de los postes como listas vacías
        for (int i = 0; i < 3; i++) {
            postes[i] = new ArrayList<>();
        }

        // Colocar los discos en el poste de origen en orden descendente
        for (int i = numDiscos; i >= 1; i--) {
            postes[origen - 1].add(i);
        }
    }

    /**
     * Método recursivo para resolver el problema de las Torres de Hanoi.
     * @param n Número de discos a mover.
     * @param origen Poste de origen.
     * @param destino Poste de destino.
     * @param auxiliar Poste auxiliar.
     * @param writer Escribe los movimientos y el estado de los postes.
     * @param traza Indica si se debe escribir el estado detallado de los postes.
     */
    private void resolverhanoiParam(int n, int origen, int destino, int auxiliar, BufferedWriter writer, boolean traza) throws IOException {
        if (n == 1) { // Caso base: mover un solo disco
            moverDisco(origen, destino, writer, traza);
            return;
        }

        // Paso 1: Mover los n-1 discos al poste auxiliar
        resolverhanoiParam(n - 1, origen, auxiliar, destino, writer, traza);

        // Paso 2: Mover el disco más grande directamente al destino
        moverDisco(origen, destino, writer, traza);

        // Paso 3: Mover los n-1 discos desde el auxiliar al destino
        resolverhanoiParam(n - 1, auxiliar, destino, origen, writer, traza);
    }
    
    /**
     * Metodo para mover un disco de un poste a otro y registra el movimiento.
     * @param origen Poste desde donde se mueve el disco.
     * @param destino Poste al que se mueve el disco.
     * @param writer Escribe el movimiento y el estado actual si la traza está activada.
     * @param traza Indica si se debe detallar el estado actual de los postes.
     */
    private void moverDisco(int origen, int destino, BufferedWriter writer, boolean traza) throws IOException {
        // Validación de rango
        if (origen < 1 || origen > 3 || destino < 1 || destino > 3) {
        throw new IllegalArgumentException("Los valores de origen y destino deben estar entre 1 y 3.");
        }
        // Remover el disco de la lista del poste de origen
        int disco = postes[origen - 1].remove(postes[origen - 1].size() - 1);
        // Añadir el disco al poste de destino
        postes[destino - 1].add(disco);

        // Registrar el movimiento en el formato "origen destino"
        writer.write(origen + " " + destino + "\n");

        // Si la traza está activada, registrar el estado de los postes
        if (traza) {
            writer.write("Movimiento: Disco " + disco + " de Poste " + origen + " a Poste " + destino + "\n");
            writer.write("Estado actual de los postes:\n");
            for (int i = 0; i < 3; i++) {
                writer.write("Poste " + (i + 1) + ": " + postes[i] + "\n");
            }
            writer.write("\n"); // Separador entre estados
        }
    }
    
    /**
     * Método principal para ejecutar la solución de las Torres de Hanoi.
     * @param writer Escribe los resultados y el estado (si aplica).
     * @param traza Activa el modo de traza.
     */
    public void ejecutar(BufferedWriter writer, boolean traza) throws IOException {
        if (numDiscos <= 0) {// Validar que haya discos para mover
            writer.write("No hay discos que mover.\n");
            return;
        }
        // Determinar el poste auxiliar según la fórmula "1 + 2 + 3 - origen - destino"
        int auxiliar = 6 - origen - destino;
        // Llamar al método recursivo para resolver el problema
        resolverhanoiParam(numDiscos, origen, destino, auxiliar, writer, traza);
    }
}
