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
    private ListView<?> listView;

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
    void processButtonSearch(ActionEvent event) {

    }

    @FXML
    void processClickOnListView(MouseEvent event) {

    }

}
