package txtToCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class timetable {
    public static void main(String[] args) {
        int lineIndex = 1;
        try{
            File myTxt = new File("CourseScheduler/src/txt/Timetable.txt");
            // File result = new File("CourseScheduler/src/txt/table.txt");
            File csv = new File("CourseScheduler/src/txt/courses.csv");
            
            
            BufferedReader br = new BufferedReader(
                                new InputStreamReader(new FileInputStream(myTxt), StandardCharsets.UTF_8));
            // BufferedWriter bw = new BufferedWriter(
            //                 new OutputStreamWriter(new FileOutputStream(result), StandardCharsets.UTF_8));
            BufferedWriter bw2 = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(csv), StandardCharsets.UTF_8));

            
            ArrayList<String[]> courses = new ArrayList<>();
            ArrayList<Integer> sections = Sections();
            
            String currentCourseCode = "";
            String currentCourseTitle = "";
            String credit = "";
            
            while (br.ready()){
                String s = br.readLine();
                if (sections.contains(lineIndex)){
                    currentCourseCode = s.substring(0,8).strip();
                    // courseTitle
                    if (s.contains("    ")){
                        String temp = s.substring(8).strip();
                        int end = temp.indexOf("   ");
                        currentCourseTitle = temp.substring(0, end);
                    }
                    //credit
                    if (s.contains("Sem. Hr.")){
                        int start = s.indexOf("Sem. Hr.");
                        credit = s.substring(start + 8).strip();
                        if (credit == ""){
                            // System.out.println(lineIndex);
                            credit = br.readLine().strip();
                            lineIndex ++;
                        }
                    }
                }
                
                if (!currentCourseCode.equals("")){
                    if(s.length() > 6){
                        if(s.substring(0, 7).contains("Sec.")){
                            String section = s.substring(0, 14).strip();
                            section = section.substring(5);
                            String time = s.substring(17, 37).strip();
                            String date = s.substring(42, 47).strip();
                            String prof = s.substring(74).strip();
                            if (prof.contains("Fee")){
                                int index = prof.indexOf("Fee");
                                prof = prof.substring(0,index).strip();
                            }

                            String[] description = {currentCourseCode, currentCourseTitle, section, time, date, prof, credit};
                            courses.add(description);
                        }
                    }
                }
                lineIndex ++;
            }
            bw2.write("sep=;\n");
            bw2.write("num;CourseCode;CourseTitle;section;time;date;instructor;credit\n");
            int num = 1;
            for (String[] des : courses){
                String a = des[0] + ";" + des[1] + ";" + des[2] + ";" + des[3] + ";" + des[4] + ";" + des[5] + ";" + des[6] + "\n";
                bw2.write(num + ";" + a) ;
                bw2.flush();
                num++;
            }
            br.close();
            bw2.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(lineIndex);
        }
            
    }

    static ArrayList<Integer> Sections(){
        ArrayList<Integer> sectionLineIndex = new ArrayList<Integer>();
        try{
            File myTxt = new File("CourseScheduler/src/txt/Timetable.txt");
            // File result = new File("CourseScheduler/src/txt/table.txt");
            
            BufferedReader br = new BufferedReader(
                                new InputStreamReader(new FileInputStream(myTxt), StandardCharsets.UTF_8));
            // BufferedWriter bw = new BufferedWriter(
            //                 new OutputStreamWriter(new FileOutputStream(result), StandardCharsets.UTF_8));

            
            int lineCount = 1;
            while (br.ready()){
                String s = br.readLine();
                if (s.length() > 4){
                    String first3 = s.substring(0,3);
                    if (UpperCheck(first3)){
                        sectionLineIndex.add(lineCount);
                    }
                }
                lineCount ++;
                
            }
            br.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return sectionLineIndex;
    }
    public static boolean UpperCheck(String s){
        boolean result = false;
        for (int i = 0; i < s.length(); i++){
            if (Character.isUpperCase(s.charAt(i))){
                result = true;
            }
            else{
                result = false;
                break;
            }
        }
        return result;
    }
}
