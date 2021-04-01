package javaFxControllers;

import entities.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ServicesController implements Initializable {

    @FXML
    private TableView<Service> servicesTable;

    @FXML
    private TableColumn<Service, Integer> serviceId;

    @FXML
    private TableColumn<Service, String> serviceName;

    @FXML
    private TableColumn<Service, Double> serviceCharge;

    @FXML
    void goToAboutUs() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/about_us.fxml")));
        Stage primaryStage = (Stage) servicesTable.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void goToAdmin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/AdminSigninPage.fxml")));
        Stage primaryStage = (Stage) servicesTable.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void goToContactUs() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxmls/AdminTimeSlots.fxml"));

        Stage stage = (Stage) servicesTable.getScene().getWindow();
        Scene scene = null;

        try {
            scene = new Scene(loader.load(), 1114, 627);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
    }

    @FXML
    void goToHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/Home.fxml")));
        Stage primaryStage = (Stage) servicesTable.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void goToService() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeColumns();
        populateServiceTable();
    }

    private void populateServiceTable() {
        new Thread(() -> {

            final List<Service> servicesList = dbOperations.DbServices.getInstance()
                    .getAllServicesRecords();

            if (servicesList.isEmpty()) {
                Platform.runLater(ServicesController::showAlert);
            } else {
                Platform.runLater(() -> {
                    servicesTable.setItems(FXCollections.observableList(servicesList));
                    servicesTable.refresh();
                });
            }
        }).start();
    }

    private void initializeColumns() {
        serviceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        serviceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceCharge.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
    }

    private static void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed to fetch services");
        alert.setHeaderText(null);
        alert.setContentText("Failed to get servies, please try again");
        alert.showAndWait();
    }

}
