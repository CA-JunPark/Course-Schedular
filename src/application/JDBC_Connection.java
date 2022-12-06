package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC_Connection {

    public static void main(String[] args) {
        try{
            Connection connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/courses", "root", "1234");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from Student");

            while (resultSet.next()){
                System.out.println(resultSet.getString("Student_id"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
