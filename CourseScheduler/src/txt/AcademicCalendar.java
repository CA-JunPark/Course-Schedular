package txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class AcademicCalendar {
    
    public static void main(String[] args) {
        /* This code only produce partial courseDescriptions
         * I did manually to find the exceptions on the csv file produced 
         * and fix those directly on the csv file
         * 
         * 7 15 35 64 91 92 95 147 148 149 160 161 162 165 188 197 242 243 244 262 291 292 293 302 317
         * 327 335 337 338 373 375 377 383 388 401 440 were the exceptions that I found
         */
        int lineIndex = 0;
        try{
            File myTxt = new File("CourseScheduler/src/txt/Academic_Calendar.txt");
            
            File csv = new File("CourseScheduler/src/txt/courseDescriptions.csv");

            File courses = new File("CourseScheduler/src/txt/courses.csv");
            
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(myTxt), StandardCharsets.UTF_8));
            BufferedReader br2 = new BufferedReader(
                new InputStreamReader(new FileInputStream(courses), StandardCharsets.UTF_8));
            
            BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(csv), StandardCharsets.UTF_8));
            
            HashMap<String, String> codeMap = new HashMap<String, String>(); // key = code, value = title
            br2.readLine();
            br2.readLine();
            while (br2.ready()){
                String[] course = br2.readLine().split(";");
                codeMap.put(course[1], course[2]);
            }
            
            String courseDescription = "";

            HashMap<String, String> descriptionMap = new HashMap<String, String>();
            
            while(br.ready()){
                String s = br.readLine();
                lineIndex ++;
                // exceptions
                if (lineIndex == 3528 || lineIndex == 10606 || lineIndex == 14316){
                    continue;
                }
                else{
                    if (ContainsOneOfItem(s, codeMap)){
                        courseDescription = "";
                        courseDescription += s + " ";
                        while (br.ready()){
                            String a = br.readLine();
                            lineIndex ++;
                            if (a.equals("")){
                                courseDescription += "\n";
                                break;
                            }
                            courseDescription += a + " ";
                        }
                    }
                }
                
                if (!courseDescription.equals("")){
                    int secondSpaceIndex = courseDescription.indexOf(" ", 5);
                    String courseCode = courseDescription.substring(0, secondSpaceIndex);
                    descriptionMap.put(courseCode, courseDescription);
                }
            }

            bw.write("sep={\n");
            bw.write("#{CourseCode{Description\n");
            int count = 0;
            for (String code : descriptionMap.keySet()){
                bw.write(count + "{" + code + "{" + descriptionMap.get(code));
                count++;
                bw.flush();
            }
            br.close();
            br2.close();
            bw.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(lineIndex);
        }
        System.out.println(lineIndex);
    }

    public static boolean ContainsOneOfItem(String s, HashMap<String, String> map){
        boolean contains = false;
        if (s.length() > 7){
            s = s.substring(0, 8).strip();
            
            for (String item : map.keySet()){
                if (s.contains(item)){
                    contains = true;
                }
            }
        }
        return contains;
    }
    public static boolean isDigitWithSpace(String s){
        try {
            Integer.parseInt(s.substring(0, 3));
            if (s.substring(3).equals(" ")){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }
}
