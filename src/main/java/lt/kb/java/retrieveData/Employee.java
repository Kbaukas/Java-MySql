package lt.kb.java.retrieveData;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Employee {
//    @Override
//    public String toString() {
//        return "Employee{" +
//                "empNo=" + empNo +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", gender='" + gender + '\'' +
//                ", birthDate=" + birthDate +
//                ", hireDate=" + hireDate +
//                '}';
//    }

    private Integer empNo;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private Date hireDate;
    List<Salary> salaries = new ArrayList<>();

    public Integer getEmpNo() {
        return empNo;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }

    public static Employee fromResultSetByIndex(ResultSet resultSet) {
        try {
            Employee employee = new Employee();
            employee.setEmpNo(resultSet.getInt(1));
            employee.setFirstName(resultSet.getString(2));
            employee.setLastName(resultSet.getString(3));
            employee.setGender(resultSet.getString(4));
            employee.setBirthDate(resultSet.getDate(5));
            employee.setHireDate(resultSet.getDate(6));
            return employee;

        } catch (SQLException e) {
            return null;
        }
    }


    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
}