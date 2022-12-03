package sqlInsert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class insert {
    static final String URL = "jdbc:mysql://remotemysql.com:3306/D62PAbWL1b";
    static final String USER = "D62PAbWL1b";
    static final String PASSWORD = "pT9OQggEJ3";
    public static void main(String[] args) {
        try{
            /*/////////////////////

            DO NOT EVER RUN THIS CODE AGAIN.
            IT WILL CREATE DUPLICATES IN THE TABLE

            //////////////////*/

            // insert courseDescription
            // File myTxt = new File("CourseScheduler/src/csvFile/courseDescriptionsFinal.csv");
            
            // BufferedReader br = new BufferedReader(
            //     new InputStreamReader(new FileInputStream(myTxt), StandardCharsets.UTF_8));
            
            
            // Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Statement statement = connection.createStatement();
    
            // //discard first 2 lines
            // br.readLine();
            // br.readLine();
            // while(br.ready()){
            //     String s = br.readLine();
            //     String[] a = s.split("%");
            //     String sql = String.format("INSERT INTO courseDescriptions VALUES (%1$s, \"%2$s\", \"%3$s\")", a[0],a[1],a[2]);
            //     // System.out.println(sql);
            //     // break;
            //     statement.executeUpdate(sql);
            // }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
