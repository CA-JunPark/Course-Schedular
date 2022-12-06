package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    void processButtonDone(ActionEvent event) {
        Controller.stage.close();
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
            Controller.stage.close();
        }

    }

}
