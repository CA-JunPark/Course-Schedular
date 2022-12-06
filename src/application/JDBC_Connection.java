package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC_Connection {

    static final String URL = "jdbc:mysql://remotemysql.com:3306/D62PAbWL1b";
    static final String USER = "D62PAbWL1b";
    static final String PASSWORD = "pT9OQggEJ3";

    public static ResultSet JDBC(String search) {
        ResultSet resultSet = null;

        try{
            Connection connection = DriverManager.getConnection
                    (URL, USER, PASSWORD);

            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from Courses");

            while (resultSet.next()){
                System.out.println(resultSet.getString("Student_id"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;

    }
}
