package javaFxControllers;

import dbOperations.DbServices;
import entities.Service;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.SQLException;

import static javaFxControllers.HomeAppointmentController.*;

public class AddService {

    private final DbServices dbServices = DbServices.getInstance();

    @FXML
    private TextField serviceName;

    @FXML
    private TextField servicePrice;

    @FXML
    private Button addService;

    @FXML
    void initialize() {

        addService.setOnAction(e -> {

            if(serviceName.getText().isEmpty())
            {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Service name cn not be empty");
            }
            else if(servicePrice.getText().isEmpty())
            {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Service price can not be empty");
            }
            else
            {
                Service service = new Service();

                service.setServiceName(serviceName.getText());
                service.setServicePrice(Double.parseDouble(servicePrice.getText()));


                new Thread(() -> {
                    try {
                        dbServices.addNewService(service);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    Platform.runLater(() -> {
                        serviceName.setText(null);
                        servicePrice.setText(null);
                        showAlert(Alert.AlertType.CONFIRMATION, "Successful",
                                "Service successfully created!");
                    });
                }).start();
            }
        });
    }
}
