package software.ulpgc.katas.io;

import software.ulpgc.katas.model.Employee;
import software.ulpgc.katas.model.Gender;
import java.io.*; import java.util.List;
import java.util.Objects;

public class FileEmployeeReader implements {
    private final File file;
    public FileEmployeeReader(File file) {
        this.file = file;
    }
    @Override public List<Employee> readAll() {
        try (InputStream is = new FileInputStream(file)) {
            return loadFrom(is);
        } catch (FileNotFoundException e) {
            System.out.println("Aviso: El archivo no existe o la ruta es inválida -> " + file.getPath()); return List.of();
        } catch (IOException e) {
            System.out.println("Error de lectura al procesar el archivo -> " + e.getMessage()); return List.of();
        }
    }
    public List<Employee> loadFrom(InputStream is) {
        return new BufferedReader(new InputStreamReader(is)) .lines() .skip(1) .map(this::toEmployee) .filter(Objects::nonNull) .toList();
    }
    private Employee toEmployee(String line) {
        return toEmployee(line.split("\t"));
    }
    private Employee toEmployee(String[] fields) {
        if (fields.length < 6) return null;
        try {
            return new Employee( fields[0].trim(), parseIntOrDefault(fields[1].trim(), 0), fields[3].trim(), genderOf(fields[5].trim()) );
        } catch (Exception e) {
            return null;
        }
    }
    private Gender genderOf(String field) {
        try {
            return Gender.valueOf(field);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value); } catch (NumberFormatException e) { return defaultValue;
        }
    }
}

