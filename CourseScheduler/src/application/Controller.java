package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Menu menuFile;

    @FXML
    private Menu menuHelp;

    @FXML
    private AnchorPane anchorPaneCalendar;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonSearch;

    @FXML
    private ListView<String> listView;

    @FXML
    private MenuButton menuButtonSemester;

    @FXML
    private MenuButton menuButtonSort;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextArea textAreaDetailsMain;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TextField textFieldSemesterDisplay;

    boolean isClicked = false;

    public static Stage stage;
    
    // last clicked index in the listView
    static int index = 0;

    static ArrayList<String> C1 = new ArrayList<>();
    static ArrayList<String> C2 = new ArrayList<>();
    static ArrayList<String> T = new ArrayList<>();
    static ArrayList<String[]> W = new ArrayList<>();
    static ArrayList<String> D = new ArrayList<>();
    static ArrayList<String> P = new ArrayList<>();
    public void initialize(){
        setTimeLines();

        ResultSet a = JDBC_Connection.initialSearch();
        
        try{
            while(a.next()){
                String code = a.getString("CourseCode") + " " + a.getString("Section");
                C1.add(code);
                C2.add(a.getString("CourseTitle"));

                String time = a.getString("_Time");
                String[] t = time.split(" ");
                String start = t[0];
                String end = t[3];
                if (start.length() == 4){
                   start = "0" + t[0];
                }
                if(end.length() == 4){
                    end = "0" + t[3];
                }
                T.add(start+t[2]+end);

                String date = a.getString("_Date");
                String[] d = date.split("");
                W.add(d);
                D.add(a.getString("_Description"));
                P.add(a.getString("Instructor"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        updateListView();
    }
    @FXML
    public void processClickOnListView(MouseEvent mouseEvent) {
        if (!listView.getSelectionModel().isEmpty()) {
            index = listView.getSelectionModel().getSelectedIndex();
            String text = D.get(index)+"\n\n";
            for (String k : W.get(index)){
                text += k;
            }
            text += "\n"+T.get(index)+"\n"+P.get(index);
            textAreaDetailsMain.setText(text);
        }
    }
    @FXML
    void processButtonAdd(ActionEvent event) {
        String[] w = W.get(index);
        int length = w.length;
        
        for (int i = 0; i < length; i++){
            addCourse(C1.get(index), getStartTimeFromData(T.get(index)), 
                        getEndTimeFromData(T.get(index)), getDayOfWeekFromData(w[i]), "#FF6666");
        }
        
    }
    @FXML
    void processScrollPaneClicked(MouseEvent event) {
        if (isClicked) {
            scrollPane.setPrefHeight(416);
            isClicked = false;
        }
        else{
            scrollPane.setPrefHeight(612);
            isClicked = true;
        }
    }
    @FXML
    void processMenuFile(ActionEvent event) {
        // USER INFO
        menuFile.getItems().get(0).setOnAction(e->{

        });
        // EXPORT
        menuFile.getItems().get(1).setOnAction(e->{
            WritableImage image = anchorPaneCalendar.snapshot(null, null);
            ImageView view = new ImageView(image);
            Stage stage = new Stage();
            AnchorPane root = new AnchorPane();
            Scene scene = new Scene(root, image.getWidth(),image.getHeight());
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            root.getChildren().add(view);
        });
        // EXIT
        menuFile.getItems().get(2).setOnAction(e->{
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        });

    }

    @FXML
    void processMenuHelp(ActionEvent event) {
        System.out.println("HELP");
        // ABOUT
        menuHelp.getItems().get(0).setOnAction(e->{
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("aboutPage.fxml")));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            assert root != null;
            Scene scene = new Scene(root, 480, 320);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
            stage.setScene(scene);
            stage.setTitle("About");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });
    }














    // UI Design
    public void setTimeLines(){
        Text textStart = new Text("Start");
        anchorPaneCalendar.getChildren().add(textStart);
        textStart.setLayoutX(5);
        textStart.setLayoutY(15);
        for (int i=0; i<18; i++){
            Line line = new Line();
            Text text = new Text(6+i+":"+"00");
            text.setLayoutX(5);
            text.setLayoutY(38+i*60);
            line.setStartX(45);
            line.setEndX(648);
            line.setLayoutY(34+i*60);
            anchorPaneCalendar.getChildren().addAll(text, line);
        }
    }
    // to add a course on the calendar
    // addCourse(courseTitle, start, end, M/W/T/R/F,)
    public void addCourse(String description, String start, String end, int day, String color){
        int interval = Integer.parseInt(end)-Integer.parseInt(start);
        String time = "";
        time = time.concat(start.substring(0,2)+":").concat(start.substring(2)+"~");
        time = time.concat(end.substring(0,2)+":").concat(end.substring(2));
        Button button = new Button();
        button.setText(description+"\n"+time);
        button.setPrefSize(115, getTimeDifference(start, end));
        button.setLayoutX(52+(115*(day-1)));
        button.setStyle("-fx-background-color: "+color);
        button.setLayoutY(34+((Integer.parseInt(start.substring(0,2))+Double.parseDouble(start.substring(2))/60)-6)*60);
        anchorPaneCalendar.getChildren().add(button);
        button.setOnMouseClicked(v->{
            AnchorPane root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("details.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert root != null;
            Scene scene = new Scene(root, 540, 360);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Details");
            stage.show();
        });
    }

    public String getStartTimeFromData(String time){
        return time.substring(0,5).replace(":","");
    }
    public String getEndTimeFromData(String time){
        return time.substring(6).replace(":","");
    }
    public int getDayOfWeekFromData(String days){
        if (days.equals("M")){
            return 1;
        }
        if (days.equals("T")){
            return 2;
        }
        if (days.equals("W")){
            return 3;
        }
        if (days.equals("R")){
            return 4;
        }
        if (days.equals("F")){
            return 5;
        }
        else{
           return 0;
        }
    }
    public int getTimeDifference(String start, String end){
        int H = Integer.parseInt(end.substring(0,2))-Integer.parseInt(start.substring(0,2));
        int M = Integer.parseInt(end.substring(2))-Integer.parseInt(start.substring(2));
        return H*60+M;
    }

    public void updateListView(){
        for (int i=0; i < C1.size(); i++) {
            listView.getItems().add(C1.get(i)+": "+C2.get(i));
        }
    }
}
