package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class adminWindowController {

    @FXML
    private CheckMenuItem SearchByCourseCode;

    @FXML
    private CheckMenuItem SearchByCourseTitle;

    @FXML
    private CheckMenuItem SearchByProfessor;

    @FXML
    private CheckMenuItem SortCheckCode;

    @FXML
    private CheckMenuItem SortCheckTitle;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button buttonSearch;

    @FXML
    private ListView<String> listView;

    @FXML
    private MenuButton menuButtonSemester;

    @FXML
    private MenuButton menuButtonSort;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TextField textFieldSearchByDisplay;

    public String searchOption = "CourseCode";
    public String sortOption = "CourseCode";

    static int index;

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

    @FXML
    void processButtonSearch(ActionEvent event) throws SQLException {
        String input = textFieldSearch.getText();
        ResultSet set = JDBC_Connection.Search(input, searchOption, sortOption);
        updateListView(set);
    }

    void updateListView(ResultSet a){
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


    @FXML
    void processClickOnListView(MouseEvent event) {
        if (!listView.getSelectionModel().isEmpty()) {
            index = listView.getSelectionModel().getSelectedIndex();

            //TODO put data into edit area's text area
        }
    }

}
