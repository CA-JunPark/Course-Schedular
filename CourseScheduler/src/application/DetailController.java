package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.util.Optional;

public class DetailController {

    @FXML
    private Button buttonDone;

    @FXML
    private Button buttonDrop;

    @FXML
    private TextArea textAreaDetails;

    @FXML
    private TextField textFieldCourseTitle;

    private String courseCode;

    public void setTextField(CourseButton button){
        this.courseCode = button.getCourseCode();
        textFieldCourseTitle.setText(button.getCourseCode() + " "  + button.getSection() + ": " + button.getCourseTitle());
    }

    public void setTextAreaDetails(CourseButton button){
        textAreaDetails.setWrapText(true);
        String description = "";
        description += button.getDescription() + "\n\n" +
                        button.getTime() + "\n" + button.getProf() + "\nCredit: " + button.getCredit();
        textAreaDetails.setText(description);
    }

    @FXML
    void processButtonDone(ActionEvent event) {
        Stage stage = (Stage) textAreaDetails.getScene().getWindow();
        stage.close();
    }

    @FXML
    void processButtonDrop(ActionEvent event) {
        Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to drop the course");
        alert.setHeight(50);
        alert.setWidth(100);
        alert.setResizable(false);
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            // DO SOMETHING //
            CourseButton[] buttons = Controller.scheduleMap.get(courseCode);
            for (CourseButton button : buttons){
                Pane pane = (Pane) button.getParent();
                pane.getChildren().remove(button);
            }
            Stage stage = (Stage) textAreaDetails.getScene().getWindow();
            stage.close();
        }

    }

}
