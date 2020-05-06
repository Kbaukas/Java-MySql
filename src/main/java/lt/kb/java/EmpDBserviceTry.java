package lt.kb.java;

import lt.kb.java.empDBServices.EmployeeService;

import java.sql.SQLException;

public class EmpDBserviceTry {
    public static void main(String[] args) throws SQLException {
        System.out.println("*******************************************************SALARIES***************************************************************");
        EmployeeService.loadEmployees(20, 7).forEach(employee -> {
                    System.out.println(
                            employee.getFirstName() + "  " + employee.getLastName() + " ");
                    employee.getSalaries().forEach(salary -> {
                        System.out.print(salary.getSalary() + ", ");

                    });
                    System.out.println("\n");
                }

        );
        System.out.println("***********************************************************END****************************************************************");

    }

}
