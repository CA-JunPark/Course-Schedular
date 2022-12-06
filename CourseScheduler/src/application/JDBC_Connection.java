package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class JDBC_Connection {

    static final String URL = "jdbc:mysql://remotemysql.com:3306/D62PAbWL1b";
    static final String USER = "D62PAbWL1b";
    static final String PASSWORD = "pT9OQggEJ3";

    
    //https://www.youtube.com/watch?v=e8g9eNnFpHQ
    public static void main(String[] args) {
        ResultSet a = initialSearch();
        ArrayList<String> C1 = null;
        ArrayList<String> C2 = null;
        ArrayList<String> T = null;
        ArrayList<String[]> W = null;
        ArrayList<String> D = null;
        ArrayList<String> P = null;
        try{
            while(a.next()){
                String code = a.getString("CourseCode") + " " + a.getString("Section");
                C1.add(code);
                C2.add(a.getString("CourseTitle"));
                String time = a.getString("_Time");
                String[] t = time.split(" ");
                T.add(t[0]+t[2]+t[3]);
                String date = a.getString("_Date");
                String[] d = date.split("");
                W.add(d);
                D.add(a.getString("_DEscription"));
                P.add(a.getString("Instructor"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public static ResultSet initialSearch(){
        ResultSet resultSet = null;
        try{
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM courses c JOIN courseDescriptions d using (CourseCode) WHERE CourseCode like 'CMPT%' ORDER BY courseCode ASC");
        } catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }
}
