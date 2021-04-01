package javaFxControllers;

import dbOperations.CustomerOperations;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static javaFxControllers.HomeAppointmentController.*;

public class AddCustomer {

    private final CustomerOperations customerOperations = new CustomerOperations();

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField mobileField;

    @FXML
    private Button addButton;

    @FXML
    void initialize() {

        addButton.setOnAction(e -> {

            if(nameField.getText().isEmpty())
            {
                showAlert(Alert.AlertType.ERROR, "Error", "Name can not be empty");
            }
            else if(emailField.getText().isEmpty())
            {
                showAlert(Alert.AlertType.ERROR, "Error", "Email address can not be empty");
            }
            else if(mobileField.getText().isEmpty())
            {
                showAlert(Alert.AlertType.ERROR, "Error", "Mobile number can not be empty");
            }
            else
            {
                boolean success = customerOperations.signup(nameField.getText(), emailField.getText(), mobileField.getText());

                if(success)
                {
                    showAlert(Alert.AlertType.CONFIRMATION, "Successful", "Successfully SignedUp");
                    emailField.setText(null);
                    mobileField.setText(null);
                    nameField.setText(null);
                }
                else
                {
                    showAlert(Alert.AlertType.ERROR, "Error", "Error occurred, Can not save to database");
                }
            }

        });
    }
}
