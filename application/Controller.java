package application;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Controller {

    @FXML
    private MenuBar MenuBar;

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
    String[] C1 = new String[]{"CMPT 211 A","CMPT 360 A","CMPT 380 A","CMPT 470 A"};
    String[] C2 = new String[]{"Web Programming I", "Comparative Programming Languages", "Artificial Intelligence", "Introduction to Bioinformatics"};
    String[] T = new String[]{"18:15~21:00", "09:30~10:45", "15:00~16:15", "12:00~14:45"};
    String[][] W = new String[][]{{"R"},{"M","W","F"},{"T","R"},{"F"}};
    String[] D = new String[]{"An introduction to web application development using current web technologies, best practices, and standards. The modern web application framework will be studied.",
            "The history, development, and design principles for programming notations. The design and internal operations of the major notational categories are examined in detail. Students are expected to become proficient in at least four languages they have not previously learned, typically chosen from historical, modern working, and cutting edge languages and from among such (non-exclusive) categories as Algol-descended, functional scripting, Web-based, modular, application-specific, visual, and object oriented. They will also learn how to select appropriate programming notations for a given project. Programming will be undertaken in at least three OS environments.",
            "Artificial Intelligence: knowledge representation, logic programming, knowledge inference. Application domains within the dicisipline of Artificial Intelligence include logical and probabilistic reasoning, natural language understanding, vision and expert systems.",
            "An overview of the interdisciplinary science of genomics, proteomics, and bioinformatics which applies the tools of information technology (computer hardware and software) to analyze biological data such as gene or protein sequences. This course examines the theory of bioinformatics as well as its practical application to biological problems using approaches such as BLAST searches, phylogenetics, and protein structure function analysis."};
    String[] P = new String[]{"Mcbride, Ryan","Sutcliffe, Richard Joseph","Park, Andrew Jaehyung","Tsang, Herbert H" };

    public void initialize(){
        setTimeLines();
        addCourse(C1[0], getStartTimeFromData(T[0]), getEndTimeFromData(T[0]), getDayOfWeekFromData(W[0][0]), "#4D66CC");

        addCourse(C1[1], getStartTimeFromData(T[1]), getEndTimeFromData(T[1]), getDayOfWeekFromData(W[1][0]), "#664DB3");
        addCourse(C1[1], getStartTimeFromData(T[1]), getEndTimeFromData(T[1]), getDayOfWeekFromData(W[1][1]), "#664DB3");
        addCourse(C1[1], getStartTimeFromData(T[1]), getEndTimeFromData(T[1]), getDayOfWeekFromData(W[1][2]), "#664DB3");

        addCourse(C1[2], getStartTimeFromData(T[2]), getEndTimeFromData(T[2]), getDayOfWeekFromData(W[2][0]), "#FF9966");
        addCourse(C1[2], getStartTimeFromData(T[2]), getEndTimeFromData(T[2]), getDayOfWeekFromData(W[2][1]), "#FF9966");

        addCourse(C1[3], getStartTimeFromData(T[3]), getEndTimeFromData(T[3]), getDayOfWeekFromData(W[3][0]), "#FF6666");

        for (int i=0; i<C1.length; i++) {
            listView.getItems().add(C1[i]+": "+C2[i]);
        }


    }
    @FXML
    public void processClickOnListView(MouseEvent mouseEvent) {
        if (!listView.getSelectionModel().isEmpty()) {
            int index = listView.getSelectionModel().getSelectedIndex();
            textAreaDetailsMain.setText(D[index]+"\n\n"+Arrays.toString(W[index])+"\n"+T[index]+"\n"+P[index]);
        }
    }
    @FXML
    void processButtonAdd(ActionEvent event) {

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
}
