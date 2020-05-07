import lt.kb.java.model.Employee;
import lt.kb.java.services.DBService;
import lt.kb.java.services.EmployeeService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeServiceTest extends DBTestBase {

    @Test
    public void testInitData() throws SQLException {
        Connection connection = DBService.getConnectionFromCP();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) FROM employees");
        assertTrue(resultSet.next());
        Assertions.assertEquals(14, resultSet.getInt(1));
       connection.close();
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

    }

    // testas kai employee neturi salary
    @Test
    public void testSalaries() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        employeeList = EmployeeService.loadEmployees(2, 5);
        assertEquals(0, employeeList.get(3).getSalaries().size());
    }

}

