package application;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AboutController {

    @FXML
    private TextArea textAreaAbout;
    @FXML
    private AnchorPane anchorPane;
    

    public void initialize() throws IOException {
        FileInputStream file = new FileInputStream("CourseScheduler/src/application/about.txt");
        String text = new String(file.readAllBytes(), StandardCharsets.UTF_8);
        textAreaAbout.setText(text);
        textAreaAbout.prefWidthProperty().bind(anchorPane.widthProperty().subtract(45));
        textAreaAbout.prefHeightProperty().bind(anchorPane.heightProperty().subtract(45));

    }
}
