import lt.kb.java.services.DBService;
import org.junit.Before;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {

    @Before
    public void start() throws SQLException {
        Connection conn;
        PreparedStatement statement;
        conn = DBService.createConnection();

        Statement stmt = conn.createStatement();
        // stmt.execute("drop table if exists employees");
        stmt.execute(
                "create table employees (" +
                        " emp_no int," +
                        " first_name varchar(14)," +
                        " last_name varchar(16)," +
                        " gender char(1)," +
                        " birth_date date," +
                        " hire_date date" +
                        ")");
        stmt.execute(
                "insert into employees values" +
                        " (1, 'A1', 'B1', 'F', '2000-01-01', '2018-03-01')," +
                        " (2, 'A2', 'B2', 'M', '2000-01-02', '2018-03-02')," +
                        " (3, 'A3', 'B3', 'F', '2000-01-03', '2018-03-03')," +
                        " (4, 'A4', 'B4', 'M', '2000-01-04', '2018-03-04')," +
                        " (5, 'A5', 'B5', 'F', '2000-01-05', '2018-03-05')," +
                        " (6, 'A6', 'B6', 'M', '2000-01-06', '2018-03-06')," +
                        " (7, 'A7', 'B1', 'F', '2000-01-07', '2018-03-01')," +
                        " (8, 'A8', 'B2', 'M', '2000-01-08', '2018-03-02')," +
                        " (9, 'A9', 'B3', 'F', '2000-01-09', '2018-03-03')," +
                        " (10, 'A10', 'B4', 'M', '2000-01-10', '2018-03-04')," +
                        " (11, 'A11', 'B5', 'F', '2000-01-11', '2018-03-05')," +
                        " (12, 'A12', 'B6', 'M', '2000-01-12', '2018-03-06')," +
                        " (13, 'A13', 'B1', 'F', '2000-01-13', '2018-03-01')," +
                        " (14, 'A14', 'B2', 'M', '2000-01-14', '2018-03-02')"

                // TODO pabaigti

        );
//        stmt.execute("drop table if exists salaries");
        stmt.execute(
                "create table salaries (" +
                        " emp_no int," +
                        " from_date date," +
                        " to_date date," +
                        " salary int" +
                        ")");
        stmt.execute(
                "insert into salaries values" +
                        " (1, '2018-03-01', '9999-01-01', 1500)," +
                        " (3, '2018-03-03', '2018-04-01', 1000)," +
                        " (3, '2018-04-01', '9999-01-01', 2000)," +
                        " (4, '2018-03-04', '2018-05-01', 1100)," +
                        " (4, '2018-05-01', '2020-02-15', 1200)," +
                        " (4, '2020-02-15', '9999-01-01', 1300)," +
                        " (5, '2018-03-05', '9999-01-01', 1111)," +
                        " (6, '2018-03-01', '9999-01-01', 1500)," +
                        " (7, '2018-03-03', '2018-04-01', 1000)," +
                        " (8, '2018-04-01', '9999-01-01', 2000)," +
                        " (9, '2018-03-04', '2018-05-01', 1100)," +
                        " (9, '2018-05-01', '2020-02-15', 1200)," +
                        " (10, '2020-02-15', '9999-01-01', 1300)," +
                        " (11, '2018-03-05', '2019-01-01', 1111)," +
                        " (11, '2018-03-01', '9999-01-01', 1500)," +
                        " (12, '2018-03-03', '2018-04-01', 1000)," +
                        " (12, '2018-04-01', '2019-01-01', 2000)," +
                        " (12, '2019-01-01', '2020-05-01', 1100)," +
                        " (13, '2019-01-01', '2020-05-01', 1100)");
        conn.close();
    }

}

