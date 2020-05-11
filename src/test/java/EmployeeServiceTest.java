import lt.kb.java.model.Employee;
import lt.kb.java.services.DBService;
import lt.kb.java.services.EmployeeService;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest extends DBTestBase {

    @Test
    public void testInitData() throws SQLException {
        Connection connection = DBService.getConnectionFromCP();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT count (*) FROM employees");
        assertTrue(resultSet.next());
        assertEquals(14, resultSet.getInt(1));
        connection.close();
//        System.out.println("reee");
    }

    @Test
    public void test() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        employeeList = EmployeeService.loadEmployees(2, 5);
        assertNotNull(employeeList);
        assertEquals(4, employeeList.size());
        assertNotNull(employeeList.get(0).getSalaries());
        assertNotNull(employeeList.get(1).getSalaries());
        assertEquals(1111, employeeList.get(0).getSalaries().get(0).getSalary());
        assertEquals(1500, employeeList.get(0).getSalaries().get(1).getSalary());
        assertEquals(1000, employeeList.get(1).getSalaries().get(0).getSalary());
        assertEquals(2000, employeeList.get(1).getSalaries().get(1).getSalary());
        assertEquals(1100, employeeList.get(1).getSalaries().get(2).getSalary());
        assertEquals(2, employeeList.get(0).getSalaries().size());
        assertEquals(3, employeeList.get(1).getSalaries().size());
//        employeeList = EmployeeService.loadEmployees(2, 5);
        assertEquals(1, employeeList.get(2).getSalaries().size());
        assertEquals(0, employeeList.get(3).getSalaries().size());

    }


    @Test
    public void testUpdate() throws SQLException {
        Employee employee = EmployeeService.loadEmployee(3);
        assertEquals("A3", employee.getFirstName());
        assertEquals(Date.valueOf("2000-01-03"), employee.getBirthDate());

        employee.setFirstName("A31");
        employee.setBirthDate(Date.valueOf("2000-02-15"));
        EmployeeService.saveEmployee(employee);
        employee = EmployeeService.loadEmployee(3);
        System.out.println("emp " + employee.getFirstName());
        assertEquals("A31", employee.getFirstName());
        assertEquals(Date.valueOf("2000-02-15"), employee.getBirthDate());
    }

    @Test
    public void testCreate() throws SQLException {
        Employee employee = new Employee();
        employee.setFirstName("X1");
        employee.setLastName("X2");
        employee.setBirthDate(Date.valueOf(LocalDate.of(2000, 12, 31)));
        employee.setGender("M");
        employee.setHireDate(Date.valueOf(LocalDate.of(2020, 3, 31)));
        Employee employee1 = EmployeeService.createEmployee(employee);
        System.out.println(employee1.getFirstName());
        assertNotNull(employee1.getEmpNo());
        assertEquals("X1", employee1.getFirstName());

    }

    @Test
    public void testDelete() throws SQLException {
        Employee employee = EmployeeService.loadEmployee(3);
        EmployeeService.deleteEmployee(employee);
        assertNull(EmployeeService.loadEmployee(3));
//        System.out.println(employee.getFirstName());
    }

}

