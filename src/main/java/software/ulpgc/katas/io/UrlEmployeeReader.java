package software.ulpgc.katas.io;

import software.ulpgc.katas.model.Employee;
import software.ulpgc.katas.model.Gender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UrlEmployeeReader implements EmployeeReader {
    private final URL url;
    public UrlEmployeeReader(URL url) {
        this.url = url;
    }
    @Override public List<Employee> readAll() {
        if (url == null) {
            System.err.println("[Error] La URL proporcionada es nula.");
            return List.of();
        }
        try (InputStream is = url.openStream()) {
            return loadFrom(is);
        } catch (IOException e) {
            System.err.println("[Error] Ocurrió un error al leer desde la URL: " + e.getMessage()); return List.of();
        }
    }
    private List<Employee> loadFrom(InputStream is) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines().toList();
            return loadFrom(lines);
        }
    }
    private List<Employee> loadFrom(List<String> lines) {
        List<Employee> list = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) continue;
            Employee employee = loadFrom(line);
            if (employee != null) {
                list.add(employee);
            }
        }
        return list;
    }
    private Employee loadFrom(String line) {
        String[] fields = line.contains("\t") ? line.split("\t", -1) : line.trim().split("\\s{2,}");
        if (fields.length < 6) {
            System.err.println("[Aviso] Fila omitida por faltar columnas: " + line);
            return null;
        } try {
            String name = fields[0].trim(); int age = fields[1].trim().isEmpty() ? 0 : Integer.parseInt(fields[1].trim());
            String department = fields[3].trim().isEmpty() ? "Desconocido" : fields[3].trim();
            Gender gender = genderOf(fields[5]);
            if (gender == null) {
                System.err.println("[Aviso] Fila omitida por género inválido o ausente: " + name); return null;
            }
            return new Employee(name, age, department, gender);
        } catch (NumberFormatException e) {
            System.err.println("[Aviso] Fila omitida por formato numérico inválido en: " + fields[0]);
            return null;
        }
    }
    private Gender genderOf(String field) {
        return field.isEmpty() ? null : Gender.valueOf(field);
    }
}

