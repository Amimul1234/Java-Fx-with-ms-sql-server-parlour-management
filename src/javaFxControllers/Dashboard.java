package javaFxControllers;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Dashboard {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button dashboard;

    @FXML
    private Button service;

    @FXML
    private Button appointment;

    @FXML
    private Button addCustomer;

    @FXML
    private Button customerList;

    @FXML
    private Button addService;

    @FXML
    private Button invoice;

    @FXML
    private Button logOut;

    @FXML
    private Button servicesReview;

    @FXML
    private Button addCustomerDetails;

    @FXML
    private Button addServiceDetails;


    @FXML
    void initialize() {

        try {
            Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().
                    getClassLoader().getResource("Fxml/AdminDashboard.fxml")));
            borderPane.setCenter(view);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        dashboard.setOnAction(e -> {
            try {
                Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/AdminDashboard.fxml")));
                borderPane.setCenter(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        addService.setOnAction(e -> {
            try {
                Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/AddServices.fxml")));
                borderPane.setCenter(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        service.setOnAction(e -> {

            try {
                Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/ServiceTable.fxml")));
                borderPane.setCenter(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        servicesReview.setOnAction(e -> {
            try {
                Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().
                        getResource("Fxml/serviceReviews.fxml")));
                borderPane.setCenter(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        appointment.setOnAction(e -> {
            try {
                Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/Appointment.fxml")));
                borderPane.setCenter(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        addCustomer.setOnAction(e -> {
            try {
                Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/AddCustomer.fxml")));
                borderPane.setCenter(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        customerList.setOnAction(e -> {
            try {
                Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/CustomerList.fxml")));
                borderPane.setCenter(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        invoice.setOnAction(e -> {
            try {
                Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().
                        getClassLoader().getResource("Fxml/Invoice.fxml")));
                borderPane.setCenter(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        logOut.setOnAction(e ->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Home.fxml"));
                Scene scene = new Scene(root, 1173, 721);
                Stage appStage = (Stage) borderPane.getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        addCustomerDetails.setOnAction(e -> {
            try {
                Pane view = FXMLLoader.load(Objects.requireNonNull(getClass().
                        getClassLoader().getResource("Fxml/customerDetails.fxml")));

                borderPane.setCenter(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

    }

}
