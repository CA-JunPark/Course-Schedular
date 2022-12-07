package application;
import com.sun.source.tree.WhileLoopTree;

import java.sql.*;
import java.util.ArrayList;

public class JDBC_Connection {

    static final String URL = "jdbc:mysql://remotemysql.com:3306/D62PAbWL1b";
    static final String USER = "D62PAbWL1b";
    static final String PASSWORD = "pT9OQggEJ3";

    //https://www.youtube.com/watch?v=e8g9eNnFpHQ

    public static ResultSet initialSearch(){
        ResultSet resultSet = null;
        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();
            String select = "SELECT * FROM courses c JOIN courseDescriptions d using (CourseCode) WHERE CourseCode like 'CMPT%' ORDER BY courseCode ASC";
            resultSet = statement.executeQuery(select);
        } catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public static ResultSet Search(String input, String searchOption, String sortOption) throws SQLException {
        ResultSet resultSet = null;
        System.out.println("sortOp = " + sortOption);
        String where = String.format("%2$s like '%1$s%%' or %2$s like '%%%1$s' or %2$s like '%%%1$s%%'", input, searchOption);

        String select = String.format("SELECT * FROM courses c JOIN courseDescriptions d using (CourseCode) WHERE %2$s ORDER BY %3$s ASC", input, where, sortOption);
        System.out.println(select);
        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(select);

        } catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public static void DeleteCoursesRecord(String CourseCode, String Section){
        String query = String.format("delete from courses where CourseCode = '%1$s' and Section = '%2$s'", CourseCode, Section);
        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateCoursesRecord(String currentCourseCode, String currentSection,
                                    String newCourseCode, String newCourseTitle, String newSection, String newTime,
                                    String newDate, String newProf, int newCredit){
        String query = String.format("UPDATE courses SET CourseCode = '%1$s', CourseTitle = '%2$s', Section = '%3$s', " +
                                    "_Time = '%4$s', _Date = '%5$s', Instructor = '%6$s', Credit = '%7$d' " +
                                    "WHERE CourseCode = '%8$s' and Section = '%9$s'",
                newCourseCode, newCourseTitle, newSection, newTime,newDate, newProf, newCredit, currentCourseCode, currentSection);
        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
