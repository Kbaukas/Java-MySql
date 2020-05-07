import lt.kb.java.Connection.MysqlCon;
import lt.kb.java.empDBServices.EmployeeService;
import lt.kb.java.retrieveData.Employee;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestServices {
    @Before
    public void start() throws SQLException {
        Connection conn;
        PreparedStatement statement;
        conn = MysqlCon.createConnection();
        statement = conn.prepareStatement("Create table employees(emp_no int PRIMARY KEY auto_increment,birth_date date,first_name varchar(14),last_name varchar(16),gender varchar(1),hire_date date)");
        statement.executeUpdate();
        statement = conn.prepareStatement("INSERT into employees(birth_date,first_name,last_name,gender,hire_date)" +
                " values ('1921-12-12','kitas','kitas','M','1940-12-12')," +
                "('1921-12-12','kitas3','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas4','adsad','M','1940-12-12')" +
                ",('1921-12-12','kitas6','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas5','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas1','akkk','M','1940-12-12')," +
                "('1921-12-12','kitas7','adsad','M','1940-12-12'),('1921-12-12','kitas8','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas9','adsad','M','1940-12-12'),('1921-12-12','kitas10','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas11','adsad','M','1940-12-12'),('1921-12-12','kitas12','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas13','adsad','M','1940-12-12'),('1921-12-12','kitas14','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas15','adsad','M','1940-12-12'),('1921-12-12','kitas16','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas17','adsad','M','1940-12-12'),('1921-12-12','kitas18','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas19','adsad','M','1940-12-12'),('1921-12-12','kitas20','adsad','M','1940-12-12')," +
                "('1921-12-12','kitas2','adsad','M','1940-12-12')");

        statement.executeUpdate();
        statement = conn.prepareStatement("Create table salaries(emp_no int,salary int, from_date date, to_date date)");
        statement.executeUpdate();
        statement = conn.prepareStatement("INSERT into salaries(emp_no,salary,from_date,to_date)" +
                " values ('1','3000','1979-11-11','1980-02-12')," +
                "('1','3000','1980-10-12','1981-12-12')," +
                "('1','4000','1982-11-12','1983-12-12')," +
                "('2','3000','1980-02-17','1981-12-12')," +
                "('3','3000','1980-03-08','1981-12-12')," +
                "('4','3000','1980-08-11','1981-12-12')," +
                "('5','3000','1980-09-10','1981-12-12')," +
                "('6','3000','1980-11-14','1981-12-12')," +
                "('7','3000','1980-10-10','1981-12-12')," +
                "('9','6000','1977-10-19','1979-12-12')," +
                "('8','3000','1980-10-12','1981-12-12')," +
                "('8','4000','1981-10-12','1982-12-12')," +
                "('10','4000','1982-11-12','1983-12-12')," +
                "('11','3000','1980-02-17','1981-12-12')," +
                "('11','3000','1980-03-08','1981-12-12')," +
                "('12','3000','1980-08-11','1981-12-12')," +
                "('12','3000','1980-09-10','1981-12-12')," +
                "('13','3000','1980-11-14','1981-12-12')," +
                "('14','3000','1980-10-10','1981-12-12')," +
                "('15','6000','1977-10-19','1979-12-12')," +
                "('15','3000','1980-10-12','1981-12-12')," +
                "('16','4000','1982-11-12','1983-12-12')," +
                "('16','3000','1980-02-17','1981-12-12')," +
                "('17','3000','1980-03-08','1981-12-12')," +
                "('18','3000','1980-08-11','1981-12-12')," +
                "('18','3000','1980-09-10','1981-12-12')," +
                "('19','3500','1980-11-14','1981-12-12')," +
                "('19','3000','1980-10-10','1981-12-12')," +
                "('20','6000','1977-10-19','1979-12-12')," +
                "('2','7000','1977-10-19','1979-12-10')"
        );

        statement.executeUpdate();
        statement.close();
        conn.close();
    }

    @Test
    public void test() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        employeeList = EmployeeService.loadEmployees(3, 6);
        assertNotNull(employeeList);
        assertEquals(3, employeeList.size());
        assertNotNull(employeeList.get(0).getSalaries());
        assertNotNull(employeeList.get(1).getSalaries());
        assertEquals(3500, employeeList.get(0).getSalaries().get(0).getSalary());
        assertEquals(3000, employeeList.get(0).getSalaries().get(1).getSalary());
        assertEquals(6000, employeeList.get(1).getSalaries().get(0).getSalary());
        assertEquals(2, employeeList.get(0).getSalaries().size());
        assertEquals(1, employeeList.get(1).getSalaries().size());

    }
// testas kai employee neturi salary
    @Test
    public void testSalaries() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        employeeList = EmployeeService.loadEmployees(3, 6);
        assertEquals(0, employeeList.get(2).getSalaries().size());
    }

}

