package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class initialWindowController {

    @FXML
    private Button buttonAdmin;

    @FXML
    private TextField userNameInput;
    @FXML
    private Button buttonEnter;

    public void initialize(){

    }
    @FXML
    void processButtonAdmin(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        loadAdminPage(stage);
        Stage toClose = (Stage) buttonAdmin.getScene().getWindow();
        toClose.close();
    }

    @FXML
    void processButtonEnter(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        loadMainPage(stage);
        Stage toClose = (Stage) buttonEnter.getScene().getWindow();
        toClose.close();
    }

    public void loadMainPage(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainpage.fxml")));
        stage.setTitle("Course Scheduler - "+userNameInput.getText());
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public void loadAdminPage(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminPasswordWindow.fxml")));
        stage.setTitle("Admin Login");
        Scene scene = new Scene(root, 400, 200);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
