package lt.kb.java;

import com.google.gson.*;
import lt.kb.java.Connection.MysqlCon;
import lt.kb.java.empDBServices.EmployeeService;
import lt.kb.java.retrieveData.Employee;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static lt.kb.java.retrieveData.EmployeeMap.fromResultSet;

public class EmployeesDemo {
    public static void main(String[] args) throws SQLException, IOException {
        try (Connection con = MysqlCon.createConnection()) {
//        Logger logger = LoggerFactory.getLogger(EmployeesDemo.class);
//        logger.info("Hello World-----------");
            PreparedStatement statement = con.prepareStatement
                    ("INSERT into Employees(birth_date,first_name,last_name,gender,hire_date)" +
                                    " values ('1921-12-12','kitas','kitas','M','1940-12-12')," +
                                    "('1921-12-12','kitas1','1212','M','1940-12-12')," +
                                    "('1921-12-12','kitas2','adsad','M','1940-12-12')",
                            Statement.RETURN_GENERATED_KEYS);
            System.out.println("Prisijunge");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Statement stmt1 = con.createStatement();
            statement.executeUpdate();

//     -----------------------------------Metodo loadEmployees tikrinimas :) --------------------------
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

//            stmt.execute("DELETE from employees where employees.first_name like '%naujas%'");
//            stmt.execute("INSERT INTO employees ( birth_date, first_name, last_name, gender, hire_date) values('1981-12-03','Karolis','Baukas', 'M','2009-10-09')");
//
//            stmt.executeQuery("SELECT * from employees where first_name='Pohua' ");
//            stmt.execute("delete from employees where first_name like 'Karolis'");
//



// ResultSet rs1 = statement.getGeneratedKeys();
//        while(rs1.next()){
//
//            System.out.println("key "+rs1.getInt(1));
//        delete(rs1.getInt(1));}

            ResultSet rs = stmt.executeQuery("SELECT * from employees order by emp_no desc limit 1");
            List<Employee> listas = new ArrayList<>();

//        List<Integer> books = stmt1.executeQuery("SELECT emp_no FROM employees"+emp).list();
//        System.out.println(Employee.fromResultSet(rs));
            long start = System.currentTimeMillis();
            DateFormat df = DateFormat.getDateInstance();

//        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();


            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
            Employee emp = null;
            while (rs.next()) {

                listas.add(fromResultSet(rs));

                emp = fromResultSet(rs);

                //            System.out.println(emp.getBirthDate());
                assert emp != null;
                String g1 = gson.toJson(emp.getBirthDate());


            }
            System.out.println(System.currentTimeMillis() - start);
            System.out.println();
//        listas.stream().forEach(l-> System.out.println(l.getEmpNo()));
            rs.close();
            stmt.close();
            con.close();
        }
    }

    //listas employee
    static List<Employee> getEmployees() {
        try (
                Connection conn = MysqlCon.createConnection();  // 1 zingsnis
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT * FROM employees LIMIT 1");
        ) {
            try (ResultSet resultSet = statement.executeQuery()) { // 2 zingsnis
                List<Employee> employees = new ArrayList<>();
                while (resultSet.next()) {
                    employees.add(fromResultSet(resultSet));   // 3 zingsnis
                }
                return employees;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void delete(int id) {
        String sql = "DELETE FROM employees WHERE emp_no = ?";

        try (Connection conn = MysqlCon.createConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}


class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        String date = element.getAsString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            return (Date) format.parse(date);
        } catch (ParseException e) {
            System.err.println(e);
            return null;
        }
    }
}