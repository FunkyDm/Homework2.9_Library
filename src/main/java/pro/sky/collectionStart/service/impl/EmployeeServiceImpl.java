package pro.sky.collectionStart.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.collectionStart.exceptions.*;
import pro.sky.collectionStart.model.Employee;
import pro.sky.collectionStart.service.EmployeeService;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final int MAX_NUM_EMPLOYEES = 10;

    private final Map<String, Employee> employees = new HashMap<>();

    @Override
    public Employee addEmployee(String firstName, String lastName, double salary, int department) {
        checkStorageIdFull();
        Employee employee = new Employee(firstName, lastName, salary, department);
        checkEmployeeAlreadyExists(employee);
        if (employee.getSalary() <= 0) {
            throw new EmployeeWrongSalaryException("Установлено неправильное значение зарплаты.");
        } else if (0 < employee.getDepartment() && employee.getDepartment() > 5) {
            throw new EmployeeWrongDepartmentNumberException("Установлен неправильный номер отдела.");
        } else {
            employees.put(employee.getFullName(), employee);
            return employee;
        }
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        checkEmployeeNotFound(employee);
        return employees.remove(employee.getFullName());
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        checkEmployeeNotFound(employee);
        return employees.get(employee.getFullName());
    }

    @Override
    public Collection<Employee> printAllEmployees() {
        return Collections.unmodifiableCollection(employees.values());
    }

    private void checkStorageIdFull() {
        if (employees.size() == MAX_NUM_EMPLOYEES) {
            throw new EmployeesStorageFullException("Больше нельзя добавлять сотрудников.");
        }
    }

    private void checkEmployeeAlreadyExists(Employee employee) {
        if (employees.containsKey(employee.getFullName())) {
            throw new EmployeeAlreadyAddedException("Такой сотрудник уже существует.");
        }
    }

    private void checkEmployeeNotFound(Employee employee) {
        if (!employees.containsKey(employee.getFullName())) {
            throw new EmployeeNotFoundExceptions("Такого сотрудника не существует.");
        }
    }

}