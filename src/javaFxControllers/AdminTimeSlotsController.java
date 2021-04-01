package javaFxControllers;


import dbOperations.DbServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AdminTimeSlotsController {

    private final DbServices dbServices = DbServices.getInstance();

    @FXML
    private TextField timeSlotInput;

    @FXML
    void saveTimeSlotToDatabase(ActionEvent event) {
        if (timeSlotInput.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Time slot can not be empty", "Please enter a suitable timeslot");
        } else {
            saveTimeSlot();
        }
    }

    private void saveTimeSlot() {
        String response = dbServices.saveTime(timeSlotInput.getText());
        if (response.equals("Successfully added time slot to database")) {
            showAlert(Alert.AlertType.CONFIRMATION, "Time slot added", response);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error occurred", "Can not add time slot, please try again");
        }
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
