package software.ulpgc.katas;

import software.ulpgc.katas.io.*;
import software.ulpgc.katas.model.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // -------------------------------------------------------------
        // CASO 1: Leer de fichero TSV -> Escribir en NUEVO fichero TSV
        // -------------------------------------------------------------
        File inputFile = new File("employees.tsv");
        File outputFile = new File("empleados_extraidos.tsv");

        if (inputFile.exists()) {
            // 1. Extraer los datos
            EmployeeReader fileReader = new FileEmployeeReader(inputFile);
            List<Employee> employeesFromFile = fileReader.readAll();

            // 2. Crear el nuevo fichero inyectando un OutputStream al escritor
            try (OutputStream fileOut = new FileOutputStream(outputFile)) {
                EmployeeWriter tsvWriter = new TsvEmployeeWriter(fileOut);
                tsvWriter.write(employeesFromFile);

                System.out.println("ÉXITO: Se ha creado el archivo '" + outputFile.getName() + "' con " + employeesFromFile.size() + " empleados.");
            } catch (Exception e) {
                System.err.println("Error al escribir el nuevo fichero: " + e.getMessage());
            }
        } else {
            System.err.println("El archivo " + inputFile.getName() + " no se ha encontrado en la raíz.");
        }

        System.out.println("\n======================================================\n");

        // -------------------------------------------------------------
        // CASO 2: Leer desde URL remota -> Imprimir en Consola
        // -------------------------------------------------------------
        System.out.println("--- Imprimiendo empleados desde URL ---");
        try {
            URL url = URI.create("https://raw.githubusercontent.com/sachin365123/CSV-files-for-Data-Science-and-Machine-Learning/refs/heads/main/Employee%20Dataset%20-%20TSV.txt").toURL();
            EmployeeReader urlReader = new UrlEmployeeReader(url);
            List<Employee> employeesFromUrl = urlReader.readAll();

            // Imprimimos el resultado directamente
            employeesFromUrl.forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Error al procesar la URL: " + e.getMessage());
        }
    }
}