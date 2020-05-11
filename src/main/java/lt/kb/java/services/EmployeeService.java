package lt.kb.java.services;

import lt.kb.java.model.Employee;
import lt.kb.java.model.Salary;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeService {


    public static Employee createEmployee(Employee employee) throws SQLException {
        Connection conn = DBService.getConnectionFromCP();
        PreparedStatement stm = conn.prepareStatement(
                "Insert into employees " +
                        "(birth_date, first_name, last_name, gender, hire_date) " +
                        "values (?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        try {
            stm.setDate(1, employee.getBirthDate());
            stm.setString(2, employee.getFirstName());
            stm.setString(3, employee.getLastName());
            stm.setString(4, employee.getGender());
            stm.setDate(5, employee.getHireDate());
            int row = stm.executeUpdate();
            System.out.println("DELETE " + row);


            ResultSet rs = stm.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            System.out.println("ID: " + generatedKey);
            employee.setEmpNo(generatedKey);
            stm.executeUpdate();
            stm.close();
            return employee;

        } catch (SQLException se) {

            return null;
        }
    }


    public static void deleteEmployee(Employee employee) throws SQLException {
        Connection conn = DBService.getConnectionFromCP();
        try (PreparedStatement stm = conn.prepareStatement("DELETE from employees where emp_no=?")) {
            stm.setInt(1, employee.getEmpNo());
            int row = stm.executeUpdate();
            System.out.println(row);
            stm.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("bedaaaa " + e);
            conn.close();
        } finally {
            conn.close();
        }

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static List<Employee> loadEmployees(int from, int limit) {
        List<Employee> empLIst;
        try {
            Connection conn = DBService.getConnectionFromCP();
// -----------------------Pirmas variantas selekto-------------------------------------
/*
            PreparedStatement stm = conn.prepareStatement(" with em as ( select * from employees order by emp_no limit ?,?)\n" +
                    " select emp.*, s.salary,s.from_date,s.to_date from salaries s \n" +
                    " join employees emp\n" +
                    " on emp.emp_no=s.emp_no\n" +
                    " where s.emp_no in (select emp_no from em)");*/

// ------------------------Antras variantas selekto------------------------

            PreparedStatement stm = conn.prepareStatement("select m.*, " +
                    " s.salary,s.from_date,s.to_date,s.to_date,s.emp_no as s_emp_no" +
                    " from  (select * from employees limit ?,?) m\n" +
                    " left join salaries s using (emp_no);");
            stm.setInt(1, (limit * from));
            stm.setInt(2, limit);
            ResultSet rs = stm.executeQuery();
            List<Salary> salaryList = new ArrayList<>();
            Map<Integer, Employee> employeeMap = new HashMap<>();

//Einam per result seta ir susirenkam duomenis i salary lista ir employee Mapa

            while (rs.next()) {
                employeeMap.put(rs.getInt(1), EmployeeMap.fromResultSet(rs));
                salaryList.add(SalaryMap.fromResultSet(rs));
            }

            empLIst = employeeMap.entrySet().stream().map(e -> {
                List<Salary> salaryList1;
                salaryList1 = salaryList.stream()
                        .filter(salary -> salary.getEmpNo() == e.getValue().getEmpNo())
                        .collect(Collectors.toList());
                e.getValue().setSalaries(salaryList1);
                return e.getValue();
            }).collect(Collectors.toList());
            stm.close();
            conn.close();
            return empLIst;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static Employee loadEmployee(int emp_no) throws SQLException {
        Employee emp;
        Connection conn;
        conn = DBService.getConnectionFromCP();
        PreparedStatement stm = conn.prepareStatement(
                "select m.*,  s.salary,s.from_date,s.to_date,s.emp_no as s_emp_no \n" +
                        " from employees m\n" +
                        " left join salaries s\n" +
                        "using (emp_no) where m.emp_no=?;"
        );
        stm.setInt(1, emp_no);
        ResultSet rs = stm.executeQuery();
        List<Salary> salaryList = new ArrayList<>();

//Einam per result seta ir susirenkam duomenis i salary lista ir employee Mapa

        if (!rs.next()) {
            System.out.println("ResultSet is empty");
            stm.close();
            conn.close();
            return null;
        } else {
            do {
                emp = EmployeeMap.fromResultSet(rs);
                salaryList.add(SalaryMap.fromResultSet(rs));
                System.out.println(rs.getString(3));
            } while (rs.next());
            emp.setSalaries(salaryList);
            emp.getSalaries().stream().
                    forEach(salary -> System.out.println(salary.getSalary()));
            System.out.println(emp.getLastName());
            stm.close();
            conn.close();

        }
        return emp;

    }

    public static void saveEmployee(Employee employee) throws SQLException {

        Connection conn = DBService.getConnectionFromCP();
//         conn = DBService.getConnectionFromCP();
        Integer emp_no = employee.getEmpNo();
        String firsName = employee.getFirstName();
        String secondName = employee.getLastName();
        int row;
        try {
            PreparedStatement stm = conn.prepareStatement("update employees SET first_name=?,last_name=?,gender=?,hire_date=?,birth_date=? where emp_no= ?;");
            stm.setString(1, firsName);
            stm.setString(2, secondName);
            stm.setString(3, employee.getGender());
            stm.setDate(4, employee.getHireDate());
            stm.setDate(5, employee.getBirthDate());
            stm.setInt(6, emp_no);


            System.out.println("as cia");
            row = stm.executeUpdate();

            System.out.println("***row**** " + row);
            stm.close();
            conn.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            conn.close();
        }

    }

}