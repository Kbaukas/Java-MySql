package lt.kb.java.retrieveData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryMap {

    public static Salary fromResultSet(ResultSet resultSet) {
        try {
            Salary salary = new Salary();
            salary.setEmpNo(resultSet.getInt("s_emp_no"));


            if(resultSet.getDate("from_date")==null){ }
            else  salary.setFromDate(resultSet.getDate("from_date").toLocalDate());


            if(resultSet.getDate("to_date")==null){ }
            else  salary.setToDate(resultSet.getDate("to_date").toLocalDate());

            if(resultSet.getInt("salary")==0){
            }
            else  salary.setSalary(resultSet.getInt("salary"));



//            salary.setSalary(resultSet.getInt("salary"));
            return salary;

        } catch (SQLException e) {
            return null;
        }
    }
}