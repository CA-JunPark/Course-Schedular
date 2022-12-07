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
        String where = String.format("%2$s like '%1$s%%' or %2$s like '%%%1$s' or %2$s like '%%%1$s%%'", input, searchOption);

        String select = String.format("SELECT * FROM courses c JOIN courseDescriptions d using (CourseCode) WHERE %2$s ORDER BY %3$s ASC", input, where, sortOption);
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
        updateTable(query);
    }

    public static void updateCoursesRecord(String currentCourseCode, String currentSection,
                                    String newCourseCode, String newCourseTitle, String newSection, String newTime,
                                    String newDate, String newProf, int newCredit){
        String query = String.format("UPDATE courses SET CourseCode = '%1$s', CourseTitle = '%2$s', Section = '%3$s', " +
                                    "_Time = '%4$s', _Date = '%5$s', Instructor = '%6$s', Credit = '%7$d' " +
                                    "WHERE CourseCode = '%8$s' and Section = '%9$s'",
                newCourseCode, newCourseTitle, newSection, newTime,newDate, newProf, newCredit, currentCourseCode, currentSection);
        updateTable(query);
    }

    public static void addCoursesRecord(String newCourseCode, String newCourseTitle, String newSection, String newTime,
                                        String newDate, String newProf, int newCredit) throws SQLException {

        int num = 0;
        ResultSet count = execute("SELECT count(num) c from courses");
        while (count.next()){
            num = count.getInt("c") + 1;
        }
        if (num == 0){return;}
        String sql = String.format("INSERT INTO courses(num,CourseCode,CourseTitle,Section,_Time,_Date,Instructor,Credit)" +
                                    " VALUES (%8$d,'%1$s','%2$s','%3$s','%4$s','%5$s','%6$s',%7$d)",
                                    newCourseCode,newCourseTitle,newSection,newTime,newDate,newProf,newCredit,num);
        System.out.println(sql);
        updateTable(sql);
    }

    private static ResultSet execute(String query){
        ResultSet resultSet = null;
        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

        } catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    private static void updateTable(String sql){

        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
