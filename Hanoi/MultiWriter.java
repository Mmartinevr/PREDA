
/**
 * Write a description of class MultiWriter here.
 * 
 * @author (Miguel Martínez Redondo) 
 * @version (a version number or a date)
 */
import java.io.BufferedWriter;
import java.io.IOException;

public class MultiWriter extends BufferedWriter {
    private BufferedWriter[] writers; // Array de escritores a los que se enviarán los datos
    
    // Constructor que recibe múltiples escritores
    public MultiWriter(BufferedWriter... writers) {
        super(writers[0]); // El primer escritor se pasa al constructor padre
        this.writers = writers;
    }

    // Escribe una cadena en todos los escritores
    @Override
    public void write(String s) throws IOException {
        for (BufferedWriter writer : writers) {
            writer.write(s);
        }
    }

    // Añade una nueva línea en todos los escritores
    @Override
    public void newLine() throws IOException {
        for (BufferedWriter writer : writers) {
            writer.newLine();
        }
    }

    // Vacía todos los escritores
    @Override
    public void flush() throws IOException {
        for (BufferedWriter writer : writers) {
            writer.flush();
        }
    }

    // Cierra todos los escritores
    @Override
    public void close() throws IOException {
        for (BufferedWriter writer : writers) {
            writer.close();
        }
    }
}
