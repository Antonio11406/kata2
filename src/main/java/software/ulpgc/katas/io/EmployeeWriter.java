package software.ulpgc.katas.io;

import software.ulpgc.katas.model.Employee;

import java.io.IOException;

public interface EmployeeWriter {
    void write(List<Employee> employees) throws IOException;
}
