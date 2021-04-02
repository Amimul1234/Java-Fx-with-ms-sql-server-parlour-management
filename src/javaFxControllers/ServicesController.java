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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
    private TextField searchKeyword;

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

    @FXML
    void searchService() {
        new Thread(() -> {

            final List<Service> servicesList = dbOperations.DbServices.getInstance()
                    .getSearchedService(searchKeyword.getText());

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


        servicesTable.setOnMousePressed(event ->
        {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
            {
                Node node = ((Node) event.getTarget()).getParent();

                TableRow row;

                if (node instanceof TableRow) {
                    row = (TableRow) node;
                } else {
                    row = (TableRow) node.getParent();
                }

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/serviceDetails.fxml"));

                    Scene scene = new Scene(fxmlLoader.load(), 1173, 721);
                    Stage stage = new Stage();
                    stage.setTitle("Service Details");

                    ServiceDetails serviceDetails = fxmlLoader.getController();
                    serviceDetails.setService(servicesTable.getSelectionModel().getSelectedItem());


                    stage.getIcons().add(new Image("HomeImages/parlourLogo.png"));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(row.getItem());
            }
        });


    }

    private static void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed to fetch services");
        alert.setHeaderText(null);
        alert.setContentText("Failed to get servies, please try again");
        alert.showAndWait();
    }

}
