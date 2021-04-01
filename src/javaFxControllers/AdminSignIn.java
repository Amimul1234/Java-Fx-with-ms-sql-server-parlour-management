package javaFxControllers;

import java.io.IOException;
import java.util.Objects;
import controllers.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminSignIn {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private TextField usernameFIeld;

    @FXML
    private PasswordField paswordField;

    @FXML
    private Button signInButton;

    @FXML
    void initialize() {

        signInButton.setOnAction(e -> {

            if (LoginController.
                    isValid(usernameFIeld.getText(), paswordField.getText()))
            {

                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().
                            getClassLoader().getResource("Fxml/Dashboard.fxml")));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                Stage primaryStage = (Stage) anchorpane.getScene().getWindow();

                primaryStage.getScene().setRoot(root);


            } else {
                showAlert(Alert.AlertType.ERROR, "Wrong username and password!",
                        "Please enter right username and password");

            };
        });
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
