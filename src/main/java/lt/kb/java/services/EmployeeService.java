package lt.kb.java.services;

import lt.kb.java.model.Employee;
import lt.kb.java.model.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

            return empLIst;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }
}