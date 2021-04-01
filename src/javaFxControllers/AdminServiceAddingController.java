package javaFxControllers;

import dbOperations.DbServices;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import entities.Service;
import java.sql.SQLException;

public class AdminServiceAddingController {

    private final DbServices dbServices = DbServices.getInstance();

    @FXML
    private TextField serviceName;

    @FXML
    private TextField servicePrice;

    @FXML
    void addService() throws SQLException {
        validateInput();
    }

    private void validateInput() throws SQLException {
        if (serviceName.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Must provide service name", "Please enter service name");
        } else if (servicePrice.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Must provide service price", "Please enter service price");
        } else {
            Double price = Double.parseDouble(servicePrice.getText());
            addServiceToDatabase(serviceName.getText(), price);
        }
    }

    private void addServiceToDatabase(String serviceName, Double servicePrice) throws SQLException {

        Service service = new Service();

        service.setServiceName(serviceName);
        service.setServicePrice(servicePrice);

        String response = dbServices.addNewService(service);

        if (response.equals("Successfully added new service")) {
            showAlert(Alert.AlertType.CONFIRMATION, "Service added successfully", response);
        } else {
            showAlert(Alert.AlertType.ERROR, "Can not add service, please try again", response);
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
