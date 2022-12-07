package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class adminPasswordWindowController {

    @FXML
    private Button buttonEnter;
    @FXML
    private TextField passwordInput;


    private final String password = "12345678";

    @FXML
    void processButtonEnter(ActionEvent event) throws IOException {
        if (passwordInput.getText().strip().equals(password)){
            Stage stage = new Stage();
            loadAdminPage(stage);
            Stage toClose = (Stage) buttonEnter.getScene().getWindow();
            toClose.close();
        }
        else{
            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Password Does Not Match");
            alert.setHeaderText("Warning");
            alert.setHeight(50);
            alert.setWidth(100);
            alert.setResizable(false);
            alert.showAndWait();
        }

    }
    public void loadAdminPage(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminWindow.fxml")));
        stage.setTitle("Administrator");
        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
