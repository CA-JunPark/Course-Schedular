package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC_Connection {

    public static void main(String[] args) {
        //https://www.youtube.com/watch?v=e8g9eNnFpHQ
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/D62PAbWL1b", "D62PAbWL1b", "pT9OQggEJ3");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from STUDENT");

            while (resultSet.next()){
                System.out.println(resultSet.getString("Student_id"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
