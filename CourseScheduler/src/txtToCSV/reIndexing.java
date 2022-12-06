package txtToCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class reIndexing {
    public static void main(String[] args) {
        try{
            File myTxt = new File("CourseScheduler/src/txt/courseDescriptions.csv");
            
            File csv = new File("CourseScheduler/src/txt/courseDescriptionsFinal.csv");;
            
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(myTxt), StandardCharsets.UTF_8));
            
            BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(csv), StandardCharsets.UTF_8));

            
            int count = 0;
            bw.write("sep={\n");
            bw.write("#{CourseCode{Description\n");

            br.readLine();
            br.readLine();
            while (br.ready()){
                String s = br.readLine();
                int numberIndex = s.indexOf("{");
                bw.write(count+s.substring(numberIndex) + "\n");
                bw.flush();
                count++;
            }
            br.close();
            bw.close();
        }
        catch(Exception e){

        }
    }
}
