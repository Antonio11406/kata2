package software.ulpgc.katas.io;

import software.ulpgc.katas.model.Employee;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TsvEmployeeWriter implements EmployeeWriter {
    private final OutputStream outputStream;

    public TsvEmployeeWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(List<Employee> employees) throws IOException {
        if (outputStream == null) {
            throw new IOException("El OutputStream es nulo.");
        }

        // Usamos PrintWriter para facilitar la escritura línea a línea
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            // Escribimos la cabecera del nuevo fichero
            writer.println("Name\tAge\tDepartment\tGender");

            // Escribimos cada empleado extraído
            for (Employee employee : employees) {
                writer.printf("%s\t%d\t%s\t%s%n",
                        employee.name(),
                        employee.age(),
                        employee.department(),
                        employee.gender());
            }
        }
    }
}