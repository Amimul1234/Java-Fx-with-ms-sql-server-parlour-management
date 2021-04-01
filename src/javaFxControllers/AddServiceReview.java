package javaFxControllers;

import dbOperations.DbServices;
import entities.Service;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static javaFxControllers.HomeAppointmentController.showAlert;

public class AddServiceReview {

    private final DbServices dbServices = DbServices.getInstance();
    private final List<Service> serviceList = new ArrayList<>();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ChoiceBox<String> selectedService;

    @FXML
    private Text selectServiceText;

    @FXML
    private TextArea serviceReview;

    @FXML
    private Rating ratingBar;

    @FXML
    public void initialize()
    {
        new Thread(() -> {

            final List<String> servicesNamesList = dbServices.getServicesName();
            serviceList.addAll(dbServices.getAllServicesRecords());

            if (!servicesNamesList.isEmpty())
            {
                Platform.runLater(() -> {
                    selectedService.getItems().addAll(servicesNamesList);
                    selectedService.setValue(servicesNamesList.get(0));
                    selectServiceText.setVisible(false);
                });
            } else {
                Platform.runLater(() ->
                        showAlert(Alert.AlertType.ERROR, "Can not get services",
                                "Can not get services,please try again"));
            }
        }
        ).start();
    }

    @FXML
    void aboutUs() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/about_us.fxml")));
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void admin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/AdminSigninPage.fxml")));
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void contactUs() {

    }

    @FXML
    void homeScreen() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/Home.fxml")));
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void serviceReview() {

    }

    @FXML
    void services() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Fxml/Services.fxml")));
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        primaryStage.getScene().setRoot(root);
    }

    @FXML
    void submitReview() {
        if(serviceReview.getText().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Service review can not be empty");
        }
        else
        {

            new Thread(() -> {

                int serviceId = 0;
                String serviceName = selectedService.getSelectionModel().getSelectedItem();

                for(Service service : serviceList)
                {
                    if(service.getServiceName().equals(serviceName))
                    {
                        serviceId = service.getId();
                        break;
                    }
                }

                boolean saved = dbServices.addServiceReview(serviceId, serviceReview.getText());

                if(saved)
                {
                    Platform.runLater(() -> {
                        serviceReview.setText(null);
                        showAlert(Alert.AlertType.CONFIRMATION, "SUCCESS!", "Review added successfully");
                    });
                }
                else
                {
                    Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error!", "Can not save review"));
                }

            }).start();
        }
    }

}
