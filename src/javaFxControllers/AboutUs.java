package javaFxControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class AboutUs {

    @FXML
    private BorderPane borderPane;

    @FXML
    void aboutUs()
    {

    }

    @FXML
    void admin() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "Fxml/AdminSigninPage.fxml")));

        Stage primaryStage = (Stage) borderPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void contactUs()
    {

    }

    @FXML
    void homeScreen() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/Home.fxml")));
        Stage primaryStage = (Stage) borderPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void services() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/Services.fxml")));
        Stage primaryStage = (Stage) borderPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

}
