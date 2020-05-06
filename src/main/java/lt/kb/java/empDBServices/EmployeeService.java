package lt.kb.java.empDBServices;

import lt.kb.java.Connection.MysqlCon;
import lt.kb.java.retrieveData.Employee;
import lt.kb.java.retrieveData.EmployeeMap;
import lt.kb.java.retrieveData.Salary;
import lt.kb.java.retrieveData.SalaryMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Grazinti employee puslapi
     *
     * @param from  puslapio numeris (numeruojame nuo 0)
     * @param limit puslapio dydis
     * @return
     */

    public static List<Employee> loadEmployees(int from, int limit) throws SQLException {
        List<Employee> empLIst = new ArrayList<>();
        try {
            Connection conn = MysqlCon.createConnection();
            PreparedStatement stm = conn.prepareStatement(" with em as ( select * from employees order by emp_no limit ?,?)\n" +
                    " select emp.*, s.salary,s.from_date,s.to_date from salaries s \n" +
                    " join employees emp\n" +
                    " on emp.emp_no=s.emp_no\n" +
                    " where s.emp_no in (select emp_no from em)");

            stm.setInt(1, (limit * from));
            stm.setInt(2, limit);
            ResultSet rs = stm.executeQuery();
            List<Salary> salaryList = new ArrayList<>();
            Set<Employee> employeeSet = new HashSet<>();
            Map<Integer, Employee> employeeMap = new HashMap<>();

//Einam per result seta ir susirenkam duomenis i salary lista ir employee Mapa

            while (rs.next()) {
                employeeMap.put(rs.getInt(1), EmployeeMap.fromResultSet(rs));
                salaryList.add(SalaryMap.fromResultSet(rs));
            }

            empLIst = employeeMap.entrySet().stream().map(e -> {
                List<Salary> salaryList1 = new ArrayList<>();
                salaryList1 = salaryList.stream()
                        .filter(salary -> salary.getEmpNo() == e.getValue().getEmpNo())
                        .collect(Collectors.toList());
                e.getValue().setSalaries(salaryList1);
                return e.getValue();
            }).collect(Collectors.toList());
//            empLIst.forEach(e -> System.out.println(e.getFirstName()));

            return empLIst;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }
}