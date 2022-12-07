package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.ArrayList;

import java.sql.ResultSet;


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
    private MenuButton menuButtonSearchOption;

    @FXML
    private MenuButton menuButtonSort;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextArea textAreaDetailsMain;

    @FXML
    private TextField textFieldSearch;
    @FXML
    private CheckMenuItem SearchCheckCode;

    @FXML
    private CheckMenuItem SearchCheckProf;

    @FXML
    private CheckMenuItem SearchCheckTitle;
    @FXML
    private CheckMenuItem SortCheckCode;

    @FXML
    private CheckMenuItem SortCheckTitle;

    public String searchOption = "CourseCode";
    public String sortOption = "CourseCode";

    @FXML
    private TextField textFieldSemesterDisplay;

    boolean isClicked = false;

    public static Stage stage;
    
    // last clicked index in the listView
    static int index = 0;

    // CourseCode
    static ArrayList<String> C1 = new ArrayList<>();
    // Section
    static ArrayList<String> S = new ArrayList<>();
    // CourseTitle
    static ArrayList<String> C2 = new ArrayList<>();
    // Time
    static ArrayList<String> T = new ArrayList<>();
    // Date
    static ArrayList<String[]> W = new ArrayList<>();
    // Description
    static ArrayList<String> D = new ArrayList<>();
    // prof
    static ArrayList<String> P = new ArrayList<>();
    // credit
    static ArrayList<Integer> CR = new ArrayList<>();

    static HashMap<String,CourseButton[]> scheduleMap = new HashMap<String, CourseButton[]>();


    public void initialize(){
        setTimeLines();
        SearchCheckCode.setSelected(true);
        SortCheckCode.setSelected(true);

        ResultSet a = JDBC_Connection.initialSearch();

        updateListView(a);

    }
    @FXML
    public void processClickOnListView(MouseEvent mouseEvent) {
        if (!listView.getSelectionModel().isEmpty()) {
            index = listView.getSelectionModel().getSelectedIndex();
            String text = D.get(index)+"\n\n";
            for (String k : W.get(index)){
                text += k;
            }
            text += "\n"+T.get(index) + "\n" + P.get(index) + "\nCredit: " + CR.get(index);
            textAreaDetailsMain.setText(text);
        }
    }
    @FXML
    void processButtonAdd(ActionEvent event) throws IOException {
        String[] w = W.get(index);
        int length = w.length;
        CourseButton[] buttons = new CourseButton[length];
        for (int i = 0; i < length; i ++) {
            CourseButton button =  addCourse(C1.get(index), S.get(index), C2.get(index),
                                        T.get(index), w[i], w, P.get(index),CR.get(index),  D.get(index), "#FF6666");
            if (button != null){
               buttons[i] = button;
            }
        }
        if (buttons[0] != null){
            scheduleMap.put(C1.get(index), buttons);
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
    public CourseButton addCourse(String CourseCode, String Section, String CourseTitle,
                          String Time, String oneDate, String[] Date,
                          String prof, int credit, String Description, String color) throws IOException{
        if (Date[0].equals("")) {return null;}
        String startTime = getStartTimeFromData(Time);
        String endTime = getEndTimeFromData(Time);
        int interval = Integer.parseInt(endTime)-Integer.parseInt(startTime);
        String time = "";
        time = time.concat(startTime.substring(0,2)+":").concat(startTime.substring(2)+"~");
        time = time.concat(endTime.substring(0,2)+":").concat(endTime.substring(2));
        CourseButton button = new CourseButton(CourseCode, Section, CourseTitle,
                Time, Date,
                prof, credit, Description);
        button.setText(CourseCode + " " + Section + "\n" + time);
        button.setPrefSize(115, getTimeDifference(startTime, endTime));
        int day = getDayOfWeekFromData(oneDate);
        button.setLayoutX(52+(115*(day-1)));
        button.setStyle("-fx-background-color: "+color);
        button.setLayoutY(34+((Integer.parseInt(startTime.substring(0,2))+Double.parseDouble(startTime.substring(2))/60)-6)*60);
        button.setOnAction(this::clickOnCourse);
        anchorPaneCalendar.getChildren().add(button);
        return button;
    }

    public void clickOnCourse(ActionEvent event){
        CourseButton clickedButton = (CourseButton) event.getSource();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("details.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        DetailController detailController = fxmlLoader.getController();
        detailController.setTextAreaDetails(clickedButton);
        detailController.setTextField(clickedButton);
        Stage stage = new Stage();
        stage.setTitle("Details");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
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

    public void updateListView(ResultSet a){
        ResultSet set = a;
        resetInfo();
        listView.getItems().clear();
        try{
            while(set.next()){
                String code = set.getString("CourseCode");
                C1.add(code);
                S.add(set.getString("Section"));
                C2.add(set.getString("CourseTitle"));

                String time = set.getString("_Time");
                if (time.length() > 4){
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
                }
                else{
                    T.add("12:00-12:01");
                }

                String date = set.getString("_Date");
                String[] d = date.split("");
                W.add(d);
                D.add(set.getString("_Description"));
                P.add(set.getString("Instructor"));
                CR.add(set.getInt("Credit"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        for (int i=0; i < C1.size(); i++) {
            listView.getItems().add(C1.get(i) + " " + S.get(i) + ": " + C2.get(i));
        }
    }

    public void processButtonSearch() throws SQLException {
        String input = textFieldSearch.getText();
        //TODO TODO TODO
        ResultSet set = JDBC_Connection.Search(input, searchOption, sortOption);
        updateListView(set);
    }

    void resetInfo(){
        C1 = new ArrayList<>();
        // Section
        S = new ArrayList<>();
        // CourseTitle
        C2 = new ArrayList<>();
        // Time
        T = new ArrayList<>();
        // Date
        W = new ArrayList<>();
        // Description
        D = new ArrayList<>();
        // prof
        P = new ArrayList<>();
        // credit
        CR = new ArrayList<>();
    }

    // select only one is possible
    public void checkSearchOption(ActionEvent event){
        CheckMenuItem selected = (CheckMenuItem) event.getSource();
        if (selected.equals(SearchCheckCode)){
            SearchCheckCode.setSelected(true);
            SearchCheckTitle.setSelected(false);
            SearchCheckProf.setSelected(false);
            searchOption = "CourseCode";
        }
        else if (selected.equals(SearchCheckTitle)){
            SearchCheckCode.setSelected(false);
            SearchCheckProf.setSelected(true);
            SearchCheckProf.setSelected(false);
            searchOption = "CourseTitle";
        }
        else{
            SearchCheckCode.setSelected(false);
            SearchCheckTitle.setSelected(false);
            SearchCheckProf.setSelected(true);
            searchOption = "Instructor";
        }
    }
    public void checkSortOption(ActionEvent event){
        CheckMenuItem selected = (CheckMenuItem) event.getSource();
        if (selected.equals(SortCheckCode)){
            SortCheckCode.setSelected(true);
            SortCheckTitle.setSelected(false);
            sortOption = "CourseCode";
        }
        else{
            SortCheckCode.setSelected(false);
            SortCheckTitle.setSelected(true);
            sortOption = "CourseTitle";
        }
    }
}
