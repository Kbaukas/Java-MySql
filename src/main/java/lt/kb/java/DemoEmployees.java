package lt.kb.java;

import lt.kb.java.model.Employee;
import lt.kb.java.services.EmployeeService;

import java.sql.Date;
import java.sql.SQLException;

public class DemoEmployees {
    public static void main(String[] args) throws SQLException {
        System.out.println("*******************************************************SALARIES***************************************************************");
        EmployeeService.loadEmployees(20, 7).forEach(employee -> {
                    System.out.println(
                            employee.getFirstName() + "  " + employee.getLastName() + " ");
                    employee.getSalaries().forEach(salary -> System.out.print(salary.getSalary() + ", "));
                    System.out.println("\n");
                }
        );
        System.out.println("***********************************************************END****************************************************************");


        Employee emp = EmployeeService.loadEmployee(1);
        System.out.println(emp.getLastName());
        emp.setFirstName("A31");
        System.out.println(emp.getFirstName());
        emp.setBirthDate(Date.valueOf("2000-02-15"));
        System.out.println(emp.getBirthDate());
        EmployeeService.saveEmployee(emp);
//        Employee emp1=EmployeeService.loadEmployee(111);
//        EmployeeService.deleteEmployee(emp1);

        System.out.println(emp.getFirstName());
        EmployeeService.createEmployee(emp);
    }


}
