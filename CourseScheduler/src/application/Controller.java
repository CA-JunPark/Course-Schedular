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
    private ListView<String> listView;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextArea textAreaDetailsMain;


    //******** OBJECTS OF SORT OPTIONS ******//
    @FXML
    private MenuButton menuButtonSort;
    @FXML
    private CheckMenuItem SortCheckCode;
    @FXML
    private CheckMenuItem SortCheckTitle;
    //************** END *****************//


    //******** OBJECTS OF SEARCH CHOICE ******//
    @FXML
    private CheckMenuItem SearchByCourseCode;
    @FXML
    private CheckMenuItem SearchByProfessor;
    @FXML
    private CheckMenuItem SearchByCourseTitle;
    @FXML
    private TextField textFieldSearchByDisplay;
    //************** END *****************//


    //******** OBJECTS OF SEARCH FEATURE ******//
    @FXML
    private TextField textFieldSearch;
    @FXML
    private Button buttonSearch;
    //************** END *****************//



    public String searchOption = "CourseCode";
    public String sortOption = "CourseCode";


    boolean isClicked = false;
    public static Stage stage;
    // last clicked index in the listView
    static int index;

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

    static HashMap<String,CourseButton[]> scheduleMap = new HashMap<>();

    // Calender Time
    private static ReservedTime mon = new ReservedTime(1);
    private static ReservedTime tue = new ReservedTime(2);
    private static ReservedTime wed = new ReservedTime(3);
    private static ReservedTime thr = new ReservedTime(4);
    private static ReservedTime fri = new ReservedTime(5);
    public  static ReservedTime[] reservedTimes = {mon, tue, wed, thr, fri};

    public void initialize(){
        setTimeLines();
        SearchByCourseCode.setSelected(true);
        SearchByCourseTitle.setSelected(false);
        SearchByProfessor.setSelected(false);
        textFieldSearchByDisplay.setText("Search By Course Code");

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
        int[] startEnd = ReservedTime.getStartEnd(getStartTimeFromData(T.get(index)), getEndTimeFromData(T.get(index)));
        for (String s : w) {
            int day = getDayOfWeekFromData(s);
            if (reservedTimes[day - 1].conflictCheck(startEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There is Conflict", ButtonType.CLOSE);
                alert.showAndWait();
                return;
            }
        }
        for (int i = 0; i < length; i ++) {
            int day = getDayOfWeekFromData(w[i]);

            CourseButton button =  addCourse(C1.get(index), S.get(index), C2.get(index),
                                        T.get(index), w[i], w, P.get(index),CR.get(index), D.get(index), "#FF6666");
            reservedTimes[day-1].reserve(startEnd);
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
            AnchorPane pane = fxmlLoader.load();
            scene = new Scene(pane);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DetailController detailController = fxmlLoader.getController();
        detailController.setTextAreaDetails(clickedButton);
        detailController.setTextField(clickedButton);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Details");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public static String getStartTimeFromData(String time){
        String[] timeArr = time.split(" "); // timeArr[1] = AM or PM
        time = timeArr[0];
        String startTime;
        if (timeArr[1].equals("PM")){
            String[] hrAndM = time.substring(0,5).split(":");
            int conversion = Integer.parseInt(hrAndM[0]);
            conversion += 12;
            hrAndM[0] = Integer.toString(conversion);
            startTime = hrAndM[0] + hrAndM[1];
        }
        else{
            startTime = time.substring(0,5).replace(":","") ;
        }
        return startTime;
    }
    public static String getEndTimeFromData(String time){
        String[] timeArr = time.split(" "); // timeArr[1] = AM or PM
        time = timeArr[0];
        String endTime;
        if (timeArr[1].equals("PM")){
            String[] hrAndM = time.substring(6).split(":");
            int conversion = Integer.parseInt(hrAndM[0]);
            conversion += 12;
            hrAndM[0] = Integer.toString(conversion);
            endTime = hrAndM[0] + hrAndM[1];
        }
        else{
            endTime = time.substring(6).replace(":","") ;
        }
        return endTime;
    }
    public static int getDayOfWeekFromData(String days){
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
        resetInfo();
        listView.getItems().clear();
        try{
            while(a.next()){
                String code = a.getString("CourseCode");
                C1.add(code);
                S.add(a.getString("Section"));
                C2.add(a.getString("CourseTitle"));

                String time = a.getString("_Time");
                if (time.length() > 4){
                    String[] t = time.split(" ");
                    String start = t[0];
                    String end = t[3];
                    String ampm = t[4];
                    if (start.length() == 4){
                        start = "0" + t[0];
                    }
                    if(end.length() == 4){
                        end = "0" + t[3];
                    }
                    T.add(start + t[2] + end + " " + t[4]);
                }
                else{
                    T.add("12:00-12:01 PM");
                }

                String date = a.getString("_Date");
                String[] d = date.split("");
                W.add(d);
                D.add(a.getString("_Description"));
                P.add(a.getString("Instructor"));
                CR.add(a.getInt("Credit"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        for (int i=0; i < C1.size(); i++) {
            listView.getItems().add(C1.get(i) + " " + S.get(i) + ": " + C2.get(i));
        }
    }

    public void processButtonSearch(ActionEvent event) throws SQLException{
        String input = textFieldSearch.getText();
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



    // This Two Function Control the Users Choice of Semester and Sort Options //
    @FXML
    public void checkSearchOption(ActionEvent event){
        CheckMenuItem selected = (CheckMenuItem) event.getSource();
        if (selected.equals(SearchByCourseCode)){
            SearchByCourseCode.setSelected(true);
            SearchByCourseTitle.setSelected(false);
            SearchByProfessor.setSelected(false);
            textFieldSearchByDisplay.setText("Search By Course Code");
        }
        if (selected.equals(SearchByCourseTitle)){
            SearchByCourseCode.setSelected(false);
            SearchByCourseTitle.setSelected(true);
            SearchByProfessor.setSelected(false);
            textFieldSearchByDisplay.setText("Search By Course Title");
        }
        if (selected.equals(SearchByProfessor)){
            SearchByCourseCode.setSelected(false);
            SearchByCourseTitle.setSelected(false);
            SearchByProfessor.setSelected(true);
            textFieldSearchByDisplay.setText("Search By Professor");
            textFieldSearchByDisplay.setText("Search By Professor");
        }

    }
    @FXML
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
    // ********************* End *********************//
}
